/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export interface ICreationActionItem {
	data?: {
		permissionKey?: string;
		size?: string;
		title?: string;
	};
	href: string;
	icon?: string;
	label: string;
	onClick: Function;
	target:
		| 'event'
		| 'link'
		| 'modal'
		| 'modal-full-screen'
		| 'modal-lg'
		| 'modal-sm'
		| 'sidePanel';
	type: string;
}
declare const filterCreationActions: (
	actions: Array<ICreationActionItem>,
	itemData: any
) => Array<ICreationActionItem> | null;
export default filterCreationActions;
