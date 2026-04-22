/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import interceptValueSetter from '../../common/utils/interceptValueSetter';

type EditableElement = HTMLInputElement | HTMLTextAreaElement;

export default function useIsContentEdited(formSelector: string): boolean {
	const [isContentEdited, setIsContentEdited] = useState(false);

	useEffect(() => {
		const form = document.querySelector(formSelector);

		if (!form) {
			return;
		}

		const abortController = new AbortController();
		const {signal} = abortController;

		const markAsEdited = () => setIsContentEdited(true);

		// Interactions on standard inputs

		form.addEventListener('input', markAsEdited, {signal});
		form.addEventListener('change', markAsEdited, {signal});

		// Interactions on non-standard inputs whose values are updated
		// programmatically via `.value = ...` assignments, which do not trigger
		// native DOM events. We override the setter to detect these changes.
		//
		// Examples: select-from-list, rich-text input, multi-select dropdown,
		// file upload components.

		let observer: MutationObserver | null = null;

		const interceptAllInputs = () => {
			form.querySelectorAll<EditableElement>('input, textarea').forEach(
				(element) => interceptValueSetter(element, markAsEdited)
			);
		};

		form.addEventListener(
			'focusin',
			() => {
				interceptAllInputs();

				observer = new MutationObserver(interceptAllInputs);

				observer.observe(form, {childList: true, subtree: true});
			},
			{once: true, signal}
		);

		return () => {
			abortController.abort();
			observer?.disconnect();
		};
	}, [formSelector]);

	return isContentEdited;
}
