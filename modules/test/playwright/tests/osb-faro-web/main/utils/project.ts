/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../../../../helpers/ApiHelpers';

export async function getDefaultProject(apiHelpers: ApiHelpers) {
	const projects = await apiHelpers.jsonWebServicesOSBFaro.getProjects();

	return projects.find(({name}) => name === 'FARO-DEV-liferay');
}
