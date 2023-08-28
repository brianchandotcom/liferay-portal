/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InputLocalized} from 'frontend-js-components-web';
import React from 'react';

import {updateFieldSettings} from '../../../../utils/fieldSettings';
import ObjectFieldFormBase, {
	ObjectFieldErrors,
} from '../../ObjectFieldFormBase';
import {AttachmentProperties} from './AttachmentProperties';
import {AggregationFilters} from './BasicInfoTab';
import {MaxLengthProperties} from './MaxLengthProperties';

interface BasicInfoContainerProps {
	creationLanguageId2?: Liferay.Language.Locale;
	errors: ObjectFieldErrors;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	isApproved: boolean;
	objectDefinitionExternalReferenceCode: string;
	objectFieldTypes: ObjectFieldType[];
	objectName: string;
	objectRelationshipId: number;
	readOnly: boolean;
	setAggregationFilters: (values: AggregationFilters[]) => void;
	setObjectDefinitionExternalReferenceCode2: (value: string) => void;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}

export function BasicInfoContainer({
	creationLanguageId2,
	errors,
	handleChange,
	isApproved,
	objectDefinitionExternalReferenceCode,
	objectFieldTypes,
	objectName,
	objectRelationshipId,
	readOnly,
	setAggregationFilters,
	setObjectDefinitionExternalReferenceCode2,
	setValues,
	values,
}: BasicInfoContainerProps) {
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

	return (
		<>
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
				onRelationshipChange={setObjectDefinitionExternalReferenceCode2}
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
		</>
	);
}
