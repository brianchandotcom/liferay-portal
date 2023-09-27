/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateFragments from '../actions/updateFragments';
import updateWidgets from '../actions/updateWidgets';
import FragmentService from '../services/FragmentService';

export default function updateSetsOrder({
	fragments,
	widgetFragmentEntryLinks,
	widgets,
}) {
	return (dispatch) => {
		return FragmentService.updateSetsOrder({
			fragmentCollectionKeys: fragments,
			onNetworkStatus: dispatch,
			portletCategoryKeys: widgets,
		}).then(({fragmentCollections, portletCategories}) => {
			dispatch(
				updateWidgets({
					fragmentEntryLinks: widgetFragmentEntryLinks,
					widgets: portletCategories,
				})
			);

			dispatch(
				updateFragments({
					fragments: fragmentCollections,
				})
			);
		});
	};
}
