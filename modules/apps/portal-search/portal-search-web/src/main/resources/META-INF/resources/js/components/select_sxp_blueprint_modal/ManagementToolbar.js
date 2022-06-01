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
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ManagementToolbar as FrontendManagementToolbar} from 'frontend-js-components-web';
import React, {useState} from 'react';

const ManagementToolbar = ({
	filterItems,
	loading,
	onChangeSortOrder,
	onSearch,
	searchValue,
	sortOrder,
	totalCount,
}) => {
	const [searchInputValue, setSearchInputValue] = useState(searchValue);
	const [searchMobile, setSearchMobile] = useState(false);

	return (
		<>
			<FrontendManagementToolbar.Container>
				<FrontendManagementToolbar.ItemList>
					<ClayDropDownWithItems
						items={filterItems}
						trigger={
							<ClayButton
								className="nav-link"
								disabled={loading}
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

					<FrontendManagementToolbar.Item>
						<ClayButton
							className="nav-link nav-link-monospaced"
							disabled={loading}
							displayType="unstyled"
							onClick={onChangeSortOrder}
						>
							{sortOrder === 'asc' ? (
								<ClayIcon symbol="order-list-up" />
							) : (
								<ClayIcon symbol="order-list-down" />
							)}
						</ClayButton>
					</FrontendManagementToolbar.Item>
				</FrontendManagementToolbar.ItemList>

				<FrontendManagementToolbar.Search showMobile={searchMobile}>
					<ClayInput.Group>
						<ClayInput.GroupItem>
							<ClayInput
								aria-label={Liferay.Language.get('search')}
								className="form-control input-group-inset input-group-inset-after"
								disabled={loading}
								onChange={(event) => {
									setSearchInputValue(event.target.value);
								}}
								onKeyDown={(event) => {
									if (event.key === 'Enter') {
										event.preventDefault();

										onSearch(searchInputValue);
									}
								}}
								placeholder={Liferay.Language.get('search')}
								type="text"
								value={searchInputValue}
							/>

							<ClayInput.GroupInsetItem after tag="span">
								<ClayButtonWithIcon
									className="navbar-breakpoint-d-none"
									displayType="unstyled"
									onClick={() => setSearchMobile(false)}
									symbol="times"
								/>

								<ClayButtonWithIcon
									disabled={loading}
									displayType="unstyled"
									onClick={() => onSearch(searchInputValue)}
									symbol="search"
								/>
							</ClayInput.GroupInsetItem>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</FrontendManagementToolbar.Search>

				<FrontendManagementToolbar.ItemList>
					<FrontendManagementToolbar.Item className="navbar-breakpoint-d-none">
						<ClayButton
							className="nav-link nav-link-monospaced"
							displayType="unstyled"
							onClick={() => setSearchMobile(true)}
						>
							<ClayIcon symbol="search" />
						</ClayButton>
					</FrontendManagementToolbar.Item>
				</FrontendManagementToolbar.ItemList>
			</FrontendManagementToolbar.Container>

			{!!searchValue && !loading && (
				<FrontendManagementToolbar.ResultsBar>
					<FrontendManagementToolbar.ResultsBarItem>
						<span className="component-text text-truncate-inline">
							<span className="text-truncate">
								{Liferay.Util.sub(
									totalCount === 1
										? Liferay.Language.get('x-result-for-x')
										: Liferay.Language.get(
												'x-results-for-x'
										  ),
									[totalCount, searchValue]
								)}
							</span>
						</span>
					</FrontendManagementToolbar.ResultsBarItem>

					<FrontendManagementToolbar.ResultsBarItem>
						<ClayButton
							className="component-link tbar-link"
							displayType="unstyled"
							onClick={() => {
								onSearch('');
								setSearchInputValue('');
							}}
						>
							{Liferay.Language.get('clear')}
						</ClayButton>
					</FrontendManagementToolbar.ResultsBarItem>
				</FrontendManagementToolbar.ResultsBar>
			)}
		</>
	);
};

export default ManagementToolbar;
