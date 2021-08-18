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
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';

import {SelectField} from '../../../../../../app/components/fragment-configuration-fields/SelectField';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../app/config/constants/freemarkerFragmentEntryProcessor';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../../app/config/constants/layoutDataItemTypes';
import {useHoverItem} from '../../../../../../app/contexts/ControlsContext';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
} from '../../../../../../app/contexts/StoreContext';
import selectLanguageId from '../../../../../../app/selectors/selectLanguageId';
import CollectionService from '../../../../../../app/services/CollectionService';
import {isLayoutDataItemDeleted} from '../../../../../../app/utils/isLayoutDataItemDeleted';
import updateConfigurationValue from '../../../../../../app/utils/updateConfigurationValue';
import {useId} from '../../../../../../app/utils/useId';
import getLayoutDataItemPropTypes from '../../../../../../prop-types/getLayoutDataItemPropTypes';
import {FieldSet} from './FieldSet';

const selectConfiguredCollectionDisplays = (state) =>
	Object.values(state.layoutData.items).filter(
		(item) =>
			item.type === LAYOUT_DATA_ITEM_TYPES.collection &&
			item.config?.collection &&
			Object.keys(item.config.collection).length > 0 &&
			!isLayoutDataItemDeleted(state.layoutData, item.itemId)
	);

function TargetCollectionsField({onValueSelect, value}) {
	const [active, setActive] = useState(false);
	const inputId = useId();
	const [nextValue, setNextValue] = useState(value || []);
	const hoverItem = useHoverItem();

	const inputValue = useSelectorCallback(
		(state) => {
			if (nextValue.length === 0) {
				return '';
			}
			else if (nextValue.length === 1) {
				return state.layoutData.items[nextValue[0]]?.config?.collection
					?.title;
			}

			return Liferay.Language.get('multiple');
		},
		[nextValue]
	);

	const handleChange = (layoutItemId, checked) => {
		const included = nextValue.includes(layoutItemId);
		let selectedItems = nextValue;

		if (checked && !included) {
			selectedItems = [...nextValue, layoutItemId];

			setNextValue(selectedItems);
			onValueSelect('targetCollections', selectedItems);
		}
		else if (included) {
			selectedItems = nextValue.filter(
				(itemId) => itemId !== layoutItemId
			);

			setNextValue(selectedItems);
			onValueSelect('targetCollections', selectedItems);
		}
	};

	const items = useSelectorCallback(
		(state) =>
			selectConfiguredCollectionDisplays(state).map((item) => ({
				checked: nextValue.includes(item.itemId),
				label: item.config.collection.title,
				onChange: (checked) => handleChange(item.itemId, checked),
				type: 'checkbox',
				value: item.itemId,
			})),
		[nextValue]
	);

	return (
		<ClayForm.Group className="mt-1">
			<label htmlFor={inputId}>
				{Liferay.Language.get('target-collection')}
			</label>

			<ClayDropDown
				active={active}
				id={inputId}
				onActiveChange={setActive}
				trigger={
					<ClayButton
						aria-label={Liferay.Language.get('select')}
						className="bg-light font-weight-normal form-control-select text-left w-100"
						displayType="secondary"
						small
					>
						{inputValue ? (
							<span className="text-dark">{inputValue}</span>
						) : (
							Liferay.Language.get('select')
						)}
					</ClayButton>
				}
			>
				{items.map((item) => (
					<label
						className="d-flex dropdown-item"
						key={item.value}
						onMouseLeave={() => hoverItem(null)}
						onMouseOver={() => hoverItem(item.value)}
					>
						<ClayCheckbox
							checked={item.checked}
							onChange={item.onChange}
						/>
						<span className="font-weight-normal ml-2">
							{item.label}
						</span>
					</label>
				))}
			</ClayDropDown>
		</ClayForm.Group>
	);
}

export const CollectionFilterGeneralPanel = ({item}) => {
	const dispatch = useDispatch();
	const fragmentEntryLink = useSelectorCallback(
		(state) => state.fragmentEntryLinks[item.config.fragmentEntryLinkId],
		[item.config.fragmentEntryLinkId]
	);
	const hasConfiguredCollections = useSelectorCallback(
		(state) => selectConfiguredCollectionDisplays(state).length > 0,
		[]
	);
	const languageId = useSelector(selectLanguageId);

	const configurationValues =
		fragmentEntryLink.editableValues[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR] ||
		{};

	const [collectionFilters, setCollectionFilters] = useState(null);

	const selectedFilter =
		collectionFilters?.[configurationValues['filterKey']];

	useEffect(() => {
		if (hasConfiguredCollections && !collectionFilters) {
			CollectionService.getCollectionFilters().then(
				(collectionFilters) => {
					setCollectionFilters(collectionFilters);
				}
			);
		}
	}, [hasConfiguredCollections, collectionFilters]);

	const onValueSelect = useCallback(
		(name, value) => {
			updateConfigurationValue({
				configuration: selectedFilter?.configuration,
				dispatch,
				fragmentEntryLink,
				languageId,
				name,
				value,
			});
		},
		[dispatch, selectedFilter, fragmentEntryLink, languageId]
	);

	if (!hasConfiguredCollections) {
		return (
			<p className="alert alert-info text-center" role="alert">
				{Liferay.Language.get(
					'display-a-collection-on-the-page-so-that-you-can-use-the-collection-filter-fragment'
				)}
			</p>
		);
	}

	return (
		<>
			<TargetCollectionsField
				onValueSelect={onValueSelect}
				value={configurationValues.targetCollections}
			/>

			{collectionFilters && Object.keys(collectionFilters).length > 0 && (
				<SelectField
					field={{
						label: Liferay.Language.get('filter'),
						name: 'filterKey',
						typeOptions: {
							validValues: [
								{
									label: Liferay.Language.get('none'),
									value: '',
								},
								...Object.values(
									collectionFilters
								).map(({key, label}) => ({label, value: key})),
							],
						},
					}}
					onValueSelect={onValueSelect}
					value={configurationValues['filterKey']}
				/>
			)}

			{selectedFilter?.configuration &&
				selectedFilter.configuration.fieldSets
					?.filter((fieldSet) => fieldSet.fields.length)
					.map((fieldSet, index) => (
						<FieldSet
							fields={fieldSet.fields}
							key={`${fieldSet.label || ''}-${index}`}
							label={fieldSet.label}
							languageId={languageId}
							onValueSelect={(name, value) =>
								onValueSelect(name, value)
							}
							values={configurationValues}
						/>
					))}
		</>
	);
};

CollectionFilterGeneralPanel.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({
			fragmentEntryLinkId: PropTypes.string.isRequired,
		}).isRequired,
	}),
};
