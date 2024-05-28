/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';

const DEFAULT_OPTIONS = {
	baseDir: '.',
	maxDepth: Infinity,
	type: 'file',
};

/**
 * Given a list of glob patterns and a list of ignore patterns, returns a list
 * of matching files, searching in the current directory.
 */
export default async function expandGlobs(
	matchGlobs,
	ignoreGlobs = [],
	options = {}
) {
	const {baseDir, maxDepth, type} = {
		...DEFAULT_OPTIONS,
		...options,
	};

	const ignorers = [];
	const matchers = matchGlobs.map(getRegExpForGlob);
	const results = [];

	// If any matchers are negated, move them into the "ignores" list.

	for (let i = matchers.length - 1; i >= 0; i--) {
		if (matchers[i].negated) {

			// Make a copy of the regular expression without the "negated" flag.

			ignorers.unshift(getRegExpForGlob(matchGlobs[i].slice(1)));
		}
	}

	// Make note of index of the last negation. (If a file has been
	// ignored, we can stop testing it as soon as we get past the last
	// negation.)

	let lastNegationIndex = 0;

	ignorers.push(
		...ignoreGlobs.map((glob, index) => {
			const regExp = getRegExpForGlob(glob);

			if (regExp.negated) {
				lastNegationIndex = ignorers.length + index;
			}

			return regExp;
		})
	);

	// As a special case, if we see an ignore glob like "a/b/c/**" past the
	// lastNegationIndex we can short-circuit.

	const prunable = new Map();
	const seen = [];

	for (let i = lastNegationIndex; i < ignorers.length; i++) {
		const glob = ignorers[i].glob;

		const match = glob.match(/^([^!*]+)\/\*\*$/);

		if (match) {
			const base = match[1];

			// Warn about overlapping ignore patterns. This check is O(n^2) but
			// n is expected to be tiny (approx. 10).

			seen.forEach((previous) => {
				if (
					previous.startsWith(base) ||
					base.startsWith(previous) ||
					previous.endsWith(base) ||
					base.endsWith(previous)
				) {
					throw new Error(
						`Redundant ignore patterns (\`${previous}\`, \`${base}\`) detected`
					);
				}
			});

			seen.push(base);

			const components = base.split('/');

			// For fast lookup later on, given "a/b/c", produce this trie:
			//
			//     c -> b -> a -> true
			//

			let current = prunable;

			for (let j = components.length - 1; j >= 0; j--) {
				const component = components[j];

				if (!current.has(component)) {
					if (j) {
						current.set(component, new Map());
					}
					else {

						// Mark the root with "true".

						current.set(component, true);
					}
				}

				current = current.get(component);
			}
		}
	}

	let currentDepth = 0;

	async function traverse(directory) {
		if (currentDepth >= maxDepth) {
			return;
		}

		currentDepth++;

		const entries = await fs.readdir(directory);

		for (const entry of entries) {
			const file = path.posix.join(directory, entry);
			const testedFilePath = path.isAbsolute(baseDir)
				? path.relative(baseDir, file)
				: file;

			// Check trie to see whether entire subtree can be pruned.

			let trie = prunable;
			let current = file;

			while (current !== '.') {
				trie = trie.get(path.basename(current));

				if (trie === true) {
					return;
				}
				else if (!trie) {
					break;
				}

				current = path.dirname(current);
			}

			let ignored = false;

			for (let i = 0; i < ignorers.length; i++) {
				const ignorer = ignorers[i];

				if (ignored ^ ignorer.negated) {

					// File is ignored, but ignorer is not a negation;
					// or file is not ignored, and ignorer is a negation.

					continue;
				}

				if (ignorer.test(testedFilePath)) {
					if (ignorer.negated) {

						// File got unignored.

						ignored = false;
					}
					else {

						// File is provisionally ignored, for now.

						ignored = true;
					}
				}

				if (ignored && i >= lastNegationIndex) {

					// File got definitively ignored.

					return;
				}
			}

			const stat = await fs.stat(file);

			const match = () =>
				matchers.some((matcher) => matcher.test(testedFilePath));

			if (stat.isDirectory()) {
				if (type === 'directory' && match()) {
					results.push(file);
				}

				await traverse(file);
			}
			else if (type === 'file' && match()) {
				results.push(file);
			}
		}

		currentDepth--;
	}

	await traverse(baseDir);

	return results;
}

/**
 * Returns a regular expression equivalent to the supplied `glob`.
 *
 * Semantics match those described in `man 5 gitignore`.
 */
function getRegExpForGlob(glob) {
	let pattern = '';

	const state = {
		input: glob,
		lastMatch: null,
	};

	const negated = scan('!', state);

	const anchored = scan('/', state);

	if (!anchored && !state.input.startsWith('**/')) {

		// Unless anchored, all patterns implicitly match anywhere in the
		// hierarchy.

		state.input = `**/${state.input}`;
	}

	while (state.input.length) {
		if (scan('/**/', state)) {
			pattern += '/([^/]+/)*';
		}
		else if (scan('**/', state)) {
			pattern += '([^/]+/)*';
		}
		else if (scan('/**', state)) {
			pattern += '.+';
		}
		else if (scan('**', state)) {
			pattern += '[^/]*';
		}
		else if (scan('*', state)) {
			pattern += '[^/]*';
		}
		else if (scan(/[^/*]+/, state)) {
			pattern += escape(state.lastMatch);
		}
		else if (scan('/', state)) {
			pattern += escape('/');
		}
	}

	const result = new RegExp(`^${pattern}$`);
	result.glob = glob;

	if (negated) {
		result.negated = true;
	}

	return result;
}

/**
 * Helper function for scanning and consuming a "prefix" at the beginning of
 * a glob pattern.
 *
 * Returns `true` if the prefix was consumed.
 */
function scan(prefix, state) {
	if (typeof prefix === 'string') {
		if (state.input.startsWith(prefix)) {
			state.input = state.input.slice(prefix.length);
			state.lastMatch = prefix;

			return true;
		}

		return false;
	}
	else {
		let pattern = prefix.source;

		if (!pattern.startsWith('^')) {
			pattern = '^' + pattern;
		}

		const match = state.input.match(new RegExp(pattern));

		if (match) {
			state.input = state.input.slice(match[0].length);
			state.lastMatch = match[0];

			return true;
		}

		return false;
	}
}

function escape(pattern) {

	// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_Expressions

	return pattern.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
}
