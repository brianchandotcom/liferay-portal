/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {IInternalRenderer} from '@liferay/frontend-data-set-web';
import {openModal} from 'frontend-js-components-web';

import DSRTemplateInitializer from '../components/DSRTemplateInitializer';
import deleteDSRAction from './actions/deleteDSRAction';
import DSRRoomNameRenderer from './cell_renderers/DSRRoomNameRenderer';

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

							if (
								action &&
								action === 'addDigitalSalesRoomTemplate'
							) {
								return openModal({
									containerProps: {
										className: '',
									},
									contentComponent: ({
										closeModal,
									}: {
										closeModal: () => void;
									}) =>
										DSRTemplateInitializer({
											closeModal,
											numberOfSteps: 2,
										}),
									size: 'md',
								});
							}
						},
					};
				}
			),
		},
		customRenderers: {
			tableCell: [
				{
					component: DSRRoomNameRenderer,
					name: 'templateName',
					type: 'internal',
				} as IInternalRenderer,
			],
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
				friendlyUrlPath: string;
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
					model: 'room-template',
					title: Liferay.Language.get(
						'delete-digital-sales-room-template-confirmation-title'
					),
				});
			}
			else if (action?.data?.id === 'edit') {
				event?.preventDefault();

				window.location.href = `/web${itemData.friendlyUrlPath}`;
			}
		},
	};
}
