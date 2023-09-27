/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ACTIONS} from './actions';

export default function propsTransformer({
	additionalProps: {
		deleteFragmentCollectionURL,
		exportFragmentCollectionsURL,
		importURL,
		viewDeleteFragmentCollectionsURL,
		viewExportFragmentCollectionsURL,
		viewImportURL,
	},
	items,
	portletNamespace,
	...props
}) {
	return {
		...props,
		items: items.map((item) => {
			return {
				...item,
				items: item.items?.map((child) => {
					return {
						...child,
						onClick(event) {
							const action = child.data?.action;

							if (action) {
								event.preventDefault();

								ACTIONS[action]({
									deleteFragmentCollectionURL,
									exportFragmentCollectionsURL,
									importURL,
									portletNamespace,
									viewDeleteFragmentCollectionsURL,
									viewExportFragmentCollectionsURL,
									viewImportURL,
								});
							}
						},
					};
				}),
			};
		}),
	};
}
