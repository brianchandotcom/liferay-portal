import * as vscode from 'vscode';

export function info(message: string) {
	vscode.window.showInformationMessage(message);
}
export function error(message: string) {
	vscode.window.showErrorMessage(message);
}
export function warning(message: string) {
	vscode.window.showWarningMessage(message);
}
