/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getFragmentItem(layoutData, fragmentEntryLinkId) {
	if (!layoutData || !fragmentEntryLinkId) {
		return null;
	}

	return Object.values(layoutData.items).find(
		(item) => item.config.fragmentEntryLinkId === fragmentEntryLinkId
	);
}
