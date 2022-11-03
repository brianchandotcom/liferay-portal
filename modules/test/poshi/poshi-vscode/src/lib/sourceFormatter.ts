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
