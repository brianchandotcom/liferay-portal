/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IInternalRenderer} from '@liferay/frontend-data-set-web';
import {navigate, sessionStorage, sub} from 'frontend-js-web';

import AuthorRenderer from './cell_renderers/AuthorRenderer';
import NameRenderer from './cell_renderers/NameRenderer';
import {executeAsyncItemAction} from './utils/executeAsyncItemAction';

export default function ViewVersionHistoryFDSPropsTransformer({
	additionalProps,
	itemsActions = [],
	...otherProps
}: {
	additionalProps: any;
	itemsActions?: any[];
}) {
	return {
		...otherProps,
		customRenderers: {
			tableCell: [
				{
					component: AuthorRenderer,
					name: 'authorTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
				{
					component: NameRenderer,
					name: 'nameTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
			],
		},
		itemsActions: itemsActions.map((action) => {
			if (action?.data?.id === 'download') {
				return {
					...action,
					isVisible: (item: any) => Boolean(item?.file?.link?.href),
				};
			}

			return action;
		}),
		onActionDropdownItemClick({
			action,
			event,
			itemData,
		}: {
			action: {data: {id: string}};
			event: Event;
			itemData: {
				actions: {
					copy: {href: string; method: string};
				};
				systemProperties: {
					version: {
						number: number;
					};
				};
			};
		}) {
			if (action.data.id === 'copy') {
				event?.preventDefault();

				executeAsyncItemAction({
					method: itemData.actions.copy.method,
					refreshData: (responseData) => {
						sessionStorage.setItem(
							'com.liferay.site.cms.site.initializer.successMessage',
							sub(
								Liferay.Language.get(
									'version-x-successfully-copied-as-x'
								),
								itemData.systemProperties.version.number,
								`<strong>"${responseData.title}"</strong>`
							),
							sessionStorage.TYPES.NECESSARY
						);

						navigate(additionalProps.backURL);
					},
					showToast: false,
					url: itemData.actions.copy.href,
				});
			}
		},
	};
}
