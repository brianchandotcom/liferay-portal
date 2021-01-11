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
import ClayLink from '@clayui/link';
import ClayManagementToolbar from '@clayui/management-toolbar';
import React from 'react';

const FilterOrderControls = ({disabled, filterDropdownItems, sortingURL}) => {
	return (
		<>
			{filterDropdownItems && (
				<ClayManagementToolbar.Item>
					<ClayDropDownWithItems
						items={filterDropdownItems}
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
					<ClayLink
						className="nav-link nav-link-monospaced"
						displayType="unstyled"
						href={sortingURL}
						title={Liferay.Language.get('reverse-sort-direction')}
					>
						<ClayIcon symbol={'order-arrow'} />
					</ClayLink>
				</ClayManagementToolbar.Item>
			)}
		</>
	);
};

export default FilterOrderControls;
