/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDatePicker from '@clayui/date-picker';
import {ClayCheckbox} from '@clayui/form';
import {useMemo, useState} from 'react';
import {Link} from 'react-router-dom';
import i18n from '../../../../../common/I18n';
import {Button} from '../../../../../common/components';
import Layout from '../../../../../common/containers/setup-forms/Layout';

const now = new Date();
const NAVIGATION_YEARS_RANGE = 2;

const ComplimentaryDate = ({
	infoSelectedKey,
	setInfoSelectedKey,
	setStep,
	urlPreviousPage,
}) => {
	const currentDate = now.toISOString().split('T')[0];
	const [selectedSubscription] = useState(
		infoSelectedKey?.selectedSubscription
	);
	const [expandedOnOrAfter, setExpandedOnOrAfter] = useState(false);
	const [selectedStartDate, setSelectedStartDate] = useState(currentDate);

	const [checkBoxConfirmationTerms, setCheckBoxConfirmationTerms] = useState(
		false
	);

	const {endDate, startDate} = useMemo(() => {
		const inputStartDate = new Date(selectedStartDate);
		const timestamp = inputStartDate.getTime();
		const timezoneOffset = inputStartDate.getTimezoneOffset() * 60000;
		const startDateFormatted = new Date(timestamp + timezoneOffset);
		const startDate = new Date(timestamp + timezoneOffset);
		const endDate = new Date(
			startDateFormatted.setDate(startDateFormatted.getDate() + 30)
		);

		return {
			endDate,
			inputStartDate,
			startDate,
			startDateFormatted,
		};
	}, [selectedStartDate]);

	const updatedSelectedSubscription = useMemo(() => {
		return {
			...selectedSubscription,
			complimentary: true,
			endDate,
			startDate,
		};
	}, [selectedSubscription, endDate, startDate]);

	const hasDateLimitExceeded = useMemo(() => {
		const daysLimit = 29;
		const StartDateLimit = new Date();
		StartDateLimit.setDate(StartDateLimit.getDate() - daysLimit);
		const dateLimitExceeded = startDate < StartDateLimit;

		return dateLimitExceeded;
	}, [startDate]);

	return (
		<div>
			<Layout
				footerProps={{
					footerClass: 'mx-5 mb-2',
					leftButton: (
						<Link to={urlPreviousPage}>
							<Button
								className="btn btn-borderless btn-style-neutral"
								displayType="secondary"
							>
								{i18n.translate('cancel')}
							</Button>
						</Link>
					),
					middleButton: (
						<div>
							<Button
								className="btn btn-secondary mr-3"
								displayType="secundary"
								onClick={() => {
									setInfoSelectedKey(() => ({
										selectedSubscription: {},
									}));
									setStep(0);
								}}
							>
								{i18n.translate('previous')}
							</Button>

							<Button
								disabled={
									!checkBoxConfirmationTerms ||
									!selectedStartDate ||
									hasDateLimitExceeded
								}
								displayType="primary"
								onClick={() => {
									setInfoSelectedKey(
										(previousInfoSelectedKey) => ({
											...previousInfoSelectedKey,
											selectedSubscription: updatedSelectedSubscription,
										})
									);

									setStep(2);
								}}
							>
								{i18n.translate('next')}
							</Button>
						</div>
					),
				}}
				headerProps={{
					headerClass: 'ml-5 mt-4 mb-3',
					helper: i18n.translate(
						'select-the-subscription-and-key-type-you-would-like-to-generate'
					),
					title: i18n.translate('generate-activation-keys'),
				}}
				layoutType="cp-generateKey"
			>
				<div className="h-50 mx-6">
					<h2>{i18n.translate('complimentary')}</h2>

					<p>
						{i18n.translate(
							'you-can-use-this-option-to-generate-complimentary-activation-keys-with-a-duration-of-30-days'
						)}
					</p>

					<h5>{i18n.translate('start-date')}</h5>

					<ClayDatePicker
						dateFormat="yyyy-MM-dd"
						expanded={expandedOnOrAfter}
						onChange={(value, eventType) => {
							setSelectedStartDate(value);

							if (eventType === 'click') {
								setExpandedOnOrAfter(false);
							}
						}}
						onExpandedChange={setExpandedOnOrAfter}
						placeholder={i18n.translate('yyyy-mm-dd')}
						value={selectedStartDate}
						years={{
							end: now.getFullYear() + NAVIGATION_YEARS_RANGE,
							start:
								now.getFullYear() -
								(now.getMonth() === 0 ? 1 : 0),
						}}
					/>

					{hasDateLimitExceeded && (
						<p className="text-danger">
							{i18n.translate('the-start-date-must-be-less-than')}
						</p>
					)}

					<p>
						{i18n.translate(
							'choose-the-date-you-would-like-this-option-to-start'
						)}
					</p>

					<h5 className="mt-5">
						{i18n.translate('confirmation-terms')}
					</h5>

					<div className="d-flex mt-4">
						<div className="pr-2 pt-1">
							<ClayCheckbox
								checked={checkBoxConfirmationTerms}
								id="expiration-checkbox"
								onChange={() =>
									setCheckBoxConfirmationTerms(
										(checkedBoxSubcription) =>
											!checkedBoxSubcription
									)
								}
							/>
						</div>

						<label>
							{i18n.translate(
								'the-requested-activation-key-exceeds-the-purchased-subscriptions-for-this-liferay-project-in-case-of-unauthorized-use-liferay-can-request-financial-compensation-for-breach-of-use'
							)}
						</label>
					</div>
				</div>
			</Layout>
		</div>
	);
};

export default ComplimentaryDate;
