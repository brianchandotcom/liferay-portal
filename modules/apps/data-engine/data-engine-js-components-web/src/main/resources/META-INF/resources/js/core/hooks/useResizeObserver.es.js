/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

export function useResizeObserver(targetRef) {
	const [contentRect, setContentRect] = useState({});

	useEffect(() => {
		let resizeObserver;

		if ('ResizeObserver' in window) {
			resizeObserver = new ResizeObserver((entries) => {
				const {
					bottom,
					height,
					left,
					right,
					top,
					width,
				} = entries[0].contentRect;

				setContentRect({bottom, height, left, right, top, width});
			});

			if (targetRef.current) {
				resizeObserver.observe(targetRef.current);
			}

			return () => resizeObserver.disconnect();
		}
	}, [targetRef]);

	return contentRect;
}
