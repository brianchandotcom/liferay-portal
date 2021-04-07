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

import React, {useLayoutEffect, useRef, useState} from 'react';

import {usePage} from '../hooks/usePage.es';

export const AutoFocus = ({children}) => {
	const childRef = useRef();
	const {activePage, containerElement} = usePage();
	const [increment, setIncrement] = useState(0);

	useLayoutEffect(() => {
		if (childRef.current && containerElement?.current) {
			if (childRef.current.querySelector('.loading-animation')) {

				/**
				 * This hack is to update the variable increment and cause one
				 * more render until all loadings are removed from the screen
				 * and so the field will be ready to focus
				 */

				setTimeout(() => setIncrement((value) => value + 1), 5);
			}
			else {
				const firstInput = childRef.current.querySelector('input');
				const sidebarOpen = document.querySelector(
					'.ddm-form-builder--sidebar-open'
				);

				if (
					firstInput &&
					!containerElement.current.contains(
						document.activeElement
					) &&
					sidebarOpen
				) {
					firstInput.focus();

					if (firstInput.select) {
						firstInput.select();
					}
				}
			}
		}
	}, [activePage, childRef, containerElement, increment]);

	return React.cloneElement(children, {
		ref: (node) => {
			childRef.current = node;
		},
	});
};
