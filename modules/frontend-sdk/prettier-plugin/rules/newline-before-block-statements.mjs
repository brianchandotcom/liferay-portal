/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

import visitNode from '../utils/visitNode.mjs';

export function newlineBeforeBlockStatement(formattedText, ast) {
	let modifiedText = formattedText;

	const linesAdded = {};

	visitNode(ast, (node) => {
		if (node.type === 'IfStatement') {
			/*
			 *                } else if {
			 *     consequent ^         ^ alternate
			 *
			 *                } else {
			 *     consequent ^      ^ alternate
			 */

			const {alternate, consequent} = node;

			if (alternate) {
				const alternateStartingLine = alternate.loc.start.line;
				const consequentEndingLine = consequent.loc.end.line;

				if (alternateStartingLine === consequentEndingLine) {
					modifiedText = insertNewLineAfter(
						consequent,
						modifiedText,
						linesAdded
					);
				}
			}
		}

		if (node.type === 'TryStatement') {
			/*
			 *                } catch (error) {
			 *         block--^ ^--handler
			 *
			 * or:
			 *
			 *                } catch {
			 *         block--^ ^--handler
			 *
			 * or:
			 *
			 *                      } finally {
			 *     block-or-handler ^         ^ finalizer
			 */
			const {block, finalizer, handler} = node;

			/*
			 * We want to make sure and replace the finalizer before the
			 * handler since we are working in a "bottom-up" traversal.
			 */
			if (finalizer) {
				const nodeAbove = handler || block;
				const nodeAboveEndingLine = nodeAbove.loc.end.line;
				const finalizerStartLine = finalizer.loc.start.line;

				if (nodeAboveEndingLine === finalizerStartLine) {
					modifiedText = insertNewLineAfter(
						nodeAbove,
						modifiedText,
						linesAdded
					);
				}
			}

			if (handler) {
				const blockEndingLine = block.loc.end.line;
				const handlerStartLine = handler.loc.start.line;

				if (blockEndingLine === handlerStartLine) {
					modifiedText = insertNewLineAfter(
						block,
						modifiedText,
						linesAdded
					);
				}
			}
		}
	});

	return modifiedText;
}

function insertNewLineAfter(nodeAbove, content, linesAdded) {
	const aboveEndingIndex = nodeAbove.range[1];
	const endingLine = nodeAbove.loc.end.line;

	/*
	 * Determine the index offset from the number of characters added above.
	 */
	const offset = Object.keys(linesAdded).reduce((acc, lineNumber) => {
		if (lineNumber < endingLine) {
			acc += linesAdded[lineNumber];
		}

		return acc;
	}, 0);

	const indentSize = '\t'.repeat(nodeAbove.loc.end.column - 1);

	/*
	 * Keep track of the number of characters added at each line.
	 */
	linesAdded[endingLine] = indentSize.length;

	/*
	 * We know we are working with prettier's standard output, which means
	 * we can assume the literal space(' ') between the closing bracket and
	 * the keyword. '} else'
	 *                ^--- this space
	 */
	const spaceOffset = 1;

	const index = aboveEndingIndex + offset;

	return (
		content.slice(0, index) +
		'\n' +
		indentSize +
		content.slice(index + spaceOffset)
	);
}
