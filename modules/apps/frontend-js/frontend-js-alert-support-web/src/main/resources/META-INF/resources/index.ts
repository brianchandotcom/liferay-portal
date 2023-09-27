/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {delegate} from 'frontend-js-web';

let handle: ReturnType<typeof delegate> | undefined;

export default function main() {
	if (!handle) {
		handle = delegate(
			document.body,
			'click',
			'[data-dismiss="liferay-alert"]',
			(event) => {
				event.preventDefault();

				const container = event.delegateTarget.closest('.alert');

				if (container) {
					container.parentNode.removeChild(container);
				}
			}
		);
	}
}
