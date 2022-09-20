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
import {VIEWS_ACTION_TYPES} from '../../views/viewsReducer';

const CustomViewsControls = () => {
	const [viewsDropdownActive, setViewsDropdownActive] = useState(false);
	const [actionsDropdownActive, setActionsDropdownActive] = useState(false);

	const {appURL, id: fdsName, namespace, portletId} = useContext(
		FrontendDataSetContext
	);
	const [
		{
			activeCustomViewId,
			activeView,
			customViews,
			filters,
			paginationDelta,
			sorting,
			viewUpdated,
			visibleFieldNames,
		},
		viewsDispatch,
	] = useContext(ViewsContext);

	const customViewLabelInputRef = useRef();

	const SaveCustomViewModalBody = () => (
		<ClayForm.Group>
			<label htmlFor={`${namespace}customViewLabelInput`}>
				{Liferay.Language.get('name')}

				<RequiredMark />
			</label>

			<ClayInput
				autoFocus={true}
				id={`${namespace}customViewLabelInput`}
				ref={customViewLabelInputRef}
				type="text"
			/>
		</ClayForm.Group>
	);

	const ActionsItemList = () => (
		<ClayDropDown.ItemList>
			{activeCustomViewId && (
				<ClayDropDown.Item
					onClick={() => {
						saveCustomView({
							id: activeCustomViewId,
						});

						setActionsDropdownActive(false);
					}}
					symbolLeft="disk"
				>
					{Liferay.Language.get('save-view')}
				</ClayDropDown.Item>
			)}

			<ClayDropDown.Item
				onClick={openSaveCustomViewModal}
				symbolLeft="disk"
			>
				{Liferay.Language.get('save-view-as')}
			</ClayDropDown.Item>
		</ClayDropDown.ItemList>
	);

	const getNextCustomViewId = () => {
		const ids = Object.keys(customViews);

		let nextId = 1;

		if (ids.length) {
			nextId = Math.max(...ids.map((item) => Number(item))) + 1;
		}

		return String(nextId);
	};

	const saveCustomView = ({id, label, processClose}) => {
		const url = new URL(`${appURL}/fds/${fdsName}/custom-views`);

		url.searchParams.append('portletId', portletId);

		const viewState = {
			activeView,
			customViewLabel: label ?? customViews[id].customViewLabel,
			filters,
			paginationDelta,
			sorting,
			visibleFieldNames,
		};

		fetch(url, {
			body: JSON.stringify({
				customViewId: id,
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
					if (processClose) {
						processClose();
					}

					openToast({
						message: Liferay.Language.get(
							'view-was-saved-successfully'
						),
						type: 'success',
					});

					viewsDispatch({
						type: VIEWS_ACTION_TYPES.ADD_OR_UPDATE_CUSTOM_VIEW,
						value: {
							id,
							viewState,
						},
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
						saveCustomView({
							id: getNextCustomViewId(),
							label: customViewLabelInputRef.current.value,
							processClose,
						});
					},
				},
			],
			title: Liferay.Language.get('save-new-view-as'),
		});
	};

	return (
		<>
			<ManagementToolbar.Item>
				<ClayDropDown
					active={viewsDropdownActive}
					className="custom-views-selection"
					hasLeftSymbols
					onActiveChange={setViewsDropdownActive}
					trigger={
						<ClayButton displayType="unstyled">
							<span className="navbar-text-truncate">
								{activeCustomViewId
									? customViews[activeCustomViewId]
											.customViewLabel
									: Liferay.Language.get('default-view')}
							</span>

							{viewUpdated && (
								<span className="inline-item-after reference-mark view-updated-mark">
									<ClayIcon symbol="asterisk" />
								</span>
							)}

							<ClayIcon className="ml-2" symbol="caret-bottom" />
						</ClayButton>
					}
				>
					<ClayDropDown.ItemList>
						{Object.keys(customViews).map((id) => (
							<ClayDropDown.Item
								key={id}
								onClick={() => {
									viewsDispatch({
										type:
											VIEWS_ACTION_TYPES.UPDATE_ACTIVE_CUSTOM_VIEW,
										value: id,
									});

									setViewsDropdownActive(false);
								}}
								symbolLeft={
									id === activeCustomViewId && 'check'
								}
							>
								{customViews[id].customViewLabel}
							</ClayDropDown.Item>
						))}

						<ClayDropDown.Item
							onClick={() => {
								viewsDispatch({
									type:
										VIEWS_ACTION_TYPES.RESET_TO_DEFAULT_VIEW,
								});

								setViewsDropdownActive(false);
							}}
							symbolLeft={!activeCustomViewId && 'check'}
						>
							{Liferay.Language.get('default-view')}
						</ClayDropDown.Item>
					</ClayDropDown.ItemList>

					<ClayDropDown.Divider />

					<ActionsItemList />
				</ClayDropDown>
			</ManagementToolbar.Item>

			<ManagementToolbar.Item>
				<ClayDropDown
					active={actionsDropdownActive}
					className="custom-views-actions"
					hasLeftSymbols
					onActiveChange={setActionsDropdownActive}
					trigger={
						<ClayButton displayType="unstyled">
							<ClayIcon symbol="ellipsis-v" />
						</ClayButton>
					}
				>
					<ActionsItemList />
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
