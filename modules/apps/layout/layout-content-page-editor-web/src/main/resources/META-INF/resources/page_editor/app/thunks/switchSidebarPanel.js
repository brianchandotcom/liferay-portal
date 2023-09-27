/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {switchSidebarPanel as switchSidebarPanelAction} from '../actions/index';
import hideProductMenuIfPresent from '../utils/hideProductMenuIfPresent';

export default function switchSidebarPanel(action) {
	return (dispatch) => {
		hideProductMenuIfPresent({
			onHide: () => {
				dispatch(switchSidebarPanelAction({...action}));
			},
		});
	};
}
