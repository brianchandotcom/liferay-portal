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

import getCurrentEndDate from '../../../../../../../common/utils/getCurrentEndDate';

const DNE_YEARS = 100;

const ExpirationDateColumn = ({activationKey}) => {
	const today = new Date();
	const unlimitedLicenseDate = today.setFullYear(
		today.getFullYear() + DNE_YEARS
	);
	if (
		new Date(activationKey.expirationDate) >= new Date(unlimitedLicenseDate)
	) {
		return (
			<p
				className="cp-dxp-activation-key-cell-small font-weight-bold m-0 text-neutral-10"
				title={['This key does not expire']}
			>
				DNE
			</p>
		);
	}

	return (
		<p className="font-weight-bold m-0 text-neutral-10">
			{getCurrentEndDate(activationKey.expirationDate)}
		</p>
	);
};

export {ExpirationDateColumn};
