/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './CustomTooltip.scss';

import React from 'react';

const CustomTooltip = ({active, categories, label: dateString, payload}) => {
	if (active && payload && payload.length) {
		return (
			<div className="forecast-chart-tooltip">
				<span className="forecast-chart-tooltip-date">
					{dateString}
				</span>

				{payload.map((entry, index) => {
					const categoryId = Object.keys(categories).find(
						(key) => categories[key].name === entry.name
					);

					if (!categoryId) {
						return null;
					}

					const {high, low, mid} = entry.payload.values[categoryId];

					return (
						<div
							className="forecast-chart-tooltip-row"
							key={`item-${index}`}
						>
							<span
								className="forecast-chart-tooltip-color-box"
								style={{
									background: entry.color,
								}}
							/>

							<span>{entry.name}</span>

							<span>
								<strong>High:</strong> {`${high}`}
							</span>

							<span>
								<strong>Mid:</strong> {`${mid}`}
							</span>

							<span>
								<strong>Low:</strong>

								{`${low}`}
							</span>
						</div>
					);
				})}
			</div>
		);
	}

	return null;
};

export default CustomTooltip;
