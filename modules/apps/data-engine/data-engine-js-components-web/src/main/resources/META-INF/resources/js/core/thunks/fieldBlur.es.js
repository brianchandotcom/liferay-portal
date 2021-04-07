/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {EVENT_TYPES} from '../actions/eventTypes.es';

export default function fieldBlur({
	activePage,
	focusDuration,
	formId,
	properties,
}) {
	return (dispatch) => {
		const {fieldInstance} = properties;

		dispatch({payload: properties, type: EVENT_TYPES.FIELD.BLUR});

		Liferay.fire('ddmFieldBlur', {
			fieldName: fieldInstance.label,
			focusDuration:
				focusDuration.end - (focusDuration.start ?? focusDuration.end),
			formId,
			page: activePage,
		});
	};
}
