/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {IFDSTableProps} from '../../utils/fds';
import './ViewObjectDefinitions.scss';
interface ViewObjectDefinitionsProps extends IFDSTableProps {
	baseResourceURL: string;
	modelBuilderURL: string;
	objectFolderPermissionsURL: string;
	storages: LabelTypeObject[];
}
export declare type ViewObjectDefinitionsModals = {
	addFolder: boolean;
	addObjectDefinition: boolean;
	deleteFolder: boolean;
	deleteObjectDefinition: boolean;
	editFolder: boolean;
	moveObjectDefinition: boolean;
};
export interface DeletedObjectDefinition extends ObjectDefinition {
	hasObjectRelationship: boolean;
	objectEntriesCount: number;
}
export default function ViewObjectDefinitions({
	apiURL,
	baseResourceURL,
	id,
	items,
	modelBuilderURL,
	objectFolderPermissionsURL,
	sorting,
	storages,
	url,
}: ViewObjectDefinitionsProps): JSX.Element;
export {};
