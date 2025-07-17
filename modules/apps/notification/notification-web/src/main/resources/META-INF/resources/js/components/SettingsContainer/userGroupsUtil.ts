/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MultiSelectItem} from '@liferay/object-js-components-web';
import {createResourceURL, fetch} from 'frontend-js-web';

const userGroupLabel = Liferay.Language.get('user-groups');

export async function getEmailNotificationUserGroups(baseResourceURL: string) {
	const response = await fetch(
		createResourceURL(baseResourceURL, {
			p_p_resource_id:
				'/notification_templates/get_email_notification_user_groups',
		}).toString()
	);

	const userGroupsResponse = await response.json();
	const userGroups = [] as MultiSelectItem[];

	userGroups.push({
		children: (Object.values(userGroupsResponse) as string[]).map(
			(name: string) => ({
				checked: false,
				label: name,
				value: name,
			})
		),
		label: userGroupLabel,
		value: 'user-groups',
	});

	return userGroups;
}
