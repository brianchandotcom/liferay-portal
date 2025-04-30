/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const IMPORT_TASK_PATH = '/import-task';

const VERSION = 'v1.0';

function resolveImportTaskPath(basePath = '', importTaskId) {
	return `${basePath}${VERSION}${IMPORT_TASK_PATH}/${importTaskId}`;
}

export default function Task(basePath) {
	return {
		getImportTaskId: (importTaskId) =>
			AJAX.GET(resolveImportTaskPath(basePath, importTaskId)),
	};
}
