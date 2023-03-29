import BillboardChart from 'react-billboardjs';
import GeoMap from './GeoMap';
import Predictive from './Predictive';
import React from 'react';

import {config} from 'clay-charts-shared';

const {
	DEFAULT_COLORS,
	DEFAULT_GRID_OBJECT,
	DEFAULT_LINE_CLASSES,
	DEFAULT_POINT_PATTERNS
} = config;

/**
 * Chart component.
 * @param {Object} props
 * @return {ReactElement}
 */

interface IPropsCharts {
	color?: string;
	data: any;
	grid?: boolean;
	line?: boolean;
	point?: boolean;
}

type RefType = {
	ref: string;
};

export default React.forwardRef<RefType, IPropsCharts>((props, ref) => {
	const {color, data, grid, line, point, ...otherProps} = props;

	let ChartComponent;

	switch (data.type) {
		case 'geo-map':
			ChartComponent = GeoMap;
			break;
		case 'predictive':
			ChartComponent = Predictive;
			break;
		default:
			ChartComponent = BillboardChart;
	}

	return (
		<ChartComponent
			{...otherProps}
			color={Object.assign({pattern: DEFAULT_COLORS}, color)}
			data={data}
			grid={Object.assign(DEFAULT_GRID_OBJECT, grid)}
			line={Object.assign({classes: DEFAULT_LINE_CLASSES}, line)}
			point={Object.assign(
				{
					pattern: DEFAULT_POINT_PATTERNS
				},
				point
			)}
			ref={ref}
		/>
	);
});
