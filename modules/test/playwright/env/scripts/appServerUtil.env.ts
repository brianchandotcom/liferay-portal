/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import axios from 'axios';
import {setDefaultResultOrder} from 'dns';
import {glob} from 'glob';
import {copyFile, existsSync, mkdirSync, readFileSync} from 'node:fs'

import {executeBashScript, executeBashScriptPrint} from './bashUtil.env';
import {getLiferayHome, getLiferayPortalDir, getPlaywrightBaseDir, getPlaywrightParentDirs, getPlaywrightProjectDir} from './common.env';
import {executeGradleTask} from './gradleUtil.env';
import {getPropertiesFromFiles, writePropertiesFile} from './propertiesUtil.env';

setDefaultResultOrder("ipv4first");

export function deployParentProjectClientExtensions() {
	for (const playwrightParentDir of getPlaywrightParentDirs()) {
		_deployClientExtensionsListFile(playwrightParentDir);
	}
}

export function deployParentProjectDeployDir() {
	for (const playwrightParentDir of getPlaywrightParentDirs()) {
		_deployProjectDeployDir(playwrightParentDir);
	}
}

export function deployParentProjectOSGiModules() {
	for (const playwrightParentDir of getPlaywrightParentDirs()) {
		_deployOSGiModulesListFile(playwrightParentDir);
	}
}

export function deployProjectClientExtensions() {
	_deployClientExtensionsListFile(getPlaywrightProjectDir());
}

export function deployProjectDeployDir() {
	_deployProjectDeployDir(getPlaywrightProjectDir());
}

export function deployProjectOSGiModules() {
	_deployOSGiModulesListFile(getPlaywrightProjectDir())
}

export function startAppServer() {
	const script = 'cd ' + _getTomcatDir() + ' && /bin/bash startup.sh';

	return executeBashScriptPrint(script);
}

export function stopAppServer() {
	const script = 'cd ' + _getTomcatDir() + ' && /bin/bash shutdown.sh';

	executeBashScriptPrint(script);
}

export function updatePortalExtProperties() {
	const liferayHome = getLiferayHome();
	
	let portalExtPropertiesFile = liferayHome + '/portal-ext.properties';

	let portalExtPropertiesFiles = glob.sync(liferayHome + '/**/portal-ext.properties');

	if (portalExtPropertiesFiles.length == 0) {
		portalExtPropertiesFiles.push(portalExtPropertiesFile);
	}
	else {
		portalExtPropertiesFile = portalExtPropertiesFiles[0];
	}

	portalExtPropertiesFiles = portalExtPropertiesFiles.concat([
		getPlaywrightBaseDir() + '/env/portal-ext.properties',
		getPlaywrightProjectDir() + '/env/portal-ext.properties'
	]);
	
	const portalProperties = getPropertiesFromFiles(portalExtPropertiesFiles);

	writePropertiesFile(portalExtPropertiesFile, portalProperties);
}

export async function waitForStartedAppServer() {
	console.log('Wating for app server to start up.');

	return _waitForURLAvailable('http://localhost:8080/web/guest');
}

function _deployClientExtension(clientExtension: string) {
	const clientExtensionDirs = glob.sync(getLiferayPortalDir() + '/workspaces/*-workspace/client-extensions/' + clientExtension);

	if (clientExtensionDirs.length == 0) {
		throw new Error("Unable to find client extension " + clientExtension);
	}

	const clientExtensionDir = clientExtensionDirs[0]

	const workspaceDir = _getWorkspaceDir(clientExtensionDir);

	const gradleParameters = [];

	gradleParameters.push({name: 'liferay.workspace.home.dir', value: getLiferayHome()});

	executeGradleTask(workspaceDir, ':client-extensions:' + clientExtension + ':deploy', gradleParameters);
}

function _deployClientExtensionsListFile(playwrightProjectDir: string) {
	const clientExtensionsListFile = playwrightProjectDir + '/env/client-extensions.list';

	if (existsSync(clientExtensionsListFile)) {
		const clientExtensionsListFileContent = readFileSync(clientExtensionsListFile).toString();

		for (const clientExtension of clientExtensionsListFileContent.split("\n")) {
			_deployClientExtension(clientExtension);
		}
	}
}

function _deployOSGiModule(osgiModule: string) {
	const script = 'find ' + getLiferayPortalDir() + '/modules | grep -v .releng | grep -v node_modules | grep -v \.npmscripts | grep /' + osgiModule + '$';

	let osgiModuleDir = executeBashScript(script);

	osgiModuleDir = osgiModuleDir.trim();

	if (osgiModuleDir === '') {
		throw new Error('Unable to find ' + osgiModule);
	}

	osgiModuleDir = osgiModuleDir.replace(/.+\/modules(\/.+)/, '\$1');

	const modulesDir = getLiferayPortalDir() + '/modules';

	executeGradleTask(modulesDir, osgiModuleDir.replace(/\//g, ':') + ':deploy', []);
}

function _deployOSGiModulesListFile(playwrightProjectDir: string) {
	const osgiModulesListFile = playwrightProjectDir + '/env/osgi-modules.list';

	if (existsSync(osgiModulesListFile)) {
		const osgiModulesListFileContent = readFileSync(osgiModulesListFile).toString();

		for (const osgiModule of osgiModulesListFileContent.split("\n")) {
			_deployOSGiModule(osgiModule);
		}
	}
}

function _deployProjectDeployDir(playwrightProjectDir: string) {
	const projectDeployDir = playwrightProjectDir + '/env/deploy/';

	if (existsSync(projectDeployDir)) {
		const deployDir = getLiferayHome() + '/deploy';

		if (!existsSync(deployDir)){
			mkdirSync(deployDir, { recursive: true });
		}

		const deployFiles = glob.sync(projectDeployDir + '/*');

		for (const deployFile of deployFiles) {
			const deployFileName = deployFile.replace(/.+\/([^\/]+)/, '\$1');

			copyFile(deployFile, deployDir + '/' + deployFileName, (error) => {
				if (error) {
					throw error;
				}
			});
		}
	}
}

function _getTomcatDir(): string {
	const tomcatBinDirs = glob.sync(getLiferayHome() + '/tomcat*/bin');

	if (tomcatBinDirs.length === 0) {
		throw new Error('Could not find tomcat bin dir');
	}

	return tomcatBinDirs[0];
}

function _getWorkspaceDir(clientExtensionDir: string): string {
	return clientExtensionDir.replace(/(.+\/workspaces\/[^\/]+-workspace)\/.+/, '\$1');
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