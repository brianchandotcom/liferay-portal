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
