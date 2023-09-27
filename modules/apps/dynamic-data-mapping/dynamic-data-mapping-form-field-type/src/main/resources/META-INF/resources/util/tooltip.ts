/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getTooltipTitle({
	placeholder,
	value,
}: {
	placeholder: string;
	value: string;
}) {
	let tooltipText: {title: string} | string = '';

	if (value !== '') {
		tooltipText = {title: value};
	}
	else if (placeholder !== '') {
		tooltipText = {title: placeholder};
	}

	return tooltipText;
}
