/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import os from 'os';
import path from 'path';

import {getBuildPropertiesPath, getRootDir} from '../../util/constants.mjs';

const SASS_BINARY = {
	darwin: {
		arm64: {
			binary: 'dart-sass/sass',
			buildPropertiesKeyPrefix: 'nodejs.sass.mac.arm',
		},
		x64: {
			binary: 'dart-sass/sass',
			buildPropertiesKeyPrefix: 'nodejs.sass.mac',
		},
	},
	linux: {
		x64: {
			binary: 'dart-sass/sass',
			buildPropertiesKeyPrefix: 'nodejs.sass.linux',
		},
	},
	win32: {
		x64: {
			binary: 'dart-sass/sass.bat',
			buildPropertiesKeyPrefix: 'nodejs.sass.windows',
		},
	},
};

export default async function getSassBinaryArchMap() {
	let archMap = SASS_BINARY[os.platform()];

	if (archMap === null) {
		return null;
	}

	archMap = archMap[os.arch()];

	if (!archMap) {
		return null;
	}

	if (archMap.url && archMap.hash) {
		return archMap;
	}

	// Fill in URL and hash

	const props = await fs.readFile(await getBuildPropertiesPath(), 'utf-8');

	const lines = props
		.split('\n')
		.map((line) => line.trim())
		.filter((line) => line.startsWith('nodejs.sass.'));

	const map = lines.reduce((map, line) => {
		const parts = line.split('=');

		map[parts[0]] = parts[1];

		return map;
	}, {});

	// Interpolate ${project.dir} variable first

	const projectDir = path.resolve(await getRootDir(), '..');

	for (const key of Object.keys(map)) {
		map[key] = map[key].replace('${project.dir}', projectDir);
	}

	// Then interpolate self referenced variables (beware: order of interpolation matters!)

	for (const interpolateKey of [
		'nodejs.sass.version',
		'nodejs.sass.linux.name',
		'nodejs.sass.mac.name',
		'nodejs.sass.mac.arm.name',
		'nodejs.sass.windows.name',
		'nodejs.sass.base.url',
	]) {
		for (const key of Object.keys(map)) {
			map[key] = map[key].replace(
				`\${${interpolateKey}}`,
				map[interpolateKey]
			);
		}
	}

	archMap.hash = map[`${archMap.buildPropertiesKeyPrefix}.hash`];
	archMap.url = map[`${archMap.buildPropertiesKeyPrefix}.url`];

	return archMap;
}
