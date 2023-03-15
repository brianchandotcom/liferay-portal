import React from 'react';
import {noop} from 'lodash';

export default class ClayChart extends React.Component {
	constructor(props) {
		super(props);

		this.bbChart = {
			select: noop,
			tooltip: {
				hide: noop,
				show: noop
			},
			unselect: noop
		};
	}

	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			/>
		);
	}
}

export {
	ClayChart as BarChart,
	ClayChart as LineChart,
	ClayChart as PieChart,
	ClayChart as SplineChart
};
