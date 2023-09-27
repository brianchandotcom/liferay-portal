/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function formDataToObj(formData) {
	return Array.from(formData.entries()).reduce(
		(accumulator, [key, value]) => {
			accumulator[key] = value;

			return accumulator;
		},
		{}
	);
}
