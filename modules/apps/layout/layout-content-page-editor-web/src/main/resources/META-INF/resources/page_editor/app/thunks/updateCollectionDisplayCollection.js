/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateCollectionDisplayCollectionAction from '../actions/updateCollectionDisplayCollection';
import LayoutService from '../services/LayoutService';

export default function updateCollectionDisplayCollection({
	collection,
	itemId,
	listStyle,
}) {
	return (dispatch, getState) =>
		LayoutService.updateCollectionDisplayConfig({
			itemConfig: {
				collection,
				listItemStyle: null,
				listStyle,
				paginationType: 'numeric',
				showAllItems: false,
				templateKey: null,
			},
			itemId,
			languageId: getState().languageId,
			onNetworkStatus: dispatch,
			segmentsExperienceId: getState().segmentsExperienceId,
		}).then(({fragmentEntryLinks, layoutData, pageContents}) =>
			dispatch(
				updateCollectionDisplayCollectionAction({
					fragmentEntryLinks,
					itemId,
					layoutData,
					pageContents,
				})
			)
		);
}
