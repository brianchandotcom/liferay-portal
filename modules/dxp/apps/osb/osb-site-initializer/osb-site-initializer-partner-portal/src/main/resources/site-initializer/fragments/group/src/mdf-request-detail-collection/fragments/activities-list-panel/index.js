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

import ClayAlert from '@clayui/alert';
import ClayPanel from '@clayui/panel';
import ClayTable from '@clayui/table';
import React, {useEffect, useState} from 'react';

const currentPath = Liferay.currentURL.split('/');
const mdfRequestId = +currentPath[currentPath.length - 1];

const getIntlNumberFormat = () =>
	new Intl.NumberFormat(Liferay.ThemeDisplay.getBCP47LanguageId(), {
		currency: 'USD',
		style: 'currency',
	});

const getBooleanValue = (value) => (value ? 'Yes' : 'No');

const BudgetBreakdownTable = ({mdfRequestActivityId}) => {
	const [budgets, setBudgets] = useState();

	useEffect(() => {
		const getActivityToBudgets = async () => {
			// eslint-disable-next-line @liferay/portal/no-global-fetch
			const response = await fetch(
				`/o/c/activities/${mdfRequestActivityId}/activityToBudgets`,
				{
					headers: {
						'accept': 'application/json',
						'x-csrf-token': Liferay.authToken,
					},
				}
			);

			if (response.ok) {
				setBudgets(await response.json());

				return;
			}

			Liferay.Util.openToast({
				message: 'An unexpected error occured.',
				type: 'danger',
			});
		};

		if (mdfRequestActivityId) {
			getActivityToBudgets();
		}
	}, [mdfRequestActivityId]);

	return (
		<div>
			{budgets && (
				<Table
					items={budgets.items.map((budget) => ({
						title: budget.expense.name,
						value: getIntlNumberFormat().format(budget.cost),
					}))}
					title="Budget Breakdown"
				/>
			)}
		</div>
	);
};

const LeadListTable = ({mdfRequestActivity}) => (
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

const getDigitalMarketFields = (mdfRequestActivity) => [
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

const getContentMarketFields = (mdfRequestActivity) => [
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
		value: getBooleanValue(mdfRequestActivity.hiringOutsideWriterOrAgency),
	},
];

const getEventFields = (mdfRequestActivity) => [
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

const getMiscellaneousMarketingField = (mdfRequestActivity) => [
	{
		title: 'Marketing activity',
		value: mdfRequestActivity.marketingActivity,
	},
];

const TypeActivityExternalReferenceCode = {
	CONTENT_MARKETING: 'PRMTACT-003',
	DIGITAL_MARKETING: 'PRMTACT-002',
	EVENT: 'PRMTACT-001',
	MISCELLANEOUS_MARKETING: 'PRMTACT-004',
};

const CampaignActivityTable = ({mdfRequestActivity}) => {
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
		[TypeActivityExternalReferenceCode.MISCELLANEOUS_MARKETING]: getMiscellaneousMarketingField(
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
					value:
						mdfRequestActivity
							.r_typeActivityToActivities_c_typeActivity.name,
				},
				{
					title: 'Tactic',
					value:
						mdfRequestActivity.r_tacticToActivities_c_tactic.name,
				},
				...fieldsByTypeActivity[
					mdfRequestActivity.r_typeActivityToActivities_c_typeActivity
						.externalReferenceCode
				],
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
	);
};

const Table = ({items, title}) => (
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
			{items.map((item, index) => (
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

const DATE_FORMAT_OPTION = {
	day: 'numeric',
	month: 'short',
};

const RangeDate = ({endDate, startDate}) => (
	<div className="mb-1 text-neutral-7 text-paragraph-sm">
		{new Date(startDate).toLocaleString(
			Liferay.ThemeDisplay.getBCP47LanguageId(),
			DATE_FORMAT_OPTION
		)}
		&nbsp; - &nbsp;
		{new Date(endDate).toLocaleString(
			Liferay.ThemeDisplay.getBCP47LanguageId(),
			DATE_FORMAT_OPTION
		)}
		, &nbsp;
		{new Date(endDate).getFullYear()}
	</div>
);

const Panel = ({children, mdfRequestActivity}) => (
	<ClayPanel
		className="border-brand-primary-lighten-4"
		collapsable
		displayTitle={
			<ClayPanel.Title className="py-2 text-dark">
				<RangeDate
					endDate={mdfRequestActivity.endDate}
					startDate={mdfRequestActivity.startDate}
				/>

				<h4 className="mb-1">
					{mdfRequestActivity.name} ({mdfRequestActivity.id})
				</h4>
			</ClayPanel.Title>
		}
		showCollapseIcon
		spritemap
	>
		<ClayPanel.Body>{children}</ClayPanel.Body>
	</ClayPanel>
);

export default function () {
	const [activities, setActivities] = useState();
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		const getActivities = async () => {
			// eslint-disable-next-line @liferay/portal/no-global-fetch
			const response = await fetch(
				`/o/c/mdfrequests/${mdfRequestId}/mdfRequestToActivities/?nestedFields=typeActivity,tactic`,
				{
					headers: {
						'accept': 'application/json',
						'x-csrf-token': Liferay.authToken,
					},
				}
			);

			if (response.ok) {
				setActivities(await response.json());

				setLoading(false);

				return;
			}

			Liferay.Util.openToast({
				message: 'An unexpected error occured.',
				type: 'danger',
			});
		};

		if (mdfRequestId) {
			getActivities();
		}
	}, []);

	if (loading) {
		return <>Loading...</>;
	}

	return (
		<div>
			{!activities?.items.length ? (
				<ClayAlert displayType="info" title="Info:">
					No entries were found
				</ClayAlert>
			) : (
				activities?.items.map((mdfRequestActivity, index) => (
					<Panel key={index} mdfRequestActivity={mdfRequestActivity}>
						<CampaignActivityTable
							mdfRequestActivity={mdfRequestActivity}
						/>

						<BudgetBreakdownTable
							mdfRequestActivityId={mdfRequestActivity.id}
						/>

						<LeadListTable
							mdfRequestActivity={mdfRequestActivity}
						/>
					</Panel>
				))
			)}
		</div>
	);
}
