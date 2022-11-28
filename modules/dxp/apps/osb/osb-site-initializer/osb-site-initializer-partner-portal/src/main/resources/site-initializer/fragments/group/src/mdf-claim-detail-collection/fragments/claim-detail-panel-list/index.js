/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import ClayPanel from '@clayui/panel';
import ClayTable from '@clayui/table';
import React, {useEffect, useState} from 'react';

function getSiteVariables() {
	const currentPath = Liferay.currentURL.split('/');
	const mdfClaimId = +currentPath.at(-1);

	return mdfClaimId;
}

const getIntlNumberFormat = () =>
	new Intl.NumberFormat(Liferay.ThemeDisplay.getBCP47LanguageId(), {
		currency: 'USD',
		style: 'currency',
	});

const Panel = ({activity, children}) => (
	<ClayPanel
		collapsable
		displayTitle={
			<ClayPanel.Title>
				<div className="d-flex">
					<div className="w-100">
						<h4 className="text-neutral-10">{activity?.name}</h4>

						<div className="d-flex justify-content-between">
							<h6 className="text-neutral-6">MDF Claim:</h6>

							<h5 className="justify-content-end text-neutral-10">
								{getIntlNumberFormat().format(
									activity?.totalCost
								)}
							</h5>
						</div>
					</div>
				</div>
			</ClayPanel.Title>
		}
		displayType="secondary"
		showCollapseIcon={true}
	>
		<ClayPanel.Body>{children}</ClayPanel.Body>
	</ClayPanel>
);

const getIconSpriteMap = () => {
	const pathThemeImages = Liferay.ThemeDisplay.getPathThemeImages();
	const spritemap = `${pathThemeImages}/clay/icons.svg`;

	return spritemap;
};

const ReimbursementInvoice = () => {
	const [document, setDocument] = useState();
	const mdfClaimId = getSiteVariables();

	useEffect(() => {
		const getDocuments = async () => {
			// eslint-disable-next-line @liferay/portal/no-global-fetch
			const response = await fetch(
				`/o/c/mdfclaims/${mdfClaimId}/mdfClaimToMdfClaimDocuments`,
				{
					headers: {
						'accept': 'application/json',
						'x-csrf-token': Liferay.authToken,
					},
				}
			);

			if (response.ok) {
				setDocument(await response.json());

				return;
			}

			Liferay.Util.openToast({
				message: 'An unexpected error occured.',
				type: 'danger',
			});
		};

		getDocuments();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const currentDocument = document?.items[0];
	const urlInvoice = `${Liferay.ThemeDisplay.getPortalURL()}${
		currentDocument?.url
	}`;

	return (
		<ClayCard>
			<ClayCard.Body>
				<ClayCard.Description displayType="title">
					Reimbursement Invoice
				</ClayCard.Description>

				<ClayCard.Description displayType="text" truncate={false}>
					<a
						className="text-decoration-none"
						download
						href={currentDocument ? urlInvoice : ''}
					>
						<div className="d-flex document-pdf mt-3">
							<ClayIcon
								className="mr-2 mt-3 text-neutral-5"
								spritemap={getIconSpriteMap()}
								symbol="document-pdf"
							/>

							<div>
								<h5 className="mb-0 text-neutral-9">
									{currentDocument?.fileName}
								</h5>

								<p className="mb-0 text-neutral-9">
									Size:{currentDocument?.fileSize}
								</p>
							</div>
						</div>
					</a>
				</ClayCard.Description>
			</ClayCard.Body>
		</ClayCard>
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

const BudgetBreakdownTable = ({activityId}) => {
	const [budgets, setBudgets] = useState();

	useEffect(() => {
		const getBudgets = async () => {
			// eslint-disable-next-line @liferay/portal/no-global-fetch
			const response = await fetch(
				`/o/c/mdfclaimactivities/${activityId}/mdfClaimActivityToMdfClaimBudgets`,
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

		if (activityId) {
			getBudgets();
		}
	}, [activityId]);

	return (
		<>
			{budgets && (
				<Table
					items={budgets.items.map((budget) => ({
						title: budget.expenseName,
						value: getIntlNumberFormat().format(
							budget.invoiceAmount
						),
					}))}
					title="Budget Breakdown"
				/>
			)}
		</>
	);
};

export default function () {
	const [activities, setActivities] = useState();
	const mdfClaimId = getSiteVariables();

	useEffect(() => {
		const getActivities = async () => {
			// eslint-disable-next-line @liferay/portal/no-global-fetch
			const response = await fetch(
				`/o/c/mdfclaims/${mdfClaimId}/mdfClaimToMdfClaimActivities`,
				{
					headers: {
						'accept': 'application/json',
						'x-csrf-token': Liferay.authToken,
					},
				}
			);

			if (response.ok) {
				setActivities(await response.json());

				return;
			}

			Liferay.Util.openToast({
				message: 'An unexpected error occured.',
				type: 'danger',
			});
		};

		getActivities();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	if (!activities) {
		return <>Loading</>;
	}

	return (
		<div>
			<ReimbursementInvoice />

			{activities?.items.map((activity, index) => (
				<Panel activity={activity} key={index}>
					<BudgetBreakdownTable activityId={activity.id} />
				</Panel>
			))}
		</div>
	);
}
