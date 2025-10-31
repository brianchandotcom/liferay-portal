/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Tag} from '../types/Tag';
import ApiHelper from './ApiHelper';

async function createTag({
	assetLibraryId,
	cmsGroupId,
	name,
}: {
	assetLibraryId: number | string | null | undefined;
	cmsGroupId: number | string;
	name: string;
}) {
	let requestBody;

	if (assetLibraryId === null || assetLibraryId === undefined) {
		requestBody = {name};
	}
	else {
		requestBody = {
			assetLibraries: [{id: assetLibraryId}],
			name,
		};
	}

	return await ApiHelper.post<Tag>(
		`/o/headless-admin-taxonomy/v1.0/sites/${cmsGroupId}/keywords`,
		requestBody
	);
}

export default {
	createTag,
};
