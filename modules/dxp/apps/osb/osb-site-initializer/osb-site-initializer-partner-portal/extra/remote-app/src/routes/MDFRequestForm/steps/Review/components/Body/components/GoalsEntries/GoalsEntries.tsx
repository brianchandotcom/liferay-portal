/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
					value: mdfRequest.liferayBusinessSalesGoals?.includes(
						'Other - Please describe'
					)
						? mdfRequest.liferayBusinessSalesGoalsOther +
						  '; ' +
						  mdfRequest.liferayBusinessSalesGoals
								?.filter(
									(item) => item !== 'Other - Please describe'
								)
								.join('; ')
						: mdfRequest.liferayBusinessSalesGoals?.join('; '),
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
					value: mdfRequest.additionalOption?.name,
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
