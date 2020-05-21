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
	<ul className="list-unstyled">
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
	</ul>
);

export const WidgetPanel = ({category}) => (
	<ul className="list-unstyled">
		{category.portlets.map((widget) => (
			<Widget key={widget.portletId} {...widget} />
		))}
	</ul>
);

const ElementsPanel = ({elements}) => {
	const isFragment = elements[0].fragmentCollectionId;

	return elements.map((element, index) => (
		<li key={isFragment ? element.fragmentCollectionId : element.title}>
			<Collapse
				label={isFragment ? element.name : element.title}
				open={isFragment ? index < 2 : index < 3}
			>
				{isFragment ? (
					<FragmentPanel collection={element} />
				) : (
					<WidgetPanel category={element} />
				)}
			</Collapse>
		</li>
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

	const removeDuplicatedElements = (elements) => {
		const unduplicatedElements = [
			...new Set(elements.map((element) => JSON.stringify(element))),
		];

		return unduplicatedElements.map((element) => JSON.parse(element));
	};

	const filterElementByName = (item, searchValueLowerCase) =>
		item.toLowerCase().indexOf(searchValueLowerCase) !== -1;

	const filteredElements = useMemo(() => {
		const filterElements = (elements) => {
			const searchValueLowerCase = searchValue.toLowerCase();
			const isFragment = elements[0].fragmentEntries;

			return isFragment
				? elements.reduce((acc, element) => {
						const fragmentEntries = element.fragmentEntries.filter(
							(fragmentEntry) =>
								filterElementByName(
									fragmentEntry.name,
									searchValueLowerCase
								)
						);

						return [...acc, ...fragmentEntries];
				  }, [])
				: elements.reduce((acc, element) => {
						const portlets = element.portlets.filter((widget) =>
							filterElementByName(
								widget.title,
								searchValueLowerCase
							)
						);

						return [...acc, ...portlets];
				  }, []);
		};

		const filteredFragments = filterElements(fragments);
		const filteredWidgets = removeDuplicatedElements(
			filterElements(widgets)
		);

		return {
			fragments: {fragmentEntries: filteredFragments},
			widgets: {portlets: filteredWidgets},
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
								<ul className="list-unstyled">
									{!searchValue.length && <LayoutElements />}

									<ElementsPanel elements={fragments} />

									{!searchValue.length &&
										!contentDisplayCollectionIncluded && (
											<CollectionDisplay />
										)}
								</ul>
							</ClayTabs.TabPane>
							<ClayTabs.TabPane aria-labelledby="tab-widgets">
								<ul className="list-unstyled">
									<ElementsPanel elements={widgets} />
								</ul>
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
