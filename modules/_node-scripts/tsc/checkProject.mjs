/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {$} from 'execa';
import path from 'path';
import resolve from 'resolve';

/**
 * @returns string|boolean
 * The output of the tsc command if captureOutput is passed as true or a boolean indicating if the
 * check was successful otherwise.
 */
export default async function checkProject(projectDir, captureOutput) {
	const tscPath = resolve.sync('typescript/bin/tsc', {basedir: '.'});

	const configPath = path.join(
		'src',
		'main',
		'resources',
		'META-INF',
		'resources',
		'tsconfig.json'
	);

	const {all} = await $({
		all: true,
		cwd: projectDir,
		reject: false,
		stdout: captureOutput ? 'pipe' : ['inherit', 'pipe'],
	})`${tscPath} -b ${configPath}`;

	return captureOutput ? all : all.trim().length;
}
