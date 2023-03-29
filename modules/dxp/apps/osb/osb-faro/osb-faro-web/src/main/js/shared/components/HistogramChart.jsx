import Chart, {BAR_CHART} from 'shared/components/Chart';
import React from 'react';
import {get, merge} from 'lodash';

const DEFAULT_BAR_WIDTH = 60;

function getAxisTickConfig(axisX, bar) {
	const origTickConfig = get(axisX, 'tick', {});
	const origBarWidth = get(bar, 'width', 0) + get(bar, 'padding', 0);

	return merge(
		{
			multiline: false,
			outer: false,
			show: true,
			text: {
				position: {
					y: origBarWidth ? origBarWidth / 2 : DEFAULT_BAR_WIDTH / 2
				}
			}
		},
		origTickConfig
	);
}

export default React.forwardRef((props, ref) => {
	const {axisX, bar, ...otherProps} = props;

	return (
		<Chart
			axisRotated
			axisX={{
				...axisX,
				tick: getAxisTickConfig(axisX, bar)
			}}
			axisY={{
				tick: {
					show: false,
					text: {
						show: false
					}
				}
			}}
			bar={merge({width: {ratio: 0.9}}, bar)}
			chartType={BAR_CHART}
			className='histogram-chart-root'
			ref={ref}
			{...otherProps}
		/>
	);
});
