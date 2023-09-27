/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const warnMessage = 'Changes you made may not be saved.';

export function createExitAlert() {
	window.onbeforeunload = function (event) {
		event.preventDefault();
		event.returnValue = warnMessage;

		return warnMessage;
	};
}

export function clearExitAlert() {
	window.onbeforeunload = function () {};
}
