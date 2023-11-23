/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './ObjectDetails.scss';
export declare type Scope = {
	items: LabelValueObject[];
	label: string;
};
interface EditObjectDetailsProps {
	backURL: string;
	companies: Scope[];
	dbTableName: string;
	hasPublishObjectPermission: boolean;
	hasUpdateObjectDefinitionPermission: boolean;
	isApproved: boolean;
	isRootDescendantNode: boolean;
	label: LocalizedValue<string>;
	nonRelationshipObjectFieldsInfo: {
		label: LocalizedValue<string>;
		name: string;
	}[];
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionId: number;
	pluralLabel: LocalizedValue<string>;
	portletNamespace: string;
	shortName: string;
	sites: Scope[];
	storageTypes: LabelValueObject[];
}
export default function EditObjectDetails({
	backURL,
	companies,
	dbTableName,
	hasPublishObjectPermission,
	hasUpdateObjectDefinitionPermission,
	isApproved,
	isRootDescendantNode,
	label,
	nonRelationshipObjectFieldsInfo,
	objectDefinitionExternalReferenceCode,
	objectDefinitionId,
	pluralLabel,
	portletNamespace,
	shortName,
	sites,
	storageTypes,
}: EditObjectDetailsProps): JSX.Element;
export {};
