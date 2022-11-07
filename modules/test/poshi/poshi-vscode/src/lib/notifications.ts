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
