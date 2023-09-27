/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import {
	getApplicationsById,
	getPolicyByExternalReferenceCode,
} from '../../../../common/services';
import {getQuotesById} from '../../../../common/services/Quote';
import PolicyActiveClaims from './policy-activeclaims-details';
import PolicyDetailsActivities from './policy-activites-details';
import PolicyDetail from './policy-navigator-details';
import PolicySummary from './policy-summary-details';
interface PolicySummary {
	boundDate: Date;
	commission: number;
	currencyType: string;
	dataJSON: JSON;
	email: string;
	endDate: Date;
	phone: string;
	policyCreateDate: Date;
	r_quoteToPolicies_c_raylifeQuoteId: number;
	startDate: Date;
	termPremium: number;
}

const PolicyDetails = () => {
	const [policy, setPolicy] = useState<PolicySummary | any>(null);
	const [application, setApplication] = useState<PolicySummary | any>(null);

	const getApplicationThroughPoliciesERC = async (
		externalReferenceCode: string
	) => {
		const policyElement = await getPolicyByExternalReferenceCode<
			PolicySummary
		>(externalReferenceCode);

		setPolicy(policyElement);

		const quoteID = policyElement?.data?.r_quoteToPolicies_c_raylifeQuoteId;

		const getQuoteThroughPolicy = await getQuotesById(quoteID);

		const applicationId =
			getQuoteThroughPolicy?.data?.items[0]
				?.r_applicationToQuotes_c_raylifeApplicationId;

		const getApplicationThroughQuote = await getApplicationsById(
			applicationId
		);

		const applicationElement = getApplicationThroughQuote?.data?.items[0];

		setApplication(applicationElement);
	};

	useEffect(() => {
		const queryParams = new URLSearchParams(window.location.search);
		const externalReferenceCode = queryParams.get('externalReferenceCode');

		if (externalReferenceCode) {
			getApplicationThroughPoliciesERC(externalReferenceCode);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const summaryData = {
		applicationDataJSON: application?.dataJSON,
		boundDate: policy?.data?.boundDate,
		commission: policy?.data?.commission,
		email: application?.email,
		endDate: policy?.data?.endDate,
		phone: application?.phone,
		policyDataJSON: policy?.data?.dataJSON,
		termPremium: policy?.data?.termPremium,
	};

	return (
		<div className="policy-details-container">
			<div className="policy-detail-content">
				{policy && (
					<div className="row">
						<div className="col-xl-3 d-flex">
							<div className="mb-4 summary-policy-content w-100">
								<PolicySummary summaryData={summaryData} />
							</div>
						</div>

						<div className="col-xl-9 d-flex mb-4">
							<PolicyDetail
								dataJSON={application?.dataJSON}
								email={application?.email}
								phone={application?.phone}
							/>
						</div>
					</div>
				)}

				<PolicyActiveClaims
					dataJSON={policy?.data?.dataJSON}
					id={policy?.data?.id}
				/>

				<PolicyDetailsActivities />
			</div>
		</div>
	);
};

export default PolicyDetails;
