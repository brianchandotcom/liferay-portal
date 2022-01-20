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

import {openErrorToast, openSuccessToast} from './toasts';

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
