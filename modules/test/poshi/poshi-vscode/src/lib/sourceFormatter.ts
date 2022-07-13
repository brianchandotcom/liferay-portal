import { spawn } from 'child_process';
import { dirname } from 'path';

import * as vscode from 'vscode';

import { sourceFormatterJarPath } from './configurationProvider';

export const getSourceFormatterOutput = async (
	documentFilePath: string,
): Promise<string | undefined> => {
	let text = '';

	try {
		const sfPath = sourceFormatterJarPath();

		await vscode.workspace.fs.stat(
			vscode.Uri.from({ scheme: 'file', path: sfPath }),
		);

		const javaProcess = spawn(
			'java',
			[
				// '-Xdebug',
				// '-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005',
				'-jar',
				sfPath,
				'-Dsource.auto.fix=false',
				'-Dcommit.count=0',
				'-Dshow.debug.information=true',
				`-Dsource.files=${documentFilePath}`,
			],
			{
				cwd: dirname(sfPath),
			},
		);

		for await (const chunk of javaProcess.stdout) {
			text += chunk;
		}

		return text.trim();
	} catch (error) {
		const err = error as Error;

		if (error instanceof vscode.FileSystemError) {
			vscode.window.showWarningMessage(
				'Source formatter jar not found:\n' + error.message,
			);
		} else {
			vscode.window.showWarningMessage(err.message);
		}
	}
};
