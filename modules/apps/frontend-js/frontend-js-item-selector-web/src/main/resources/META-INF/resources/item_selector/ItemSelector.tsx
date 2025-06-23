/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAutocomplete from '@clayui/autocomplete';
import {FetchPolicy, useResource} from '@clayui/data-provider';
import {InternalDispatch, useControlledState} from '@clayui/shared';
import {fetch} from 'frontend-js-web';
import React, {useState} from 'react';

const NETWORK_STATUS_UNUSED = 4;

const getNextPageURL = ({apiURL, page}: {apiURL: string; page: number}) => {
	const url = new URL(apiURL);

	url.searchParams.set('page', `${page}`);
	url.searchParams.set('pageSize', '30');

	return url.toString();
};

type ChildrenFunction<T, P> =
	P extends Array<unknown>
		? (item: T, ...args: P) => React.ReactElement
		: (item: T, index?: number) => React.ReactElement;

export interface IProps<T>
	extends Omit<
		React.HTMLAttributes<HTMLInputElement>,
		'onChange' | 'children'
	> {

	/**
	 * The URL that will be fetched to return the items.
	 */
	apiURL: string;

	/**
	 * Children function to render a dynamic or static content.
	 */
	children: ChildrenFunction<T, unknown>;

	/**
	 * Property to set the default value (uncontrolled).
	 */
	defaultValue?: string;

	/**
	 * A string key used to locate the id, label, or value within each item.
	 */
	locator?: {
		id: string;
		label: string;
		value: string;
	};

	/**
	 * Callback called when input value changes (controlled).
	 */
	onChange?: InternalDispatch<string>;

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
	defaultValue,
	...otherProps
}: IProps<T>) {
	const [value = '', setValue] = useControlledState({
		defaultName: 'defaultValue',
		defaultValue,
		handleName: 'onChange',
		name: 'value',
		onChange,
		value: externalValue,
	});

	const [networkStatus, setNetworkStatus] = useState(NETWORK_STATUS_UNUSED);

	const {loadMore, resource: items = []} = useResource({
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

			const {items, lastPage, page} = json;

			return {
				cursor:
					page < lastPage
						? getNextPageURL({apiURL, page: page + 1})
						: null,
				items,
			} as {
				cursor: string | null;
				items: T[];
			};
		},
		fetchDelay: 500,
		fetchPolicy: 'cache-first' as FetchPolicy.CacheFirst,
		link: getNextPageURL({apiURL, page: 1}),
		onNetworkStatusChange: setNetworkStatus,
		variables: {search: value},
	});

	return (
		<ClayAutocomplete<T>
			{...otherProps}
			filterKey={locator.label}
			items={items}
			loadingState={networkStatus}
			menuTrigger="focus"
			onChange={setValue}
			onLoadMore={async () => loadMore()}
			value={value}
		>
			{children}
		</ClayAutocomplete>
	);
}

ItemSelector.Item = ClayAutocomplete.Item;

export default ItemSelector;
