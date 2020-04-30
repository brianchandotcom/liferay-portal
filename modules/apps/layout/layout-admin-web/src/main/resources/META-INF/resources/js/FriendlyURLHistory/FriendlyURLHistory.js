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
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import FriendlyURLHistoryModal from './FriendlyURLHistoryModal';
import * as provisionalData from './provisionalData';

function FriendlyURLHistory({portletNamespace, ...restProps}) {
	const [showModal, setShowModal] = useState(false);
	const [callback, setCallback] = useState();
	const BRIDGE_COMPONENT_ID = `${portletNamespace}FriendlyURLHistory`;

	const handleOnClose = () => {
		setShowModal(false);
	};

	const {observer, onClose} = useModal({
		onClose: handleOnClose,
	});

	if (!Liferay.component(BRIDGE_COMPONENT_ID)) {
		Liferay.component(
			BRIDGE_COMPONENT_ID,
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

	return (
		<>
			{showModal && (
				<FriendlyURLHistoryModal
					{...restProps}
					callback={callback}
					observer={observer}
					onModalClose={onClose}
				/>
			)}
		</>
	);
}

FriendlyURLHistory.propTypes = {
	portletNamespace: PropTypes.string.isRequired,
};

export default function (props) {
	return (
		<FriendlyURLHistory
			{...provisionalData}
			{...props}
			portletNamespace={`_${props.portletNamespace}_`}
		/>
	);
}
