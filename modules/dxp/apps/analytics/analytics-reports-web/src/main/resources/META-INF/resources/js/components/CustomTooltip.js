/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		<div className="custom-tooltip popover">
			<p className="mx-2 popover-header py-1">
				<b>{labelFormatter ? labelFormatter(label) : label}</b>
			</p>

			<div className="mb-0 p-2 popover-body">
				{showPublishedDateLabel && (
					<span>
						<span
							className="custom-circle mr-1"
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
