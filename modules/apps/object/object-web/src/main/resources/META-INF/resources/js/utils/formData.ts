/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type FormDataJSONFormat = {
	[key: string]: Blob | string;
};

export function jsonToFormData(json: FormDataJSONFormat) {
	const formData = new FormData();
	for (const key in json) {
		formData.append(key, json[key]);
	}

	return formData;
}
