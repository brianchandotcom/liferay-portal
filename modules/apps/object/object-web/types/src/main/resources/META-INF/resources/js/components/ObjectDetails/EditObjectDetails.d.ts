/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './ObjectDetails.scss';
export declare type SiteCompanyJSONArray = {
	items: LabelValueObject[];
	label: string;
};
interface EditObjectDetailsProps {
	backURL: string;
	companyJSONArray: SiteCompanyJSONArray[];
	dbTableName: string;
	externalReferenceCode: string;
	hasPublishObjectPermission: boolean;
	hasUpdateObjectDefinitionPermission: boolean;
	isApproved: boolean;
	isRootDescendantNode: boolean;
	label: LocalizedValue<string>;
	nonRelationshipObjectFieldsInfo: {
		label: LocalizedValue<string>;
		name: string;
	}[];
	objectDefinitionId: number;
	pluralLabel: LocalizedValue<string>;
	portletNamespace: string;
	shortName: string;
	siteJSONArray: SiteCompanyJSONArray[];
	storageTypes: LabelValueObject[];
}
export default function EditObjectDetails({
	backURL,
	companyJSONArray,
	dbTableName,
	externalReferenceCode,
	hasPublishObjectPermission,
	hasUpdateObjectDefinitionPermission,
	isApproved,
	isRootDescendantNode,
	label,
	nonRelationshipObjectFieldsInfo,
	objectDefinitionId,
	pluralLabel,
	portletNamespace,
	shortName,
	siteJSONArray,
	storageTypes,
}: EditObjectDetailsProps): JSX.Element;
export {};
