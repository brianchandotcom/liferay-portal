/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {dateUtils} from 'frontend-js-web';

import {ActionItem, DesignLibrary} from '../../types';

export default function createSetItemComponentProps(symbol: string) {
	return function ({
		item,
		props,
	}: {
		item: DesignLibrary;
		props: {actions: ActionItem[]};
	}) {
		const editAction = props.actions.find((action) => {
			return action.data.id === 'edit';
		});

		return {
			...props,
			description: dateUtils.fromNow(new Date(item.dateModified)),
			href: editAction ? editAction.href : undefined,
			symbol,
		};
	};
}
