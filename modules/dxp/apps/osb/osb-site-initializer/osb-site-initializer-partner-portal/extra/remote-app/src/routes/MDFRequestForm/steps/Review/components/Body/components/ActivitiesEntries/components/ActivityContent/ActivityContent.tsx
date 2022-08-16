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

import React from 'react';

import MDFRequestActivity from '../../../../../../../../../../common/interfaces/mdfRequestActivity';
import GetBooleanValue from '../../../../../../utils/GetBooleanValue';
import Table from '../../../../../Table';

interface IProps {
	values: MDFRequestActivity;
}

const ActivityContent = ({values}: IProps) => (
	<>
		<Table
			items={values.budgets.map((budget) => ({
				title: budget.expense?.name,
				value: '$' + budget.cost,
			}))}
			title="Budget Breakdown"
		/>

		<Table
			items={[
				{
					title: 'Is a lead list an outcome of this activity?',
					value: GetBooleanValue(values.leadGenerated),
				},
				{
					title: 'Target # of Leads',
					value: values.targetofLeads,
				},
				{
					title: 'Lead Follow Up strategy',
					value: values.leadFollowUpStrategies.join(', '),
				},
				{
					title: 'Details on Lead Follow Up',
					value: values.detailsLeadFollowUp,
				},
			]}
			title="Lead List"
		/>
	</>
);
export default ActivityContent;
