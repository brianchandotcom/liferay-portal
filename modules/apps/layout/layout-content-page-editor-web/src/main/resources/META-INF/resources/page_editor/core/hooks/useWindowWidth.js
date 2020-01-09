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

import {useEventListener} from 'frontend-js-react-web';
import {useState} from 'react';

export default function useWindowWidth() {
	const [width, setWidth] = useState(window.innerWidth);

	const handleResize = () => setWidth(window.innerWidth);

	useEventListener('resize', handleResize, false, window);

	return width;
}
