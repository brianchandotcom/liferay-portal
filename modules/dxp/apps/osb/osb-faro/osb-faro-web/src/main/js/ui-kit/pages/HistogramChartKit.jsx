import autobind from 'autobind-decorator';
import ClayButton from '@clayui/button';
import HistogramChart from 'shared/components/HistogramChart';
import React from 'react';
import Row from '../components/Row';

const CHART_DATA_ID = 'data_id';
const CHART_ID = 'id';
const BAR_WIDTH = 60;

const data = [30, 200, 100, 400, 150, 250];

export default class HistogramChartKit extends React.Component {
	state = {
		categories: [
			'1,000 - 2,000',
			'2,001 - 3,000',
			'3,001 - 4,000',
			'4,001 - 5,000',
			'5,001 - 6,000',
			'6,001 - 7,000'
		],
		data
	};

	constructor() {
		super();

		this._chartRef = React.createRef();
	}

	@autobind
	handleAdd() {
		const {categories, data} = this.state;

		this.setState(
			{
				categories: categories.concat([
					`category ${categories.length}${1}`
				]),
				data: data.concat([data[data.length - 1] + 50])
			},
			() => {
				this._chartRef.current._chartRef.current._chartRef.current.chart.resize(
					{
						height: BAR_WIDTH * this.state.data.length
					}
				);
			}
		);
	}

	@autobind
	handleRemove() {
		const {categories, data} = this.state;

		this.setState(
			{
				categories: categories.slice(-1),
				data: data.slice(0, -1)
			},
			() => {
				this._chartRef.current._chartRef.current._chartRef.current.chart.resize(
					{
						height: BAR_WIDTH * this.state.data.length
					}
				);
			}
		);
	}

	render() {
		return (
			<div>
				<ClayButton
					className='button-root mr-2'
					displayType='secondary'
					onClick={this.handleAdd}
				>
					{'Add Item'}
				</ClayButton>

				<ClayButton
					className='button-root'
					displayType='secondary'
					onClick={this.handleRemove}
				>
					{'Remove Item'}
				</ClayButton>

				<p>{`Total Items: ${this.state.data.length}`}</p>

				<Row>
					<h3>{'Chart with Ratio Bar Width'}</h3>
					<HistogramChart
						axisRotated
						axisX={{
							categories: this.state.categories,
							tick: {
								format: (_, name) => name,
								outer: false,
								show: false,
								text: {
									position: {
										y: BAR_WIDTH / 2
									}
								}
							},
							type: 'category'
						}}
						axisY={{
							tick: {
								show: false,
								text: {
									show: false
								}
							}
						}}
						axisY2={{show: true}}
						bar={{width: {ratio: 0.9}}}
						data={[
							{axis: 'y2', data: this.state.data, id: 'data1'}
						]}
						dataId={CHART_DATA_ID}
						height={BAR_WIDTH * this.state.data.length}
						id={CHART_ID}
						ref={this._chartRef}
					/>
				</Row>
			</div>
		);
	}
}
