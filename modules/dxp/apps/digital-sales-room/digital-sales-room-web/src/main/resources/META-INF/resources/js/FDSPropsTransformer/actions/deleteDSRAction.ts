/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import DigitalSalesRoomService from '../../commons/DigitalSalesRoomService';

export default function deleteDSRAction({
	groupId,
	loadData,
	title,
}: {
	groupId: number;
	loadData: () => void;
	title: string;
}) {
	Liferay.Util.openModal({
		bodyHTML: `<p>${Liferay.Language.get(
			'delete-digital-sales-room-confirmation-body'
		)}</p>`,
		buttons: [
			{
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				type: 'cancel',
			},
			{
				displayType: 'danger',
				label: Liferay.Language.get('delete'),
				onClick: async ({processClose}: {processClose: () => void}) => {
					try {
						const response =
							await DigitalSalesRoomService.deleteDigitalSalesRoom(
								groupId
							);

						if (response.error) {
							throw new Error(response.error);
						}

						Liferay.Util.openToast({
							message: Liferay.Language.get(
								'your-request-completed-successfully'
							),
							type: 'success',
						});

						loadData();
					}
					catch (error) {
						Liferay.Util.openToast({
							message: Liferay.Language.get('an-error-occurred'),
							type: 'danger',
						});
					}
					finally {
						processClose();
					}
				},
			},
		],
		role: 'alert',
		status: 'danger',
		title,
	});
}
