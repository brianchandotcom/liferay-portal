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

const getDealsByType = ({leads, opportunities}) => {
	return {
		approvedDeals: opportunities?.filter((item) => item.stage === 'Open'),
		closedWonDeals: opportunities?.filter(
			(item) => item.stage === 'Closed Won'
		),
		rejectedDeals:
			leads?.filter((item) => item.leadStatus === 'CAM rejected') ||
			opportunities?.filter((item) => item.stage === 'Rejected'),
		submitedDeals: leads?.filter(
			(item) =>
				item.leadType === 'Partner Prospect Lead (PPL)' &&
				(item.leadStatus !== 'Sales Qualified Opportunity' ||
					item.leadStatus !== 'CAM rejected')
		),
	};
};

export default getDealsByType;
