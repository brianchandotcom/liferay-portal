import HeatmapChart from 'shared/components/HeatmapChart';
import React from 'react';
import Row from '../components/Row';
import {flatten, range} from 'lodash';

const WEEKDAYS = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];

const mockData = flatten(
	WEEKDAYS.map(day =>
		range(24).map(i => ({
			column: day,
			row: i,
			value: i >= 4 && i + 1 <= 20 ? Math.random() * 100 : 0
		}))
	)
);

export default class HeatmapChartKit extends React.Component {
	render() {
		return (
			<div>
				<Row>
					<HeatmapChart data={mockData} />
				</Row>
			</div>
		);
	}
}
