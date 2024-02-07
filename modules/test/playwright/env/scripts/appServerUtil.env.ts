/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import axios from 'axios';
import {setDefaultResultOrder} from 'dns';
import {glob} from 'glob';

import {getLiferayHome} from './common.env';
import {executeBashScriptPrint} from './bashUtil.env';

setDefaultResultOrder("ipv4first");

export function startAppServer() {
	const script = 'cd ' + _getTomcatDir() + ' && /bin/bash startup.sh';

	return executeBashScriptPrint(script);
}

export function stopAppServer() {
	const script = 'cd ' + _getTomcatDir() + ' && /bin/bash shutdown.sh';

	executeBashScriptPrint(script);
}

export async function waitForStartedAppServer() {
	console.log('Wating for app server to start up.');

	return _waitForURLAvailable('http://localhost:8080/web/guest');
}

function _getTomcatDir(): string {
	const tomcatBinDirs = glob.sync(getLiferayHome() + '/tomcat*/bin');

	if (tomcatBinDirs.length === 0) {
		throw new Error('Could not find tomcat bin dir');
	}

	return tomcatBinDirs[0];
}

async function _isURLAvailable(url: string): Promise<Boolean> {
	try {
		const response = await axios.head(url);

		if (response.status === 200) {
			return true;
		}
	}
	catch (error) {
		return false;
	}

	return false;
}

async function _waitForURLAvailable(url: string, timeoutMinutes: number = 5) {
	const startTime = Date.now();

	while (!(await _isURLAvailable(url))) {
		if (Date.now() - startTime > timeoutMinutes * 60 * 1000) {
			console.log(url + ' is unavailable in ' + timeoutMinutes + ' minutes.');

			return false;
		}

		await new Promise(resolve => setTimeout(resolve, 5000));
	}

	console.log(url + ' is available.');

	return true;
}