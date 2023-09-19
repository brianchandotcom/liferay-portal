/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/modal';
import React, {useEffect, useState} from 'react';

import ScheduleModal from './ScheduleModal';

function ScheduleKBArticle(props) {
	const [showModal, setShowModal] = useState();
	const namespace = props.portletNamespace;

	const handleOnClose = () => {
		setShowModal(false);
	};

	const {observer, onClose} = useModal({
		onClose: handleOnClose,
	});

	useEffect(() => {
		const bridgeComponentId = `${namespace}ScheduleKBArticleComponent`;

		if (!Liferay.component(bridgeComponentId)) {
			Liferay.component(
				bridgeComponentId,
				{
					open: () => {
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
	}, [namespace]);

	return (
		<>
			{showModal && (
				<ScheduleModal
					{...props}
					observer={observer}
					onModalClose={onClose}
				/>
			)}
		</>
	);
}

export default ScheduleKBArticle;
