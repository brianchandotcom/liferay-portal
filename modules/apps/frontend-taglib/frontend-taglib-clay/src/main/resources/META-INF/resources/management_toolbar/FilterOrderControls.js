/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {ManagementToolbar} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useContext} from 'react';

import FeatureFlagContext from './FeatureFlagContext';
import LinkOrButton from './LinkOrButton';

const FilterOrderControls = ({
	disabled,
	filterDropdownItems,
	onFilterDropdownItemClick,
	onOrderDropdownItemClick,
	orderDropdownItems = [],
	sortingOrder,
	sortingURL,
}) => {
	const {showDesignImprovements} = useContext(FeatureFlagContext);

	const showOrderToggle =
		!orderDropdownItems || orderDropdownItems.length <= 1;

	return (
		<>
			{filterDropdownItems && (
				<ManagementToolbar.Item>
					<ClayDropDownWithItems
						items={filterDropdownItems.map((item) =>
							item.items
								? {
										...item,
										items: item.items.map((childItem) => {
											return {
												...childItem,
												onClick(event) {
													onFilterDropdownItemClick(
														event,
														{
															item: childItem,
														}
													);
												},
											};
										}),
								  }
								: {
										...item,
										onClick: (event) =>
											onFilterDropdownItemClick(event, {
												item,
											}),
								  }
						)}
						trigger={
							<ClayButton
								aria-label={Liferay.Language.get(
									'filter-and-order'
								)}
								className={classNames('nav-link', {
									'ml-2 mr-2': showDesignImprovements,
								})}
								disabled={disabled}
								displayType="unstyled"
							>
								<span className="navbar-breakpoint-down-d-none">
									{showDesignImprovements && (
										<span className="inline-item inline-item-before">
											<ClayIcon symbol="filter" />
										</span>
									)}

									<span className="navbar-text-truncate">
										{showDesignImprovements
											? Liferay.Language.get('filter')
											: Liferay.Language.get(
													'filter-and-order'
											  )}
									</span>

									<ClayIcon
										className="inline-item inline-item-after"
										symbol="caret-bottom"
									/>
								</span>

								<span
									className="navbar-breakpoint-d-none"
									title={
										showDesignImprovements
											? Liferay.Language.get(
													'show-filter-options'
											  )
											: undefined
									}
								>
									<ClayIcon symbol="filter" />
								</span>
							</ClayButton>
						}
					/>
				</ManagementToolbar.Item>
			)}

			{showDesignImprovements && !showOrderToggle && (
				<ManagementToolbar.Item>
					<ClayDropDownWithItems
						items={[
							...orderDropdownItems.map((item) => {
								return {
									...item,
									onClick: (event) => {
										onOrderDropdownItemClick(event, {
											item,
										});
									},
								};
							}),
							{type: 'divider'},
							{
								active: sortingOrder === 'asc',
								href:
									sortingOrder === 'asc' ? null : sortingURL,
								label: Liferay.Language.get('ascending'),
								type: 'item',
							},
							{
								active: sortingOrder !== 'asc',
								href:
									sortingOrder !== 'asc' ? null : sortingURL,
								label: Liferay.Language.get('descending'),
								type: 'item',
							},
						]}
						trigger={
							<ClayButton
								className="ml-2 mr-2 nav-link"
								disabled={disabled}
								displayType="unstyled"
							>
								<span className="navbar-breakpoint-down-d-none">
									<span className="inline-item inline-item-before">
										<ClayIcon
											symbol={
												sortingOrder === 'desc'
													? 'order-list-down'
													: 'order-list-up'
											}
										/>
									</span>

									<span className="navbar-text-truncate">
										{Liferay.Language.get('order[sort]')}
									</span>

									<ClayIcon
										className="inline-item inline-item-after"
										symbol="caret-bottom"
									/>
								</span>

								<span
									className="navbar-breakpoint-d-none"
									title={Liferay.Language.get(
										'show-order-options'
									)}
								>
									<ClayIcon
										symbol={
											sortingOrder === 'desc'
												? 'order-list-down'
												: 'order-list-up'
										}
									/>
								</span>
							</ClayButton>
						}
					/>
				</ManagementToolbar.Item>
			)}

			{((!showDesignImprovements && sortingURL) ||
				(showDesignImprovements && sortingURL && showOrderToggle)) && (
				<ManagementToolbar.Item>
					<LinkOrButton
						aria-label={sub(
							showDesignImprovements
								? Liferay.Language.get(
										'reverse-order-direction-currently-x'
								  )
								: Liferay.Language.get(
										'reverse-sort-direction-currently-x'
								  ),
							sortingOrder === 'desc'
								? Liferay.Language.get('descending')
								: Liferay.Language.get('ascending')
						)}
						className="nav-link nav-link-monospaced"
						disabled={disabled}
						displayType="unstyled"
						href={sortingURL}
						role="button"
						symbol={classNames({
							'order-list-down': sortingOrder === 'desc',
							'order-list-up':
								sortingOrder === 'asc' || sortingOrder === null,
						})}
						title={
							showDesignImprovements
								? Liferay.Language.get(
										'reverse-order-direction'
								  )
								: Liferay.Language.get('reverse-sort-direction')
						}
					/>
				</ManagementToolbar.Item>
			)}
		</>
	);
};

export default FilterOrderControls;
