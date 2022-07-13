import * as path from 'path';

import * as vscode from 'vscode';

import { ripgrepMatches } from '../ripgrep';
import { getToken } from '../tokens';

async function findUsageLocations(
	className: string,
	methodName: string,
	workspaceFolder: vscode.WorkspaceFolder,
) {
	const matches = await ripgrepMatches({
		search: `${className}\.${methodName}`,
		paths: [workspaceFolder?.uri.fsPath],
	});

	return matches.map((ripgrepMatch) => ripgrepMatch.location);
}

export class ReferenceProviderImpl implements vscode.ReferenceProvider {
	async provideReferences(
		document: vscode.TextDocument,
		position: vscode.Position,
		_context: vscode.ReferenceContext,
		_token: vscode.CancellationToken,
	): Promise<vscode.Location[] | undefined> {
		const line = document.lineAt(position);

		const token = getToken(line.text, position.character);

		if (!token) {
			return;
		}

		const workspaceFolder = vscode.workspace.getWorkspaceFolder(
			document.uri,
		);

		if (!workspaceFolder) {
			return;
		}

		let fileName;
		let methodName;

		switch (token.type) {
			case 'methodDefinition':
				fileName = path.parse(document.fileName).name;
				methodName = token.matches[1];

				return await findUsageLocations(
					path.parse(document.fileName).name,
					token.matches[1],
					workspaceFolder,
				);

			case 'methodInvocation':
				return await findUsageLocations(
					token.matches[1],
					token.matches[2],
					workspaceFolder,
				);
		}
	}
}
