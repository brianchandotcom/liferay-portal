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

import {useModal} from '@clayui/modal';
import {openConfirmModal} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

import {COLLECTION_APPLIED_FILTERS_FRAGMENT_ENTRY_KEY} from '../../../../../../../app/config/constants/collectionAppliedFiltersFragmentKey';
import {COLLECTION_FILTER_FRAGMENT_ENTRY_KEY} from '../../../../../../../app/config/constants/collectionFilterFragmentEntryKey';
import {COMMON_STYLES_ROLES} from '../../../../../../../app/config/constants/commonStylesRoles';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../../app/config/constants/freemarkerFragmentEntryProcessor';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../../../app/config/constants/layoutDataItemTypes';
import {VIEWPORT_SIZES} from '../../../../../../../app/config/constants/viewportSizes';
import {config} from '../../../../../../../app/config/index';
import {
	useDispatch,
	useGetState,
	useSelector,
} from '../../../../../../../app/contexts/StoreContext';
import selectSegmentsExperienceId from '../../../../../../../app/selectors/selectSegmentsExperienceId';
import CollectionService from '../../../../../../../app/services/CollectionService';
import InfoItemService from '../../../../../../../app/services/InfoItemService';
import updateCollectionDisplayCollection from '../../../../../../../app/thunks/updateCollectionDisplayCollection';
import updateItemConfig from '../../../../../../../app/thunks/updateItemConfig';
import {getResponsiveConfig} from '../../../../../../../app/utils/getResponsiveConfig';
import {useId} from '../../../../../../../app/utils/useId';
import Collapse from '../../../../../../../common/components/Collapse';
import CollectionSelector from '../../../../../../../common/components/CollectionSelector';
import CollectionFilterConfigurationModal from '../../CollectionFilterConfigurationModal';
import {CommonStyles} from '../CommonStyles';
import {LayoutSelector} from './LayoutSelector';
import {ListItemStyleSelector} from './ListItemStyleSelector';
import {NoPaginationOptions} from './NoPaginationOptions';
import {PaginationOptions} from './PaginationOptions';
import {PaginationSelector} from './PaginationSelector';
import {ShowGutterSelector} from './ShowGutterSelector';
import {StyleDisplaySelector} from './StyleDisplaySelector';
import {VerticalAlignmentSelector} from './VerticalAlignmentSelector';

const LIST_STYLE_GRID = '';

export function CollectionGeneralPanel({item}) {
	const {
		collection,
		displayAllItems,
		displayAllPages,
		listStyle,
		numberOfColumns,
		numberOfItems: initialNumberOfItems,
		numberOfItemsPerPage: initialNumberOfItemsPerPage,
		numberOfPages: initialNumberOfPages,
		paginationType,
	} = item.config;

	const [availableListItemStyles, setAvailableListItemStyles] = useState([]);
	const [collectionConfiguration, setCollectionConfiguration] = useState(
		null
	);
	const collectionItemType = collection?.itemType || null;
	const collectionLayoutId = useId();
	const collectionListItemStyleId = useId();
	const collectionPaginationTypeId = useId();
	const collectionVerticalAlignmentId = useId();
	const dispatch = useDispatch();
	const getState = useGetState();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const {
		observer: filterConfigurationObserver,
		onClose: onFilterConfigurationClose,
	} = useModal({onClose: () => setFilterConfigurationVisible(false)});

	const [
		filterConfigurationVisible,
		setFilterConfigurationVisible,
	] = useState(false);

	const optionsMenuItems = useMemo(
		() =>
			collectionConfiguration
				? [
						{
							label: Liferay.Language.get('prefilter-collection'),
							onClick: () => setFilterConfigurationVisible(true),
							symbolLeft: 'filter',
						},
				  ]
				: [],
		[collectionConfiguration, setFilterConfigurationVisible]
	);

	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const collectionConfig = getResponsiveConfig(
		item.config,
		selectedViewportSize
	);

	const handleCollectionSelect = (collection = {}) => {
		dispatch(
			updateCollectionDisplayCollection({
				collection: Object.keys(collection).length ? collection : null,
				itemId: item.itemId,
				listStyle: LIST_STYLE_GRID,
			})
		);
	};

	const onPreventCollectionSelect = useCallback(
		(callback) => {
			const state = getState();

			const isLinkedToFilter = Object.values(state.layoutData.items).some(
				(layoutDataItem) => {
					if (
						layoutDataItem.type !== LAYOUT_DATA_ITEM_TYPES.fragment
					) {
						return false;
					}

					const fragmentEntryLink =
						state.fragmentEntryLinks[
							layoutDataItem.config.fragmentEntryLinkId
						];

					if (
						fragmentEntryLink.fragmentEntryKey !==
							COLLECTION_FILTER_FRAGMENT_ENTRY_KEY &&
						fragmentEntryLink.fragmentEntryKey !==
							COLLECTION_APPLIED_FILTERS_FRAGMENT_ENTRY_KEY
					) {
						return false;
					}

					return fragmentEntryLink.editableValues[
						FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
					]?.targetCollections?.includes(item.itemId);
				}
			);

			if (isLinkedToFilter) {
				openConfirmModal({
					message: `${Liferay.Language.get(
						'if-you-change-the-collection-you-unlink-the-collection-filter'
					)}\n\n${Liferay.Language.get('do-you-want-to-continue')}`,
					onConfirm: (isConfirmed) => {
						if (isConfirmed) {
							callback(isConfirmed);
						}
					},
				});
			}
			else {
				callback(false);
			}
		},
		[getState, item.itemId]
	);

	const handleConfigurationChanged = useCallback(
		(itemConfig) => {
			if (selectedViewportSize !== VIEWPORT_SIZES.desktop) {
				itemConfig = {[selectedViewportSize]: itemConfig};
			}

			dispatch(
				updateItemConfig({
					itemConfig,
					itemId: item.itemId,
					segmentsExperienceId,
				})
			);
		},
		[item.itemId, dispatch, segmentsExperienceId, selectedViewportSize]
	);

	useEffect(() => {
		if (collection && listStyle && listStyle !== LIST_STYLE_GRID) {
			InfoItemService.getAvailableListItemRenderers({
				itemSubtype: collection.itemSubtype,
				itemType: collection.itemType,
				listStyle,
			})
				.then((response) => {
					setAvailableListItemStyles(response);
				})
				.catch(() => {
					setAvailableListItemStyles([]);
				});
		}
	}, [collection, listStyle]);

	useEffect(() => {
		if (collection?.key) {
			CollectionService.getCollectionConfiguration(collection)
				.then((nextCollectionConfiguration) => {
					if (
						nextCollectionConfiguration?.fieldSets.some(
							(fieldSet) => fieldSet?.fields?.length
						)
					) {
						setCollectionConfiguration(nextCollectionConfiguration);
					}
					else {
						setCollectionConfiguration(null);
					}
				})
				.catch(() => setCollectionConfiguration(null));
		}
		else {
			setCollectionConfiguration(null);
		}
	}, [collection]);

	return (
		<>
			<div className="mb-3">
				<Collapse
					label={Liferay.Language.get('collection-display-options')}
					open
				>
					{selectedViewportSize === VIEWPORT_SIZES.desktop && (
						<CollectionSelector
							collectionItem={collection}
							itemSelectorURL={config.collectionSelectorURL}
							label={Liferay.Language.get('collection')}
							onCollectionSelect={handleCollectionSelect}
							onPreventCollectionSelect={
								onPreventCollectionSelect
							}
							optionsMenuItems={optionsMenuItems}
						/>
					)}

					{collection && (
						<>
							{selectedViewportSize ===
								VIEWPORT_SIZES.desktop && (
								<StyleDisplaySelector
									collectionItemType={collectionItemType}
									handleConfigurationChanged={
										handleConfigurationChanged
									}
									listStyle={listStyle}
								/>
							)}

							{listStyle === LIST_STYLE_GRID && (
								<>
									<LayoutSelector
										collectionConfig={collectionConfig}
										collectionLayoutId={collectionLayoutId}
										handleConfigurationChanged={
											handleConfigurationChanged
										}
									/>

									{selectedViewportSize ===
										VIEWPORT_SIZES.desktop && (
										<>
											{numberOfColumns > 1 && (
												<ShowGutterSelector
													checked={
														item.config.gutters
													}
													handleConfigurationChanged={
														handleConfigurationChanged
													}
												/>
											)}

											<VerticalAlignmentSelector
												collectionVerticalAlignmentId={
													collectionVerticalAlignmentId
												}
												handleConfigurationChanged={
													handleConfigurationChanged
												}
												value={
													item.config
														.verticalAlignment
												}
											/>
										</>
									)}
								</>
							)}
							{selectedViewportSize ===
								VIEWPORT_SIZES.desktop && (
								<>
									{listStyle !== LIST_STYLE_GRID &&
										!!availableListItemStyles.length && (
											<ListItemStyleSelector
												availableListItemStyles={
													availableListItemStyles
												}
												collectionListItemStyleId={
													collectionListItemStyleId
												}
												handleConfigurationChanged={
													handleConfigurationChanged
												}
												item={item}
											/>
										)}

									<PaginationSelector
										collectionPaginationTypeId={
											collectionPaginationTypeId
										}
										handleConfigurationChanged={
											handleConfigurationChanged
										}
										value={paginationType || 'none'}
									/>

									{paginationType !== 'none' ? (
										<PaginationOptions
											displayAllPages={displayAllPages}
											handleConfigurationChanged={
												handleConfigurationChanged
											}
											initialNumberOfItemsPerPage={
												initialNumberOfItemsPerPage
											}
											initialNumberOfPages={
												initialNumberOfPages
											}
										/>
									) : (
										<NoPaginationOptions
											collection={collection}
											displayAllItems={displayAllItems}
											handleConfigurationChanged={
												handleConfigurationChanged
											}
											initialNumberOfItems={
												initialNumberOfItems
											}
										/>
									)}
								</>
							)}
						</>
					)}
				</Collapse>

				{filterConfigurationVisible ? (
					<CollectionFilterConfigurationModal
						collectionConfiguration={collectionConfiguration}
						handleConfigurationChanged={handleConfigurationChanged}
						itemConfig={item.config}
						observer={filterConfigurationObserver}
						onClose={onFilterConfigurationClose}
					/>
				) : null}
			</div>

			<CommonStyles
				commonStylesValues={collectionConfig.styles}
				item={item}
				role={COMMON_STYLES_ROLES.general}
			/>
		</>
	);
}

CollectionGeneralPanel.propTypes = {
	item: PropTypes.object.isRequired,
};
