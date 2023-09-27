/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as vscode from 'vscode';

import {isGoToDefinitionEnabled} from '../configurationProvider';
import {RipgrepMatch, ripgrepMatches} from '../ripgrep';
import {getToken} from '../tokens';

const getFileLocations = async (glob: string): Promise<vscode.Location[]> => {
	const files = await vscode.workspace.findFiles(glob);

	if (!files.length) {
		return [];
	}

	return files.map(
		(uri) => new vscode.Location(uri, new vscode.Position(0, 0))
	);
};

const getFileMethodLocations = async (files: vscode.Uri[], search: string) => {
	const lines = await ripgrepMatches({
		paths: files.map((uri) => uri.fsPath),
		search,
	});

	return lines.map((ripgrepMatch: RipgrepMatch) => ripgrepMatch.location);
};

const getMethodLocations = async (
	glob: string,
	search: string
): Promise<vscode.Location[]> => {
	const files = await vscode.workspace.findFiles(glob);

	if (!files.length) {
		return [];
	}

	return getFileMethodLocations(files, search);
};

export class DefinitionProviderImpl implements vscode.DefinitionProvider {
	async provideDefinition(
		document: vscode.TextDocument,
		position: vscode.Position
	): Promise<vscode.Definition | undefined> {
		if (!isGoToDefinitionEnabled()) {
			return;
		}

		const line = document.lineAt(position);

		const token = getToken(line.text, position.character);

		if (!token) {
			return;
		}

		switch (token.type) {
			case 'className': {
				const [, fileName] = token.match.captures;

				const result = await Promise.all([
					getFileLocations(`**/poshi/**/${fileName}.java`),
					getFileLocations(`**/${fileName}.{function,macro}`),
				]);

				return result.flat();
			}
			case 'methodInvocation': {
				const [, fileName, methodName] = token.match.captures;

				const result = await Promise.all([
					getMethodLocations(
						`**/poshi/**/${fileName}.java`,
						`public static .* (${methodName})\\(`
					),
					getMethodLocations(
						`**/${fileName}.{function,macro}`,
						`(?:macro|function) (${methodName}) \\{`
					),
				]);

				return result.flat();
			}
			case 'pathFileName':
				return getFileLocations(`**/${token.match.captures[1]}.path`);
			case 'pathLocator': {
				const [, fileName, locatorName] = token.match.captures;

				return getMethodLocations(
					`**/${fileName}.path`,
					`<td>(${locatorName})</td>`
				);
			}
			case 'variable': {
				const [, variableName] = token.match.captures;

				const variableLocations = await getFileMethodLocations(
					[document.uri],
					`var (${variableName}) `
				);

				return variableLocations
					.filter((location) =>
						location.range.start.isBefore(position)
					)
					.pop();
			}
			case 'liferaySelenium':
				return getFileLocations(
					`**/poshi-runner/**/selenium/BaseWebDriverImpl.java`
				);
			case 'liferaySeleniumMethod':
				return getMethodLocations(
					`**/poshi-runner/**/selenium/BaseWebDriverImpl.java`,
					`public .* (${token.match.captures[2]})\\(`
				);
			default:
				break;
		}
	}
}
