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
import React from 'react';
import ManageAllowedFragmentModal from './ManageAllowedFragmentModal.es';

const ManageAllowedFragmentButton = () => {
	const handleOpenModalClick = event => {
		event.preventDefault();


	};

	return (
		<ClayButton displayType="secondary" onClick={handleOpenModalClick}>
			{Liferay.Language.get('manage-allowed-fragments')}
		</ClayButton>
	);
};

export {ManageAllowedFragmentButton};
export default ManageAllowedFragmentButton;
