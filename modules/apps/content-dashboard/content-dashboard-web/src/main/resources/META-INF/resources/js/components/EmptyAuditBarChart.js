/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import ClayEmptyState from '@clayui/empty-state';
import ClayIcon from '@clayui/icon';
import React from 'react';
import {
	BarChart,
	CartesianGrid,
	ResponsiveContainer,
	XAxis,
	YAxis,
} from 'recharts';

const EMPTY_DATA = [];

const BAR_CHART = {
	dotRadiusMax: 35,
	dotRadiusMin: 5,
	height: 300,
	stroke: '#E7E7ED',
	width: 1150,
};

export default function EmptyAuditBarChart() {
	return (
		<>
			<ClayEmptyState
				className="empty-state text-center"
				description={Liferay.Language.get(
					'create-marketing-categories-to-label-and-audit-your-content'
				)}
				title={Liferay.Language.get('there-is-no-data')}
			>
				<ClayButton displayType="secondary">
					{Liferay.Language.get('add-marketing-categories')}
					<ClayIcon
						className="inline-item inline-item-after"
						symbol="shortcut"
					/>
				</ClayButton>
			</ClayEmptyState>

			<ResponsiveContainer height={BAR_CHART.height}>
				<BarChart
					data={EMPTY_DATA}
					height={BAR_CHART.height}
					width={BAR_CHART.width}
				>
					<CartesianGrid
						horizontal={true}
						horizontalPoints={[
							BAR_CHART.dotRadiusMin,
							BAR_CHART.height - BAR_CHART.dotRadiusMax,
						]}
						stroke={BAR_CHART.stroke}
					/>
					<XAxis
						axisLine={{
							stroke: BAR_CHART.stroke,
						}}
						tickLine={false}
					/>
					<YAxis
						axisLine={{
							stroke: BAR_CHART.stroke,
						}}
						domain={[0, 40]}
						tickLine={false}
					/>
				</BarChart>
			</ResponsiveContainer>
		</>
	);
}
