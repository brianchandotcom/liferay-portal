/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getDataSetSnapshotResourceURL({
	params,
	snapshotERC,
}: {
	params?: Record<string, string>;
	snapshotERC?: string;
} = {}): string {
	const apiURL = ['/o/data-set-admin/snapshots'];

	if (snapshotERC) {
		apiURL.push(`/by-external-reference-code/${snapshotERC}`);
	}

	if (params) {
		apiURL.push(`?${new URLSearchParams(params).toString()}`);
	}

	return apiURL.join('');
}
