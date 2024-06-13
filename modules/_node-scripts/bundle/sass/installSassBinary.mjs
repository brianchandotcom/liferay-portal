/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import crypto from 'crypto';
import fs from 'fs/promises';
import os from 'os';
import path from 'path';
import stream from 'stream/promises';
import * as tar from 'tar';
import unzipper from 'unzipper';
import url from 'url';

import fileExists from '../../util/fileExists.mjs';

const SASS_VERSION = '1.77.4';
const BASE_URL = `https://github.com/sass/dart-sass/releases/download/${SASS_VERSION}`;
const SASS_BINARY = {
	darwin: {
		x64: {
			binary: 'dart-sass/sass',
			hash: 'dc1d6ea39e61af298156f1a4b97f4725cf4df48e75d225e8156eb73135885e2e',
			url: `${BASE_URL}/dart-sass-${SASS_VERSION}-macos-x64.tar.gz`
		}
	},
	linux: {
		x64: {
			binary: 'dart-sass/sass',
			hash: 'dc1d6ea39e61af298156f1a4b97f4725cf4df48e75d225e8156eb73135885e2e',
			url: `${BASE_URL}/dart-sass-${SASS_VERSION}-linux-x64.tar.gz`
		}
	},
	win32: {
		x64: {
			binary: 'dart-sass/sass.bat',
			hash: '87a983129c5a7af7defd496f36fa901009a32ee24add6ff268c85f3f2ef40046',
			url: `${BASE_URL}/dart-sass-${SASS_VERSION}-windows-x64.zip`
		}
	}
};

export default async function installSassBinary() {
	const platformMap = SASS_BINARY[os.platform()] || {};
	const archMap = platformMap[os.arch()];

	if (!archMap) {
		return null;
	}

	const __dirname = path.dirname(url.fileURLToPath(import.meta.url));
	
	const downloadPath = path.join(__dirname, 'binary');

	const sassBinaryPath = path.join(downloadPath, archMap.binary);

	if (await fileExists(sassBinaryPath)) {
		const hash = 
			crypto
				.createHash('sha256')
				.update(await fs.readFile(sassBinaryPath))
				.digest('hex');

		if (hash === archMap.hash) {
			return sassBinaryPath;
		}

		console.error(hash);

		console.error('⚠️ Redownloading Sass binary compiler because it is corrupted or outdated');
	}

	try {
		await fs.rm(downloadPath, {recursive: true});
	}
	catch(error) {
		if (error.code !== 'ENOENT') {
			throw error;
		}
	}

	try {
		await downloadAndExtract(archMap.url, downloadPath);
	}
	catch(error) {
		console.error(
			'⚠️ Unable to download binary SASS compiler (will use Node.js based one)' 
		);
		console.error(error);

		return null;
	}

	return sassBinaryPath;
}

async function downloadAndExtract(url, dir) {
	const parts = url.split('/');
	const bundleName = parts[parts.length - 1];
	const bundlePath = path.join(dir, bundleName);

	console.log('🚛 Downloading binary Sass compiler...');

	const response = await fetch(url, {redirect: 'follow'});

	await fs.mkdir(path.dirname(bundlePath), {recursive: true});

	const fd = await fs.open(bundlePath, 'w');
	const file = fd.createWriteStream();

	await stream.pipeline(response.body, file);
	
	if (bundlePath.endsWith('.tar.gz')) {
		await tar.x({
			cwd: dir,
			f: bundlePath
		});
	}
	else if (bundlePath.endsWith('.zip')) {
		const fd = await fs.open(bundlePath, 'r');
		const file = fd.createReadStream();

		await stream.pipeline(file, unzipper.Extract({path: dir}));
	}
	else {
		throw new Error(`Don't know how to uncompress ${bundlePath}`);
	}

	console.log('✅ Binary Sass compiler is ready');
}
