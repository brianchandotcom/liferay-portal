/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {printPage} from 'frontend-js-web';

export default function ({namespace, printPageURL}) {
	const printPageButton = document.getElementById(
		`${namespace}printPageButton`
	);

	if (printPageButton) {
		printPageButton.addEventListener('click', (event) => {
			event.preventDefault();

			printPage(printPageURL);
		});
	}
}
