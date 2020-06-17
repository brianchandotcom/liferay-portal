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

import {BAR_CHART} from '../utils/constants';

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

			<ResponsiveContainer height={BAR_CHART.emptyHeight}>
				<BarChart
					data={[]}
					height={BAR_CHART.emptyHeight}
					width={BAR_CHART.width}
				>
					<CartesianGrid
						horizontal={true}
						horizontalPoints={[
							BAR_CHART.dotRadiusMin,
							BAR_CHART.emptyHeight - BAR_CHART.dotRadiusMax,
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
