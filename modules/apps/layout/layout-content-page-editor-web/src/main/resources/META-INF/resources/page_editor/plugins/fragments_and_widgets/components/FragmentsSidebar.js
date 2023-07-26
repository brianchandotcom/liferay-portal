/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {
	SearchForm,
	SearchResultsMessage,
	isNullOrUndefined,
} from '@liferay/layout-js-components-web';
import {useSessionState} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

import {ReorderSetsModal} from '../../../app/components/ReorderSetsModal';
import {FRAGMENTS_DISPLAY_STYLES} from '../../../app/config/constants/fragmentsDisplayStyles';
import {HIGHLIGHTED_COLLECTION_ID} from '../../../app/config/constants/highlightedCollectionId';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {config} from '../../../app/config/index';
import {
	useDispatch,
	useSelector,
	useSelectorRef,
} from '../../../app/contexts/StoreContext';
import selectWidgetFragmentEntryLinks from '../../../app/selectors/selectWidgetFragmentEntryLinks';
import loadWidgets from '../../../app/thunks/loadWidgets';
import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';
import SearchResultsPanel from './SearchResultsPanel';
import TabsPanel from './TabsPanel';

export const COLLECTION_IDS = {
	fragments: 0,
	widgets: 1,
};

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
			if (collection.collectionId === HIGHLIGHTED_COLLECTION_ID) {
				return acc;
			}

			if (itemFilter(collection)) {
				return [...acc, collection];
			}
			else {
				const updateCollection = {
					...collection,
					children: collection.children?.filter(
						(item) =>
							itemFilter(item) ||
							item.portletItems?.some(itemFilter)
					),
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
		highlighted: widget.highlighted,
		icon: widget.instanceable ? 'square-hole-multi' : 'square-hole',
		itemId: widget.portletId,
		label: widget.title,
		portletItems: widget.portletItems?.length
			? widget.portletItems.map(normalizeWidget)
			: null,
		preview: '',
		type: LAYOUT_DATA_ITEM_TYPES.fragment,
	};
};

const normalizeCollection = (collection) => {
	const normalizedElement = {
		children: collection.portlets.map(normalizeWidget),
		collectionId: collection.path,
		label: collection.title,
	};

	if (collection.categories?.length) {
		normalizedElement.collections = collection.categories.map(
			normalizeCollection
		);
	}

	return normalizedElement;
};

const normalizeFragmentEntry = (fragmentEntry) => ({
	data: {
		fragmentEntryKey: fragmentEntry.fragmentEntryKey,
		groupId: fragmentEntry.groupId,
		type: fragmentEntry.type,
	},
	highlighted: fragmentEntry.highlighted,
	icon: fragmentEntry.icon,
	itemId: fragmentEntry.fragmentEntryKey,
	label: fragmentEntry.name,
	preview: fragmentEntry.imagePreviewURL,
	type: fragmentEntry.itemType || LAYOUT_DATA_ITEM_TYPES.fragment,
});

export default function FragmentsSidebar() {
	const fragments = useSelector((state) => state.fragments);
	const widgets = useSelector((state) => state.widgets);

	const dispatch = useDispatch();
	const widgetFragmentEntryLinksRef = useSelectorRef(
		selectWidgetFragmentEntryLinks
	);
	const [loadingWidgets, setLoadingWidgets] = useState(false);

	const [activeTabId, setActiveTabId] = useSessionState(
		`${config.portletNamespace}_fragments-sidebar_active-tab-id`,
		0
	);

	const [displayStyle, setDisplayStyle] = useSessionState(
		'FRAGMENTS_DISPLAY_STYLE_KEY',
		FRAGMENTS_DISPLAY_STYLES.LIST
	);

	const [searchValue, setSearchValue] = useState(null);
	const [showReorderModal, setShowReorderModal] = useState(false);

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
				id: COLLECTION_IDS.fragments,
				label: Liferay.Language.get('fragments'),
			},
			{
				collections: widgets
					? widgets.map((collection) =>
							normalizeCollection(collection)
					  )
					: [],
				id: COLLECTION_IDS.widgets,
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

	const displayStyleButtonDisabled =
		searchValue || activeTabId === COLLECTION_IDS.widgets;

	const numberOfResults = useMemo(
		() =>
			isNullOrUndefined(searchValue)
				? null
				: filteredTabs.flatMap((tab) =>
						tab.collections.flatMap(
							(collection) => collection.children
						)
				  ).length,
		[filteredTabs, searchValue]
	);

	useEffect(() => {
		if (searchValue && !widgets) {
			setLoadingWidgets(true);

			dispatch(
				loadWidgets({
					fragmentEntryLinks: widgetFragmentEntryLinksRef.current,
				})
			).then(() => setLoadingWidgets(false));
		}
	}, [dispatch, searchValue, widgetFragmentEntryLinksRef, widgets]);

	const viewButtonLabel = sub(
		Liferay.Language.get('switch-to-x-view'),
		displayStyle === FRAGMENTS_DISPLAY_STYLES.LIST
			? Liferay.Language.get('card')
			: Liferay.Language.get('list[noun]')
	);

	return (
		<>
			<SidebarPanelHeader>
				{Liferay.Language.get('fragments-and-widgets')}
			</SidebarPanelHeader>

			<SearchResultsMessage numberOfResults={numberOfResults} />

			<div className="d-flex flex-column page-editor__sidebar__fragments-widgets-panel">
				<div className="align-items-center d-flex flex-shrink-0 justify-content-between mb-3 px-3">
					<SearchForm
						className="flex-grow-1 mb-0"
						label={Liferay.Language.get(
							'search-fragments-and-widgets'
						)}
						onChange={setSearchValue}
					/>

					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('reorder-sets')}
						borderless
						className="lfr-portal-tooltip ml-2 mt-0"
						data-tooltip-align="bottom-right"
						displayType="secondary"
						onClick={() => setShowReorderModal(true)}
						size="sm"
						symbol="order-arrow"
						title={Liferay.Language.get('reorder-sets')}
					/>

					<ClayButtonWithIcon
						aria-label={viewButtonLabel}
						borderless
						className="lfr-portal-tooltip ml-2 mt-0"
						data-tooltip-align="bottom-right"
						disabled={displayStyleButtonDisabled}
						displayType="secondary"
						onClick={() => {
							setDisplayStyle(
								displayStyle === FRAGMENTS_DISPLAY_STYLES.LIST
									? FRAGMENTS_DISPLAY_STYLES.CARDS
									: FRAGMENTS_DISPLAY_STYLES.LIST
							);
						}}
						size="sm"
						symbol={
							displayStyleButtonDisabled ||
							displayStyle === FRAGMENTS_DISPLAY_STYLES.LIST
								? 'cards2'
								: 'list'
						}
						title={viewButtonLabel}
					/>
				</div>

				{searchValue ? (
					<SearchResultsPanel
						filteredTabs={filteredTabs}
						loading={loadingWidgets}
					/>
				) : (
					<TabsPanel
						activeTabId={activeTabId}
						displayStyle={displayStyle}
						setActiveTabId={setActiveTabId}
						tabs={tabs}
					/>
				)}
			</div>

			{showReorderModal && (
				<ReorderSetsModal
					onCloseModal={() => setShowReorderModal(false)}
				/>
			)}
		</>
	);
}
