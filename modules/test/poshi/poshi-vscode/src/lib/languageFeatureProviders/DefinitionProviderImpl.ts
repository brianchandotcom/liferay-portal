import * as vscode from 'vscode';

import { RipgrepMatch, ripgrepMatches } from '../ripgrep';
import { isGoToDefinitionEnabled } from '../configurationProvider';
import { getToken } from '../tokens';

const getFileLocations = async (
	glob: string,
): Promise<vscode.Location[] | undefined> => {
	const files = await vscode.workspace.findFiles(glob);

	if (files.length === 0) {
		return;
	}

	return files.map(
		(uri) => new vscode.Location(uri, new vscode.Position(0, 0)),
	);
};

const getFileMethodLocations = async (files: vscode.Uri[], search: string) => {
	const lines = await ripgrepMatches({
		search,
		paths: files.map((uri) => uri.fsPath),
	});

	return lines.map((ripgrepMatch: RipgrepMatch) => ripgrepMatch.location);
};

const getMethodLocations = async (
	glob: string,
	search: string,
): Promise<vscode.Location[] | undefined> => {
	const files = await vscode.workspace.findFiles(glob);

	if (files.length === 0) {
		return;
	}

	return getFileMethodLocations(files, search);
};

export class DefinitionProviderImpl implements vscode.DefinitionProvider {
	async provideDefinition(
		document: vscode.TextDocument,
		position: vscode.Position,
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
			case 'className':
				return getFileLocations(
					`**/${token.matches[1]}.{function,macro}`,
				);
			case 'methodInvocation':
				const [, className, methodName] = token.matches;

				return getMethodLocations(
					`**/${className}.{function,macro}`,
					`(?:macro|function) (${methodName}) \\{`,
				);
			case 'pathFileName':
				return getFileLocations(`**/${token.matches[1]}.path`);
			case 'pathLocator':
				const [, fileName, locatorName] = token.matches;

				return getMethodLocations(
					`**/${fileName}.path`,
					`<td>(${locatorName})</td>`,
				);
			case 'variable':
				const [, variableName] = token.matches;

				const variableLocations = await getFileMethodLocations(
					[document.uri],
					`var (${variableName}) `,
				);

				return variableLocations
					.filter((location) =>
						location.range.start.isBefore(position),
					)
					.pop();
			case 'liferaySelenium':
				return getFileLocations(
					`**/poshi-runner/**/selenium/BaseWebDriverImpl.java`,
				);
			case 'liferaySeleniumMethod':
				return getMethodLocations(
					`**/poshi-runner/**/selenium/BaseWebDriverImpl.java`,
					`public .* (${token.matches[2]})\\(`,
				);
			case 'utilClass':
				return getFileLocations(`**/poshi/**/${token.matches[1]}.java`);
			case 'utilClassMethod':
				const [, utilFileName, utilMethodName] = token.matches;

				return getMethodLocations(
					`**/poshi/**/${utilFileName}.java`,
					`public static .* (${utilMethodName})\\(`,
				);
		}
	}
}
