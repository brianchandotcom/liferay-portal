/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';

import {DSRInitializer} from '../index';
import deleteDSRAction from './actions/deleteDSRAction';

export default function propsTransformer({
	creationMenu,
	itemsActions = [],
	...otherProps
}: {
	creationMenu: any;
	itemsActions?: any[];
}) {
	return {
		...otherProps,
		creationMenu: {
			...creationMenu,
			primaryItems: creationMenu.primaryItems.map(
				(item: {data?: {action?: string}}) => {
					return {
						...item,
						onClick() {
							const action = item?.data?.action;

							if (action && action === 'addDigitalSalesRoom') {
								return openModal({
									containerProps: {
										className: '',
									},
									contentComponent: ({
										closeModal,
									}: {
										closeModal: () => void;
									}) =>
										DSRInitializer({
											closeModal,
											numberOfSteps: 3,
										}),
									size: 'md',
								});
							}
						},
					};
				}
			),
		},
		itemsActions,
		onActionDropdownItemClick: ({
			action,
			event,
			itemData,
			loadData,
		}: {
			action: {
				data: {
					id: string;
					permissionKey: string | null;
				};
			};
			event: Event;
			itemData: {
				id: number;
				name: string;
			};
			loadData: () => {};
		}) => {
			if (action?.data?.id === 'delete') {
				event?.preventDefault();

				deleteDSRAction({
					groupId: itemData.id,
					loadData,
					title: sub(
						Liferay.Language.get(
							'delete-digital-sales-room-confirmation-title'
						),
						itemData.name
					),
				});
			}
		},
	};
}
