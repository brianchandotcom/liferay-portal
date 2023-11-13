import React, {useState} from 'react';
import {Link} from './Link';
import {
	MAIN_NODE_HEIGHT,
	MAIN_NODE_WIDTH,
	SANKEY_HEIGHT,
	SANKEY_WIDTH
} from './utils';
import {Node} from './Node';
import {Tooltip as RechartsTooltip, Sankey as SankeyChart} from 'recharts';
import {Tooltip} from './Tooltip';

const Sankey = ({data}) => {
	const [hovered, setMouseEnter] = useState(false);
	const [selectedNode, setSelectedNode] = useState(null);

	const emptyState = !data.links.length && data.nodes.length === 1;

	return (
		<SankeyChart
			data={data}
			height={emptyState ? MAIN_NODE_HEIGHT : SANKEY_HEIGHT}
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
					emptyState={emptyState}
					hovered={hovered}
					onNodeChange={setSelectedNode}
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
			width={emptyState ? MAIN_NODE_WIDTH : SANKEY_WIDTH}
		>
			<RechartsTooltip
				allowEscapeViewBox={{x: true, y: true}}
				content={<Tooltip />}
			/>
		</SankeyChart>
	);
};

export default Sankey;
