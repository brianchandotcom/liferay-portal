import * as vscode from 'vscode';

let configuration = vscode.workspace.getConfiguration('poshi');

vscode.workspace.onDidChangeConfiguration((event: vscode.ConfigurationChangeEvent) => {
    if (event.affectsConfiguration('poshi')) {
        configuration = vscode.workspace.getConfiguration('poshi');
    }
});

export const isCompletionEnabled = (): boolean =>
	configuration.get('completion.enabled', false);
export const isSourceFormatterEnabled = (): boolean =>
	configuration.get('sourceFormatter.enabled', false);
export const isGoToDefinitionEnabled = (): boolean =>
	configuration.get('goToDefinition.enabled', true);
export const sourceFormatterJarPath = (): string =>
	configuration.get('sourceFormatter.jarPath', '');
