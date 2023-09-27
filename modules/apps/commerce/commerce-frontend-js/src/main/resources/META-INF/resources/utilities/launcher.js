/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@liferay/frontend-js-react-web';

export default function launcher(
	Component,
	componentId,
	containerId,
	props = {}
) {
	const container = window.document.getElementById(containerId);
	const renderData = {
		componentId,
		...props,
	};

	return render(Component, renderData, container);
}
