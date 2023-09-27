/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

export async function checkFriendlyURL(
	getFriendlyURLWarningURL: string,
	formData: FormData
) {
	const response = await fetch(getFriendlyURLWarningURL, {
		body: formData,
		method: 'POST',
	});

	const json = await response.json();

	if (json.hasWarnings) {
		return {
			shouldSubmit: confirm(json.warningMessage),
		};
	}

	return {
		shouldSubmit: true,
	};
}
