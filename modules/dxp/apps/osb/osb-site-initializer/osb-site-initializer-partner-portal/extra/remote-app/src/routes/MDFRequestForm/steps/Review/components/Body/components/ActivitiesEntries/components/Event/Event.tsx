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
	tacticName?: string;
	typeOfActivitieName?: string;
	values: MDFRequestActivity;
}

const Event = ({tacticName, typeOfActivitieName, values}: IProps) => (
	<div>
		<Table
			items={[
				{
					title: 'Activity name',
					value: values.name,
				},
				{
					title: 'Type of Activity',
					value: typeOfActivitieName,
				},
				{
					title: 'Tactic',
					value: tacticName,
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
	</div>
);

export default Event;
