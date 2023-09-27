/* eslint-disable no-console */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as vscode from 'vscode';

import {runTestCaseInFile, runTestCaseUnderCursor} from './lib/commands';
import {CompletionItemProviderImpl} from './lib/languageFeatureProviders/CompletionItemProviderImpl';
import {DefinitionProviderImpl} from './lib/languageFeatureProviders/DefinitionProviderImpl';
import {DocumentFormattingEditProviderImpl} from './lib/languageFeatureProviders/DocumentFormattingEditProviderImpl';
import {ReferenceProviderImpl} from './lib/languageFeatureProviders/ReferenceProviderImpl';

export function activate(context: vscode.ExtensionContext) {
	console.log('Registering language feature providers...');

	context.subscriptions.push(
		vscode.languages.registerCompletionItemProvider(
			{pattern: '**/*.{function,macro,testcase}'},
			new CompletionItemProviderImpl(context),
			'.',
			'#',
			' '
		)
	);
	context.subscriptions.push(
		vscode.languages.registerDefinitionProvider(
			{
				pattern: '**/*.{function,macro,testcase}',
			},
			new DefinitionProviderImpl()
		)
	);
	context.subscriptions.push(
		vscode.languages.registerReferenceProvider(
			{pattern: '**/*.{function,macro,path,testcase}'},
			new ReferenceProviderImpl()
		)
	);
	context.subscriptions.push(
		vscode.languages.registerDocumentFormattingEditProvider(
			{
				pattern: '**/*.{function,macro,testcase}',
			},
			new DocumentFormattingEditProviderImpl()
		)
	);

	context.subscriptions.push(
		vscode.commands.registerTextEditorCommand(
			'poshi.run.test.case.in.file',
			async (textEditor: vscode.TextEditor) =>
				runTestCaseInFile(textEditor)
		)
	);
	context.subscriptions.push(
		vscode.commands.registerTextEditorCommand(
			'poshi.run.test.case.under.cursor',
			async (textEditor: vscode.TextEditor) =>
				runTestCaseUnderCursor(textEditor)
		)
	);

	console.log(
		'Congratulations, your extension "poshi-language-support" is now active!'
	);
}

export function deactivate() {}
