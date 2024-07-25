/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import moment from 'moment';
import {getRandomInt} from '../../../../utils/getRandomInt';
import {
	EMDFRequestActivityExpenseTypes,
	EMDFRequestActivityTactics,
	EMDFRequestActivityTypes,
	EMDFRequestLiferayBusinessSalesGoals,
	EMDFRequestTargetAudienceRoles,
	EMDFRequestTargetMarkets,
} from './constants';
import { TAccount } from '../types/account';
import { TUserAccount } from '../types/user';
import { TMDFRequest } from '../types/mdf';
import { mdfRequestMock } from '../mocks/mdfMock';

const namespace = getRandomInt();

export function generateMDFRequestData(parnterAccount: TAccount, userAccount: TUserAccount): TMDFRequest {
	let mdfRequest = mdfRequestMock;

	mdfRequest.activities[0].endDate = moment().add(2, 'days').format('YYYY-MM-DD');
	mdfRequest.activities[0].startDate = moment().add(1, 'days').format('YYYY-MM-DD');
	mdfRequest.goals.companyName = parnterAccount.name;
	mdfRequest.submitDate = moment().format('YYYY-MM-DD');
	mdfRequest.userId = Number(userAccount.id);
	
	return mdfRequest;
}
