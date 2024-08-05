/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from '@liferay/object-js-components-web';

export interface Item {
	termLabel: string;
	termName: string;
}

export default function ({itemData}: {itemData: Item}) {
	navigator.clipboard.writeText(itemData.termName);

	openToast({
		message: Liferay.Language.get('term-copied-successfully'),
		type: 'success',
	});
}
