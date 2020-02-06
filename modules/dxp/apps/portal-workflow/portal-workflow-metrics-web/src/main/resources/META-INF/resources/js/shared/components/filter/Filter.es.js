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

import getClassName from 'classnames';
import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';

import {useFilter} from '../../hooks/useFilter.es';
import {useRouter} from '../../hooks/useRouter.es';
import Icon from '../Icon.es';
import {FilterItem} from './FilterItem.es';
import {FilterSearch} from './FilterSearch.es';
import {
	addClickOutsideListener,
	handleClickOutside,
	removeClickOutsideListener
} from './util/filterEvents.es';
import {
	getCapitalizedFilterKey,
	getSelectedItemsQuery,
	replaceHistory
} from './util/filterUtil.es';

const Filter = ({
	buttonClassName = 'btn-secondary btn-sm',
	children,
	dataTestId = 'filterComponent',
	defaultItem,
	disabled,
	elementClasses,
	filterKey,
	hideControl = false,
	items,
	multiple = true,
	name,
	onChangeFilter,
	onClickFilter,
	position = 'left',
	prefixKey = '',
	style,
	withoutRouteParams
}) => {
	const {dispatchFilter} = useFilter({withoutRouteParams});
	const [expanded, setExpanded] = useState(false);
	const [searchTerm, setSearchTerm] = useState('');
	const [changed, setChanged] = useState(false);

	const prefixedFilterKey = getCapitalizedFilterKey(prefixKey, filterKey);

	const routerProps = useRouter();

	const wrapperRef = useRef();

	const classes = useMemo(
		() => ({
			children: getClassName(
				'custom',
				'dropdown-menu',
				children && 'show',
				position && `dropdown-menu-${position}`
			),
			custom: getClassName(
				'btn',
				'dropdown-toggle',
				'nav-link',
				buttonClassName
			),
			dropdown: getClassName('dropdown', 'nav-item', elementClasses),
			menu: getClassName(
				'dropdown-menu',
				expanded && 'show',
				position && `dropdown-menu-${position}`
			)
		}),
		[buttonClassName, children, elementClasses, expanded, position]
	);

	const filteredItems = useMemo(() => {
		return searchTerm
			? items.filter(item =>
					item.name.toLowerCase().includes(searchTerm.toLowerCase())
			  )
			: items;
	}, [items, searchTerm]);

	const applyFilterChanges = useCallback(() => {
		dispatchFilter(prefixedFilterKey, getSelectedItems(items));

		if (!withoutRouteParams) {
			const query = getSelectedItemsQuery(
				items,
				prefixedFilterKey,
				routerProps.location.search
			);

			replaceHistory(query, routerProps);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [items, routerProps.location.search]);

	const closeDropdown = () => {
		setExpanded(false);
		setSearchTerm('');
	};

	const getSelectedItems = items => items.filter(item => item.active);

	const onClickHandler = item => () =>
		onClickFilter ? onClickFilter(item) : true;

	const onInputChange = useCallback(
		({target}) => {
			const index = items.findIndex(
				item => item.key === target.dataset.key
			);
			const current = items[index];

			const preventDefault = onChangeFilter
				? onChangeFilter(current)
				: false;

			if (!preventDefault) {
				if (!multiple) {
					items.forEach(item => {
						item.active = false;
					});
				}

				current.active = target.checked;

				if (!multiple) {
					applyFilterChanges();
					closeDropdown();
				}
				else {
					setChanged(true);
				}
			}
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[items]
	);

	const selectDefaultItem = useCallback(() => {
		if (defaultItem && !multiple) {
			const selectedItems = getSelectedItems(items);

			if (!selectedItems.length) {
				const index = items.findIndex(
					item => item.key === defaultItem.key
				);

				items[index].active = true;
				applyFilterChanges();
			}
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [defaultItem, items]);

	useEffect(() => {
		selectDefaultItem();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [defaultItem]);

	useEffect(() => {
		selectDefaultItem();

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
	}, [expanded, changed]);

	return (
		<li
			className={classes.dropdown}
			data-testid={dataTestId}
			ref={wrapperRef}
			style={style}
		>
			<button
				aria-expanded={expanded}
				aria-haspopup="true"
				className={classes.custom}
				disabled={disabled}
				onClick={() => {
					setExpanded(!expanded);
				}}
				type="button"
			>
				<span
					className="mr-2 navbar-text-truncate"
					data-testid="filterName"
				>
					{name}
				</span>

				<Icon iconName="caret-bottom" />
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
								itemKey={item.key}
								key={index}
								multiple={multiple}
								onChange={onInputChange}
								onClick={onClickHandler(item)}
							/>
						))}
					</ul>
				</FilterSearch>
			</div>

			<div className={classes.children}>{children}</div>
		</li>
	);
};
export default Filter;
