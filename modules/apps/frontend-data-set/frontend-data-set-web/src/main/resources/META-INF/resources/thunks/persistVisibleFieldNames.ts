/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {saveViewSettings} from '../utils/saveViewSettings';

// @ts-ignore

import {EViewsActionTypes} from '../views/viewsReducer';

export type VisibleFieldNames = {
	[fieldName: string]: boolean;
};

export default function persistVisibleFieldNames({
	appURL,
	id,
	portletId,
	visibleFieldNames,
}: {
	appURL?: string;
	id?: string;
	portletId?: string;
	visibleFieldNames: VisibleFieldNames;
}) {
	return (viewsDispatch: any) => {
		viewsDispatch({
			type: EViewsActionTypes.UPDATE_VISIBLE_FIELD_NAMES,
			value: visibleFieldNames,
		});

		return saveViewSettings({
			appURL,
			id,
			portletId,
			settings: {visibleFieldNames},
		});
	};
}
