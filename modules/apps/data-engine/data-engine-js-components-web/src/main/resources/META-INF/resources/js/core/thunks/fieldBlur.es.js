/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EVENT_TYPES} from '../actions/eventTypes.es';

export default function fieldBlur({
	activePage,
	focusDuration,
	formId,
	formPageTitle,
	properties,
	title,
}) {
	return (dispatch) => {
		const {fieldInstance} = properties;

		dispatch({payload: properties, type: EVENT_TYPES.FIELD.BLUR});

		Liferay.fire('ddmFieldBlur', {
			fieldName: fieldInstance.label,
			focusDuration:
				focusDuration.end - (focusDuration.start ?? focusDuration.end),
			formId,
			formPageTitle,
			page: activePage,
			title,
		});
	};
}
