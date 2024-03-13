/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/modal';
import React, {useEffect, useState} from 'react';

import CheckinModal from './CheckinModal.es';

export default function Checkin({
	checkedOut,
	dlVersionNumberIncreaseValues,
	portletNamespace,
}) {
	const [showModal, setShowModal] = useState(false);
	const [callback, setCallback] = useState();

	const handleOnClose = () => {
		setShowModal(false);
	};

	const {observer, onClose} = useModal({
		onClose: handleOnClose,
	});

	useEffect(() => {
		const bridgeComponentId = `${portletNamespace}DocumentLibraryCheckinModal`;
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
				<CheckinModal
					callback={callback}
					checkedOut={checkedOut}
					dlVersionNumberIncreaseValues={
						dlVersionNumberIncreaseValues
					}
					observer={observer}
					onModalClose={onClose}
				/>
			)}
		</>
	);
}
