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

import MDFClaimDTO from '../../../common/interfaces/dto/mdfClaimDTO';

export default function getTotalAmountPaid(mdfClaims: MDFClaimDTO[]) {
	return mdfClaims.reduce(
		(previousValue: number, currentValue: MDFClaimDTO) => {
			const sumAmount = currentValue.claimPaid
				? currentValue.claimPaid
				: 0;

			const totalAmountClaimed = previousValue + sumAmount;

			return totalAmountClaimed;
		},
		0
	);
}
