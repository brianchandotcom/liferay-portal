/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {IFDSTableProps} from '../../utils/fds';
import './ViewObjectDefinitions.scss';
export interface ModalImportProperties {
	JSONInputId: string;
	apiURL: string;
	importExtendedInfo?: {
		key: string;
		value: string;
	};
	importURL: string;
	modalImportKey: string;
}
interface ViewObjectDefinitionsProps extends IFDSTableProps {
	baseResourceURL: string;
	editObjectDefinitionURL: string;
	importObjectDefinitionURL: string;
	importObjectFolderURL: string;
	learnResourceContext: any;
	modelBuilderURL: string;
	nameMaxLength: string;
	objectDefinitionsCreationMenu: {
		primaryItems?: any[];
		secondaryItems?: any[];
	};
	objectDefinitionsFDSActionDropdownItems: any[];
	objectDefinitionsFDSName: any;
	objectDefinitionsStorageTypes: LabelValueObject[];
	objectFolderPermissionsURL: string;
	portletNamespace: string;
}
export default function ViewObjectDefinitions({
	baseResourceURL,
	editObjectDefinitionURL,
	importObjectDefinitionURL,
	importObjectFolderURL,
	learnResourceContext,
	modelBuilderURL,
	nameMaxLength,
	objectDefinitionsCreationMenu,
	objectDefinitionsFDSActionDropdownItems,
	objectDefinitionsFDSName,
	objectDefinitionsStorageTypes,
	objectFolderPermissionsURL,
	portletNamespace,
}: ViewObjectDefinitionsProps): JSX.Element;
export {};
