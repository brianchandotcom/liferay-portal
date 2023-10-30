/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {SiteCompanyJSONArray} from '../ObjectDetails/EditObjectDetails';
interface CustomObjectFolderWrapperProps {
	baseResourceURL: string;
	companyJSONArray: SiteCompanyJSONArray[];
	editObjectDefinitionURL: string;
	filterOperators: TFilterOperators;
	forbiddenChars: string[];
	forbiddenLastChars: string[];
	forbiddenNames: string[];
	objectDefinitionPermissionsURL: string;
	objectDefinitionsStorageTypes: LabelValueObject[];
	objectRelationshipDeletionTypes: LabelValueObject[];
	objectWebLearnResources: ObjectWebLearnResources;
	siteJSONArray: SiteCompanyJSONArray[];
	workflowStatusJSONArray: LabelValueObject[];
}
export default function CustomObjectFolderWrapper({
	baseResourceURL,
	companyJSONArray,
	editObjectDefinitionURL,
	filterOperators,
	forbiddenChars,
	forbiddenLastChars,
	forbiddenNames,
	objectDefinitionPermissionsURL,
	objectDefinitionsStorageTypes,
	objectRelationshipDeletionTypes,
	objectWebLearnResources,
	siteJSONArray,
	workflowStatusJSONArray,
}: CustomObjectFolderWrapperProps): JSX.Element;
export {};
