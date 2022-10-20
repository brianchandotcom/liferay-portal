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

import classNames from 'classnames';
import {useEffect, useState} from 'react';

import {getPolicies} from '../../../../../../common/services';
import {getApplications} from '../../../../../../common/services/Application';
import {getQuotes} from '../../../../../../common/services/Quote';

type externalReferenceCodeType = {
	externalReferenceCode: string;
};

const QuotedSummary = ({externalReferenceCode}: externalReferenceCodeType) => {
	const [policyStartDate, setPolicyStartDate] = useState<string>('');
	const [policyEndDate, setPolicyEndDate] = useState<string>('');
	const [totalPremium, setTotalPremium] = useState<number>(0);
	const [productName, setProductName] = useState<string>('');
	const [bodilyInjury, setBodilyInjury] = useState<string>('');
	const [medicalPayments, setMedicalPayments] = useState<string>('');
	const [propertyDamage, setPropertyDamage] = useState<string>('');
	const [motoristBodilyInjury, setMotoristBodilyInjury] = useState<string>(
		''
	);
	const [motoristPropertyDamage, setMotoristPropertyDamage] = useState<
		string
	>('');

	const PARAMETERS_APPLICATIONS = {
		filter: `contains(externalReferenceCode,'${externalReferenceCode}')`,
		pageSize: '0',
	};

	const PARAMETERS_GET_ALL_ITEMS = {
		pageSize: '0',
	};

	const getPoliciesThroughApplicationERC = async () => {
		const applicationRequest = await getApplications(
			PARAMETERS_APPLICATIONS
		);
		const applicationData = applicationRequest?.data?.items[0];
		const applicationId = applicationData?.id;
		const applicationDataJSON = applicationData?.dataJSON
			? JSON.parse(applicationData?.dataJSON)
			: {};
		const applicationDataForm = applicationDataJSON?.coverage?.form;

		setProductName(applicationData?.productName);
		setBodilyInjury(applicationDataForm?.bodilyInjury);
		setMedicalPayments(applicationDataForm?.medical);
		setPropertyDamage(applicationDataForm?.propertyDamage);
		setMotoristBodilyInjury(
			applicationDataForm?.uninsuredOrUnderinsuredMBI
		);
		setMotoristPropertyDamage(
			applicationDataForm?.uninsuredOrUnderinsuredMPD
		);

		const quoteRequest = await getQuotes(PARAMETERS_GET_ALL_ITEMS);
		const quoteItems = quoteRequest?.data?.items;
		const quoteItem = quoteItems?.find(
			(item: any) =>
				item?.r_applicationToQuotes_c_raylifeApplicationId ===
				applicationId
		);
		const quoteId = quoteItem.id;

		const policiesRequest = await getPolicies(PARAMETERS_GET_ALL_ITEMS);
		const policiesItems = policiesRequest?.data?.items;
		const policyItem = policiesItems?.find(
			(item: any) => item?.r_quoteToPolicies_c_raylifeQuoteId === quoteId
		);

		const startDate: string = policyItem.startDate;
		const endDate: string = policyItem.endDate;
		const termPremium: number = policyItem.termPremium.toFixed(2);

		function handleDateFormatter(date: string) {
			const formattedDate = new Date(date).toLocaleDateString('en-us', {
				day: '2-digit',
				month: '2-digit',
				timeZone: 'UTC',
				year: 'numeric',
			});

			return formattedDate;
		}

		const formattedStartDate = handleDateFormatter(startDate);
		const formattedEndDate = handleDateFormatter(endDate);

		setPolicyStartDate(formattedStartDate);
		setPolicyEndDate(formattedEndDate);
		setTotalPremium(termPremium);
	};

	useEffect(() => {
		getPoliciesThroughApplicationERC();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	function handleValuesFormatter(value: string) {
		if (value === bodilyInjury || motoristBodilyInjury) {
			return value.replaceAll(',000', ',000.00');
		}

		return value.replace(',000', ',000.00');
	}

	return (
		<div className="bg-neutral-0 quoted-summary-container rounded">
			<div className="pt-3 px-5 quoted-summary-title">
				<h5 className="m-0">Quoted Summary</h5>
			</div>

			<hr />

			<div className="action-detail-content px-5">
				<p>
					The following quote has been submitted to the applicant for
					review.
				</p>
			</div>

			<div className="justify-content-between px-5 quoted-summary-content">
				<div className="col-md-4 d-flex flex-column mb-3">
					<label>Policy Period</label>

					{policyStartDate && policyEndDate ? (
						<span className="font-weight-bold">
							{`${policyStartDate} - ${policyEndDate}`}
						</span>
					) : (
						<i>No data</i>
					)}
				</div>

				<div className="col-md-4 d-flex flex-column mb-3">
					<label>Total Premium</label>

					{totalPremium ? (
						<span className="font-weight-bold">{`$${totalPremium}`}</span>
					) : (
						<i>No data</i>
					)}
				</div>

				<div className="col-md-4 d-flex flex-column mb-3">
					<label>Coverage Limit</label>

					<span className="font-weight-bold">$100,000.00</span>
				</div>
			</div>

			<div
				className={classNames('quoted-summary-content px-5', {
					'd-block': productName === 'Auto',
					'd-none': productName !== 'Auto',
				})}
			>
				<p>For Drivers & Passengers</p>

				<div className="justify-content-between quoted-summary-content">
					<div className="col-md-4 d-flex flex-column mb-3 mx-0">
						<label>Bodily Injury Liability</label>

						{bodilyInjury ? (
							<span className="font-weight-bold">
								{handleValuesFormatter(bodilyInjury)}
							</span>
						) : (
							<i>No data</i>
						)}
					</div>

					<div className="col-md-4 d-flex flex-column mb-3">
						<label>Property Damage Liability</label>

						{propertyDamage ? (
							<span className="font-weight-bold">
								{handleValuesFormatter(propertyDamage)}
							</span>
						) : (
							<i>No data</i>
						)}
					</div>

					<div className="col-md-4 d-flex flex-column mb-3">
						<label>
							Uninsured/Underinsured Motorist Bodily Injury
						</label>

						{motoristBodilyInjury ? (
							<span className="font-weight-bold">
								{handleValuesFormatter(motoristBodilyInjury)}
							</span>
						) : (
							<i>No data</i>
						)}
					</div>
				</div>

				<div className="justify-content-between quoted-summary-content">
					<div className="col-md-4 d-flex flex-column mb-3">
						<label>Medial Payments</label>

						{medicalPayments ? (
							<span className="font-weight-bold">
								{handleValuesFormatter(medicalPayments)}
							</span>
						) : (
							<i>No data</i>
						)}
					</div>

					<div className="col-md-8 d-flex flex-column mb-3">
						<label>
							Uninsured/Underinsured Motorist Property Damage
						</label>

						{motoristPropertyDamage ? (
							<span className="font-weight-bold">
								{handleValuesFormatter(motoristPropertyDamage)}
							</span>
						) : (
							<i>No data</i>
						)}
					</div>
				</div>
			</div>

			<div className="d-flex pb-5 px-5">
				<span className="font-weight-bold pr-2 text-danger">PDF</span>

				<span>ABD-123-EDF</span>
			</div>
		</div>
	);
};

export default QuotedSummary;
