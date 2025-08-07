/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	MultiSelectItem,
	MultiSelectItemChild,
} from '@liferay/object-js-components-web';

export function getCheckedChildren(
	namesList: EmailNotificationRecipients[],
	children: MultiSelectItemChild[],
	type: EmailNotificationRecipientTypeOptions
) {
	const names = namesList.map((name) => name[type]) as string[];

	return children.map((child) => {
		return {
			...child,
			checked: names.includes(child.value),
		};
	});
}

export function handleMultiSelectItemsChange(
	itemsGroup: MultiSelectItem[],
	type: EmailNotificationRecipientTypeOptions
) {
	const newRecipients: EmailNotificationRecipients[] = [];

	if (itemsGroup.length) {
		itemsGroup.forEach((itemGroup) => {
			itemGroup.children.forEach((child) => {
				if (child.checked) {
					newRecipients.push({[type]: child.value});
				}
			});
		});
	}

	return newRecipients;
}

export function uncheckMultiSelectItemChildrens(items: MultiSelectItem[]) {
	return items.map((item) => {
		return {
			...item,
			children: item.children.map((child) => {
				return {
					...child,
					checked: false,
				};
			}),
		};
	});
}
