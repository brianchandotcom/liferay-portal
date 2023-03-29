import Chart, {
	BAR_CHART,
	COMBINED_CHART,
	LINE_CHART,
	SPLINE_CHART
} from 'shared/components/Chart';
import React from 'react';
import Row from '../components/Row';

const CHART_DATA_ID = 'data_id';
const CHART_ID = 'id';

const dummyData = [
	{
		data: [30, 200, 100, 400, 150, 250],
		id: 'data1',
		name: 'Data 1'
	},
	{
		data: [50, 20, 10, 40, 15, 25],
		id: 'data2'
	},
	{
		data: [150, 90, 30, 400, 15, 205],
		id: 'data3'
	},
	{
		data: [10, 30, 130, 200, 50, 20],
		id: 'data4'
	}
];

const combinedDummyData = [
	{
		data: [30, 200, 100, 400, 150, 250],
		id: 'data1',
		name: 'Data 1',
		type: BAR_CHART
	},
	{
		data: [50, 20, 10, 40, 15, 25],
		id: 'data2',
		type: BAR_CHART
	},
	{
		data: [150, 90, 30, 400, 15, 205],
		id: 'data3',
		type: SPLINE_CHART
	},
	{
		data: [10, 30, 130, 200, 50, 20],
		id: 'data4',
		type: LINE_CHART
	}
];

export default class ChartKit extends React.Component {
	constructor() {
		super();

		this._selectChartRef = React.createRef();
	}

	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<h4>{'Chart with automatic selected point'}</h4>
					<Chart
						chartType={SPLINE_CHART}
						data={[dummyData[0]]}
						dataId={CHART_DATA_ID}
						id={CHART_ID}
						onafterinit={() => {
							this._selectChartRef.current.select([
								dummyData[0].data.length - 1
							]);
						}}
						onPointSelect={() => {}}
						ref={this._selectChartRef}
						unloadBeforeLoad={false}
					/>
				</Row>

				<Row>
					<Chart
						chartType={LINE_CHART}
						data={dummyData}
						dataId={CHART_DATA_ID}
						id={CHART_ID}
					/>
				</Row>

				<Row>
					<Chart
						chartType={BAR_CHART}
						data={dummyData}
						dataId={CHART_DATA_ID}
						id={CHART_ID}
					/>
				</Row>

				<Row>
					<Chart
						chartType={SPLINE_CHART}
						data={dummyData}
						dataId={CHART_DATA_ID}
						id={CHART_ID}
					/>
				</Row>

				<Row>
					<Chart
						chartType={COMBINED_CHART}
						data={combinedDummyData}
						dataId={CHART_DATA_ID}
						id={CHART_ID}
					/>
				</Row>
			</div>
		);
	}
}
