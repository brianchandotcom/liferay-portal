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

import {useSessionState} from '@liferay/layout-content-page-editor-web';
import {debounce} from 'frontend-js-web';
import React, {useEffect, useRef} from 'react';

import {useConstants} from '../contexts/ConstantsContext';
import {useItems} from '../contexts/ItemsContext';
import {MenuItem} from './MenuItem';

export function Menu() {
	const items = useItems();

	const {portletNamespace} = useConstants();

	const [scrollPosition, setScrollPosition] = useSessionState(
		`${portletNamespace}_scrollPosition`,
		0
	);

	const containerRef = useRef(null);
	const scrollPositionRef = useRef(scrollPosition);

	useEffect(() => {
		const containerElement = containerRef.current;

		if (!containerElement) {
			return;
		}

		if (scrollPositionRef.current) {
			containerElement.scrollY = scrollPositionRef.current;
		}

		const handleScroll = debounce(() => {
			setScrollPosition(containerElement.scrollY);
		}, 300);

		containerElement.addEventListener('scroll', handleScroll, {
			passive: true,
		});

		return () => {
			containerElement.removeEventListener('scroll', handleScroll);
		};
	}, [setScrollPosition]);

	return (
		<div
			className="container ml-lg-auto ml-sm-0 p-3 pt-4"
			ref={containerRef}
			role="list"
		>
			{items.map((item) => (
				<MenuItem item={item} key={item.siteNavigationMenuItemId} />
			))}
		</div>
	);
}
