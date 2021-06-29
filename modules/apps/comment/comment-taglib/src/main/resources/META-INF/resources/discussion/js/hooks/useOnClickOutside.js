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

import React from 'react';

const {useEffect} = React;

export default function useOnClickOutside(elements, handler) {
	useEffect(() => {
		const listener = (event) => {
			const {target} = event;

			/**
			 * Detect clicks on elements or their descendent elements.
			 */
			const filtered = elements.filter((element) => {
				if (typeof element === 'string') {
					return !!target.closest(element);
				}

				return element && element.contains(target);
			});

			if (!filtered.length) {
				handler(event);
			}
		};

		document.addEventListener('mousedown', listener);
		document.addEventListener('touchstart', listener);

		return () => {
			document.removeEventListener('mousedown', listener);
			document.removeEventListener('touchstart', listener);
		};
	}, [elements, handler]);
}
