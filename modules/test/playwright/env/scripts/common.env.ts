/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {glob} from 'glob';
import {readFileSync} from 'node:fs'
import {resolve} from 'node:path'
import {KeyValuePairObject} from 'properties-file';

import {getPropertiesFromFiles, getPropertyValue} from './propertiesUtil.env';

export function getLiferayHome(): string {
	return resolve(getPlaywrightProperty('liferay.home'));
}

export function getLiferayPortalDir(): string {
	return resolve(getPlaywrightProperty('liferay.portal.dir'));
}

export function getPlaywrightBaseDir(): string {
	return resolve(getPlaywrightProperty('playwright.base.dir'));
}

export function getPlaywrightProjectDir(): string {
	const playwrightProjectConfigFiles = glob.sync(getPlaywrightBaseDir() + '/tests/**/config.ts');

	const playwrightProjectName = getPlaywrightProperty('playwright.project.name');

	for (const playwrightProjectConfigFile of playwrightProjectConfigFiles) {
		const regex = /.*name: '([^']+)'.*/;

		const regexResults = regex.exec(readFileSync(playwrightProjectConfigFile).toString());

		if (playwrightProjectName === regexResults[1]) {
			return resolve(playwrightProjectConfigFile.replace(/(.+)\/[^\/]+/, '\$1'));
		}
	}

	return null;
}

export function getPlaywrightProperties(): KeyValuePairObject {
	return getPropertiesFromFiles([
		'./playwright.properties',
		'./playwright.' + process.env.HOSTNAME + '.properties',
		'./playwright.' + process.env.HOST + '.properties',
		'./playwright.' + process.env.COMPUTERNAME + '.properties',
		'./playwright.' + _getUserName() + '.properties'
	]);
}

export function getPlaywrightProperty(propertyName: string): string {
	return getPropertyValue(getPlaywrightProperties(), propertyName);
}

function _getUserName(): string {
	return require("os").userInfo().username;
}