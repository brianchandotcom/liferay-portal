/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSelectionModal} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps,
	portletNamespace,
	...props
}) {
	return {
		...props,
		onClick() {
			const {currentURL, itemSelectorURL} = additionalProps;

			openSelectionModal({
				id: `${portletNamespace}selectSite`,
				onSelect: (event) => {
					const toGroupIdInput = document.getElementById(
						`${portletNamespace}toGroupId`
					);

					toGroupIdInput.value = event.groupid;

					const redirectInput = document.getElementById(
						`${portletNamespace}redirect`
					);

					redirectInput.value = currentURL;

					submitForm(toGroupIdInput.form);
				},
				selectEventName: `${portletNamespace}selectSite`,
				title: Liferay.Language.get('select-site'),
				url: `${itemSelectorURL}`,
			});
		},
	};
}
