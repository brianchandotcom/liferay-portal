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
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useMemo, useState} from 'react';

import {AddPanelContext, normalizeContent} from './AddPanel';
import Collapse from './Collapse';
import ContentOptions from './ContentOptions';
import SearchForm from './SearchForm';
import SearchResultsPanel from './SearchResultPanel';
import TabItem from './TabItem';

const CONTENT_TAB_ID = 'content';
const INITIAL_EXPANDED_ITEM_COLLECTIONS = 3;
const EMPTY_COLLECTIONS = {collections: []};

const TabsContent = ({tab, tabIndex}) => {
	const {displayGrid, getContentsURL, namespace} = useContext(
		AddPanelContext
	);

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
		[searchValueLowerCase, tab]
	);

	useEffect(() => {
		if (searchValueLowerCase || totalItems) {
			const body = {
				[`${namespace}keywords`]: searchValueLowerCase,
			};

			if (totalItems) {
				body[`${namespace}delta`] = totalItems;
			}

			fetch(getContentsURL, {
				body: objectToFormData(body),
				method: 'post',
			})
				.then((response) => response.json())
				.then((items) => {
					const normalizedItems = {
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

					return setFilteredContent(normalizedItems);
				})
				.catch(() => {
					return setFilteredContent([EMPTY_COLLECTIONS]);
				});
		}
		else {
			return setFilteredContent(tab);
		}
	}, [getContentsURL, namespace, searchValueLowerCase, tab, totalItems]);

	return (
		<>
			<SearchForm
				onChange={setSearchValue}
				tabIndex={tabIndex}
				value={searchValue}
			/>
			{isContentTab && <ContentOptions onChangeSelect={setTotalItems} />}
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

TabsContent.propTypes = {
	tab: PropTypes.shape({
		collections: PropTypes.arrayOf(
			PropTypes.shape({
				children: PropTypes.arrayOf(PropTypes.shape()).isRequired,
				collectionId: PropTypes.string.isRequired,
				label: PropTypes.string.isRequired,
			})
		),
		id: PropTypes.string.isRequired,
		label: PropTypes.string.isRequired,
	}).isRequired,
	tabIndex: PropTypes.number.isRequired,
};

export default TabsContent;
