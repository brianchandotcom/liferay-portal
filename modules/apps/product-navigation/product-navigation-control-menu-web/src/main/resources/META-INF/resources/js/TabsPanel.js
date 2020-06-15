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

import 'product-navigation-control-menu/css/TabsPanel.scss';
import classNames from 'classnames';

import Collapse from './Collapse';
import ContentOptions from './ContentOptions';
import SearchForm from './SearchForm';
import TabItem from './TabItem';

let nextId = 0;
const CONTENT_TAB_NAME = Liferay.Language.get('content');
const INITIAL_EXPANDED_ITEM_COLLECTIONS = 3;

const useId = ({portletNamespace}) => {
	return useMemo(() => `${portletNamespace}_useId_${nextId++}`, [
		portletNamespace,
	]);
};

const filterTotalItems = (items, totalItems, label) => {
	return label === CONTENT_TAB_NAME ? items.slice(0, totalItems) : items;
};

const AddPanel = ({portletNamespace, tabs}) => {
	const [activeTabId, setActiveTabId] = useState(0);
	const [grid, setGrid] = useState(false);
	const [totalItems, setTotalItems] = useState(4);
	const tabIdNamespace = useId({portletNamespace});

	const getTabId = (tabId) => `${tabIdNamespace}tab${tabId}`;
	const getTabPanelId = (tabId) => `${tabIdNamespace}tabPanel${tabId}`;

	return (
		<>
			<ClayTabs className="sidebar-content__add-panel__tabs" modern>
				{tabs.map((tab, index) => (
					<ClayTabs.Item
						active={activeTabId === index}
						innerProps={{
							'aria-controls': getTabPanelId(index),
							id: getTabId(index),
						}}
						key={index}
						onClick={() => setActiveTabId(index)}
					>
						{tab.label}
					</ClayTabs.Item>
				))}
			</ClayTabs>
			<ClayTabs.Content
				activeIndex={activeTabId}
				className="sidebar-content__add-panel__tab-content"
				fade
			>
				{tabs.map((tab, index) => (
					<ClayTabs.TabPane
						aria-labelledby={getTabId(index)}
						id={getTabPanelId(index)}
						key={index}
					>
						<SearchForm />
						{tab.label === CONTENT_TAB_NAME && (
							<ContentOptions
								grid={grid}
								onChangeListMode={setGrid}
								onChangeSelect={setTotalItems}
								portletNamespace={portletNamespace}
							/>
						)}
						<ul className="list-unstyled">
							{tab.collections.map((collection, index) => (
								<Collapse
									key={collection.collectionId}
									label={collection.label}
									open={
										index <
										INITIAL_EXPANDED_ITEM_COLLECTIONS
									}
								>
									<ul
										className={classNames('list-unstyled', {
											grid:
												grid &&
												tab.label === CONTENT_TAB_NAME,
										})}
									>
										{filterTotalItems(
											collection.children,
											totalItems,
											tab.label
										).map((item) => (
											<TabItem
												item={item}
												key={item.itemId}
											/>
										))}
									</ul>
								</Collapse>
							))}
						</ul>
					</ClayTabs.TabPane>
				))}
			</ClayTabs.Content>
		</>
	);
};

export default AddPanel;
