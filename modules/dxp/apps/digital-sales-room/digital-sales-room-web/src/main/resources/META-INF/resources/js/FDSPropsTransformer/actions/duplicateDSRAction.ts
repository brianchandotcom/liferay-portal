/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import DigitalSalesRoomService from '../../commons/DigitalSalesRoomService';

export default async function duplicateDSRAction({
	groupId,
	loadData,
}: {
	groupId: number;
	loadData: () => void;
}) {
	try {
		await DigitalSalesRoomService.postDigitalSalesRoomTemplateDigitalSalesRoomTemplate(
			groupId
		);

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
}
