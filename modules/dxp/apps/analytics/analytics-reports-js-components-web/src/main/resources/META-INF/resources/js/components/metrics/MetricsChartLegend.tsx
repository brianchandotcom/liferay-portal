/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import ClayLink from '@clayui/link';
import classNames from 'classnames';
import React from 'react';

import {DotProps} from './Dots';

export interface IMetricsChartLegendProps {
	activeTabIndex: boolean;
	legendItems: {
		Dot: React.JSXElementConstructor<DotProps>;
		block?: boolean;
		dataKey: string;
		dotColor: string;
		title: string;
		total: string | number;
		url?: string;
	}[];
	onDatakeyChange: (dataKey: string | null) => void;
}

const MetricsChartLegend: React.FC<IMetricsChartLegendProps> = ({
	activeTabIndex,
	legendItems,
	onDatakeyChange,
}) => {
	return (
		<ul className="d-inline-block mb-3 metrics-chart__legend ml-5">
			{legendItems.map(
				(
					{Dot, block = false, dataKey, dotColor, title, total, url},
					index
				) => {
					return (
						<li
							className={classNames('mb-2 mr-3 tab-focus', {
								'd-block': block,
							})}
							key={dataKey}
							onBlur={() => onDatakeyChange(null)}
							onFocus={() => onDatakeyChange(dataKey)}
							onMouseEnter={() => onDatakeyChange(dataKey)}
							onMouseLeave={() => onDatakeyChange(null)}
							tabIndex={activeTabIndex ? 0 : -1}
						>
							<span className="mr-2">
								<Dot
									displayOutsideOfRecharts
									stroke={dotColor}
								/>
							</span>

							{url ? (
								<ClayLink
									className={
										index === 0
											? 'text-dark'
											: 'text-secondary'
									}
									data-tooltip-align="top"
									href={url}
									onKeyDown={(event) => {
										if (event.key === 'Enter') {
											window.location.assign(url);
										}
									}}
									tabIndex={activeTabIndex ? 0 : -1}
									title={Liferay.Language.get(
										'click-to-view-page'
									)}
								>
									<Text size={3}>{`${title}: ${total}`}</Text>
								</ClayLink>
							) : (
								<Text
									color={
										index !== 0 ? 'secondary' : undefined
									}
									size={3}
								>
									{`${title}: ${total}`}
								</Text>
							)}
						</li>
					);
				}
			)}
		</ul>
	);
};

export default MetricsChartLegend;
