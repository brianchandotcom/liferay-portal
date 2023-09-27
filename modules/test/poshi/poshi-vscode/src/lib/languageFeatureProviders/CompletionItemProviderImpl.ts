/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {chain as _chain} from 'lodash';
import * as vscode from 'vscode';

import {isCompletionEnabled} from '../configurationProvider';
import {RipgrepMatch, ripgrep, ripgrepMatches} from '../ripgrep';

const classNamePattern = /\b([A-Z][A-Za-z]+)/g;
const pathNamePattern = /"([A-Z][A-Za-z]+)/g;

function isCompleteProperty(lineText: vscode.TextLine) {
	return lineText.text.trimLeft().startsWith('property');
}

function getPatternMatch(
	lineText: vscode.TextLine,
	position: vscode.Position,
	pattern: RegExp
) {
	const matches = lineText.text.matchAll(new RegExp(pattern));

	let result = null;

	for (const match of matches) {
		if (
			match.index &&
			match.index + match[0].length === position.character - 1
		) {
			result = match[1];

			break;
		}
	}

	return result;
}

const getFunctionCompletionItem = (label: string) => {
	const completionItem = new vscode.CompletionItem(
		label,
		vscode.CompletionItemKind.Function
	);

	const snippetString = new vscode.SnippetString();

	snippetString.appendText(label);
	snippetString.appendText('(');
	snippetString.appendTabstop();
	snippetString.appendText(');');

	completionItem.insertText = snippetString;

	return completionItem;
};

const getLocatorCompletionItem = (label: string) => {
	const completionItem = new vscode.CompletionItem(
		label,
		vscode.CompletionItemKind.Field
	);

	const snippetString = new vscode.SnippetString();

	snippetString.appendText(label);

	completionItem.insertText = snippetString;

	return completionItem;
};

const getPropertyCompletionItem = (label: string) => {
	const completionItem = new vscode.CompletionItem(
		label,
		vscode.CompletionItemKind.Property
	);

	const snippetString = new vscode.SnippetString();

	snippetString.appendText(label);
	snippetString.appendText(' = "');
	snippetString.appendTabstop();
	snippetString.appendText('";');

	completionItem.insertText = snippetString;

	return completionItem;
};

export class CompletionItemProviderImpl
	implements vscode.CompletionItemProvider
{
	private extensionContext: vscode.ExtensionContext;

	constructor(extensionContext: vscode.ExtensionContext) {
		this.extensionContext = extensionContext;
	}

	async provideCompletionItems(
		document: vscode.TextDocument,
		position: vscode.Position,
		_token: vscode.CancellationToken,
		context: vscode.CompletionContext
	): Promise<vscode.CompletionList | undefined> {
		if (!isCompletionEnabled()) {
			return;
		}

		const line: vscode.TextLine = document.lineAt(position);

		if (line.isEmptyOrWhitespace) {
			// Do something with an empty line

			const completionList: vscode.CompletionList =
				new vscode.CompletionList([], false);

			if (position.character === 0) {
				completionList.items.push(
					new vscode.CompletionItem(
						'definition',
						vscode.CompletionItemKind.Keyword
					)
				);
			} else if (position.character > 0) {
				return new vscode.CompletionList(
					[
						new vscode.CompletionItem(
							'property',
							vscode.CompletionItemKind.Keyword
						),
						new vscode.CompletionItem(
							'setUp',
							vscode.CompletionItemKind.Keyword
						),
					],
					false
				);
			}
		}

		if (context.triggerCharacter === '.') {
			const functionOrMacroFileBaseName = getPatternMatch(
				line,
				position,
				classNamePattern
			);

			if (functionOrMacroFileBaseName) {
				const functionOrMacroNames = await this._getItems(
					`**/${functionOrMacroFileBaseName}.{function,macro}`,
					'(?:macro|function) ([_a-zA-Z]+)'
				);

				return new vscode.CompletionList(
					functionOrMacroNames.map(getFunctionCompletionItem),
					false
				);
			}
		}

		if (context.triggerCharacter === '#') {
			const pathName = getPatternMatch(line, position, pathNamePattern);

			if (pathName) {
				const locatorNames = await this._getItems(
					`**/${pathName}.path`,
					'<td>([A-Z][A-Z_-]+)</td>'
				);

				return new vscode.CompletionList(
					locatorNames.map(getLocatorCompletionItem),
					false
				);
			}
		}

		if (context.triggerCharacter === ' ' && isCompleteProperty(line)) {
			const workspaceFolder = vscode.workspace.getWorkspaceFolder(
				document.uri
			);

			if (workspaceFolder) {
				const props = await this._getProps(workspaceFolder.uri);

				return new vscode.CompletionList(
					props.map(getPropertyCompletionItem)
				);
			}
		}
	}

	private async _getProps(workspaceUri: vscode.Uri): Promise<string[]> {
		const lines: string[] = await ripgrep({
			args: ['--multiline', '--multiline-dotall'],
			paths: [
				vscode.Uri.joinPath(workspaceUri, 'test.properties').fsPath,
			],
			search: 'test.case.available.property.names=.*?\n\n',
		});

		const results = [];
		const pattern = new RegExp(/^\s*([a-z.]+),\\/);

		for (const line of lines) {
			const match = line.match(pattern);

			if (match) {
				results.push(match[1]);
			}
		}

		return results;
	}

	private async _getItems(glob: string, search: string): Promise<string[]> {
		const uris: vscode.Uri[] = await vscode.workspace.findFiles(glob);

		const matches: RipgrepMatch[] = await ripgrepMatches({
			paths: uris.map((uri) => uri.fsPath),
			search,
		});

		return _chain(matches)
			.map((ripgrepMatch) => ripgrepMatch.captures[0])
			.compact()
			.sort()
			.sortedUniq()
			.value();
	}
}
