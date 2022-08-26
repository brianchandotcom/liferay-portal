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

import ClayPanel from '@clayui/panel';
import ClayTable from '@clayui/table';
import React, {useEffect, useState} from 'react';

const ID_MDF_REQUEST = 46677;

export function Table({items, title}) {
	return (
		<ClayTable className="border-0">
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell
						className="border-neutral-2 border-top"
						expanded
						headingCell
					>
						{title}
					</ClayTable.Cell>

					<ClayTable.Cell className="border-neutral-2 border-top"></ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				{items?.map((item, index) => (
					<ClayTable.Row key={index}>
						<ClayTable.Cell className="border-0 w-50">
							{item.title}
						</ClayTable.Cell>

						<ClayTable.Cell className="border-0 w-50">
							{item.value}
						</ClayTable.Cell>
					</ClayTable.Row>
				))}
			</ClayTable.Body>
		</ClayTable>
	);
}

export function Panel({activity, children, detail}) {
	return (
		<ClayPanel
			className="border-brand-primary-lighten-4"
			collapsable={detail}
			displayTitle={
				<ClayPanel.Title className="bg-brand-primary-lighten-6 py-2 text-dark">
					<div className="d-flex justify-content-between">
						<div>
							<h5 className="mb-1">{activity.name}</h5>
						</div>
					</div>

					<div className="align-items-center d-flex justify-content-between">
						<div className="font-weight-semi-bold text-neutral-7 text-paragraph-sm">
							MDF Requested:
						</div>

						<h5 className="mr-4">{activity.mdfRequestAmount}</h5>
					</div>
				</ClayPanel.Title>
			}
			displayType="secondary"
			showCollapseIcon={detail}
		>
			{detail && <ClayPanel.Body>{children}</ClayPanel.Body>}
		</ClayPanel>
	);
}

export default function () {
	const [dataActivities, setDataActivities] = useState();

	useEffect(() => {
		const getActivities = async () => {
			// eslint-disable-next-line @liferay/portal/no-global-fetch
			const response = await fetch(
				`/o/c/mdfrequests/${ID_MDF_REQUEST}/mdfRequestToActivities`,
				{
					headers: {
						'accept': 'application/json',
						'x-csrf-token': Liferay.authToken,
					},
				}
			);

			if (response.ok) {
				const mdfRequestActivitydata = await response.json();

				setDataActivities(mdfRequestActivitydata);
			}
		};

		getActivities();
	}, []);

	// const TypeActivityExternalReferenceCode = {
	// 	CONTENT_MARKETING: 'PRMTACT-003',
	// 	DIGITAL_MARKETING: 'PRMTACT-002',
	// 	EVENT: 'PRMTACT-001',
	// 	MISCELLANEOUS_MARKETING: 'PRMTACT-004',
	// };

	// const fieldsByTypeActivity = {
	// 	[TypeActivityExternalReferenceCode.DIGITAL_MARKETING]: 'a',
	// 	[TypeActivityExternalReferenceCode.CONTENT_MARKETING]: 'b',
	// 	[TypeActivityExternalReferenceCode.EVENT]: 'c',
	// 	[TypeActivityExternalReferenceCode.MISCELLANEOUS_MARKETING]: 'd',
	// };

	function campaignActivity(activity) {
		return [
			{
				title: 'Activity name',
				value: activity.name,
			},
			{
				title: 'Type of Activity',
				value: activity.typeActivity,
			},
			{
				title: 'Tactic',
				value: activity.tactic,
			},

			// ...fieldsByTypeActivity[activity.typeActivity],

			{
				title: 'Start Date',
				value: activity.startDate,
			},
			{
				title: 'End Date',
				value: activity.endDate,
			},
		];
	}

	function leadList(activity) {
		return [
			{
				title: 'Is a lead list an outcome of this activity?',
				value: activity.leadGenerated,
			},
			{
				title: 'Target # of Leads',
				value: activity.targetOfLeads,
			},
			{
				title: 'Lead Follow Up strategy',
				value: activity.leadFollowUpStrategies,
			},
			{
				title: 'Details on Lead Follow Up',
				value: activity.detailsLeadFollowUp,
			},
		];
	}

	// eslint-disable-next-line no-console
	console.log(dataActivities);

	return (
		<div>
			{dataActivities?.items.map((activity, index) => (
				<>
					<Panel activity={activity} detail key={index}>
						<Table
							items={campaignActivity(activity)}
							title="Campaign Activity"
						/>

						<Table items={leadList(activity)} title="Lead List" />
					</Panel>
				</>
			))}
		</div>
	);
}
