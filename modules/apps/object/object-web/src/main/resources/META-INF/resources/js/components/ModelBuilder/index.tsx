/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {ReactFlowProvider} from 'react-flow-renderer';

import {KeyValuePair} from '../ObjectDetails/EditObjectDetails';
import EditObjectFolder from './EditObjectFolder';
import {ObjectFolderContextProvider} from './ModelBuilderContext/objectFolderContext';

interface CustomObjectFolderWrapperProps {
	baseResourceURL: string;
	companyKeyValuePairs: KeyValuePair[];
	editObjectDefinitionURL: string;
	objectDefinitionPermissionsURL: string;
	objectDefinitionsStorageTypes: LabelValueObject[];
	objectRelationshipDeletionTypes: LabelValueObject[];
	siteKeyValuePairs: KeyValuePair[];
}

export default function CustomObjectFolderWrapper({
	baseResourceURL,
	companyKeyValuePairs,
	editObjectDefinitionURL,
	objectDefinitionPermissionsURL,
	objectDefinitionsStorageTypes,
	objectRelationshipDeletionTypes,
	siteKeyValuePairs,
}: CustomObjectFolderWrapperProps) {
	return (
		<ReactFlowProvider>
			<ObjectFolderContextProvider
				value={{
					baseResourceURL,
					editObjectDefinitionURL,
					objectDefinitionPermissionsURL,
					objectDefinitionsStorageTypes,
				}}
			>
				<EditObjectFolder
					companyKeyValuePairs={companyKeyValuePairs}
					objectRelationshipDeletionTypes={
						objectRelationshipDeletionTypes
					}
					siteKeyValuePairs={siteKeyValuePairs}
				/>
			</ObjectFolderContextProvider>
		</ReactFlowProvider>
	);
}
