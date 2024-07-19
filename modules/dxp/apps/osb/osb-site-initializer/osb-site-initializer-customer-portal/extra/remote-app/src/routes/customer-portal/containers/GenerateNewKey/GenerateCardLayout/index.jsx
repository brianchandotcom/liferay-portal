/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayCard from '@clayui/card';
import i18n from '../../../../../common/I18n';
import {FORMAT_DATE_TYPES} from '../../../../../common/utils/constants';
import getDateCustomFormat from '../../../../../common/utils/getDateCustomFormat';
import {getLicenseKeyEndDatesByLicenseType} from '../utils/licenseKeyEndDate';

const GenerateCardLayout = ({
	expirationRenewDate,
	isRenew,
	licenseEntryTypeName,
	selectedKeyData,
}) => {
	const endDate = selectedKeyData?.selectedSubscription?.endDate;
	const isComplimentaryKey = selectedKeyData?.selectedSubscription?.complimentary;
	const licenseEndDate = getLicenseKeyEndDatesByLicenseType(selectedKeyData);
	const startDate = selectedKeyData?.selectedSubscription?.startDate;

	const formatDate = (
		date,
		formatType = FORMAT_DATE_TYPES.day2DMonthSYearN
	) => getDateCustomFormat(date, formatType);

	const currentDate = `${formatDate(startDate)} - ${formatDate(
		licenseEndDate ?? endDate
	)}`;
	const renewalDates = `${formatDate(startDate)} - ${formatDate(
		expirationRenewDate
	)}`;

	const HandleSelectedDates = () => {
		if (selectedKeyData?.selectedSubscription.perpetual) {
			return i18n.translate('not-applicable');
		}

		if (!isComplimentaryKey && isRenew) {
			return renewalDates;
		}

		return currentDate;
	};

	return (
		<ClayCard className="mr-5 position-absolute rounded-xl shadow-none">
			<ClayCard.Body className="bg-brand-primary-lighten-6 cp-info-new-key-card p-4 rounded-xl">
				<ClayCard.Description displayType="title" tag="div">
					<div className="p-2">
						<p className="m-0">{i18n.translate('product')}</p>

						<p className="font-weight-normal">
							{selectedKeyData?.productType}
						</p>

						<p className="m-0">{i18n.translate('version')}</p>

						<p className="font-weight-normal">
							{selectedKeyData?.productVersion}
						</p>

						<p className="m-0">{i18n.translate('key-type')}</p>

						<p className="font-weight-normal">
							{isRenew
								? licenseEntryTypeName
								: selectedKeyData?.licenseEntryType}{' '}
						</p>

						<p className="m-0">
							{i18n.translate('start-date-exp-date')}
						</p>

						<p className="font-weight-normal">
							<HandleSelectedDates />
						</p>

						{!(isComplimentaryKey && isRenew) && (						
							<>
								<p className="m-0">
									{i18n.translate('key-activations-available')}
								</p>

								<p className="font-weight-normal">
									{selectedKeyData?.selectedSubscription?.quantity -
										selectedKeyData?.selectedSubscription
											?.provisionedCount}

									{' of '}

									{selectedKeyData?.selectedSubscription?.quantity}
								</p>

								<p className="m-0">{i18n.translate('instance-size')}</p>

								<p className="font-weight-normal m-0">
									{selectedKeyData?.selectedSubscription
										?.instanceSize || 1}
								</p>
							</>
						)}						
					</div>
				</ClayCard.Description>
			</ClayCard.Body>
		</ClayCard>
	);
};
export default GenerateCardLayout;
