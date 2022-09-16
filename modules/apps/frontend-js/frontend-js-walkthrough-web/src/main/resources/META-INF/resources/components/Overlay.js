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

import {OverlayMask} from '@clayui/core';
import React, {useCallback, useEffect, useState} from 'react';

import {useObserveRect} from '../hooks/useObserveRect';

export function Overlay({popoverVisible, trigger}) {
	const [overlayBounds, setOverlayBounds] = useState({
		height: 0,
		width: 0,
		x: 0,
		y: 0,
	});

	const updateOverlayBounds = useCallback(
		(overlayBounds) => {
			overlayBounds = overlayBounds
				? overlayBounds
				: trigger.getBoundingClientRect();

			setOverlayBounds({
				height: overlayBounds.height,
				width: overlayBounds.width,
				x: overlayBounds.x,
				y: overlayBounds.y,
			});
		},
		[trigger]
	);

	useEffect(() => {
		if (trigger) {
			updateOverlayBounds();
		}
	}, [updateOverlayBounds, trigger]);

	useObserveRect(updateOverlayBounds, trigger);

	return (
		<OverlayMask
			bounds={overlayBounds}
			onBoundsChange={setOverlayBounds}
			visible={popoverVisible}
		/>
	);
}
