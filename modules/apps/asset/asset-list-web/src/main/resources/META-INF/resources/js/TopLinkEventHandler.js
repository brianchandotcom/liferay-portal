/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {delegate, getTop} from 'frontend-js-web';

export default function () {
	const delegateHandler = delegate(document.body, 'click', 'a', (event) => {
		const openerWindow = getTop();

		if (openerWindow && event.delegateTarget.target === '_top') {
			event.preventDefault();

			openerWindow.Liferay.Util.navigate(event.delegateTarget.href);
		}
	});

	return {
		dispose() {
			delegateHandler.dispose();
		},
	};
}
