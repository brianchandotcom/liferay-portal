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

import {useEffect} from 'react';

const PROPERTY = {
	overflowY: {
		off: 'hidden',
		on: 'scroll',
	},
	touchAction: {
		off: 'none',
		on: 'auto',
	},
};

const useImperativeDisableScroll = ({disabled, element}) => {
	useEffect(() => {
		if (!element) {
			return;
		}

		element.style['touch-action'] = disabled // on/off scroll for Mobile Phone
			? PROPERTY.touchAction.off
			: PROPERTY.touchAction.on;

		element.style.overflowY = disabled // on/off scroll for Desktop Browser
			? PROPERTY.overflowY.off
			: PROPERTY.overflowY.on;

		return () => {
			element.style['touch-action'] = PROPERTY.overflowY.on;
			element.style.overflowY = PROPERTY.overflowY.on;
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [disabled]);
};

export default useImperativeDisableScroll;
