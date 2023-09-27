/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {isNullOrUndefined} from '@liferay/layout-js-components-web';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {useContext, useEffect, useMemo, useState} from 'react';

import {COLUMN_SIZE_MODULE_PER_ROW_SIZES} from '../../config/constants/columnSizes';
import {CONTENT_DISPLAY_OPTIONS} from '../../config/constants/contentDisplayOptions';
import {config} from '../../config/index';
import {
	CollectionItemContext,
	CollectionItemContextProvider,
	useToControlsId,
} from '../../contexts/CollectionItemContext';
import {useDisplayPagePreviewItem} from '../../contexts/DisplayPagePreviewItemContext';
import {useDispatch, useSelector} from '../../contexts/StoreContext';
import selectLanguageId from '../../selectors/selectLanguageId';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';
import CollectionService from '../../services/CollectionService';
import updateItemConfig from '../../thunks/updateItemConfig';
import {collectionIsMapped} from '../../utils/collectionIsMapped';
import getLayoutDataItemClassName from '../../utils/getLayoutDataItemClassName';
import getLayoutDataItemUniqueClassName from '../../utils/getLayoutDataItemUniqueClassName';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import UnsafeHTML from '../UnsafeHTML';
import CollectionPagination from './CollectionPagination';

const COLLECTION_ID_DIVIDER = '$';

function paginationIsEnabled(collectionConfig) {
	return collectionConfig.paginationType !== 'none';
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

const EditModeMaxItemsAlert = () => (
	<div className="alert alert-fluid alert-info">
		<div className="container-fluid">
			{sub(
				Liferay.Language.get(
					'in-edit-mode,-the-number-of-elements-displayed-is-limited-to-x-due-to-performance'
				),
				config.maxNumberOfItemsInEditMode
			)}
		</div>
	</div>
);

const FlexContainer = ({
	child,
	collection,
	collectionConfig,
	collectionId,
	collectionLength,
}) => {
	const {align, flexWrap, justify, listStyle} = collectionConfig;

	const maxNumberOfItems =
		Math.min(
			collectionLength,
			getNumberOfItems(collection, collectionConfig)
		) || 1;

	const numberOfItemsToDisplay = Math.min(
		maxNumberOfItems,
		config.maxNumberOfItemsInEditMode
	);

	return (
		<div
			className={classNames({
				[align]: !!align,
				'd-flex flex-column':
					listStyle === CONTENT_DISPLAY_OPTIONS.flexColumn,
				'd-flex flex-row':
					listStyle === CONTENT_DISPLAY_OPTIONS.flexRow,
				[flexWrap]: Boolean(flexWrap),
				[justify]: Boolean(justify),
			})}
		>
			{Array.from({length: numberOfItemsToDisplay}).map((_, index) => (
				<ItemContext
					collectionConfig={collectionConfig}
					collectionId={collectionId}
					collectionItem={collection.items[index] ?? {}}
					customCollectionSelectorURL={
						collection.customCollectionSelectorURL
					}
					index={index}
					key={index}
				>
					{child}
				</ItemContext>
			))}
		</div>
	);
};

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
			getNumberOfItems(collection, collectionConfig)
		) || 1;

	const numberOfItemsToDisplay = Math.min(
		maxNumberOfItems,
		config.maxNumberOfItemsInEditMode
	);

	const numberOfRows = Math.ceil(
		numberOfItemsToDisplay / collectionConfig.numberOfColumns
	);

	return (
		<>
			{Array.from({length: numberOfRows}).map((_, i) => (
				<ClayLayout.Row
					className={classNames(
						`align-items-${collectionConfig.verticalAlignment}`,
						{
							'no-gutters': !collectionConfig.gutters,
						}
					)}
					key={`row-${i}`}
				>
					{Array.from({length: collectionConfig.numberOfColumns}).map(
						(_, j) => {
							const key = `col-${i}-${j}`;
							const index =
								i * collectionConfig.numberOfColumns + j;

							return (
								<ClayLayout.Col
									key={key}
									size={
										COLUMN_SIZE_MODULE_PER_ROW_SIZES[
											collectionConfig.numberOfColumns
										][collectionConfig.numberOfColumns][j]
									}
								>
									{index < numberOfItemsToDisplay && (
										<ItemContext
											collectionConfig={collectionConfig}
											collectionId={collectionId}
											collectionItem={
												collection.items[index] ?? {}
											}
											customCollectionSelectorURL={
												customCollectionSelectorURL
											}
											index={index}
										>
											{child}
										</ItemContext>
									)}
								</ClayLayout.Col>
							);
						}
					)}
				</ClayLayout.Row>
			))}
			{maxNumberOfItems > config.maxNumberOfItemsInEditMode && (
				<EditModeMaxItemsAlert />
			)}
		</>
	);
};

const ItemContext = ({
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
	React.forwardRef(({children, item}, ref) => {
		const child = React.Children.toArray(children)[0];
		const collectionConfig = item.config;
		const emptyCollection = useMemo(
			() => ({
				fakeCollection: true,
				items: {length: collectionConfig.numberOfItems || 1},
				length: collectionConfig.numberOfItems || 1,
				totalNumberOfItems: collectionConfig.numberOfItems || 1,
			}),
			[collectionConfig.numberOfItems]
		);

		const dispatch = useDispatch();
		const languageId = useSelector(selectLanguageId);

		const [activePage, setActivePage] = useState(1);
		const [collection, setCollection] = useState(emptyCollection);
		const [loading, setLoading] = useState(!!collectionConfig.collection);

		const numberOfItems = getNumberOfItems(collection, collectionConfig);

		useEffect(() => {
			if (
				activePage > collectionConfig.numberOfPages &&
				!collectionConfig.displayAllPages
			) {
				setActivePage(1);
			}
		}, [
			collectionConfig.displayAllPages,
			collectionConfig.numberOfItems,
			collectionConfig.numberOfItemsPerPage,
			collectionConfig.numberOfPages,
			activePage,
		]);

		const context = useContext(CollectionItemContext);
		const {classNameId, classPK, externalReferenceCode} =
			context.collectionItem || {};

		const displayPagePreviewItemData =
			useDisplayPagePreviewItem()?.data ?? {};

		const itemClassNameId =
			classNameId || displayPagePreviewItemData.classNameId;
		const itemClassPK = classPK || displayPagePreviewItemData.classPK;
		const itemExternalReferenceCode =
			externalReferenceCode ||
			displayPagePreviewItemData.externalReferenceCode;
		const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

		useEffect(() => {
			if (
				collectionConfig.collection &&
				(activePage <= collectionConfig.numberOfPages ||
					collectionConfig.displayAllPages)
			) {
				setLoading(true);

				CollectionService.getCollectionField({
					activePage,
					classNameId: itemClassNameId,
					classPK: itemClassPK,
					collection: collectionConfig.collection,
					displayAllItems: collectionConfig.displayAllItems,
					displayAllPages: collectionConfig.displayAllPages,
					externalReferenceCode: itemExternalReferenceCode,
					languageId,
					listItemStyle: collectionConfig.listItemStyle || null,
					listStyle: collectionConfig.listStyle,
					numberOfItems: collectionConfig.numberOfItems,
					numberOfItemsPerPage: collectionConfig.numberOfItemsPerPage,
					numberOfPages: collectionConfig.numberOfPages,
					onNetworkStatus: dispatch,
					paginationType: collectionConfig.paginationType,
					segmentsExperienceId,
					templateKey: collectionConfig.templateKey || null,
				})
					.then((response) => {
						const {itemSubtype, itemType, ...collection} = response;

						setCollection(
							!!collection.length && collection.items?.length > 0
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
			itemExternalReferenceCode,
			languageId,
			segmentsExperienceId,
		]);

		const selectedViewportSize = useSelector(
			(state) => state.selectedViewportSize
		);

		const responsiveConfig = getResponsiveConfig(
			item.config,
			selectedViewportSize
		);

		const showEmptyMessage =
			collectionConfig.listStyle !== '' && collection.fakeCollection;

		const flexEnabled =
			collectionConfig.listStyle === CONTENT_DISPLAY_OPTIONS.flexColumn ||
			collectionConfig.listStyle === CONTENT_DISPLAY_OPTIONS.flexRow;

		let CollectionContent = null;

		if (Liferay.FeatureFlags['LPS-169923'] && collection.isRestricted) {
			CollectionContent = (
				<ClayAlert displayType="secondary" role={null}>
					{Liferay.Language.get(
						'this-content-cannot-be-displayed-due-to-permission-restrictions'
					)}
				</ClayAlert>
			);
		}
		else if (loading) {
			CollectionContent = <ClayLoadingIndicator />;
		}
		else if (!collectionIsMapped(collectionConfig)) {
			CollectionContent = <NotCollectionSelectedMessage />;
		}
		else if (showEmptyMessage) {
			CollectionContent = <EmptyCollectionMessage />;
		}
		else if (collection.content) {
			CollectionContent = <UnsafeHTML markup={collection.content} />;
		}
		else {
			CollectionContent = (
				<>
					{collection.fakeCollection && (
						<EmptyCollectionGridMessage />
					)}
					{flexEnabled ? (
						<FlexContainer
							child={child}
							collection={collection}
							collectionConfig={responsiveConfig}
							collectionId={item.itemId}
							collectionLength={collection.items.length}
						/>
					) : (
						<Grid
							child={child}
							collection={collection}
							collectionConfig={responsiveConfig}
							collectionId={item.itemId}
							collectionLength={collection.items.length}
							customCollectionSelectorURL={
								collection.customCollectionSelectorURL
							}
						/>
					)}
				</>
			);
		}

		return (
			<div
				className={classNames(
					'page-editor__collection',
					getLayoutDataItemUniqueClassName(item.itemId),
					getLayoutDataItemClassName(item.type)
				)}
				ref={ref}
			>
				{CollectionContent}

				{!collection.isRestricted &&
					collectionIsMapped(collectionConfig) &&
					paginationIsEnabled(collectionConfig) && (
						<CollectionPagination
							activePage={activePage}
							collectionConfig={collectionConfig}
							collectionId={item.itemId}
							onPageChange={setActivePage}
							totalNumberOfItems={
								collection.fakeCollection ? 0 : numberOfItems
							}
							totalPages={getNumberOfPages(
								collection,
								collectionConfig
							)}
						/>
					)}
			</div>
		);
	})
);

Collection.displayName = 'Collection';

function getNumberOfItems(collection, collectionConfig) {
	if (paginationIsEnabled(collectionConfig)) {
		const itemsPerPage = Math.min(
			collectionConfig.numberOfItemsPerPage,
			config.searchContainerPageMaxDelta
		);

		return collectionConfig.displayAllPages
			? collection.totalNumberOfItems
			: Math.min(
					collectionConfig.numberOfPages * itemsPerPage,
					collection.totalNumberOfItems
			  );
	}

	return collectionConfig.displayAllItems
		? collection.totalNumberOfItems
		: Math.min(
				collectionConfig.numberOfItems,
				collection.totalNumberOfItems
		  );
}

function getNumberOfPages(collection, collectionConfig) {
	const itemsPerPage = Math.min(
		collectionConfig.numberOfItemsPerPage,
		config.searchContainerPageMaxDelta
	);

	return collectionConfig.displayAllPages
		? Math.ceil(collection.totalNumberOfItems / itemsPerPage)
		: Math.min(
				Math.ceil(collection.totalNumberOfItems / itemsPerPage),
				collectionConfig.numberOfPages
		  );
}

export default Collection;
