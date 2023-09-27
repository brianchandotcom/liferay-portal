/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function createAutoCorrectedNumberPipe(max: number, min: number) {
	return function (conformedValue: string) {
		const textValue = conformedValue.replace(/\D/g, '');
		const value = parseInt(textValue, 10);

		return textValue.length && (value > max || value < min)
			? false
			: {value: textValue};
	};
}
