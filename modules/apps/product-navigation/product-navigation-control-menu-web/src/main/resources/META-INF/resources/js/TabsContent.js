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
	const {getContentsURL, namespace} = useContext(AddPanelContext);

	const [filteredContent, setFilteredContent] = useState(tab);
	const [totalItems, setTotalItems] = useState(0);
	const [searchValue, setSearchValue] = useState('');

	const isContentTab = tab.id === CONTENT_TAB_ID;

	const collections = isContentTab
		? filteredContent.collections
		: tab.collections;

	const filteredWidgets = useMemo(
		() =>
			searchValue
				? [
						{
							...tab,
							collections: collectionFilter(
								tab.collections,
								searchValue
							),
						},
				  ]
				: tab,
		[searchValue, tab]
	);

	useEffect(() => {
		if (searchValue || totalItems) {
			const body = {
				[`${namespace}keywords`]: searchValue.toLowerCase(),
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
	}, [getContentsURL, namespace, searchValue, tab, totalItems]);

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
					<Collections
						collections={
							searchValue && filteredWidgets.length
								? filteredWidgets[0].collections
								: collections
						}
						isContentTab={isContentTab}
						open
					/>
				</ul>
			)}
		</>
	);
};

const Collections = ({
	collections,
	indentation = false,
	isContentTab,
	open,
}) => {
	const {displayGrid} = useContext(AddPanelContext);

	return collections.map((collection, index) => (
		<Collapse
			indentation
			key={collection.collectionId}
			label={collection.label}
			open={open && index < INITIAL_EXPANDED_ITEM_COLLECTIONS}
		>
			{collection.collections && (
				<Collections
					collections={collection.collections}
					indentation={indentation}
				/>
			)}

			<ul
				className={classNames('list-unstyled', {
					grid: isContentTab && displayGrid,
				})}
			>
				{collection.children.map((item) => (
					<React.Fragment key={item.itemId}>
						<TabItem item={item} />
						{item.portletItems?.length && (
							<TabPortletItem items={item.portletItems} />
						)}
					</React.Fragment>
				))}
			</ul>
		</Collapse>
	));
};

const TabPortletItem = ({items}) => (
	<ul className="list-unstyled">
		{items.map((item) => (
			<TabItem item={item} key={item.data.portletItemId} />
		))}
	</ul>
);

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
