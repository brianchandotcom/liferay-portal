/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getRandomInt} from '../../../../utils/getRandomInt';
import {TPartnerAccount} from '../types/account';

const namespace = getRandomInt();

export const accountPlatinumMock: TPartnerAccount = {
	currency: 'USD',
	name: 'Account Platinum ' + namespace,
	type: 'business',
};

export const accountGoldMock: TPartnerAccount = {
	currency: 'USD',
	externalReferenceCode: '0017000000b3ScRAAU',
	level: 'Gold',
	name: 'Account Gold ' + namespace,
	partnerCountry: 'US',
	type: 'business',
};

export const accountSilverMock: TPartnerAccount = {
	currency: 'USD',
	externalReferenceCode: '0017000000b3ScRAAU',
	level: 'Silver',
	name: 'Account Silver ' + namespace,
	partnerCountry: 'US',
	type: 'business',
};
