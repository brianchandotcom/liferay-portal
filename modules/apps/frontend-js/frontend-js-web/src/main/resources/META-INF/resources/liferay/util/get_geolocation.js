/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getGeolocation(success, fallback, options) {
	if (success && navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(
			(position) => {
				success(
					position.coords.latitude,
					position.coords.longitude,
					position
				);
			},
			fallback,
			options
		);
	}
	else if (fallback) {
		fallback();
	}
}
