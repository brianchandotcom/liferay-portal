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

import classNames from 'classnames';
import {fetch, objectToFormData} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

import 'product-navigation-control-menu/css/TabsPanel.scss';

import {normalizeContent} from './AddPanel';
import Collapse from './Collapse';
import ContentOptions from './ContentOptions';
import SearchForm from './SearchForm';
import SearchResultsPanel from './SearchResultPanel';
import TabItem from './TabItem';

const CONTENT_TAB_ID = 'content';
const INITIAL_EXPANDED_ITEM_COLLECTIONS = 3;

const TabsContent = ({
	addContentsURLs,
	getContentsURL,
	namespace,
	portletNamespace,
	tab,
}) => {
	const [displayGrid, setDisplayGrid] = useState(false);
	const [filteredContent, setFilteredContent] = useState(tab);
	const [totalItems, setTotalItems] = useState(0);
	const [searchValue, setSearchValue] = useState('');

	const isContentTab = tab.id === CONTENT_TAB_ID;

	const collections = isContentTab
		? filteredContent.collections
		: tab.collections;

	const searchValueLowerCase = useMemo(() => searchValue.toLowerCase(), [
		searchValue,
	]);

	const filteredWidgets = useMemo(
		() =>
			searchValueLowerCase
				? [
						{
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
						},
				  ]
				: tab,
		[tab, searchValueLowerCase]
	);

	useEffect(() => {
		if (searchValueLowerCase || totalItems) {
			let body = {
				[`${namespace}keywords`]: searchValueLowerCase,
			};

			if (totalItems) {
				body = {
					...body,
					[`${namespace}delta`]: totalItems,
				};
			}

			fetch(getContentsURL, {
				body: objectToFormData(body),
				method: 'post',
			})
				.then((response) => response.json())
				.then((items) => {
					const normalizeItems = {
						...tab,
						collections: items.contents.length
							? tab.collections.map((collection) => ({
									...collection,
									children: items.contents.map(
										normalizeContent
									),
							  }))
							: items.contents,
					};

					return setFilteredContent(normalizeItems);
				});
		}
		else {
			return setFilteredContent(tab);
		}
	}, [getContentsURL, namespace, searchValueLowerCase, tab, totalItems]);

	return (
		<>
			<SearchForm onChange={setSearchValue} value={searchValue} />
			{isContentTab && (
				<ContentOptions
					addContentsURLs={addContentsURLs}
					displayGrid={displayGrid}
					onChangeDisplayStyle={setDisplayGrid}
					onChangeSelect={setTotalItems}
					portletNamespace={portletNamespace}
				/>
			)}
			{searchValue ? (
				<SearchResultsPanel
					alertTitle={
						isContentTab
							? Liferay.Language.get(
									'there-is-no-content-on-this-page'
							  )
							: Liferay.Language.get(
									'there-are-no-widgets-on-this-page'
							  )
					}
					displayGrid={isContentTab && displayGrid}
					filteredTabs={
						isContentTab ? [filteredContent] : filteredWidgets
					}
				/>
			) : (
				<ul className="list-unstyled">
					{collections.map((collection, index) => (
						<Collapse
							key={collection.collectionId}
							label={collection.label}
							open={index < INITIAL_EXPANDED_ITEM_COLLECTIONS}
						>
							<ul
								className={classNames('list-unstyled', {
									grid: isContentTab && displayGrid,
								})}
							>
								{collection.children.map((item) => (
									<TabItem item={item} key={item.itemId} />
								))}
							</ul>
						</Collapse>
					))}
				</ul>
			)}
		</>
	);
};

export default TabsContent;
