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
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayManagementToolbar from '@clayui/management-toolbar';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import ActionControls from './ActionControls';
import CreationMenu from './CreationMenu';
import FilterOrderControls from './FilterOrderControls';
import InfoPanelControl from './InfoPanelControl';
import ResultsBar from './ResultsBar';
import SearchControls from './SearchControls';
import SelectionControls from './SelectionControls';

function ManagementToolbar({
	clearResultsURL,
	clearSelectionURL,
	creationMenu,
	disabled,
	filterDropdownItems,
	filterLabelItems,
	itemsTotal,
	infoPanelId,
	initialActionDropdownItems,
	initialCheckboxStatus,
	initialSelectAllButtonVisible,
	initialSelectedItems,
	onActionButtonClick = () => {},
	onCheckboxChange = () => {},
	onClearSelectionButtonClick = () => {},
	onCreateButtonClick = () => {},
	onCreationMenuItemClick = () => {},
	onInfoButtonClick = () => {},
	onFilterDropdownItemClick = () => {},
	onSelectAllButtonClick = () => {},
	onShowMoreButtonClick,
	searchActionURL,
	searchContainerId,
	searchData,
	searchFormMethod,
	searchFormName,
	searchInputAutoFocus,
	searchInputName,
	searchValue,
	selectAllURL,
	selectable,
	showCreationMenu,
	showInfoButton,
	showResultsBar,
	showSearch,
	sortingOrder,
	sortingURL,
	supportsBulkActions,
	viewTypeItems,
}) {
	const [actionDropdownItems, setActionDropdownItems] = useState(
		initialActionDropdownItems
	);
	const [active, setActive] = useState(initialCheckboxStatus !== 'unchecked');
	const [searchMobile, setSearchMobile] = useState(false);

	return (
		<>
			<ClayManagementToolbar active={active}>
				<ClayManagementToolbar.ItemList>
					{selectable && (
						<SelectionControls
							actionDropdownItems={actionDropdownItems}
							active={active}
							clearSelectionURL={clearSelectionURL}
							disabled={disabled}
							initialCheckboxStatus={initialCheckboxStatus}
							initialSelectAllButtonVisible={
								initialSelectAllButtonVisible
							}
							initialSelectedItems={initialSelectedItems}
							itemsTotal={itemsTotal}
							onCheckboxChange={onCheckboxChange}
							onClearButtonClick={onClearSelectionButtonClick}
							onSelectAllButtonClick={onSelectAllButtonClick}
							searchContainerId={searchContainerId}
							selectAllURL={selectAllURL}
							setActionDropdownItems={setActionDropdownItems}
							setActive={setActive}
							showCheckBoxLabel={
								!active &&
								!filterDropdownItems &&
								!sortingURL &&
								!showSearch
							}
							supportsBulkActions={supportsBulkActions}
						/>
					)}

					{!active && (
						<FilterOrderControls
							disabled={disabled}
							filterDropdownItems={filterDropdownItems}
							onFilterDropdownItemClick={
								onFilterDropdownItemClick
							}
							sortingOrder={sortingOrder}
							sortingURL={sortingURL}
						/>
					)}
				</ClayManagementToolbar.ItemList>
				{!active && showSearch && (
					<SearchControls
						disabled={disabled}
						searchActionURL={searchActionURL}
						searchData={searchData}
						searchFormMethod={searchFormMethod}
						searchFormName={searchFormName}
						searchInputAutoFocus={searchInputAutoFocus}
						searchInputName={searchInputName}
						searchMobile={searchMobile}
						searchValue={searchValue}
						setSearchMobile={setSearchMobile}
					/>
				)}
				<ClayManagementToolbar.ItemList>
					{!active && showSearch && (
						<SearchControls.ShowMobileButton
							disabled={disabled}
							setSearchMobile={setSearchMobile}
						/>
					)}
					{showInfoButton && (
						<InfoPanelControl
							infoPanelId={infoPanelId}
							onInfoButtonClick={onInfoButtonClick}
						/>
					)}
					{active ? (
						<>
							<ActionControls
								actionDropdownItems={actionDropdownItems}
								disabled={disabled}
								onActionButtonClick={onActionButtonClick}
							/>
						</>
					) : (
						<>
							{viewTypeItems && (
								<ClayManagementToolbar.Item>
									<ClayDropDownWithItems
										items={viewTypeItems}
										trigger={
											<ClayButtonWithIcon
												className="nav-link nav-link-monospaced"
												displayType="unstyled"
												symbol={
													viewTypeItems.find(
														(item) => item.active
													)?.icon || ''
												}
											/>
										}
									/>
								</ClayManagementToolbar.Item>
							)}

							{showCreationMenu && (
								<ClayManagementToolbar.Item>
									{creationMenu ? (
										<CreationMenu
											{...creationMenu}
											onCreateButtonClick={
												onCreateButtonClick
											}
											onCreationMenuItemClick={
												onCreationMenuItemClick
											}
											onShowMoreButtonClick={
												onShowMoreButtonClick
											}
										/>
									) : (
										<ClayButtonWithIcon
											className="nav-btn nav-btn-monospaced"
											displayType="primary"
											onClick={onCreateButtonClick}
											symbol="plus"
										/>
									)}
								</ClayManagementToolbar.Item>
							)}
						</>
					)}
				</ClayManagementToolbar.ItemList>
			</ClayManagementToolbar>

			{showResultsBar && (
				<ResultsBar
					clearResultsURL={clearResultsURL}
					filterLabelItems={filterLabelItems}
					itemsTotal={itemsTotal}
					searchValue={searchValue}
				/>
			)}
		</>
	);
}

ManagementToolbar.propTypes = {
	actionDropdownItems: PropTypes.arrayOf(
		PropTypes.shape({
			href: PropTypes.string,
			icon: PropTypes.string,
			label: PropTypes.string,
			quickAction: PropTypes.bool,
		})
	),
	clearResultsURL: PropTypes.string,
	clearSelectionURL: PropTypes.string,
	creationMenu: PropTypes.object,
	disabled: PropTypes.bool,
	filterDropdownItems: PropTypes.array,
	initialCheckboxStatus: PropTypes.oneOf([
		'checked',
		'indeterminate',
		'unchecked',
	]),
	itemsTotal: PropTypes.number,
	onCheckboxChange: PropTypes.func,
	onCreateButtonClick: PropTypes.func,
	onInfoButtonClick: PropTypes.func,
	onViewTypeSelect: PropTypes.func,
	searchActionURL: PropTypes.string,
	searchContainerId: PropTypes.string,
	searchData: PropTypes.object,
	searchFormMethod: PropTypes.string,
	searchFormName: PropTypes.string,
	searchInputName: PropTypes.string,
	searchValue: PropTypes.string,
	selectAllURL: PropTypes.string,
	selectable: PropTypes.bool,
	showCreationMenu: PropTypes.bool,
	showInfoButton: PropTypes.bool,
	showResultsBar: PropTypes.bool,
	showSearch: PropTypes.bool,
	showSelectAllButton: PropTypes.bool,
	sortingOrder: PropTypes.string,
	sortingURL: PropTypes.string,
	viewTypeItems: PropTypes.array,
};

export default ManagementToolbar;
