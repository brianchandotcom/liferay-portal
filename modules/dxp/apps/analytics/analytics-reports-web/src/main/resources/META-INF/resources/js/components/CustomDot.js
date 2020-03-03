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

import React from 'react';

/**
 * Component to customize the content of recharts Line#dot and Line#activeDot
 * http://recharts.org/en-US/api/Line#dot
 * http://recharts.org/en-US/api/Line#activeDot
 */
export default function CustomDot(props) {
	const {
		active,
		cx,
		cy,
		dataKey,
		fill,
		radius = 3,
		radiusActive = 4,
		strokeWidth = 0,
	} = props;

	if (active) {
		return renderActiveDot();
	}
	else {
		return renderDot();
	}

	/*
	 * It renders a Line#activeDot
	 */
	function renderActiveDot() {
		if (dataKey == 'analyticsReportsHistoricalReads') {
			return (
				<rect
					fill={fill}
					height={radiusActive * 2}
					strokeWidth={strokeWidth}
					width={radiusActive * 2}
					x={cx - radiusActive}
					y={cy - radiusActive}
				/>
			);
		}

		return (
			<circle
				cx={cx}
				cy={cy}
				fill={fill}
				r={radiusActive}
				strokeWidth={strokeWidth}
			/>
		);
	}

	/*
	 * It renders a Line#dot
	 */
	function renderDot() {
		if (dataKey == 'analyticsReportsHistoricalReads') {
			return (
				<rect
					fill={fill}
					height={radius * 2}
					width={radius * 2}
					x={cx - radius}
					y={cy - radius}
				/>
			);
		}

		return <circle cx={cx} cy={cy} fill={fill} r={radius} />;
	}
}
