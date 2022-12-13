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
import ClassNames from 'classnames';
import React, {useEffect, useState} from 'react';

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
	const currentDate = new Date();
	const milisecondsPerDay = 1000 * 3600 * 24;

	useEffect(() => {
		const getRenewalsData = async () => {
			// eslint-disable-next-line @liferay/portal/no-global-fetch
			await fetch('/o/c/opportunitysfs?&sort=closeDate:asc', {
				headers: {
					'accept': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
			})
				.then((response) => response.json())
				.then((data) => {
					setData(data);
				})
				.catch(() => {
					Liferay.Util.openToast({
						message: 'An unexpected error occured.',
						type: 'danger',
					});
				});
		};
		getRenewalsData();
	}, []);

	return (
		<Container
			footer={
				<ClayButton
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
			{!data && <ClayLoadingIndicator />}

			<div className="align-items-start d-flex flex-column mt-3">
				{data?.items?.map((item, index) => {
					const expirationInTime =
						new Date(item.closeDate) - currentDate;

					const expirationInDays =
						Math.floor(expirationInTime / milisecondsPerDay) + 1;

					const currentStatusColor = () => {
						if (expirationInDays <= 5) {
							return status[5];
						}
						else if (expirationInDays <= 15) {
							return status[15];
						}
						else if (expirationInDays <= 30) {
							return status[30];
						}
					};

					if (expirationInDays > 0 && expirationInDays <= 30) {
						return (
							<div
								className="align-items-center d-flex flex-row justify-content-center mb-4"
								key={index}
							>
								<div
									className={ClassNames(
										'mr-3 status-bar-vertical',
										currentStatusColor()
									)}
								></div>

								<div>
									<div className="font-weight-semi-bold">
										{item.opportunityName}
									</div>

									<div>
										Expires in &nbsp;
										<span className="font-weight-semi-bold">
											{expirationInDays} days.
										</span>

										<span className="ml-2">
											{item.closeDate}
										</span>
									</div>
								</div>
							</div>
						);
					}
				})}
			</div>
		</Container>
	);
}
