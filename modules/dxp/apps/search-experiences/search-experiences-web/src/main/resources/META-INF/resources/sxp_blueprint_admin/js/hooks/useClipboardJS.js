/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
