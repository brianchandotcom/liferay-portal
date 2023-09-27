/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SearchForm} from '@liferay/layout-js-components-web';
import {fetch, objectToFormData} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useMemo, useState} from 'react';

import {AddPanelContext, normalizeContent} from './AddPanel';
import Collection from './Collection';
import ContentOptions from './ContentOptions';
import SearchResultsPanel from './SearchResultPanel';

const CONTENT_TAB_ID = 'content';
const EMPTY_COLLECTIONS = {collections: []};
const INITIAL_EXPANDED_ITEM_COLLECTIONS = 3;

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
					children: collection.children.filter(
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
				collections.map((collection, index) => (
					<Collection
						collection={collection}
						isContentTab={isContentTab}
						key={index}
						open={index < INITIAL_EXPANDED_ITEM_COLLECTIONS}
					/>
				))
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
