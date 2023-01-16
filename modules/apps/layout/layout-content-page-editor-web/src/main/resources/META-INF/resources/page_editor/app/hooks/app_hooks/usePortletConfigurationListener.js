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

import {useEffect} from 'react';

import updatePageContents from '../../actions/updatePageContents';
import {useDispatch, useSelector} from '../../contexts/StoreContext';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';
import InfoItemService from '../../services/InfoItemService';

const PAGE_CONTENTS_AWARE_PORTLET_IDS = [
	'com_liferay_journal_content_web_portlet_JournalContentPortlet',
	'com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet',
];

export default function usePortletConfigurationListener() {
	const dispatch = useDispatch();

	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	useEffect(() => {
		const onEditConfiguration = ({portletId}) => {
			if (PAGE_CONTENTS_AWARE_PORTLET_IDS.includes(portletId)) {
				InfoItemService.getPageContents({
					onNetworkStatus: dispatch,
					segmentsExperienceId,
				}).then((pageContents) => {
					dispatch(
						updatePageContents({
							pageContents,
						})
					);
				});
			}
		};

		Liferay.on('editConfiguration', onEditConfiguration);

		return () => Liferay.detach('editConfiguration', onEditConfiguration);
	}, [dispatch, segmentsExperienceId]);
}
