/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {deleteEntry, renameEntry} from './RepositoryBrowserDropdownActions';

const ACTIONS = {
	delete({deleteURL}) {
		deleteEntry(deleteURL);
	},

	rename({renameURL, value}) {
		renameEntry(renameURL, value);
	},
};

export default function propsTransformer({items, portletNamespace, ...props}) {
	return {
		...props,
		items: items.map((item) => ({
			...item,
			onClick(event) {
				const action = item.data?.action;

				if (action) {
					event.preventDefault();

					ACTIONS[action](item.data, portletNamespace);
				}
			},
		})),
	};
}
