/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as vscode from 'vscode';

import {isSourceFormatterEnabled} from '../configurationProvider';
import {getSourceFormatterOutput} from '../sourceFormatter';

export class DocumentFormattingEditProviderImpl
	implements vscode.DocumentFormattingEditProvider
{
	async provideDocumentFormattingEdits(
		document: vscode.TextDocument,
		_options: vscode.FormattingOptions,
		_token: vscode.CancellationToken
	): Promise<vscode.TextEdit[] | undefined> {
		if (!isSourceFormatterEnabled()) {
			return;
		}

		const sfOutput = await getSourceFormatterOutput(document.fileName);

		if (!sfOutput) {
			return;
		}

		const textEdits = [];

		// line 44 was deleted

		const lineRemovedRegex = new RegExp(/.* line (\d+) was deleted/g);
		for (const match of sfOutput.matchAll(lineRemovedRegex)) {
			const deletedLine = Number(match[1]);

			textEdits.push(
				vscode.TextEdit.delete(
					new vscode.Range(
						new vscode.Position(deletedLine - 1, 0),
						new vscode.Position(deletedLine, 0)
					)
				)
			);
		}

		// lines 39-40 were deleted

		const linesRemovedRegex = new RegExp(
			/.* lines (\d+)-(\d+) were deleted/g
		);
		for (const match of sfOutput.matchAll(linesRemovedRegex)) {
			const firstDeletedLine = Number(match[1]);
			const lastDeletedLine = Number(match[2]);

			textEdits.push(
				vscode.TextEdit.delete(
					new vscode.Range(
						new vscode.Position(firstDeletedLine - 1, 0),
						new vscode.Position(lastDeletedLine, 0)
					)
				)
			);
		}

		// line 38 was added

		const lineAddedRegex = new RegExp(/.* line (\d+) was added/g);
		for (const match of sfOutput.matchAll(lineAddedRegex)) {
			const addedLine = Number(match[1]);

			textEdits.push(
				vscode.TextEdit.insert(
					new vscode.Position(addedLine - 1, 0),
					'\n'
				)
			);
		}

		// lines 26-30 were added

		const linesAddedRegex = new RegExp(/.* lines (\d+)-(\d+) were added/g);
		for (const match of sfOutput.matchAll(linesAddedRegex)) {
			const firstAddedLine = Number(match[1]);
			const lastAddedLine = Number(match[2]);

			const totalLinesAdded = lastAddedLine - firstAddedLine;

			textEdits.push(
				vscode.TextEdit.insert(
					new vscode.Position(firstAddedLine - 1, 0),
					'\n'.repeat(totalLinesAdded)
				)
			);
		}

		const lineChangedRegex = new RegExp(
			/^.* line (\d+) changed:\nbefore:((?:\n^\[.*\]$)+)\nafter:((?:\n^\[.*\]$)+)/gm
		);
		for (const match of sfOutput.matchAll(lineChangedRegex)) {
			const changedLine = Number(match[1]);
			const oldText = match[2].trim().replace(/[[\]]/g, '');
			const newText = match[3].trim().replace(/[[\]]/g, '');

			const numberOfChangedLines = oldText.split('\n').length;

			textEdits.push(
				vscode.TextEdit.replace(
					new vscode.Range(
						new vscode.Position(changedLine - 1, 0),
						new vscode.Position(
							changedLine - 1 + numberOfChangedLines,
							0
						)
					),
					`${newText}\n`
				)
			);
		}

		// Sort in descending order so that later edits are applied first and line numbers stay relevant

		textEdits.sort((a, b) => {
			if (a.range.start.isBefore(b.range.start)) {
				return 1;
			} else if (a.range.start.isAfter(b.range.start)) {
				return -1;
			} else {
				return 0;
			}
		});

		return textEdits;
	}
}
