/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSimpleInputModal} from 'frontend-js-web';

export default function propsTransformer({
	items,
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		items: items.map((item) => {
			return {
				...item,
				onClick() {
					const {action, addAssetListEntryURL, title} = item.data;

					if (action === 'addAssetListEntry') {
						openSimpleInputModal({
							dialogTitle: title,
							formSubmitURL: addAssetListEntryURL,
							mainFieldLabel: Liferay.Language.get('title'),
							mainFieldName: 'title',
							mainFieldPlaceholder: Liferay.Language.get('title'),
							namespace: portletNamespace,
						});
					}
				},
			};
		}),
	};
}
