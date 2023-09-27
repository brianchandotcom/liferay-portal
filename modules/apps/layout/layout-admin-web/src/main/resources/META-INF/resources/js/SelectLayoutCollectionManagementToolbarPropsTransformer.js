/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSimpleInputModal} from 'frontend-js-web';

export default function propsTransformer({...otherProps}) {
	return {
		...otherProps,
		onCreationMenuItemClick: (event, {item}) => {
			const data = item?.data;

			const action = data?.action;

			if (action === 'addAssetListEntry') {
				openSimpleInputModal({
					dialogTitle: data?.title,
					formSubmitURL: data?.addAssetListEntryURL,
					mainFieldLabel: Liferay.Language.get('title'),
					mainFieldName: 'title',
					mainFieldPlaceholder: Liferay.Language.get('title'),
					namespace: data?.namespace,
				});
			}
		},
	};
}
