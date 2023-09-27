/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {saveViewSettings} from '../utils/saveViewSettings';
import {VIEWS_ACTION_TYPES} from '../views/viewsReducer';

export default function persistActiveView({
	activeViewName,
	appURL,
	id,
	portletId,
}) {
	return (viewsDispatch) => {
		viewsDispatch({
			type: VIEWS_ACTION_TYPES.UPDATE_ACTIVE_VIEW,
			value: activeViewName,
		});

		return saveViewSettings({
			appURL,
			id,
			portletId,
			settings: {name: activeViewName},
		});
	};
}
