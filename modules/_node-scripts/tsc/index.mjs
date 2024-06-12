/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getNamedArguments from '../util/getNamedArguments.mjs';
import runTscChecks from './runTscChecks.mjs';

export default async function main() {
	const {modifiedSince} = getNamedArguments({
		modifiedSince: '--modified-since',
	});

	await runTscChecks(modifiedSince);
}
