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
import {fetch, openModal, openToast} from 'frontend-js-web';
import React, {useContext, useRef, useState} from 'react';

import FrontendDataSetContext from '../../FrontendDataSetContext';
import ViewsContext from '../../views/ViewsContext';

const CustomViewsControls = () => {
	const [viewsDropdownActive, setViewsDropdownActive] = useState(false);
	const [actionsDropdownActive, setActionsDropdownActive] = useState(false);
	const [customViews, setCustomViews] = useState({});

	const {appURL, id, namespace, portletId} = useContext(
		FrontendDataSetContext
	);
	const [{activeView}] = useContext(ViewsContext);

	const customViewNameInputRef = useRef();

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
					ref={customViewNameInputRef}
					type="text"
				/>
			</ClayForm.Group>
		);
	};

	const getNextCustomViewId = () => {
		const ids = Object.keys(customViews);

		let nextId = 1;

		if (ids.length) {
			nextId = Math.max(...ids.map((item) => Number(item))) + 1;
		}

		return String(nextId);
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
						const url = new URL(`${appURL}/fds/${id}/custom-views`);

						url.searchParams.append('portletId', portletId);

						const nextCustomViewId = getNextCustomViewId();
						const viewState = {
							contentRenderer: activeView.contentRenderer,
							customViewName:
								customViewNameInputRef.current.value,
						};

						fetch(url, {
							body: JSON.stringify({
								customViewId: nextCustomViewId,
								viewState,
							}),
							headers: {
								'Accept': 'application/json',
								'Content-Type': 'application/json',
							},
							method: 'POST',
						})
							.then((response) => {
								if (response.ok) {
									processClose();

									openToast({
										message: Liferay.Language.get(
											'view-was-saved-successfully'
										),
										type: 'success',
									});

									setCustomViews({
										...customViews,
										[nextCustomViewId]: viewState,
									});
								}
								else {
									openToast({
										message: Liferay.Language.get(
											'an-unexpected-error-occurred'
										),
										type: 'danger',
									});
								}
							})
							.catch(() => {
								openToast({
									message: Liferay.Language.get(
										'an-unexpected-error-occurred'
									),
									type: 'danger',
								});
							});
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
