/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import {useFilter} from '../../hooks/useFilter.es';
import {useRouter} from '../../hooks/useRouter.es';
import {FilterItem} from './FilterItem.es';
import {FilterSearch} from './FilterSearch.es';
import {
	addClickOutsideListener,
	handleClickOutside,
	removeClickOutsideListener,
} from './util/filterEvents.es';
import {
	getCapitalizedFilterKey,
	getSelectedItemsQuery,
	replaceHistory,
} from './util/filterUtil.es';

const Filter = ({
	buttonClassName = 'btn-secondary btn-sm',
	children,
	defaultItem,
	disabled,
	elementClasses,
	filterKey,
	hideControl = false,
	items,
	labelPropertyName = 'name',
	multiple = true,
	name,
	onClickFilter,
	position = 'left',
	prefixKey = '',
	preventClick,
	show = true,
	withoutRouteParams,
}) => {
	const {dispatchFilter} = useFilter({withoutRouteParams});
	const [expanded, setExpanded] = useState(false);
	const [filteredItems, setFilteredItems] = useState([]);
	const [searchTerm, setSearchTerm] = useState('');
	const [changed, setChanged] = useState(false);

	const prefixedFilterKey = getCapitalizedFilterKey(prefixKey, filterKey);
	const routerProps = useRouter();
	const wrapperRef = useRef();

	const classes = {
		children: getCN(
			'custom dropdown-menu',
			children && 'show',
			position && `dropdown-menu-${position}`
		),
		custom: getCN('btn dropdown-toggle nav-link', buttonClassName),
		dropdown: getCN('dropdown nav-item', elementClasses),
		menu: getCN(
			'dropdown-menu',
			expanded && 'show',
			position && `dropdown-menu-${position}`
		),
	};

	const getSelectedItems = (items) => items.filter((item) => item.active);

	const applyFilterChanges = useCallback(() => {
		if (!withoutRouteParams) {
			const query = getSelectedItemsQuery(
				items,
				prefixedFilterKey,
				routerProps.location.search
			);

			replaceHistory(query, routerProps);
		}
		else {
			dispatchFilter(prefixedFilterKey, getSelectedItems(items));
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [items, routerProps]);

	const closeDropdown = () => {
		setExpanded(false);
		setSearchTerm('');
	};

	const onClick = (item) => (onClickFilter ? onClickFilter(item) : true);

	const onSelect = useCallback(
		(item) => {
			if (!preventClick) {
				if (!multiple) {
					items.forEach((item) => {
						item.active = false;
					});
				}

				item.active = !item.active;

				if (!multiple) {
					applyFilterChanges();
					closeDropdown();
				}
				else {
					setChanged(true);
				}
			}
			else {
				onClick(item);
			}
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[applyFilterChanges, items]
	);

	const selectDefaultItem = useCallback(() => {
		if (defaultItem && !multiple) {
			const selectedItems = getSelectedItems(items);

			if (!selectedItems.length) {
				const index = items.findIndex(
					(item) => item.key === defaultItem.key
				);

				items[index].active = true;

				if (!preventClick) {
					applyFilterChanges();
				}
				else {
					onClick(items[index]);
				}
			}
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [applyFilterChanges, defaultItem, items]);

	useEffect(() => {
		selectDefaultItem();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [defaultItem]);

	useEffect(() => {
		const callback = handleClickOutside(() => {
			if (expanded) {
				closeDropdown();

				if (changed) {
					setChanged(false);
					applyFilterChanges();
				}
			}
		}, wrapperRef.current);

		addClickOutsideListener(callback);

		return () => {
			removeClickOutsideListener(callback);
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [applyFilterChanges, expanded, changed]);

	useEffect(() => {
		setFilteredItems(
			searchTerm
				? items.filter((item) =>
						item[labelPropertyName]
							.toLowerCase()
							.includes(searchTerm.toLowerCase())
				  )
				: items
		);
	}, [items, labelPropertyName, searchTerm]);

	return (
		show && (
			<li className={classes.dropdown} ref={wrapperRef}>
				<button
					className={classes.custom}
					disabled={disabled}
					onClick={() => {
						setExpanded(!expanded);
					}}
				>
					<span className="mr-2 navbar-text-truncate">{name}</span>

					<ClayIcon symbol="caret-bottom" />
				</button>

				<div className={classes.menu} role="menu">
					<FilterSearch
						filteredItems={filteredItems}
						onChange={({target}) => {
							setSearchTerm(target.value);
						}}
						searchTerm={searchTerm}
						totalCount={items.length}
					>
						<ul className="list-unstyled">
							{filteredItems.map((item, index) => (
								<FilterItem
									{...item}
									hideControl={hideControl}
									key={index}
									labelPropertyName={labelPropertyName}
									multiple={multiple}
									onClick={() => onSelect(item)}
									preventClick={preventClick}
								/>
							))}
						</ul>
					</FilterSearch>
				</div>

				<div className={classes.children}>{children}</div>
			</li>
		)
	);
};
export default Filter;
