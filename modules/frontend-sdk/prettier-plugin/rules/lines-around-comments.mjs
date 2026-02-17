/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

export function linesAroundComments(formattedText, ast, parserName, options) {
	const {
		commentIgnorePatterns = [],
		commentIgnoreAfterPatterns = [],
		commentIgnoreBeforePatterns = [],
	} = options;

	const totalLines = ast.loc.end.line;

	let hasDirective = false;

	/*
	 * Track where each inline comment is so that we can group them
	 */
	const linesWithInlineComment = ast.comments.reduce(
		(inlineComments, commentNode) => {
			if (
				isInlineComment(commentNode) &&
				!isEndofLineComment(commentNode, formattedText)
			) {
				inlineComments.push(commentNode.loc.start.line);
			}

			if (isDirective(commentNode, formattedText, parserName)) {
				hasDirective = true;
			}

			return inlineComments;
		},
		[]
	);

	let modifiedText = formattedText;

	const ignorePatterns = [
		...[...commentIgnorePatterns, '/ <reference'].map((item) => {
			const regex = new RegExp(item);

			return {
				afterPattern: regex,
				beforePattern: regex,
			};
		}),
		...[
			...commentIgnoreAfterPatterns,
			'eslint-disable',
			'prettier-ignore',
			'@ts-ignore',
			'webpackIgnore: true',
		].map((item) => ({
			afterPattern: new RegExp(item),
		})),
		...commentIgnoreBeforePatterns.map((item) => ({
			beforePattern: new RegExp(item),
		})),
	];

	/*
	 * We are reading these comments from a "bottom to top" approach.
	 */
	for (let i = ast.comments.length - 1; i >= 0; i--) {
		const commentNode = ast.comments[i];

		let skipAfter = false;
		let skipBefore = false;

		/*
		 * Ignore new line above comment node value matches option
		 */
		ignorePatterns.forEach(({afterPattern, beforePattern}) => {
			if (afterPattern?.exec(commentNode.value)) {
				skipAfter = true;
			}

			if (beforePattern?.exec(commentNode.value)) {
				skipBefore = true;
			}
		});

		if (skipAfter && skipBefore) {
			continue;
		}

		/*
		 * Ignore comments that are at the end of a line
		 */
		if (isEndofLineComment(commentNode, modifiedText)) {
			continue;
		}

		/*
		 * Skip directives
		 */
		if (isDirective(commentNode, modifiedText, parserName)) {
			continue;
		}

		const startingLine = commentNode.loc.start.line;
		const endingLine = commentNode.loc.end.line;

		/*
		 * Don't add a line after if the comment is at the end of the file
		 */
		if (endingLine === totalLines - 1) {
			skipAfter = true;
		}

		/*
		 * Don't add a line before if its the first line in the file
		 * or
		 * Don't add a line before if the line above is a directive
		 */
		if (startingLine === 1 || (hasDirective && startingLine === 2)) {
			skipBefore = true;
		}

		/*
		 * This allows to group inline comments next to one another
		 */
		if (isInlineComment(commentNode)) {
			if (linesWithInlineComment.includes(startingLine - 1)) {
				skipBefore = true;
			}

			if (linesWithInlineComment.includes(endingLine + 1)) {
				skipAfter = true;
			}
		}

		/*
		 * Since we are traversing from "bottom to top" of the file, we need
		 * want to check the 'after' line first.
		 */
		if (
			!skipAfter &&
			!isBlockComment(commentNode) &&
			!isNewLineAfter(commentNode, modifiedText)
		) {
			modifiedText = insertNewLineAfter(commentNode, modifiedText);
		}

		if (!skipBefore && !isNewLineBefore(commentNode, modifiedText)) {
			modifiedText = insertNewLineBefore(commentNode, modifiedText);
		}
	}

	return modifiedText;
}

function isDirective(node, text, parserName) {
	if (parserName === 'typescript') {
		return (
			node.range[0] === 0 &&
			text.substring(...node.range).startsWith('#!')
		);
	}

	return node.type === 'InterpreterDirective';
}

function isBlockComment(node) {
	return node.type === 'CommentBlock' || node.type === 'Block';
}

function isInlineComment(node) {
	return node.type === 'CommentLine' || node.type === 'Line';
}

/*
 * Returns The contents on the same line before the comment starts
 */
function getContentsBeforeColumn(node, source) {
	const {column} = node.loc.start;

	const index = node.start ?? node.range[0];

	return source.slice(index - column, index - 1);
}

/*
 * A comment like `var test = 'foo'; // this is my variable`
 */
function isEndofLineComment(node, source) {
	return (
		node.loc.start.column !== 0 &&
		/\S/.test(getContentsBeforeColumn(node, source))
	);
}

function isNewLineBefore(commentNode, content) {
	const startingIndex = (commentNode.start ?? commentNode.range[0]) - 1;
	const indentSize = commentNode.loc.start.column;

	return (
		content.charAt(startingIndex - indentSize) === '\n' &&
		content.charAt(startingIndex - indentSize - 1) === '\n'
	);
}

function isNewLineAfter(commentNode, content) {
	const endindex = commentNode.end ?? commentNode.range[1];

	return (
		content.charAt(endindex) === '\n' &&
		content.charAt(endindex + 1) === '\n'
	);
}

function insertNewLineAfter(commentNode, content) {
	const endingIndex = commentNode.end ?? commentNode.range[1];

	const insertIndex = endingIndex + 1;

	return content.slice(0, insertIndex) + '\n' + content.slice(insertIndex);
}

function insertNewLineBefore(commentNode, content) {
	const startingIndex = commentNode.start ?? commentNode.range[0];

	const indentSize = commentNode.loc.start.column;

	const insertIndex = startingIndex - indentSize;

	return content.slice(0, insertIndex) + '\n' + content.slice(insertIndex);
}
