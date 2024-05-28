/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import childProcess from 'child_process';

import onExit from './onExit.mjs';

export default async function forkModule(modulePath, params, options) {
	const child = childProcess.fork(modulePath, params, options);

	let killChild = true;

	onExit(() => {
		if (killChild) {
			child.kill();
		}
	});

	return new Promise((resolve, reject) => {
		child.on('exit', (code, signal) => {
			killChild = false;

			if (code === 0) {
				resolve();
			}
			else if (code !== null) {
				reject(
					new Error(
						`Error: ${modulePath} finished with status ${code}`
					)
				);
			}
			else {
				reject(
					new Error(
						`Error: ${modulePath} finished due to signal ${signal}`
					)
				);
			}
		});

		child.on('error', reject);
	});
}
