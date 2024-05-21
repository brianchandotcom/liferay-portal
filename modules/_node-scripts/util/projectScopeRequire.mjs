/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import module from 'module';
import resolve from 'resolve';

const require = module.createRequire(import.meta.url);

export default function projectScopeRequire(filePath, projectDir = '.') {
	return require(resolve.sync(filePath, {basedir: projectDir}));
}
