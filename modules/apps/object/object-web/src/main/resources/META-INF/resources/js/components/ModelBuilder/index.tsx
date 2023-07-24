/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {KeyValuePair} from '../ObjectDetails/EditObjectDetails';
import EditObjectFolder from './EditObjectFolder';
import {FolderContextProvider} from './objectFolderContext';

interface ICustomFolderWrapperProps extends React.HTMLAttributes<HTMLElement> {
	objectDefinitions: ObjectDefinition[];
	companyKeyValuePair: KeyValuePair[];
	siteKeyValuePair: KeyValuePair[];
}

const CustomFolderWrapper: React.FC<ICustomFolderWrapperProps> = ({
	companyKeyValuePair,
	objectDefinitions,
	siteKeyValuePair,
}) => {
	return (
		<FolderContextProvider value={{objectDefinitions}}>
			<EditObjectFolder
				companyKeyValuePair={companyKeyValuePair}
				siteKeyValuePair={siteKeyValuePair}
			/>
		</FolderContextProvider>
	);
};

export default CustomFolderWrapper;
