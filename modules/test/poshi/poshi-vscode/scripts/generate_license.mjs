/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
