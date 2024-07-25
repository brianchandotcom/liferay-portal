/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {$} from 'execa';

import {GIT_ORIGIN_NAME, LIFERAY_WORKING_BRANCH} from './constants.mjs';

export async function getRemoteBranchHash(remoteName, branchName) {
	const {stdout} = await $`git ls-remote ${remoteName} ${branchName}`;

	return stdout.match(/^\w+/)[0];
}

export async function getUpstreamCommitHash() {
	let commitHash;

	try {
		commitHash = await getRemoteBranchHash(
			GIT_ORIGIN_NAME,
			LIFERAY_WORKING_BRANCH
		);
	}
	catch (error) {
		const remoteName = await getUpstreamRemoteName();

		console.log(
			`ℹ️ Remote '${GIT_ORIGIN_NAME}' not found. Using '${remoteName}' instead.`
		);

		try {
			commitHash = await getRemoteBranchHash(
				remoteName,
				LIFERAY_WORKING_BRANCH
			);
		}
		catch (error) {
			throw new Error(
				`Could not find remote '${remoteName}/${LIFERAY_WORKING_BRANCH}'.`
			);
		}
	}

	return commitHash;
}

const cachedGitModifiedFiles = {};

export async function getGitModifiedFiles(commit = undefined) {
	if (commit === undefined) {
		commit = await getUpstreamCommitHash();
	}

	if (!cachedGitModifiedFiles[commit]) {
		const {stdout} = await $`git diff --name-only ${commit} HEAD`;

		cachedGitModifiedFiles[commit] = stdout.split('\n');
	}

	return cachedGitModifiedFiles[commit];
}

export async function getUpstreamRemoteName() {
	const {stdout} = await $`git remote -v`;

	const line = stdout
		.split('\n')
		.find((line) => line.includes('liferay/liferay-portal'));

	return line.split(/\s/)[0];
}

export async function getCurrentBranchName() {
	const {stdout} = await $`git rev-parse --abbrev-ref HEAD`;

	return stdout;
}
