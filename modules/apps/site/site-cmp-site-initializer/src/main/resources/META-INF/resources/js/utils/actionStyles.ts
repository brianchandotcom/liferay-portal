/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function styleActions(actions: any[]): any[] {
	return actions.map((action) => {
		const styled =
			action?.data?.id === 'delete'
				? {...action, className: 'text-danger'}
				: action;

		return styled.items
			? {...styled, items: styleActions(styled.items)}
			: styled;
	});
}

export function styleBulkActions(bulkActions: any[]) {
	return bulkActions.map((action) =>
		action?.data?.id === 'delete'
			? {
					...action,
					className: 'text-danger',
					displayType: 'danger',
					symbol: 'trash',
				}
			: action
	);
}
