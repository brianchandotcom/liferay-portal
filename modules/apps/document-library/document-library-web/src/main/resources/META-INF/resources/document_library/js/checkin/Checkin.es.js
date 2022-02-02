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
