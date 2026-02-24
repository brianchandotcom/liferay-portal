/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const getMimeTypeProperty = ({
	map,
	mimeType,
}: {
	map: Record<string, string>;
	mimeType: string;
}): string => {
	if (!mimeType) {
		return map['default'];
	}

	if (map[mimeType]) {
		return map[mimeType];
	}

	const prefix = mimeType.split('/')[0];
	if (map[prefix]) {
		return map[prefix];
	}

	return map['default'];
};

export default getMimeTypeProperty;
