/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {GlobalContext} from '../../types/GlobalContext';

export default function isVisible(
	element: Element,
	globalContext: GlobalContext
): boolean {
	const computedStyle = globalContext.window.getComputedStyle(element);
	const {parentElement} = element;

	return (
		computedStyle.display !== 'none' &&
		computedStyle.visibility !== 'collapse' &&
		computedStyle.visibility !== 'hidden' &&
		!element.hasAttribute('hidden') &&
		(!parentElement ||
			parentElement.classList.contains('page-editor__form-children') ||
			isVisible(parentElement, globalContext))
	);
}
