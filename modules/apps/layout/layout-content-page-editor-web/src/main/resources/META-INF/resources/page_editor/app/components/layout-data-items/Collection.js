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
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {useContext, useEffect, useMemo, useState} from 'react';

import {COLUMN_SIZE_MODULE_PER_ROW_SIZES} from '../../config/constants/columnSizes';
import {
	CollectionItemContext,
	CollectionItemContextProvider,
	useToControlsId,
} from '../../contexts/CollectionItemContext';
import {useDisplayPagePreviewItem} from '../../contexts/DisplayPagePreviewItemContext';
import {useDispatch, useSelector} from '../../contexts/StoreContext';
import selectLanguageId from '../../selectors/selectLanguageId';
import CollectionService from '../../services/CollectionService';
import updateItemConfig from '../../thunks/updateItemConfig';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import isNullOrUndefined from '../../utils/isNullOrUndefined';
import UnsafeHTML from '../UnsafeHTML';
import CollectionPagination from './CollectionPagination';

const COLLECTION_ID_DIVIDER = '$';

function collectionIsMapped(collectionConfig) {
	return collectionConfig.collection;
}

function getCollectionPrefix(collectionId, index) {
	return `collection-${collectionId}-${index}${COLLECTION_ID_DIVIDER}`;
}

export function getToControlsId(collectionId, index, toControlsId) {
	return (itemId) => {
		if (!itemId) {
			return null;
		}

		return toControlsId(
			`${getCollectionPrefix(collectionId, index)}${itemId}`
		);
	};
}

export function fromControlsId(controlsItemId) {
	if (!controlsItemId) {
		return null;
	}

	const splits = controlsItemId.split(COLLECTION_ID_DIVIDER);

	const itemId = splits.pop();

	return itemId || controlsItemId;
}

const NotCollectionSelectedMessage = () => (
	<div className="page-editor__collection__message">
		{Liferay.Language.get('no-collection-selected-yet')}
	</div>
);

const EmptyCollectionMessage = () => (
	<div className="page-editor__collection__message">
		{Liferay.Language.get('there-are-no-items-to-display')}
	</div>
);

const EmptyCollectionGridMessage = () => (
	<div className="alert alert-info">
		{Liferay.Language.get(
			'the-collection-is-empty-to-display-your-items-add-them-to-the-collection-or-choose-a-different-collection'
		)}
	</div>
);

const Grid = ({
	child,
	collection,
	collectionConfig,
	collectionId,
	collectionLength,
	customCollectionSelectorURL,
}) => {
	const maxNumberOfItems =
		Math.min(
			collectionLength,
			collectionConfig.paginationType
				? Math.min(
						collectionConfig.numberOfItems,
						collectionConfig.numberOfItemsPerPage
				  )
				: collectionConfig.numberOfItems
		) || 1;
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
									customCollectionSelectorURL={
										customCollectionSelectorURL
									}
									index={index}
								>
									{child}
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
	customCollectionSelectorURL,
	index,
}) => {
	const toControlsId = useToControlsId();

	const contextValue = useMemo(
		() => ({
			collectionConfig,
			collectionItem,
			collectionItemIndex: index,
			customCollectionSelectorURL,
			fromControlsId,
			parentToControlsId: toControlsId,
			toControlsId: getToControlsId(collectionId, index, toControlsId),
		}),
		[
			collectionConfig,
			collectionId,
			collectionItem,
			index,
			toControlsId,
			customCollectionSelectorURL,
		]
	);

	return (
		<CollectionItemContextProvider value={contextValue}>
			{children}
		</CollectionItemContextProvider>
	);
};

const Collection = React.memo(
	React.forwardRef(({children, item, withinTopper = false}, ref) => {
		const child = React.Children.toArray(children)[0];
		const collectionConfig = item.config;
		const emptyCollection = useMemo(
			() => ({
				fakeCollection: true,
				items: Array.from(
					Array(collectionConfig.numberOfItems || 1),
					() => ({})
				),
				length: collectionConfig.numberOfItems || 1,
				totalNumberOfItems: collectionConfig.numberOfItems || 1,
			}),
			[collectionConfig.numberOfItems]
		);

		const dispatch = useDispatch();
		const languageId = useSelector(selectLanguageId);

		const [activePage, setActivePage] = useState(1);
		const [collection, setCollection] = useState(emptyCollection);
		const [loading, setLoading] = useState(false);

		const totalPages = Math.ceil(
			Math.min(
				collectionConfig.numberOfItems,
				collection.totalNumberOfItems
			) / collectionConfig.numberOfItemsPerPage
		);

		useEffect(() => {
			if (activePage > totalPages) {
				setActivePage(1);
			}
		}, [
			collectionConfig.numberOfItems,
			collectionConfig.numberOfItemsPerPage,
			activePage,
			totalPages,
		]);

		const context = useContext(CollectionItemContext);
		const {classNameId, classPK} = context.collectionItem || {};

		const displayPagePreviewItemData =
			useDisplayPagePreviewItem()?.data ?? {};

		const itemClassNameId =
			classNameId || displayPagePreviewItemData.classNameId;
		const itemClassPK = classPK || displayPagePreviewItemData.classPK;

		useEffect(() => {
			if (collectionConfig.collection && activePage <= totalPages) {
				setLoading(true);

				CollectionService.getCollectionField({
					activePage,
					classNameId: itemClassNameId,
					classPK: itemClassPK,
					collection: collectionConfig.collection,
					languageId,
					listItemStyle: collectionConfig.listItemStyle || null,
					listStyle: collectionConfig.listStyle,
					numberOfItems: collectionConfig.numberOfItems,
					numberOfItemsPerPage: collectionConfig.numberOfItemsPerPage,
					onNetworkStatus: dispatch,
					paginationType: collectionConfig.paginationType,
					templateKey: collectionConfig.templateKey || null,
				})
					.then((response) => {
						const {itemSubtype, itemType, ...collection} = response;

						setCollection(
							collection.length > 0 &&
								collection.items?.length > 0
								? collection
								: {...collection, ...emptyCollection}
						);

						// LPS-133832
						// Update itemType/itemSubtype if the user changes the type of the collection

						const {
							itemSubtype: previousItemSubtype,
							itemType: previousItemType,
						} = collectionConfig?.collection ?? {};

						if (
							(!isNullOrUndefined(itemType) &&
								itemType !== previousItemType) ||
							(!isNullOrUndefined(itemSubtype) &&
								itemSubtype !== previousItemSubtype)
						) {
							const nextItemType = isNullOrUndefined(itemType)
								? previousItemType
								: itemType;

							const nextItemSubtype = isNullOrUndefined(
								itemSubtype
							)
								? previousItemSubtype
								: itemSubtype;

							dispatch(
								updateItemConfig({
									itemConfig: {
										...collectionConfig,
										collection: {
											...collectionConfig.collection,
											itemSubtype: nextItemSubtype,
											itemType: nextItemType,
										},
									},
									itemId: item.itemId,
								})
							);
						}
					})
					.catch((error) => {
						if (process.env.NODE_ENV === 'development') {
							console.error(error);
						}
					})
					.finally(() => {
						setLoading(false);
					});
			}
		}, [
			activePage,
			collectionConfig,
			dispatch,
			emptyCollection,
			item.itemId,
			itemClassNameId,
			itemClassPK,
			languageId,
			totalPages,
		]);

		const selectedViewportSize = useSelector(
			(state) => state.selectedViewportSize
		);

		const responsiveConfig = getResponsiveConfig(
			item.config,
			selectedViewportSize
		);

		const {display} = responsiveConfig.styles;

		const style = {};

		if (!withinTopper) {
			style.display = display;
		}

		const showEmptyMessage =
			collectionConfig.listStyle !== '' && collection.fakeCollection;

		return (
			<div className="page-editor__collection" ref={ref} style={style}>
				{loading ? (
					<ClayLoadingIndicator />
				) : !collectionIsMapped(collectionConfig) ? (
					<NotCollectionSelectedMessage />
				) : showEmptyMessage ? (
					<EmptyCollectionMessage />
				) : collection.content ? (
					<UnsafeHTML markup={collection.content} />
				) : (
					<>
						{collection.fakeCollection && (
							<EmptyCollectionGridMessage />
						)}
						<Grid
							child={child}
							collection={collection.items}
							collectionConfig={collectionConfig}
							collectionId={item.itemId}
							collectionLength={collection.items.length}
							customCollectionSelectorURL={
								collection.customCollectionSelectorURL
							}
						/>
					</>
				)}

				{collectionConfig.paginationType && (
					<CollectionPagination
						activePage={activePage}
						collectionConfig={collectionConfig}
						collectionId={item.itemId}
						onPageChange={setActivePage}
						totalNumberOfItems={
							collection.fakeCollection
								? 0
								: collection.totalNumberOfItems
						}
						totalPages={totalPages}
					/>
				)}
			</div>
		);
	})
);

Collection.displayName = 'Collection';

export default Collection;
