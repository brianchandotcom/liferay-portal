/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface Editable {
	classNameId?: string;
	classPK?: string;
	externalReferenceCode?: string;
	fieldId?: string;
}

interface BaseMapped {
	classNameId: string;
	fieldId: string;
}

interface MappedWithClassPK extends BaseMapped {
	classPK: string;
}

interface MappedWithERC extends BaseMapped {
	externalReferenceCode: string;
}

type MappedEditable = MappedWithClassPK | MappedWithERC;

export default function isMappedToInfoItem(
	editable: Editable | null
): editable is MappedEditable {
	if (!editable) {
		return false;
	}

	if (typeof editable !== 'object') {
		return false;
	}

	return (
		'classNameId' in editable &&
		'fieldId' in editable &&
		('classPK' in editable || 'externalReferenceCode' in editable)
	);
}
