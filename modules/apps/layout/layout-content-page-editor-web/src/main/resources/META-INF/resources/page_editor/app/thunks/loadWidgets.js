/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateWidgets from '../actions/updateWidgets';
import WidgetService from '../services/WidgetService';

export default function loadWidgets({fragmentEntryLinks}) {
	return (dispatch) => {
		return WidgetService.getWidgets().then((widgets) =>
			dispatch(
				updateWidgets({
					fragmentEntryLinks,
					widgets,
				})
			)
		);
	};
}
