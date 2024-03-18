/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getLabelDisplay(value) {
	let label = {...value};

	if ('key' in label && 'name' in label) {
		label = {
			label: value.key,
			label_i18n: value.name,
		};
	}

	switch (label.label) {
		case 'approved':
		case 'complete':
			label.displayType = 'success';
			break;
		case 'denied':
			label.displayType = 'danger';
			break;
		case 'expired':
			label.displayType = 'warning';
			break;
		case 'draft':
		case 'pending':
		case 'scheduled':
			label.displayType = 'info';
			break;
		default:
			label.displayType = 'secondary';
			break;
	}

	return label;
}
