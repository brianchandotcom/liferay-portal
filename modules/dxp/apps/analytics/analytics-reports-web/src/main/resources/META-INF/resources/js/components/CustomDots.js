/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

export default function CustomDot(props) {
	return props.active ? <ActiveDot {...props} /> : <Dot {...props} />;
}

/**
 * Component to customize the content of recharts Line#ActiveDot
 * http://recharts.org/en-US/api/Line#activeDot
 */
export function ActiveDot(props) {
	const {cx, cy, fill, r, shape, strokeWidth = 0} = props;

	if (cy === null) {
		return null;
	}
	else if (shape === 'square') {
		const squareSize = r * 2;

		return (
			<rect
				fill={fill}
				height={squareSize}
				strokeWidth={strokeWidth}
				width={squareSize}
				x={cx - r}
				y={cy - r}
			/>
		);
	}
	else {
		return (
			<circle
				cx={cx}
				cy={cy}
				fill={fill}
				r={r}
				strokeWidth={strokeWidth}
			/>
		);
	}
}

ActiveDot.propTypes = {
	props: PropTypes.objectOf(
		PropTypes.shape({
			cx: PropTypes.number.required,
			cy: PropTypes.number.required,
			fill: PropTypes.string.required,
			r: PropTypes.number.required,
			shape: PropTypes.oneOf(['square', 'circle']),
			strokeWidth: PropTypes.number.required,
		})
	),
};

/**
 * Component to customize the content of recharts Line#dot
 * http://recharts.org/en-US/api/Line#dot
 */
export function Dot(props) {
	const {cx, cy, fill, r, shape} = props;
	if (cy === null) {
		return null;
	}
	else if (shape === 'square') {
		const squareSize = r * 2;

		return (
			<rect
				fill={fill}
				height={squareSize}
				width={squareSize}
				x={cx - r}
				y={cy - r}
			/>
		);
	}
	else {
		return <circle cx={cx} cy={cy} fill={fill} r={r} />;
	}
}

Dot.propTypes = {
	props: PropTypes.objectOf(
		PropTypes.shape({
			cx: PropTypes.number.required,
			cy: PropTypes.number.required,
			fill: PropTypes.string.required,
			r: PropTypes.number.required,
			shape: PropTypes.oneOf(['circle', 'square']),
		})
	),
};
