/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {InputLocalized} from 'frontend-js-components-web';
import React from 'react';

import {updateFieldSettings} from '../../../../utils/fieldSettings';
import ObjectFieldFormBase, {
	ObjectFieldErrors,
} from '../../ObjectFieldFormBase';
import {AttachmentProperties} from './AttachmentProperties';
import {AggregationFilters} from './BasicInfoTab';
import {MaxLengthProperties} from './MaxLengthProperties';

import '../../EditObjectFieldContent.scss';

interface BasicInfoContainerProps {
	baseResourceURL: string;
	creationLanguageId2?: Liferay.Language.Locale;
	errors: ObjectFieldErrors;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	isApproved: boolean;
	modelBuilder?: boolean;
	objectDefinition: Partial<ObjectDefinition>;
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionName: string;
	objectFieldTypes: ObjectFieldType[];
	objectRelationshipId: number;
	onSubmit?: () => void;
	readOnly: boolean;
	setAggregationFilters: (values: AggregationFilters[]) => void;
	setObjectDefinitionExternalReferenceCode2: (value: string) => void;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}

export function BasicInfoContainer({
	baseResourceURL,
	creationLanguageId2,
	errors,
	handleChange,
	isApproved,
	modelBuilder = false,
	objectDefinition,
	objectDefinitionExternalReferenceCode,
	objectDefinitionName,
	objectFieldTypes,
	objectRelationshipId,
	onSubmit,
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
		<div
			className={classNames({
				'lfr-objects__edit-object-field-card-content': !modelBuilder,
				'lfr-objects__edit-object-field-model-builder-panel': modelBuilder,
			})}
		>
			<InputLocalized
				disableFlag={readOnly}
				disabled={readOnly}
				error={errors.label}
				label={Liferay.Language.get('label')}
				onBlur={(event) => {
					event.stopPropagation();

					if (onSubmit) {
						onSubmit();
					}
				}}
				onChange={(label) => setValues({label})}
				required
				translations={values.label as LocalizedValue<string>}
			/>

			<ObjectFieldFormBase
				baseResourceURL={baseResourceURL}
				creationLanguageId2={
					creationLanguageId2 as Liferay.Language.Locale
				}
				disabled={disableFieldFormBase}
				editingObjectField
				errors={errors}
				handleChange={handleChange}
				modelBuilder={modelBuilder}
				objectDefinition={objectDefinition}
				objectDefinitionExternalReferenceCode={
					objectDefinitionExternalReferenceCode
				}
				objectDefinitionName={objectDefinitionName}
				objectField={values}
				objectFieldTypes={objectFieldTypes}
				objectRelationshipId={objectRelationshipId}
				onAggregationFilterChange={setAggregationFilters}
				onObjectRelationshipChange={
					setObjectDefinitionExternalReferenceCode2
				}
				onSubmit={onSubmit}
				setValues={setValues}
			>
				{values.businessType === 'Attachment' && (
					<AttachmentProperties
						errors={errors}
						objectFieldSettings={
							values.objectFieldSettings as ObjectFieldSetting[]
						}
						onSettingsChange={handleSettingsChange}
						onSubmit={onSubmit}
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
						onSubmit={onSubmit}
						setValues={setValues}
					/>
				)}
			</ObjectFieldFormBase>
		</div>
	);
}
