/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';

import {TRoomDocumentsStatistics} from '../../../../common/utils/types';

export function UserInvolvedDataRenderer({
	itemData,
}: {
	itemData: TRoomDocumentsStatistics;
}) {
	const {userInvolved = []} = itemData;

	if (!userInvolved.length) {
		return 0;
	}

	return sub(Liferay.Language.get('x-users'), [new Set(userInvolved).size]);
}
