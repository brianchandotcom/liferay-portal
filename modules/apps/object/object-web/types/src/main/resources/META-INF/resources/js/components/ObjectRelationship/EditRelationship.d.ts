/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

export declare type TDeletionType = {
	label: string;
	value: string;
};
interface EditRelationshipProps {
	baseResourceURL: string;
	deletionTypes: TDeletionType[];
	hasUpdateObjectDefinitionPermission: boolean;
	objectDefinitionExternalReferenceCode: string;
	objectRelationship: ObjectRelationship;
	parameterEndpoint: string;
	parameterRequired: boolean;
}
export default function EditRelationship({
	baseResourceURL,
	deletionTypes,
	hasUpdateObjectDefinitionPermission,
	objectDefinitionExternalReferenceCode,
	objectRelationship: initialValues,
	parameterEndpoint,
	parameterRequired,
}: EditRelationshipProps): JSX.Element;
export {};
