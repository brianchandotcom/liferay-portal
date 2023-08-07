/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getLocalizableLabel} from '@liferay/object-js-components-web';
import {sub} from 'frontend-js-web';
import React from 'react';
import {Node, useStore} from 'react-flow-renderer';

import {AccountRestrictionContainer} from '../../ObjectDetails/AccountRestrictionContainer';
import {ConfigurationContainer} from '../../ObjectDetails/ConfigurationContainer';
import {KeyValuePair} from '../../ObjectDetails/EditObjectDetails';
import {EntryDisplayContainer} from '../../ObjectDetails/EntryDisplayContainer';
import {ObjectDataContainer} from '../../ObjectDetails/ObjectDataContainer';
import {ScopeContainer} from '../../ObjectDetails/ScopeContainer';
import {ObjectDefinitionNodeData} from '../types';

import './RightSidebarObjectDefinitionDetails.scss';

interface RightSidebarObjectDefinitionDetailsProps {
	companyKeyValuePair: KeyValuePair[];
	siteKeyValuePair: KeyValuePair[];
}
export function RightSidebarObjectDefinitionDetails({
	companyKeyValuePair,
	siteKeyValuePair,
}: RightSidebarObjectDefinitionDetailsProps) {
	const store = useStore();
	const {nodes} = store.getState();

	const selectedNode = nodes.find(
		(objectDefinitionNode: Node<ObjectDefinitionNodeData>) => {
			return objectDefinitionNode.data?.nodeSelected;
		}
	);

	return (
		<>
			<div className="lfr-objects__model-builder-right-sidebar-definition-node-title">
				<span>
					{sub(
						Liferay.Language.get('x-details'),
						getLocalizableLabel(
							selectedNode?.data
								.defaultLanguageId as Liferay.Language.Locale,
							selectedNode?.data.label as LocalizedValue<string>,
							selectedNode?.data.name
						)
					)}
				</span>
			</div>
			<div className="lfr-objects__model-builder-right-sidebar-definition-node-content">
				<ObjectDataContainer
					dbTableName={selectedNode?.data.dbTableName as string}
					errors={{}}
					handleChange={() => {}}
					hasUpdateObjectDefinitionPermission={true}
					isApproved={selectedNode?.data.status?.label === 'approved'}
					setValues={() => {}}
					values={selectedNode?.data as ObjectDefinition}
				/>
			</div>

			<div className="lfr-objects__model-builder-right-sidebar-definition-node-content">
				<EntryDisplayContainer
					errors={{}}
					nonRelationshipObjectFieldsInfo={[]}
					objectFields={
						selectedNode?.data.objectFields as ObjectField[]
					}
					setValues={() => {}}
					values={selectedNode?.data as ObjectDefinition}
				/>

				<ScopeContainer
					companyKeyValuePair={companyKeyValuePair}
					errors={{}}
					hasUpdateObjectDefinitionPermission={true}
					isApproved={selectedNode?.data.status?.label === 'approved'}
					setValues={() => {}}
					siteKeyValuePair={siteKeyValuePair}
					values={selectedNode?.data as ObjectDefinition}
				/>
			</div>

			{(Liferay.FeatureFlags['LPS-167253']
				? selectedNode?.data.modifiable
				: !selectedNode?.data.system) && (
				<div className="lfr-objects__model-builder-right-sidebar-definition-node-content">
					<AccountRestrictionContainer
						errors={{}}
						isApproved={
							selectedNode?.data.status?.label === 'approved'
						}
						objectFields={
							selectedNode?.data.objectFields as ObjectField[]
						}
						setValues={() => {}}
						values={selectedNode?.data as ObjectDefinition}
					/>
				</div>
			)}

			<div className="lfr-objects__model-builder-right-sidebar-definition-node-content">
				<ConfigurationContainer
					hasUpdateObjectDefinitionPermission={true}
					setValues={() => {}}
					values={selectedNode?.data as ObjectDefinition}
				/>
			</div>
		</>
	);
}
