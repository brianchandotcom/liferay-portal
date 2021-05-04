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

import ClayForm, {ClaySelect, ClaySelectWithOption} from '@clayui/form';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {addMappedInfoItem, addMappingFields} from '../../app/actions/index';
import {EDITABLE_TYPES} from '../../app/config/constants/editableTypes';
import {LAYOUT_TYPES} from '../../app/config/constants/layoutTypes';
import {config} from '../../app/config/index';
import {useCollectionConfig} from '../../app/contexts/CollectionItemContext';
import {useDispatch, useSelector} from '../../app/contexts/StoreContext';
import CollectionService from '../../app/services/CollectionService';
import InfoItemService from '../../app/services/InfoItemService';
import isMapped from '../../app/utils/editable-value/isMapped';
import isMappedToInfoItem from '../../app/utils/editable-value/isMappedToInfoItem';
import isMappedToStructure from '../../app/utils/editable-value/isMappedToStructure';
import getMappingFieldsKey from '../../app/utils/getMappingFieldsKey';
import itemSelectorValueToInfoItem from '../../app/utils/item-selector-value/itemSelectorValueToInfoItem';
import {useId} from '../../app/utils/useId';
import ItemSelector from './ItemSelector';

const MAPPING_SOURCE_TYPES = {
	content: 'content',
	structure: 'structure',
};

const UNMAPPED_OPTION = {
	label: `-- ${Liferay.Language.get('unmapped')} --`,
	value: 'unmapped',
};

function filterFields(fields, fieldType) {
	return fields.reduce((acc, fieldSet) => {
		const newFields = fieldSet.fields.filter((field) =>
			fieldType === EDITABLE_TYPES.image ||
			fieldType === EDITABLE_TYPES.backgroundImage
				? field.type === EDITABLE_TYPES.image
				: field.type !== EDITABLE_TYPES.image
		);

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
	let classNameId, classTypeId;

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
	mappedItem,
	onMappingSelect,
}) {
	const collectionConfig = useCollectionConfig();
	const [collectionFields, setCollectionFields] = useState([]);
	const [
		collectionItemSubtypeLabel,
		setCollectionItemSubtypeLabel,
	] = useState('');
	const [collectionItemTypeLabel, setCollectionItemTypeLabel] = useState('');
	const mappingFields = useSelector((state) => state.mappingFields);

	useEffect(() => {
		if (!collectionConfig) {
			setCollectionFields([]);

			return;
		}

		const key = getMappingFieldsKey(
			collectionConfig.collection.classNameId,
			collectionConfig.collection.itemSubtype
		);

		const fields = mappingFields[key];

		if (fields) {
			setCollectionFields(filterFields(fields, fieldType));
		}
	}, [collectionConfig, mappingFields, fieldType]);

	useEffect(() => {
		if (!collectionConfig) {
			return;
		}

		CollectionService.getCollectionMappingFields({
			itemSubtype: collectionConfig.collection.itemSubtype || '',
			itemType: collectionConfig.collection.itemType,
			onNetworkStatus: () => {},
		})
			.then((response) => {
				setCollectionItemSubtypeLabel(response.itemSubtypeLabel);
				setCollectionItemTypeLabel(response.itemTypeLabel);
			})
			.catch((error) => {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}
			});
	});

	return collectionConfig ? (
		<>
			{collectionItemTypeLabel && (
				<p className="mb-2 page-editor__mapping-panel__type-label">
					<span className="mr-1">
						{Liferay.Language.get('type')}:
					</span>
					{collectionItemTypeLabel}
				</p>
			)}

			{collectionItemSubtypeLabel && (
				<p className="mb-2 page-editor__mapping-panel__type-label">
					<span className="mr-1">
						{Liferay.Language.get('subtype')}:
					</span>
					{collectionItemSubtypeLabel}
				</p>
			)}

			<MappingFieldSelect
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
			mappedItem={mappedItem}
			onMappingSelect={onMappingSelect}
		/>
	);
}

function MappingSelector({fieldType, mappedItem, onMappingSelect}) {
	const dispatch = useDispatch();
	const mappedInfoItems = useSelector((state) => state.mappedInfoItems);
	const mappingFields = useSelector((state) => state.mappingFields);
	const pageContents = useSelector((state) => state.pageContents);
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

		const type = selectedItem?.itemType || mappedContent?.name;
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
			const mappedInfoItem = mappedInfoItems.find(
				(item) =>
					item.classNameId === selectedItem.classNameId &&
					item.classPK === selectedItem.classPK
			);

			if (!mappedInfoItem) {
				dispatch(
					addMappedInfoItem({title: selectedItem.title, ...data})
				);
			}

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
			const infoItem = mappedInfoItems.find(
				(infoItem) =>
					infoItem.classNameId === mappedItem.classNameId &&
					infoItem.classPK === mappedItem.classPK
			);

			setSelectedItem({
				...infoItem,
				...mappedItem,
			});
		}
	}, [mappedItem, mappedInfoItems, setSelectedItem]);

	useEffect(() => {
		if (
			selectedSourceType === MAPPING_SOURCE_TYPES.content &&
			!selectedItem.classNameId
		) {
			setItemFields(null);

			return;
		}

		const infoItem =
			mappedInfoItems.find(
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
			setItemFields(filterFields(fields, fieldType));
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
		mappedInfoItems,
		mappingFields,
		selectedItem,
		selectedMappingTypes,
		selectedSourceType,
	]);

	return (
		<>
			{config.layoutType === LAYOUT_TYPES.display && (
				<ClayForm.Group small>
					<label htmlFor="mappingSelectorSourceSelect">
						{Liferay.Language.get('source')}
					</label>

					<ClaySelectWithOption
						aria-label={Liferay.Language.get('source')}
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
								label: Liferay.Util.sub(
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
					label={Liferay.Language.get('content')}
					onItemSelect={onInfoItemSelect}
					selectedItemTitle={selectedItem.title}
					transformValueCallback={itemSelectorValueToInfoItem}
				/>
			)}

			{typeLabel && (
				<p className="mb-2 mt-3 page-editor__mapping-panel__type-label">
					<span className="mr-1">
						{Liferay.Language.get('type')}:
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
				<MappingFieldSelect
					fieldType={fieldType}
					fields={itemFields}
					onValueSelect={onFieldSelect}
					value={selectedItem.mappedField || selectedItem.fieldId}
				/>
			</ClayForm.Group>
		</>
	);
}

function MappingFieldSelect({fieldType, fields, onValueSelect, value}) {
	const mappingSelectorFieldSelectId = useId();

	const hasWarnings = fields && fields.length === 0;

	return (
		<ClayForm.Group
			className={classNames('mt-3', {'has-warning': hasWarnings})}
			small
		>
			<label htmlFor="mappingSelectorFieldSelect">
				{Liferay.Language.get('field')}
			</label>

			<ClaySelect
				aria-label={Liferay.Language.get('field')}
				disabled={!(fields && fields.length)}
				id={mappingSelectorFieldSelectId}
				onChange={onValueSelect}
				value={value}
			>
				{fields && fields.length && (
					<>
						<ClaySelect.Option
							label={UNMAPPED_OPTION.label}
							value={UNMAPPED_OPTION.value}
						/>

						{fields.map((fieldSet, index) => {
							const key = `${fieldSet.label || ''}${index}`;

							const Wrapper = ({children, ...props}) =>
								fieldSet.label ? (
									<ClaySelect.OptGroup {...props}>
										{children}
									</ClaySelect.OptGroup>
								) : (
									<React.Fragment key={key}>
										{children}
									</React.Fragment>
								);

							return (
								<Wrapper key={key} label={fieldSet.label}>
									{fieldSet.fields.map((field) => (
										<ClaySelect.Option
											key={field.key}
											label={field.label}
											value={field.key}
										/>
									))}
								</Wrapper>
							);
						})}
					</>
				)}
			</ClaySelect>

			{hasWarnings && (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						{Liferay.Util.sub(
							Liferay.Language.get(
								'no-fields-are-available-for-x-editable'
							),
							[
								EDITABLE_TYPES.backgroundImage,
								EDITABLE_TYPES.image,
							].includes(fieldType)
								? Liferay.Language.get('image')
								: Liferay.Language.get('text')
						)}
					</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			)}
		</ClayForm.Group>
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
