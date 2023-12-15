/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {Scope} from '../ObjectDetails/EditObjectDetails';
interface CustomObjectFolderWrapperProps {
	baseResourceURL: string;
	companies: Scope[];
	editObjectDefinitionURL: string;
	filterOperators: TFilterOperators;
	forbiddenChars: string[];
	forbiddenLastChars: string[];
	forbiddenNames: string[];
	learnResourceContext: any;
	objectDefinitionPermissionsURL: string;
	objectDefinitionsStorageTypes: LabelValueObject[];
	objectRelationshipDeletionTypes: LabelValueObject[];
	objectWebLearnResources: ObjectWebLearnResources;
	sites: Scope[];
	workflowStatuses: LabelValueObject[];
}
export default function CustomObjectFolderWrapper({
	baseResourceURL,
	companies,
	editObjectDefinitionURL,
	filterOperators,
	forbiddenChars,
	forbiddenLastChars,
	forbiddenNames,
	learnResourceContext,
	objectDefinitionPermissionsURL,
	objectDefinitionsStorageTypes,
	objectRelationshipDeletionTypes,
	objectWebLearnResources,
	sites,
	workflowStatuses,
}: CustomObjectFolderWrapperProps): JSX.Element;
export {};
