/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {IFDSTableProps} from '../../utils/fds';
import './ViewObjectDefinitions.scss';
interface ViewObjectDefinitionsProps extends IFDSTableProps {
	baseResourceURL: string;
	editObjectDefinitionURL: string;
	modelBuilderURL: string;
	objectDefinitionsAPIURL: any;
	objectDefinitionsCreationMenu: {
		primaryItems?: any[];
		secondaryItems?: any[];
	};
	objectDefinitionsFDSActionDropdownItems: any[];
	objectDefinitionsFDSName: any;
	objectDefinitionsStorageTypes: LabelValueObject[];
	objectFolderPermissionsURL: string;
}
export interface DeletedObjectDefinition {
	hasObjectRelationship: boolean;
	id: number;
	name: string;
	objectEntriesCount: number;
}
export default function ViewObjectDefinitions({
	baseResourceURL,
	editObjectDefinitionURL,
	modelBuilderURL,
	objectDefinitionsAPIURL,
	objectDefinitionsCreationMenu,
	objectDefinitionsFDSActionDropdownItems,
	objectDefinitionsFDSName,
	objectDefinitionsStorageTypes,
	objectFolderPermissionsURL,
}: ViewObjectDefinitionsProps): JSX.Element;
export {};
