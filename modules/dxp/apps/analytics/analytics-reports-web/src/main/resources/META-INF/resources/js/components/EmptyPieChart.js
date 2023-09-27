/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';
import {Cell, Pie, PieChart} from 'recharts';

const EMPTY_PIE_CHART_FILL_COLOR = '#f1f2f5';

export default function EmptyPieChart({
	height,
	innerRadius,
	outerRadius,
	width,
}) {
	return (
		<div className="pie-chart-wrapper--chart">
			<PieChart height={height} width={width}>
				<Pie
					cx="50%"
					cy="50%"
					data={[{value: 100}]}
					dataKey="value"
					innerRadius={innerRadius}
					isAnimationActive={false}
					nameKey="name"
					outerRadius={outerRadius}
					paddingAngle={1}
				>
					<Cell fill={EMPTY_PIE_CHART_FILL_COLOR} />
				</Pie>
			</PieChart>
		</div>
	);
}

EmptyPieChart.propTypes = {
	height: PropTypes.number.isRequired,
	innerRadius: PropTypes.number.isRequired,
	outerRadius: PropTypes.number.isRequired,
	width: PropTypes.number.isRequired,
};
