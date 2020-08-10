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

export default function OverviewChart(
	_accountIdParamName,
	_APIBaseUrl,
	_commerceAccountId,
	_noAccountErrorMessage,
	_noDataErrorMessage
) {
	// const [accountId, setAccountId] = useState(commerceAccountId);

	// Liferay.on('accountSelected', ({accountId}) => setAccountId(accountId));

	// useEffect(updateData, [accountId]);

	return (
		<div className="overview-chart-wrapper">
			<div className="donut" style={{'--perc': 66}}>
				<div className="chart">
					<svg
						viewBox="0 0 104 104"
						xmlns="http://www.w3.org/2000/svg"
					>
						<circle className="track" cx="52" cy="52" r="50" />
						<circle className="filler" cx="52" cy="52" r="50" />
					</svg>
					<div className="text">66%</div>
				</div>
				<div className="label">Label</div>
			</div>
			<div className="donut" style={{'--perc': 66}}>
				<div className="chart">
					<svg
						viewBox="0 0 104 104"
						xmlns="http://www.w3.org/2000/svg"
					>
						<circle className="track" cx="52" cy="52" r="50" />
						<circle className="filler" cx="52" cy="52" r="50" />
					</svg>
					<div className="text">66%</div>
				</div>
				<div className="label">Label</div>
			</div>
			<div className="donut" style={{'--perc': 66}}>
				<div className="chart">
					<svg
						viewBox="0 0 104 104"
						xmlns="http://www.w3.org/2000/svg"
					>
						<circle className="track" cx="52" cy="52" r="50" />
						<circle className="filler" cx="52" cy="52" r="50" />
					</svg>
					<div className="text">66%</div>
				</div>
				<div className="label">Label</div>
			</div>
		</div>
	);
}
