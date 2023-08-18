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
	baseResourceURL: string;
	companyKeyValuePair: KeyValuePair[];
	deletionTypes: TDeletionType[];
	editObjectDefinitionURL: string;
	objectDefinitionPermissionsURL: string;
	siteKeyValuePair: KeyValuePair[];
	storages: LabelValueObject[];
	viewApiURL: string;
}

const CustomFolderWrapper: React.FC<ICustomFolderWrapperProps> = ({
	baseResourceURL,
	companyKeyValuePair,
	deletionTypes,
	editObjectDefinitionURL,
	objectDefinitionPermissionsURL,
	siteKeyValuePair,
	storages,
	viewApiURL,
}) => {
	const urlParams = new URLSearchParams(window.location.search);
	const folderERC = urlParams.get('folderERC');

	return (
		<ReactFlowProvider>
			<FolderContextProvider
				value={{
					baseResourceURL,
					editObjectDefinitionURL,
					objectDefinitionPermissionsURL,
					selectedFolderERC: folderERC,
					storages,
					viewApiURL,
				}}
			>
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
