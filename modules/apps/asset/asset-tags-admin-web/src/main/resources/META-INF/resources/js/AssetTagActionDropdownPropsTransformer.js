/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import openDeleteTagModal from './openDeleteTagModal';

const ACTIONS = {
	deleteTag({deleteTagURL}) {
		openDeleteTagModal({
			onDelete: () => {
				submitForm(document.hrefFm, deleteTagURL);
			},
		});
	},
};

export default function propsTransformer({items, ...otherProps}) {
	return {
		...otherProps,
		items: items.map((item) => ({
			...item,
			items: item.items?.map((child) => ({
				...child,
				onClick() {
					const action = child.data?.action;

					ACTIONS[action](child.data);
				},
			})),
		})),
	};
}
