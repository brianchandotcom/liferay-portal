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

import ClayEmptyState from '@clayui/empty-state';
import ClayLink from '@clayui/link';
import React from 'react';
import {
	BarChart,
	CartesianGrid,
	ResponsiveContainer,
	XAxis,
	YAxis,
} from 'recharts';

import {BAR_CHART} from '../../utils/constants';

export default function EmptyAuditBarChart({learnHowLink}) {
	const description = learnHowLink && (
		<ClayLink href={learnHowLink.url} target="_blank">
			{learnHowLink.message}
		</ClayLink>
	);

	return (
		<>
			<ClayEmptyState
				className="empty-state position-absolute text-center"
				description={description}
				title={Liferay.Language.get('there-is-no-data')}
			/>

			<ResponsiveContainer height={BAR_CHART.emptyHeight}>
				<BarChart
					data={[]}
					height={BAR_CHART.emptyHeight}
					width={BAR_CHART.width}
				>
					<CartesianGrid stroke={BAR_CHART.stroke} />

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
