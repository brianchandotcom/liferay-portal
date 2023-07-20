/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import Diagram from './Diagram/Diagram';
import Header from './Header/Header';
import LeftSidebar from './LeftSidebar/LeftSidebar';
import {RightSideBar} from './RightSidebar/index';
import {useFolderContext} from './objectFolderContext';

export default function EditObjectFolder() {
	const [{rightSidebarType}] = useFolderContext();

	return (
		<>
			<Header
				folderExternalReferenceCode="uncategorized"
				folderName="Uncategorized"
				hasDraftObjectDefinitions={false}
			/>
			<div className="lfr-objects__model-builder-diagram-container">
				<LeftSidebar />

				<Diagram />

				<RightSideBar.Root>
					{rightSidebarType === 'empty' && <RightSideBar.Empty />}
				</RightSideBar.Root>
			</div>
		</>
	);
}
