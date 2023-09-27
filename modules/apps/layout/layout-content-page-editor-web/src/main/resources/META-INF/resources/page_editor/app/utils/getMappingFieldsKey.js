/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getMappingFieldsKey(item) {
	if (item.type?.id) {
		return `${item.type.id}-${item.subtype.id || 0}`;
	}

	return `${item.classNameId}-${
		item.classTypeId || item.classPK || item.externalReferenceCode
	}`;
}
