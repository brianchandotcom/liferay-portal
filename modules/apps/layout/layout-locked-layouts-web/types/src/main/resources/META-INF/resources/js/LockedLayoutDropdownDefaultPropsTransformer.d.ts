/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

declare type Item = {
	items: ItemChild[];
	type: 'group';
};
declare type ItemChild = {
	data: ItemData;
	icon: string;
	label: string;
	type: 'item';
};
declare type ItemData = {
	action: 'unlockLockedLayout';
	unlockLockedLayoutURL: string;
};
export default function propsTransformer({
	items,
	...props
}: {
	items: Item[];
}): {
	items: {
		items: {
			onClick(event: React.MouseEvent<HTMLButtonElement>): void;
			data: ItemData;
			icon: string;
			label: string;
			type: 'item';
		}[];
		type: 'group';
	}[];
};
export {};
