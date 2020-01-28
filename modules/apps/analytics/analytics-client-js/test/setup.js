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

/**
 * The analytics-client-js implementation relies on the non-standard
 * `innerText` property, which jsdom does not implement, so we need this
 * special helper in tests that sets `innerText` whenever `innerHTML` is
 * set.
 *
 * @see https://github.com/jsdom/jsdom/issues/1245
 */
global.setInnerHTML = (element, html) => {
	element.innerHTML = html;
	element.innerText = element.textContent;
};
