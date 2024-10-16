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

	label.displayType = 'secondary';

	if (label.label === 'approved' || label.label === 'completed') {
		label.displayType = 'success';
	}
	else if (label.label === 'denied') {
		label.displayType = 'danger';
	}
	else if (
		label.label === 'draft' ||
		label.label === 'pending' ||
		label.label === 'scheduled'
	) {
		label.displayType = 'info';
	}
	else if (
		label.label === 'cancelled' ||
		label.label === 'expired' ||
		label.label === 'on-hold'
	) {
		label.displayType = 'warning';
	}

	return label;
}
