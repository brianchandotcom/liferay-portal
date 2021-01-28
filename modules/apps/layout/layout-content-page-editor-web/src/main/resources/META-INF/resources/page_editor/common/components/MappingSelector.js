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

import {addMappedInfoItem} from '../../app/actions/index';
import {useCollectionConfig} from '../../app/components/CollectionItemContext';
import {EDITABLE_TYPES} from '../../app/config/constants/editableTypes';
import {LAYOUT_TYPES} from '../../app/config/constants/layoutTypes';
import {config} from '../../app/config/index';
import CollectionService from '../../app/services/CollectionService';
import InfoItemService from '../../app/services/InfoItemService';
import {useDispatch, useSelector} from '../../app/store/index';
import isMapped from '../../app/utils/isMapped';
import {useId} from '../../app/utils/useId';
import ItemSelector from './ItemSelector';

const MAPPING_SOURCE_TYPE_IDS = {
	content: 'content',
	structure: 'structure',
};

const UNMAPPED_OPTION = {
	label: `-- ${Liferay.Language.get('unmapped')} --`,
	value: 'unmapped',
};

function loadFields({
	dispatch,
	fieldType,
	selectedItem,
	selectedMappingTypes,
	selectedSourceTypeId,
}) {
	let promise;

	if (selectedSourceTypeId === MAPPING_SOURCE_TYPE_IDS.structure) {
		promise = InfoItemService.getAvailableStructureMappingFields({
			classNameId: selectedMappingTypes.type.id,
			classTypeId: selectedMappingTypes.subtype.id,
			fieldType,
			onNetworkStatus: dispatch,
		});
	}
	else if (
		selectedSourceTypeId === MAPPING_SOURCE_TYPE_IDS.content &&
		selectedItem.classNameId &&
		selectedItem.classPK
	) {
		promise = InfoItemService.getAvailableInfoItemMappingFields({
			classNameId: selectedItem.classNameId,
			classPK: selectedItem.classPK,
			fieldType,
			onNetworkStatus: dispatch,
		});
	}

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
	const [collectionFieldSets, setCollectionFieldSets] = useState([]);
	const [
		collectionItemSubtypeLabel,
		setCollectionItemSubtypeLabel,
	] = useState('');
	const [collectionItemTypeLabel, setCollectionItemTypeLabel] = useState('');

	useEffect(() => {
		if (!collectionConfig) {
			setCollectionFieldSets([]);

			return;
		}

		CollectionService.getCollectionMappingFields({
			fieldType,
			itemSubtype: collectionConfig.collection.itemSubtype || '',
			itemType: collectionConfig.collection.itemType,
			onNetworkStatus: () => {},
		})
			.then((response) => {
				setCollectionFieldSets(response.mappingFields);
				setCollectionItemSubtypeLabel(response.itemSubtypeLabel);
				setCollectionItemTypeLabel(response.itemTypeLabel);
			})
			.catch((error) => {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}
			});
	}, [collectionConfig, fieldType]);

	return collectionConfig ? (
		<>
			{collectionItemTypeLabel && (
				<p className="mb-2 page-editor__mapping-panel__type-label">
					<span className="mr-1">
						{Liferay.Language.get('item-type')}:
					</span>
					{collectionItemTypeLabel}
				</p>
			)}

			{collectionItemSubtypeLabel && (
				<p className="mb-2 page-editor__mapping-panel__type-label">
					<span className="mr-1">
						{Liferay.Language.get('item-subtype')}:
					</span>
					{collectionItemSubtypeLabel}
				</p>
			)}

			<MappingFieldSelect
				fieldSets={collectionFieldSets}
				fieldType={fieldType}
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
	const mappingSelectorSourceSelectId = useId();

	const {selectedMappingTypes} = config;

	const [fieldSets, setFieldSets] = useState(null);
	const [selectedItem, setSelectedItem] = useState(mappedItem);

	const [selectedSourceTypeId, setSelectedSourceTypeId] = useState(
		mappedItem.mappedField || config.layoutType === LAYOUT_TYPES.display
			? MAPPING_SOURCE_TYPE_IDS.structure
			: MAPPING_SOURCE_TYPE_IDS.content
	);

	const onInfoItemSelect = (selectedInfoItem) => {
		setSelectedItem(selectedInfoItem);

		if (isMapped(mappedItem)) {
			onMappingSelect({
				className: '',
				classNameId: '',
				classPK: '',
				fieldId: '',
				mappedField: '',
			});
		}
	};

	const onFieldSelect = (event) => {
		const fieldValue = event.target.value;

		const data =
			fieldValue === UNMAPPED_OPTION.value
				? {
						className: '',
						classNameId: '',
						classPK: '',
						fieldId: '',
						mappedField: '',
				  }
				: selectedSourceTypeId === MAPPING_SOURCE_TYPE_IDS.content
				? {
						className: selectedItem.className,
						classNameId: selectedItem.classNameId,
						classPK: selectedItem.classPK,
						fieldId: fieldValue,
				  }
				: {mappedField: fieldValue};

		if (selectedSourceTypeId === MAPPING_SOURCE_TYPE_IDS.content) {
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
		const data =
			selectedSourceTypeId === MAPPING_SOURCE_TYPE_IDS.structure
				? {
						dispatch,
						fieldType,
						selectedMappingTypes,
						selectedSourceTypeId,
				  }
				: {
						dispatch,
						fieldType,
						selectedItem,
						selectedSourceTypeId,
				  };

		loadFields(data).then((newFieldSets) => {
			setFieldSets(newFieldSets);
		});
	}, [
		dispatch,
		fieldType,
		selectedItem,
		selectedMappingTypes,
		selectedSourceTypeId,
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
							setSelectedSourceTypeId(event.target.value);

							setSelectedItem({});

							if (isMapped(mappedItem)) {
								onMappingSelect({
									classNameId: '',
									classPK: '',
									fieldId: '',
									mappedField: '',
								});
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
								value: MAPPING_SOURCE_TYPE_IDS.structure,
							},
							{
								label: Liferay.Language.get('specific-content'),
								value: MAPPING_SOURCE_TYPE_IDS.content,
							},
						]}
						value={selectedSourceTypeId}
					/>
				</ClayForm.Group>
			)}

			{selectedSourceTypeId === MAPPING_SOURCE_TYPE_IDS.content && (
				<ClayForm.Group small>
					<ItemSelector
						label={Liferay.Language.get('content')}
						onItemSelect={onInfoItemSelect}
						selectedItemTitle={selectedItem.title}
					/>
				</ClayForm.Group>
			)}

			<ClayForm.Group small>
				<MappingFieldSelect
					fieldSets={fieldSets}
					fieldType={fieldType}
					onValueSelect={onFieldSelect}
					value={selectedItem.mappedField || selectedItem.fieldId}
				/>
			</ClayForm.Group>
		</>
	);
}

function MappingFieldSelect({fieldSets, fieldType, onValueSelect, value}) {
	const mappingSelectorFieldSelectId = useId();

	const hasWarnings = fieldSets && fieldSets.length === 0;

	return (
		<ClayForm.Group
			className={classNames({'has-warning': hasWarnings})}
			small
		>
			<label htmlFor="mappingSelectorFieldSelect">
				{Liferay.Language.get('field')}
			</label>

			<ClaySelect
				aria-label={Liferay.Language.get('field')}
				disabled={!(fieldSets && fieldSets.length)}
				id={mappingSelectorFieldSelectId}
				onChange={onValueSelect}
				value={value}
			>
				{fieldSets && fieldSets.length && (
					<>
						<ClaySelect.Option
							label={UNMAPPED_OPTION.label}
							value={UNMAPPED_OPTION.value}
						/>

						{fieldSets.map((fieldSet, index) => {
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
		}),
		PropTypes.shape({mappedField: PropTypes.string}),
	]),
	onMappingSelect: PropTypes.func.isRequired,
};
