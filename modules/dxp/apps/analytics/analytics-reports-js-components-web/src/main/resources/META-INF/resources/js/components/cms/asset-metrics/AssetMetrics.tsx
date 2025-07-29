/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropdown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React, {useContext, useState} from 'react';

import {Context} from '../../../Context';
import {MetricName, MetricType} from '../../../types/global';
import {AssetMetricsChart} from './AssetMetricsChart';
import {AssetMetricsTableView} from './AssetMetricsTableView';

// TODO: Remove it after integrating with backend

const mockedChartData = {
	histograms: [
		{
			metricName: MetricName.Impressions,
			metrics: [
				{
					previousValue: 8360000,
					previousValueKey: '2025-07-19T17:00',
					value: 9700000,
					valueKey: '2025-07-20T17:00',
				},
				{
					previousValue: 6880000,
					previousValueKey: '2025-07-19T18:00',
					value: 150000,
					valueKey: '2025-07-20T18:00',
				},
				{
					previousValue: 1160000,
					previousValueKey: '2025-07-19T19:00',
					value: 3180000,
					valueKey: '2025-07-20T19:00',
				},
				{
					previousValue: 7990000,
					previousValueKey: '2025-07-19T20:00',
					value: 590000,
					valueKey: '2025-07-20T20:00',
				},
				{
					previousValue: 9740000,
					previousValueKey: '2025-07-19T21:00',
					value: 180000,
					valueKey: '2025-07-20T21:00',
				},
				{
					previousValue: 4360000,
					previousValueKey: '2025-07-19T22:00',
					value: 2310000,
					valueKey: '2025-07-20T22:00',
				},
				{
					previousValue: 8240000,
					previousValueKey: '2025-07-19T23:00',
					value: 5060000,
					valueKey: '2025-07-20T23:00',
				},
				{
					previousValue: 130000,
					previousValueKey: '2025-07-20T00:00',
					value: 7490000,
					valueKey: '2025-07-21T00:00',
				},
				{
					previousValue: 5520000,
					previousValueKey: '2025-07-20T01:00',
					value: 5560000,
					valueKey: '2025-07-21T01:00',
				},
				{
					previousValue: 2900000,
					previousValueKey: '2025-07-20T02:00',
					value: 9670000,
					valueKey: '2025-07-21T02:00',
				},
				{
					previousValue: 8540000,
					previousValueKey: '2025-07-20T03:00',
					value: 7300000,
					valueKey: '2025-07-21T03:00',
				},
				{
					previousValue: 760000,
					previousValueKey: '2025-07-20T04:00',
					value: 720000,
					valueKey: '2025-07-21T04:00',
				},
				{
					previousValue: 9710000,
					previousValueKey: '2025-07-20T05:00',
					value: 8380000,
					valueKey: '2025-07-21T05:00',
				},
				{
					previousValue: 2360000,
					previousValueKey: '2025-07-20T06:00',
					value: 7540000,
					valueKey: '2025-07-21T06:00',
				},
				{
					previousValue: 4880000,
					previousValueKey: '2025-07-20T07:00',
					value: 6430000,
					valueKey: '2025-07-21T07:00',
				},
				{
					previousValue: 3110000,
					previousValueKey: '2025-07-20T08:00',
					value: 2510000,
					valueKey: '2025-07-21T08:00',
				},
			],
			total: 1231,
			totalValue: 3000,
		},
		{
			metricName: MetricName.Downloads,
			metrics: [
				{
					previousValue: 171,
					previousValueKey: '2025-07-19T17:00',
					value: 37,
					valueKey: '2025-07-20T17:00',
				},
				{
					previousValue: 29,
					previousValueKey: '2025-07-19T18:00',
					value: 965,
					valueKey: '2025-07-20T18:00',
				},
				{
					previousValue: 24,
					previousValueKey: '2025-07-19T19:00',
					value: 500,
					valueKey: '2025-07-20T19:00',
				},
				{
					previousValue: 234,
					previousValueKey: '2025-07-19T20:00',
					value: 399,
					valueKey: '2025-07-20T20:00',
				},
				{
					previousValue: 576,
					previousValueKey: '2025-07-19T21:00',
					value: 97,
					valueKey: '2025-07-20T21:00',
				},
				{
					previousValue: 296,
					previousValueKey: '2025-07-19T22:00',
					value: 416,
					valueKey: '2025-07-20T22:00',
				},
				{
					previousValue: 11,
					previousValueKey: '2025-07-19T23:00',
					value: 303,
					valueKey: '2025-07-20T23:00',
				},
				{
					previousValue: 338,
					previousValueKey: '2025-07-20T00:00',
					value: 580,
					valueKey: '2025-07-21T00:00',
				},
				{
					previousValue: 636,
					previousValueKey: '2025-07-20T01:00',
					value: 264,
					valueKey: '2025-07-21T01:00',
				},
				{
					previousValue: 885,
					previousValueKey: '2025-07-20T02:00',
					value: 113,
					valueKey: '2025-07-21T02:00',
				},
				{
					previousValue: 374,
					previousValueKey: '2025-07-20T03:00',
					value: 981,
					valueKey: '2025-07-21T03:00',
				},
				{
					previousValue: 843,
					previousValueKey: '2025-07-20T04:00',
					value: 859,
					valueKey: '2025-07-21T04:00',
				},
				{
					previousValue: 762,
					previousValueKey: '2025-07-20T05:00',
					value: 982,
					valueKey: '2025-07-21T05:00',
				},
				{
					previousValue: 152,
					previousValueKey: '2025-07-20T06:00',
					value: 598,
					valueKey: '2025-07-21T06:00',
				},
				{
					previousValue: 966,
					previousValueKey: '2025-07-20T07:00',
					value: 324,
					valueKey: '2025-07-21T07:00',
				},
				{
					previousValue: 494,
					previousValueKey: '2025-07-20T08:00',
					value: 387,
					valueKey: '2025-07-21T08:00',
				},
				{
					previousValue: 894,
					previousValueKey: '2025-07-20T09:00',
					value: 13,
					valueKey: '2025-07-21T09:00',
				},
				{
					previousValue: 846,
					previousValueKey: '2025-07-20T10:00',
					value: 478,
					valueKey: '2025-07-21T10:00',
				},
				{
					previousValue: 399,
					previousValueKey: '2025-07-20T11:00',
					value: 130,
					valueKey: '2025-07-21T11:00',
				},
				{
					previousValue: 824,
					previousValueKey: '2025-07-20T12:00',
					value: 833,
					valueKey: '2025-07-21T12:00',
				},
				{
					previousValue: 737,
					previousValueKey: '2025-07-20T13:00',
					value: 801,
					valueKey: '2025-07-21T13:00',
				},
				{
					previousValue: 462,
					previousValueKey: '2025-07-20T14:00',
					value: 401,
					valueKey: '2025-07-21T14:00',
				},
				{
					previousValue: 483,
					previousValueKey: '2025-07-20T15:00',
					value: 805,
					valueKey: '2025-07-21T15:00',
				},
				{
					previousValue: 878,
					previousValueKey: '2025-07-20T16:00',
					value: 595,
					valueKey: '2025-07-21T16:00',
				},
			],
			total: 1231,
			totalValue: 3000,
		},
		{
			metricName: MetricName.Views,
			metrics: [
				{
					previousValue: 103,
					previousValueKey: '2025-07-19T17:00',
					value: 48,
					valueKey: '2025-07-20T17:00',
				},
				{
					previousValue: 25,
					previousValueKey: '2025-07-19T18:00',
					value: 566,
					valueKey: '2025-07-20T18:00',
				},
				{
					previousValue: 382,
					previousValueKey: '2025-07-19T19:00',
					value: 684,
					valueKey: '2025-07-20T19:00',
				},
				{
					previousValue: 525,
					previousValueKey: '2025-07-19T20:00',
					value: 990,
					valueKey: '2025-07-20T20:00',
				},
				{
					previousValue: 663,
					previousValueKey: '2025-07-19T21:00',
					value: 256,
					valueKey: '2025-07-20T21:00',
				},
				{
					previousValue: 372,
					previousValueKey: '2025-07-19T22:00',
					value: 353,
					valueKey: '2025-07-20T22:00',
				},
				{
					previousValue: 217,
					previousValueKey: '2025-07-19T23:00',
					value: 124,
					valueKey: '2025-07-20T23:00',
				},
				{
					previousValue: 37,
					previousValueKey: '2025-07-20T00:00',
					value: 738,
					valueKey: '2025-07-21T00:00',
				},
				{
					previousValue: 623,
					previousValueKey: '2025-07-20T01:00',
					value: 164,
					valueKey: '2025-07-21T01:00',
				},
				{
					previousValue: 396,
					previousValueKey: '2025-07-20T02:00',
					value: 659,
					valueKey: '2025-07-21T02:00',
				},
				{
					previousValue: 637,
					previousValueKey: '2025-07-20T03:00',
					value: 614,
					valueKey: '2025-07-21T03:00',
				},
				{
					previousValue: 157,
					previousValueKey: '2025-07-20T04:00',
					value: 327,
					valueKey: '2025-07-21T04:00',
				},
				{
					previousValue: 22,
					previousValueKey: '2025-07-20T05:00',
					value: 249,
					valueKey: '2025-07-21T05:00',
				},
				{
					previousValue: 445,
					previousValueKey: '2025-07-20T06:00',
					value: 335,
					valueKey: '2025-07-21T06:00',
				},
				{
					previousValue: 651,
					previousValueKey: '2025-07-20T07:00',
					value: 169,
					valueKey: '2025-07-21T07:00',
				},
				{
					previousValue: 275,
					previousValueKey: '2025-07-20T08:00',
					value: 84,
					valueKey: '2025-07-21T08:00',
				},
				{
					previousValue: 482,
					previousValueKey: '2025-07-20T09:00',
					value: 358,
					valueKey: '2025-07-21T09:00',
				},
				{
					previousValue: 681,
					previousValueKey: '2025-07-20T10:00',
					value: 63,
					valueKey: '2025-07-21T10:00',
				},
				{
					previousValue: 386,
					previousValueKey: '2025-07-20T11:00',
					value: 670,
					valueKey: '2025-07-21T11:00',
				},
				{
					previousValue: 808,
					previousValueKey: '2025-07-20T12:00',
					value: 944,
					valueKey: '2025-07-21T12:00',
				},
				{
					previousValue: 96,
					previousValueKey: '2025-07-20T13:00',
					value: 699,
					valueKey: '2025-07-21T13:00',
				},
				{
					previousValue: 973,
					previousValueKey: '2025-07-20T14:00',
					value: 466,
					valueKey: '2025-07-21T14:00',
				},
				{
					previousValue: 596,
					previousValueKey: '2025-07-20T15:00',
					value: 884,
					valueKey: '2025-07-21T15:00',
				},
				{
					previousValue: 634,
					previousValueKey: '2025-07-20T16:00',
					value: 259,
					valueKey: '2025-07-21T16:00',
				},
			],
			total: 1231,
			totalValue: 3000,
		},
	],
};

export type Histogram = {
	metricName: string;
	metrics: {
		previousValue: number;
		previousValueKey: string;
		value: number;
		valueKey: string;
	}[];
	total: number;
	totalValue: number;
};

export interface ICommonProps {
	histogram: Histogram;
	metricType: MetricType;
}

const renderComponent = (Component: React.ComponentType<ICommonProps>) => {
	return (props: ICommonProps) => {
		if (!props.histogram) {
			return <></>;
		}

		return <Component {...props} />;
	};
};

const dropdownItems: {
	icon: string;
	name: string;
	renderer: (props: ICommonProps) => JSX.Element;
	value: string;
}[] = [
	{
		icon: 'analytics',
		name: Liferay.Language.get('chart'),
		renderer: renderComponent(AssetMetricsChart),
		value: 'chart',
	},
	{
		icon: 'table',
		name: Liferay.Language.get('table'),
		renderer: renderComponent(AssetMetricsTableView),
		value: 'table',
	},
];

const AssetMetrics = () => {
	const {filters} = useContext(Context);
	const [selectedItem, setSelectedItem] = useState(dropdownItems[0]);
	const [dropdownActive, setDropdownActive] = useState(false);

	const metricName: Partial<{
		[key in MetricType]: MetricName;
	}> = {
		[MetricType.Views]: MetricName.Views,
		[MetricType.Impressions]: MetricName.Impressions,
		[MetricType.Downloads]: MetricName.Downloads,
	};

	return (
		<>
			<div className="align-items-center d-flex justify-content-around mt-3">
				<span className="text-3 text-nowrap text-secondary">
					{Liferay.Language.get(
						'total-number-of-times-an-asset-is-seen-by-visitors'
					)}
				</span>

				<ClayDropdown
					active={dropdownActive}
					closeOnClickOutside={true}
					onActiveChange={setDropdownActive}
					trigger={
						<ClayButton
							aria-label={selectedItem.name}
							borderless={true}
							displayType="secondary"
							onClick={() => {
								setDropdownActive(!dropdownActive);
							}}
							size="sm"
						>
							{selectedItem.icon && (
								<ClayIcon symbol={selectedItem.icon} />
							)}

							<ClayIcon className="mx-2" symbol="caret-bottom" />
						</ClayButton>
					}
				>
					{dropdownItems.map((item) => (
						<ClayDropdown.Item
							active={item.value === selectedItem.value}
							key={item.value}
							onClick={() => {
								setSelectedItem(item);
								setDropdownActive(false);
							}}
						>
							{item.icon && (
								<ClayIcon className="mr-2" symbol={item.icon} />
							)}

							{item.name}
						</ClayDropdown.Item>
					))}
				</ClayDropdown>
			</div>

			<main className="mt-3">
				{selectedItem.renderer({
					histogram: mockedChartData.histograms.find(
						({metricName: currentMetricName}) =>
							currentMetricName === metricName[filters.metric]
					) as Histogram,
					metricType: filters.metric,
				})}
			</main>
		</>
	);
};
export {AssetMetrics};
