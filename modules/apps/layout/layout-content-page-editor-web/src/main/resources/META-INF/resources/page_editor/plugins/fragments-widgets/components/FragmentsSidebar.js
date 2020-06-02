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

import React, {useMemo, useState} from 'react';

import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {useSelector} from '../../../app/store/index';
import SearchForm from '../../../common/components/SearchForm';
import SidebarPanelContent from '../../../common/components/SidebarPanelContent';
import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';
import SearchResultsPanel from './SearchResultsPanel';
import TabsPanel from './TabsPanel';

export default function FragmentsSidebar() {
	const fragments = useSelector((state) => state.fragments);
	const widgets = useSelector((state) => state.widgets);

	const [searchValue, setSearchValue] = useState('');

	const searchValueLowerCase = useMemo(() => searchValue.toLowerCase(), [
		searchValue,
	]);

	const tabs = useMemo(
		() => [
			{
				collections: fragments.map((collection) => ({
					children: collection.fragmentEntries.map(
						normalizeFragmentEntry
					),
					collectionId: collection.fragmentCollectionId,
					label: collection.name,
				})),
				label: Liferay.Language.get('fragments'),
			},
			{
				collections: widgets.map((collection) => ({
					children: collection.portlets.map(normalizeWidget),
					collectionId: collection.path,
					label: collection.title,
				})),
				label: Liferay.Language.get('widgets'),
			},
		],
		[fragments, widgets]
	);

	const filteredTabs = useMemo(
		() =>
			searchValueLowerCase
				? tabs
						.map((tab) => ({
							...tab,

							collections: tab.collections
								.map((collection) => ({
									...collection,
									children: collection.children.filter(
										(item) =>
											item.label
												.toLowerCase()
												.indexOf(
													searchValueLowerCase
												) !== -1
									),
								}))
								.filter(
									(collection) => collection.children.length
								),
						}))
						.filter((tab) => tab.collections.length)
				: tabs,
		[tabs, searchValueLowerCase]
	);

	return (
		<>
			<SidebarPanelHeader>
				{Liferay.Language.get('fragments-and-widgets')}
			</SidebarPanelHeader>

			<SidebarPanelContent className="page-editor__sidebar__fragments-widgets-panel">
				<SearchForm onChange={setSearchValue} value={searchValue} />
				{searchValue ? (
					<SearchResultsPanel filteredTabs={filteredTabs} />
				) : (
					<TabsPanel tabs={tabs} />
				)}
			</SidebarPanelContent>
		</>
	);
}

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
		preview: fragmentEntry.type ? null : fragmentEntry.imagePreviewURL,
		type: LAYOUT_DATA_ITEM_TYPES.fragment,
	};
};

const normalizeWidget = (widget) => {
	return {
		data: {
			instanceable: widget.instanceable,
			portletId: widget.portletId,
			used: widget.used,
		},
		disabled: !widget.instanceable && widget.used,
		icon: widget.instanceable ? 'cards2' : 'square-hole',
		itemId: widget.portletId,
		label: widget.title,
		preview: '',
		type: LAYOUT_DATA_ITEM_TYPES.fragment,
	};
};
