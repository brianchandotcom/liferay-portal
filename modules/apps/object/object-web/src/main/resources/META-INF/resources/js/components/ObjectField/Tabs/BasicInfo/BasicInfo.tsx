/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	API,
	Card,
	Input,
	InputLocalized,
} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import {updateFieldSettings} from '../../../../utils/fieldSettings';
import ObjectFieldFormBase, {
	ObjectFieldErrors,
} from '../../ObjectFieldFormBase';
import {AggregationFilterContainer} from './AggregationFilterContainer';
import {AttachmentProperties} from './AttachmentProperties';
import {FormulaContainer} from './FormulaContainer';
import {MaxLengthProperties} from './MaxLengthProperties';
import {SearchableContainer} from './SearchableContainer';
import {TranslationOptionsContainer} from './TranslationOptionsContainer';

interface AggregationFilters {
	defaultSort?: boolean;
	fieldLabel?: string;
	filterBy?: string;
	filterType?: string;
	label: LocalizedValue<string>;
	objectFieldBusinessType?: string;
	objectFieldName: string;
	priority?: number;
	sortOrder?: string;
	type?: string;
	value?: string;
	valueList?: LabelValueObject[];
}

interface BasicInfoProps {
	errors: ObjectFieldErrors;
	filterOperators: TFilterOperators;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	isApproved: boolean;
	isDefaultStorageType: boolean;
	objectDefinitionExternalReferenceCode: string;
	objectFieldTypes: ObjectFieldType[];
	objectName: string;
	objectRelationshipId: number;
	readOnly: boolean;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
	workflowStatusJSONArray: LabelValueObject[];
}

export function BasicInfo({
	errors,
	filterOperators,
	handleChange,
	isApproved,
	isDefaultStorageType,
	objectDefinitionExternalReferenceCode,
	objectFieldTypes,
	objectName,
	objectRelationshipId,
	readOnly,
	setValues,
	values,
	workflowStatusJSONArray,
}: BasicInfoProps) {
	const [objectDefinition, setObjectDefinition] = useState<
		Partial<ObjectDefinition>
	>({enableLocalization: false});
	const [
		objectDefinitionExternalReferenceCode2,
		setObjectDefinitionExternalReferenceCode2,
	] = useState<string>();
	const [aggregationFilters, setAggregationFilters] = useState<
		AggregationFilters[]
	>([]);

	const [creationLanguageId2, setCreationLanguageId2] = useState<
		Liferay.Language.Locale
	>();

	const disableFieldFormBase = !!(
		isApproved ||
		values.system ||
		values.relationshipType
	);

	const handleSettingsChange = ({name, value}: ObjectFieldSetting) =>
		setValues({
			objectFieldSettings: updateFieldSettings(
				values.objectFieldSettings,
				{name, value}
			),
		});

	useEffect(() => {
		const makeFetch = async () => {
			const objectDefinitionResponse = await API.getObjectDefinitionByExternalReferenceCode(
				objectDefinitionExternalReferenceCode
			);

			setObjectDefinition(objectDefinitionResponse);
		};

		makeFetch();
	}, [objectDefinitionExternalReferenceCode]);

	return (
		<>
			<Card title={Liferay.Language.get('basic-info')}>
				<InputLocalized
					disableFlag={readOnly}
					disabled={readOnly}
					error={errors.label}
					label={Liferay.Language.get('label')}
					onChange={(label) => setValues({label})}
					required
					translations={values.label as LocalizedValue<string>}
				/>

				<ObjectFieldFormBase
					creationLanguageId2={
						creationLanguageId2 as Liferay.Language.Locale
					}
					disabled={disableFieldFormBase}
					editingField
					errors={errors}
					handleChange={handleChange}
					objectDefinitionExternalReferenceCode={
						objectDefinitionExternalReferenceCode
					}
					objectField={values}
					objectFieldTypes={objectFieldTypes}
					objectName={objectName}
					objectRelationshipId={objectRelationshipId}
					onAggregationFilterChange={setAggregationFilters}
					onRelationshipChange={
						setObjectDefinitionExternalReferenceCode2
					}
					setValues={setValues}
				>
					{values.businessType === 'Attachment' && (
						<AttachmentProperties
							errors={errors}
							objectFieldSettings={
								values.objectFieldSettings as ObjectFieldSetting[]
							}
							onSettingsChange={handleSettingsChange}
						/>
					)}

					{(values.businessType === 'Encrypted' ||
						values.businessType === 'LongText' ||
						values.businessType === 'Text') && (
						<MaxLengthProperties
							disabled={values.system}
							errors={errors}
							objectField={values}
							objectFieldSettings={
								values.objectFieldSettings as ObjectFieldSetting[]
							}
							onSettingsChange={handleSettingsChange}
							setValues={setValues}
						/>
					)}
				</ObjectFieldFormBase>
			</Card>

			{values.businessType === 'Aggregation' &&
				objectDefinitionExternalReferenceCode !==
					objectDefinitionExternalReferenceCode2 && (
					<AggregationFilterContainer
						aggregationFilters={aggregationFilters}
						creationLanguageId2={creationLanguageId2}
						filterOperators={filterOperators}
						objectDefinitionExternalReferenceCode2={
							objectDefinitionExternalReferenceCode2
						}
						setAggregationFilters={setAggregationFilters}
						setCreationLanguageId2={setCreationLanguageId2}
						setValues={setValues}
						values={values}
						workflowStatusJSONArray={workflowStatusJSONArray}
					/>
				)}

			{values.businessType === 'Formula' && (
				<FormulaContainer
					errors={errors}
					objectFieldSettings={
						values.objectFieldSettings as ObjectFieldSetting[]
					}
					setValues={setValues}
				/>
			)}

			{values.DBType !== 'Blob' && values.businessType !== 'Formula' && (
				<SearchableContainer
					errors={errors}
					isApproved={isApproved}
					objectField={values}
					readOnly={readOnly}
					setValues={setValues}
				/>
			)}

			{Liferay.FeatureFlags['LPS-172017'] && (
				<TranslationOptionsContainer
					objectDefinition={objectDefinition}
					published={isApproved}
					setValues={setValues}
					values={values}
				/>
			)}

			{Liferay.FeatureFlags['LPS-135430'] && !isDefaultStorageType && (
				<Card title={Liferay.Language.get('external-data-source')}>
					<Input
						label={Liferay.Language.get('external-reference-code')}
						name="externalReferenceCode"
						onChange={handleChange}
						value={values.externalReferenceCode}
					/>
				</Card>
			)}
		</>
	);
}
