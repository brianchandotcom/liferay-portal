/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {$} from 'execa';

import fileExists from './fileExists.mjs';

export default async function launchSoffice(...args) {
	if (await fileExists('/usr/bin/soffice')) {
		await $`/usr/bin/soffice ${args.join(' ')}`;
	}
	else {
		console.warn(`
⚠️  Please install LibreOffice if you want to automatically launch
   it after the performed operation.

   Or if it is installed but was not autodetected, launch it
   manually passing the following arguments:

       ${args.join(' ')}

`);
	}
}
