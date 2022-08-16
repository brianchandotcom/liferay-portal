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

import MDFRequestActivity from '../../../../../../../../../../common/interfaces/mdfRequestActivity';
import {Liferay} from '../../../../../../../../../../common/services/liferay';
import Table from '../../../../../Table';
interface IProps {
	values: MDFRequestActivity;
}

const Event = ({values}: IProps) => {
	return (
		<div>
			<Table
				items={[
					{
						title: 'Activity name',
						value: values.name,
					},
					{
						title: 'Type of Activity',
						value:
							values.r_typeActivityToActivities_c_typeActivityId,
					},
					{
						title: 'Tactic',
						value: values.r_tacticToActivities_c_tacticId,
					},
					{
						title: 'Activity Description',
						value: values.description,
					},
					{
						title: 'Venue Name',
						value: values.venueName,
					},
					{
						title: 'Liferay Branding',
						value: values.liferayBranding,
					},
					{
						title: 'Liferay Participation / Requirements',
						value: values.liferayParticipationRequirements,
					},
					{
						title: 'Source and Size of Invitee List',
						value: values.sourceAndSizeOfInviteeList,
					},
					{
						title: 'Activity Promotion',
						value: values.activityPromotion,
					},
					{
						title: 'Start Date',
						value: new Date(values.startDate).toLocaleDateString(
							Liferay.ThemeDisplay.getBCP47LanguageId()
						),
					},
					{
						title: 'End Date',
						value: new Date(values.endDate).toLocaleDateString(
							Liferay.ThemeDisplay.getBCP47LanguageId()
						),
					},
				]}
				title="Campaign Activity"
			/>

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
						value: values.leadGenerated === 'true' ? 'Yes' : 'No',
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
		</div>
	);
};
export default Event;
