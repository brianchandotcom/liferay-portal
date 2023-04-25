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
	SingleSelect,
	stringIncludesQuery,
} from '@liferay/object-js-components-web';
import React, {
	ChangeEventHandler,
	ReactNode,
	useEffect,
	useMemo,
	useState,
} from 'react';

import {defaultLanguageId} from '../../utils/constants';
import {
	getDefaultValueFieldSettings,
	getUpdatedDefaultValueType,
} from '../../utils/defaultValues';
import {removeFieldSettings} from '../../utils/fieldSettings';
import {toCamelCase} from '../../utils/string';
import {AggregationFormBase} from './AggregationFormBase';
import {AttachmentFormBase} from './AttachmentFormBase';
import {UniqueValues} from './UniqueValues';
import {FORMULA_OUTPUT_OPTIONS, FormulaOutput} from './formulaFieldUtil';

import './ObjectFieldFormBase.scss';

interface ObjectFieldFormBaseProps {
	children?: ReactNode;
	creationLanguageId2?: Liferay.Language.Locale;
	disabled?: boolean;
	editingField?: boolean;
	errors: ObjectFieldErrors;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	objectDefinition?: ObjectDefinition;
	objectDefinitionExternalReferenceCode: string;
	objectField: Partial<ObjectField>;
	objectFieldTypes: ObjectFieldType[];
	objectName: string;
	objectRelationshipId?: number;
	onAggregationFilterChange?: (aggregationFilterArray: []) => void;
	onRelationshipChange?: (
		objectDefinitionExternalReferenceCode2: string
	) => void;
	setValues: (values: Partial<ObjectField>) => void;
}

type TObjectRelationship = {
	deletionType: string;
	id: number;
	label: LocalizedValue<string>;
	name: string;
	objectDefinitionExternalReferenceCode2: number;
};

export type ObjectFieldErrors = FormError<
	ObjectField & {[key in ObjectFieldSettingName]: unknown}
>;

const fieldSettingsMap = new Map<string, ObjectFieldSetting[]>([
	[
		'Aggregation',
		[
			{
				name: 'filters',
				objectFieldId: 0,
				value: Array(0),
			},
		],
	],
	[
		'Attachment',
		[
			{
				name: 'acceptedFileExtensions',
				value: 'jpeg, jpg, pdf, png',
			},
			{
				name: 'maximumFileSize',
				value: 100,
			},
		],
	],
	[
		'LongText' || 'Text',
		[
			{
				name: 'showCounter',
				value: false,
			},
		],
	],
]);

async function getFieldSettingsByBusinessType(
	objectRelationshipId: number,
	setOneToManyRelationship: (value: TObjectRelationship) => void,
	setPickLists: (value: PickList[]) => void,
	setSelectedOutput: (value: string) => void,
	values: Partial<ObjectField>
) {
	const {businessType, objectFieldSettings} = values;

	if (businessType === 'Picklist' || businessType === 'MultiselectPicklist') {
		const picklistData = await API.getPickLists();

		setPickLists(picklistData);
	}

	if (businessType === 'Formula') {
		const output = objectFieldSettings?.find(
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

	if (businessType === 'Relationship' && objectRelationshipId) {
		const relationshipData = await API.getRelationship<TObjectRelationship>(
			objectRelationshipId!
		);

		if (relationshipData.id) {
			setOneToManyRelationship(relationshipData);
		}
	}
}

export default function ObjectFieldFormBase({
	children,
	creationLanguageId2,
	disabled,
	editingField,
	errors,
	handleChange,
	objectDefinition,
	objectDefinitionExternalReferenceCode,
	objectField: values,
	objectFieldTypes,
	objectName,
	objectRelationshipId,
	onAggregationFilterChange,
	onRelationshipChange,
	setValues,
}: ObjectFieldFormBaseProps) {
	const businessTypeMap = useMemo(() => {
		const businessTypeMap = new Map<string, ObjectFieldType>();

		objectFieldTypes.forEach((type) => {
			businessTypeMap.set(type.businessType, type);
		});

		return businessTypeMap;
	}, [objectFieldTypes]);

	const [pickLists, setPickLists] = useState<Partial<PickList>[]>([]);
	const [picklistQuery, setPicklistQuery] = useState<string>('');

	const [oneToManyRelationship, setOneToManyRelationship] = useState<
		TObjectRelationship
	>();
	const [selectedOutput, setSelectedOutput] = useState<string>('');

	const validListTypeDefinitionId =
		values.listTypeDefinitionId !== undefined &&
		values.listTypeDefinitionId !== 0;

	const filteredPicklist = useMemo(() => {
		return pickLists.filter(({name}) => {
			return stringIncludesQuery(name as string, picklistQuery);
		});
	}, [picklistQuery, pickLists]);

	const selectedPicklist = useMemo(() => {
		return pickLists.find(({id}) => values.listTypeDefinitionId === id);
	}, [pickLists, values.listTypeDefinitionId]);

	const handleTypeChange = async (option: ObjectFieldType) => {
		const objectFieldSettings: ObjectFieldSetting[] =
			fieldSettingsMap.get(option.businessType) || [];

		const indexed = option.businessType !== 'Encrypted';

		const isSearchableByText =
			option.businessType === 'Attachment' ||
			option.dbType === 'Clob' ||
			option.dbType === 'String';

		const indexedAsKeyword = isSearchableByText && values.indexedAsKeyword;

		const indexedLanguageId =
			isSearchableByText && !values.indexedAsKeyword
				? values.indexedLanguageId ?? defaultLanguageId
				: null;

		setValues({
			DBType: option.dbType,
			businessType: option.businessType,
			indexed,
			indexedAsKeyword,
			indexedLanguageId,
			listTypeDefinitionExternalReferenceCode: '',
			listTypeDefinitionId: 0,
			objectFieldSettings,
			state: false,
		});
	};

	const getMandatoryToggleDisabledState = () => {
		if (
			objectDefinition?.accountEntryRestricted &&
			objectDefinition?.accountEntryRestrictedObjectFieldName ===
				values.name
		) {
			return true;
		}

		if (
			oneToManyRelationship &&
			oneToManyRelationship.deletionType !== 'disassociate'
		) {
			return false;
		}

		const readOnlySetting = values.objectFieldSettings?.find(
			(fieldSetting) => fieldSetting.name === 'readOnly'
		);

		if (
			readOnlySetting?.value === 'true' ||
			readOnlySetting?.value === 'conditional'
		) {
			return true;
		}

		return disabled || values.state;
	};

	useEffect(() => {
		const makeFetch = async () => {
			await getFieldSettingsByBusinessType(
				objectRelationshipId as number,
				setOneToManyRelationship,
				setPickLists,
				setSelectedOutput,
				values
			);
		};

		makeFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values.businessType]);

	const handleStateToggleChange = (toggled: boolean) => {
		if (Liferay.FeatureFlags['LPS-163716']) {
			let defaultValue;
			let defaultValueType;

			if (values.id) {
				const currentDefaultValueSettings = getDefaultValueFieldSettings(
					values
				);
				defaultValue = currentDefaultValueSettings.defaultValue;
				defaultValueType = currentDefaultValueSettings.defaultValueType;
			}

			if (toggled) {
				if (defaultValueType && defaultValue) {
					setValues({required: toggled, state: toggled});
				}
				else if (!defaultValueType || !defaultValue) {
					setValues({
						objectFieldSettings: getUpdatedDefaultValueType(
							values,
							'inputAsValue'
						),
						required: toggled,
						state: toggled,
					});
				}
			}
			else {
				setValues({
					required: toggled,
					state: toggled,
				});
			}
		}
		else {
			if (toggled) {
				setValues({
					required: toggled,
					state: toggled,
				});
			}
			else {
				setValues({
					defaultValue: undefined,
					objectFieldSettings: removeFieldSettings(
						['stateFlow'],
						values
					),
					required: toggled,
					state: toggled,
				});
			}
		}
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
					toCamelCase(values.label?.[defaultLanguageId] ?? '', true)
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
					creationLanguageId2={
						creationLanguageId2 as Liferay.Language.Locale
					}
					editingField={editingField}
					errors={errors}
					objectDefinitionExternalReferenceCode={
						objectDefinitionExternalReferenceCode
					}
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
						let newObjectFieldSettings: ObjectFieldSetting[] = [];

						if (values.objectFieldSettings) {
							newObjectFieldSettings = values.objectFieldSettings?.filter(
								(objectFieldSetting) =>
									objectFieldSetting.name !== 'output'
							) as ObjectFieldSetting[];
						}

						setValues({
							objectFieldSettings: [
								...newObjectFieldSettings,
								{
									name: 'output',
									value,
								},
							],
						});

						setSelectedOutput(label);
					}}
					options={FORMULA_OUTPUT_OPTIONS.filter(
						(formulaOutput) =>
							formulaOutput.value === 'Decimal' ||
							formulaOutput.value === 'Integer'
					)}
					required
					value={selectedOutput}
				/>
			)}

			{(values.businessType === 'Picklist' ||
				values.businessType === 'MultiselectPicklist') && (
				<AutoComplete<Partial<PickList>>
					creationLanguageId={
						creationLanguageId2 as Liferay.Language.Locale
					}
					disabled={disabled}
					emptyStateMessage={Liferay.Language.get('option-not-found')}
					error={errors.listTypeDefinitionId}
					items={filteredPicklist}
					label={Liferay.Language.get('picklist')}
					onChangeQuery={setPicklistQuery}
					onSelectItem={(item) => {
						Liferay.FeatureFlags['LPS-163716']
							? setValues({
									listTypeDefinitionExternalReferenceCode:
										item.externalReferenceCode,
									listTypeDefinitionId: item.id,
									objectFieldSettings: removeFieldSettings(
										['defaultValue', 'stateFlow'],
										values
									),
							  })
							: setValues({
									defaultValue: undefined,
									listTypeDefinitionExternalReferenceCode:
										item.externalReferenceCode,
									listTypeDefinitionId: item.id,
									objectFieldSettings: removeFieldSettings(
										['stateFlow'],
										values
									),
							  });
					}}
					query={picklistQuery}
					value={selectedPicklist?.name}
				>
					{({name}) => (
						<div className="d-flex justify-content-between">
							<div>{name}</div>
						</div>
					)}
				</AutoComplete>
			)}

			{children}

			<ClayForm.Group>
				{values.businessType !== 'Aggregation' &&
					values.businessType !== 'Formula' && (
						<ClayToggle
							disabled={getMandatoryToggleDisabledState()}
							label={Liferay.Language.get('mandatory')}
							name="required"
							onToggle={(required) => setValues({required})}
							toggled={values.required || values.state}
						/>
					)}
			</ClayForm.Group>

			{values.businessType === 'Picklist' && validListTypeDefinitionId && (
				<ClayForm.Group>
					<ClayToggle
						disabled={
							disabled ||
							(Liferay.FeatureFlags['LPS-167253']
								? !objectDefinition?.modifiable
								: objectDefinition?.system)
						}
						label={Liferay.Language.get('mark-as-state')}
						name="state"
						onToggle={async (state) => {
							handleStateToggleChange(state);
						}}
						toggled={values.state}
					/>
				</ClayForm.Group>
			)}

			{Liferay.FeatureFlags['LPS-135398'] &&
				(values.businessType === 'Text' ||
					values.businessType === 'Integer') && (
					<UniqueValues
						disabled={disabled}
						objectField={values}
						setValues={setValues}
					/>
				)}
		</>
	);
}
