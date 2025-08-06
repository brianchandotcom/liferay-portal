/* eslint-disable @typescript-eslint/no-unused-vars */

/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import React, {useContext, useEffect, useState} from 'react';

import {Context} from '../../Context';
import ApiHelper from '../../apis/ApiHelper';
import {buildQueryString} from '../../utils/buildQueryString';

import './traffic_channel.scss';

type TrafficChannelsData = {
	count: number;
	name: string;
	percentage: number;
};

type TrafficChannelsApiResponse = Array<{
	count: number;
	name: string;
}>;

function mapData(
	data: TrafficChannelsApiResponse | null
): TrafficChannelsData[] {
	if (!data) {
		return [];
	}

	const totalCount = data.reduce((total, value) => {
		return total + value.count;
	}, 0);

	return data
		? data.map((channel) => ({
				count: channel.count,
				name: channel.name,
				percentage: (channel.count / totalCount) * 100,
			}))
		: [];
}

async function fetchTrafficChannelsData(
	externalReferenceCode: string,
	groupId?: string,
	rangeKey?: string
): Promise<TrafficChannelsApiResponse | null> {
	const mockedData: TrafficChannelsApiResponse = [
		{
			count: 10,
			name: 'Direct',
		},
		{
			count: 20,
			name: 'Social',
		},
		{
			count: 15,
			name: 'Referrals',
		},
		{
			count: 10,
			name: 'Paid Search',
		},
		{
			count: 30,
			name: 'Email',
		},
		{
			count: 35,
			name: 'Others',
		},
	];

	return mockedData;
}

const TrafficChannelsEntry = ({
	name,
	percentage,
	volume,
}: {
	name: string;
	percentage: number;
	volume: number;
}) => {
	return (
		<div
			aria-label={`Traffic channel: ${name}`}
			className="d-flex flex-row py-3 traffic-channel-item"
			role="row"
		>
			<div
				aria-label={`Channel name: ${name}`}
				className="tab-focus traffic-channel-item__name"
				role="cell"
				style={{width: '35%'}}
				tabIndex={0}
			>
				<Text size={3} weight="semi-bold">
					{name}
				</Text>
			</div>

			<div
				aria-label={`Volume: ${volume}, Percentage: ${percentage.toFixed(
					2
				)}%`}
				className="d-flex flex-row tab-focus traffic-channel-item__chart"
				role="cell"
				style={{width: '40%'}}
				tabIndex={0}
			>
				<div
					aria-hidden="true"
					className="traffic-channel-item__chart__bar"
					style={{width: `${percentage}%`}}
				/>

				<div className="traffic-channel-item__chart__value">
					<Text size={3} weight="semi-bold">
						{volume}
					</Text>
				</div>
			</div>

			<div
				aria-label={`Percentage: ${percentage.toFixed(2)}%`}
				className="d-flex justify-content-end tab-focus traffic-channel-item__percentage"
				role="cell"
				style={{width: '25%'}}
				tabIndex={0}
			>
				<Text size={3} weight="semi-bold">
					{`${percentage.toFixed(2)}%`}
				</Text>
			</div>
		</div>
	);
};

export function TrafficChannels() {
	const [trafficData, setTrafficData] = useState<TrafficChannelsData[]>([]);

	const {externalReferenceCode} = useContext(Context);

	useEffect(() => {
		async function fetchData() {
			const data = await fetchTrafficChannelsData(externalReferenceCode);

			if (data) {
				const mappedData = mapData(data);
				setTrafficData(mappedData);
			}
		}

		fetchData();
	}, [externalReferenceCode]);

	return (
		<section aria-labelledby="traffic-channels-header" className="mt-3">
			<header
				className="py-2 text-uppercase w-100"
				style={{borderBottom: '1px solid #dfe0e7'}}
			>
				<Text color="secondary" size={3} weight="semi-bold">
					{Liferay.Language.get('views-by-traffic-channels')}
				</Text>
			</header>

			<section
				aria-labelledby="top-five-traffic-channels"
				className="pt-3"
			>
				<Text color="secondary" size={3} weight="normal">
					{Liferay.Language.get('top-five-traffic-channels')}
				</Text>
			</section>

			<main
				aria-label={Liferay.Language.get('traffic-channels-table')}
				className="traffic-channels-table"
				role="table"
			>
				<header
					className="d-flex flex-row justify-content-between py-3"
					role="row"
				>
					<div
						aria-label={Liferay.Language.get('traffic-channel')}
						role="columnheader"
						style={{width: '35%'}}
					>
						<Text color="secondary" size={3} weight="semi-bold">
							{Liferay.Language.get('traffic-channel')}
						</Text>
					</div>

					<div
						aria-label={Liferay.Language.get('views')}
						role="columnheader"
						style={{width: '35%'}}
					>
						<Text color="secondary" size={3} weight="semi-bold">
							{Liferay.Language.get('views')}
						</Text>
					</div>

					<div
						aria-label={`${Liferay.Language.get('%-of')} ${Liferay.Language.get('views')}`}
						className="d-flex justify-content-end"
						role="columnheader"
						style={{width: '30%'}}
					>
						<Text color="secondary" size={3} weight="semi-bold">
							{`${Liferay.Language.get('%-of')} ${Liferay.Language.get('views')}`}
						</Text>
					</div>
				</header>

				{trafficData.map(({count, name, percentage}) => (
					<TrafficChannelsEntry
						key={name}
						name={name}
						percentage={percentage}
						volume={count}
					/>
				))}
			</main>
		</section>
	);
}
