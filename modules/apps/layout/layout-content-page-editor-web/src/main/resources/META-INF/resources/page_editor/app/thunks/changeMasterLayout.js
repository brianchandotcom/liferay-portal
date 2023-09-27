/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import changeMasterLayoutAction from '../actions/changeMasterLayout';
import LayoutService from '../services/LayoutService';

export default function changeMasterLayout({masterLayoutPlid}) {
	return (dispatch) => {
		return LayoutService.changeMasterLayout({
			masterLayoutPlid,
			onNetworkStatus: dispatch,
		}).then((data = {}) => {
			dispatch(
				changeMasterLayoutAction({
					fragmentEntryLinks: data.fragmentEntryLinks,
					masterLayoutData: data.masterLayoutData,
					masterLayoutPlid,
				})
			);

			return data;
		});
	};
}
