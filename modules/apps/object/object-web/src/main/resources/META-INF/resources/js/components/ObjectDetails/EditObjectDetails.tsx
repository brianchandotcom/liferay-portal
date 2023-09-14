/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
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
	isRootDescendantNode: boolean;
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
	isRootDescendantNode,
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
				const publishResponse = await API.postObjectDefinitionPublish(
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
			const objectFieldsResponse = await API.getObjectDefinitionByExternalReferenceCodeObjectFields(
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
					isRootDescendantNode={isRootDescendantNode}
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
					<ClayPanel
						displayTitle={Liferay.Language.get(
							'object-definition-data'
						)}
						displayType="unstyled"
					>
						<ClayPanel.Body>
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
						</ClayPanel.Body>
					</ClayPanel>

					<ClayPanel
						collapsable
						defaultExpanded
						displayTitle={Liferay.Language.get('entry-display')}
						displayType="unstyled"
					>
						<ClayPanel.Body>
							<EntryDisplayContainer
								errors={errors}
								nonRelationshipObjectFieldsInfo={
									nonRelationshipObjectFieldsInfo
								}
								objectFields={objectFields}
								setValues={setValues}
								values={values}
							/>
						</ClayPanel.Body>
					</ClayPanel>

					{Liferay.FeatureFlags['LPS-135430'] && (
						<ClayPanel
							collapsable
							defaultExpanded
							displayTitle={Liferay.Language.get(
								'external-data-source'
							)}
							displayType="unstyled"
						>
							<ClayPanel.Body>
								<div className="lfr__object-web-edit-object-details-external-data-source-container">
									<ExternalDataSourceContainer
										errors={errors}
										setValues={setValues}
										storageTypes={storageTypes}
										values={values}
									/>

									<div className="lfr__object-web-edit-object-details-external-data-source-container-beta">
										{values.storageType ===
											'salesforce' && <BetaButton />}
									</div>
								</div>
							</ClayPanel.Body>
						</ClayPanel>
					)}

					<ClayPanel
						collapsable
						defaultExpanded
						displayTitle={Liferay.Language.get('scope')}
						displayType="unstyled"
					>
						<ClayPanel.Body>
							<ScopeContainer
								companyKeyValuePairs={companyKeyValuePair}
								errors={errors}
								hasUpdateObjectDefinitionPermission={
									hasUpdateObjectDefinitionPermission
								}
								isApproved={isApproved}
								isRootDescendantNode={isRootDescendantNode}
								setValues={setValues}
								siteKeyValuePairs={siteKeyValuePair}
								values={values}
							/>
						</ClayPanel.Body>
					</ClayPanel>

					{(Liferay.FeatureFlags['LPS-167253']
						? values.modifiable
						: !values.system) && (
						<ClayPanel
							collapsable
							defaultExpanded
							displayTitle={Liferay.Language.get(
								'account-restriction'
							)}
							displayType="unstyled"
						>
							<ClayPanel.Body>
								<AccountRestrictionContainer
									errors={errors}
									isApproved={isApproved}
									isRootDescendantNode={isRootDescendantNode}
									objectFields={objectFields}
									setValues={setValues}
									values={values}
								/>
							</ClayPanel.Body>
						</ClayPanel>
					)}

					<ClayPanel
						collapsable
						defaultExpanded
						displayTitle={Liferay.Language.get('configuration')}
						displayType="unstyled"
					>
						<ClayPanel.Body>
							<ConfigurationContainer
								hasUpdateObjectDefinitionPermission={
									hasUpdateObjectDefinitionPermission
								}
								isRootDescendantNode={isRootDescendantNode}
								setValues={setValues}
								values={values}
							/>
						</ClayPanel.Body>
					</ClayPanel>

					{Liferay.FeatureFlags['LPS-172017'] && (
						<ClayPanel
							collapsable
							defaultExpanded
							displayTitle={Liferay.Language.get('translations')}
							displayType="unstyled"
						>
							<ClayPanel.Body>
								<TranslationsContainer
									setValues={setValues}
									values={values}
								/>
							</ClayPanel.Body>
						</ClayPanel>
					)}
				</Sheet>
			</div>
		</>
	);
}
