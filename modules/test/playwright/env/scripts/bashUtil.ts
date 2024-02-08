/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {execSync} from 'child_process';

export function executeBashScript(bashScript: string): string {
	const results = execSync(bashScript);

	return results.toString('utf8');
}

export function executeBashScriptPrint(bashScript: string) {
	process.stdout.write('Executing Bash Script:');
	process.stdout.write(bashScript);

	execSync(bashScript, {stdio: 'inherit'});
}
