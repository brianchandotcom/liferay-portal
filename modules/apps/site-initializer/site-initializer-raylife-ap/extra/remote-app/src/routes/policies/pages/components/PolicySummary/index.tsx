/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import './index.scss';

import classnames from 'classnames';

const PolicySummary = ({application, policy}: any) => {
	const {data} = policy;

	const applicationDataJSON = application?.dataJSON
		? JSON.parse(application?.dataJSON)
		: {};

	const {driverInfo} = applicationDataJSON;

	const policyEndDate = Date.parse(data?.endDate);

	const differenceOfDays = Math.abs(+policyEndDate - +new Date());

	const renewalDue = Math.floor(differenceOfDays / (1000 * 60 * 60 * 24)) + 1;

	const totalClaimAmount = 1963.58;

	const coverageLimit = '$2,500.00/$100,000.00';

	function dateFormatter(date: string) {
		const formattedDate = new Date(date).toLocaleDateString('en-us', {
			day: '2-digit',
			month: '2-digit',
			timeZone: 'UTC',
			year: 'numeric',
		});

		return formattedDate;
	}

	function valueFormatter(value: number) {
		const dollarValue = Intl.NumberFormat('en-US', {
			currency: 'USD',
			style: 'currency',
		});

		return dollarValue.format(value);
	}

	const handleRedirectToGmail = (email: string) => {
		window.location.href = `mailto:${email}`;
	};

	return (
		<div className="bg-neutral-0 rounded summary-policy-container">
			<div className="policy-summary-title pt-3 px-5">
				<h5 className="m-0">Summary</h5>
			</div>

			<hr />

			<div className="d-flex flex-column pb-5 policy-summary-container px-5">
				<div className="d-flex flex-column mb-4">
					<div className="mb-2 text-neutral-7">Current Period</div>

					{data?.boundDate ? (
						`${dateFormatter(data.boundDate)} - ${dateFormatter(
							data?.endDate
						)}`
					) : (
						<i>No data</i>
					)}
				</div>

				<div className="d-flex flex-column mb-4">
					<div className="mb-2 text-neutral-7">Renewal Due</div>

					{renewalDue ? `${renewalDue} Days` : <i>No data</i>}
				</div>

				<div className="d-flex flex-column mb-4">
					<div className="mb-2 text-neutral-7">Total Premium</div>

					{data?.termPremium ? (
						`${valueFormatter(data.termPremium.toFixed(2))}`
					) : (
						<i>No data</i>
					)}
				</div>

				<div className="d-flex flex-column mb-4">
					<div className="mb-2 text-neutral-7">Commission</div>

					{data?.commission ? (
						`${valueFormatter(data.commission.toFixed(2))}`
					) : (
						<i>No data</i>
					)}
				</div>

				<div className="d-flex flex-column mb-4">
					<div className="mb-2 text-neutral-7">
						Total Claim Amount
					</div>

					{`${valueFormatter(totalClaimAmount)}`}
				</div>

				<div className="d-flex flex-column mb-4">
					<div className="mb-2 text-neutral-7">
						Coverage Limit (Used/Available)
					</div>

					{coverageLimit}
				</div>

				<div className="d-flex flex-column mb-4">
					<div className="mb-2 text-neutral-7">Primary Holder </div>

					{driverInfo?.form[0]?.firstName ? (
						`${driverInfo.form[0].firstName} ${driverInfo.form[0].lastName}`
					) : (
						<i>No data</i>
					)}
				</div>

				<div className="d-flex flex-column mb-4">
					<div className="mb-2 text-neutral-7">Email</div>

					<span
						className={classnames('', {
							'cursor-pointer text-primary': application,
						})}
						onClick={() => handleRedirectToGmail(application.email)}
					>
						{application?.email ? (
							application.email
						) : (
							<i>No data</i>
						)}
					</span>
				</div>

				<div className="d-flex flex-column">
					<div className="mb-2 text-neutral-7">Phone</div>

					{application?.phone ? application.phone : <i>No data</i>}
				</div>
			</div>
		</div>
	);
};

export default PolicySummary;
