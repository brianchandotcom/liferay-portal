/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
