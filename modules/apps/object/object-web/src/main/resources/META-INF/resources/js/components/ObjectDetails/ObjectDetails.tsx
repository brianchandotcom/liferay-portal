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
import {
	API,
	AutoComplete,
	Input,
	InputLocalized,
	SingleSelect,
	filterArrayByQuery,
	openToast,
} from '@liferay/object-js-components-web';
import React, {useEffect, useMemo, useState} from 'react';

import {defaultLanguageId} from '../../utils/constants';
import ObjectManagementToolbar from '../ObjectManagementToolbar';

import './ObjectDetails.scss';
import Sheet from './Sheet';
import {useObjectDetailsForm} from './useObjectDetailsForm';

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

type KeyValuePair = {
	key: string;
	value: string;
};

const SCOPE_OPTIONS = [
	{
		label: Liferay.Language.get('company'),
		value: 'company',
	},
	{
		label: Liferay.Language.get('site'),
		value: 'site',
	},
];

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
	const [selectedObjectField, setSelectedObjectField] = useState<
		ObjectField
	>();
	const [selectedPanelCategoryKey, setSelectedPanelCategoryKey] = useState(
		''
	);
	const [panelCategoryKeyQuery, setPanelCategoryKeyQuery] = useState('');

	const titleFieldOptions = useMemo(() => {
		return nonRelationshipObjectFieldsInfo.map(({label, name}) => {
			return {label: label[defaultLanguageId] ?? '', name};
		});
	}, [nonRelationshipObjectFieldsInfo]);

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

	const setPanelCategoryKey = (
		KeyValuePairArray: KeyValuePair[],
		panelCategoryKey: string
	) => {
		const currentPanelCategory = KeyValuePairArray.find(
			(company) => company.key === panelCategoryKey
		);

		if (currentPanelCategory) {
			setSelectedPanelCategoryKey(currentPanelCategory.value);
		}
	};

	const filteredPanelCaretogyKey = useMemo(() => {
		return filterArrayByQuery(
			values.scope === 'company' ? companyKeyValuePair : siteKeyValuePair,
			'value',
			panelCategoryKeyQuery
		) as KeyValuePair[];
	}, [
		values.scope,
		companyKeyValuePair,
		siteKeyValuePair,
		panelCategoryKeyQuery,
	]);

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

			const newObjectDefinition = {
				...objectDefinitionResponse,
			};

			if (!objectDefinitionResponse.titleObjectFieldName) {
				const idField = objectFieldsResponse.find(
					(field) => field.name === 'id'
				);

				newObjectDefinition.titleObjectFieldName = idField?.name as string;

				setSelectedObjectField(idField);
			}
			else {
				const titleObjectField = objectFieldsResponse.find(
					(objectField) =>
						objectField.name ===
						objectDefinitionResponse.titleObjectFieldName
				);

				setSelectedObjectField(titleObjectField);
			}

			if (objectDefinitionResponse.scope === 'company') {
				setPanelCategoryKey(
					companyKeyValuePair,
					objectDefinitionResponse.panelCategoryKey
				);
			}
			else {
				setPanelCategoryKey(
					siteKeyValuePair,
					objectDefinitionResponse.panelCategoryKey
				);
			}

			setValues(newObjectDefinition);
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
					<ClayPanel
						displayTitle={Liferay.Language.get(
							'object-definition-data'
						)}
						displayType="unstyled"
					>
						<ClayPanel.Body>
							<Input
								disabled={
									isApproved ||
									!hasUpdateObjectDefinitionPermission
								}
								error={errors.name}
								label={Liferay.Language.get('name')}
								name="name"
								onChange={handleChange}
								required
								value={values.name}
							/>

							<InputLocalized
								disabled={
									values.system ||
									!hasUpdateObjectDefinitionPermission
								}
								error={errors.label}
								label={Liferay.Language.get('label')}
								onChange={(label) => setValues({label})}
								required
								translations={
									values.label as LocalizedValue<string>
								}
							/>

							<InputLocalized
								disabled={
									values.system ||
									!hasUpdateObjectDefinitionPermission
								}
								error={errors.pluralLabel}
								label={Liferay.Language.get('plural-label')}
								onChange={(pluralLabel) =>
									setValues({pluralLabel})
								}
								required
								translations={
									values.pluralLabel as LocalizedValue<string>
								}
							/>

							<Input
								disabled
								label={Liferay.Language.get(
									'object-definition-table-name'
								)}
								name="name"
								value={DBTableName}
							/>

							<ClayToggle
								disabled={
									!isApproved ||
									values.system ||
									!hasUpdateObjectDefinitionPermission
								}
								label={Liferay.Language.get('active')}
								name="active"
								onToggle={() =>
									setValues({active: !values.active})
								}
								toggled={values.active}
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
							<SingleSelect<{label: string; name: string}>
								error={errors.titleObjectFieldId}
								label={Liferay.Language.get(
									'title-object-field-id'
								)}
								onChange={(target: {
									label: string;
									name: string;
								}) => {
									const field = objectFields.find(
										({name}) => name === target.name
									);

									setSelectedObjectField(field);

									setValues({
										titleObjectFieldName: field?.name,
									});
								}}
								options={titleFieldOptions}
								value={
									selectedObjectField?.label[
										defaultLanguageId
									]
								}
							/>
						</ClayPanel.Body>
					</ClayPanel>

					<ClayPanel
						collapsable
						defaultExpanded
						displayTitle={Liferay.Language.get('scope')}
						displayType="unstyled"
					>
						<ClayPanel.Body>
							<SingleSelect<LabelValueObject>
								disabled={
									isApproved ||
									!hasUpdateObjectDefinitionPermission
								}
								error={errors.titleObjectFieldId}
								label={Liferay.Language.get('scope')}
								onChange={({value}) => {
									setValues({
										panelCategoryKey: '',
										scope: value,
									});
									setSelectedPanelCategoryKey('');
								}}
								options={SCOPE_OPTIONS}
								value={
									SCOPE_OPTIONS.find(
										(scopeOption) =>
											scopeOption.value === values.scope
									)?.label
								}
							/>

							<AutoComplete
								disabled={
									values.system ||
									!hasUpdateObjectDefinitionPermission
								}
								emptyStateMessage={Liferay.Language.get(
									'no-options-were-found'
								)}
								error={errors.titleObjectFieldId}
								items={filteredPanelCaretogyKey}
								label={Liferay.Language.get('panelCategoryKey')}
								onChangeQuery={setPanelCategoryKeyQuery}
								onSelectItem={({key, value}: KeyValuePair) => {
									setValues({
										panelCategoryKey: key,
									});

									setSelectedPanelCategoryKey(value);
								}}
								query={panelCategoryKeyQuery}
								value={selectedPanelCategoryKey}
							>
								{({value}) => (
									<div className="d-flex justify-content-between">
										<div>{value}</div>
									</div>
								)}
							</AutoComplete>
						</ClayPanel.Body>
					</ClayPanel>

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

					<ClayPanel
						collapsable
						defaultExpanded
						displayTitle={Liferay.Language.get('configuration')}
						displayType="unstyled"
					>
						<ClayPanel.Body>
							<div className="lfr-objects__object-definition-details-configuration">
								<ClayToggle
									disabled={
										values.system ||
										!hasUpdateObjectDefinitionPermission
									}
									label={Liferay.Language.get('show-widget')}
									name="showWidget"
									onToggle={() =>
										setValues({portlet: !values.portlet})
									}
									toggled={values.portlet}
								/>

								<ClayToggle
									disabled={
										values.system ||
										!hasUpdateObjectDefinitionPermission
									}
									label={Liferay.Language.get(
										'enable-categorization'
									)}
									name="enableCategorization"
									onToggle={() =>
										setValues({
											enableCategorization: !values.enableCategorization,
										})
									}
									toggled={values.enableCategorization}
								/>

								<ClayToggle
									disabled={
										values.system ||
										!hasUpdateObjectDefinitionPermission
									}
									label={Liferay.Language.get(
										'enable-comments'
									)}
									name="enableComments"
									onToggle={() =>
										setValues({
											enableComments: !values.enableComments,
										})
									}
									toggled={values.enableComments}
								/>

								<ClayToggle
									disabled={values.system || isApproved}
									label={Liferay.Language.get(
										'enable-entry-history'
									)}
									name="enableEntryHistory"
									onToggle={() =>
										setValues({
											enableObjectEntryHistory: !values.enableObjectEntryHistory,
										})
									}
									toggled={values.enableObjectEntryHistory}
								/>
							</div>
						</ClayPanel.Body>
					</ClayPanel>
				</Sheet>
			</div>
		</>
	);
}
