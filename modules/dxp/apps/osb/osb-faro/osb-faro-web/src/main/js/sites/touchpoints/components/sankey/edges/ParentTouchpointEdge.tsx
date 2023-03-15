import AssetsQuery from 'shared/queries/AssetsQuery';
import Header from './Header';
import React, {useState} from 'react';
import {
	assetTypeLabels,
	getMarginY,
	getNodeHeight,
	getSize,
	getTitleY,
	getTotalViews,
	getWrappedText
} from '../utils/edges';
import {
	calcExpandedTouchpointBoxPosition,
	calcSankeyNodePosition,
	getCenterY,
	getDeltaY,
	getNodeColor,
	isParentNode,
	SANKEY_COLORS
} from '../utils/sankey';
import {Colors} from 'shared/util/charts';
import {getVariables} from 'shared/util/mappers';
import {RangeSelectors} from 'shared/types';
import {SankeyNode} from '../utils/types';
import {toRounded, toThousands} from 'shared/util/numbers';
import {useQuery} from '@apollo/react-hooks';

const CLASSNAME = 'analytics-sankey';

interface IParentEdgeProps extends React.SVGAttributes<SVGElement> {
	activeIndex: number;
	expandedTouchpoint: SankeyNode;
	hasOnlyOneReferrer: boolean;
	node: SankeyNode;
	nodes: Array<SankeyNode>;
	rangeSelectors: RangeSelectors;
	router: {
		params: object;
		query: object;
	};
	setActiveIndex: (index: number) => void;
	setExpandedTouchpoint: (object) => void;
}

const ParentEdge: React.FC<IParentEdgeProps> = ({
	activeIndex,
	expandedTouchpoint,
	hasOnlyOneReferrer,
	node,
	nodes,
	rangeSelectors,
	router,
	setActiveIndex,
	setExpandedTouchpoint
}) => {
	const [items, setItems] = useState([]);

	const {variables} = getVariables({params: router.params, rangeSelectors});

	useQuery(AssetsQuery, {
		onCompleted: ({assets}) => {
			const items = assets.map(
				({assetId, assetTitle, assetType, defaultMetric}) => ({
					assetId,
					assetType,
					interactions: defaultMetric.value,
					title: assetTitle || assetId,
					type: assetTypeLabels[assetType],
					wrappedText: getWrappedText(assetTitle || assetId)
				})
			);

			setItems(items);
		},
		skip: !!items.length,
		variables
	});

	const handleMouseEnter = ({currentTarget}) => {
		setActiveIndex(parseInt(currentTarget.dataset.index));
	};

	const handleMouseLeave = () => {
		setActiveIndex(-1);
	};

	const centerY = getCenterY(nodes);

	const {directAccessMetric, indirectAccessMetric, x0, x1, y0, y1} = node;
	const height = getSize(y1 - y0);
	const deltaY = getSize(getDeltaY(node, centerY));
	const rectPosition = getSize(
		calcExpandedTouchpointBoxPosition(expandedTouchpoint, node) + 230
	);
	const calculatedY0 = calcSankeyNodePosition(
		expandedTouchpoint,
		node,
		node.y0
	);
	const titleY = getTitleY(node, items);
	const totalViews = getTotalViews(node);
	const marginY = getMarginY(nodes);

	const renderParentNodeRainbow = (parentX, parentY, opacity = 1) => {
		const width = 200;

		let nodeY0 = parentY.y0;
		let previousNodeHeight = 0;

		const heightOffset = nodes.length <= 3 ? 1 : 0;

		return nodes
			.filter(node => !isParentNode(node))
			.map(node => {
				const index = node.index;

				nodeY0 += previousNodeHeight;

				const nodeHeight = getNodeHeight(node, nodes);

				previousNodeHeight = nodeHeight;

				return (
					<svg key={`${index}${opacity}_barColor`}>
						<rect
							className={`${CLASSNAME}-node`}
							data-index={index}
							fill={getNodeColor(node, activeIndex)}
							fillOpacity={opacity}
							height={nodeHeight + heightOffset}
							onMouseEnter={handleMouseEnter}
							onMouseLeave={handleMouseLeave}
							width={width}
							x={parentX.x0}
							y={nodeY0}
						/>
						<line
							data-index={index}
							onMouseEnter={handleMouseEnter}
							onMouseLeave={handleMouseLeave}
							stroke={SANKEY_COLORS.bgPage}
							strokeOpacity={opacity}
							strokeWidth={2}
							x1={parentX.x0}
							x2={parentX.x1}
							y1={nodeY0}
							y2={nodeY0}
						/>
					</svg>
				);
			});
	};

	return (
		<svg x={x0} y={calculatedY0 - marginY}>
			{/* percentage bar color */}
			{renderParentNodeRainbow(
				{x0: 0, x1},
				{y0: rectPosition, y1: y1 + deltaY}
			)}

			{/* background white */}
			{!hasOnlyOneReferrer && (
				<rect
					className={`${CLASSNAME}-node`}
					fill={SANKEY_COLORS.bgShapeMain}
					fillOpacity={0.8}
					height={getSize(parseInt(toRounded(height)) - 20)}
					stroke='none'
					width={getSize(x1 - x0 - 20)}
					x={10}
					y={rectPosition + 10}
				/>
			)}

			{/* percentage bar color without opacity to handle mouse hover*/}
			{renderParentNodeRainbow(
				{x0: 0, x1},
				{y0: rectPosition, y1: y1 + deltaY},
				0
			)}

			{directAccessMetric != 0 && (
				<g>
					{/* background direct traffic */}
					<rect
						className={`${CLASSNAME}-node`}
						fill={getNodeColor(node, activeIndex)}
						height={40}
						width={getSize(x1 - x0)}
						y={rectPosition - 41}
					/>

					{/* text direct traffic number */}
					<text
						className={`${CLASSNAME}-title`}
						dy={-14}
						fill={SANKEY_COLORS.directTraffic}
						textAnchor='middle'
						x={100}
						y={rectPosition}
					>
						{toThousands(directAccessMetric)}
					</text>

					<title>{Liferay.Language.get('direct-traffic')}</title>
				</g>
			)}

			{/* title */}
			<svg y={titleY}>
				<g>
					<Header
						activeIndex={activeIndex}
						expandedTouchpoint={expandedTouchpoint}
						isParentNode
						items={items}
						node={{
							...node,
							color: Colors.gray,
							views: totalViews,
							x: 0,
							y: 0
						}}
						router={router}
						setExpandedTouchpoint={setExpandedTouchpoint}
					/>
				</g>
			</svg>

			{/* value */}
			<text
				className={`${CLASSNAME}-numbers-of-views`}
				dy={5}
				textAnchor='middle'
				x={100}
				y={rectPosition + height / 2}
			>
				{toThousands(indirectAccessMetric)}
			</text>
		</svg>
	);
};

export default ParentEdge;
