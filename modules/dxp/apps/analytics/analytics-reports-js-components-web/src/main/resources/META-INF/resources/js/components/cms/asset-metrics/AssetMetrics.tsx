/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropdown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React, {useContext, useEffect, useState} from 'react';

import {Context} from '../../../Context';
import ApiHelper from '../../../apis/ApiHelper';
import {AssetTypes, MetricName, MetricType} from '../../../types/global';
import {buildQueryString} from '../../../utils/buildQueryString';
import {assetMetrics} from '../../../utils/metrics';
import {AssetMetricsChart} from './AssetMetricsChart';
import {AssetMetricsTableView} from './AssetMetricsTableView';

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
	const {
		externalReferenceCode,
		filters,
		objectEntryFolderExternalReferenceCode,
	} = useContext(Context);
	const [dropdownActive, setDropdownActive] = useState(false);
	const [histograms, setHistograms] = useState<Histogram[]>([]);
	const [selectedItem, setSelectedItem] = useState(dropdownItems[0]);

	useEffect(() => {
		async function fetchData() {
			const selectedMetrics =
				assetMetrics[
					objectEntryFolderExternalReferenceCode as AssetTypes
				];

			const queryParams = buildQueryString({
				externalReferenceCode,
				selectedMetrics,
			});

			const endpoint = `/o/analytics-cms-rest/v1.0/object-entry-histogram-metric${queryParams}`;

			const {data, error} = await ApiHelper.get<{
				histograms: Histogram[];
			}>(endpoint);

			if (error) {
				console.error(error);
			}

			if (data) {
				setHistograms(data.histograms);
			}
		}

		fetchData();
	}, [
		externalReferenceCode,
		filters.metric,
		objectEntryFolderExternalReferenceCode,
	]);

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
						'this-metric-calculates-the-total-number-of-times-an-asset-is-seen-by-visitors'
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
					histogram: histograms.find(
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
