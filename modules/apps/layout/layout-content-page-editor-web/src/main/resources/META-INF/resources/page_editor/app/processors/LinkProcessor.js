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

import {isNullOrUndefined} from '@liferay/layout-js-components-web';

import {getEditableLinkValue} from '../utils/getEditableLinkValue';
import getAlloyEditorProcessor from './getAlloyEditorProcessor';
import {getLinkableEditableEditorWrapper} from './getLinkableEditableEditorWrapper';

export default getAlloyEditorProcessor(
	'text',
	getLinkableEditableEditorWrapper,
	(element, value, editableConfig, languageId) => {
		const anchor =
			element instanceof HTMLAnchorElement
				? element
				: element.querySelector('a');

		const link = getEditableLinkValue(editableConfig, languageId);

		if (anchor) {
			const href = link.href
				? `${editableConfig.prefix || ''}${link.href}`
				: '#';

			anchor.href = href;
			anchor.target = link.target;

			if (!isNullOrUndefined(value)) {
				anchor.innerHTML = value;
			}
		}
	}
);
