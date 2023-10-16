/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface EditObjectRelationshipProps {
	baseResourceURL: string;
	hasUpdateObjectDefinitionPermission: boolean;
	objectDefinitionExternalReferenceCode: string;
	objectRelationship: ObjectRelationship;
	objectRelationshipDeletionTypes: LabelValueObject[];
	parameterRequired: boolean;
	restContextPath: string;
}
export default function EditObjectRelationship({
	baseResourceURL,
	hasUpdateObjectDefinitionPermission,
	objectDefinitionExternalReferenceCode,
	objectRelationship: initialValues,
	objectRelationshipDeletionTypes,
	parameterRequired,
	restContextPath,
}: EditObjectRelationshipProps): JSX.Element;
export {};
