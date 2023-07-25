/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CommerceFrontendUtils} from 'commerce-frontend-js';

import {updateProductFields} from './util/index';

export default function ({namespace}) {
	Liferay.on(
		`${namespace}${CommerceFrontendUtils.Events.CP_INSTANCE_CHANGED}`,
		updateProductFields
	);
}
