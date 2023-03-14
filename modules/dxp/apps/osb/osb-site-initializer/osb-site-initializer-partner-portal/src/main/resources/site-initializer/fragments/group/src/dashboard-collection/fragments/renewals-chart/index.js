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

import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import React, {useEffect, useMemo, useState} from 'react';

import Container from '../../common/components/container';

const status = {
	5: 'bg-accent-1',
	15: 'bg-warning',
	30: 'bg-success',
};

const siteURL = Liferay.ThemeDisplay.getLayoutRelativeURL()
	.split('/')
	.slice(0, 3)
	.join('/');

export default function () {
	const [data, setData] = useState();
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		const getRenewalsData = async () => {
			setLoading(true);
			// eslint-disable-next-line @liferay/portal/no-global-fetch
			await fetch('/o/c/opportunitysfs?pageSize=200&sort=closeDate:asc', {
				headers: {
					'accept': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
			})
				.then((response) => response.json())
				.then((data) => {
					setData(data);
					setLoading(false);
				})
				.catch(() => {
					Liferay.Util.openToast({
						message: 'An unexpected error occured.',
						type: 'danger',
					});
					setLoading(false);
				});
		};
		getRenewalsData();
	}, []);

	const STAGE_REJECTED = 'Rejected';
	const STAGE_CLOSEDLOST = 'Closed Lost';
	const STAGE_DISQUALIFIED = 'Disqualified';
	const STAGE_ROLLED_INTO_ANOTHER_OPPORTUNITY =
		'Rolled into another opportunity';

	const filteredRenewals = useMemo(() => {
		const newArray = [];
		const currentDate = new Date();
		const milisecondsPerDay = 1000 * 3600 * 24;

		data?.items?.map((item) => {
			const expirationInTime = currentDate - new Date(item.closeDate);
			const expirationInDays =
				Math.floor(expirationInTime / milisecondsPerDay) + 1;

			if (
				expirationInDays > 0 &&
				expirationInDays <= 30 &&
				item.stage !== STAGE_REJECTED &&
				item.stage !== STAGE_ROLLED_INTO_ANOTHER_OPPORTUNITY &&
				item.stage !== STAGE_CLOSEDLOST &&
				item.stage !== STAGE_DISQUALIFIED
			) {
				newArray.push({
					closeDate: item.closeDate,
					expirationDays: expirationInDays,
					opportunityName: item.opportunityName,
					stage: item.stage,
				});
			}
		});

		return newArray.slice(0, 4);
	}, [data?.items]);

	const getCurrentStatusColor = (item) => {
		if (item?.expirationDays <= 5) {
			return status[5];
		}
		else if (item?.expirationDays <= 15) {
			return status[15];
		}
		else if (item?.expirationDays <= 30) {
			return status[30];
		}
	};

	return (
		<Container
			footer={
				<ClayButton
					className="border-brand-primary-darken-1 mt-2 text-brand-primary-darken-1"
					displayType="secondary"
					onClick={() =>
						Liferay.Util.navigate(
							`${siteURL}/sales/renewal-opportunities`
						)
					}
					type="button"
				>
					View all Renewals
				</ClayButton>
			}
			title="Renewals"
		>
			{loading && (
				<ClayLoadingIndicator className="mb-10 mt-9" size="md" />
			)}

			{!loading && !data && (
				<h2 className="mb-10 mt-9 text-center">No Data Available</h2>
			)}

			{data && (
				<div className="align-items-start d-flex flex-column justify-content-center mt-3 renewal-chart-card-height">
					{filteredRenewals?.map((item, index) => {
						getCurrentStatusColor(item);

						return (
							<div
								className="align-items-center d-flex flex-row justify-content-center mb-4"
								key={index}
							>
								<div
									className={classNames(
										'mr-3 status-bar-vertical',
										getCurrentStatusColor(item)
									)}
								></div>

								<div>
									<div className="font-weight-semi-bold">
										{item.opportunityName}
									</div>

									<div>
										Expires in &nbsp;
										<span className="font-weight-semi-bold">
											{item.expirationDays} days.
										</span>
										&nbsp;
										<span className="ml-2">
											{item.closeDate}
										</span>
									</div>
								</div>
							</div>
						);
					})}
				</div>
			)}
		</Container>
	);
}
