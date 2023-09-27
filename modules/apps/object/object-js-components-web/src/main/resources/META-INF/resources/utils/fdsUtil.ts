/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function onActionDropdownItemClick<T>({
	action,
	event,
	itemData,
}: {
	action: FDSAction;
	event: Event;
	itemData: T;
}) {
	event.preventDefault();

	if (action.target === 'event') {
		Liferay.fire(action.id, {itemData});
	}
}

interface FDSAction {
	id: string;
	target: 'event' | 'async';
}
