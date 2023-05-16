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

import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {addMappingFields} from '../../app/actions/index';
import {EDITABLE_TYPES} from '../../app/config/constants/editableTypes';
import {LAYOUT_TYPES} from '../../app/config/constants/layoutTypes';
import {config} from '../../app/config/index';
import {useCollectionConfig} from '../../app/contexts/CollectionItemContext';
import {useDispatch, useSelector} from '../../app/contexts/StoreContext';
import {selectPageContents} from '../../app/selectors/selectPageContents';
import InfoItemService from '../../app/services/InfoItemService';
import isMapped from '../../app/utils/editable_value/isMapped';
import isMappedToInfoItem from '../../app/utils/editable_value/isMappedToInfoItem';
import isMappedToStructure from '../../app/utils/editable_value/isMappedToStructure';
import getMappingFieldsKey from '../../app/utils/getMappingFieldsKey';
import itemSelectorValueToInfoItem from '../../app/utils/item_selector_value/itemSelectorValueToInfoItem';
import {useId} from '../hooks/useId';
import ItemSelector from './ItemSelector';
import MappingFieldSelector from './MappingFieldSelector';

const COLLECTION_TYPE_DIVIDER = ' - ';

const MAPPING_SOURCE_TYPES = {
	content: 'content',
	structure: 'structure',
};

const UNMAPPED_OPTION = {
	label: `-- ${Liferay.Language.get('unmapped')} --`,
	value: 'unmapped',
};

function filterFields(fields, fieldType, filterLinkTypes) {
	return fields.reduce((acc, fieldSet) => {
		const newFields = fieldSet.fields.filter((field) => {
			if (fieldType === EDITABLE_TYPES['date-time']) {
				return field.type === 'date';
			}
			else if (fieldType === EDITABLE_TYPES.link && filterLinkTypes) {
				return (
					field.type !== EDITABLE_TYPES.image && field.type !== 'date'
				);
			}
			else if (
				fieldType === EDITABLE_TYPES.image ||
				fieldType === EDITABLE_TYPES.backgroundImage
			) {
				return field.type === EDITABLE_TYPES.image;
			}
			else {
				return field.type !== EDITABLE_TYPES.image;
			}
		});

		if (newFields.length) {
			return [
				...acc,
				{
					...fieldSet,
					fields: newFields,
				},
			];
		}

		return acc;
	}, []);
}

function loadMappingFields({dispatch, item, sourceType}) {
	let classNameId;
	let classTypeId;

	if (sourceType === MAPPING_SOURCE_TYPES.structure) {
		const {selectedMappingTypes} = config;

		classNameId = selectedMappingTypes.type.id;
		classTypeId = selectedMappingTypes.subtype.id;
	}
	else if (
		sourceType === MAPPING_SOURCE_TYPES.content &&
		item.classNameId
	) {
		classNameId = item.classNameId;
		classTypeId = item.classTypeId;
	}

	const promise = InfoItemService.getAvailableStructureMappingFields({
		classNameId,
		classTypeId,
		onNetworkStatus: dispatch,
	});

	if (promise) {
		return promise.then((response) => {
			if (Array.isArray(response)) {
				return response;
			}

			return [];
		});
	}

	return Promise.resolve(null);
}

export default function MappingSelectorWrapper({
	fieldType,
	filterLinkTypes = false,
	mappedItem,
	onMappingSelect,
}) {
	const collectionConfig = useCollectionConfig();
	const [collectionFields, setCollectionFields] = useState([]);
	const [collectionTypeLabels, setCollectionItemTypeLabels] = useState({
		itemSubtype: '',
		itemType: '',
	});
	const mappingFields = useSelector((state) => state.mappingFields);
	const pageContents = useSelector(selectPageContents);

	useEffect(() => {
		if (!collectionConfig) {
			setCollectionFields([]);

			return;
		}

		const {classNameId, classPK} = collectionConfig.collection;

		const key = classNameId
			? getMappingFieldsKey(classNameId, classPK)
			: collectionConfig.collection.key;

		const fields = mappingFields[key];

		if (fields) {
			setCollectionFields(
				filterFields(fields, fieldType, filterLinkTypes)
			);
		}
	}, [collectionConfig, mappingFields, fieldType, filterLinkTypes]);

	useEffect(() => {
		if (!collectionConfig?.collection?.itemType) {
			return;
		}

		const {
			classNameId,
			classPK,
			key: collectionKey,
		} = collectionConfig.collection;

		const collection = pageContents.find((content) =>
			collectionKey
				? content.classPK === collectionKey
				: content.classNameId === classNameId &&
				  content.classPK === classPK
		);

		if (collection) {
			const [typeLabel, subtypeLabel] =
				collection?.subtype?.split(COLLECTION_TYPE_DIVIDER) || [];

			setCollectionItemTypeLabels({
				itemSubtype: subtypeLabel,
				itemType: typeLabel,
			});
		}
	}, [collectionConfig, pageContents]);

	return collectionConfig ? (
		<>
			{collectionTypeLabels.itemType && (
				<p
					className={classNames(
						'page-editor__mapping-panel__type-label',
						{
							'mb-0': collectionTypeLabels.itemSubtype,
							'mb-2': !collectionTypeLabels.itemSubtype,
						}
					)}
				>
					<span className="mr-1">
						{Liferay.Language.get('content-type')}:
					</span>

					{collectionTypeLabels.itemType}
				</p>
			)}

			{collectionTypeLabels.itemSubtype && (
				<p className="mb-2 page-editor__mapping-panel__type-label">
					<span className="mr-1">
						{Liferay.Language.get('subtype')}:
					</span>

					{collectionTypeLabels.itemSubtype}
				</p>
			)}

			<MappingFieldSelector
				fieldType={fieldType}
				fields={collectionFields}
				onValueSelect={(event) => {
					if (event.target.value === UNMAPPED_OPTION.value) {
						onMappingSelect({collectionFieldId: ''});
					}
					else {
						onMappingSelect({
							collectionFieldId: event.target.value,
						});
					}
				}}
				value={mappedItem.collectionFieldId}
			/>
		</>
	) : (
		<MappingSelector
			fieldType={fieldType}
			filterLinkTypes={filterLinkTypes}
			mappedItem={mappedItem}
			onMappingSelect={onMappingSelect}
		/>
	);
}

function MappingSelector({
	fieldType,
	filterLinkTypes,
	mappedItem,
	onMappingSelect,
}) {
	const dispatch = useDispatch();
	const mappingFields = useSelector((state) => state.mappingFields);
	const pageContents = useSelector(selectPageContents);
	const mappingSelectorSourceSelectId = useId();

	const {selectedMappingTypes} = config;

	const [itemFields, setItemFields] = useState(null);
	const [selectedItem, setSelectedItem] = useState(mappedItem);

	const [typeLabel, setTypeLabel] = useState(null);
	const [subtypeLabel, setSubtypeLabel] = useState(null);

	useEffect(() => {
		const mappedContent = pageContents.find(
			(infoItem) =>
				infoItem.classNameId === selectedItem.classNameId &&
				infoItem.classPK === selectedItem.classPK
		);

		const type = selectedItem?.itemType || mappedContent?.type;
		const subtype = selectedItem?.itemSubtype || mappedContent?.subtype;

		setTypeLabel(type);
		setSubtypeLabel(subtype);
	}, [selectedItem, pageContents]);

	const [selectedSourceType, setSelectedSourceType] = useState(
		!isMappedToInfoItem(mappedItem) &&
			(isMappedToStructure(mappedItem) ||
				config.layoutType === LAYOUT_TYPES.display)
			? MAPPING_SOURCE_TYPES.structure
			: MAPPING_SOURCE_TYPES.content
	);

	const onInfoItemSelect = (selectedInfoItem) => {
		setSelectedItem(selectedInfoItem);

		if (isMapped(mappedItem)) {
			onMappingSelect({});
		}
	};

	const onFieldSelect = (event) => {
		const fieldValue = event.target.value;

		const data =
			fieldValue === UNMAPPED_OPTION.value
				? {}
				: selectedSourceType === MAPPING_SOURCE_TYPES.content
				? {...selectedItem, fieldId: fieldValue}
				: {mappedField: fieldValue};

		if (selectedSourceType === MAPPING_SOURCE_TYPES.content) {
			setSelectedItem((selectedItem) => ({
				...selectedItem,
				fieldId: fieldValue,
			}));
		}
		else {
			setSelectedItem((selectedItem) => ({
				...selectedItem,
				mappedField: fieldValue,
			}));
		}

		onMappingSelect(data);
	};

	useEffect(() => {
		if (mappedItem.classNameId && mappedItem.classPK) {
			const pageContent = pageContents.find(
				(pageContent) =>
					pageContent.classNameId === mappedItem.classNameId &&
					pageContent.classPK === mappedItem.classPK
			);

			setSelectedItem({
				...pageContent,
				...mappedItem,
			});
		}
	}, [mappedItem, pageContents, setSelectedItem]);

	useEffect(() => {
		if (
			selectedSourceType === MAPPING_SOURCE_TYPES.content &&
			!selectedItem.classNameId
		) {
			setItemFields(null);

			return;
		}

		const infoItem =
			pageContents.find(
				({classNameId, classPK}) =>
					selectedItem.classNameId === classNameId &&
					selectedItem.classPK === classPK
			) || selectedItem;

		const key =
			selectedSourceType === MAPPING_SOURCE_TYPES.content
				? getMappingFieldsKey(
						infoItem.classNameId,
						infoItem.classTypeId
				  )
				: getMappingFieldsKey(
						selectedMappingTypes.type.id,
						selectedMappingTypes.subtype.id || 0
				  );

		const fields = mappingFields[key];

		if (fields) {
			setItemFields(filterFields(fields, fieldType, filterLinkTypes));
		}
		else {
			loadMappingFields({
				dispatch,
				item: selectedItem,
				sourceType: selectedSourceType,
			}).then((newFields) => {
				dispatch(addMappingFields({fields: newFields, key}));
			});
		}
	}, [
		dispatch,
		fieldType,
		filterLinkTypes,
		pageContents,
		mappingFields,
		selectedItem,
		selectedMappingTypes,
		selectedSourceType,
	]);

	return (
		<>
			{config.layoutType === LAYOUT_TYPES.display && (
				<ClayForm.Group small>
					<label htmlFor={mappingSelectorSourceSelectId}>
						{Liferay.Language.get('source')}
					</label>

					<ClaySelectWithOption
						className="pr-4 text-truncate"
						id={mappingSelectorSourceSelectId}
						onChange={(event) => {
							setSelectedSourceType(event.target.value);

							setSelectedItem({});

							if (isMapped(mappedItem)) {
								onMappingSelect({});
							}
						}}
						options={[
							{
								label: sub(
									Liferay.Language.get('x-default'),
									selectedMappingTypes.subtype
										? selectedMappingTypes.subtype.label
										: selectedMappingTypes.type.label
								),
								value: MAPPING_SOURCE_TYPES.structure,
							},
							{
								label: Liferay.Language.get('specific-content'),
								value: MAPPING_SOURCE_TYPES.content,
							},
						]}
						value={selectedSourceType}
					/>
				</ClayForm.Group>
			)}

			{selectedSourceType === MAPPING_SOURCE_TYPES.content && (
				<ItemSelector
					className="mb-2"
					label={Liferay.Language.get('item')}
					onItemSelect={onInfoItemSelect}
					selectedItem={selectedItem}
					transformValueCallback={itemSelectorValueToInfoItem}
				/>
			)}

			{typeLabel && (
				<p
					className={classNames(
						'page-editor__mapping-panel__type-label',
						{'mb-0': subtypeLabel, 'mb-2': !subtypeLabel}
					)}
				>
					<span className="mr-1">
						{Liferay.Language.get('content-type')}:
					</span>

					{typeLabel}
				</p>
			)}

			{subtypeLabel && (
				<p className="mb-2 page-editor__mapping-panel__type-label">
					<span className="mr-1">
						{Liferay.Language.get('subtype')}:
					</span>

					{subtypeLabel}
				</p>
			)}

			<ClayForm.Group small>
				<MappingFieldSelector
					fieldType={fieldType}
					fields={itemFields}
					onValueSelect={onFieldSelect}
					value={selectedItem.mappedField || selectedItem.fieldId}
				/>
			</ClayForm.Group>
		</>
	);
}

MappingSelector.propTypes = {
	fieldType: PropTypes.string,
	mappedItem: PropTypes.oneOfType([
		PropTypes.shape({
			classNameId: PropTypes.string,
			classPK: PropTypes.string,
			fieldId: PropTypes.string,
			fileEntryId: PropTypes.string,
		}),
		PropTypes.shape({
			collectionFieldId: PropTypes.string,
			fileEntryId: PropTypes.string,
		}),
		PropTypes.shape({mappedField: PropTypes.string}),
	]),
	onMappingSelect: PropTypes.func.isRequired,
};
