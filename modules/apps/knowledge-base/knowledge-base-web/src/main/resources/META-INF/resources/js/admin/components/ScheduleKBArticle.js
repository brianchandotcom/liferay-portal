/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/modal';
import React, {useEffect, useState} from 'react';

import ScheduleModal from './ScheduleModal';

export default function ScheduleKBArticle({
	displayDate,
	portletNamespace,
	scheduled,
	timeZone,
}) {
	const [showModal, setShowModal] = useState();
	const [callback, setCallback] = useState();

	const handleOnClose = () => {
		setShowModal(false);
	};

	const {observer, onClose} = useModal({
		onClose: handleOnClose,
	});

	useEffect(() => {
		const bridgeComponentId = `${portletNamespace}ScheduleKBArticleComponent`;

		if (!Liferay.component(bridgeComponentId)) {
			Liferay.component(
				bridgeComponentId,
				{
					open: (callback) => {
						setCallback(() => callback);
						setShowModal(true);
					},
				},
				{
					destroyOnNavigate: true,
				}
			);
		}

		return () => {
			Liferay.destroyComponent(bridgeComponentId);
		};
	}, [portletNamespace]);

	return (
		<>
			{showModal && (
				<ScheduleModal
					callback={callback}
					displayDate={displayDate}
					observer={observer}
					onModalClose={onClose}
					scheduled={scheduled}
					timeZone={timeZone}
				/>
			)}
		</>
	);
}
