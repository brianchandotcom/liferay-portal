import React, {useState} from 'react';
import {Link} from './Link';
import {
	MAIN_NODE_HEIGHT,
	MAIN_NODE_WIDTH,
	SANKEY_HEIGHT,
	SANKEY_WIDTH
} from './utils';
import {Node} from './Node';
import {Sankey as SankeyChart, Tooltip} from 'recharts';
import {sub} from 'shared/util/lang';
import {toLocale} from 'shared/util/numbers';

const CustomTooltip: React.FC<any> = ({payload}) => {
	if (!payload.length) return null;

	const data = payload[0].payload?.payload;

	let description = Liferay.Language.get('page-views');

	if (!data?.main) {
		if (data?.type === 'previous' || data?.target?.main) {
			description = sub(Liferay.Language.get('page-views-x'), [
				Liferay.Language.get('referral')
			]);
		} else {
			description = sub(Liferay.Language.get('page-views-x'), [
				Liferay.Language.get('exit-pages')
			]);
		}
	}

	return (
		<div className='clay-popover-top fade popover position-relative show'>
			<div className='popover-header'>{description}</div>
			<div className='popover-body d-flex justify-content-between'>
				<div className='mr-2'>{payload[0].name}</div>
				<div>{toLocale(payload[0].value)}</div>
			</div>
		</div>
	);
};

const Sankey = ({data}) => {
	const [hovered, setMouseEnter] = useState(false);
	const [selectedNode, setSelectedNode] = useState(null);

	const emptyState = !data.links.length && data.nodes.length === 1;

	return (
		/**
		 * TODO: It is necessary to update Recharts to latest version
		 * to be able to sort main path on center of the card.
		 * But it will break changes on Recharts API
		 *
		 * 1. Remove interations props
		 * 2. Add sort={false}
		 */

		<SankeyChart
			data={data}
			height={emptyState ? MAIN_NODE_HEIGHT : SANKEY_HEIGHT}
			iterations={0}
			link={
				<Link
					hovered={hovered}
					onNodeChange={setSelectedNode}
					selectedNode={selectedNode}
				/>
			}
			linkCurvature={0.3}
			margin={{bottom: 30, top: 50}}
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
			width={emptyState ? MAIN_NODE_WIDTH : SANKEY_WIDTH}
		>
			<Tooltip content={<CustomTooltip />} />
		</SankeyChart>
	);
};

export default Sankey;
