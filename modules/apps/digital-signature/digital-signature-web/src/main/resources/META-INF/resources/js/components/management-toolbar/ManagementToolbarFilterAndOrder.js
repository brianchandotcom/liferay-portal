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
import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
import classNames from 'classnames';
import React, {useContext, useState} from 'react';

import DropDown, {CheckboxGroup, ItemsGroup, RadioGroup} from './DropDown';
import SearchContext from './SearchContext';

const getSortable = (columns, sort = '', defaultSort) => {
	if (sort.length) {
		const [column, order] = sort.split(':');

		return {asc: order === 'asc', column};
	}
	else if (columns.length) {
		const {asc = defaultSort === 'asc', key: column} =
			columns.find(({asc}) => asc !== undefined) || columns[0];

		return {asc, column};
	}

	return {};
};

export default ({columns = [], disabled, filters = []}) => {
	const [
		{filters: appliedFilters = {}, defaultSort, sort},
		dispatch,
	] = useContext(SearchContext);
	const [localFilters, setLocalFilters] = useState(appliedFilters);
	const [isDropDownActive, setDropDownActive] = useState(false);

	const sortableColumns = columns.filter(({sortable}) => sortable);

	const {asc, column} = getSortable(sortableColumns, sort, defaultSort);
	const [sortColumn, setSortColumn] = useState(column);

	const filterItems = filters.map(
		({defaultText, items, key, multiple, name}) => {
			const props = {
				checked: localFilters[key],
				items,
				label: name,
			};

			if (multiple) {
				return (
					<CheckboxGroup
						{...props}
						onAdd={(value) => {
							setLocalFilters((prevFilters) => {
								const values = prevFilters[key] || [];

								return {
									...prevFilters,
									[key]: values.concat(value),
								};
							});
						}}
						onRemove={(value) => {
							setLocalFilters((prevFilters) => ({
								...prevFilters,
								[key]: prevFilters[key].filter(
									(currentValue) => currentValue !== value
								),
							}));
						}}
					/>
				);
			}
			else {
				return (
					<RadioGroup
						{...props}
						items={[
							{
								label:
									defaultText || Liferay.Language.get('any'),
							},
							...props.items,
						]}
						onChange={(value) => {
							setLocalFilters((prevFilters) => ({
								...prevFilters,
								[key]: value,
							}));
						}}
					/>
				);
			}
		}
	);

	const enableDoneButton = filterItems.length > 0;

	const onSortButtonClick = (asc, newColumn) => {
		dispatch({
			sort: `${newColumn}:${asc ? 'asc' : 'desc'}`,
			type: 'SORT',
		});
	};

	const orderByItems = () => {
		if (sortableColumns.length === 0) {
			return [];
		}

		const props = {
			checked: sortColumn,
			items: sortableColumns.map(({key, value}) => ({
				label: value,
				value: key,
			})),
			label: Liferay.Language.get('order-by'),
		};

		let item = <RadioGroup {...props} onChange={setSortColumn} />;

		if (!enableDoneButton) {
			item = (
				<ItemsGroup
					{...props}
					onClick={(newColumn) => {
						setSortColumn(newColumn);
						onSortButtonClick(asc, newColumn);
						setDropDownActive(false);
					}}
				/>
			);
		}

		return [item];
	};

	const dropDownItems = [...filterItems, ...orderByItems()];

	const onDropDownActiveChange = (active) => {
		setDropDownActive(active);
		setLocalFilters(appliedFilters);
		setSortColumn(column);
	};

	const onDoneButtonClick = () => {
		dispatch({
			filters: localFilters,
			sort: `${sortColumn}:${asc ? 'asc' : 'desc'}`,
			type: 'UPDATE_FILTERS_AND_SORT',
		});

		setDropDownActive(false);
	};

	return (
		<>
			{dropDownItems.length > 0 && (
				<ClayManagementToolbar.ItemList>
					<ClayManagementToolbar.Item>
						<DropDown
							active={isDropDownActive}
							footerContent={
								enableDoneButton && (
									<ClayButton
										block
										onClick={onDoneButtonClick}
									>
										{Liferay.Language.get('done')}
									</ClayButton>
								)
							}
							onActiveChange={onDropDownActiveChange}
							trigger={
								<ClayButton
									className="nav-link"
									disabled={disabled}
									displayType="unstyled"
									id="filter-and-order"
								>
									<span className="navbar-breakpoint-down-d-none">
										{Liferay.Language.get(
											'filter-and-order'
										)}

										<ClayIcon
											className="inline-item inline-item-after"
											symbol="caret-bottom"
										/>
									</span>
									<span className="navbar-breakpoint-d-none">
										<ClayIcon
											className="inline-item inline-item-after"
											symbol="filter"
										/>
									</span>
								</ClayButton>
							}
						>
							{dropDownItems.map((item, index) => (
								<div key={index}>{item}</div>
							))}
						</DropDown>
					</ClayManagementToolbar.Item>

					<ClayManagementToolbar.Item>
						<ClayButtonWithIcon
							className={classNames(
								'nav-link',
								'nav-link-monospaced',
								{
									'order-arrow-down-active': !asc,
									'order-arrow-up-active': asc,
								}
							)}
							disabled={disabled}
							displayType="unstyled"
							onClick={() => onSortButtonClick(!asc, column)}
							symbol="order-arrow"
							title={Liferay.Language.get(
								'reverse-sort-direction'
							)}
						/>
					</ClayManagementToolbar.Item>
				</ClayManagementToolbar.ItemList>
			)}
		</>
	);
};
