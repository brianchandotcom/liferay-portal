/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Lexer from './Lexer.mjs';
import dedent from './dedent.mjs';
import {IDENTIFIER} from './getPaddedReplacement.mjs';
import indent from './indent.mjs';
import {SCRIPTLET_CONTENT} from './substituteTags.mjs';
import {CLOSE_TAG, OPEN_TAG} from './tagReplacements.mjs';
import {TAB_CHAR, isFiller} from './toFiller.mjs';

/**
 * Takes a source string and reinserts tags that were previously extracted with
 * substituteTags().
 */
function restoreTags(source, tags) {
	let tagCount = 0;

	const lexer = new Lexer((api) => {
		const {choose, match} = api;

		const ANYTHING = match(/[\s\S]/);
		const COMMENT = match(/\/\*.*?\*\//);
		const SELF_CLOSING_TAG_REPLACEMENT = match(isFiller());
		const CLOSE_TAG_REPLACEMENT = match(CLOSE_TAG);
		const IDENTIFIER_REPLACEMENT = match(IDENTIFIER);
		const OPEN_TAG_REPLACEMENT = match(OPEN_TAG);
		const SCRIPTLET = match(isFiller(SCRIPTLET_CONTENT));
		const NEWLINE = match(/\r?\n/);
		const WHITESPACE = match(/[ \t]+/);

		return choose({

			/* eslint-disable sort-keys */
			SCRIPTLET,
			SELF_CLOSING_TAG_REPLACEMENT,
			OPEN_TAG_REPLACEMENT,
			CLOSE_TAG_REPLACEMENT,
			COMMENT,
			IDENTIFIER_REPLACEMENT,
			NEWLINE,
			WHITESPACE,
			ANYTHING,

			/* eslint-enable sort-keys */
		});
	});

	let output = '';
	let indent = '';
	let indentLevel = 0;

	let tokens = [...lexer.lex(source)];

	// First pass: restore internal indents related to JSP entities
	// (ie. undo the effects of `stripIndents()`.

	for (let i = 0; i < tokens.length; i++) {
		const token = tokens[i];
		const {contents, name} = token;

		switch (name) {
			case 'OPEN_TAG_REPLACEMENT':
				output += indent + contents;
				indent = '';
				indentLevel++;
				tagCount++;
				break;

			case 'CLOSE_TAG_REPLACEMENT':
				output = removeIndent(output, token);
				output += indent.slice(1) + contents;
				indent = '';
				indentLevel--;
				tagCount++;
				break;

			case 'SCRIPTLET':
				{
					const delta = getImpliedIndentFromScriptlet(tags[tagCount]);

					if (delta < 0) {
						output = removeIndent(output, token, -delta);
						output += indent.slice(-delta) + contents;
					}
					else {
						output += indent + contents;
					}

					indentLevel += delta;
					indent = '';
					tagCount++;
				}
				break;

			case 'IDENTIFIER_REPLACEMENT':
			case 'SELF_CLOSING_TAG_REPLACEMENT':
				output += indent + contents;
				indent = '';
				tagCount++;
				break;

			case 'ANYTHING':
			case 'COMMENT':
			case 'WHITESPACE':
				output += indent + contents;
				indent = '';
				break;

			case 'NEWLINE':
				output += contents;
				indent = '\t'.repeat(indentLevel);
				break;

			default:
				throw new Error(`Unexpected token type: ${name}`);
		}
	}

	// Second pass: re-insert JSP tags into source.

	tagCount = 0;
	tokens = [...lexer.lex(output)];
	output = '';

	for (let i = 0; i < tokens.length; i++) {
		const token = tokens[i];
		const {contents, name} = token;

		switch (name) {
			case 'SCRIPTLET':
				output = appendScriptlet(tags[tagCount++], token, output);
				break;

			case 'OPEN_TAG_REPLACEMENT':
			case 'CLOSE_TAG_REPLACEMENT':
			case 'IDENTIFIER_REPLACEMENT':
			case 'SELF_CLOSING_TAG_REPLACEMENT':
				output += getIndentedTag(tags[tagCount++], token);
				break;

			case 'ANYTHING':
			case 'COMMENT':
			case 'NEWLINE':
			case 'WHITESPACE':
				output += contents;
				break;

			default:
				throw new Error(`Unexpected token type: ${name}`);
		}
	}

	if (tagCount !== tags.length) {
		throw new Error(
			`Expected replacement count ${tagCount}, but got ${tags.length}`
		);
	}

	return output;
}

/**
 * Special handling for scriptlets, which are expected to be preceded and trailed
 * by a single blank line each.
 */
function appendScriptlet(scriptlet, token, output) {
	let prefix = '';
	let suffix = '';

	// Look up neighboring tokens.

	const tokens = {

		/* eslint-disable sort-keys */
		'-3':
			token.previous &&
			token.previous.previous &&
			token.previous.previous.previous,
		'-2': token.previous && token.previous.previous,
		'-1': token.previous,
		'0': token,
		'1': token.next,
		'2': token.next && token.next.next,

		/* eslint-enable sort-keys */
	};

	if (!tokens[-1]) {

		// SCRIPTLET is the very first thing in the script block.

		prefix = '\n';
	}

	if (
		tokens[-1] &&
		tokens[-1].name === 'NEWLINE' &&
		tokens[-2] &&
		tokens[-2].name !== 'NEWLINE'
	) {

		// Need to add another newline to force a blank line.

		prefix = '\n';
	}

	if (
		tokens[-1] &&
		tokens[-1].name === 'WHITESPACE' &&
		tokens[-2] &&
		tokens[-2].name === 'NEWLINE' &&
		tokens[-3] &&
		tokens[-3].name !== 'NEWLINE'
	) {

		// Trim off WHITESPACE.
		//
		// Note: cannot use `tokens[-1].index` here because it may have been
		// invalidated by previous edits.

		const whitespace = tokens[-1].contents;
		output = output.slice(0, -whitespace.length);

		// Add newline to force blank line, then re-add trimmed whitespace.

		prefix = '\n' + whitespace;
	}

	if (
		tokens[1] &&
		tokens[1].name === 'NEWLINE' &&
		tokens[2] &&
		tokens[2].name !== 'NEWLINE'
	) {
		suffix = '\n';
	}

	if (tokens[1] && tokens[1].name === 'NEWLINE' && !tokens[2]) {

		// Scriptlet is (basically) the last thing in the script block.

		suffix = '\n';
	}

	return output + prefix + getIndentedTag(scriptlet, token) + suffix;
}

/**
 * Based on preceding indent, adjust the internal indent of a tag before
 * substituting it.
 *
 * For example, this source:
 *
 *      \t\t\t\t\t\t<%
 *      \t\t\t\t\t\t// Contents
 *      \t\t\t\t\t\t%>
 *
 * May be changed by Prettier to:
 *
 *      \t\t<%
 *      \t\t\t\t\t\t// Contents
 *      \t\t\t\t\t\t%>
 *
 * So this function must correct that to:
 *
 *      \t\t<%
 *      \t\t// Contents
 *      \t\t%>
 *
 * Necessary because Prettier will often change the first line of multi-line
 * placeholder comment, but it never changes the subsequent lines.
 */
function getIndentedTag(tag, token) {
	if (!token.previous) {
		return tag;
	}

	let previous;

	const {contents, name} = token.previous;

	if (name === 'WHITESPACE') {
		previous = contents;
	}
	else if (name === 'NEWLINE') {
		previous = '';
	}
	else {
		return tag;
	}

	// This is incredibly hacky, so strap yourself in:
	//
	// We know the size of the indent (the "previous" token)
	// after formatting, but we don't know the size it was before
	// formatting. Let's call that unknown size "X": what we want to
	// calculate is "X - previous", because that is the amount that
	// Prettier changed the indent, and which we'll need to change
	// the other lines by as well.
	//
	// We don't know "X" but we can make a best guess based on the
	// following assumptions (which we can expect to be valid only
	// because the Java Source Formatter enforces them):
	//
	// - "X" will always be a sequence of tabs.
	// - The indent of the last line (call that "Y") should be the same as
	//   "X".
	//
	// Seeing as we can figure out "Y", we can use that as our guess for
	// "X".

	const lines = token.contents.split(/\r?\n/g);

	if (lines.length > 1) {

		// Look at last line to figure out original indent.

		const last = lines.pop();
		const {0: original} = last.match(new RegExp(`^${TAB_CHAR}*`));

		// Restore original indent to first line, then dedent the whole tag.

		const dedented = dedent('\t'.repeat(original.length) + tag);

		// And indent it to the correct level, but trim off the indent on
		// the first line because we already emitted that.

		return indent(dedented, 1, previous).replace(previous, '');
	}

	return tag;
}

/**
 * Scan tag contents for braces that would imply an increased or decreased
 * indent level.
 */
function getImpliedIndentFromScriptlet(tag) {
	let delta = 0;

	// Keeping this simple for now; can always extend to lex more rigorously if
	// we find that it's needed.

	tag.replace(/[{}]/g, ([brace]) => {
		if (brace === '}') {
			delta--;
		}
		else {
			delta++;
		}
	});

	return delta;
}

function removeIndent(output, token, count = 1) {
	if (
		token.previous &&
		token.previous.name === 'WHITESPACE' &&
		token.previous.previous &&
		token.previous.previous.name === 'NEWLINE'
	) {

		// We already emitted too much indent; roll it back.

		return output.replace(
			new RegExp(`\t{0,${count}}${token.previous.contents}$`),
			token.previous.contents
		);
	}

	return output;
}

export default restoreTags;
