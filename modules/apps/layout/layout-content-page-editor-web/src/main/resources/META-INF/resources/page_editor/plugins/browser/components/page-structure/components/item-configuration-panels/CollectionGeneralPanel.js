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
import {useIsMounted} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

import {config} from '../../../../../../app/config/index';
import {
	useDispatch,
	useSelector,
} from '../../../../../../app/contexts/StoreContext';
import selectSegmentsExperienceId from '../../../../../../app/selectors/selectSegmentsExperienceId';
import CollectionService from '../../../../../../app/services/CollectionService';
import InfoItemService from '../../../../../../app/services/InfoItemService';
import updateItemConfig from '../../../../../../app/thunks/updateItemConfig';
import {useId} from '../../../../../../app/utils/useId';
import CollectionSelector from '../../../../../../common/components/CollectionSelector';

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

export const CollectionGeneralPanel = ({item}) => {
	const collectionLayoutId = useId();
	const collectionListItemStyleId = useId();
	const collectionNumberOfItemsId = useId();
	const collectionNumberOfItemsPerPageId = useId();
	const collectionPaginationTypeId = useId();
	const dispatch = useDispatch();
	const isMounted = useIsMounted();
	const listStyleId = useId();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const [nextValue, setNextValue] = useState({
		numberOfItems: item.config.numberOfItems,
		numberOfItemsPerPage: item.config.numberOfItemsPerPage,
	});
	const [showAllItems, setShowAllItems] = useState(item.config.showAllItems);
	const [totalNumberOfItems, setTotalNumberOfItems] = useState(null);

	const numberOfItemsPerPageError =
		item.config.numberOfItemsPerPage > config.searchContainerPageMaxDelta;

	const handleConfigurationChanged = (itemConfig) => {
		dispatch(
			updateItemConfig({
				itemConfig,
				itemId: item.itemId,
				segmentsExperienceId,
			})
		);
	};

	const [availableListItemStyles, setAvailableListItemStyles] = useState([]);

	const [availableListStyles, setAvailableListStyles] = useState([
		DEFAULT_LIST_STYLE,
	]);

	const collectionItemType = item.config.collection
		? item.config.collection.itemType
		: null;

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
		if (
			config.collectionDisplayFragmentPaginationEnabled &&
			showAllItems &&
			item.config.collection
		) {
			CollectionService.getCollectionItemCount({
				collection: item.config.collection,
				onNetworkStatus: () => {},
			}).then(({totalNumberOfItems}) => {
				if (isMounted()) {
					setTotalNumberOfItems(totalNumberOfItems);
				}
			});
		}
	}, [item.config.collection, showAllItems, isMounted]);

	useEffect(() => {
		if (
			(item.config.collection,
			item.config.listStyle && item.config.listStyle !== LIST_STYLE_GRID)
		) {
			InfoItemService.getAvailableListItemRenderers({
				itemSubtype: item.config.collection.itemSubtype,
				itemType: item.config.collection.itemType,
				listStyle: item.config.listStyle,
			})
				.then((response) => {
					setAvailableListItemStyles(response);
				})
				.catch(() => {
					setAvailableListItemStyles([]);
				});
		}
	}, [item.config.collection, item.config.listStyle]);

	return (
		<>
			<CollectionSelector
				collectionItem={item.config.collection}
				itemSelectorURL={config.collectionSelectorURL}
				label={Liferay.Language.get('collection')}
				onCollectionSelect={(collection = {}) => {
					const nextCollection =
						Object.keys(collection).length !== 0
							? collection
							: null;
					handleConfigurationChanged({
						collection: nextCollection,
						listItemStyle: null,
						listStyle: LIST_STYLE_GRID,
						templateKey: null,
					});
				}}
			/>
			{item.config.collection && (
				<>
					<ClayForm.Group small>
						<label htmlFor={listStyleId}>
							{Liferay.Language.get('list-style')}
						</label>
						<ClaySelectWithOption
							aria-label={Liferay.Language.get('list-style')}
							id={listStyleId}
							onChange={({target: {value}}) =>
								handleConfigurationChanged({
									listStyle: value,
								})
							}
							options={availableListStyles}
							value={item.config.listStyle}
						/>
					</ClayForm.Group>

					{item.config.listStyle === LIST_STYLE_GRID && (
						<ClayForm.Group small>
							<label htmlFor={collectionLayoutId}>
								{Liferay.Language.get('layout')}
							</label>
							<ClaySelectWithOption
								aria-label={Liferay.Language.get('layout')}
								id={collectionLayoutId}
								onChange={({target: {value}}) =>
									handleConfigurationChanged({
										numberOfColumns: value,
									})
								}
								options={LAYOUT_OPTIONS}
								value={item.config.numberOfColumns}
							/>
						</ClayForm.Group>
					)}

					{item.config.listStyle !== LIST_STYLE_GRID &&
						availableListItemStyles.length > 0 && (
							<ClayForm.Group small>
								<label htmlFor={collectionListItemStyleId}>
									{Liferay.Language.get('list-item-style')}
								</label>
								<ClaySelect
									aria-label={Liferay.Language.get(
										'list-item-style'
									)}
									id={collectionListItemStyleId}
									onChange={({target}) =>
										handleConfigurationChanged({
											listItemStyle:
												target.options[
													target.selectedIndex
												].dataset.key,
											templateKey:
												target.options[
													target.selectedIndex
												].dataset.templateKey,
										})
									}
								>
									{availableListItemStyles.map(
										(listItemStyle) => {
											if (listItemStyle.templates) {
												return (
													<ClaySelect.OptGroup
														key={
															listItemStyle.label
														}
														label={
															listItemStyle.label
														}
													>
														{listItemStyle.templates.map(
															(template) => (
																<ClaySelect.Option
																	data-key={
																		template.key
																	}
																	data-template-key={
																		template.templateKey
																	}
																	key={`${template.key}_${template.templateKey}`}
																	label={
																		template.label
																	}
																	selected={
																		item
																			.config
																			.listItemStyle ===
																			template.key &&
																		(!item
																			.config
																			.templateKey ||
																			item
																				.config
																				.templateKey ===
																				template.templateKey)
																	}
																/>
															)
														)}
													</ClaySelect.OptGroup>
												);
											}
											else {
												return (
													<ClaySelect.Option
														data-key={
															listItemStyle.key
														}
														key={
															listItemStyle.label
														}
														label={
															listItemStyle.label
														}
														selected={
															item.config
																.listItemStyle ===
															listItemStyle.key
														}
													/>
												);
											}
										}
									)}
								</ClaySelect>
							</ClayForm.Group>
						)}

					{config.collectionDisplayFragmentPaginationEnabled && (
						<>
							<ClayForm.Group small>
								<label htmlFor={collectionPaginationTypeId}>
									{Liferay.Language.get('pagination')}
								</label>
								<ClaySelectWithOption
									aria-label={Liferay.Language.get(
										'pagination'
									)}
									id={collectionPaginationTypeId}
									onChange={({target: {value}}) =>
										handleConfigurationChanged({
											paginationType: value,
										})
									}
									options={PAGINATION_TYPE_OPTIONS}
									value={item.config.paginationType}
								/>
							</ClayForm.Group>

							{item.config.paginationType && (
								<div className="mb-1 pt-1">
									<ClayCheckbox
										checked={showAllItems}
										label={Liferay.Language.get(
											'display-all-collection-items'
										)}
										onChange={({target}) => {
											setShowAllItems(target.checked);
											setNextValue({
												...nextValue,
												numberOfItems: totalNumberOfItems,
											});

											handleConfigurationChanged({
												numberOfItems: totalNumberOfItems,
												showAllItems: target.checked,
											});
										}}
									/>
								</div>
							)}
						</>
					)}

					<ClayForm.Group small>
						<label htmlFor={collectionNumberOfItemsId}>
							{Liferay.Language.get('maximum-number-of-items')}
						</label>
						<ClayInput
							id={collectionNumberOfItemsId}
							onBlur={({target: {value}}) => {
								if (
									nextValue.numberOfItems !==
									item.config.numberOfItems
								) {
									handleConfigurationChanged({
										numberOfItems: value,
									});
								}
							}}
							onChange={({target: {value}}) => {
								setNextValue({
									...nextValue,
									numberOfItems: value,
								});

								if (showAllItems) {
									setShowAllItems(false);
								}
							}}
							type="number"
							value={nextValue.numberOfItems}
						/>
					</ClayForm.Group>

					{config.collectionDisplayFragmentPaginationEnabled &&
						item.config.paginationType && (
							<ClayForm.Group small>
								<label
									htmlFor={collectionNumberOfItemsPerPageId}
								>
									{Liferay.Language.get(
										'maximum-number-of-items-per-page'
									)}
								</label>
								<ClayInput
									id={collectionNumberOfItemsPerPageId}
									onBlur={({target: {value}}) => {
										if (
											nextValue.numberOfItemsPerPage !==
											item.config.numberOfItemsPerPage
										) {
											handleConfigurationChanged({
												numberOfItemsPerPage: value,
											});
										}
									}}
									onChange={({target: {value}}) =>
										setNextValue({
											...nextValue,
											numberOfItemsPerPage: value,
										})
									}
									type="number"
									value={nextValue.numberOfItemsPerPage}
								/>
								<p
									className={classNames(
										'mt-2 page-editor__collection-general-panel__pagination-label',
										{
											error: numberOfItemsPerPageError,
										}
									)}
								>
									<span
										className={classNames('mr-1', {
											'font-weight-bold': numberOfItemsPerPageError,
										})}
									>
										{Liferay.Util.sub(
											Liferay.Language.get(
												'x-items-maximum'
											),
											config.searchContainerPageMaxDelta
										)}
									</span>

									{numberOfItemsPerPageError &&
										Liferay.Util.sub(
											Liferay.Language.get(
												'only-x-items-will-be-displayed'
											),
											config.searchContainerPageMaxDelta
										)}
								</p>
							</ClayForm.Group>
						)}
				</>
			)}
		</>
	);
};
