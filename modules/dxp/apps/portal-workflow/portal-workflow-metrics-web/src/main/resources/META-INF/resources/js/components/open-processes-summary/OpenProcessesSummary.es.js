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

import ClayCharts from 'clay-charts-react';
import React from 'react';
import SummaryCard from './SummaryCard';

/**
 * @class
 * @memberof open-processes-summary
 */
export default class OpenProcessesSummary extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		const CHART_DATA = {
			columns: [
				['data1', 30, 20, 50, 40, 60, 50],
				['data2', 200, 130, 90, 240, 130, 220],
				['data3', 300, 200, 160, 400, 250, 250]
			],
			type: 'bar'
		};

		return (
			<div className="row card-panel">
				<div className="col-9">
					<div className="card">
						<div className="card-header bg-transparent border-secondary card-header-default text-secondary semi-bold">
							{Liferay.Language.get('open-processes-summary')}
						</div>

						<div className="card-body">
							<div
								className="row d-flex justify-content-start"
								style={{marginTop: '8px'}}
							>
								<SummaryCard
									description={Liferay.Language.get(
										'total-open'
									)}
									total="15"
								/>
								<SummaryCard
									description={Liferay.Language.get(
										'on-time'
									)}
									total="82"
								/>
								<SummaryCard
									description={Liferay.Language.get(
										'overdue'
									)}
									total="33"
								/>
							</div>

							<div
								className="col-12"
								style={{paddingTop: '30px'}}
							>
								<ClayCharts data={CHART_DATA} />
							</div>
						</div>
					</div>
				</div>
			</div>
		);
	}
}
