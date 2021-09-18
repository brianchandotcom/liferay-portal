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
import ClayIcon from '@clayui/icon';
import {useModal} from '@clayui/modal';
import React, {useState} from 'react';

import ModalAddObjectLayoutTab from './ModalAddObjectLayoutTab';

const AddNewTabButton = () => {
	const [visibleModal, setVisibleModal] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	return (
		<>
			<div className="layout-tab__add-tab-btn">
				<ClayButton
					displayType="secondary"
					onClick={() => setVisibleModal(true)}
				>
					<ClayIcon symbol="plus" />

					<span className="ml-2">
						{Liferay.Language.get('add-tab')}
					</span>
				</ClayButton>
			</div>

			{visibleModal && (
				<ModalAddObjectLayoutTab
					observer={observer}
					onClose={onClose}
				/>
			)}
		</>
	);
};

export default AddNewTabButton;
