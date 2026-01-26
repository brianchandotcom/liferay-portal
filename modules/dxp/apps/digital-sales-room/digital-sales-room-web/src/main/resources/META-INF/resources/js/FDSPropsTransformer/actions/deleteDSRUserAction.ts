/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import DigitalSalesRoomService from '../../commons/DigitalSalesRoomService';

export default function deleteDSRUserAction({
	digitalSalesRoomId,
	loadData,
	userId,
}: {
	digitalSalesRoomId: number;
	loadData: () => void;
	userId: number;
}) {
	Liferay.Util.openModal({
		bodyHTML: `<p>${Liferay.Language.get(
			'are-you-sure-you-want-to-remove-this-user'
		)}</p>`,
		buttons: [
			{
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				type: 'cancel',
			},
			{
				displayType: 'danger',
				label: Liferay.Language.get('remove'),
				onClick: async ({processClose}: {processClose: () => void}) => {
					try {
						await DigitalSalesRoomService.deleteDigitalSalesRoomUserAccountBrief(
							digitalSalesRoomId,
							userId
						);

						Liferay.Util.openToast({
							message: Liferay.Language.get(
								'user-removed-successfully'
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
		title: Liferay.Language.get('remove-user-from-digital-sales-room'),
	});
}
