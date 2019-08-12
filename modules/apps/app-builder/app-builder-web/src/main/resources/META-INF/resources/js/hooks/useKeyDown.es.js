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

import {useEffect, useRef} from 'react';

export default (callback, targetKeyCode, element = window) => {
	const callbackRef = useRef();

	useEffect(() => {
		callbackRef.current = callback;
	}, [callback]);

	useEffect(() => {
		const handler = () => {
			if (event.keyCode === targetKeyCode) {
				callbackRef.current(event);
			}
		};

		const current = element.current ? element.current : element;
		current.addEventListener('keydown', handler);

		return () => current.removeEventListener('keydown', handler);
	}, [element, callbackRef, targetKeyCode]);
};
