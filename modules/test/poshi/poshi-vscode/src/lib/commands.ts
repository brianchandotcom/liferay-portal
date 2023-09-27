/* eslint-disable no-console */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as child_process from 'node:child_process';
import * as fs from 'node:fs';
import * as fsPromises from 'node:fs/promises';
import * as path from 'node:path';
import * as vscode from 'vscode';

import * as notifications from './notifications';
import {getDocumentTokens, getTokens} from './tokens';

export async function runTestCaseInFile(textEditor: vscode.TextEditor) {
	const document = textEditor.document;

	const tokens = getDocumentTokens(document);

	const testCaseNames = tokens
		.filter((token) => token.type === 'testCaseName')
		.map((token) => token.match.captures[1]);

	const testName = await vscode.window.showQuickPick(testCaseNames);
	if (!testName) {
		return;
	}

	runTestCase(document, testName);
}

export async function runTestCaseUnderCursor(textEditor: vscode.TextEditor) {
	const document = textEditor.document;

	const getTestName = (n: number) => {
		const line = document.lineAt(n);
		for (const token of getTokens(line.text)) {
			if (token.type === 'testCaseName') {
				return token.match.captures[1];
			}
		}

		return null;
	};

	let testName;

	for (
		let lineNumber = textEditor.selection.start.line;
		lineNumber >= 0;
		lineNumber--
	) {
		const name = getTestName(lineNumber);

		if (name) {
			testName = name;
			break;
		}
	}

	if (!testName) {
		notifications.warning('No test case found under the cursor.');

		return;
	}

	runTestCase(document, testName);
}

function buildAntCommand(antBuildFilePath: string, testCase: string) {
	return `ant -f "${antBuildFilePath}" run-selenium-test -Dtest.class="${testCase}"`;
}
function buildGradleCommand(gradleExecutablePath: string, testCase: string) {
	return `${gradleExecutablePath} runPoshi -Dtest.name="${testCase}"`;
}

interface Command {
	command: string;
	cwd: string;
}

async function getCommand(
	document: vscode.TextDocument,
	testCase: string
): Promise<Command | void> {
	const workspaceFolder = vscode.workspace.getWorkspaceFolder(document.uri);

	if (!workspaceFolder) {
		return;
	}

	try {
		const buildFileUri = vscode.Uri.joinPath(
			workspaceFolder.uri,
			'build-test.xml'
		);

		await vscode.workspace.fs.stat(buildFileUri);

		console.log(
			`Found ant build file in workspace: ${buildFileUri.fsPath}`
		);

		return {
			command: buildAntCommand(buildFileUri.fsPath, testCase),
			cwd: workspaceFolder.uri.fsPath,
		};
	} catch (error) {
		console.log('No ant build file found in workspace.');
	}

	try {
		const gradleExecutableUri = vscode.Uri.joinPath(
			workspaceFolder.uri,
			'gradlew'
		);

		await vscode.workspace.fs.stat(gradleExecutableUri);

		console.log(
			`Found gradle build file in workspace: ${gradleExecutableUri.fsPath}`
		);

		return {
			command: buildGradleCommand(gradleExecutableUri.fsPath, testCase),
			cwd: getNearestGradleBuildFileDir(
				document,
				gradleExecutableUri.fsPath
			),
		};
	} catch (error) {
		console.log('No gradle build file found in workspace.');
	}

	const gitRevParse: child_process.SpawnSyncReturns<string> =
		child_process.spawnSync('git', ['rev-parse', '--show-toplevel'], {
			cwd: workspaceFolder.uri.fsPath,
			encoding: 'utf8',
		});

	const gitTopLevelDirectory = gitRevParse.stdout.trim();

	try {
		const buildFilePath = path.join(gitTopLevelDirectory, 'build-test.xml');

		await fsPromises.stat(buildFilePath);

		console.log(
			`Found ant build file at the git top-level directory: ${buildFilePath}`
		);

		return {
			command: buildAntCommand(buildFilePath, testCase),
			cwd: workspaceFolder.uri.fsPath,
		};
	} catch (error) {
		console.log('No ant build file found at the Git top-level directory.');
	}

	try {
		const gradleExecutablePath = path.join(gitTopLevelDirectory, 'gradlew');

		await fsPromises.stat(gradleExecutablePath);

		console.log(
			`Found gradle executable at the git top-level directory: ${gradleExecutablePath}`
		);

		return {
			command: buildGradleCommand(gradleExecutablePath, testCase),
			cwd: getNearestGradleBuildFileDir(document, gradleExecutablePath),
		};
	} catch (error) {
		console.log(
			'No gradle build file found at the Git top-level directory.'
		);
	}
}

function getNearestGradleBuildFileDir(
	document: vscode.TextDocument,
	gradleExecutablePath: string
): string {
	const workspaceFolder = vscode.workspace.getWorkspaceFolder(
		document.uri
	) as vscode.WorkspaceFolder;

	const gradleExecutableDir = path.dirname(gradleExecutablePath);

	for (
		let dir = path.dirname(document.uri.fsPath);
		dir !== gradleExecutableDir;
		dir = path.resolve(dir, '..')
	) {
		try {
			const files = fs.readdirSync(dir);

			if (files.includes('build.gradle')) {
				return dir;
			}
		} catch (error) {
			break;
		}
	}

	return workspaceFolder.uri.fsPath;
}

async function runTestCase(document: vscode.TextDocument, testName: string) {
	const parsed = path.parse(document.fileName);

	const testCase = `${parsed.name}#${testName}`;

	const command = await getCommand(document, testCase);
	if (!command) {
		notifications.warning(
			`Unable to run the test case: ${testCase}. No Ant or Gradle task runner was found.`
		);

		return;
	}

	notifications.info(`Running testcase: ${testCase}`);

	const options: vscode.TerminalOptions = {
		cwd: command.cwd,
		message: `Running Poshi testcase: ${testCase}`,
		name: `Poshi: Run ${testCase}`,
	};

	const terminal = vscode.window.createTerminal(options);

	terminal.show();

	terminal.sendText(command.command);
}
