/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {ReactFlowProvider} from 'react-flow-renderer';

import {KeyValuePair} from '../ObjectDetails/EditObjectDetails';
import {TDeletionType} from '../ObjectRelationship/EditRelationship';
import EditObjectFolder from './EditObjectFolder';
import {FolderContextProvider} from './ModelBuilderContext/objectFolderContext';

interface ICustomFolderWrapperProps extends React.HTMLAttributes<HTMLElement> {
	companyKeyValuePair: KeyValuePair[];
	deletionTypes: TDeletionType[];
	objectDefinitions: ObjectDefinition[];
	siteKeyValuePair: KeyValuePair[];
}

const CustomFolderWrapper: React.FC<ICustomFolderWrapperProps> = ({
	companyKeyValuePair,
	deletionTypes,
	siteKeyValuePair,
}) => {
	return (
		<ReactFlowProvider>
			<FolderContextProvider value={{selectedFolderERC: 'uncategorized'}}>
				<EditObjectFolder
					companyKeyValuePair={companyKeyValuePair}
					deletionTypes={deletionTypes}
					siteKeyValuePair={siteKeyValuePair}
				/>
			</FolderContextProvider>
		</ReactFlowProvider>
	);
};

export default CustomFolderWrapper;
