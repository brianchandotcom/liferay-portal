/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface EditRelationshipProps {
	baseResourceURL: string;
	deletionTypes: LabelValueObject[];
	hasUpdateObjectDefinitionPermission: boolean;
	objectDefinitionExternalReferenceCode: string;
	objectRelationship: ObjectRelationship;
	parameterRequired: boolean;
	restContextPath: string;
}
export default function EditRelationship({
	baseResourceURL,
	deletionTypes,
	hasUpdateObjectDefinitionPermission,
	objectDefinitionExternalReferenceCode,
	objectRelationship: initialValues,
	parameterRequired,
	restContextPath,
}: EditRelationshipProps): JSX.Element;
export {};
