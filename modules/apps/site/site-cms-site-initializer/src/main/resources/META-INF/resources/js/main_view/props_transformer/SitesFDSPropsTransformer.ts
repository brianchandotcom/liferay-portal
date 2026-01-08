/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IInternalRenderer} from '@liferay/frontend-data-set-web';

import manageConnectedSitesAction from './actions/manageConnectedSitesAction';
import SiteRenderer from './cell_renderers/SiteRenderer';

export default function SitesFDSPropsTransformer({
	creationMenu,
	itemsActions = [],
	...otherProps
}: {
	creationMenu: any;
	itemsActions?: any[];
	otherProps: any;
}) {
	return {
		...otherProps,
		creationMenu: {
			...creationMenu,
			primaryItems: creationMenu.primaryItems.map(
				(item: {data: {action: string}}) => {
					return {
						...item,
						onClick() {
							const action = item.data.action;

							const loadData = () => window.location.reload();

							if (action === 'connectSites') {
								manageConnectedSitesAction(
									item.data as any,
									loadData
								);
							}
						},
					};
				}
			),
		},
		customRenderers: {
			tableCell: [
				{
					component: SiteRenderer,
					name: 'siteTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
			],
		},
		itemsActions: itemsActions.map((action) => {
			if (action?.data?.id === 'make-searchable') {
				return {
					...action,
					isVisible: (item: any) => Boolean(!item?.searchable),
				};
			}
			else if (action?.data?.id === 'make-unsearchable') {
				return {
					...action,
					isVisible: (item: any) => Boolean(item?.searchable),
				};
			}

			return action;
		}),
		onActionDropdownItemClick: ({
			action,
			loadData,
		}: {
			action: {data: {id: string}};
			loadData: () => {};
		}) => {
			if (action.data.id === 'delete') {
				loadData();
			}
		},
	};
}
