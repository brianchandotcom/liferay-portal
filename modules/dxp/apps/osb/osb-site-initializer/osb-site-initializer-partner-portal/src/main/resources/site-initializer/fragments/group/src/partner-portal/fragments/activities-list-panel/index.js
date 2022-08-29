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

const ID_MDF_REQUEST = 87707;

export function getIntlNumberFormat() {
	return new Intl.NumberFormat(Liferay.ThemeDisplay.getBCP47LanguageId(), {
		currency: 'USD',
		style: 'currency',
	});
}

export function getBooleanValue(value) {
	return value === 'true' ? 'Yes' : 'No';
}

export function BudgetTable({mdfRequestActivity}) {
	const [budgets, setBudgets] = useState();

	useEffect(() => {
		const getActivityToBudgets = async () => {
			// eslint-disable-next-line @liferay/portal/no-global-fetch
			const response = await fetch(
				`/o/c/activities/${mdfRequestActivity.id}/activityToBudgets`,
				{
					headers: {
						'accept': 'application/json',
						'x-csrf-token': Liferay.authToken,
					},
				}
			);

			if (response.ok) {
				const budgetsResponse = await response.json();

				setBudgets(budgetsResponse);
			}
		};
		getActivityToBudgets();
	}, [mdfRequestActivity.id]);

	return (
		<Table
			items={budgets?.items.map((budget) => ({
				title: budget.expense.name,
				value: getIntlNumberFormat().format(budget.cost),
			}))}
			title="Budget Breakdown"
		/>
	);
}

export function LeadTable({mdfRequestActivity}) {
	return (
		<Table
			items={[
				{
					title: 'Is a lead list an outcome of this activity?',
					value: getBooleanValue(mdfRequestActivity.leadGenerated),
				},
				{
					title: 'Target # of Leads',
					value: mdfRequestActivity.targetOfLeads,
				},
				{
					title: 'Lead Follow Up strategy',
					value: mdfRequestActivity.leadFollowUpStrategies,
				},
				{
					title: 'Details on Lead Follow Up',
					value: mdfRequestActivity.detailsLeadFollowUp,
				},
			]}
			title="Lead List"
		/>
	);
}

export function getDigitalMarketFields(mdfRequestActivity) {
	return [
		{
			title: 'Overall message, content or CTA',
			value: mdfRequestActivity.overallMessageContentCTA,
		},
		{
			title: 'Specific sites to be used',
			value: mdfRequestActivity.specificSites,
		},
		{
			title: 'Keywords for PPC campaigns',
			value: mdfRequestActivity.keywordsForPPCCampaigns,
		},
		{
			title: 'Ad (any size/type)',
			value: mdfRequestActivity.ad,
		},
		{
			title: 'Do you require any assets from Liferay?',
			value: getBooleanValue(mdfRequestActivity.assetsLiferayRequired),
		},
		{
			title: 'How will the Liferay brand be used in the campaign?',
			value: mdfRequestActivity.howLiferayBrandUsed,
		},
	];
}

export function getContentMarketFields(mdfRequestActivity) {
	return [
		{
			title: 'Will this content be gated and have a landing page?',
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
	];
}

export function getEventFields(mdfRequestActivity) {
	return [
		{
			title: 'Activity Description',
			value: mdfRequestActivity.description,
		},
		{
			title: 'Venue Name',
			value: mdfRequestActivity.venueName,
		},
		{
			title: 'Liferay Branding',
			value: mdfRequestActivity.liferayBranding,
		},
		{
			title: 'Liferay Participation / Requirements',
			value: mdfRequestActivity.liferayParticipationRequirements,
		},
		{
			title: 'Source and Size of Invitee List',
			value: mdfRequestActivity.sourceAndSizeOfInviteeList,
		},
		{
			title: 'Activity Promotion',
			value: mdfRequestActivity.activityPromotion,
		},
	];
}

export function getMiscellaneousMarketing(mdfRequestActivity) {
	return [
		{
			title: 'Marketing activity',
			value: mdfRequestActivity.marketingActivity,
		},
	];
}

export function CampaignTable({mdfRequestActivity}) {
	const [typeOfActivity, setTypeOfActivity] = useState({
		externalReferenceCode: '',
		name: '',
	});
	const [tacticName, setTacticName] = useState();

	useEffect(() => {
		const getTypeOfActivity = async () => {
			// eslint-disable-next-line @liferay/portal/no-global-fetch
			const response = await fetch(
				`/o/c/typeactivities/${mdfRequestActivity.r_typeActivityToActivities_c_typeActivityId}`,
				{
					headers: {
						'accept': 'application/json',
						'x-csrf-token': Liferay.authToken,
					},
				}
			);

			if (response.ok) {
				const typeOfActivityResponse = await response.json();

				setTypeOfActivity({
					externalReferenceCode:
						typeOfActivityResponse.externalReferenceCode,
					name: typeOfActivityResponse.name,
				});
			}
		};
		getTypeOfActivity();
	}, [mdfRequestActivity.r_typeActivityToActivities_c_typeActivityId]);

	useEffect(() => {
		const getTactic = async () => {
			// eslint-disable-next-line @liferay/portal/no-global-fetch
			const response = await fetch(
				`/o/c/tactics/${mdfRequestActivity.r_tacticToActivities_c_tacticId}`,
				{
					headers: {
						'accept': 'application/json',
						'x-csrf-token': Liferay.authToken,
					},
				}
			);

			if (response.ok) {
				const tacticResponse = await response.json();

				setTacticName(tacticResponse.name);
			}
		};
		getTactic();
	}, [mdfRequestActivity.r_tacticToActivities_c_tacticId]);

	const TypeActivityExternalReferenceCode = {
		CONTENT_MARKETING: 'PRMTACT-003',
		DIGITAL_MARKETING: 'PRMTACT-002',
		EVENT: 'PRMTACT-001',
		MISCELLANEOUS_MARKETING: 'PRMTACT-004',
	};

	const fieldsByTypeActivity = {
		[TypeActivityExternalReferenceCode.DIGITAL_MARKETING]: getDigitalMarketFields(
			mdfRequestActivity
		),
		[TypeActivityExternalReferenceCode.CONTENT_MARKETING]: getContentMarketFields(
			mdfRequestActivity
		),
		[TypeActivityExternalReferenceCode.EVENT]: getEventFields(
			mdfRequestActivity
		),
		[TypeActivityExternalReferenceCode.MISCELLANEOUS_MARKETING]: getMiscellaneousMarketing(
			mdfRequestActivity
		),
	};

	return (
		<Table
			items={[
				{
					title: 'Activity name',
					value: mdfRequestActivity.name,
				},
				{
					title: 'Type of Activity',
					value: typeOfActivity.name,
				},
				{
					title: 'Tactic',
					value: tacticName,
				},
				...(typeOfActivity &&
					typeOfActivity.externalReferenceCode &&
					fieldsByTypeActivity[typeOfActivity.externalReferenceCode]),
				{
					title: 'Start Date',
					value:
						mdfRequestActivity.startDate &&
						new Date(
							mdfRequestActivity.startDate
						).toLocaleDateString(
							Liferay.ThemeDisplay.getBCP47LanguageId()
						),
				},
				{
					title: 'End Date',
					value:
						mdfRequestActivity.endDate &&
						new Date(
							mdfRequestActivity.startDate
						).toLocaleDateString(
							Liferay.ThemeDisplay.getBCP47LanguageId()
						),
				},
			]}
			title="Campaign Activity"
		/>
	);
}

export function Table({items, title}) {
	return (
		<ClayTable className="bg-brand-primary-lighten-6 border-0 table-striped">
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell
						className="border-neutral-2 border-top rounded-0 w-50"
						expanded
						headingCell
					>
						<p className="mt-4 text-neutral-10">{title}</p>
					</ClayTable.Cell>

					<ClayTable.Cell className="border-neutral-2 border-top rounded-0 w-50"></ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				{items?.map((item, index) => (
					<ClayTable.Row key={index}>
						<ClayTable.Cell className="border-0 w-50">
							<p className="text-neutral-10">{item.title}</p>
						</ClayTable.Cell>

						<ClayTable.Cell className="border-0 w-50">
							<p className="text-neutral-10">{item.value}</p>
						</ClayTable.Cell>
					</ClayTable.Row>
				))}
			</ClayTable.Body>
		</ClayTable>
	);
}

export function RangeDate({endDate, startDate}) {
	const dateOptions = {
		day: 'numeric',
		month: 'short',
	};

	return (
		<div className="mb-1 text-neutral-7 text-paragraph-sm">
			{new Date(startDate).toLocaleString(
				Liferay.ThemeDisplay.getBCP47LanguageId(),
				dateOptions
			)}
			&nbsp; - &nbsp;
			{new Date(endDate).toLocaleString(
				Liferay.ThemeDisplay.getBCP47LanguageId(),
				dateOptions
			)}
			, &nbsp;
			{new Date(endDate).getFullYear()}
		</div>
	);
}

export function Panel({children, mdfRequestActivity}) {
	return (
		<ClayPanel
			className="border-brand-primary-lighten-4"
			collapsable
			displayTitle={
				<ClayPanel.Title className="py-2 text-dark">
					<RangeDate
						endDate={
							mdfRequestActivity.endDate &&
							new Date(mdfRequestActivity.endDate)
						}
						startDate={
							mdfRequestActivity.startDate &&
							new Date(mdfRequestActivity.startDate)
						}
					/>

					<div>
						<h5 className="mb-1">{mdfRequestActivity.name}</h5>
					</div>

					<div className="font-weight-semi-bold mt-1 text-neutral-7 text-paragraph-sm">
						Claim Status:
					</div>

					<div className="font-weight-semi-bold mt-1 text-neutral-7 text-paragraph-sm">
						Activity Status:
					</div>
				</ClayPanel.Title>
			}
			showCollapseIcon
			spritemap
		>
			<ClayPanel.Body>{children}</ClayPanel.Body>
		</ClayPanel>
	);
}

export default function () {
	const [activities, setActivities] = useState();

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
				const mdfRequestToActivity = await response.json();

				setActivities(mdfRequestToActivity);
			}
		};

		getActivities();
	}, []);

	return (
		<div>
			{activities?.items.map((mdfRequestActivity) => (
				<>
					<Panel mdfRequestActivity={mdfRequestActivity}>
						<CampaignTable
							mdfRequestActivity={mdfRequestActivity}
						/>

						<BudgetTable mdfRequestActivity={mdfRequestActivity} />

						<LeadTable mdfRequestActivity={mdfRequestActivity} />
					</Panel>
				</>
			))}
		</div>
	);
}
