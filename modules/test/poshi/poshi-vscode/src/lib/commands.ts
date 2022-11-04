/* eslint-disable no-console */
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

import * as vscode from 'vscode';

export async function runTestCaseFromTextEditor(textEditor: vscode.TextEditor) {
	const workspaceFolder = vscode.workspace.getWorkspaceFolder(
		textEditor.document.uri
	);

	if (!workspaceFolder) {
		return;
	}

	const filePath = textEditor.document.fileName;

	if (!filePath.endsWith('.testcase')) {
		return;
	}

	const fileName = filePath.substring(
		filePath.lastIndexOf('/') + 1,
		filePath.lastIndexOf('.testcase')
	);

	let lineNumber = textEditor.selection.start.line;

	const regex = new RegExp(/^\W*test ([A-Z][A-Za-z]+)/g);

	const getName = (n: number) => {
		const line = textEditor.document.lineAt(n);

		for (const match of line.text.matchAll(regex)) {
			const {index} = match;

			if (index === undefined) {
				continue;
			}

			if (index === -1) {
				continue;
			}

			return match[1];
		}

		return null;
	};

	let testName;

	while (lineNumber >= 0) {
		const name = getName(lineNumber);

		if (name) {
			testName = name;
			break;
		}

		lineNumber--;
	}

	if (testName) {
		const testCase = `${fileName}#${testName}`;

		const command = await getCommand(workspaceFolder, testCase);

		if (command) {
			const terminal = vscode.window.createTerminal(`Run: ${testCase}`);

			terminal.show();

			terminal.sendText(command);
		}
	}
}

async function getCommand(
	workspaceFolder: vscode.WorkspaceFolder,
	testCase: string
): Promise<string | void> {
	try {
		const buildFileUri = vscode.Uri.joinPath(
			workspaceFolder.uri,
			'build-test.xml'
		);

		await vscode.workspace.fs.stat(buildFileUri);

		return `ant -f "${buildFileUri.fsPath}" run-selenium-test -Dtest.class="${testCase}"`;
	} catch (error) {
		console.log('Not in liferay-portal.');
	}

	try {
		const gradleExecutableUri = vscode.Uri.joinPath(
			workspaceFolder.uri,
			'gradlew'
		);

		await vscode.workspace.fs.stat(gradleExecutableUri);

		return `./gradlew runPoshi -Dtest.name="${testCase}"`;
	} catch (error) {
		console.log('Not in a gradle project.');
	}
}
