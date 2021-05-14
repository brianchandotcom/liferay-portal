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

import ClayLayout from '@clayui/layout';
import React, {useEffect, useMemo, useState} from 'react';

import {COLUMN_SIZE_MODULE_PER_ROW_SIZES} from '../../config/constants/columnSizes';
import {CollectionItemContextProvider} from '../../contexts/CollectionItemContext';
import {useDisplayPagePreviewItem} from '../../contexts/DisplayPagePreviewItemContext';
import {useDispatch, useSelector} from '../../contexts/StoreContext';
import selectLanguageId from '../../selectors/selectLanguageId';
import CollectionService from '../../services/CollectionService';
import UnsafeHTML from '../UnsafeHTML';

const COLLECTION_ID_DIVIDER = '$';

function collectionIsMapped(collectionConfig) {
	return collectionConfig.collection;
}

function getCollectionPrefix(collectionId, index) {
	return `collection-${collectionId}-${index}${COLLECTION_ID_DIVIDER}`;
}

export function getToControlsId(collectionId, index) {
	return (itemId) => {
		if (!itemId) {
			return null;
		}

		return `${getCollectionPrefix(collectionId, index)}${itemId}`;
	};
}

export function fromControlsId(controlsItemId) {
	if (!controlsItemId) {
		return null;
	}

	const [, itemId] = controlsItemId.split(COLLECTION_ID_DIVIDER);

	return itemId || controlsItemId;
}

const NotCollectionSelectedMessage = () => (
	<div className="page-editor__collection__not-collection-selected-message">
		{Liferay.Language.get('no-collection-selected-yet')}
	</div>
);

const Grid = ({
	child,
	collection,
	collectionConfig,
	collectionId,
	collectionLength,
}) => {
	const maxNumberOfItems = Math.min(
		collectionLength,
		collectionConfig.numberOfItems
	);
	const numberOfRows = Math.ceil(
		maxNumberOfItems / collectionConfig.numberOfColumns
	);

	return Array.from({length: numberOfRows}).map((_, i) => (
		<ClayLayout.Row key={`row-${i}`}>
			{Array.from({length: collectionConfig.numberOfColumns}).map(
				(_, j) => {
					const key = `col-${i}-${j}`;
					const index = i * collectionConfig.numberOfColumns + j;

					return (
						<ClayLayout.Col
							key={key}
							size={
								COLUMN_SIZE_MODULE_PER_ROW_SIZES[
									collectionConfig.numberOfColumns
								][collectionConfig.numberOfColumns][j]
							}
						>
							{index < maxNumberOfItems && (
								<ColumnContext
									collectionConfig={collectionConfig}
									collectionId={collectionId}
									collectionItem={collection[index]}
									index={index}
								>
									{React.cloneElement(child)}
								</ColumnContext>
							)}
						</ClayLayout.Col>
					);
				}
			)}
		</ClayLayout.Row>
	));
};

const ColumnContext = ({
	children,
	collectionConfig,
	collectionId,
	collectionItem,
	index,
}) => {
	const contextValue = useMemo(
		() => ({
			collectionConfig,
			collectionItem,
			collectionItemIndex: index,
			fromControlsId: index === 0 ? null : fromControlsId,
			toControlsId:
				index === 0 ? null : getToControlsId(collectionId, index),
		}),
		[collectionConfig, collectionId, collectionItem, index]
	);

	return (
		<CollectionItemContextProvider value={contextValue}>
			{children}
		</CollectionItemContextProvider>
	);
};

const DEFAULT_COLLECTION = {
	items: [{defaultTitle: Liferay.Language.get('title')}],
	length: 1,
};

const Collection = React.forwardRef(({children, item}, ref) => {
	const child = React.Children.toArray(children)[0];
	const collectionConfig = item.config;

	const dispatch = useDispatch();
	const languageId = useSelector(selectLanguageId);

	const [collection, setCollection] = useState(DEFAULT_COLLECTION);

	const displayPagePreviewItemData = useDisplayPagePreviewItem()?.data ?? {};

	useEffect(() => {
		if (collectionConfig.collection) {
			CollectionService.getCollectionField({
				classNameId: displayPagePreviewItemData.classNameId,
				classPK: displayPagePreviewItemData.classPK,
				collection: collectionConfig.collection,
				languageId,
				listItemStyle: collectionConfig.listItemStyle || null,
				listStyle: collectionConfig.listStyle,
				onNetworkStatus: dispatch,
				size: collectionConfig.numberOfItems,
				templateKey: collectionConfig.templateKey || null,
			})
				.then((response) => {
					setCollection(
						response.length > 0 && response.items?.length > 0
							? response
							: DEFAULT_COLLECTION
					);
				})
				.catch((error) => {
					if (process.env.NODE_ENV === 'development') {
						console.error(error);
					}
				});
		}
	}, [
		displayPagePreviewItemData.classNameId,
		displayPagePreviewItemData.classPK,
		collectionConfig.collection,
		collectionConfig.listItemStyle,
		collectionConfig.listStyle,
		collectionConfig.numberOfItems,
		collectionConfig.templateKey,
		dispatch,
		languageId,
	]);

	return (
		<div className="page-editor__collection" ref={ref}>
			{!collectionIsMapped(collectionConfig) ? (
				<NotCollectionSelectedMessage />
			) : collection.content ? (
				<UnsafeHTML markup={collection.content} />
			) : (
				<Grid
					child={child}
					collection={collection.items}
					collectionConfig={collectionConfig}
					collectionId={item.itemId}
					collectionLength={collection.items.length}
				/>
			)}
		</div>
	);
});

export default Collection;
