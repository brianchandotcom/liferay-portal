/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@liferay/frontend-js-react-web';

export default function (renderFunction, renderData, placeholderId) {
	const element = document.getElementById(placeholderId);

	if (element) {
		render(renderFunction, renderData, element.parentElement);
	}
}
