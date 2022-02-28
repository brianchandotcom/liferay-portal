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
import {ClayDropDownWithItems} from '@clayui/drop-down';
import {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import {ManagementToolbar} from 'frontend-js-components-web';
import React, {useState} from 'react';

const filterItems = [
	{label: 'Filter Action 1', onClick: () => alert('Filter clicked')},
	{label: 'Filter Action 2', onClick: () => alert('Filter clicked')},
];

const viewTypes = [
	{
		label: 'List',
		onClick: () => alert('Show view list'),
		symbolLeft: 'list',
	},
	{
		active: true,
		label: 'Table',
		onClick: () => alert('Show view talbe'),
		symbolLeft: 'table',
	},
	{
		label: 'Card',
		onClick: () => alert('Show view card'),
		symbolLeft: 'cards2',
	},
];

export default function ManagementToolbarSamples() {
	const [searchMobile, setSearchMobile] = useState(false);
	const viewTypeActive = viewTypes.find((type) => type.active);

	return (
		<>
			<ManagementToolbar.Container aria-label="Management toolbar">
				<ManagementToolbar.ItemList>
					<ManagementToolbar.Item>
						<ClayCheckbox checked={false} onChange={() => {}} />
					</ManagementToolbar.Item>

					<ManagementToolbar.Item>
						<ClayDropDownWithItems
							items={filterItems}
							trigger={
								<ClayButton
									aria-label="Filter items"
									className="nav-link"
									displayType="link"
								>
									<span className="navbar-breakpoint-down-d-none">
										<span className="navbar-text-truncate">
											Filter and Order
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
					</ManagementToolbar.Item>

					<ManagementToolbar.Item>
						<ClayButton
							aria-label="Order items"
							className="nav-link nav-link-monospaced"
							displayType="link"
							onClick={() => {}}
						>
							<ClayIcon symbol="order-list-up" />
						</ClayButton>
					</ManagementToolbar.Item>
				</ManagementToolbar.ItemList>

				<ManagementToolbar.Search showMobile={searchMobile}>
					<ClayInput.Group>
						<ClayInput.GroupItem>
							<ClayInput
								aria-label="Search"
								className="form-control input-group-inset input-group-inset-after"
								defaultValue="Red"
								type="text"
							/>

							<ClayInput.GroupInsetItem after tag="span">
								<ClayButtonWithIcon
									aria-label="Clear search button"
									className="navbar-breakpoint-d-none"
									displayType="unstyled"
									onClick={() => setSearchMobile(false)}
									symbol="times"
								/>

								<ClayButtonWithIcon
									aria-label="Search button"
									displayType="unstyled"
									symbol="search"
									type="submit"
								/>
							</ClayInput.GroupInsetItem>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ManagementToolbar.Search>

				<ManagementToolbar.ItemList>
					<ManagementToolbar.Item className="navbar-breakpoint-d-none">
						<ClayButton
							aria-label="Search button mobile"
							className="nav-link nav-link-monospaced"
							displayType="unstyled"
							onClick={() => setSearchMobile(true)}
						>
							<ClayIcon symbol="search" />
						</ClayButton>
					</ManagementToolbar.Item>

					<ManagementToolbar.Item>
						<ClayButton
							aria-label="Information"
							className="nav-link nav-link-monospaced"
							displayType="link"
							onClick={() => {}}
						>
							<ClayIcon symbol="info-circle-open" />
						</ClayButton>
					</ManagementToolbar.Item>

					<ClayDropDownWithItems
						containerElement={ManagementToolbar.Item}
						items={viewTypes}
						trigger={
							<ClayButton
								aria-label="Display type"
								className="nav-link nav-link-monospaced"
								displayType="link"
							>
								<ClayIcon
									symbol={
										viewTypeActive
											? viewTypeActive.symbolLeft
											: ''
									}
								/>
							</ClayButton>
						}
					/>

					<ManagementToolbar.Item>
						<ClayButtonWithIcon
							aria-label="Add new"
							className="nav-btn nav-btn-monospaced"
							symbol="plus"
						/>
					</ManagementToolbar.Item>
				</ManagementToolbar.ItemList>
			</ManagementToolbar.Container>

			<ManagementToolbar.ResultsBar>
				<ManagementToolbar.ResultsBarItem>
					<span className="component-text text-truncate-inline">
						<span className="text-truncate">
							2 results for <strong>Red</strong>
						</span>
					</span>
				</ManagementToolbar.ResultsBarItem>

				<ManagementToolbar.ResultsBarItem expand>
					<ClayLabel
						className="component-label tbar-label"
						displayType="unstyled"
					>
						Filter
					</ClayLabel>
				</ManagementToolbar.ResultsBarItem>

				<ManagementToolbar.ResultsBarItem>
					<ClayButton
						className="component-link tbar-link"
						displayType="link"
					>
						Clear
					</ClayButton>
				</ManagementToolbar.ResultsBarItem>
			</ManagementToolbar.ResultsBar>
		</>
	);
}
