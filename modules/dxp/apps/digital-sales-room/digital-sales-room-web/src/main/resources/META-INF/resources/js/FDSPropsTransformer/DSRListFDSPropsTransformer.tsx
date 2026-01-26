/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {IInternalRenderer} from '@liferay/frontend-data-set-web';
import {openModal} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';

import DSRRoomSaveAsTemplate from '../components/DSRRoomSaveAsTemplate';
import DSRRoomUsersStep from '../components/DSRRoomUsersStep';
import {DSRInitializer, DSRRoomNameRenderer} from '../index';
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

							if (
								action &&
								(action === 'addDigitalSalesRoom' ||
									action ===
										'addDigitalSalesRoomFromTemplate')
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
										DSRInitializer({
											closeModal,
											numberOfSteps:
												action ===
												'addDigitalSalesRoomFromTemplate'
													? 4
													: 3,
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
					name: 'roomName',
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
					title: sub(
						Liferay.Language.get(
							'delete-digital-sales-room-confirmation-title'
						),
						itemData.name
					),
				});
			}
			else if (action?.data?.id === 'edit') {
				event?.preventDefault();

				window.location.href = `/web${itemData.friendlyUrlPath}`;
			}
			else if (action?.data?.id === 'saveAsTemplate') {
				event?.preventDefault();

				return openModal({
					containerProps: {
						className: '',
					},
					contentComponent: ({
						closeModal,
					}: {
						closeModal: () => void;
					}) =>
						DSRRoomSaveAsTemplate({
							closeModal,
							digitalSalesRoomId: itemData.id,
						}),
					size: 'md',
				});
			}
			else if (action?.data?.id === 'share') {
				event?.preventDefault();

				return openModal({
					containerProps: {
						className: '',
					},
					contentComponent: ({
						closeModal,
					}: {
						closeModal: () => void;
					}) =>
						DSRRoomUsersStep({
							closeModal,
							digitalSalesRoomId: itemData.id,
							numberOfSteps: 1,
							setHandleStepSubmit: () => {},
							showHeader: false,
							step: 1,
						}),
					size: 'lg',
				});
			}
		},
	};
}
