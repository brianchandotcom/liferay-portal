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

export function AutoFocus({children}) {
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
				if (
					!document.activeElement ||
					(!Liferay.ThemeDisplay.isControlPanel() &&
						document.activeElement.querySelector('input') &&
						document.activeElement.querySelector('input').type ===
							'hidden') ||
					containerElement.current.parentNode.className.includes(
						'ddm-form-builder-app'
					)
				) {
					removeTabs();

					const currentTitle = containerElement?.current.parentNode.getElementsByClassName(
						'lfr-ddm__default-page-header-title'
					)[0];

					scrollComponentToTop(currentTitle);

					const componentTitle = currentTitle?.innerHTML;

					const currentDescription = containerElement?.current.parentNode.getElementsByClassName(
						'.lfr-ddm__default-page-header-description'
					)[0];

					const componentDescription = currentDescription?.innerHTML;

					const currentPage = document.activeElement.querySelector(
						'.ddm-layout-builder:not(.hide)'
					);

					const firstInput = currentPage?.querySelector(
						'input:not([type="hidden"])'
					);

					const sidebarOpen = document.querySelector(
						'.ddm-form-builder--sidebar-open'
					);

					const userViewContent = containerElement.current.querySelector(
						'.ddm-user-view-content'
					);

					const defaultTitle = Liferay.Language.get('untitled-form');

					if (
						(!componentTitle || componentTitle === defaultTitle) &&
						!componentDescription &&
						firstInput &&
						!containerElement.current.contains(
							document.activeElement
						) &&
						(sidebarOpen || userViewContent)
					) {
						firstInput.focus({
							behavior: 'smooth',
							preventScroll: false,
						});

						if (firstInput.select) {
							firstInput.select();
						}
					}

					scrollComponentToTop(currentTitle);
				}
			}
		}
	}, [activePage, childRef, containerElement, increment]);

	return React.cloneElement(children, {
		ref: (node) => {
			childRef.current = node;
		},
	});
}

function scrollComponentToTop(currentTitle) {
	const containerPosition = currentTitle.getBoundingClientRect();

	const menuSize = document.querySelector('.control-menu-container')
		?.clientHeight;

	window.scroll(
		containerPosition.x - menuSize,
		containerPosition.y - menuSize
	);
}

function removeTabs() {
	const firstPageComponent = document.querySelector(
		'div[class^="lfr-layout-structure-item'
	);

	const formPortlet = firstPageComponent?.querySelector('.portlet-forms');

	if (!formPortlet) {
		document
			.querySelectorAll(
				'.lfr-ddm__default-page-header-title,.lfr-ddm__default-page-header-description,.lfr-ddm-form-page-title,.lfr-ddm-form-page-description'
			)
			?.forEach((element) => element.removeAttribute('tabindex'));
	}
}
