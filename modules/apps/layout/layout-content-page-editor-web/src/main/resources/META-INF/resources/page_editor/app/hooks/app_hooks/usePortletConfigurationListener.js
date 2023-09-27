/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';

import updateDraft from '../../actions/updateDraft';
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

			dispatch(updateDraft({draft: true}));
		};

		Liferay.on('editConfiguration', onEditConfiguration);

		return () => Liferay.detach('editConfiguration', onEditConfiguration);
	}, [dispatch, segmentsExperienceId]);
}
