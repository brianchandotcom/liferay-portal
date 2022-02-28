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

import ClayForm, {
	ClayCheckbox,
	ClayInput,
	ClaySelect,
	ClaySelectWithOption,
} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useModal} from '@clayui/modal';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

import {COLLECTION_APPLIED_FILTERS_FRAGMENT_ENTRY_KEY} from '../../../../../../app/config/constants/collectionAppliedFiltersFragmentKey';
import {COLLECTION_FILTER_FRAGMENT_ENTRY_KEY} from '../../../../../../app/config/constants/collectionFilterFragmentEntryKey';
import {COMMON_STYLES_ROLES} from '../../../../../../app/config/constants/commonStylesRoles';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../app/config/constants/freemarkerFragmentEntryProcessor';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../../app/config/constants/layoutDataItemTypes';
import {config} from '../../../../../../app/config/index';
import {
	useDispatch,
	useGetState,
	useSelector,
} from '../../../../../../app/contexts/StoreContext';
import selectSegmentsExperienceId from '../../../../../../app/selectors/selectSegmentsExperienceId';
import CollectionService from '../../../../../../app/services/CollectionService';
import InfoItemService from '../../../../../../app/services/InfoItemService';
import updateCollectionDisplayCollection from '../../../../../../app/thunks/updateCollectionDisplayCollection';
import updateItemConfig from '../../../../../../app/thunks/updateItemConfig';
import {getResponsiveConfig} from '../../../../../../app/utils/getResponsiveConfig';
import {useId} from '../../../../../../app/utils/useId';
import Collapse from '../../../../../../common/components/Collapse';
import CollectionSelector from '../../../../../../common/components/CollectionSelector';
import useControlledState from '../../../../../../core/hooks/useControlledState';
import CollectionFilterConfigurationModal from '../CollectionFilterConfigurationModal';
import {CommonStyles} from './CommonStyles';

const LAYOUT_OPTIONS = [
	{label: Liferay.Language.get('full-width'), value: '1'},
	{label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 2), value: '2'},
	{label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 3), value: '3'},
	{label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 4), value: '4'},
	{label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 5), value: '5'},
	{label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 6), value: '6'},
];

const PAGINATION_TYPE_OPTIONS = [
	{label: Liferay.Language.get('none'), value: ''},
	{label: Liferay.Language.get('numeric'), value: 'numeric'},
	{label: Liferay.Language.get('simple'), value: 'simple'},
];

const LIST_STYLE_GRID = '';

const DEFAULT_LIST_STYLE = {
	label: Liferay.Language.get('grid'),
	value: LIST_STYLE_GRID,
};

const ERROR_MESSAGES = {
	maximumItems: Liferay.Language.get(
		'the-current-number-of-items-in-this-collection-is-x'
	),
	maximumItemsPerPage: Liferay.Language.get(
		'you-can-only-display-a-maximum-of-x-items-per-page'
	),
	noItems: Liferay.Language.get('this-collection-has-no-items'),
};

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
	const [availableListStyles, setAvailableListStyles] = useState([
		DEFAULT_LIST_STYLE,
	]);
	const [collectionConfiguration, setCollectionConfiguration] = useState(
		null
	);
	const collectionItemType = collection?.itemType || null;
	const collectionLayoutId = useId();
	const collectionListItemStyleId = useId();
	const collectionPaginationTypeId = useId();
	const dispatch = useDispatch();
	const getState = useGetState();
	const isMounted = useIsMounted();
	const listStyleId = useId();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const [totalNumberOfItems, setTotalNumberOfItems] = useState(0);

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
							label: Liferay.Language.get('filter-collection'),
							onClick: () => setFilterConfigurationVisible(true),
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

	const handleCollectionListItemStyleChanged = ({target}) => {
		const options = target.options;

		handleConfigurationChanged({
			listItemStyle: options[target.selectedIndex].dataset.key,
			templateKey: options[target.selectedIndex].dataset.templateKey,
		});
	};

	const handleCollectionSelect = (collection = {}) => {
		dispatch(
			updateCollectionDisplayCollection({
				collection: Object.keys(collection).length ? collection : null,
				itemId: item.itemId,
				listStyle: LIST_STYLE_GRID,
			})
		);
	};

	const shouldPreventCollectionSelect = () => {
		const state = getState();

		const isLinkedToFilter = Object.values(state.layoutData.items).some(
			(layoutDataItem) => {
				if (layoutDataItem.type !== LAYOUT_DATA_ITEM_TYPES.fragment) {
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

		return (
			isLinkedToFilter &&
			!confirm(
				`${Liferay.Language.get(
					'if-you-change-the-collection-you-unlink-the-collection-filter'
				)}\n\n${Liferay.Language.get('do-you-want-to-continue')}`
			)
		);
	};

	const handleConfigurationChanged = useCallback(
		(itemConfig) => {
			dispatch(
				updateItemConfig({
					itemConfig,
					itemId: item.itemId,
					segmentsExperienceId,
				})
			);
		},
		[item.itemId, dispatch, segmentsExperienceId]
	);

	useEffect(() => {
		if (collectionItemType) {
			InfoItemService.getAvailableListRenderers({
				className: collectionItemType,
			})
				.then((response) => {
					setAvailableListStyles([
						DEFAULT_LIST_STYLE,
						{
							label: Liferay.Language.get('templates'),
							options: response,
							type: 'group',
						},
					]);
				})
				.catch(() => {
					setAvailableListStyles([DEFAULT_LIST_STYLE]);
				});
		}
	}, [collectionItemType]);

	useEffect(() => {
		if (collection) {
			CollectionService.getCollectionItemCount({
				collection,
				onNetworkStatus: () => {},
			}).then(({totalNumberOfItems}) => {
				if (isMounted()) {
					setTotalNumberOfItems(totalNumberOfItems);
				}
			});
		}
	}, [collection, isMounted]);

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
					<CollectionSelector
						collectionItem={collection}
						itemSelectorURL={config.collectionSelectorURL}
						label={Liferay.Language.get('collection')}
						onCollectionSelect={handleCollectionSelect}
						optionsMenuItems={optionsMenuItems}
						shouldPreventCollectionSelect={
							shouldPreventCollectionSelect
						}
					/>

					{collection && (
						<>
							<ClayForm.Group small>
								<label htmlFor={listStyleId}>
									{Liferay.Language.get('list-style')}
								</label>

								<ClaySelectWithOption
									aria-label={Liferay.Language.get(
										'list-style'
									)}
									id={listStyleId}
									onChange={(event) =>
										handleConfigurationChanged({
											listStyle: event.target.value,
										})
									}
									options={availableListStyles}
									value={listStyle}
								/>
							</ClayForm.Group>

							{listStyle === LIST_STYLE_GRID && (
								<ClayForm.Group small>
									<label htmlFor={collectionLayoutId}>
										{Liferay.Language.get('layout')}
									</label>

									<ClaySelectWithOption
										aria-label={Liferay.Language.get(
											'layout'
										)}
										id={collectionLayoutId}
										onChange={(event) =>
											handleConfigurationChanged({
												numberOfColumns:
													event.target.value,
											})
										}
										options={LAYOUT_OPTIONS}
										value={numberOfColumns}
									/>
								</ClayForm.Group>
							)}

							{listStyle !== LIST_STYLE_GRID &&
								availableListItemStyles.length > 0 && (
									<ClayForm.Group small>
										<label
											htmlFor={collectionListItemStyleId}
										>
											{Liferay.Language.get(
												'list-item-style'
											)}
										</label>

										<ClaySelect
											aria-label={Liferay.Language.get(
												'list-item-style'
											)}
											id={collectionListItemStyleId}
											onChange={
												handleCollectionListItemStyleChanged
											}
										>
											<ListItemStylesOptions
												item={item}
												listItemStyles={
													availableListItemStyles
												}
											/>
										</ClaySelect>
									</ClayForm.Group>
								)}

							<ClayForm.Group small>
								<label htmlFor={collectionPaginationTypeId}>
									{Liferay.Language.get('pagination')}
								</label>

								<ClaySelectWithOption
									aria-label={Liferay.Language.get(
										'pagination'
									)}
									id={collectionPaginationTypeId}
									onChange={(event) =>
										handleConfigurationChanged({
											paginationType: event.target.value,
										})
									}
									options={PAGINATION_TYPE_OPTIONS}
									value={paginationType || ''}
								/>
							</ClayForm.Group>

							{paginationType ? (
								<PaginationOptions
									displayAllPages={displayAllPages}
									handleConfigurationChanged={
										handleConfigurationChanged
									}
									initialNumberOfItemsPerPage={
										initialNumberOfItemsPerPage
									}
									initialNumberOfPages={initialNumberOfPages}
								/>
							) : (
								<NoPaginationOptions
									displayAllItems={displayAllItems}
									handleConfigurationChanged={
										handleConfigurationChanged
									}
									initialNumberOfItems={initialNumberOfItems}
									totalNumberOfItems={totalNumberOfItems}
								/>
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

function NoPaginationOptions({
	displayAllItems,
	handleConfigurationChanged,
	initialNumberOfItems,
	totalNumberOfItems,
}) {
	const collectionNumberOfItemsId = useId();

	const [numberOfItems, setNumberOfItems] = useControlledState(
		initialNumberOfItems
	);
	const [numberOfItemsError, setNumberOfItemsError] = useState(null);

	useEffect(() => {
		let errorMessage = null;

		if (totalNumberOfItems) {
			if (initialNumberOfItems > totalNumberOfItems) {
				errorMessage = Liferay.Util.sub(
					ERROR_MESSAGES.maximumItems,
					totalNumberOfItems
				);
			}
		}
		else {
			errorMessage = ERROR_MESSAGES.noItems;
		}

		setNumberOfItemsError(errorMessage);
	}, [totalNumberOfItems, initialNumberOfItems]);

	const handleDisplayAllItemsChanged = (event) =>
		handleConfigurationChanged({
			displayAllItems: event.target.checked,
		});

	const handleCollectionNumberOfItemsBlurred = (event) => {
		const nextValue = Math.abs(Number(event.target.value)) || 1;

		if (!numberOfItems || numberOfItems < 0) {
			setNumberOfItems(nextValue);
		}

		if (nextValue !== initialNumberOfItems) {
			handleConfigurationChanged({
				numberOfItems: nextValue,
			});
		}
	};

	return (
		<>
			<div className="mb-2 pt-1">
				<ClayCheckbox
					checked={displayAllItems}
					label={Liferay.Language.get('display-all-collection-items')}
					onChange={handleDisplayAllItemsChanged}
				/>
			</div>

			{displayAllItems && (
				<p className="mt-1 small text-secondary">
					{Liferay.Util.sub(
						Liferay.Language.get(
							'this-setting-can-affect-page-performance-severely-if-the-number-of-collection-items-is-above-x.-we-strongly-recommend-using-pagination-instead'
						),
						config.searchContainerPageMaxDelta
					)}
				</p>
			)}

			{!displayAllItems && (
				<ClayForm.Group
					className={classNames({
						'has-warning': numberOfItemsError,
					})}
					small
				>
					<label htmlFor={collectionNumberOfItemsId}>
						{Liferay.Language.get(
							'maximum-number-of-items-to-display'
						)}
					</label>

					<ClayInput
						id={collectionNumberOfItemsId}
						min="1"
						onBlur={handleCollectionNumberOfItemsBlurred}
						onChange={(event) =>
							setNumberOfItems(Number(event.target.value))
						}
						type="number"
						value={numberOfItems || ''}
					/>

					<p className="mt-1 small text-secondary">
						{Liferay.Util.sub(
							Liferay.Language.get(
								'setting-a-value-above-x-can-affect-page-performance-severely'
							),
							config.searchContainerPageMaxDelta
						)}
					</p>

					{numberOfItemsError && (
						<FeedbackMessage message={numberOfItemsError} />
					)}
				</ClayForm.Group>
			)}
		</>
	);
}

NoPaginationOptions.propTypes = {
	displayAllItems: PropTypes.bool.isRequired,
	handleConfigurationChanged: PropTypes.func.isRequired,
	initialNumberOfItems: PropTypes.number.isRequired,
	totalNumberOfItems: PropTypes.number.isRequired,
};

function PaginationOptions({
	displayAllPages,
	handleConfigurationChanged,
	initialNumberOfItemsPerPage,
	initialNumberOfPages,
}) {
	const collectionNumberOfItemsPerPageId = useId();
	const collectionNumberOfPagesId = useId();

	const [numberOfItemsPerPage, setNumberOfItemsPerPage] = useControlledState(
		initialNumberOfItemsPerPage
	);
	const [numberOfItemsPerPageError, setNumberOfItemsPerPageError] = useState(
		null
	);
	const [numberOfPages, setNumberOfPages] = useState(initialNumberOfPages);

	const isMaximumValuePerPageError =
		initialNumberOfItemsPerPage > config.searchContainerPageMaxDelta;

	useEffect(() => {
		let errorMessage = null;

		if (isMaximumValuePerPageError) {
			errorMessage = Liferay.Util.sub(
				ERROR_MESSAGES.maximumItemsPerPage,
				config.searchContainerPageMaxDelta
			);
		}
		else if (initialNumberOfItemsPerPage < 1) {
			errorMessage = ERROR_MESSAGES.neededItem;
		}

		setNumberOfItemsPerPageError(errorMessage);
	}, [isMaximumValuePerPageError, initialNumberOfItemsPerPage]);

	const handleCollectionNumberOfItemsPerPageBlurred = (event) => {
		const nextValue = Math.abs(Number(event.target.value)) || 1;

		if (!numberOfItemsPerPage || numberOfItemsPerPage < 0) {
			setNumberOfItemsPerPage(nextValue);
		}

		if (nextValue !== initialNumberOfItemsPerPage) {
			handleConfigurationChanged({
				numberOfItemsPerPage: nextValue,
			});
		}
	};

	const handleCollectionNumberOfPagesBlurred = (event) => {
		const nextValue = Math.abs(Number(event.target.value)) || 1;

		if (!numberOfPages || numberOfPages < 0) {
			setNumberOfPages(nextValue);
		}

		if (nextValue !== initialNumberOfPages) {
			handleConfigurationChanged({
				numberOfPages: nextValue,
			});
		}
	};

	const handleDisplayAllPagesChanged = (event) =>
		handleConfigurationChanged({
			displayAllPages: event.target.checked,
		});

	return (
		<>
			<div className="mb-2 pt-1">
				<ClayCheckbox
					checked={displayAllPages}
					label={Liferay.Language.get('display-all-pages')}
					onChange={handleDisplayAllPagesChanged}
				/>
			</div>

			{!displayAllPages && (
				<ClayForm.Group small>
					<label htmlFor={collectionNumberOfPagesId}>
						{Liferay.Language.get(
							'maximum-number-of-pages-to-display'
						)}
					</label>

					<ClayInput
						id={collectionNumberOfPagesId}
						min="1"
						onBlur={handleCollectionNumberOfPagesBlurred}
						onChange={(event) =>
							setNumberOfPages(Number(event.target.value))
						}
						type="number"
						value={numberOfPages || ''}
					/>
				</ClayForm.Group>
			)}

			<ClayForm.Group
				className={classNames({
					'has-warning': numberOfItemsPerPageError,
				})}
				small
			>
				<label htmlFor={collectionNumberOfItemsPerPageId}>
					{Liferay.Language.get('maximum-number-of-items-per-page')}
				</label>

				<ClayInput
					id={collectionNumberOfItemsPerPageId}
					min="1"
					onBlur={handleCollectionNumberOfItemsPerPageBlurred}
					onChange={(event) =>
						setNumberOfItemsPerPage(Number(event.target.value))
					}
					type="number"
					value={numberOfItemsPerPage || ''}
				/>

				<div className="mb-2 mt-1">
					<span
						className={classNames(
							'mr-1 small',
							isMaximumValuePerPageError &&
								numberOfItemsPerPageError
								? 'text-warning'
								: 'text-secondary',
							{
								'font-weight-bold':
									isMaximumValuePerPageError &&
									numberOfItemsPerPageError,
							}
						)}
					>
						{Liferay.Util.sub(
							Liferay.Language.get('x-items-maximum'),
							config.searchContainerPageMaxDelta
						)}
					</span>

					{numberOfItemsPerPageError && (
						<FeedbackMessage message={numberOfItemsPerPageError} />
					)}
				</div>
			</ClayForm.Group>
		</>
	);
}

PaginationOptions.propTypes = {
	displayAllPages: PropTypes.bool.isRequired,
	handleConfigurationChanged: PropTypes.func.isRequired,
	initialNumberOfItemsPerPage: PropTypes.number.isRequired,
	initialNumberOfPages: PropTypes.number.isRequired,
};

function ListItemStylesOptions({item, listItemStyles}) {
	return listItemStyles.map((listItemStyle) =>
		listItemStyle.templates ? (
			<ClaySelect.OptGroup
				key={listItemStyle.label}
				label={listItemStyle.label}
			>
				{listItemStyle.templates.map((template) => (
					<ClaySelect.Option
						data-key={template.key}
						data-template-key={template.templateKey}
						key={`${template.key}_${template.templateKey}`}
						label={template.label}
						selected={
							item.config.listItemStyle === template.key &&
							(!item.config.templateKey ||
								item.config.templateKey ===
									template.templateKey)
						}
					/>
				))}
			</ClaySelect.OptGroup>
		) : (
			<ClaySelect.Option
				data-key={listItemStyle.key}
				key={listItemStyle.label}
				label={listItemStyle.label}
				selected={item.config.listItemStyle === listItemStyle.key}
			/>
		)
	);
}

ListItemStylesOptions.propTypes = {
	item: PropTypes.object.isRequired,
	listItemStyles: PropTypes.array.isRequired,
};

function FeedbackMessage({message}) {
	return (
		<div className="autofit-row mt-2 small text-warning">
			<div className="autofit-col">
				<div className="autofit-section mr-2">
					<ClayIcon symbol="warning-full" />
				</div>
			</div>

			<div className="autofit-col autofit-col-expand">
				<div className="autofit-section">{message}</div>
			</div>
		</div>
	);
}

FeedbackMessage.propTypes = {
	message: PropTypes.string.isRequired,
};
