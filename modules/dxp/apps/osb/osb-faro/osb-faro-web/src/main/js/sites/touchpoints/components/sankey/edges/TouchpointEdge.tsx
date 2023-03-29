import AssetsQuery from 'shared/queries/AssetsQuery';
import Header from './Header';
import React, {useState} from 'react';

import {AssetNode, SankeyNode} from '../utils/types';
import {assetTypeLabels, getWrappedText} from '../utils/edges';
import {
	calcExpandedTouchpointBoxPosition,
	calcSankeyNodePosition,
	getNodeColor
} from '../utils/sankey';
import {getVariables} from 'shared/util/mappers';
import {RangeSelectors} from 'shared/types';
import {toThousands} from 'shared/util/numbers';
import {useQuery} from '@apollo/react-hooks';

const CLASSNAME = 'analytics-sankey';

interface ITouchpointEdgeProps extends React.SVGAttributes<SVGElement> {
	activeIndex: number;
	expandedTouchpoint: SankeyNode;
	node: SankeyNode;
	rangeSelectors: RangeSelectors;
	router: {
		params: object;
		query: object;
	};
	setActiveIndex: (index: number) => void;
	setExpandedTouchpoint: (object) => void;
}

const TouchpointEdge: React.FC<ITouchpointEdgeProps> = ({
	activeIndex,
	expandedTouchpoint,
	node,
	rangeSelectors,
	router,
	setActiveIndex,
	setExpandedTouchpoint
}) => {
	const {index, url, value, x0, x1, y0, y1} = node;

	const [items, setItems] = useState<Array<AssetNode>>([]);

	const {variables} = getVariables({params: router.params, rangeSelectors});

	const {loading} = useQuery(AssetsQuery, {
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
		variables: {
			...variables,
			touchpoint: decodeURIComponent(url)
		}
	});

	const calculatedY0 = calcSankeyNodePosition(expandedTouchpoint, node, y0);

	const height = y1 - y0;
	const rectPosition = calcExpandedTouchpointBoxPosition(
		expandedTouchpoint,
		node
	);

	const informationsY = !loading && !items.length ? 20 : 0;

	return (
		<svg
			className={`${CLASSNAME}-node-group`}
			data-index={index}
			onMouseEnter={({currentTarget}) =>
				setActiveIndex(parseInt(currentTarget.dataset.index))
			}
			onMouseLeave={() => setActiveIndex(-1)}
			x={x0}
			y={calculatedY0 - 30}
		>
			{/* percentage bar color */}
			<rect
				className={`${CLASSNAME}-node`}
				fill={getNodeColor(node, activeIndex)}
				fillOpacity={1}
				height={y1 - y0}
				stroke='none'
				width={x1 - x0}
				y={rectPosition}
			/>

			<g className={`${CLASSNAME}-information`}>
				<Header
					activeIndex={activeIndex}
					expandedTouchpoint={expandedTouchpoint}
					items={items}
					loading={loading}
					node={{
						...node,
						views: value,
						x: x0,
						y: informationsY
					}}
					router={router}
					setExpandedTouchpoint={setExpandedTouchpoint}
				/>
			</g>

			{/* value */}
			<text
				className={`${CLASSNAME}-numbers-of-views`}
				dy={5}
				textAnchor='middle'
				x={100}
				y={rectPosition + height / 2}
			>
				{toThousands(value)}
			</text>
		</svg>
	);
};

export default TouchpointEdge;
