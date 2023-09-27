/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as vscode from 'vscode';

let configuration = vscode.workspace.getConfiguration('poshi');

vscode.workspace.onDidChangeConfiguration(
	(event: vscode.ConfigurationChangeEvent) => {
		if (event.affectsConfiguration('poshi')) {
			configuration = vscode.workspace.getConfiguration('poshi');
		}
	}
);

export function isCompletionEnabled(): boolean {
	return configuration.get('completion.enabled', false);
}
export function isSourceFormatterEnabled(): boolean {
	return configuration.get('sourceFormatter.enabled', false);
}
export function isGoToDefinitionEnabled(): boolean {
	return configuration.get('goToDefinition.enabled', true);
}
export function sourceFormatterJarPath(): string {
	return configuration.get('sourceFormatter.jarPath', '');
}
