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

import React, {useLayoutEffect, useRef} from 'react';

import {usePage} from '../hooks/usePage.es';

export const AutoFocus = ({children}) => {
	const childRef = useRef();
	const {containerElement} = usePage();

	// This is a hack to know when FieldLazy is ready.

	const lazyStatus = children?.props?.children?.type?._status;

	useLayoutEffect(() => {
		if (childRef.current && containerElement && containerElement.current) {
			const firstInput = childRef.current.querySelector('input');

			if (
				firstInput &&
				!containerElement.current.contains(document.activeElement)
			) {
				firstInput.focus();

				if (firstInput.select) {
					firstInput.select();
				}
			}
		}
	}, [childRef, containerElement, lazyStatus]);

	return React.cloneElement(children, {
		ref: (node) => {
			childRef.current = node;
		},
	});
};
