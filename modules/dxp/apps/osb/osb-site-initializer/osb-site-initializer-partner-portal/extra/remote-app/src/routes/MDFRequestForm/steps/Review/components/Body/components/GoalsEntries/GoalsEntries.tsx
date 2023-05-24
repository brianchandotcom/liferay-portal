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

import Table from '../../../../../../../../common/components/Table';
import MDFRequest from '../../../../../../../../common/interfaces/mdfRequest';

interface IProps {
	mdfRequest: MDFRequest;
}

interface Item {
	[key: string]: string | undefined | string[];
}
const GoalsEntries = ({mdfRequest}: IProps) => (
	<div>
		<Table<Item>
			className="bg-brand-primary-lighten-6 border-top table-striped"
			columns={[
				{
					columnKey: 'title',
					label: 'Partner Summary',
				},
				{
					columnKey: 'value',
					label: '',
				},
			]}
			rows={[
				{
					title: 'Company Name',
					value: mdfRequest.company?.name,
				},
				{
					title: 'Country',
					value: mdfRequest.partnerCountry?.name,
				},
			]}
		/>

		<Table<Item>
			className="bg-brand-primary-lighten-6 border-top table-striped"
			columns={[
				{
					columnKey: 'title',
					label: 'Activity Summary',
				},
				{
					columnKey: 'value',
					label: '',
					render: (data: string | string[] | undefined) => (
						<p className="text-wrap">{data}</p>
					),
				},
			]}
			rows={[
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
					value: mdfRequest.liferayBusinessSalesGoals?.join('; '),
				},
				{
					title: 'Liferay business/sales goals Other',
					value: mdfRequest.liferayBusinessSalesGoalsOther,
				},
			]}
		/>

		<Table<Item>
			className="bg-brand-primary-lighten-6 border-top table-striped"
			columns={[
				{
					columnKey: 'title',
					label: 'Target Market',
				},
				{
					columnKey: 'value',
					label: '',
				},
			]}
			rows={[
				{
					title: 'Target Market(s)',
					value: mdfRequest.targetMarkets?.join('; '),
				},
				{
					title: 'Additional Options',
					value: mdfRequest.additionalOption?.name
						? mdfRequest.additionalOption?.name
						: 'N/A',
				},
				{
					title: 'Target Audience/Role',
					value: mdfRequest.targetAudienceRoles?.join('; '),
				},
			]}
		/>
	</div>
);
export default GoalsEntries;
