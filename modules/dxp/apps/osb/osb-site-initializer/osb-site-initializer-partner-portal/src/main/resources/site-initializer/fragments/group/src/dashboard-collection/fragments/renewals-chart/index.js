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
import ClassNames from 'classnames';
import React, {useEffect, useState} from 'react';

import Container from '../../common/components/container';
const items = [
	{
		name: 'Rent-A-Centre Texas, L.P',
		status: 30,
	},
	{
		name: 'Advance Stores Co, INC',
		status: 60,
	},
	{
		name: 'Mission of Hope Haiti',
		status: 60,
	},
	{
		name: 'Rite Aid',
		status: 90,
	},
];
const status = {
	30: 'bg-accent-1',
	60: 'bg-warning',
	90: 'bg-success',
};
export default function () {
	const [data, setData] = useState();
	useEffect(() => {
		const getRenewalsData = async () => {
			// eslint-disable-next-line @liferay/portal/no-global-fetch
			await fetch('/o/c/opportunitysfs', {
				headers: {
					'accept': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
			})
				.then((response) => response.json())
				.then((data) => {
					setData(data);
				})
				.catch((error) => {
					console.error('Error:', error);
				});
		};
		getRenewalsData();
	}, []);
	// eslint-disable-next-line no-console
	console.log(data);

	return (
		<Container
			className="renewals-chart-card-height"
			footer={
				<ClayButton
					displayType="secondary"
					onClick={() =>
						Liferay.Util.navigate('https://www.liferay.com/pt/home')
					}
					type="button"
				>
					View all Renewals
				</ClayButton>
			}
			title="Renewals"
		>
			<div className="align-items-start d-flex flex-column mt-3">
				{items.map((item, index) => {
					return (
						<div
							className="align-items-center d-flex flex-row justify-content-center mb-4"
							key={index}
						>
							<div
								className={ClassNames(
									'mr-3 status-bar-vertical',
									status[item.status]
								)}
							></div>

							<div>
								<div className="font-weight-semi-bold">
									{item.name}
								</div>

								<div>
									Expires in &nbsp;
									<span className="font-weight-semi-bold">
										{item.status} days
									</span>
								</div>
							</div>
						</div>
					);
				})}
			</div>
		</Container>
	);
}
