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
		formatter,
		label,
		labelFormatter,
		payload,
		publishDateFill,
		separator = '',
		showPublishedDateLabel,
	} = props;

	return label ? (
		<div className="custom-tooltip">
			<p className="mb-1 mt-0">
				<b>{labelFormatter ? labelFormatter(label) : label}</b>
			</p>
			<hr className="mb-1 mt-1" />
			{showPublishedDateLabel && (
				<span>
					<span
						className={'custom-circle mr-1'}
						style={{
							backgroundColor: 'white',
							border: `2px solid ${publishDateFill}`,
						}}
					></span>
					{Liferay.Language.get('published')}
				</span>
			)}
			<ul className="list-unstyled mb-0">
				{payload.map((item) => {
					const [value, name, iconType] = formatter
						? formatter(item.value, item.name, item.iconType)
						: [item.value, item.name, item.iconType];

					return (
						<li key={item.name}>
							<span
								className={`custom-${iconType} mr-1`}
								style={{
									backgroundColor: item.color,
								}}
							></span>
							{name}
							{separator}
							<b>{value}</b>
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
			value: PropTypes.number.isRequired,
		})
	),
	publishDateFill: PropTypes.string,
	separator: PropTypes.string,
	showPublishedDateLabel: PropTypes.bool,
};
