/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function useCustomEvent(event) {
	const dispatch = (data, _event = event) => {
		window.dispatchEvent(
			new CustomEvent(_event, {
				bubbles: true,
				composed: true,
				detail: {data},
			})
		);
	};

	return [dispatch];
}
