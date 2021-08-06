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

import ClayButton from '@clayui/button';
import ClayForm, {
	ClayCheckbox,
	ClayInput,
	ClaySelect,
	ClaySelectWithOption,
} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

import {config} from '../../../../../../app/config/index';
import {
	useDispatch,
	useSelector,
} from '../../../../../../app/contexts/StoreContext';
import selectLanguageId from '../../../../../../app/selectors/selectLanguageId';
import selectSegmentsExperienceId from '../../../../../../app/selectors/selectSegmentsExperienceId';
import CollectionService from '../../../../../../app/services/CollectionService';
import InfoItemService from '../../../../../../app/services/InfoItemService';
import updateItemConfig from '../../../../../../app/thunks/updateItemConfig';
import {setIn} from '../../../../../../app/utils/setIn';
import {useId} from '../../../../../../app/utils/useId';
import CollectionSelector from '../../../../../../common/components/CollectionSelector';
import {FieldSet} from './FieldSet';

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
	const [availableListItemStyles, setAvailableListItemStyles] = useState([]);
	const [availableListStyles, setAvailableListStyles] = useState([
		DEFAULT_LIST_STYLE,
	]);
	const [collectionConfiguration, setCollectionConfiguration] = useState(
		null
	);
	const collectionItemType = item.config.collection?.itemType || null;
	const collectionLayoutId = useId();
	const collectionListItemStyleId = useId();
	const collectionNumberOfItemsId = useId();
	const collectionNumberOfItemsPerPageId = useId();
	const collectionPaginationTypeId = useId();
	const dispatch = useDispatch();
	const isMaximumValuePerPageError =
		item.config.numberOfItemsPerPage > config.searchContainerPageMaxDelta;
	const isMounted = useIsMounted();
	const listStyleId = useId();
	const [nextValue, setNextValue] = useState({
		numberOfItems: item.config.numberOfItems,
		numberOfItemsPerPage: item.config.numberOfItemsPerPage,
	});
	const [numberOfItemsError, setNumberOfItemsError] = useState(null);
	const [numberOfItemsPerPageError, setNumberOfItemsPerPageError] = useState(
		null
	);
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
							label: Liferay.Language.get('filter-collection'),
							onClick: () => setFilterConfigurationVisible(true),
						},
				  ]
				: [],
		[collectionConfiguration, setFilterConfigurationVisible]
	);

	const [showAllItems, setShowAllItems] = useState(item.config.showAllItems);
	const [totalNumberOfItems, setTotalNumberOfItems] = useState(0);

	const handleCollectionListItemStyleChanged = ({target}) => {
		const options = target.options;

		handleConfigurationChanged({
			listItemStyle: options[target.selectedIndex].dataset.key,
			templateKey: options[target.selectedIndex].dataset.templateKey,
		});
	};

	const handleCollectionNumberOfItemsBlurred = (event) => {
		if (Number(nextValue.numberOfItems) !== item.config.numberOfItems) {
			setNumberOfItemsError(
				Number(event.target.value) < 1
					? Liferay.Language.get(
							'you-need-at-least-one-item-to-use-pagination'
					  )
					: null
			);

			handleConfigurationChanged({
				numberOfItems: Number(event.target.value) || 1,
			});
		}
	};

	const handleCollectionNumberOfItemsChanged = (event) => {
		setNextValue({
			...nextValue,
			numberOfItems: event.target.value,
		});

		if (showAllItems) {
			setShowAllItems(false);
		}
	};

	const handleCollectionNumberOfItemsPerPageBlurred = (event) => {
		if (
			nextValue.numberOfItemsPerPage !== item.config.numberOfItemsPerPage
		) {
			if (Number(event.target.value) < 1) {
				setNumberOfItemsPerPageError(
					Liferay.Language.get(
						'you-need-at-least-one-item-to-use-pagination'
					)
				);
			}
			else if (
				Number(event.target.value) <= config.searchContainerPageMaxDelta
			) {
				setNumberOfItemsPerPageError(null);
			}

			handleConfigurationChanged({
				numberOfItemsPerPage: Number(event.target.value) || 1,
			});
		}
	};

	const handleCollectionNumberOfItemsPerPageChanged = (event) =>
		setNextValue({
			...nextValue,
			numberOfItemsPerPage: event.target.value,
		});

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

	const handleShowAllItemsChanged = (event) => {
		setShowAllItems(event.target.checked);

		const numberOfItems = totalNumberOfItems || 1;

		setNextValue({
			...nextValue,
			numberOfItems,
		});

		handleConfigurationChanged({
			numberOfItems,
			showAllItems: event.target.checked,
		});

		if (numberOfItemsError) {
			setNumberOfItemsError(null);
		}
	};

	useEffect(() => {
		if (
			totalNumberOfItems &&
			item.config.numberOfItems > totalNumberOfItems
		) {
			setNumberOfItemsError(
				Liferay.Util.sub(
					Liferay.Language.get(
						'the-maximum-number-of-items-in-this-collection-is-x'
					),
					totalNumberOfItems
				)
			);
		}
	}, [totalNumberOfItems, item.config.numberOfItems]);

	useEffect(() => {
		if (isMaximumValuePerPageError) {
			setNumberOfItemsPerPageError(
				Liferay.Util.sub(
					Liferay.Language.get(
						'you-can-only-display-a-maximum-of-x-items-per-page'
					),
					config.searchContainerPageMaxDelta
				)
			);
		}
	}, [isMaximumValuePerPageError]);

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
		if (item.config.collection) {
			CollectionService.getCollectionItemCount({
				collection: item.config.collection,
				onNetworkStatus: () => {},
			}).then(({totalNumberOfItems}) => {
				if (isMounted()) {
					const numberOfItems = totalNumberOfItems || 1;

					setTotalNumberOfItems(numberOfItems);

					if (showAllItems) {
						handleConfigurationChanged({
							numberOfItems,
						});

						setNextValue((prevValue) => ({
							...prevValue,
							numberOfItems,
						}));

						setNumberOfItemsError(null);
					}
				}
			});
		}
	}, [
		item.config.collection,
		isMounted,
		showAllItems,
		handleConfigurationChanged,
	]);

	useEffect(() => {
		if (
			item.config.collection &&
			item.config.listStyle &&
			item.config.listStyle !== LIST_STYLE_GRID
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

	useEffect(() => {
		if (item.config.collection?.key) {
			CollectionService.getCollectionConfiguration(item.config.collection)
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
	}, [item.config.collection]);

	return (
		<>
			<CollectionSelector
				collectionItem={item.config.collection}
				itemSelectorURL={config.collectionSelectorURL}
				label={Liferay.Language.get('collection')}
				onCollectionSelect={(collection = {}) =>
					handleConfigurationChanged({
						collection: Object.keys(collection).length
							? collection
							: null,
						listItemStyle: null,
						listStyle: LIST_STYLE_GRID,
						templateKey: null,
					})
				}
				optionsMenuItems={optionsMenuItems}
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
							onChange={(event) =>
								handleConfigurationChanged({
									listStyle: event.target.value,
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
								onChange={(event) =>
									handleConfigurationChanged({
										numberOfColumns: event.target.value,
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
									onChange={
										handleCollectionListItemStyleChanged
									}
								>
									<ListItemStylesOptions
										item={item}
										listItemStyles={availableListItemStyles}
									/>
								</ClaySelect>
							</ClayForm.Group>
						)}

					<ClayForm.Group small>
						<label htmlFor={collectionPaginationTypeId}>
							{Liferay.Language.get('pagination')}
						</label>
						<ClaySelectWithOption
							aria-label={Liferay.Language.get('pagination')}
							id={collectionPaginationTypeId}
							onChange={(event) =>
								handleConfigurationChanged({
									paginationType: event.target.value,
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
								onChange={handleShowAllItemsChanged}
							/>
						</div>
					)}

					<ClayForm.Group
						className={classNames({
							'has-warning': numberOfItemsError,
						})}
						small
					>
						<label htmlFor={collectionNumberOfItemsId}>
							{Liferay.Language.get('maximum-number-of-items')}
						</label>
						<ClayInput
							id={collectionNumberOfItemsId}
							min="1"
							onBlur={handleCollectionNumberOfItemsBlurred}
							onChange={handleCollectionNumberOfItemsChanged}
							type="number"
							value={nextValue.numberOfItems}
						/>

						{numberOfItemsError && (
							<FeedbackMessage message={numberOfItemsError} />
						)}
					</ClayForm.Group>

					{item.config.paginationType && (
						<ClayForm.Group
							className={classNames({
								'has-warning': numberOfItemsPerPageError,
							})}
							small
						>
							<label htmlFor={collectionNumberOfItemsPerPageId}>
								{Liferay.Language.get(
									'maximum-number-of-items-per-page'
								)}
							</label>
							<ClayInput
								id={collectionNumberOfItemsPerPageId}
								min="1"
								onBlur={
									handleCollectionNumberOfItemsPerPageBlurred
								}
								onChange={
									handleCollectionNumberOfItemsPerPageChanged
								}
								type="number"
								value={nextValue.numberOfItemsPerPage}
							/>

							<div className="mb-2 mt-2">
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
									<FeedbackMessage
										message={numberOfItemsPerPageError}
									/>
								)}
							</div>
						</ClayForm.Group>
					)}
				</>
			)}

			{filterConfigurationVisible ? (
				<CollectionFilterConfigurationModal
					collectionConfiguration={collectionConfiguration}
					handleConfigurationChanged={handleConfigurationChanged}
					item={item}
					observer={filterConfigurationObserver}
					onClose={onFilterConfigurationClose}
					visible={filterConfigurationVisible}
				/>
			) : null}
		</>
	);
};

function CollectionFilterConfigurationModal({
	collectionConfiguration,
	handleConfigurationChanged,
	item,
	observer,
	onClose,
	visible,
}) {
	const languageId = useSelector(selectLanguageId);
	const [itemConfig, setItemConfig] = useState(item.config);

	const handleFieldValueSelect = (fieldSet, name, value) => {
		const field = fieldSet.fields.find((field) => field.name === name);
		let nextConfig;

		if (field.localizable) {
			nextConfig = setIn(
				itemConfig,
				['collection', 'config', name, languageId],
				value
			);
		}
		else {
			nextConfig = setIn(
				itemConfig,
				['collection', 'config', name],
				value
			);
		}

		setItemConfig((previousItemConfig) => ({
			...previousItemConfig,
			...nextConfig,
		}));
	};

	const handleSaveButtonClick = () => {
		handleConfigurationChanged(itemConfig);
		onClose();
	};

	useEffect(() => {
		if (visible && item.config) {
			setItemConfig(item.config);
		}
	}, [item.config, visible]);

	return (
		<ClayModal containerProps={{className: 'cadmin'}} observer={observer}>
			<ClayModal.Header>
				{Liferay.Language.get('filter-collection')}
			</ClayModal.Header>
			<ClayModal.Body>
				{collectionConfiguration ? (
					collectionConfiguration.fieldSets
						.filter((fieldSet) => fieldSet.fields.length)
						.map((fieldSet, index) => (
							<FieldSet
								fields={fieldSet.fields}
								key={`${fieldSet.label || ''}-${index}`}
								label={fieldSet.label}
								languageId={languageId}
								onValueSelect={(name, value) =>
									handleFieldValueSelect(
										fieldSet,
										name,
										value
									)
								}
								values={item.config.collection?.config || {}}
							/>
						))
				) : (
					<ClayLoadingIndicator />
				)}
			</ClayModal.Body>
			<ClayModal.Footer
				last={
					<>
						<ClayButton
							className="mr-2"
							displayType="secondary"
							onClick={onClose}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
						<ClayButton onClick={handleSaveButtonClick}>
							{Liferay.Language.get('apply')}
						</ClayButton>
					</>
				}
			/>
		</ClayModal>
	);
}

const ListItemStylesOptions = ({item, listItemStyles}) =>
	listItemStyles.map((listItemStyle) =>
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

const FeedbackMessage = ({message}) => (
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
