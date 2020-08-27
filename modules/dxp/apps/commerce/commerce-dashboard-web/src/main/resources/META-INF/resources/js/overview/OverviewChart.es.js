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
