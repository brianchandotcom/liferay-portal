/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API, Input, SidebarCategory} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import React, {ElementType, useEffect, useState} from 'react';

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
	containerWrapper: ElementType;
	errors: ObjectFieldErrors;
	filterOperators: TFilterOperators;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	isApproved: boolean;
	isDefaultStorageType: boolean;
	modelBuilder?: boolean;
	objectDefinitionExternalReferenceCode: string;
	objectFieldTypes: ObjectFieldType[];
	objectName: string;
	objectRelationshipId: number;
	readOnly: boolean;
	setValues: (values: Partial<ObjectField>) => void;
	sidebarElements: SidebarCategory[];
	values: Partial<ObjectField>;
	workflowStatusJSONArray: LabelValueObject[];
}

export function BasicInfoTab({
	containerWrapper: ContainerWrapper,
	errors,
	filterOperators,
	handleChange,
	isApproved,
	isDefaultStorageType,
	modelBuilder = false,
	objectDefinitionExternalReferenceCode,
	objectFieldTypes,
	objectName,
	objectRelationshipId,
	readOnly,
	setValues,
	sidebarElements,
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
			<ContainerWrapper
				collapsable
				defaultExpanded
				displayTitle={Liferay.Language.get('basic-info')}
				displayType="unstyled"
				title={Liferay.Language.get('basic-info')}
			>
				<BasicInfoContainer
					creationLanguageId2={creationLanguageId2}
					errors={errors}
					handleChange={handleChange}
					isApproved={isApproved}
					modelBuilder={modelBuilder}
					objectDefinition={objectDefinition}
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
			</ContainerWrapper>

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
				<ContainerWrapper
					collapsable
					defaultExpanded
					displayTitle={Liferay.Language.get('formula')}
					displayType="unstyled"
					title={Liferay.Language.get('formula')}
				>
					<FormulaContainer
						errors={errors}
						modelBuilder={modelBuilder}
						objectFieldSettings={
							values.objectFieldSettings as ObjectFieldSetting[]
						}
						setValues={setValues}
						sidebarElements={sidebarElements}
					/>
				</ContainerWrapper>
			)}

			{values.DBType !== 'Blob' && values.businessType !== 'Formula' && (
				<ContainerWrapper
					collapsable
					defaultExpanded
					displayTitle={Liferay.Language.get('searchable')}
					displayType="unstyled"
					title={Liferay.Language.get('searchable')}
				>
					<SearchableContainer
						isApproved={isApproved}
						modelBuilder={modelBuilder}
						objectField={values}
						readOnly={readOnly}
						setValues={setValues}
					/>
				</ContainerWrapper>
			)}

			{Liferay.FeatureFlags['LPS-172017'] && (
				<ContainerWrapper
					collapsable
					defaultExpanded
					displayTitle={Liferay.Language.get('translation-options')}
					displayType="unstyled"
					title={Liferay.Language.get('translation-options')}
				>
					<TranslationOptionsContainer
						modelBuilder={modelBuilder}
						objectDefinition={objectDefinition}
						published={isApproved}
						setValues={setValues}
						values={values}
					/>
				</ContainerWrapper>
			)}

			{Liferay.FeatureFlags['LPS-135430'] && !isDefaultStorageType && (
				<ContainerWrapper
					collapsable
					defaultExpanded
					displayTitle={Liferay.Language.get('external-data-source')}
					displayType="unstyled"
					title={Liferay.Language.get('external-data-source')}
				>
					<Input
						className={classNames({
							'lfr-objects__edit-object-field-model-builder-panel': modelBuilder,
						})}
						label={Liferay.Language.get('external-reference-code')}
						name="externalReferenceCode"
						onChange={handleChange}
						value={values.externalReferenceCode}
					/>
				</ContainerWrapper>
			)}
		</>
	);
}
