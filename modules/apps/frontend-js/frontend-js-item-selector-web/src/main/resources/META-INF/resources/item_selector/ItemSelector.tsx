/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAutocomplete from '@clayui/autocomplete';
import {FetchPolicy, useResource} from '@clayui/data-provider';
import {ClayInput} from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import {InternalDispatch, useControlledState} from '@clayui/shared';
import {fetch, getObjectValueFromPath} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

const NETWORK_STATUS_UNUSED = 4;

const getNextPageURL = ({apiURL, page}: {apiURL: string; page: number}) => {
	const url = new URL(apiURL);

	url.searchParams.set('page', `${page}`);
	url.searchParams.set('pageSize', '30');

	return url.toString();
};

type ChildrenFunction<T, P> = P extends unknown[]
	? (item: T, ...args: P) => React.ReactElement
	: (item: T, index?: number) => React.ReactElement;

interface HeadlessPage<T = unknown> {
	items: T[];
	lastPage: number;
	page: number;
}

export interface IItemSelectorProps<T>
	extends Omit<
		React.HTMLAttributes<HTMLInputElement>,
		'onChange' | 'children'
	> {

	/**
	 * The URL that will be fetched to return the items.
	 */
	apiURL: string;

	/**
	 * Custom input component.
	 */
	as?:
		| 'input'
		| React.ForwardRefExoticComponent<any>
		| ((props: React.ComponentProps<typeof ClayInput>) => JSX.Element);

	/**
	 * Children function to render a dynamic or static content.
	 */
	children: ChildrenFunction<T, unknown>;

	/**
	 * Set the default selected items (uncontrolled).
	 */
	defaultItems?: T[];

	/**
	 * Property to set the default value (uncontrolled).
	 */
	defaultValue?: string;

	/**
	 * Controls whether selected items are displayed by the component.
	 * Set to `false` if you plan to render the selected items using a custom
	 * implementation.
	 */
	displaySelectedItems?: boolean;

	/**
	 * Items that are currently selected (controlled).
	 */
	items?: T[];

	/**
	 * A string key used to locate the id, label, or value within each item.
	 * Can be used as a period separated path (e.g.: 'embedded.id').
	 */
	locator?: {
		id: string;
		label: string;
		value: string;
	};

	/**
	 * A flag for rendering the Clay MultiSelect component and allowing multiple
	 * selection.
	 */
	multiSelect?: boolean;

	/**
	 * Callback called when input value changes (controlled).
	 */
	onChange?: InternalDispatch<string>;

	/**
	 * Callback for when items are added or removed (controlled).
	 */
	onItemsChange?: InternalDispatch<T[]>;

	/**
	 * The current value of the input (controlled).
	 */
	value?: string;
}

function ItemSelector<T extends Record<string, any>>({
	apiURL,
	children,
	locator = {
		id: 'id',
		label: 'title',
		value: 'id',
	},
	value: externalValue,
	onChange,
	onItemsChange,
	multiSelect = false,
	items: externalItems,
	defaultValue,
	defaultItems,
	displaySelectedItems = true,
	...otherProps
}: IItemSelectorProps<T>) {
	useEffect(() => {
		if (!displaySelectedItems && !multiSelect) {
			console.warn(
				'<ItemSelector>: "displaySelectedItems" should only be disabled when "multiSelect" is enabled. For single selection `as` can be used to render a custom input and selected item.'
			);
		}
	}, [displaySelectedItems, multiSelect]);

	const [active, setActive] = useState(false);

	const [value = '', setValue] = useControlledState({
		defaultName: 'defaultValue',
		defaultValue,
		handleName: 'onChange',
		name: 'value',
		onChange,
		value: externalValue,
	});

	const [items = [], setItems] = useControlledState({
		defaultName: 'defaultItems',
		defaultValue: defaultItems,
		handleName: 'onItemsChange',
		name: 'items',
		onChange: onItemsChange,
		value: externalItems,
	});

	const [networkStatus, setNetworkStatus] = useState(NETWORK_STATUS_UNUSED);

	const {loadMore, resource: sourceItems = []} = useResource({
		fetch: async (link) => {
			const result = await fetch(link);

			const contentType = result.headers.get('Content-Type') || '';

			if (!contentType.includes('application/json')) {
				console.warn(
					'The ItemSelector expects an application/json response from apiURL provided.'
				);

				return;
			}

			const json = await result.json();

			if (!Array.isArray(json.items)) {
				console.warn(
					'The ItemSelector expects the response from apiURL to include an array of items.'
				);

				return json;
			}

			const {items, lastPage, page} = json as HeadlessPage<T>;

			const cursor =
				page < lastPage
					? getNextPageURL({apiURL, page: page + 1})
					: null;

			return {cursor, items};
		},
		fetchDelay: 500,
		fetchPolicy: 'cache-first' as FetchPolicy.CacheFirst,
		link: getNextPageURL({apiURL, page: 1}),
		onNetworkStatusChange: setNetworkStatus,
		variables: {search: value},
	});

	const memoizedChildren = useCallback(
		(item: T) => {
			const child = children(item) as React.ReactElement<
				any,
				string | React.JSXElementConstructor<any>
			>;

			return React.cloneElement(child, {
				onClick: (
					event: React.MouseEvent<
						HTMLSpanElement | HTMLButtonElement | HTMLAnchorElement,
						MouseEvent
					>
				) => {
					if (child.props.onClick) {
						child.props.onClick(event);
					}

					if (event.defaultPrevented) {
						return;
					}

					if (multiSelect) {
						event.preventDefault();

						setActive(false);
						setItems([...items, item]);
						setValue('');
					}
					else {
						setItems([item]);
					}
				},
			});
		},
		[children, items, multiSelect, setItems, setValue]
	);

	if (multiSelect && displaySelectedItems) {
		return (
			<ClayMultiSelect
				{...otherProps}
				items={items}
				locator={{
					id: (item: T) => {
						return getObjectValueFromPath({
							object: item,
							path: locator.id,
						});
					},
					label: (item: T) => {
						return getObjectValueFromPath({
							object: item,
							path: locator.label,
						});
					},
					value: (item: T) => {
						return getObjectValueFromPath({
							object: item,
							path: locator.value,
						});
					},
				}}
				onChange={setValue}
				onItemsChange={setItems}
				onLoadMore={async () => loadMore()}
				sourceItems={sourceItems}
				value={value}
			>
				{children}
			</ClayMultiSelect>
		);
	}

	return (
		<ClayAutocomplete<T>
			{...otherProps}
			active={active}
			filterKey={(item: T) => {
				return getObjectValueFromPath({
					object: item,
					path: locator.label,
				});
			}}
			items={sourceItems}
			loadingState={networkStatus}
			menuTrigger="focus"
			onActiveChange={setActive}
			onChange={(value: string) => {
				if (!value.length) {
					setItems([]);
				}

				setValue(value);
			}}
			onLoadMore={async () => loadMore()}
			value={value}
		>
			{memoizedChildren}
		</ClayAutocomplete>
	);
}

ItemSelector.Item = ClayAutocomplete.Item;

export default ItemSelector;
