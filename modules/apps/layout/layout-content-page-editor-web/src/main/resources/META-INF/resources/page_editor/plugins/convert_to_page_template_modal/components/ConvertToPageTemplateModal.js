/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useIsMounted} from '@liferay/frontend-js-react-web';
import {PageTemplateModal} from '@liferay/layout-js-components-web';
import React, {useCallback, useEffect, useState} from 'react';

import {config} from '../../../app/config/index';
import {useSelector} from '../../../app/contexts/StoreContext';
import selectSegmentsExperienceId from '../../../app/selectors/selectSegmentsExperienceId';

export default function ConvertToPageTemplateModal() {
	const isMounted = useIsMounted();

	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const hasMultipleSegmentsExperienceIds = useSelector((state) =>
		state.availableSegmentsExperiences
			? Object.keys(state.availableSegmentsExperiences).length > 1
			: false
	);

	const [openModal, setOpenModal] = useState(false);
	const onClose = useCallback(() => {
		if (isMounted()) {
			setOpenModal(false);
		}
	}, [isMounted]);

	useEffect(() => {
		const handler = Liferay.on('convertToPageTemplate', () => {
			setOpenModal(true);
		});

		return () => {
			handler.detach();
		};
	}, []);

	if (!openModal) {
		return null;
	}

	return (
		<PageTemplateModal
			createTemplateURL={config.createLayoutPageTemplateEntryURL}
			getCollectionsURL={config.getLayoutPageTemplateCollectionsURL}
			hasMultipleSegmentsExperienceIds={hasMultipleSegmentsExperienceIds}
			namespace={config.portletNamespace}
			onClose={onClose}
			segmentsExperienceId={segmentsExperienceId}
		/>
	);
}
