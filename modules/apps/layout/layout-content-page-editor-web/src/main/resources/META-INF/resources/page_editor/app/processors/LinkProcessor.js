/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			anchor.rel = link.rel;
			anchor.target = link.target;

			if (!isNullOrUndefined(value)) {
				anchor.innerHTML = value;
			}
		}
	}
);
