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

import React, {forwardRef, useCallback, useEffect} from 'react';

import {useObserveRect} from '../hooks/useObserveRect';
import {doAlign} from '../utils';

export const Hotspot = forwardRef(({onHotspotClick, trigger}, ref) => {
	const align = useCallback(() => {
		if (trigger && ref?.current) {
			doAlign({
				points: ['cc', 'tl'],
				sourceElement: ref.current,
				targetElement: trigger,
			});
		}
	}, [ref, trigger]);

	useEffect(() => {
		align();
	}, [align]);

	useObserveRect(align, trigger);

	return (
		<div
			aria-label={Liferay.Language.get('start-the-walkthrough')}
			className="lfr-walkthrough-hotspot"
			onClick={onHotspotClick}
			ref={ref}
		>
			<div className="lfr-walkthrough-hotspot-inner" />
		</div>
	);
});
