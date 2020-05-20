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

import ClayTabs from '@clayui/tabs';
import React, {useMemo, useState} from 'react';

import {useSelector} from '../../../app/store/index';
import Collapse from '../../../common/components/Collapse';
import SearchForm from '../../../common/components/SearchForm';
import SidebarPanelContent from '../../../common/components/SidebarPanelContent';
import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';
import CollectionDisplay, {CollectionDisplayCard} from './CollectionDisplay';
import Fragment from './Fragment';
import LayoutElements from './LayoutElements';
import SearchResultsPanel from './SearchResultsPanel';
import Widget from './Widget';

const CONTENT_DISPLAY_COLLECTION_ID = 'content-display';

export const FragmentPanel = ({collection}) => (
	<>
		{collection.fragmentEntries.map((fragmentEntry) => (
			<Fragment
				fragmentEntryKey={fragmentEntry.fragmentEntryKey}
				groupId={fragmentEntry.groupId}
				icon={fragmentEntry.icon}
				imagePreviewURL={fragmentEntry.imagePreviewURL}
				key={fragmentEntry.fragmentEntryKey}
				name={fragmentEntry.name}
				type={fragmentEntry.type}
			/>
		))}

		{collection.fragmentCollectionId === CONTENT_DISPLAY_COLLECTION_ID && (
			<CollectionDisplayCard />
		)}
	</>
);

export const WidgetPanel = ({category}) =>
	category.portlets.map((widget) => (
		<Widget key={widget.portletId} {...widget} />
	));

const ElementsPanel = ({elements, searchValue}) => {
	const isFragment = elements[0].fragmentCollectionId;

	return elements.map((element) => (
		<div key={isFragment ? element.fragmentCollectionId : element.title}>
			<Collapse
				label={isFragment ? element.name : element.title}
				open={searchValue.length > 0}
			>
				{isFragment ? (
					<FragmentPanel collection={element} />
				) : (
					<WidgetPanel category={element} />
				)}
			</Collapse>
		</div>
	));
};

export default function FragmentsSidebar() {
	const fragments = useSelector((state) => state.fragments);
	const widgets = useSelector((state) => state.widgets);

	const [searchValue, setSearchValue] = useState('');
	const [activeTabValue, setActiveTabValue] = useState(0);

	const contentDisplayCollectionIncluded = fragments.some(
		(fragmentCollection) =>
			fragmentCollection.fragmentCollectionId ===
			CONTENT_DISPLAY_COLLECTION_ID
	);

	const filterComponentByName = (item, searchValueLowerCase) =>
		item.toLowerCase().indexOf(searchValueLowerCase) !== -1;

	const filteredElements = useMemo(() => {
		const filterComponents = (elements) => {
			const searchValueLowerCase = searchValue.toLowerCase();
			const isFragment = elements[0].fragmentEntries;

			return elements
				.map((element) =>
					isFragment
						? {
								...element,
								fragmentEntries: element.fragmentEntries.filter(
									(fragmentEntry) =>
										filterComponentByName(
											fragmentEntry.name,
											searchValueLowerCase
										)
								),
						  }
						: {
								...element,
								portlets: element.portlets.filter((widget) =>
									filterComponentByName(
										widget.title,
										searchValueLowerCase
									)
								),
						  }
				)
				.filter((element) =>
					isFragment
						? element.fragmentEntries.length > 0
						: element.portlets.length > 0
				);
		};

		const filteredFragments = filterComponents(fragments);
		const filteredWidgets = filterComponents(widgets);

		return {
			fragments: filteredFragments,
			widgets: filteredWidgets,
		};
	}, [fragments, searchValue, widgets]);

	return (
		<>
			<SidebarPanelHeader>
				{Liferay.Language.get('fragments-and-widgets')}
			</SidebarPanelHeader>

			<SidebarPanelContent className="page-editor__sidebar__fragments-widgets-panel">
				<SearchForm onChange={setSearchValue} value={searchValue} />
				{!searchValue.length ? (
					<>
						<ClayTabs className="page-editor__sidebar__tabs" modern>
							<ClayTabs.Item
								active={activeTabValue == 0}
								innerProps={{
									'aria-controls': 'tabpanel-fragments',
								}}
								onClick={() => setActiveTabValue(0)}
							>
								Fragments
							</ClayTabs.Item>
							<ClayTabs.Item
								active={activeTabValue == 1}
								innerProps={{
									'aria-controls': 'tabpanel-widgets',
								}}
								onClick={() => setActiveTabValue(1)}
							>
								Widgets
							</ClayTabs.Item>
						</ClayTabs>
						<ClayTabs.Content
							activeIndex={activeTabValue}
							className="page-editor__sidebar__tab-content"
							fade
						>
							<ClayTabs.TabPane aria-labelledby="tab-fragments">
								{!searchValue.length && <LayoutElements />}

								<ElementsPanel
									elements={filteredElements.fragments}
									searchValue={searchValue}
								/>

								{!searchValue.length &&
									!contentDisplayCollectionIncluded && (
										<CollectionDisplay />
									)}
							</ClayTabs.TabPane>
							<ClayTabs.TabPane aria-labelledby="tab-widgets">
								<ElementsPanel
									elements={filteredElements.widgets}
									searchValue={searchValue}
								/>
							</ClayTabs.TabPane>
						</ClayTabs.Content>
					</>
				) : (
					<SearchResultsPanel filteredElements={filteredElements} />
				)}
			</SidebarPanelContent>
		</>
	);
}
