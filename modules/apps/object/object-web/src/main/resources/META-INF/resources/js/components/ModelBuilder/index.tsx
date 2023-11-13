/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {ReactFlowProvider} from 'react-flow-renderer';

import {Scope} from '../ObjectDetails/EditObjectDetails';
import EditObjectFolder from './EditObjectFolder';
import {ObjectFolderContextProvider} from './ModelBuilderContext/objectFolderContext';

interface CustomObjectFolderWrapperProps {
	baseResourceURL: string;
	companies: Scope[];
	editObjectDefinitionURL: string;
	filterOperators: TFilterOperators;
	forbiddenChars: string[];
	forbiddenLastChars: string[];
	forbiddenNames: string[];
	objectDefinitionPermissionsURL: string;
	objectDefinitionsStorageTypes: LabelValueObject[];
	objectRelationshipDeletionTypes: LabelValueObject[];
	objectWebLearnResources: ObjectWebLearnResources;
	sites: Scope[];
	workflowStatuses: LabelValueObject[];
}

export default function CustomObjectFolderWrapper({
	baseResourceURL,
	companies,
	editObjectDefinitionURL,
	filterOperators,
	forbiddenChars,
	forbiddenLastChars,
	forbiddenNames,
	objectDefinitionPermissionsURL,
	objectDefinitionsStorageTypes,
	objectRelationshipDeletionTypes,
	objectWebLearnResources,
	sites,
	workflowStatuses,
}: CustomObjectFolderWrapperProps) {
	return (
		<ReactFlowProvider>
			<ObjectFolderContextProvider
				value={{
					baseResourceURL,
					editObjectDefinitionURL,
					filterOperators,
					forbiddenChars,
					forbiddenLastChars,
					forbiddenNames,
					objectDefinitionPermissionsURL,
					objectDefinitionsStorageTypes,
					objectWebLearnResources,
					workflowStatuses,
				}}
			>
				<EditObjectFolder
					companies={companies}
					objectRelationshipDeletionTypes={
						objectRelationshipDeletionTypes
					}
					sites={sites}
				/>
			</ObjectFolderContextProvider>
		</ReactFlowProvider>
	);
}
