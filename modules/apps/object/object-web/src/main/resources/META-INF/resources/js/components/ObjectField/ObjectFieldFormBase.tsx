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

import ClayForm, {ClayToggle} from '@clayui/form';
import {
	API,
	AutoComplete,
	FormError,
	Input,
	Select,
	SingleSelect,
} from '@liferay/object-js-components-web';
import React, {
	ChangeEventHandler,
	ReactNode,
	useEffect,
	useMemo,
	useState,
} from 'react';

import {toCamelCase} from '../../utils/string';
import {AggregationFormBase} from './AggregationFormBase';
import {AttachmentFormBase} from './AttachmentFormBase';
import {FORMULA_OUTPUT_OPTIONS, FormulaOutput} from './formulaFieldUtil';

import './ObjectFieldFormBase.scss';

interface IProps {
	children?: ReactNode;
	disabled?: boolean;
	editingField?: boolean;
	errors: ObjectFieldErrors;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	objectDefinitionId: number;
	objectField: Partial<ObjectField>;
	objectFieldTypes: ObjectFieldType[];
	objectName: string;
	objectRelationshipId?: number;
	onAggregationFilterChange?: (aggregationFilterArray: []) => void;
	onRelationshipChange?: (objectDefinitionId2: number) => void;
	setValues: (values: Partial<ObjectField>) => void;
}

type TObjectRelationship = {
	deletionType: string;
	id: number;
	label: LocalizedValue<string>;
	name: string;
	objectDefinitionId2: number;
};

export type ObjectFieldErrors = FormError<
	ObjectField & {[key in ObjectFieldSettingName]: unknown}
>;

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

export default function ObjectFieldFormBase({
	children,
	disabled,
	editingField,
	errors,
	handleChange,
	objectDefinitionId,
	objectField: values,
	objectFieldTypes,
	objectName,
	objectRelationshipId,
	onAggregationFilterChange,
	onRelationshipChange,
	setValues,
}: IProps) {
	const businessTypeMap = useMemo(() => {
		const businessTypeMap = new Map<string, ObjectFieldType>();

		objectFieldTypes.forEach((type) => {
			businessTypeMap.set(type.businessType, type);
		});

		return businessTypeMap;
	}, [objectFieldTypes]);

	const [picklistDefaultValueQuery, setPicklistDefaultValueQuery] = useState<
		string
	>('');
	const [pickLists, setPickLists] = useState<Partial<PickList>[]>([]);
	const [pickListItems, setPickListItems] = useState<PickListItem[]>([]);
	const [oneToManyRelationship, setOneToManyRelationship] = useState<
		TObjectRelationship
	>();
	const [selectedOutput, setSelectedOutput] = useState<string>('');

	useEffect(() => {
		const {businessType, defaultValue, objectFieldSettings} = values;

		if (businessType === 'Picklist' && objectFieldSettings?.length) {
			const [{value}] = objectFieldSettings;
			const {objectStates} = value as ObjectFieldPicklistSetting;
			const defaultPicklistValue = objectStates.find(
				({key}) => key === defaultValue
			);

			if (!defaultPicklistValue && defaultValue) {
				setValues({defaultValue: undefined});
			}
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values.defaultValue]);

	const validListTypeDefinitionId =
		values.listTypeDefinitionId !== undefined &&
		values.listTypeDefinitionId !== 0;

	useEffect(() => {
		if (
			values.businessType === 'Picklist' ||
			values.businessType === 'MultiselectPicklist'
		) {
			API.getPickLists().then(setPickLists);

			if (values.state && values.listTypeDefinitionId) {
				API.getPickListItems(values.listTypeDefinitionId).then(
					setPickListItems
				);
			}
		}

		if (values.businessType === 'Formula') {
			const output = values.objectFieldSettings?.find(
				(fieldSetting) => fieldSetting.name === 'output'
			);

			if (output) {
				setSelectedOutput(
					FORMULA_OUTPUT_OPTIONS.find(
						(formulaOption) => formulaOption.value === output?.value
					)?.label as string
				);
			}
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values.businessType, values.listTypeDefinitionId]);

	const filteredPicklistItems = useMemo(() => {
		return pickListItems.filter(({name}) => {
			return name
				.toLowerCase()
				.includes(picklistDefaultValueQuery.toLocaleLowerCase());
		});
	}, [picklistDefaultValueQuery, pickListItems]);

	const selectedPicklist = useMemo(() => {
		return pickLists.find(({id}) => values.listTypeDefinitionId === id);
	}, [pickLists, values.listTypeDefinitionId]);

	const handleTypeChange = async (option: ObjectFieldType) => {
		if (
			option.businessType === 'Picklist' ||
			values.businessType === 'MultiselectPicklist'
		) {
			setPickLists(await API.getPickLists());
		}

		let objectFieldSettings: ObjectFieldSetting[] | undefined;

		switch (option.businessType) {
			case 'Attachment':
				objectFieldSettings = [
					{
						name: 'acceptedFileExtensions',
						value: 'jpeg, jpg, pdf, png',
					},
					{
						name: 'maximumFileSize',
						value: 100,
					},
				];
				break;

			case 'LongText':
			case 'Text':
				objectFieldSettings = [
					{
						name: 'showCounter',
						value: false,
					},
				];
				break;

			default:
				break;
		}

		const isSearchableByText =
			option.businessType === 'Attachment' || option.dbType === 'String';

		const indexedAsKeyword = isSearchableByText && values.indexedAsKeyword;

		const indexedLanguageId =
			isSearchableByText && !values.indexedAsKeyword
				? values.indexedLanguageId ?? defaultLanguageId
				: null;

		setValues({
			DBType: option.dbType,
			businessType: option.businessType,
			defaultValue: '',
			indexedAsKeyword,
			indexedLanguageId,
			listTypeDefinitionExternalReferenceCode: '',
			listTypeDefinitionId: 0,
			objectFieldSettings,
			state: false,
		});
	};

	useEffect(() => {
		if (objectRelationshipId) {
			const makeFetch = async () => {
				const relationshipData = await API.getRelationship<
					TObjectRelationship
				>(objectRelationshipId!);

				if (relationshipData.id) {
					setOneToManyRelationship(relationshipData);
				}
			};

			makeFetch();
		}
	}, [objectRelationshipId]);

	const getMandatoryToggleState = () => {
		if (
			oneToManyRelationship &&
			oneToManyRelationship.deletionType !== 'disassociate'
		) {
			return false;
		}

		return disabled || values.state;
	};

	return (
		<>
			<Input
				disabled={disabled}
				error={errors.name}
				label={Liferay.Language.get('field-name')}
				name="name"
				onChange={handleChange}
				required
				value={
					values.name ??
					toCamelCase(values.label?.[defaultLanguageId] ?? '')
				}
			/>

			<SingleSelect<ObjectFieldType>
				disabled={disabled}
				error={errors.businessType}
				label={Liferay.Language.get('type')}
				onChange={handleTypeChange}
				options={objectFieldTypes}
				required
				value={businessTypeMap.get(values.businessType ?? '')?.label}
			/>

			{values.businessType === 'Attachment' && (
				<AttachmentFormBase
					disabled={disabled}
					error={errors.fileSource}
					objectFieldSettings={
						values.objectFieldSettings as ObjectFieldSetting[]
					}
					objectName={objectName}
					setValues={setValues}
				/>
			)}

			{values.businessType === 'Aggregation' && (
				<AggregationFormBase
					editingField={editingField}
					errors={errors}
					objectDefinitionId={objectDefinitionId}
					objectFieldSettings={
						values.objectFieldSettings as ObjectFieldSetting[]
					}
					onAggregationFilterChange={onAggregationFilterChange}
					onRelationshipChange={onRelationshipChange}
					setValues={setValues}
				/>
			)}

			{values.businessType === 'Formula' && (
				<SingleSelect<FormulaOutput>
					error={errors.output}
					label={Liferay.Language.get('output')}
					onChange={({label, value}) => {
						setValues({
							objectFieldSettings: [
								...(values.objectFieldSettings?.filter(
									(objectFieldSetting) =>
										objectFieldSetting.name !== 'output'
								) as ObjectFieldSetting[]),
								{
									name: 'output',
									value,
								},
							],
						});

						setSelectedOutput(label);
					}}
					options={FORMULA_OUTPUT_OPTIONS}
					required
					value={selectedOutput}
				/>
			)}

			{(values.businessType === 'Picklist' ||
				values.businessType === 'MultiselectPicklist') && (
				<Select
					disabled={disabled}
					error={errors.listTypeDefinitionId}
					label={Liferay.Language.get('picklist')}
					onChange={({target: {value}}) => {
						setValues({
							defaultValue: '',
							listTypeDefinitionExternalReferenceCode: pickLists.find(
								({name}) => name === value
							)?.externalReferenceCode,
							listTypeDefinitionId: Number(
								pickLists.find(({name}) => name === value)?.id
							),
							state: false,
						});
					}}
					options={pickLists}
					required
					value={
						validListTypeDefinitionId ? selectedPicklist?.name : ''
					}
				/>
			)}

			{children}

			<ClayForm.Group className="lfr-objects__object-field-form-base-form-group-toggles">
				{values.businessType !== 'Aggregation' &&
					values.businessType !== 'Formula' && (
						<ClayToggle
							disabled={getMandatoryToggleState()}
							label={Liferay.Language.get('mandatory')}
							name="required"
							onToggle={(required) => setValues({required})}
							toggled={values.required || values.state}
						/>
					)}

				{values.businessType === 'Picklist' &&
					validListTypeDefinitionId && (
						<ClayToggle
							disabled={disabled}
							label={Liferay.Language.get('mark-as-state')}
							name="state"
							onToggle={async (state) => {
								if (state) {
									setValues({required: state, state});
									setPickListItems(
										await API.getPickListItems(
											values.listTypeDefinitionId!
										)
									);
								}
								else {
									setValues({
										defaultValue: '',
										required: state,
										state,
									});
								}
							}}
							toggled={values.state}
						/>
					)}
			</ClayForm.Group>

			{values.state && (
				<AutoComplete
					emptyStateMessage={Liferay.Language.get('option-not-found')}
					error={errors.defaultValue}
					items={filteredPicklistItems}
					label={Liferay.Language.get('default-value')}
					onChangeQuery={setPicklistDefaultValueQuery}
					onSelectItem={(item) => {
						setValues({
							defaultValue: item.key,
						});
					}}
					placeholder={Liferay.Language.get('choose-an-option')}
					query={picklistDefaultValueQuery}
					required
					value={
						filteredPicklistItems.find(
							({key}) => key === values.defaultValue
						)?.name
					}
				>
					{({name}) => (
						<div className="d-flex justify-content-between">
							<div>{name}</div>
						</div>
					)}
				</AutoComplete>
			)}
		</>
	);
}
