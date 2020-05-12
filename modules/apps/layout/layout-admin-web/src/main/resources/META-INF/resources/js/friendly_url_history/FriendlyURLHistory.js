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

import {ClayButtonWithIcon} from '@clayui/button';
import {useModal} from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import FriendlyURLHistoryModal from './FriendlyURLHistoryModal';

function FriendlyURLHistory({portletNamespace, ...restProps}) {
	const [showModal, setShowModal] = useState(false);
	const [selectedLanguageId, setSelectedLanguageId] = useState();

	const handleOnClose = () => {
		setShowModal(false);
	};

	const {observer, onClose} = useModal({
		onClose: handleOnClose,
	});

	return (
		<>
			<ClayButtonWithIcon
				borderless
				className="btn-url-history"
				displayType="secondary"
				onClick={() => {
					setSelectedLanguageId(
						Liferay.component(
							`${portletNamespace}friendlyURL`
						).getSelectedLanguageId()
					);
					setShowModal(true);
				}}
				outline
				small
				symbol="restore"
			/>
			{showModal && (
				<FriendlyURLHistoryModal
					{...restProps}
					initialLanguageId={selectedLanguageId}
					observer={observer}
					onModalClose={onClose}
					portletNamespace={portletNamespace}
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
			{...props}
			portletNamespace={`_${props.portletNamespace}_`}
		/>
	);
}
