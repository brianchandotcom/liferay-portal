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
		x64: {
			binary: 'dart-sass/sass',
			buildPropertiesKeyPrefix: 'sass.binary.mac',
		},
	},
	linux: {
		x64: {
			binary: 'dart-sass/sass',
			buildPropertiesKeyPrefix: 'sass.binary.linux',
		},
	},
	win32: {
		x64: {
			binary: 'dart-sass/sass.bat',
			buildPropertiesKeyPrefix: 'sass.binary.windows',
		},
	},
};

export default async function getSassBinaryArchMap() {
	let archMap = SASS_BINARY[os.platform()];

	if (archMap === null) {
		return null;
	}

	archMap = archMap[os.arch()];

	if (archMap === null) {
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
		.filter((line) => line.startsWith('sass.binary.'));

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
		'sass.binary.version',
		'sass.binary.base.url',
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
