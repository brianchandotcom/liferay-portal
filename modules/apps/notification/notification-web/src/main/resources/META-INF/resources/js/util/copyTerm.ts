/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from '@liferay/object-js-components-web';

export interface Item {
	termLabel: string;
	termName: string;
}

function displaySucessToast() {
	openToast({
		message: Liferay.Language.get('term-copied-successfully'),
		type: 'success',
	});
}

function fallbackCopyToClipboard(text: string) {
	const textarea = document.createElement('textarea');

	textarea.value = text;
	textarea.className = 'sr-only';

	document.body.appendChild(textarea);

	textarea.select();

	try {
		document.execCommand('copy');
		displaySucessToast();
	}
	catch (error) {
		console.error(error);
	}

	document.body.removeChild(textarea);
}

export default function ({itemData}: {itemData: Item}) {
	if (window.isSecureContext && navigator.clipboard) {
		navigator.clipboard.writeText(itemData.termName);
		displaySucessToast();
	}
	else {
		fallbackCopyToClipboard(itemData.termName);
	}
}
