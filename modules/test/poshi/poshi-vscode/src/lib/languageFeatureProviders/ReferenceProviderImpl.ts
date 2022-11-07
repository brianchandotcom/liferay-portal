/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import * as path from 'path';
import * as vscode from 'vscode';

import {ripgrepMatches} from '../ripgrep';
import {getToken} from '../tokens';

async function findUsageLocations(
	className: string,
	methodName: string,
	workspaceFolder: vscode.WorkspaceFolder
) {
	const matches = await ripgrepMatches({
		paths: [workspaceFolder?.uri.fsPath],
		// eslint-disable-next-line no-useless-escape
		search: `${className}\.${methodName}`,
	});

	return matches.map((ripgrepMatch) => ripgrepMatch.location);
}

export class ReferenceProviderImpl implements vscode.ReferenceProvider {
	async provideReferences(
		document: vscode.TextDocument,
		position: vscode.Position,
		_context: vscode.ReferenceContext,
		_token: vscode.CancellationToken
	): Promise<vscode.Location[] | undefined> {
		const line = document.lineAt(position);

		const token = getToken(line.text, position.character);

		if (!token) {
			return;
		}

		const workspaceFolder = vscode.workspace.getWorkspaceFolder(
			document.uri
		);

		if (!workspaceFolder) {
			return;
		}

		switch (token.type) {
			case 'methodDefinition': {
				const fileName = path.parse(document.fileName).name;
				const methodName = token.match.captures[1];

				return await findUsageLocations(
					fileName,
					methodName,
					workspaceFolder
				);
			}
			case 'methodInvocation': {
				const fileName = token.match.captures[1];
				const methodName = token.match.captures[2];

				return await findUsageLocations(
					fileName,
					methodName,
					workspaceFolder
				);
			}
			default:
				break;
		}
	}
}
