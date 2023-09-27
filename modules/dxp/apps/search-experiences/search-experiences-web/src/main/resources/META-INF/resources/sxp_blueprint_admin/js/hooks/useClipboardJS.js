/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClipboardJS from 'clipboard';
import {useEffect} from 'react';

import {openErrorToast, openSuccessToast} from '../utils/toasts';

/**
 * This only should be used once per page since this will enable the clipboard
 * for all elements matching the given selector.
 *
 * To create a copy-to-clipboard button, add the class
 * `${COPY_BUTTON_CSS_CLASS}` to the element and `data-clipboard-text` attribute
 * to define the text to be copied.
 *
 * For example:
 * 		<ClayButton
 * 			className={COPY_BUTTON_CSS_CLASS}
 * 			data-clipboard-text={text}
 * 		>
 * 			Copy to Clipboard
 * 		</ClayButton>
 *
 * @param {String} selector The selector for a copy button
 */
export default function useClipboardJS(selector) {
	useEffect(() => {
		const clipboardJS = new ClipboardJS(selector);

		clipboardJS.on('success', () =>
			openSuccessToast({
				message: Liferay.Language.get('copied-to-clipboard'),
			})
		);

		clipboardJS.on('error', () => openErrorToast());

		return () => {
			clipboardJS.destroy();
		};
	}, [selector]);
}
