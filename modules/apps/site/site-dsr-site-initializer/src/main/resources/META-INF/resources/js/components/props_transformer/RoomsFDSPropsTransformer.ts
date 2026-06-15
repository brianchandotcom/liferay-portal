/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IInternalRenderer, IItemsActions} from '@liferay/frontend-data-set-web';
import {openModal} from 'frontend-js-components-web';

import {openFDSDeleteConfirmationModal} from '../../common/utils/openModalUtil';
import {IRoom} from '../../common/utils/types';
import DuplicateRoom from '../DuplicateRoom';
import RoomInitializer from '../RoomInitializer';
import RoomShare from '../RoomShare';
import RoomNameRenderer from './cell_renderers/RoomNameRenderer';
import RoomStatusRenderer from './cell_renderers/RoomStatusRenderer';
import RoomTrendRenderer from './cell_renderers/RoomTrendRenderer';

export default function RoomsFDSPropsTransformer({
	additionalProps,
	creationMenu,
	itemsActions,
	...otherProps
}: {
	additionalProps: any;
	creationMenu: any;
	itemsActions: IItemsActions[];
	otherProps: any;
}) {
	return {
		...otherProps,
		creationMenu: {
			...creationMenu,
			primaryItems: creationMenu?.primaryItems?.map(
				(item: {data?: {action?: string}}) => {
					return {
						...item,
						onClick() {
							const action = item?.data?.action;

							if (action === 'createDigitalSalesRoom') {
								return openModal({
									containerProps: {
										className: '',
									},
									contentComponent: ({
										closeModal,
									}: {
										closeModal: () => void;
									}) =>
										RoomInitializer({
											closeModal,
											createRedirectURL:
												additionalProps.createRedirectURL ||
												'',
											numberOfSteps: 3,
											siteTemplates:
												additionalProps.siteTemplates ||
												[],
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
					component: RoomNameRenderer,
					name: 'roomNameTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
				{
					component: RoomStatusRenderer,
					name: 'roomStatusTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
				{
					component: RoomTrendRenderer,
					name: 'roomTrendTableCellRenderer',
					type: 'internal',
				} as IInternalRenderer,
			],
		},
		itemsActions: itemsActions.map((action) => {
			if (action?.data?.id === 'delete') {
				return {
					...action,
					className: 'text-danger',
				};
			}

			return action;
		}),
		onActionDropdownItemClick({
			action,
			event,
			itemData,
			loadData,
		}: {
			action: {data: {id: string}};
			event: Event;
			itemData: IRoom;
			loadData: any;
		}) {
			if (action.data.id === 'delete') {
				event?.preventDefault();

				openFDSDeleteConfirmationModal({
					bodyHTML: Liferay.Language.get(
						'delete-digital-sales-room-confirmation-body'
					),
					itemName: itemData.embedded.name,
					loadData,
					title: Liferay.Language.get(
						'delete-digital-sales-room-confirmation-title'
					),
					url: itemData.actions?.delete?.href,
				});
			}
			else if (action.data.id === 'duplicate') {
				event?.preventDefault();

				openModal({
					containerProps: {
						className: '',
					},
					contentComponent: ({
						closeModal,
					}: {
						closeModal: () => void;
					}) =>
						DuplicateRoom({
							closeModal,
							loadData,
							name: itemData.embedded.name,
							roomId: itemData.embedded.id,
							siteId: itemData.embedded.siteId,
						}),
					size: 'lg',
				});
			}
			else if (action.data.id === 'share') {
				event?.preventDefault();

				openModal({
					containerProps: {
						className: '',
					},
					contentComponent: ({
						closeModal,
					}: {
						closeModal: () => void;
					}) =>
						RoomShare({
							closeModal,
							roomId: itemData.embedded.id,
						}),
					size: 'lg',
				});
			}
		},
	};
}
