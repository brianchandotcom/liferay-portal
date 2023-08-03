/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API, getLocalizableLabel} from '@liferay/object-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';
import {Node, isNode} from 'react-flow-renderer';

import {AccountRestrictionContainer} from '../../ObjectDetails/AccountRestrictionContainer';
import {ConfigurationContainer} from '../../ObjectDetails/ConfigurationContainer';
import {KeyValuePair} from '../../ObjectDetails/EditObjectDetails';
import {EntryDisplayContainer} from '../../ObjectDetails/EntryDisplayContainer';
import {ObjectDataContainer} from '../../ObjectDetails/ObjectDataContainer';
import {ScopeContainer} from '../../ObjectDetails/ScopeContainer';
import {
	ObjectDefinitionNodeData,
	nonRelationshipObjectFieldsInfo,
} from '../types';

import './RightSidebarObjectDefinitionDetails.scss';
import {useObjectDetailsForm} from '../../ObjectDetails/useObjectDetailsForm';
import {useFolderContext} from '../ModelBuilderContext/objectFolderContext';
interface RightSidebarObjectDefinitionDetailsProps {
	companyKeyValuePair: KeyValuePair[];
	siteKeyValuePair: KeyValuePair[];
}
export function RightSidebarObjectDefinitionDetails({
	companyKeyValuePair,
	siteKeyValuePair,
}: RightSidebarObjectDefinitionDetailsProps) {
	const [{elements}] = useFolderContext();

	const selectedNode = elements.find((element) => {
		if (isNode(element)) {
			return (element as Node<ObjectDefinitionNodeData>).data
				?.nodeSelected;
		}
	}) as Node<ObjectDefinitionNodeData>;

	const [
		nonRelationshipObjectFieldsInfo,
		setNonRelationshipObjectFieldsInfo,
	] = useState<nonRelationshipObjectFieldsInfo[]>();

	const {errors, handleChange, setValues, values} = useObjectDetailsForm({
		initialValues: {
			defaultLanguageId: 'en_US',
			externalReferenceCode: '',
			id: 0,
			label: {},
			name: '',
			pluralLabel: {},
		},
		onSubmit: () => {},
	});

	useEffect(() => {
		const makeFetch = async () => {
			if (selectedNode) {
				const selectedObjectDefinitionResponse = await API.getObjectDefinitionByExternalReferenceCode(
					selectedNode.data?.externalReferenceCode as string
				);

				const newNonRelationshipObjectFieldsInfo = selectedObjectDefinitionResponse.objectFields.map(
					(objectField) => {
						if (objectField.businessType !== 'Relationship') {
							return {
								label: objectField.label,
								name: objectField.name,
							};
						}
					}
				) as nonRelationshipObjectFieldsInfo[];

				setNonRelationshipObjectFieldsInfo(
					newNonRelationshipObjectFieldsInfo
				);
				setValues(selectedObjectDefinitionResponse);
			}
		};

		makeFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedNode]);

	return (
		<>
			<div className="lfr-objects__model-builder-right-sidebar-definition-node-title">
				<span>
					{sub(
						Liferay.Language.get('x-details'),
						getLocalizableLabel(
							values.defaultLanguageId as Liferay.Language.Locale,
							values?.label,
							values?.name
						)
					)}
				</span>
			</div>
			<div className="lfr-objects__model-builder-right-sidebar-definition-node-content">
				<ObjectDataContainer
					dbTableName=""
					errors={errors}
					handleChange={handleChange}
					hasUpdateObjectDefinitionPermission={
						!!values.actions?.update
					}
					isApproved={values.status?.label === 'approved'}
					setValues={setValues}
					values={values as ObjectDefinition}
				/>
			</div>

			<div className="lfr-objects__model-builder-right-sidebar-definition-node-content">
				<EntryDisplayContainer
					errors={errors}
					nonRelationshipObjectFieldsInfo={
						nonRelationshipObjectFieldsInfo ?? []
					}
					objectFields={values.objectFields ?? []}
					setValues={setValues}
					values={values as ObjectDefinition}
				/>

				<ScopeContainer
					companyKeyValuePair={companyKeyValuePair}
					errors={errors}
					hasUpdateObjectDefinitionPermission={true}
					isApproved={values.status?.label === 'approved'}
					setValues={setValues}
					siteKeyValuePair={siteKeyValuePair}
					values={values as ObjectDefinition}
				/>
			</div>

			{(Liferay.FeatureFlags['LPS-167253']
				? values?.modifiable
				: !values?.system) && (
				<div className="lfr-objects__model-builder-right-sidebar-definition-node-content">
					<AccountRestrictionContainer
						errors={errors}
						isApproved={values?.status?.label === 'approved'}
						objectFields={
							(values?.objectFields as ObjectField[]) ?? []
						}
						setValues={setValues}
						values={values as ObjectDefinition}
					/>
				</div>
			)}

			<div className="lfr-objects__model-builder-right-sidebar-definition-node-content">
				<ConfigurationContainer
					hasUpdateObjectDefinitionPermission={
						!!values.actions?.update
					}
					setValues={setValues}
					values={values as ObjectDefinition}
				/>
			</div>
		</>
	);
}
