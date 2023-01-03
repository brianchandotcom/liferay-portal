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

import getQuarter from './getQuarter';

const getDealsByQuarter = ({filteredDealsByType}) => {
	const quarters = ['quarter1', 'quarter2', 'quarter3', 'quarter4'];

	getQuarter();

	return quarters.map((quarter) => {
		const quarterData = {
			approved: filteredDealsByType?.approvedDeals?.filter(
				getQuarter(quarter)
			).length,
			closedwon: filteredDealsByType?.closedWonDeals?.filter(
				getQuarter(quarter)
			).length,
			rejected: filteredDealsByType?.rejectedDeals?.filter(
				getQuarter(quarter)
			).length,
			submited: filteredDealsByType?.submitedDeals?.filter(
				getQuarter(quarter)
			).length,
		};

		return quarterData;
	});
};

export default getDealsByQuarter;
