/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import toggleDisabled from './toggle_disabled';

export default function removeEntitySelection(
	entityIdString,
	entityNameString,
	removeEntityButton,
	namespace
) {
	const elementByEntityId = document.getElementById(
		`${namespace}${entityIdString}`
	);

	if (elementByEntityId) {
		elementByEntityId.value = 0;
	}

	const elementByEntityName = document.getElementById(
		`${namespace}${entityNameString}`
	);

	if (elementByEntityName) {
		elementByEntityName.value = '';
	}

	toggleDisabled(removeEntityButton, true);

	Liferay.fire('entitySelectionRemoved');
}
