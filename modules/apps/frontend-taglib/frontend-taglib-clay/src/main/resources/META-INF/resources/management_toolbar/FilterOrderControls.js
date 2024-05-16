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
import React from 'react';

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
	const showOrderToggle = orderDropdownItems && orderDropdownItems.length > 1;

	const sortingDirectionOptions = [
		{type: 'divider'},
		{
			active: sortingOrder === 'asc',
			href: sortingOrder === 'asc' ? null : sortingURL,
			label: Liferay.Language.get('ascending'),
			type: 'item',
		},
		{
			active: sortingOrder === 'desc',
			href: sortingOrder === 'desc' ? null : sortingURL,
			label: Liferay.Language.get('descending'),
			type: 'item',
		},
	];

	const itemMapper = (item) => {
		if (!item.items) {
			return {
				...item,
				onClick: (event) =>
					onFilterDropdownItemClick(event, {
						item,
					}),
			};
		}

		return {
			...item,
			items: item.items.map(itemMapper),
		};
	};

	return (
		<>
			{Boolean(filterDropdownItems?.length) && (
				<ManagementToolbar.Item>
					<ClayDropDownWithItems
						items={addActiveIcons(
							filterDropdownItems.map(itemMapper)
						)}
						trigger={
							<ClayButton
								aria-label={Liferay.Language.get('filter')}
								className="ml-2 mr-2 nav-link"
								disabled={disabled}
								displayType="unstyled"
							>
								<span className="navbar-breakpoint-down-d-none">
									<span className="inline-item inline-item-before">
										<ClayIcon symbol="filter" />
									</span>

									<span className="navbar-text-truncate">
										{Liferay.Language.get('filter')}
									</span>

									<ClayIcon
										className="inline-item inline-item-after"
										symbol="caret-bottom"
									/>
								</span>

								<span
									className="navbar-breakpoint-d-none"
									title={Liferay.Language.get(
										'show-filter-options'
									)}
								>
									<ClayIcon symbol="filter" />
								</span>
							</ClayButton>
						}
					/>
				</ManagementToolbar.Item>
			)}

			{showOrderToggle && (
				<ManagementToolbar.Item>
					<ClayDropDownWithItems
						items={addActiveIcons([
							...orderDropdownItems
								.map((item) => {
									return {
										...item,
										onClick: (event) => {
											onOrderDropdownItemClick(event, {
												item,
											});
										},
									};
								})
								.concat(
									sortingOrder ? sortingDirectionOptions : []
								),
						])}
						trigger={
							<ClayButton
								aria-label={Liferay.Language.get('order[sort]')}
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

			{sortingURL && !showOrderToggle && (
				<ManagementToolbar.Item>
					<LinkOrButton
						aria-label={sub(
							Liferay.Language.get(
								'reverse-order-direction-currently-x'
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
						title={Liferay.Language.get('reverse-order-direction')}
					/>
				</ManagementToolbar.Item>
			)}
		</>
	);
};

function addActiveIcons(itemList) {
	return itemList.map((item) => ({
		...item,
		items: item.items ? addActiveIcons(item.items) : undefined,
		symbolLeft: item.active ? 'check' : item.symbolLeft,
	}));
}

export default FilterOrderControls;
