/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const badFormat =
	'@deprecated requires format: https://github.com/liferay/liferay-frontend-projects/blob/master/projects/eslint-config/plugins/portal/docs/rules/deprecation.md';

const blockCommentsOnly =
	'@deprecated annotations should only appear in block (/**/) comments';

const DEPRECATED = /@deprecated/;

const FORMATS = [
	'@deprecated As of <VERSION>',
	'@deprecated As of <VERSION>, replaced by .+',
	'@deprecated As of <VERSION>, with no direct replacement',
];

const VERSIONS = new Map([
	['6.0', 'Bunyan (6.0.x)'],
	['6.1', 'Paton (6.1.x)'],
	['6.2', 'Newton (6.2.x)'],
	['7.0', 'Wilberforce (7.0.x)'],
	['7.1', 'Judson (7.1.x)'],
	['7.2', 'Mueller (7.2.x)'],
	['7.3', 'Athanasius (7.3.x)'],
	['7.4', 'Cavanaugh (7.4.x)'],
]);

/**
 * Escapes a pattern for use in a RegExp.
 *
 * Only escapes parentheses and periods, because those are the only special
 * characters we use in our version strings.
 */
function escape(string) {
	return string.replace(/[().]/g, '\\$&');
}

/**
 * Returns a re-formatted version of `line`, if it is possible to do so;
 * otherwise returns undefined.
 *
 * Matching is done relatively conservatively, so only simple cases are handled.
 */
function fix(line) {
	const match = line.match(
		/^(\s*)\*\s*@deprecated\s+(?:as of|from|since)?\s*(\w*)\s*\(?(\d\.\d(?:\.[0-9x])?)\)?(?:[ \t]*[.,]?[ \t]*)?(replaced by|with no (?:direct )?replacement)?(.*?)(\r?\n)?$/i
	);

	if (!match) {
		return;
	}

	const [
		,
		leadingWhitespace,
		versionName,
		versionNumber,
		replacementPrefix,
		trailer,
		newline,
	] = match;

	let expectedName;

	for (const versionPrefix of VERSIONS.keys()) {
		if (versionNumber.startsWith(versionPrefix)) {
			expectedName = VERSIONS.get(versionPrefix);

			if (
				versionName &&
				!expectedName
					.toLowerCase()
					.startsWith(versionName.toLowerCase())
			) {

				// Name doesn't match version number: bail.

				return;
			}
		}
	}

	if (!expectedName) {

		// Didn't find any known version number: bail.

		return;
	}

	let replacement = '';

	switch (replacementPrefix ? replacementPrefix.toLowerCase() : '') {
		case 'replaced by':
			if (trailer.trim()) {
				replacement = `, replaced by ${trailer.trim()}`;
			}
			else {

				// Empty trailer: bail.

				return;
			}
			break;

		case 'with no replacement':
		case 'with no direct replacement':
			if (trailer.trim()) {

				// Unexpected trailer: bail.

				return;
			}
			else {
				replacement = ', with no direct replacement';
			}
			break;

		case '':
			if (trailer.trim()) {

				// Unexpected trailer: bail.

				return;
			}
			break;

		default:
			throw new Error('Unreachable');
	}

	return (
		leadingWhitespace +
		'* @deprecated As of ' +
		expectedName +
		replacement +
		(newline || '')
	);
}

function isValid(line) {
	if (DEPRECATED.test(line)) {
		return FORMATS.some((format) => {
			return Array.from(VERSIONS.values()).some((version) => {
				const pattern = format.replace('<VERSION>', escape(version));

				const regExp = new RegExp(`^\\s+\\* ${pattern}(?:\r?\n)?$`);

				return regExp.test(line);
			});
		});
	}

	return true;
}

function splitLines(text) {
	let index = 0;

	const lines = [];

	text.replace(/\r?\n|$/g, (match, offset) => {
		const line = text.slice(index, offset) + match;

		lines.push(line);

		index = offset + match.length;
	});

	return lines;
}

module.exports = {
	create(context) {
		return {
			Program() {
				context
					.getSourceCode()
					.getAllComments()
					.forEach((comment) => {
						if (!DEPRECATED.test(comment.value)) {
							return;
						}

						if (comment.type === 'Line') {
							context.report({
								messageId: 'blockCommentsOnly',
								node: comment,
							});
						}
						else {
							let valid = true;
							let fixable = true;

							const lines = splitLines(comment.value).map(
								(line) => {
									if (isValid(line)) {
										return line;
									}
									else {
										valid = false;

										const fixed = fix(line);

										if (!fixed) {
											fixable = false;
										}

										return fixed || line;
									}
								}
							);

							if (!valid) {
								if (fixable) {

									// Replace only the _inside_ of comment,
									// which corresponds to `comment.value`.

									const range = [
										comment.range[0] + '/*'.length,
										comment.range[1] - '*/'.length,
									];

									context.report({
										fix: (fixer) => {
											return fixer.replaceTextRange(
												range,
												lines.join('')
											);
										},
										messageId: 'badFormat',
										node: comment,
									});
								}
								else {
									context.report({
										messageId: 'badFormat',
										node: comment,
									});
								}
							}
						}
					});
			},
		};
	},

	meta: {
		docs: {
			category: 'Best Practices',
			description: 'deprecations must note versions and replacements',
			recommended: false,
			url: 'https://github.com/liferay/eslint-config-liferay/issues/55',
		},
		fixable: 'code',
		messages: {
			badFormat,
			blockCommentsOnly,
		},
		schema: [],
		type: 'problem',
	},
};
