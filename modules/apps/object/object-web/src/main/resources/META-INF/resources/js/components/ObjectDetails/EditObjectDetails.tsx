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

import {
	API,
	BetaButton,
	getLocalizableLabel,
	openToast,
} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import ObjectManagementToolbar from '../ObjectManagementToolbar';
import {ConfigurationContainer} from './ConfigurationContainer';
import {EntryDisplayContainer} from './EntryDisplayContainer';
import {ObjectDataContainer} from './ObjectDataContainer';
import {ScopeContainer} from './ScopeContainer';
import Sheet from './Sheet';
import {useObjectDetailsForm} from './useObjectDetailsForm';

import './ObjectDetails.scss';
import {AccountRestrictionContainer} from './AccountRestrictionContainer';
import {ExternalDataSourceContainer} from './ExternalDataSourceContainer';
import {TranslationsContainer} from './TranslationsContainer';

export type KeyValuePair = {
	key: string;
	value: string;
};
interface EditObjectDetailsProps {
	backURL: string;
	companyKeyValuePair: KeyValuePair[];
	dbTableName: string;
	externalReferenceCode: string;
	hasPublishObjectPermission: boolean;
	hasUpdateObjectDefinitionPermission: boolean;
	isApproved: boolean;
	label: LocalizedValue<string>;
	nonRelationshipObjectFieldsInfo: {
		label: LocalizedValue<string>;
		name: string;
	}[];
	objectDefinitionId: number;
	pluralLabel: LocalizedValue<string>;
	portletNamespace: string;
	shortName: string;
	siteKeyValuePair: KeyValuePair[];
	storageTypes: LabelValueObject[];
}

function setAccountRelationshipFieldMandatory(
	values: Partial<ObjectDefinition>
) {
	const {objectFields} = values;

	const newObjectFields = objectFields?.map((field) => {
		if (field.name === values.accountEntryRestrictedObjectFieldName) {
			return {
				...field,
				required: true,
			};
		}

		return field;
	});

	return {
		...values,
		objectFields: newObjectFields,
	};
}

export default function EditObjectDetails({
	backURL,
	companyKeyValuePair,
	dbTableName,
	externalReferenceCode,
	hasPublishObjectPermission,
	hasUpdateObjectDefinitionPermission,
	isApproved,
	label,
	nonRelationshipObjectFieldsInfo,
	objectDefinitionId,
	pluralLabel,
	portletNamespace,
	shortName,
	siteKeyValuePair,
	storageTypes,
}: EditObjectDetailsProps) {
	const [objectFields, setObjectFields] = useState<ObjectField[]>([]);

	const {
		errors,
		handleChange,
		handleValidate,
		setValues,
		values,
	} = useObjectDetailsForm({
		initialValues: {
			defaultLanguageId: 'en_US',
			externalReferenceCode,
			id: objectDefinitionId,
			label,
			name: shortName,
			pluralLabel,
		},
		onSubmit: () => {},
	});

	const onSubmit = async (draft: boolean) => {
		const validationErrors = handleValidate();

		if (!Object.keys(validationErrors).length) {
			delete values.objectRelationships;
			delete values.objectActions;
			delete values.objectLayouts;
			delete values.objectViews;

			let objectDefinition = values;

			if (values.accountEntryRestricted) {
				objectDefinition = setAccountRelationshipFieldMandatory(values);
			}

			const saveResponse = await API.putObjectDefinitionByExternalReferenceCode(
				objectDefinition
			);

			if (!saveResponse.ok) {
				const {title} = (await saveResponse.json()) as {
					status: string;
					title: string;
				};

				openToast({
					message: title,
					type: 'danger',
				});

				return;
			}

			if (!draft) {
				const publishResponse = await API.publishObjectDefinitionById(
					values.id as number
				);

				if (!publishResponse.ok) {
					const {title} = (await publishResponse.json()) as {
						status: string;
						title: string;
					};

					openToast({
						message: title,
						type: 'danger',
					});

					return;
				}

				openToast({
					message: Liferay.Language.get(
						'the-object-was-published-successfully'
					),
					type: 'success',
				});

				setTimeout(() => window.location.reload(), 1000);

				return;
			}

			openToast({
				message: Liferay.Language.get(
					'the-object-was-saved-successfully'
				),
				type: 'success',
			});

			setTimeout(() => window.location.reload(), 1000);
		}
	};

	useEffect(() => {
		const makeFetch = async () => {
			const objectFieldsResponse = await API.getObjectFieldsByExternalReferenceCode(
				externalReferenceCode
			);
			const objectDefinitionResponse = await API.getObjectDefinitionByExternalReferenceCode(
				externalReferenceCode
			);

			setValues(objectDefinitionResponse);
			setObjectFields(objectFieldsResponse);
		};

		makeFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [objectDefinitionId]);

	return (
		<>
			<div className="lfr-objects__object-definition-details-management-toolbar">
				<ObjectManagementToolbar
					backURL={backURL}
					externalReferenceCode={externalReferenceCode}
					hasPublishObjectPermission={hasPublishObjectPermission}
					hasUpdateObjectDefinitionPermission={
						hasUpdateObjectDefinitionPermission
					}
					isApproved={isApproved}
					label={getLocalizableLabel(
						values.defaultLanguageId as Liferay.Language.Locale,
						values.label,
						values.name
					)}
					objectDefinitionId={objectDefinitionId}
					onSubmit={onSubmit}
					portletNamespace={portletNamespace}
					screenNavigationCategoryKey="details"
					setValues={setValues}
					system={values.system as boolean}
				/>
			</div>

			<div className="lfr-objects__object-definition-details">
				<Sheet title={Liferay.Language.get('basic-information')}>
					<ObjectDataContainer
						dbTableName={dbTableName}
						errors={errors}
						handleChange={handleChange}
						hasUpdateObjectDefinitionPermission={
							hasUpdateObjectDefinitionPermission
						}
						isApproved={isApproved}
						setValues={setValues}
						values={values}
					/>

					<EntryDisplayContainer
						errors={errors}
						nonRelationshipObjectFieldsInfo={
							nonRelationshipObjectFieldsInfo
						}
						objectFields={objectFields}
						setValues={setValues}
						values={values}
					/>

					{Liferay.FeatureFlags['LPS-135430'] && (
						<div className="lfr__object-web-edit-object-details-external-data-source-container">
							<ExternalDataSourceContainer
								errors={errors}
								setValues={setValues}
								storageTypes={storageTypes}
								values={values}
							/>

							<div className="lfr__object-web-edit-object-details-external-data-source-container-beta">
								{values.storageType === 'salesforce' && (
									<BetaButton />
								)}
							</div>
						</div>
					)}

					<ScopeContainer
						companyKeyValuePair={companyKeyValuePair}
						errors={errors}
						hasUpdateObjectDefinitionPermission={
							hasUpdateObjectDefinitionPermission
						}
						isApproved={isApproved}
						setValues={setValues}
						siteKeyValuePair={siteKeyValuePair}
						values={values}
					/>

					{(Liferay.FeatureFlags['LPS-167253']
						? values.modifiable
						: !values.system) && (
						<AccountRestrictionContainer
							errors={errors}
							isApproved={isApproved}
							objectFields={objectFields}
							setValues={setValues}
							values={values}
						/>
					)}

					<ConfigurationContainer
						hasUpdateObjectDefinitionPermission={
							hasUpdateObjectDefinitionPermission
						}
						setValues={setValues}
						values={values}
					/>

					{Liferay.FeatureFlags['LPS-172017'] && (
						<TranslationsContainer
							setValues={setValues}
							values={values}
						/>
					)}
				</Sheet>
			</div>
		</>
	);
}
