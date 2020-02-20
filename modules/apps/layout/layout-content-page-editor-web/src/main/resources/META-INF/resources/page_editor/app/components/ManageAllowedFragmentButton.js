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

import ClayButton from '@clayui/button';
import {useModal} from '@clayui/modal';
import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import ManageAllowedFragmentModal from './ManageAllowedFragmentModal';

const ManageAllowedFragmentButton = ({item}) => {
	const isMounted = useIsMounted();

	const [openModal, setOpenModal] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => {
			if (isMounted()) {
				setOpenModal(false);
			}
		}
	});

	const handleOpenModalClick = () => {
		setOpenModal(true);
	};

	return (
		<>
			{openModal && (
				<ManageAllowedFragmentModal
					item={item}
					observer={observer}
					onClose={onClose}
				/>
			)}

			<ClayButton
				displayType="secondary"
				onClick={handleOpenModalClick}
				small
			>
				{Liferay.Language.get('configure-allowed-fragments')}
			</ClayButton>
		</>
	);
};

ManageAllowedFragmentButton.propTypes = {
	item: PropTypes.object
};

export {ManageAllowedFragmentButton};
export default ManageAllowedFragmentButton;
