/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DateTimeRenderer} from '@liferay/frontend-data-set-web';

export default function ObjectDefinitionModifiedDateDataRenderer({
	itemData,
}: {
	itemData: ObjectDefinition;
}) {
	return DateTimeRenderer({
		options: {
			format: {
				day: 'numeric',
				hour: 'numeric',
				minute: 'numeric',
				month: 'short',
				second: 'numeric',
				timeZone: 'UTC',
				year: 'numeric',
			},
		},
		value: String(itemData.dateModified),
	});
}
