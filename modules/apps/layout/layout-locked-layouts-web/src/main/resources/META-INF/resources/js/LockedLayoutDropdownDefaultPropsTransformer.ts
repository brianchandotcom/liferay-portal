/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import openUnlockLayoutsModal from './openUnlockLayoutsModal';

type Item = {
	items: ItemChild[];
	type: 'group';
};

type ItemChild = {
	data: ItemData;
	icon: string;
	label: string;
	type: 'item';
};

type ItemData = {
	action: 'unlockLockedLayout';
	unlockLockedLayoutURL: string;
};

const ACTIONS = {
	send(url: string) {

		// @ts-ignore

		submitForm(document.hrefFm, url);
	},

	unlockLockedLayout(itemData: ItemData) {
		openUnlockLayoutsModal({
			onUnlock: () => {
				this.send(itemData.unlockLockedLayoutURL);
			},
		});
	},
};

export default function propsTransformer({items, ...props}: {items: Item[]}) {
	const updateItem = (item: Item) => {
		return {
			...item,
			items: item.items.map((child: ItemChild) => ({
				...child,

				onClick(event: React.MouseEvent<HTMLButtonElement>) {
					const action = child.data?.action;

					if (action) {
						event.preventDefault();

						ACTIONS[action](child.data);
					}
				},
			})),
		};
	};

	return {
		...props,
		items: items?.map(updateItem),
	};
}
