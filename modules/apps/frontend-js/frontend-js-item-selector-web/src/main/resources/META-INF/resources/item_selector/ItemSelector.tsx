/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAutocomplete from '@clayui/autocomplete';
import {ClayButtonWithIcon} from '@clayui/button';
import {FetchPolicy, useResource} from '@clayui/data-provider';
import {ClayInput} from '@clayui/form';
import {useModal} from '@clayui/modal';
import ClayMultiSelect from '@clayui/multi-select';
import {InternalDispatch, useControlledState} from '@clayui/shared';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {fetch, getObjectValueFromPath} from 'frontend-js-web';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

import ItemSelectorModal, {IItemSelectorModalProps} from './ItemSelectorModal';

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

type IInternalItemSelectorModalProps<T> = Omit<
	IItemSelectorModalProps<T>,
	| 'apiURL'
	| 'items'
	| 'locator'
	| 'multiSelect'
	| 'observer'
	| 'onItemsChange'
	| 'onOpenChange'
	| 'open'
>;

interface IItemSelectorModalTriggerProps<T extends Record<string, any>> {
	apiURL: string;
	itemSelectorModalProps: IInternalItemSelectorModalProps<T>;
	items: T[];
	locator: {
		id: string;
		label: string;
		value: string;
	};
	multiSelect?: boolean;
	onItemsChange: InternalDispatch<T[]>;
}

function ItemSelectorModalTrigger<T extends Record<string, any>>({
	apiURL,
	itemSelectorModalProps,
	items,
	locator,
	multiSelect = false,
	onItemsChange,
}: IItemSelectorModalTriggerProps<T>) {
	const {observer, onOpenChange, open} = useModal();

	return (
		<>
			<ClayInput.GroupItem shrink>
				<ClayTooltipProvider>
					<span
						data-tooltip-align="top"
						title={Liferay.Language.get('open-full-list')}
					>
						<ClayButtonWithIcon
							aria-label={Liferay.Language.get('select-items')}
							displayType="secondary"
							onClick={() => onOpenChange(true)}
							symbol="search-experiences"
						/>
					</span>
				</ClayTooltipProvider>
			</ClayInput.GroupItem>

			<ItemSelectorModal
				{...itemSelectorModalProps}
				apiURL={apiURL}
				items={items}
				locator={locator}
				multiSelect={multiSelect}
				observer={observer}
				onItemsChange={onItemsChange}
				onOpenChange={onOpenChange}
				open={open}
			/>
		</>
	);
}

export interface IBaseItemSelectorProps<T> {

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
	 * Props passed to the ItemSelectorModal component.
	 */

	itemSelectorModalProps?: IInternalItemSelectorModalProps<T>;

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
	 * A flag to refetch the data when the menu is active.
	 */
	refetchOnActive?: boolean;

	/**
	 * The current value of the input (controlled).
	 */
	value?: string;
}

interface IMultiSelect<T>
	extends IBaseItemSelectorProps<T>,
		Omit<
			React.ComponentProps<typeof ClayMultiSelect>,
			keyof IBaseItemSelectorProps<T>
		> {
	multiSelect: true;
}

interface IAutocomplete<T>
	extends IBaseItemSelectorProps<T>,
		Omit<
			React.ComponentProps<typeof ClayAutocomplete>,
			keyof IBaseItemSelectorProps<T>
		> {
	multiSelect?: false;
}

export type IItemSelectorProps<T> = IMultiSelect<T> | IAutocomplete<T>;

function ItemSelector<T extends Record<string, any>>({
	apiURL,
	children,
	locator = {
		id: 'id',
		label: 'title',
		value: 'id',
	},
	value: externalValue,
	itemSelectorModalProps,
	onChange,
	onItemsChange,
	multiSelect = false,
	items: externalItems,
	defaultValue,
	defaultItems,
	displaySelectedItems = true,
	refetchOnActive = false,
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

	const {
		loadMore,
		refetch,
		resource: sourceItems = [],
	} = useResource({
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
		fetchPolicy: refetchOnActive
			? ('cache-and-network' as FetchPolicy.CacheAndNetwork)
			: ('cache-first' as FetchPolicy.CacheFirst),
		link: getNextPageURL({apiURL, page: 1}),
		onNetworkStatusChange: setNetworkStatus,
		variables: {search: value},
	});

	const selectedKeys = useMemo(() => {
		return (
			items?.map((item) =>
				String(
					getObjectValueFromPath({
						object: item,
						path: locator.value,
					})
				)
			) ?? []
		);
	}, [items, locator.value]);

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

	let itemSelectorComponent;

	const handleActiveChange = (newActive: boolean) => {
		if (newActive && refetchOnActive && newActive !== active) {
			refetch();
		}

		setActive(newActive);
	};

	if (multiSelect && displaySelectedItems) {
		itemSelectorComponent = (
			<ClayMultiSelect
				{...(otherProps as any)}
				active={active}
				allowsCustomLabel={false}
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
				messages={{
					hotkeys: Liferay.Language.get(
						'press-backspace-to-delete-the-current-row'
					),
					labelAdded: Liferay.Language.get(
						'label-x-was-added-to-the-list'
					),
					labelRemoved: Liferay.Language.get(
						'label-x-was-removed-from-the-list'
					),
					listCount: Liferay.Language.get(
						'there-is-x-option-available'
					),
					listCountPlural: Liferay.Language.get(
						'there-are-x-options-available'
					),
					loading: Liferay.Language.get('loading...'),
					notFound: Liferay.Language.get('no-results-found'),
				}}
				onActiveChange={handleActiveChange}
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
	else {
		itemSelectorComponent = (
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
				messages={{
					infiniteScrollInitialLoad: Liferay.Language.get(
						'x-item-loaded-reach-the-last-item-to-load-more'
					),
					infiniteScrollInitialLoadPlural: Liferay.Language.get(
						'x-items-loaded-reach-the-last-item-to-load-more'
					),
					infiniteScrollOnLoad:
						Liferay.Language.get('loading-more-items'),
					infiniteScrollOnLoaded:
						Liferay.Language.get('x-item-loaded'),
					infiniteScrollOnLoadedPlural:
						Liferay.Language.get('x-items-loaded'),
					listCount: Liferay.Language.get('x-list-option'),
					listCountPlural: Liferay.Language.get('x-list-options'),
					loading: Liferay.Language.get('loading...'),
					notFound: Liferay.Language.get('no-results-found'),
				}}
				onActiveChange={handleActiveChange}
				onChange={(value: string) => {
					if (!value.length) {
						setItems([]);
					}

					setValue(value);
				}}
				onLoadMore={async () => loadMore()}
				selectedKeys={selectedKeys}
				value={value}
			>
				{memoizedChildren}
			</ClayAutocomplete>
		);
	}

	return itemSelectorModalProps ? (
		<ClayInput.Group>
			<ClayInput.GroupItem>{itemSelectorComponent}</ClayInput.GroupItem>

			<ItemSelectorModalTrigger
				apiURL={apiURL}
				itemSelectorModalProps={itemSelectorModalProps}
				items={items}
				locator={locator}
				multiSelect={multiSelect}
				onItemsChange={(items: T[]) => {
					setItems(items);

					if (multiSelect) {
						return;
					}

					if (items.length) {
						const firstItemLabel = getObjectValueFromPath({
							object: items[0],
							path: locator.label,
						});

						setValue(firstItemLabel);
					}
					else {
						setValue('');
					}
				}}
			/>
		</ClayInput.Group>
	) : (
		itemSelectorComponent
	);
}

ItemSelector.Item = ClayAutocomplete.Item;

export default ItemSelector;
