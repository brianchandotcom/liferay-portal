import BillboardChart from 'react-billboardjs';
import React from 'react';
import {isDefAndNotNull, isNumber} from 'metal';

/**
 * Predictive Chart component.
 * @extends React.Component
 * @param {Object} props
 * @return {ReactElement}
 */

interface IPredictiveChartProps {
	data: Object;
}

type State = {
	columns: string[] | number[];
	regions: Object[];
};

export default class PredictiveChart extends React.Component<
	IPredictiveChartProps,
	State
> {
	/** @inheritdoc */
	constructor(props) {
		super(props);

		let columns = props.data.columns;

		if (isDefAndNotNull(columns)) {
			columns = columns.map(dataSeries => {
				if (dataSeries[0] !== 'x') {
					dataSeries = dataSeries.map(element =>
						isNumber(element)
							? {
									high: element,
									low: element,
									mid: element
							  }
							: element
					);
				}

				return dataSeries;
			});
		}

		const predictionDate = props.predictionDate;
		const regions = [];

		if (isDefAndNotNull(predictionDate)) {
			regions.push({
				axis: 'x',
				start: predictionDate
			});
		}

		this.state = {
			...this.state,
			columns,
			regions
		};
	}

	/** @inheritdoc */
	render() {
		const {columns, regions} = this.state;
		const {data, ...otherProps} = this.props;

		return (
			<BillboardChart
				data={{
					...data,
					columns
				}}
				regions={regions}
				{...otherProps}
			/>
		);
	}
}
