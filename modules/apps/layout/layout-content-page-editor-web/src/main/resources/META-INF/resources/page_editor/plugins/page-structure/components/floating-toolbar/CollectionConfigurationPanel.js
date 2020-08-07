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
	ClayInput,
	ClaySelect,
	ClaySelectWithOption,
} from '@clayui/form';
import React, {useEffect, useState} from 'react';

import {config} from '../../../../app/config/index';
import selectSegmentsExperienceId from '../../../../app/selectors/selectSegmentsExperienceId';
import InfoItemService from '../../../../app/services/InfoItemService';
import {useDispatch, useSelector} from '../../../../app/store/index';
import updateItemConfig from '../../../../app/thunks/updateItemConfig';
import {useId} from '../../../../app/utils/useId';
import CollectionSelector from '../../../../common/components/CollectionSelector';

const LAYOUT_OPTIONS = [
	{label: Liferay.Language.get('full-width'), value: '1'},
	{label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 2), value: '2'},
	{label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 3), value: '3'},
	{label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 4), value: '4'},
	{label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 5), value: '5'},
	{label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 6), value: '6'},
];

const LIST_STYLE_GRID = '';

const DEFAULT_LIST_STYLE = {
	label: Liferay.Language.get('grid'),
	value: LIST_STYLE_GRID,
};

export const CollectionConfigurationPanel = ({item}) => {
	const collectionLayoutId = useId();
	const collectionListItemStyleId = useId();
	const collectionNumberOfItemsId = useId();
	const dispatch = useDispatch();
	const listStyleId = useId();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

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
			<ClayForm.Group small>
				<CollectionSelector
					collectionTitle={(item.config.collection || {}).title || ''}
					itemSelectorURL={config.collectionSelectorURL}
					label={Liferay.Language.get('collection')}
					onCollectionSelect={(collection) =>
						handleConfigurationChanged({
							collection,
							listItemStyle: null,
							listStyle: LIST_STYLE_GRID,
							templateKey: null,
						})
					}
				/>
			</ClayForm.Group>
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

					<ClayForm.Group small>
						<label htmlFor={collectionNumberOfItemsId}>
							{Liferay.Language.get('max-number-of-items')}
						</label>
						<ClayInput
							id={collectionNumberOfItemsId}
							min={1}
							onChange={({target: {value}}) =>
								handleConfigurationChanged({
									numberOfItems: value,
								})
							}
							type="number"
							value={item.config.numberOfItems}
						/>
					</ClayForm.Group>
				</>
			)}
		</>
	);
};
