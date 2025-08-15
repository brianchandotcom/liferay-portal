/* eslint-disable @typescript-eslint/no-unused-vars */

/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {sub} from 'frontend-js-web';
import React, {useContext} from 'react';

import {Context} from '../../Context';
import useFetch from '../../hooks/useFetch';
import {buildQueryString} from '../../utils/buildQueryString';
import Title from '../Title';

type TrafficChannelsData = {
	count: number;
	name: string;
	percentage: number;
};

type TrafficChannelsApiResponse = {
	items: {count: number; name: string}[];
	lastPage: number;
	page: number;
	pageSize: number;
	totalCount: number;
};

function mapData(data: TrafficChannelsApiResponse): TrafficChannelsData[] {
	const {items, totalCount} = data;

	return data
		? items.map((channel) => ({
				count: channel.count,
				name: channel.name,
				percentage: (channel.count / totalCount) * 100,
			}))
		: [];
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
			aria-label={`${Liferay.Language.get('traffic-channel')}: ${Liferay.Language.get(name)}`}
			className="d-flex flex-row py-3 tab-focus traffic-channel-item"
			role="row"
			tabIndex={0}
		>
			<div
				aria-label={Liferay.Language.get(name)}
				className="tab-focus traffic-channel-item__name"
				role="cell"
				style={{width: '35%'}}
			>
				<Text size={3} weight="semi-bold">
					{name}
				</Text>
			</div>

			<div
				aria-label={`${Liferay.Language.get('volume')}: ${volume}`}
				className="d-flex flex-row tab-focus traffic-channel-item__chart"
				role="cell"
				style={{width: '40%'}}
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
			>
				<Text size={3} weight="semi-bold">
					{`${percentage.toFixed(2)}%`}
				</Text>
			</div>
		</div>
	);
};

export function TrafficChannels() {
	const {externalReferenceCode} = useContext(Context);
	const queryParams = buildQueryString({
		externalReferenceCode,
	});

	const endpoint = `/o/analytics-cms-rest/v1.0/object-entry-acquisition-channels${queryParams}`;

	const {data, loading} = useFetch<TrafficChannelsApiResponse>(endpoint);

	if (loading) {
		return <ClayLoadingIndicator data-testid="loading" />;
	}

	if (!data) {
		return null;
	}

	const mappedData = mapData(data);

	return (
		<section
			aria-labelledby="traffic-channels-header"
			className="mt-3 tab-focus"
			tabIndex={0}
		>
			<header
				className="py-2 text-uppercase w-100"
				style={{borderBottom: '1px solid #dfe0e7'}}
			>
				<Title
					value={Liferay.Language.get('views-by-traffic-channels')}
				/>
			</header>

			<section
				aria-labelledby={Liferay.Language.get(
					'this-metric-calculates-the-top-five-traffic-channels-that-generated-the-highest-number-of-views-for-the-asset'
				)}
				className="pt-3"
			>
				<Text color="secondary" size={3} weight="normal">
					{Liferay.Language.get(
						'this-metric-calculates-the-top-five-traffic-channels-that-generated-the-highest-number-of-views-for-the-asset'
					)}
				</Text>
			</section>

			<main
				aria-label={Liferay.Language.get('traffic-channel')}
				className="tab-focus traffic-channels-table"
				role="table"
				tabIndex={0}
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
						aria-label={sub(Liferay.Language.get('x-of-x'), [
							'%',
							Liferay.Language.get('views'),
						])}
						className="d-flex justify-content-end"
						role="columnheader"
						style={{width: '30%'}}
					>
						<Text color="secondary" size={3} weight="semi-bold">
							{sub(Liferay.Language.get('x-of-x'), [
								'%',
								Liferay.Language.get('views'),
							])}
						</Text>
					</div>
				</header>

				{mappedData.map(({count, name, percentage}) => (
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
