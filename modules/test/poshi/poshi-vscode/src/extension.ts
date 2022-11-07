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
