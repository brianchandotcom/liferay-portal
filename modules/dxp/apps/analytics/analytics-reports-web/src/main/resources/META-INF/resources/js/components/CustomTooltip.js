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

/**
 * Component to customize the content of recharts Tooltip
 * http://recharts.org/en-US/api/Tooltip#content
 */
export default function CustomTooltip(props) {
	const {
		formatter = (value, label) => [value, label],
		label,
		labelFormatter = label => label,
		payload,
		separator = ''
	} = props;

	return label ? (
		<div className="custom-tooltip">
			<p className="mb-1 mt-0">
				<b>{labelFormatter(label)}</b>
			</p>
			<ul className="list-unstyled mb-0">
				{payload.map(item => {
					const [value, name] = formatter(item.value, item.name);

					return (
						<li key={item.name}>
							<div
								className="custom-tooltip-dot"
								style={{
									backgroundColor: item.color
								}}
							></div>
							{name}
							{separator}
							{value}
						</li>
					);
				})}
			</ul>
		</div>
	) : null;
}

CustomTooltip.propTypes = {
	formatter: PropTypes.func,
	label: PropTypes.string,
	labelFormatter: PropTypes.func,
	payload: PropTypes.arrayOf(
		PropTypes.shape({
			name: PropTypes.string.isRequired,
			value: PropTypes.number.isRequired
		})
	),
	separator: PropTypes.string
};
