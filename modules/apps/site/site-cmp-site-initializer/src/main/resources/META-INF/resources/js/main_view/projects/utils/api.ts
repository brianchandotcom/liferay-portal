/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

const headers = new Headers({
	'Accept': 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json',
});

export async function patchProjectById({
	body,
	projectId,
}: {
	body: {[key: string]: any};
	projectId: string;
}): Promise<Response> {
	return await fetch(`/o/cmp/projects/${projectId}`, {
		body: JSON.stringify(body),
		headers,
		method: 'PATCH',
	});
}
