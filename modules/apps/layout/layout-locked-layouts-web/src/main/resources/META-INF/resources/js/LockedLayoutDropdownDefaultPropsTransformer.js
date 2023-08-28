/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import openUnlockLayoutsModal from './openUnlockLayoutsModal';

const ACTIONS = {
	send(url) {
		submitForm(document.hrefFm, url);
	},

	unlockLockedLayout(itemData) {
		openUnlockLayoutsModal({
			onUnlock: () => {
				this.send(itemData.unlockLockedLayoutURL);
			},
		});
	},
};

export default function propsTransformer({
	actions,
	items,
	portletNamespace,
	...props
}) {
	const updateItem = (item) => {
		return {
			...item,
			items: item.items.map((child) => ({
				...child,
				onClick(event) {
					const action = child.data?.action;

					if (action) {
						event.preventDefault();

						ACTIONS[action](child.data, portletNamespace);
					}
				},
			})),
		};
	};

	return {
		...props,
		actions: actions?.map(updateItem),
		items: items?.map(updateItem),
	};
}
