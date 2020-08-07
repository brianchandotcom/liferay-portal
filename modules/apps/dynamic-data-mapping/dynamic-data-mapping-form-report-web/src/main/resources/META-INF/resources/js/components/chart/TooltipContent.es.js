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

import React from 'react';

import {getColumnLabel, roundPercentage} from '../../utils/data.es';

export default ({
	active,
	field,
	label,
	payload,
	roundBullet = true,
	showBullet = true,
	showHeader = true,
	totalEntries = 0,
}) => {
	if (active) {
		const getPercentage = (count) => count / totalEntries;

		if (!totalEntries) {
			totalEntries = payload.reduce((accumulator, payloadItem) => {
				return accumulator + payloadItem.value;
			}, 0);
		}

		return (
			<div className="custom-tooltip">
				{showHeader ? (
					<div className="header">
						<div className="title">{label}</div>
					</div>
				) : null}

				<ul>
					{payload.map(
						(
							{
								dataKey,
								payload,
								fill = fill == undefined ? payload.fill : null,
								value,
							},
							index
						) => {
							dataKey = !showHeader
								? payload.label
								: getColumnLabel(dataKey, field);

							return (
								<li key={`tooltip-${index}`}>
									{showBullet ? (
										<svg height="12" width="12">
											{roundBullet ? (
												<circle
													cx="6"
													cy="6"
													fill={fill}
													r="6"
													strokeWidth="0"
												/>
											) : (
												<rect
													fill={fill}
													height="12"
													width="12"
												/>
											)}
										</svg>
									) : null}
									<div id="tooltip-label">
										{`${dataKey}: ${value} `}
										{value == 1
											? `${Liferay.Language.get(
													'entry'
											  ).toLowerCase()} `
											: `${Liferay.Language.get(
													'entries'
											  ).toLowerCase()} `}
										<b>
											(
											{roundPercentage(
												getPercentage(value)
											)}
											)
										</b>
									</div>
								</li>
							);
						}
					)}
				</ul>
			</div>
		);
	}

	return null;
};
