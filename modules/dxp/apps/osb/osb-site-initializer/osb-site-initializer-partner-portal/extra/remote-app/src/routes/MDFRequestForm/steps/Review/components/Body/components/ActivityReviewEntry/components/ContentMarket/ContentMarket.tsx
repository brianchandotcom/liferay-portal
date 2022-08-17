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
import getBooleanValue from '../../../../../../utils/getBooleanValue';
import Table from '../../../../../Table';

interface IProps {
	mdfRequestActivity: MDFRequestActivity;
	tacticName?: string;
	typeOfActivitieName?: string;
}

const ContentMarket = ({
	mdfRequestActivity,
	tacticName,
	typeOfActivitieName,
}: IProps) => (
	<div>
		<Table
			items={[
				{
					title: 'Activity name',
					value: mdfRequestActivity.name,
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
					title:
						'Will this content be gated and have a landing page?',
					value: getBooleanValue(mdfRequestActivity.gatedLandingPage),
				},
				{
					title: 'Primary theme or message of your content',
					value: mdfRequestActivity.primaryThemeOrMessage,
				},

				{
					title: 'Goal of Content',
					value: mdfRequestActivity.goalOfContent,
				},
				{
					title:
						'Are you hiring an outside writer or agency to prepare the content?',
					value: getBooleanValue(
						mdfRequestActivity.hiringOutsideWriterOrAgency
					),
				},
				{
					title: 'Start Date',
					value: new Date(
						mdfRequestActivity.startDate
					).toLocaleDateString(
						Liferay.ThemeDisplay.getBCP47LanguageId()
					),
				},
				{
					title: 'End Date',
					value: new Date(
						mdfRequestActivity.endDate
					).toLocaleDateString(
						Liferay.ThemeDisplay.getBCP47LanguageId()
					),
				},
			]}
			title="Campaign Activity"
		/>
	</div>
);

export default ContentMarket;
