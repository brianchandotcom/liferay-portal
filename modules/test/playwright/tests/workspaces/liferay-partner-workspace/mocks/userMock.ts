/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getRandomInt} from '../../../../utils/getRandomInt';

const namespace = getRandomInt();

export const userCOMMock = {
	emailAddress: 'com' + namespace + '@liferaytest.com',
	name: 'Channel Opperations Manager (COM) ' + namespace,
	password: 'test',
};

export const userPMMock = {
	emailAddress: 'pm' + namespace + '@partnertest.com',
	name: 'Partner Manager (PM) ' + namespace,
	password: 'test',
};
