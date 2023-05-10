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

import i18n from '../../../../../../../common/I18n';
import {FORMAT_DATE_TYPES} from '../../../../../../../common/utils/constants';
import getDateCustomFormat from '../../../../../../../common/utils/getDateCustomFormat';

const DNE_YEARS = 100;

const ExpirationDateColumn = ({activationKey}) => {
	const subscriptionStartDate = new Date(activationKey.startDate);
	const unlimitedLicenseDate = subscriptionStartDate.setFullYear(
		subscriptionStartDate.getFullYear() + DNE_YEARS
	);

	if (
		new Date(activationKey.expirationDate) >= new Date(unlimitedLicenseDate)
	) {
		return (
			<p
				className="cp-activation-key-cell-small font-weight-bold m-0 text-neutral-10"
				title={[i18n.translate('this-key-does-not-expire')]}
			>
				{i18n.translate('dne')}
			</p>
		);
	}

	return (
		<p className="font-weight-bold m-0 text-neutral-10">
			{getDateCustomFormat(
				activationKey.expirationDate,
				FORMAT_DATE_TYPES.day2DMonthSYearN
			)}
		</p>
	);
};

export {ExpirationDateColumn};
