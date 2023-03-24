/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {MDFColumnKey} from '../../../common/enums/mdfColumnKey';
import MDFClaimDTO from '../../../common/interfaces/dto/mdfClaimDTO';
import LiferayPicklist from '../../../common/interfaces/liferayPicklist';
import getIntlNumberFormat from '../../../common/utils/getIntlNumberFormat';
import getTotalAmountClaimed from './getTotalAmountClaimed';
import getTotalAmountPaid from './getTotalAmountPaid';

export default function getSummaryMDFClaims(
	currency?: LiferayPicklist,
	MdfClaims?: MDFClaimDTO[]
) {
	if (MdfClaims?.length) {
		const totalClaimAmount = getIntlNumberFormat(currency).format(
			getTotalAmountClaimed(MdfClaims)
		);
		const amountPaid = getIntlNumberFormat(currency).format(
			getTotalAmountPaid(MdfClaims)
		);

		if (totalClaimAmount) {
			return {
				[MDFColumnKey.AMOUNT_CLAIMED]: totalClaimAmount,
			};
		}

		if (amountPaid) {
			return {
				[MDFColumnKey.PAID]: amountPaid,
			};
		}
	}
}
