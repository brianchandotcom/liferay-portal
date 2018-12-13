// import './open-processes-summary.scss';
import React from 'react';
import SummaryCard from './SummaryCard';
import ClayCharts from 'clay-charts-react';

export default class OpenProcessesSummary extends React.Component {
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
							{'Open Processes Summary'}
						</div>
						<div className="card-body">
							<div
								className="row d-flex justify-content-start"
								style={{marginTop: '8px'}}
							>
								<SummaryCard description="Total Open" total="15" />
								<SummaryCard description="On time" total="82" />
								<SummaryCard description="Overdue" total="33" />
							</div>
							<div className="col-12" style={{paddingTop: '30px'}}>
								<ClayCharts data={CHART_DATA} />
							</div>
						</div>
					</div>
				</div>
			</div>
		);
	}
}