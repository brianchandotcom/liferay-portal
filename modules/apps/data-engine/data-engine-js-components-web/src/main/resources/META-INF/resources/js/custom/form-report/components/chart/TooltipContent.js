/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {getColumnLabel, getPercentage, roundPercentage} from '../../utils/data';

export default function TooltipContent({
	active,
	field,
	label,
	payload,
	roundBullet = true,
	showBullet = true,
	showHeader = true,
	totalEntries = 0,
}) {
	if (active) {
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
					{payload.map(({dataKey, fill, payload, value}, index) => {
						dataKey = !showHeader
							? payload.label
							: getColumnLabel(dataKey, field);

						fill = payload.fill ?? fill;

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

									{Number(value) === 1
										? `${Liferay.Language.get(
												'entry'
										  ).toLowerCase()} `
										: `${Liferay.Language.get(
												'entries'
										  ).toLowerCase()} `}

									<b>
										(
										{roundPercentage(
											getPercentage(value, totalEntries)
										)}
										)
									</b>
								</div>
							</li>
						);
					})}
				</ul>
			</div>
		);
	}

	return null;
}
