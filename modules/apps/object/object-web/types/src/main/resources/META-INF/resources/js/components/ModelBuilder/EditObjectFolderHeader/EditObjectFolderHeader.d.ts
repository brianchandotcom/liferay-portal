/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './EditObjectFolderHeader.scss';
interface EditObjectFolderHeaderProps {
	hasDraftObjectDefinitions: boolean;
	selectedObjectFolder: ObjectFolder;
}
export default function EditObjectFolderHeader({
	hasDraftObjectDefinitions,
	selectedObjectFolder,
}: EditObjectFolderHeaderProps): JSX.Element;
export {};
