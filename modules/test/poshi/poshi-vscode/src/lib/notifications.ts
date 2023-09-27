/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as vscode from 'vscode';

export function info(message: string) {
	vscode.window.showInformationMessage(namespace(message));
}
export function error(message: string) {
	vscode.window.showErrorMessage(namespace(message));
}
export function warning(message: string) {
	vscode.window.showWarningMessage(namespace(message));
}

function namespace(message: string) {
	return `Poshi: ${message}`;
}
