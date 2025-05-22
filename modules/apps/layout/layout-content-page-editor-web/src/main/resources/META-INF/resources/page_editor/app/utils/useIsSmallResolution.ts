/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

export default function useIsSmallResolution() {
	const [isSmallResolution, setIsSmallResolution] = useState(false);

	useEffect(() => {
		const onChange = (event: MediaQueryListEvent) => {
			setIsSmallResolution(event.matches);
		};

		const mediaQuery = window.matchMedia('(max-width: 768px)');

		if (mediaQuery.matches) {
			setIsSmallResolution(true);
		}

		mediaQuery.addEventListener('change', onChange);

		return () => mediaQuery.removeEventListener('change', onChange);
	}, []);

	return isSmallResolution;
}
