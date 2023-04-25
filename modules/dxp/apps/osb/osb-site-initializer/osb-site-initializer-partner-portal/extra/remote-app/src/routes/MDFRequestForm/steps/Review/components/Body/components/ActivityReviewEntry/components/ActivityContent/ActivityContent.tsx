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

import Table from '../../../../../../../../../../common/components/Table';
import MDFRequestActivity from '../../../../../../../../../../common/interfaces/mdfRequestActivity';
import getBooleanValue from '../../../../../../../../../../common/utils/getBooleanValue';
import getIntlNumberFormat from '../../../../../../../../../../common/utils/getIntlNumberFormat';

interface IProps {
	mdfRequestActivity: MDFRequestActivity;
}

const ActivityContent = ({mdfRequestActivity}: IProps) => {
	const leadList = [
		{
			title: 'Is a lead list an outcome of this activity?',
			value: getBooleanValue(
				mdfRequestActivity?.activityDescription?.leadGenerated as string
			),
		},
	];

	if (
		getBooleanValue(
			mdfRequestActivity?.activityDescription?.leadGenerated as string
		)
	) {
		leadList.push(
			{
				title: 'Target # of Leads',
				value: mdfRequestActivity?.activityDescription
					?.targetOfLeads as string,
			},
			{
				title: 'Lead Follow Up strategy',
				value: mdfRequestActivity?.activityDescription?.leadFollowUpStrategies?.join(
					', '
				) as string,
			},
			{
				title: 'Details on Lead Follow Up',
				value: mdfRequestActivity?.activityDescription
					?.detailsLeadFollowUp as string,
			}
		);
	}

	return (
		<>
			<Table
				className="bg-brand-primary-lighten-6 border-top table-striped"
				columns={[
					{
						columnKey: 'title',
						label: 'Budget Breakdown',
					},
					{
						columnKey: 'value',
						label: '',
					},
				]}
				rows={mdfRequestActivity.budgets
					.filter((budget) => !budget.removed)
					.map((budget) => ({
						title: budget.expense.name,
						value: getIntlNumberFormat(
							mdfRequestActivity.currency
						).format(budget.cost),
					}))}
			/>

			<Table
				className="bg-brand-primary-lighten-6 border-top table-striped"
				columns={[
					{
						columnKey: 'title',
						label: 'Lead List',
					},
					{
						columnKey: 'value',
						label: '',
					},
				]}
				rows={leadList}
			/>
		</>
	);
};
export default ActivityContent;
