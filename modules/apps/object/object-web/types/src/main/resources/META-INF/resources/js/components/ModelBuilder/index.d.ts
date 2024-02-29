/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {ILearnResourceContext} from 'frontend-js-components-web';
import {Scope} from '../ObjectDetails/EditObjectDetails';
interface CustomObjectFolderWrapperProps {
	baseResourceURL: string;
	companies: Scope[];
	editObjectDefinitionURL: string;
	filterOperators: TFilterOperators;
	forbiddenChars: string[];
	forbiddenLastChars: string[];
	forbiddenNames: string[];
	learnResourceContext: ILearnResourceContext;
	objectDefinitionPermissionsURL: string;
	objectDefinitionsStorageTypes: LabelValueObject[];
	objectRelationshipDeletionTypes: LabelValueObject[];
	sites: Scope[];
	viewObjectDefinitionsURL: string;
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
	sites,
	viewObjectDefinitionsURL,
	workflowStatuses,
}: CustomObjectFolderWrapperProps): JSX.Element;
export {};
