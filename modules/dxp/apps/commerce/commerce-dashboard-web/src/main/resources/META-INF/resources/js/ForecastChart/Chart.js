/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {
	Area,
	CartesianGrid,
	ComposedChart,
	Legend,
	Line,
	ResponsiveContainer,
	Tooltip,
	XAxis,
	YAxis,
} from 'recharts';

import CustomTooltip from './CustomTooltip';

export default function Chart({data, loading}) {
	if (loading) {
		return <span aria-hidden="true" className="loading-animation" />;
	}

	if (!data || !data.columns?.length) {
		return <p>{Liferay.Language.get('no-data-available')}</p>;
	}

	return (
		<ResponsiveContainer height={400} width="100%">
			<ComposedChart data={data.columns}>
				{Object.keys(data.categories).map((categoryId) => (
					<React.Fragment key={categoryId}>
						<Line
							dataKey={(dataItem) =>
								dataItem.values[categoryId]?.mid
							}
							dot={{
								fill: data.categories[categoryId]?.color,
								strokeDasharray: 0,
							}}
							isAnimationActive={false}
							name={data.categories[categoryId].name}
							stroke={data.categories[categoryId].color}
							strokeDasharray="3 3"
							type="linear"
						/>

						<Area
							activeDot={false}
							dataKey={(dataItem) => {
								if (!dataItem.values[categoryId]) {
									return null;
								}

								return [
									dataItem.values[categoryId].low,
									dataItem.values[categoryId].high,
								];
							}}
							dot={false}
							fill={data.categories[categoryId].color}
							fillOpacity={0.2}
							isAnimationActive={false}
							legendType="none"
							stroke="none"
						/>
					</React.Fragment>
				))}

				<CartesianGrid />

				<XAxis dataKey="xAxisName" />

				<YAxis />

				<Legend />

				<Tooltip
					content={<CustomTooltip categories={data.categories} />}
				/>
			</ComposedChart>
		</ResponsiveContainer>
	);
}
