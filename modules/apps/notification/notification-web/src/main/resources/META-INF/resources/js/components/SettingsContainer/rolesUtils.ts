/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	MultiSelectItem,
	MultiSelectItemChild,
} from '@liferay/object-js-components-web';
import {createResourceURL, fetch} from 'frontend-js-web';

interface Role {
	name: string;
}

export async function getEmailNotificationRoles(baseResourceURL: string) {
	const response = await fetch(
		createResourceURL(baseResourceURL, {
			p_p_resource_id:
				'/notification_templates/get_email_notification_roles',
		}).toString()
	);

	const items = (await response.json()).accountRoles as Role[];

	const accountRolesGroup = {
		children: items.map(({name}) => {
			return {
				checked: false,
				label: name,
				value: name,
			};
		}),
		label: 'Account Roles',
		value: 'accountRolesList',
	} as MultiSelectItem;

	return [accountRolesGroup];
}

export function getCheckedChildren(
	rolesNamesList: EmailNotificationRecipients[],
	children: MultiSelectItemChild[]
) {
	const rolesNames = rolesNamesList.map(({roleName}) => roleName);

	return children.map((child) => {
		return {
			...child,
			checked: rolesNames.includes(child.value),
		};
	});
}
