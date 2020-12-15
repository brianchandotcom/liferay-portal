/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
