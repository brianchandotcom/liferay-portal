/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MiniCompare} from 'commerce-frontend-js';

export default function main({
	commerceChannelGroupId,
	compareProductsURL,
	items,
	itemsLimit,
	portletNamespace,
	spritemap,
}) {
	MiniCompare('mini-compare', 'mini-compare-root', {
		commerceChannelGroupId: Number(commerceChannelGroupId),
		compareProductsURL,
		items,
		itemsLimit,
		portletNamespace,
		spritemap,
	});
}
