/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

declare type Editable = {
	classNameId?: string;
	mappedField?: string;
};
declare type MappedToRelationship = {
	classNameId: string;
	mappedField: string;
};
export default function isMappedToRelationship(
	editable: Editable | null
): editable is MappedToRelationship;
export {};
