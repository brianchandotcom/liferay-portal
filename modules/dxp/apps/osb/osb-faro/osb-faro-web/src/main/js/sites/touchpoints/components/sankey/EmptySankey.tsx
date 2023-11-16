import React from 'react';
import {MAIN_NODE_HEIGHT, MAIN_NODE_WIDTH} from './utils';
import {Node} from './Node';
import {Tooltip as RechartsTooltip, Sankey as SankeyChart} from 'recharts';
import {Tooltip} from './Tooltip';

const Sankey = ({data, emptyState}) => {
	const marginTop = 60;

	return (
		<SankeyChart
			data={data}
			height={MAIN_NODE_HEIGHT + marginTop}
			margin={{bottom: 30, right: 20, top: marginTop}}
			node={<Node emptyState={emptyState} />}
			nodePadding={80}
			nodeWidth={MAIN_NODE_WIDTH}
			width={MAIN_NODE_WIDTH}
		>
			<RechartsTooltip
				allowEscapeViewBox={{x: true, y: true}}
				content={<Tooltip />}
			/>
		</SankeyChart>
	);
};

export default Sankey;
