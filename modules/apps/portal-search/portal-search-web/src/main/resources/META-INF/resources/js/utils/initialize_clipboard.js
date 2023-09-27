/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClipboardJS from 'clipboard';
import {openToast} from 'frontend-js-web';

export default function initializeClipboard({selector}) {
	const clipboardJS = new ClipboardJS(selector);

	clipboardJS.on('success', () => {
		openToast({
			message: Liferay.Language.get('copied-to-clipboard'),
			title: Liferay.Language.get('success'),
			type: 'success',
		});
	});

	clipboardJS.on('error', () => {
		openToast({
			message: Liferay.Language.get('an-unexpected-error-occurred'),
			title: Liferay.Language.get('error'),
			type: 'danger',
		});
	});

	Liferay.once('beforeNavigate', () => {
		clipboardJS.destroy();
	});
}
