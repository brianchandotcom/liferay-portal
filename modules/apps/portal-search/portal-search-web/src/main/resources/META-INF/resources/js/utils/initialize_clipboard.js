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
