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

import MDFRequest from '../../../../../../../../common/interfaces/mdfRequest';
import Table from '../../../Table';

interface IProps {
	mdfRequest: MDFRequest;
}

const GoalsEntries = ({mdfRequest}: IProps) => (
	<div>
		<Table
			items={[
				{
					title: 'Company Name',
					value: mdfRequest.company.name,
				},
				{
					title: 'Region',
					value: mdfRequest.country.name,
				},
			]}
			title="Partner Summary"
		/>

		<Table
			items={[
				{
					title: 'Provide the name of the campaign',
					value: mdfRequest.overallCampaignName,
				},
				{
					title:
						'Provide a short description of the overall campaign',
					value: mdfRequest.overallCampaignDescription,
				},
				{
					title: 'Liferay business/sales goals',
					value: mdfRequest.liferayBusinessSalesGoals.join('; '),
				},
			]}
			title="Activity Summary"
		/>

		<Table
			items={[
				{
					title: 'Target Market(s)',
					value: mdfRequest.targetMarkets.join('; '),
				},
				{
					title: 'Additional Options',
					value: mdfRequest.additionalOption.name
						? mdfRequest.additionalOption.name
						: 'N/A',
				},
				{
					title: 'Target Audience/Role',
					value: mdfRequest.targetAudienceRoles.join('; '),
				},
			]}
			title="Target Market"
		/>
	</div>
);
export default GoalsEntries;
