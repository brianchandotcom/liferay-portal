/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {spawnSync} from 'node:child_process';
import {createReadStream, createWriteStream} from 'node:fs';
import {dirname, join} from 'node:path';
import {fileURLToPath} from 'node:url';

const __dirname = dirname(fileURLToPath(import.meta.url));

const gitRevParseProcessResult = spawnSync(
	'git',
	['rev-parse', '--show-toplevel'],
	{
		encoding: 'utf-8',
	}
);

const sourceStream = createReadStream(
	join(gitRevParseProcessResult.stdout.trim(), 'copyright.txt')
);
const targetStream = createWriteStream(join(__dirname, '..', 'LICENSE.txt'));

sourceStream.pipe(targetStream);

process.stdout.write('Generated license file LICENSE.txt\n');
