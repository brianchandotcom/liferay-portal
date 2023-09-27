/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export interface MultiSelectItem {
	label: string;
	value: string;
}
export interface ValidatableMultiSelectItem extends MultiSelectItem {
	errorMessage?: string;
}
export interface InputGroup {
	accountRoles: ValidatableMultiSelectItem[];
	emailAddresses: ValidatableMultiSelectItem[];
	id: string;
}
