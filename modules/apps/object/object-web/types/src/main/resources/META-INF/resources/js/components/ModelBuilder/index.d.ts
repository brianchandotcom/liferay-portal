/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {KeyValuePair} from '../ObjectDetails/EditObjectDetails';
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
}: CustomObjectFolderWrapperProps): JSX.Element;
export {};
