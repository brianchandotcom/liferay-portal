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

import {ClayCheckbox} from '@clayui/form';
import ClayManagementToolbar from '@clayui/management-toolbar';
import React, {useEffect, useRef, useState} from 'react';

import {EVENT_MANAGEMENT_TOOLBAR_TOGGLE_ALL_ITEMS} from '../constants';
import LinkOrButton from './LinkOrButton';

function disableActionIfNeeded(item, event, bulkSelection) {
	if (item.type === 'group') {
		return {
			...item,
			items: item.items?.map((child) =>
				disableActionIfNeeded(child, event, bulkSelection)
			),
		};
	}

	return {
		...item,
		disabled:
			event.actions?.indexOf(item.data.action) === -1 &&
			(!bulkSelection || !item.data.enableOnBulk),
	};
}

const SelectionControls = ({
	actionDropdownItems,
	active,
	clearSelectionURL,
	disabled,
	initialCheckboxStatus,
	initialSelectAllButtonVisible,
	initialSelectedItems,
	itemsTotal,
	onCheckboxChange,
	onClearButtonClick,
	onSelectAllButtonClick,
	searchContainerId,
	selectAllURL,
	setActionDropdownItems,
	setActive,
	showCheckBoxLabel,
	supportsBulkActions,
}) => {
	const [selectedItems, setSelectedItems] = useState(initialSelectedItems);
	const [checkboxStatus, setCheckboxStatus] = useState(initialCheckboxStatus);
	const [selectAllButtonVisible, setSelectAllButtonVisible] = useState(
		initialSelectAllButtonVisible
	);

	const searchContainerRef = useRef();

	const updateControls = ({bulkSelection, elements}) => {
		const currentPageSelectedElementsCount = elements.currentPageSelectedElements.size();

		const selectedElementsCount = bulkSelection
			? itemsTotal
			: elements.allSelectedElements.filter(':enabled').size();

		setSelectedItems(selectedElementsCount);

		setActive(selectedElementsCount > 0);

		const allCurrentPageElementsSelected =
			currentPageSelectedElementsCount ===
			elements.currentPageElements.size();

		setCheckboxStatus(
			currentPageSelectedElementsCount > 0
				? allCurrentPageElementsSelected
					? 'checked'
					: 'indeterminate'
				: 'unchecked'
		);

		if (supportsBulkActions) {
			setSelectAllButtonVisible(
				allCurrentPageElementsSelected &&
					itemsTotal > selectedElementsCount &&
					!bulkSelection
			);
		}
	};

	useEffect(() => {
		let eventHandler;

		Liferay.componentReady(searchContainerId).then((searchContainer) => {
			searchContainerRef.current = searchContainer;

			const select = searchContainer.select;

			if (!select) {
				return;
			}

			const bulkSelection =
				supportsBulkActions && select.get('bulkSelection');

			eventHandler = searchContainer.on('rowToggled', (event) => {
				updateControls({
					bulkSelection,
					elements: event.elements,
				});

				setActionDropdownItems(
					actionDropdownItems?.map((item) =>
						disableActionIfNeeded(item, event, bulkSelection)
					)
				);
			});

			updateControls({
				bulkSelection,
				elements: {
					allSelectedElements: select.getAllSelectedElements(),
					currentPageElements: select.getCurrentPageElements(),
					currentPageSelectedElements: select.getCurrentPageSelectedElements(),
				},
			});
		});

		return () => {
			eventHandler?.detach();
		};

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<>
			<ClayManagementToolbar.Item>
				<ClayCheckbox
					checked={checkboxStatus !== 'unchecked'}
					disabled={disabled}
					indeterminate={checkboxStatus === 'indeterminate'}
					label={
						showCheckBoxLabel
							? Liferay.Language.get('select-items')
							: ''
					}
					onChange={(event) => {
						onCheckboxChange(event);

						const checked = event.target.checked;

						setActive(checked);

						setCheckboxStatus(checked ? 'checked' : 'unchecked');

						searchContainerRef.current?.select?.toggleAllRows(
							checked
						);

						Liferay.fire(
							EVENT_MANAGEMENT_TOOLBAR_TOGGLE_ALL_ITEMS,
							{
								checked,
							}
						);
					}}
				/>
			</ClayManagementToolbar.Item>

			{active && (
				<>
					<ClayManagementToolbar.Item>
						<span className="navbar-text">
							{selectedItems === itemsTotal
								? Liferay.Language.get('all-selected')
								: `${Liferay.Util.sub(
										Liferay.Language.get('x-of-x'),
										selectedItems,
										itemsTotal
								  )} ${Liferay.Language.get('selected')}`}
						</span>
					</ClayManagementToolbar.Item>

					{supportsBulkActions && (
						<>
							<ClayManagementToolbar.Item className="nav-item-shrink">
								<LinkOrButton
									className="nav-link"
									displayType="unstyled"
									href={clearSelectionURL}
									onClick={(event) => {
										searchContainerRef.current?.select?.toggleAllRows(
											false,
											true
										);

										setActive(false);

										setCheckboxStatus('unchecked');

										onClearButtonClick(event);
									}}
								>
									<span className="text-truncate-inline">
										<span className="text-truncate">
											{Liferay.Language.get('clear')}
										</span>
									</span>
								</LinkOrButton>
							</ClayManagementToolbar.Item>

							{selectAllButtonVisible && (
								<ClayManagementToolbar.Item className="nav-item-shrink">
									<LinkOrButton
										className="nav-link"
										displayType="unstyled"
										href={selectAllURL}
										onClick={(event) => {
											searchContainerRef.current?.select?.toggleAllRows(
												true,
												true
											);

											setSelectAllButtonVisible(false);

											setSelectedItems(itemsTotal);

											onSelectAllButtonClick(event);
										}}
									>
										<span className="text-truncate-inline">
											<span className="text-truncate">
												{Liferay.Language.get(
													'select-all'
												)}
											</span>
										</span>
									</LinkOrButton>
								</ClayManagementToolbar.Item>
							)}
						</>
					)}
				</>
			)}
		</>
	);
};

export default SelectionControls;
