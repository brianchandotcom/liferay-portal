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

import getAlloyEditorProcessor from './getAlloyEditorProcessor';
import {getLinkableEditableEditorWrapper} from './getLinkableEditableEditorWrapper';

export default getAlloyEditorProcessor(
	'text',
	getLinkableEditableEditorWrapper,
	(element, value, config = {}) => {
		if (config.href) {
			let anchor =
				element instanceof HTMLAnchorElement
					? element
					: element.querySelector('a');

			if (!anchor) {
				anchor = document.createElement('a');
			}

			anchor.href = config.href;
			anchor.target = config.target || '';
			anchor.innerHTML = value;

			element.innerHTML = anchor.outerHTML;
		}
		else {
			element.innerHTML = value;
		}
	}
);
