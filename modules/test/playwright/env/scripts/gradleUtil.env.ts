/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {existsSync, PathOrFileDescriptor} from 'node:fs'
import {resolve} from 'node:path'

import {executeBashScriptPrint} from './bashUtil.env'

export type GradleParameter = {
	name: string;
	value: string;
}

export function executeGradleTask(executionDir: PathOrFileDescriptor, taskName: string, gradleParameters: GradleParameter[]) {
	let script = 'cd ' + executionDir + ' ; ';
	
	script += _getGradleWrapper(executionDir) + ' ' + taskName;

	for (const gradleParameter of gradleParameters) {
		script += ' -P' + gradleParameter.name + '=' + gradleParameter.value;
	}

	executeBashScriptPrint(script);
}

function _getGradleWrapper(dir: PathOrFileDescriptor): string {
	const gradleWrapper = dir + "/gradlew";

	if (existsSync(gradleWrapper)) {
		return resolve(gradleWrapper.toString());
	}

	const regex = /(.+)\/[^\/]+/;

	const results = regex.exec(dir.toString());

	return _getGradleWrapper(resolve(results[1]));
}