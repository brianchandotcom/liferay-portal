/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {config} from '../config/index';

export function formIsMapped(item) {
	const {classNameId, classTypeId} = item.config;

	const {formTypes} = config;

	const type = formTypes.find(({value}) => value === classNameId);

	if (!type) {
		return false;
	}

	const subtype = type.subtypes?.find(({value}) => value === classTypeId);

	if (subtype || classTypeId === '0') {
		return true;
	}

	return false;
}
