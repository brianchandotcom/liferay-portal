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
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ManagementToolbar} from 'frontend-js-components-web';
import {openModal} from 'frontend-js-web';
import React, {useContext, useState} from 'react';

import FrontendDataSetContext from './../../FrontendDataSetContext';

const CustomViewsControls = () => {
	const [viewsDropdownActive, setViewsDropdownActive] = useState(false);
	const [actionsDropdownActive, setActionsDropdownActive] = useState(false);

	const {namespace} = useContext(FrontendDataSetContext);

	const SaveCustomViewModalBody = () => {
		return (
			<ClayForm.Group>
				<label htmlFor={`${namespace}customViewNameInput`}>
					{Liferay.Language.get('name')}

					<RequiredMark />
				</label>

				<ClayInput
					autoFocus={true}
					id={`${namespace}customViewNameInput`}
					type="text"
				/>
			</ClayForm.Group>
		);
	};

	const openSaveCustomViewModal = () => {
		openModal({
			bodyComponent: SaveCustomViewModalBody,
			buttons: [
				{
					displayType: 'secondary',
					label: Liferay.Language.get('cancel'),
					type: 'cancel',
				},
				{
					label: Liferay.Language.get('save'),
					onClick: ({processClose}) => {
						processClose();
					},
				},
			],
			title: Liferay.Language.get('save-new-view'),
		});
	};

	return (
		<>
			<ManagementToolbar.Item>
				<ClayDropDown
					active={viewsDropdownActive}
					className="custom-views-dropdown"
					onActiveChange={setViewsDropdownActive}
					trigger={
						<ClayButton displayType="unstyled">
							<span className="navbar-text-truncate">
								{Liferay.Language.get('default-view')}
							</span>

							<ClayIcon className="ml-2" symbol="caret-double" />
						</ClayButton>
					}
				>
					<ClayDropDown.ItemList>
						<ClayDropDown.Item>
							{Liferay.Language.get('default-view')}
						</ClayDropDown.Item>
					</ClayDropDown.ItemList>
				</ClayDropDown>
			</ManagementToolbar.Item>

			<ManagementToolbar.Item>
				<ClayDropDown
					active={actionsDropdownActive}
					onActiveChange={setActionsDropdownActive}
					trigger={
						<ClayButton displayType="unstyled">
							<ClayIcon symbol="ellipsis-v" />
						</ClayButton>
					}
				>
					<ClayDropDown.ItemList>
						<ClayDropDown.Item onClick={openSaveCustomViewModal}>
							{Liferay.Language.get('save')}
						</ClayDropDown.Item>
					</ClayDropDown.ItemList>
				</ClayDropDown>
			</ManagementToolbar.Item>
		</>
	);
};

const RequiredMark = () => (
	<>
		<span className="inline-item-after reference-mark text-warning">
			<ClayIcon symbol="asterisk" />
		</span>
		<span className="hide-accessible sr-only">
			{Liferay.Language.get('required')}
		</span>
	</>
);

export default CustomViewsControls;
