/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {delegate, openModal} from 'frontend-js-web';

export default function ({namespace}) {
	const editDefaultPermissionsClickHandler = delegate(
		document.getElementById(
			`${namespace}portalDefaultPermissionsSearchContainer`
		),
		'click',
		'.btn',
		(event) => {
			openModal({
				size: 'full-screen',
				title: Liferay.Language.get('edit-default-permissions'),
				url: event.delegateTarget.getAttribute('data-url'),
			});
		}
	);

	function handleDestroyPortlet() {
		editDefaultPermissionsClickHandler.dispose();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
}
