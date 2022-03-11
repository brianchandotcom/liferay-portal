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
