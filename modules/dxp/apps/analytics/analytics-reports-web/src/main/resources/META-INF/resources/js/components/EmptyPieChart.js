/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
