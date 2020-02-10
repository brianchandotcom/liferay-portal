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
	getSelectedItemsQuery,
	pushToHistory,
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
	style
}) => {
	const [expanded, setExpanded] = useState(false);
	const [searchTerm, setSearchTerm] = useState('');

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

	const getFilterQuery = useCallback(
		() =>
			getSelectedItemsQuery(
				items,
				`${prefixKey}${filterKey}`,
				routerProps.location.search
			),
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[routerProps.location.search, items]
	);

	const filteredItems = useMemo(() => {
		return searchTerm
			? items.filter(item =>
					item.name.toLowerCase().includes(searchTerm.toLowerCase())
			  )
			: items;
	}, [items, searchTerm]);

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

				pushToHistory(getFilterQuery(), routerProps);
			}
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[items, routerProps]
	);

	const selectDefaultItem = useCallback(() => {
		if (defaultItem && !multiple) {
			const selectedItems = items.filter(item => item.active);

			if (!selectedItems.length) {
				const index = items.findIndex(
					item => item.key === defaultItem.key
				);

				defaultItem.active = true;
				items[index].active = true;

				replaceHistory(getFilterQuery(), routerProps);
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
			setExpanded(false);
			setSearchTerm('');
		}, wrapperRef.current);

		addClickOutsideListener(callback);

		return () => {
			removeClickOutsideListener(callback);
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

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
