/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';

const ARROW_DOWN_KEY_CODE = 'ArrowDown';
const ARROW_LEFT_KEY_CODE = 'ArrowLeft';
const ARROW_RIGHT_KEY_CODE = 'ArrowRight';
const ARROW_UP_KEY_CODE = 'ArrowUp';

export default function useKeyboardNavigation() {
	const [element, setElement] = useState(null);
	const [isTarget, setIsTarget] = useState(false);

	const rtl =
		Liferay.Language.direction[themeDisplay.getLanguageId()] === 'rtl';

	useEffect(() => {
		const list = element?.closest('[role="menubar"]');

		const items = list?.querySelectorAll('.focusable-menu-item');

		const isFirstChild = element === items?.[0];

		setIsTarget(isFirstChild);
	}, [element]);

	const onFocus = useCallback(() => setIsTarget(true), []);

	const onBlur = useCallback((event) => {
		const list = event.target.closest('[role="menubar"]');

		const nextActiveElement = event.relatedTarget;

		if (list?.contains(nextActiveElement)) {
			setIsTarget(false);
		}
	}, []);

	const onKeyDown = useCallback(
		(event) => {
			const list = element?.closest('[role="menubar"]');

			if (event.target.closest('.menu-item-dropdown')) {
				return;
			}

			const items = Array.from(
				list?.querySelectorAll('.focusable-menu-item')
			);

			if (event.key === ARROW_DOWN_KEY_CODE) {
				const index = items.indexOf(element);

				items[index + 1]?.focus();
			}

			if (event.key === ARROW_UP_KEY_CODE) {
				const index = items.indexOf(element);

				items[index - 1]?.focus();
			}

			const NEXT_KEY = rtl ? ARROW_LEFT_KEY_CODE : ARROW_RIGHT_KEY_CODE;
			const PREVIOUS_KEY = rtl
				? ARROW_RIGHT_KEY_CODE
				: ARROW_LEFT_KEY_CODE;

			if (event.key === NEXT_KEY) {
				const buttons = Array.from(element.querySelectorAll('button'));

				const index = buttons.indexOf(event.target);

				if (index >= 0) {
					buttons[index + 1]?.focus();
				}
				else {
					buttons[0]?.focus();
				}
			}

			if (event.key === PREVIOUS_KEY) {
				const buttons = Array.from(element.querySelectorAll('button'));

				const index = buttons.indexOf(event.target);

				if (index === 0) {
					element.focus();
				}
				else if (index > 0) {
					buttons[index - 1]?.focus();
				}
			}
		},
		[element, rtl]
	);

	return {element, isTarget, onBlur, onFocus, onKeyDown, setElement};
}
