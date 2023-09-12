/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API, Card, Input} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import {ObjectFieldErrors} from '../../ObjectFieldFormBase';
import {AggregationFilterContainer} from './AggregationFilterContainer';
import {BasicInfoContainer} from './BasicInfoContainer';
import {FormulaContainer} from './FormulaContainer';
import {SearchableContainer} from './SearchableContainer';
import {TranslationOptionsContainer} from './TranslationOptionsContainer';

export interface AggregationFilters {
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

interface BasicInfoTabProps {
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

export function BasicInfoTab({
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
}: BasicInfoTabProps) {
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
				<BasicInfoContainer
					creationLanguageId2={creationLanguageId2}
					errors={errors}
					handleChange={handleChange}
					isApproved={isApproved}
					objectDefinitionExternalReferenceCode={
						objectDefinitionExternalReferenceCode
					}
					objectFieldTypes={objectFieldTypes}
					objectName={objectName}
					objectRelationshipId={objectRelationshipId}
					readOnly={readOnly}
					setAggregationFilters={setAggregationFilters}
					setObjectDefinitionExternalReferenceCode2={
						setObjectDefinitionExternalReferenceCode2
					}
					setValues={setValues}
					values={values}
				/>
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
				<Card title={Liferay.Language.get('formula')}>
					<FormulaContainer
						errors={errors}
						objectFieldSettings={
							values.objectFieldSettings as ObjectFieldSetting[]
						}
						setValues={setValues}
					/>
				</Card>
			)}

			{values.DBType !== 'Blob' && values.businessType !== 'Formula' && (
				<Card title={Liferay.Language.get('searchable')}>
					<SearchableContainer
						errors={errors}
						isApproved={isApproved}
						objectField={values}
						readOnly={readOnly}
						setValues={setValues}
					/>
				</Card>
			)}

			{Liferay.FeatureFlags['LPS-172017'] && (
				<Card title={Liferay.Language.get('translation-options')}>
					<TranslationOptionsContainer
						objectDefinition={objectDefinition}
						published={isApproved}
						setValues={setValues}
						values={values}
					/>
				</Card>
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
