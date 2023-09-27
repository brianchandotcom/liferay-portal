/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {spawn} from 'child_process';
import {dirname} from 'path';
import * as vscode from 'vscode';

import {sourceFormatterJarPath} from './configurationProvider';

export async function getSourceFormatterOutput(
	documentFilePath: string
): Promise<string | undefined> {
	let text = '';

	try {
		const sfPath = sourceFormatterJarPath();

		await vscode.workspace.fs.stat(
			vscode.Uri.from({
				path: sfPath,
				scheme: 'file',
			})
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
			}
		);

		for await (const chunk of javaProcess.stdout) {
			text += chunk;
		}

		return text.trim();
	} catch (thrown) {
		const error = thrown as Error;

		if (error instanceof vscode.FileSystemError) {
			vscode.window.showWarningMessage(
				'Source formatter jar not found:\n' + error.message
			);
		} else {
			vscode.window.showWarningMessage(error.message);
		}
	}
}
