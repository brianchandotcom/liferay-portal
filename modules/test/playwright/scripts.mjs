/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {execa} from 'execa';

const jsGlobs = '**/*.{js,jsx,mjs,ts,tsx}';

const options = {stderr: process.stdout, stdout: process.stdout};

switch (process.argv[2]) {
	case 'checkFormat': {
		await execa(options)`prettier ${jsGlobs} --check`;

		await execa(options)`eslint ${jsGlobs}`;

		break;
	}
	case 'format': {
		await execa(options)`prettier ${jsGlobs} --write`;

		await execa(options)`eslint ${jsGlobs} --fix`;

		break;
	}
	default: {

		// eslint-disable-next-line no-console
		console.log(
			'Expected either "format" or "checkFormat" as the first argument.'
		);
	}
}
