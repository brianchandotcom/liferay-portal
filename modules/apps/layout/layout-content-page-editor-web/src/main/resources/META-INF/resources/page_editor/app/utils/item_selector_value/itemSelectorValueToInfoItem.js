/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function itemSelectorValueToInfoItem(infoItem) {
	const {
		className,
		classNameId,
		classPK,
		classTypeId,
		externalReferenceCode,
		returnType,
		subtype,
		title,
		type,
	} = infoItem;

	return {
		className,
		classNameId,
		classPK,
		classTypeId,
		externalReferenceCode,
		itemSubtype: subtype,
		itemType: type,
		title,
		type: returnType,
	};
}
