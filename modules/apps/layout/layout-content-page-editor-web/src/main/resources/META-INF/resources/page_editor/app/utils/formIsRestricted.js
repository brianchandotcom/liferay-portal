/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {config} from '../config/index';
import {formIsMapped} from './formIsMapped';

export function formIsRestricted(item) {
	const {classNameId} = item.config;
	const {formTypes} = config;

	if (classNameId === '0') {
		return false;
	}

	return (
		formIsMapped(item) &&
		formTypes.find((formType) => formType.value === classNameId)
			?.isRestricted
	);
}
