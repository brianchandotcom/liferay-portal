/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {useContext, useEffect} from 'react';

import {Context} from '../Context';
import {MetricType} from '../types/global';
import {getPercentage, toThousands} from '../utils/math';
import {
	TrendClassification,
	getStatsColor,
	getStatsIcon,
} from '../utils/metrics';

type Metrics = {
	[key in MetricType]: string;
};

type MetricData = {
	metricType: MetricType;
	trend: {
		percentage?: number;
		trendClassification: TrendClassification;
	};
	value: number;
};

export type OverviewMetricsData = {
	defaultMetric: MetricData;
	selectedMetrics: MetricData[];
};

const MetricsTitle: Metrics = {
	[MetricType.Comments]: Liferay.Language.get('comments'),
	[MetricType.Downloads]: Liferay.Language.get('downloads'),
	[MetricType.Impressions]: Liferay.Language.get('impressions'),
	[MetricType.Undefined]: Liferay.Language.get('undefined'),
	[MetricType.Views]: Liferay.Language.get('views'),
};

interface IBaseOverviewMetrics extends React.HTMLAttributes<HTMLElement> {
	data: {
		defaultMetric: MetricData;
		selectedMetrics: MetricData[];
	};
	small?: boolean;
}

const BaseOverviewMetrics: React.FC<IBaseOverviewMetrics> = ({
	className,
	data,
	small,
}) => {
	const {changeMetricFilter, filters} = useContext(Context);

	useEffect(() => {
		if (filters.metric === MetricType.Undefined) {
			changeMetricFilter(data.defaultMetric.metricType);
		}
	}, [changeMetricFilter, data.defaultMetric.metricType, filters.metric]);

	return (
		<div
			className={classNames('overview-metrics', className)}
			id="overview-metrics"
		>
			{data.selectedMetrics.map(({metricType, trend, value}) => {
				const name = MetricsTitle[metricType];
				const trendPercentage = trend.percentage ?? 0;
				const selected = filters.metric === metricType;

				return (
					<button
						aria-pressed={selected}
						className={classNames(
							'cursor-pointer overview-metric',
							{
								'selected tab-focus': selected,
							}
						)}
						data-testid={name}
						key={metricType}
						onClick={() => changeMetricFilter(metricType)}
						onKeyDown={(event) => {
							if (event.key === 'Enter' || event.key === ' ') {
								event.preventDefault();

								changeMetricFilter(metricType);
							}
						}}
						tabIndex={0}
					>
						<div
							className="overview-metric__title"
							data-testid={`${name}-title`}
						>
							<Text size={3}>{name.toUpperCase()}</Text>
						</div>

						<div
							className="overview-metric__value"
							data-testid={`${name}-value`}
						>
							{toThousands(value)}
						</div>

						<div className="overview-metric__comparison">
							{trendPercentage !== 0 && (
								<span data-testid={`${name}-percentage`}>
									<Text
										color={getStatsColor(
											trend.trendClassification
										)}
										size={small ? 3 : 1}
									>
										<ClayIcon
											symbol={getStatsIcon(
												trendPercentage
											)}
										/>
									</Text>
								</span>
							)}

							<span
								className="ml-1 overview-metric__percentage-description"
								data-testid={`${name}-percentage-description`}
							>
								{small ? (
									<Text
										color={getStatsColor(
											trend.trendClassification
										)}
									>
										{`${getPercentage(trendPercentage)}%`}
									</Text>
								) : (
									<Text size={1}>
										{sub(
											Liferay.Language.get(
												'x-vs-previous-period'
											),
											[
												<span
													className={`text-${getStatsColor(trend.trendClassification)}`}
													key="PERCENTAGE"
												>
													{`${getPercentage(
														trendPercentage
													)}%`}
												</span>,
											]
										)}
									</Text>
								)}
							</span>
						</div>
					</button>
				);
			})}
		</div>
	);
};

export {BaseOverviewMetrics};
