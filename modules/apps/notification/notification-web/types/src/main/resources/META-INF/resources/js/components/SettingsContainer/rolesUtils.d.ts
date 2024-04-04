/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	MultiSelectItem,
	MultiSelectItemChild,
} from '@liferay/object-js-components-web';
export declare function getRoles(
	baseResourceURL: string
): Promise<MultiSelectItem[]>;
export declare function getCheckedChildren(
	rolesNamesList: EmailNotificationRecipients[],
	children: MultiSelectItemChild[]
): {
	checked: boolean;
	label: string;
	value: string;
}[];
