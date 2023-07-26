/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getLocalizableLabel} from '@liferay/object-js-components-web';
import {sub} from 'frontend-js-web';
import React from 'react';

import {useFolderContext} from '../objectFolderContext';

import './RightSidebarObjectDefinitionDetails.scss';
import {AccountRestrictionContainer} from '../../ObjectDetails/AccountRestrictionContainer';
import {ConfigurationContainer} from '../../ObjectDetails/ConfigurationContainer';
import {KeyValuePair} from '../../ObjectDetails/EditObjectDetails';
import {EntryDisplayContainer} from '../../ObjectDetails/EntryDisplayContainer';
import {ObjectDataContainer} from '../../ObjectDetails/ObjectDataContainer';
import {ScopeContainer} from '../../ObjectDetails/ScopeContainer';

interface RightSidebarObjectDefinitionDetailsProps {
	companyKeyValuePair: KeyValuePair[];
	siteKeyValuePair: KeyValuePair[];
}
export function RightSidebarObjectDefinitionDetails({
	companyKeyValuePair,
	siteKeyValuePair,
}: RightSidebarObjectDefinitionDetailsProps) {
	const [{selectedDefinitionNode}] = useFolderContext();

	return (
		<>
			<div className="lfr-objects__model-builder-right-sidebar-definition-node-title">
				<span>
					{sub(
						Liferay.Language.get('x-details'),
						getLocalizableLabel(
							selectedDefinitionNode.defaultLanguageId,
							selectedDefinitionNode.label,
							selectedDefinitionNode.name
						)
					)}
				</span>
			</div>
			<div className="lfr-objects__model-builder-right-sidebar-definition-node-content">
				<ObjectDataContainer
					dbTableName={selectedDefinitionNode.dbTableName as string}
					errors={{}}
					handleChange={() => {}}
					hasUpdateObjectDefinitionPermission={true}
					isApproved={
						selectedDefinitionNode.status.label === 'approved'
					}
					setValues={() => {}}
					values={selectedDefinitionNode}
				/>
			</div>

			<div className="lfr-objects__model-builder-right-sidebar-definition-node-content">
				<EntryDisplayContainer
					errors={{}}
					nonRelationshipObjectFieldsInfo={[]}
					objectFields={selectedDefinitionNode.objectFields}
					setValues={() => {}}
					values={selectedDefinitionNode}
				/>

				<ScopeContainer
					companyKeyValuePair={companyKeyValuePair}
					errors={{}}
					hasUpdateObjectDefinitionPermission={true}
					isApproved={
						selectedDefinitionNode.status.label === 'approved'
					}
					setValues={() => {}}
					siteKeyValuePair={siteKeyValuePair}
					values={selectedDefinitionNode}
				/>
			</div>

			{(Liferay.FeatureFlags['LPS-167253']
				? selectedDefinitionNode.modifiable
				: !selectedDefinitionNode.system) && (
				<div className="lfr-objects__model-builder-right-sidebar-definition-node-content">
					<AccountRestrictionContainer
						errors={{}}
						isApproved={
							selectedDefinitionNode.status.label === 'approved'
						}
						objectFields={selectedDefinitionNode.objectFields}
						setValues={() => {}}
						values={selectedDefinitionNode}
					/>
				</div>
			)}

			<div className="lfr-objects__model-builder-right-sidebar-definition-node-content">
				<ConfigurationContainer
					hasUpdateObjectDefinitionPermission={true}
					setValues={() => {}}
					values={selectedDefinitionNode}
				/>
			</div>
		</>
	);
}
