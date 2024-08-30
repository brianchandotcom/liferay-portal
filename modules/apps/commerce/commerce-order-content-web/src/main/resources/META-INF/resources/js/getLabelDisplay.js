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

	if (label.label === 'approved' || label.label === 'completed') {
		return 'success';
	}
	if (label.label === 'denied') {
		return 'danger';
	}
	if (
		label.label === 'draft' ||
		label.label === 'pending' ||
		label.label === 'scheduled'
	) {
		return 'info';
	}
	if (label.label === 'expired') {
		return 'warning';
	}

	return 'secondary';
}
