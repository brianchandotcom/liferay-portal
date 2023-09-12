/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {SidebarCategory} from '@liferay/object-js-components-web';
import './EditObjectField.scss';
export interface EditObjectFieldProps {
	baseResourceURL: string;
	creationLanguageId: Liferay.Language.Locale;
	filterOperators: TFilterOperators;
	forbiddenChars: string[];
	forbiddenLastChars: string[];
	forbiddenNames: string[];
	isApproved: boolean;
	isDefaultStorageType: boolean;
	learnResources: ObjectWebLearnResources;
	objectDefinitionExternalReferenceCode: string;
	objectFieldId: number;
	objectName: string;
	objectRelationshipId: number;
	readOnly: boolean;
	readOnlySidebarElements: SidebarCategory[];
	sidebarElements: SidebarCategory[];
	workflowStatusJSONArray: LabelValueObject[];
}
export declare const objectFieldInitialValues: Partial<ObjectField>;
export default function EditObjectField({
	baseResourceURL,
	creationLanguageId,
	filterOperators,
	forbiddenChars,
	forbiddenLastChars,
	forbiddenNames,
	isApproved,
	isDefaultStorageType,
	learnResources,
	objectDefinitionExternalReferenceCode,
	objectFieldId,
	objectName,
	objectRelationshipId,
	readOnly,
	readOnlySidebarElements,
	sidebarElements,
	workflowStatusJSONArray,
}: EditObjectFieldProps): JSX.Element;
