import React, {useState} from 'react';
import {Link} from './Link';
import {MAIN_NODE_WIDTH, SANKEY_HEIGHT} from './utils';
import {Node} from './Node';
import {RangeSelectors} from 'shared/types';
import {Tooltip as RechartsTooltip, Sankey as SankeyChart} from 'recharts';
import {Tooltip} from './Tooltip';

interface ISankeyProps {
	data: any;
	rangeSelectors: RangeSelectors;
	width: number;
}

const Sankey: React.FC<ISankeyProps> = ({data, rangeSelectors, width}) => {
	const [hovered, setMouseEnter] = useState(false);
	const [selectedNode, setSelectedNode] = useState(null);

	return (
		<SankeyChart
			className='analytics-sankey'
			data={data}
			height={SANKEY_HEIGHT}
			link={
				<Link
					hovered={hovered}
					onNodeChange={setSelectedNode}
					selectedNode={selectedNode}
				/>
			}
			linkCurvature={0.3}
			margin={{bottom: 30, right: 20, top: 60}}
			node={
				<Node
					hovered={hovered}
					onNodeChange={setSelectedNode}
					rangeSelectors={rangeSelectors}
					selectedNode={selectedNode}
				/>
			}
			nodePadding={80}
			nodeWidth={MAIN_NODE_WIDTH}
			onMouseEnter={() => {
				setMouseEnter(true);
			}}
			onMouseLeave={() => {
				setMouseEnter(false);
			}}
			sort={false}
			width={width}
		>
			<RechartsTooltip
				allowEscapeViewBox={{x: true, y: true}}
				content={<Tooltip />}
			/>
		</SankeyChart>
	);
};

export default Sankey;
