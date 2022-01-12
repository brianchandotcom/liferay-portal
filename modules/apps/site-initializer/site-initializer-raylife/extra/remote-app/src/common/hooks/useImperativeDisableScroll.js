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

const useImperativeDisableScroll = ({disabled, element}) => {
	useEffect(() => {
		if (!element) {
			return;
		}

		element.style.overflowY = disabled ? 'hidden' : 'scroll';

		return () => {
			element.style.overflowY = 'scroll';
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [disabled]);
};

export default useImperativeDisableScroll;
