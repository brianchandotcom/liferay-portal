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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {Text} from '@clayui/core';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayTable from '@clayui/table';
import React, {useEffect, useState} from 'react';

import {TProperty} from './PropertiesTable';

export type TData = {
	channelName?: string;
	friendlyURL?: string;
	id: string;
	name: string;
	siteName: string;
}[];
interface ITabsTemplate {
	channelTab?: boolean;
	checked?: boolean;
	displayChannels?: boolean;
	handleCheckboxChange: Function;
	handleSelectAll: Function;
	items: TData;
	property: TProperty;
	selectedAllDisabled?: boolean;
	siteTab?: boolean;
}

const TabsTemplate: React.FC<ITabsTemplate> = ({
	channelTab,
	checked: initialChecked = false,
	displayChannels,
	handleCheckboxChange,
	handleSelectAll,
	items,
	property,
	selectedAllDisabled,
	siteTab,
}) => {
	const [searchMobile, setSearchMobile] = useState(false);
	const [checked, setChecked] = useState(initialChecked);
	const [delta, setDelta] = useState(5);

	const filterItems = [
		{
			label: Liferay.Language.get('channel-name'),
			onClick: () => alert('Filter clicked'),
		},
		{
			label: Liferay.Language.get('related-site'),
			onClick: () => alert('Filter clicked'),
		},
		{
			label: Liferay.Language.get('assigned-property'),
			onClick: () => alert('Filter clicked'),
		},
	];

	useEffect(() => {
		setChecked(initialChecked);
	}, [initialChecked]);

	return (
		<>
			<div className="mt-3">
				<Text as="p" color="secondary" size={3}>
					{channelTab &&
						Liferay.Language.get('channels-tab-description')}

					{siteTab && Liferay.Language.get('sites-tab-description')}
				</Text>
			</div>

			<ClayManagementToolbar>
				<ClayManagementToolbar.ItemList>
					<ClayManagementToolbar.Item>
						<ClayCheckbox
							checked={checked}
							disabled={selectedAllDisabled}
							onChange={() => {
								handleSelectAll(!checked);
								setChecked(!checked);
							}}
						/>
					</ClayManagementToolbar.Item>

					<ClayDropDownWithItems
						items={filterItems}
						trigger={
							<ClayButton
								className="nav-link"
								displayType="unstyled"
							>
								<span className="navbar-breakpoint-down-d-none">
									<span className="navbar-text-truncate">
										{Liferay.Language.get(
											'filter-and-order'
										)}
									</span>

									<ClayIcon
										className="inline-item inline-item-after"
										symbol="caret-bottom"
									/>
								</span>

								<span className="navbar-breakpoint-d-none">
									<ClayIcon symbol="filter" />
								</span>
							</ClayButton>
						}
					/>

					<ClayManagementToolbar.Item>
						<ClayButton
							className="nav-link nav-link-monospaced"
							displayType="unstyled"
							onClick={() => {}}
						>
							<ClayIcon symbol="order-list-up" />
						</ClayButton>
					</ClayManagementToolbar.Item>
				</ClayManagementToolbar.ItemList>

				{/* // TODO: update this component with function to handle the search component (filter results). 
				// The function will be created on another story (LRAC-12019) */}

				<ClayManagementToolbar.Search showMobile={searchMobile}>
					<ClayInput.Group>
						<ClayInput.GroupItem>
							<ClayInput
								aria-label="Search"
								className="form-control input-group-inset input-group-inset-after"
								placeholder="Search"
								type="text"
							/>

							<ClayInput.GroupInsetItem after tag="span">
								<ClayButtonWithIcon
									className="navbar-breakpoint-d-none"
									displayType="unstyled"
									onClick={() => setSearchMobile(false)}
									symbol="times"
								/>

								<ClayButtonWithIcon
									displayType="unstyled"
									symbol="search"
									type="submit"
								/>
							</ClayInput.GroupInsetItem>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayManagementToolbar.Search>

				<ClayManagementToolbar.ItemList>
					<ClayManagementToolbar.Item className="navbar-breakpoint-d-none">
						<ClayButton
							className="nav-link nav-link-monospaced"
							displayType="unstyled"
							onClick={() => setSearchMobile(true)}
						>
							<ClayIcon symbol="search" />
						</ClayButton>
					</ClayManagementToolbar.Item>
				</ClayManagementToolbar.ItemList>
			</ClayManagementToolbar>

			<ClayTable hover={displayChannels}>
				<ClayTable.Head>
					<ClayTable.Row>
						<ClayTable.Cell></ClayTable.Cell>

						<ClayTable.Cell expanded headingCell>
							{channelTab && Liferay.Language.get('channel-name')}

							{siteTab && Liferay.Language.get('site-name')}
						</ClayTable.Cell>

						<ClayTable.Cell expanded headingCell>
							{channelTab && Liferay.Language.get('related-site')}

							{siteTab && Liferay.Language.get('friendly-url')}
						</ClayTable.Cell>

						<ClayTable.Cell expanded headingCell>
							{Liferay.Language.get('assigned-property')}
						</ClayTable.Cell>
					</ClayTable.Row>
				</ClayTable.Head>

				<ClayTable.Body>
					{items &&
						items.map((item, index) => {
							const disabledRow =
								(item.channelName &&
									item.channelName !== property.name) ||
								!displayChannels;

							return (
								<ClayTable.Row
									className={disabledRow ? 'text-muted' : ''}
									key={item.id}
								>
									<ClayTable.Cell>
										<ClayCheckbox
											checked={!!item.channelName}
											disabled={disabledRow}
											id={item.id}
											onChange={() =>
												handleCheckboxChange(index)
											}
										/>
									</ClayTable.Cell>

									<ClayTable.Cell>{item.name}</ClayTable.Cell>

									<ClayTable.Cell>
										{channelTab && item.siteName}

										{siteTab && item.friendlyURL}
									</ClayTable.Cell>

									<ClayTable.Cell>
										{item?.channelName}
									</ClayTable.Cell>
								</ClayTable.Row>
							);
						})}
				</ClayTable.Body>
			</ClayTable>

			{/* // TODO: update this component with function to handle the pagination component. 
			// The function will be created on another story (LRAC-12019) */}

			<ClayPaginationBarWithBasicItems
				activeDelta={delta}
				defaultActive={1}
				deltas={[4, 8, 20, 40, 60].map((size) => ({
					label: size,
				}))}
				onDeltaChange={setDelta}
				totalItems={10}
			/>
		</>
	);
};

export default TabsTemplate;
