/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {commerceEvents} from 'commerce-frontend-js';

function dataSetRefresh({datasetId}) {
	window.parent.Liferay.on(commerceEvents.CLOSE_MODAL, () => {
		Liferay.fire(commerceEvents.FDS_UPDATE_DISPLAY, {
			id: datasetId,
		});
	});
}

export default function (context) {
	dataSetRefresh(context);
}
