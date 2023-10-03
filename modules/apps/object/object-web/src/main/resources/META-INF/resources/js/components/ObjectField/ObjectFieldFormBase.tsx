/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import {
	API,
	AutoComplete,
	FormError,
	Input,
	SingleSelect,
	Toggle,
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
import {TimeStorage} from './TimeStorage';
import {UniqueValues} from './UniqueValues';
import {FORMULA_OUTPUT_OPTIONS, FormulaOutput} from './formulaFieldUtil';

import './ObjectFieldFormBase.scss';

interface ObjectFieldFormBaseProps {
	children?: ReactNode;
	creationLanguageId2?: Liferay.Language.Locale;
	disabled?: boolean;
	editingObjectField?: boolean;
	errors: ObjectFieldErrors;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	objectDefinition?: Partial<ObjectDefinition>;
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionName: string;
	objectField: Partial<ObjectField>;
	objectFieldTypes: ObjectFieldType[];
	objectRelationshipId?: number;
	onAggregationFilterChange?: (aggregationFilterArray: []) => void;
	onObjectRelationshipChange?: (
		objectDefinitionExternalReferenceCode2: string
	) => void;
	onSubmit?: (values?: Partial<ObjectField>) => void;
	setValues: (values: Partial<ObjectField>) => void;
}

type TObjectRelationship = {
	deletionType: string;
	edge: boolean;
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
	[
		'DateTime',
		[
			{
				name: 'timeStorage',
				value: 'convertToUTC',
			},
		],
	],
]);

async function getObjectFieldSettingsByBusinessType(
	objectRelationshipId: number,
	setListTypeDefinitions: (value: ListTypeDefinition[]) => void,
	setOneToManyObjectRelationship: (value: TObjectRelationship) => void,
	setSelectedOutput: (value: string) => void,
	values: Partial<ObjectField>
) {
	const {businessType, objectFieldSettings} = values;

	if (businessType === 'Picklist' || businessType === 'MultiselectPicklist') {
		const listTypeDefinitions = await API.getListTypeDefinitions();

		setListTypeDefinitions(listTypeDefinitions);
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

	if (businessType === 'Relationship' && objectRelationshipId !== 0) {
		const relationshipData = await API.getObjectRelationship<
			TObjectRelationship
		>(objectRelationshipId!);

		if (relationshipData.id) {
			setOneToManyObjectRelationship(relationshipData);
		}
	}
}

export default function ObjectFieldFormBase({
	children,
	creationLanguageId2,
	disabled,
	editingObjectField = false,
	errors,
	handleChange,
	objectDefinition,
	objectDefinitionExternalReferenceCode,
	objectDefinitionName,
	objectField: values,
	objectFieldTypes,
	objectRelationshipId,
	onAggregationFilterChange,
	onObjectRelationshipChange,
	onSubmit,
	setValues,
}: ObjectFieldFormBaseProps) {
	const [listTypeDefinitions, setListTypeDefinitions] = useState<
		Partial<ListTypeDefinition>[]
	>([]);
	const [listTypeDefinitionQuery, setListTypeDefinitionQuery] = useState<
		string
	>('');

	const [
		oneToManyObjectRelationship,
		setOneToManyObjectRelationship,
	] = useState<TObjectRelationship>();
	const [selectedOutput, setSelectedOutput] = useState<string>('');

	const businessTypeMap = useMemo(() => {
		const businessTypeMap = new Map<string, ObjectFieldType>();

		objectFieldTypes.forEach((type) => {
			businessTypeMap.set(type.businessType, type);
		});

		return businessTypeMap;
	}, [objectFieldTypes]);

	const validListTypeDefinitionId =
		values.listTypeDefinitionId !== undefined &&
		values.listTypeDefinitionId !== 0;

	const filteredListTypeDefinitions = useMemo(() => {
		return listTypeDefinitions.filter(({name}) => {
			return stringIncludesQuery(name as string, listTypeDefinitionQuery);
		});
	}, [listTypeDefinitionQuery, listTypeDefinitions]);

	const selectedListTypeDefinition = useMemo(() => {
		return listTypeDefinitions.find(
			({externalReferenceCode}) =>
				values.listTypeDefinitionExternalReferenceCode ===
				externalReferenceCode
		);
	}, [listTypeDefinitions, values.listTypeDefinitionExternalReferenceCode]);

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

		setSelectedOutput('');

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

		if (onSubmit) {
			onSubmit({
				...values,
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
		}
	};

	const getMandatoryToggleDisabledState = () => {
		if (
			objectDefinition?.accountEntryRestricted &&
			objectDefinition?.accountEntryRestrictedObjectFieldName ===
				values.name
		) {
			return true;
		}

		if (values.readOnly === 'true' || values.readOnly === 'conditional') {
			return true;
		}

		if (
			oneToManyObjectRelationship &&
			oneToManyObjectRelationship.deletionType !== 'disassociate'
		) {
			return Liferay.FeatureFlags['LPS-187142']
				? oneToManyObjectRelationship.edge
				: false;
		}

		return disabled || values.localized || values.state;
	};

	useEffect(() => {
		const makeFetch = async () => {
			await getObjectFieldSettingsByBusinessType(
				objectRelationshipId as number,
				setListTypeDefinitions,
				setOneToManyObjectRelationship,
				setSelectedOutput,
				values
			);
		};

		makeFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values.businessType]);

	const handleStateToggleChange = (toggled: boolean) => {
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

				if (onSubmit) {
					onSubmit({
						...values,
						required: toggled,
						state: toggled,
					});
				}
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

				if (onSubmit) {
					onSubmit({
						...values,
						objectFieldSettings: getUpdatedDefaultValueType(
							values,
							'inputAsValue'
						),
						required: toggled,
						state: toggled,
					});
				}
			}
		}
		else {
			setValues({
				required: toggled,
				state: toggled,
			});

			if (onSubmit) {
				onSubmit({
					...values,
					required: toggled,
					state: toggled,
				});
			}
		}
	};

	const applyFeatureFlag = () => {
		return objectFieldTypes.filter((objectFieldType) => {
			return objectFieldType.businessType !== 'Formula';
		});
	};

	useEffect(() => {
		const makeFetch = async () => {
			await getObjectFieldSettingsByBusinessType(
				objectRelationshipId as number,
				setListTypeDefinitions,
				setOneToManyObjectRelationship,
				setSelectedOutput,
				values
			);
		};

		makeFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [objectRelationshipId, values.businessType]);

	return (
		<>
			<Input
				disabled={disabled}
				error={errors.name}
				label={Liferay.Language.get('field-name')}
				name="name"
				onBlur={(event) => {
					event.stopPropagation();

					if (onSubmit) {
						onSubmit();
					}
				}}
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
				options={
					!Liferay.FeatureFlags['LPS-164948']
						? applyFeatureFlag()
						: objectFieldTypes
				}
				required
				value={businessTypeMap.get(values.businessType ?? '')?.label}
			/>

			{values.businessType === 'Attachment' && (
				<AttachmentFormBase
					disabled={disabled}
					error={errors.fileSource}
					objectDefinitionName={objectDefinitionName}
					objectFieldSettings={
						values.objectFieldSettings as ObjectFieldSetting[]
					}
					onSubmit={onSubmit}
					setValues={setValues}
				/>
			)}

			{values.businessType === 'Aggregation' && (
				<AggregationFormBase
					creationLanguageId2={
						creationLanguageId2 as Liferay.Language.Locale
					}
					editingObjectField={editingObjectField}
					errors={errors}
					objectDefinitionExternalReferenceCode={
						objectDefinitionExternalReferenceCode
					}
					objectFieldSettings={
						values.objectFieldSettings as ObjectFieldSetting[]
					}
					onAggregationFilterChange={onAggregationFilterChange}
					onObjectRelationshipChange={onObjectRelationshipChange}
					onSubmit={onSubmit}
					setValues={setValues}
					values={values}
				/>
			)}

			{values.businessType === 'Formula' && (
				<SingleSelect<FormulaOutput>
					error={errors.output}
					label={Liferay.Language.get('output')}
					onBlur={(event) => {
						event.stopPropagation();

						if (onSubmit) {
							onSubmit();
						}
					}}
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
				<AutoComplete<Partial<ListTypeDefinition>>
					disabled={disabled}
					emptyStateMessage={Liferay.Language.get('option-not-found')}
					error={errors.listTypeDefinitionId}
					id="objectFieldFormBase"
					items={filteredListTypeDefinitions}
					label={Liferay.Language.get('picklist')}
					onActive={(item) =>
						item.name === selectedListTypeDefinition?.name
					}
					onChangeQuery={setListTypeDefinitionQuery}
					onSelectItem={(item) => {
						setValues({
							listTypeDefinitionExternalReferenceCode:
								item.externalReferenceCode,
							listTypeDefinitionId: item.id,
							objectFieldSettings: removeFieldSettings(
								['defaultValue', 'stateFlow'],
								values
							),
						});

						if (onSubmit) {
							onSubmit({
								...values,
								listTypeDefinitionExternalReferenceCode:
									item.externalReferenceCode,
								listTypeDefinitionId: item.id,
								objectFieldSettings: removeFieldSettings(
									['defaultValue', 'stateFlow'],
									values
								),
							});
						}
					}}
					query={listTypeDefinitionQuery}
					value={selectedListTypeDefinition?.name}
				>
					{({name}) => (
						<div className="d-flex justify-content-between">
							<div>{name}</div>
						</div>
					)}
				</AutoComplete>
			)}

			{values.businessType === 'DateTime' && (
				<TimeStorage
					disabled={disabled}
					objectFieldSettings={
						values.objectFieldSettings as ObjectFieldSetting[]
					}
					onSubmit={onSubmit}
					setValues={setValues}
				/>
			)}

			{children}

			<ClayForm.Group>
				{values.businessType !== 'Aggregation' &&
					values.businessType !== 'Formula' && (
						<Toggle
							disabled={getMandatoryToggleDisabledState()}
							label={Liferay.Language.get('mandatory')}
							name="required"
							onToggle={(required) => {
								setValues({required});

								if (onSubmit) {
									onSubmit({
										...values,
										required,
									});
								}
							}}
							toggled={values.required || values.state}
						/>
					)}
			</ClayForm.Group>

			{values.businessType === 'Picklist' && validListTypeDefinitionId && (
				<ClayForm.Group>
					<Toggle
						disabled={disabled || !objectDefinition?.modifiable}
						label={Liferay.Language.get('mark-as-state')}
						name="state"
						onToggle={(state) => {
							handleStateToggleChange(state);
						}}
						toggled={values.state}
					/>
				</ClayForm.Group>
			)}

			{(values.businessType === 'Text' ||
				values.businessType === 'Integer') && (
				<UniqueValues
					disabled={disabled}
					objectField={values}
					onSubmit={onSubmit}
					setValues={setValues}
				/>
			)}
		</>
	);
}
