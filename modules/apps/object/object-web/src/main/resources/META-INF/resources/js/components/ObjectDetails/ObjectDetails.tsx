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

import {ClayToggle} from '@clayui/form';
import ClayPanel from '@clayui/panel';
import {API, SingleSelect, openToast} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import {defaultLanguageId} from '../../utils/constants';
import ObjectManagementToolbar from '../ObjectManagementToolbar';
import {ConfigurationContainer} from './ConfigurationContainer';
import {EntryDisplayContainer} from './EntryDisplayContainer';
import {ObjectDataContainer} from './ObjectDataContainer';
import {ScopeContainer} from './ScopeContainer';
import Sheet from './Sheet';
import {useObjectDetailsForm} from './useObjectDetailsForm';

import './ObjectDetails.scss';

export type KeyValuePair = {
	key: string;
	value: string;
};
interface ObjectDetailsProps {
	DBTableName: string;
	backURL: string;
	companyKeyValuePair: KeyValuePair[];
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
}

export default function ObjectDetails({
	DBTableName,
	backURL,
	companyKeyValuePair,
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
}: ObjectDetailsProps) {
	const [objectFields, setObjectFields] = useState<ObjectField[]>([]);
	const [accountRelationshipFields, setAccountRelationshipFields] = useState<
		LabelValueObject[]
	>([]);

	const {
		errors,
		handleChange,
		handleValidate,
		setValues,
		values,
	} = useObjectDetailsForm({
		initialValues: {
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
			const saveResponse = await API.putObjectDefinitionByExternalReferenceCode(
				values
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
				const publishResponse = await API.publishObjectDefinition(
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

				window.location.reload();

				return;
			}

			openToast({
				message: Liferay.Language.get(
					'the-object-was-saved-successfully'
				),
				type: 'success',
			});

			window.location.reload();
		}
	};

	useEffect(() => {
		const makeFetch = async () => {
			const objectFieldsResponse = await API.getObjectFieldsByExternalReferenceCode(
				externalReferenceCode
			);
			const objectDefinitionResponse = await API.getObjectDefinitionById(
				objectDefinitionId
			);

			const relationshipFields = objectFieldsResponse.filter(
				(field) => field.businessType === 'Relationship'
			);

			const accountRelationshipFieldsResponse = relationshipFields.filter(
				(relationshipField) => {
					return relationshipField.objectFieldSettings?.find(
						(fieldSetting) => fieldSetting.value === 'AccountEntry'
					);
				}
			);

			if (accountRelationshipFieldsResponse.length) {
				setAccountRelationshipFields(
					accountRelationshipFieldsResponse.map(
						(accountRelationshipField) => {
							return {
								label: accountRelationshipField.label[
									defaultLanguageId
								] as string,
								value: accountRelationshipField.name,
							};
						}
					)
				);
			}

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
					label={
						(values?.label as LocalizedValue<string>)[
							defaultLanguageId
						] as string
					}
					objectDefinitionId={objectDefinitionId}
					onSubmit={onSubmit}
					portletNamespace={portletNamespace}
					screenNavigationCategoryKey="details"
					system={values.system as boolean}
				/>
			</div>

			<div className="lfr-objects__object-definition-details">
				<Sheet title={Liferay.Language.get('basic-information')}>
					<ObjectDataContainer
						DBTableName={DBTableName}
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

					<ClayPanel
						collapsable
						defaultExpanded
						displayTitle={Liferay.Language.get(
							'account-restriction'
						)}
						displayType="unstyled"
					>
						<ClayPanel.Body>
							<ClayToggle
								disabled={
									!accountRelationshipFields.length ||
									isApproved
								}
								label={Liferay.Language.get('active')}
								name="accountEntryRestricted"
								onToggle={() =>
									setValues({
										accountEntryRestricted: !values.accountEntryRestricted,
									})
								}
								toggled={values.accountEntryRestricted}
							/>

							<SingleSelect
								disabled={
									!accountRelationshipFields.length ||
									!values.accountEntryRestricted ||
									isApproved
								}
								label={Liferay.Language.get(
									'Account Restricted Field'
								)}
								onChange={({value}) => {
									setValues({
										accountEntryRestrictedObjectFieldName: value,
									});
								}}
								options={accountRelationshipFields}
								value={
									accountRelationshipFields.find(
										(relationshipField) =>
											relationshipField.value ===
											values.accountEntryRestrictedObjectFieldName
									)?.label
								}
							/>
						</ClayPanel.Body>
					</ClayPanel>

					<ConfigurationContainer
						hasUpdateObjectDefinitionPermission={
							hasUpdateObjectDefinitionPermission
						}
						isApproved={isApproved}
						setValues={setValues}
						values={values}
					/>
				</Sheet>
			</div>
		</>
	);
}
