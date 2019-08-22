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

const PARENT_SELECTOR = 'li.control-menu-nav-category > ul > li';
const TITLE_SELECTOR = `${PARENT_SELECTOR} > span.control-menu-level-1-heading`;
const TOOLBAR_SELECTOR =
	'li.control-menu-nav-category.tools-control-group > ul > li';
const TOOLTIP_SELECTOR = `${PARENT_SELECTOR} > span.taglib-icon-help`;

export default title => {
	const previousToolbar = useRef();

	useEffect(() => {
		const toolbarElement = document.querySelector(TOOLBAR_SELECTOR);

		if (toolbarElement) {
			previousToolbar.current = toolbarElement.cloneNode(true);
		}

		return () => {
			if (previousToolbar.current) {
				const toolbarElement = document.querySelector(TOOLBAR_SELECTOR);

				if (toolbarElement) {
					toolbarElement.parentNode.replaceChild(
						previousToolbar.current,
						toolbarElement
					);
				}
			}
		};
	}, []);

	useEffect(() => {
		const titleElement = document.querySelector(TITLE_SELECTOR);

		if (titleElement) {
			titleElement.innerText = title;
		}

		const tooltipElement = document.querySelector(TOOLTIP_SELECTOR);

		if (tooltipElement) {
			tooltipElement.remove();
		}
	}, [title]);
};
