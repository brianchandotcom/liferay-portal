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

import {ClayButtonWithIcon} from '@clayui/button';
import React, {useMemo, useState} from 'react';

import {FRAGMENTS_DISPLAY_STYLES} from '../../../app/config/constants/fragmentsDisplayStyles';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {useSelector} from '../../../app/contexts/StoreContext';
import SearchForm from '../../../common/components/SearchForm';
import SidebarPanelContent from '../../../common/components/SidebarPanelContent';
import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';
import SearchResultsPanel from './SearchResultsPanel';
import TabsPanel from './TabsPanel';

const FRAGMENTS_DISPLAY_STYLE_KEY = 'FRAGMENTS_DISPLAY_STYLE_KEY';

const collectionFilter = (collections, searchValue) => {
	const searchValueLowerCase = searchValue.toLowerCase();

	const itemFilter = (item) =>
		item.label.toLowerCase().indexOf(searchValueLowerCase) !== -1;

	const hasChildren = (collection) => {
		if (collection.children?.length) {
			return true;
		}

		return collection.collections?.some(hasChildren) ?? false;
	};

	return collections
		.reduce((acc, collection) => {
			if (itemFilter(collection)) {
				return [...acc, collection];
			}
			else {
				const updateCollection = {
					...collection,
					children: collection.children.filter(itemFilter),
					...(collection.collections?.length && {
						collections: collectionFilter(
							collection.collections,
							searchValueLowerCase
						),
					}),
				};

				return [...acc, updateCollection];
			}
		}, [])
		.filter(hasChildren);
};

const normalizeWidget = (widget) => {
	return {
		data: {
			instanceable: widget.instanceable,
			portletId: widget.portletId,
			portletItemId: widget.portletItemId || null,
			used: widget.used,
		},
		disabled: !widget.instanceable && widget.used,
		icon: widget.instanceable ? 'cards2' : 'square-hole',
		itemId: widget.portletId,
		label: widget.title,
		portletItems: widget.portletItems?.length
			? widget.portletItems.map(normalizeWidget)
			: null,
		preview: '',
		type: LAYOUT_DATA_ITEM_TYPES.fragment,
	};
};

const normalizeCollections = (collection) => {
	const normalizedElement = {
		children: collection.portlets.map(normalizeWidget),
		collectionId: collection.path,
		label: collection.title,
	};

	if (collection.categories?.length) {
		normalizedElement.collections = collection.categories.map(
			normalizeCollections
		);
	}

	return normalizedElement;
};

const normalizeFragmentEntry = (fragmentEntry) => {
	if (!fragmentEntry.fragmentEntryKey) {
		return fragmentEntry;
	}

	return {
		data: {
			fragmentEntryKey: fragmentEntry.fragmentEntryKey,
			groupId: fragmentEntry.groupId,
			type: fragmentEntry.type,
		},
		icon: fragmentEntry.icon,
		itemId: fragmentEntry.fragmentEntryKey,
		label: fragmentEntry.name,
		preview: fragmentEntry.imagePreviewURL,
		type: LAYOUT_DATA_ITEM_TYPES.fragment,
	};
};

export default function FragmentsSidebar() {
	const fragments = useSelector((state) => state.fragments);
	const widgets = useSelector((state) => state.widgets);

	const [displayStyle, setDisplayStyle] = useState(
		window.sessionStorage.getItem(FRAGMENTS_DISPLAY_STYLE_KEY) ||
			FRAGMENTS_DISPLAY_STYLES.LIST
	);

	const [searchValue, setSearchValue] = useState('');

	const tabs = useMemo(
		() => [
			{
				collections: fragments.map((collection) => ({
					children: collection.fragmentEntries.map((fragmentEntry) =>
						normalizeFragmentEntry(fragmentEntry)
					),
					collectionId: collection.fragmentCollectionId,
					label: collection.name,
				})),
				label: Liferay.Language.get('fragments'),
			},
			{
				collections: widgets.map((collection) =>
					normalizeCollections(collection)
				),
				label: Liferay.Language.get('widgets'),
			},
		],
		[fragments, widgets]
	);

	const filteredTabs = useMemo(
		() =>
			searchValue
				? tabs
						.map((tab) => ({
							...tab,
							collections: collectionFilter(
								tab.collections,
								searchValue
							),
						}))
						.filter((item) => item.collections.length)
				: tabs,
		[tabs, searchValue]
	);

	return (
		<>
			<SidebarPanelHeader>
				{Liferay.Language.get('fragments-and-widgets')}
			</SidebarPanelHeader>

			<SidebarPanelContent className="page-editor__sidebar__fragments-widgets-panel">
				<div className="align-items-center d-flex justify-content-between mb-3">
					<SearchForm
						className="flex-grow-1 mb-0"
						onChange={setSearchValue}
					/>

					<ClayButtonWithIcon
						borderless
						className="lfr-portal-tooltip ml-2 mt-0"
						displayType="secondary"
						onClick={() => {
							const nextDisplayStyle =
								displayStyle === FRAGMENTS_DISPLAY_STYLES.LIST
									? FRAGMENTS_DISPLAY_STYLES.CARDS
									: FRAGMENTS_DISPLAY_STYLES.LIST;

							setDisplayStyle(nextDisplayStyle);

							window.sessionStorage.setItem(
								FRAGMENTS_DISPLAY_STYLE_KEY,
								nextDisplayStyle
							);
						}}
						small
						symbol={
							displayStyle === FRAGMENTS_DISPLAY_STYLES.CARDS
								? 'cards2'
								: 'list'
						}
						title={Liferay.Language.get('change-view')}
					/>
				</div>
				{searchValue ? (
					<SearchResultsPanel
						displayStyle={displayStyle}
						filteredTabs={filteredTabs}
					/>
				) : (
					<TabsPanel displayStyle={displayStyle} tabs={tabs} />
				)}
			</SidebarPanelContent>
		</>
	);
}
