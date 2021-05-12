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
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
import classnames from 'classnames';
import React from 'react';

import LinkOrButton from './LinkOrButton';

const FilterOrderControls = ({
	disabled,
	filterDropdownItems,
	onFilterDropdownItemClick,
	sortingOrder,
	sortingURL,
}) => {
	return (
		<>
			{filterDropdownItems && (
				<ClayManagementToolbar.Item>
					<ClayDropDownWithItems
						items={filterDropdownItems.map((item) => {
							return {
								...item,
								items: item.items.map((childItem) => {
									return {
										...childItem,
										onClick(event) {
											onFilterDropdownItemClick(event, {
												item: childItem,
											});
										},
									};
								}),
							};
						})}
						trigger={
							<ClayButton
								className="nav-link"
								disabled={disabled}
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
				</ClayManagementToolbar.Item>
			)}

			{sortingURL && (
				<ClayManagementToolbar.Item>
					<LinkOrButton
						className="nav-link nav-link-monospaced"
						disabled={disabled}
						displayType="unstyled"
						href={sortingURL}
						symbol={classnames({
							'order-list-down': sortingOrder === 'desc',
							'order-list-up':
								sortingOrder === 'asc' || sortingOrder === null,
						})}
						title={Liferay.Language.get('reverse-sort-direction')}
					/>
				</ClayManagementToolbar.Item>
			)}
		</>
	);
};

export default FilterOrderControls;
