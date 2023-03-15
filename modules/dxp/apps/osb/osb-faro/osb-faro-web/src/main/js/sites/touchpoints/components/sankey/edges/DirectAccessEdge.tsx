import AssetsQuery from 'shared/queries/AssetsQuery';
import HTMLBox from './HTMLBox';
import React, {useState} from 'react';
import {AssetNode, SankeyNode} from '../utils/types';
import {assetTypeLabels, getWrappedText} from '../utils/edges';
import {getVariables} from 'shared/util/mappers';
import {RangeSelectors} from 'shared/types';
import {SANKEY_COLORS} from '../utils/sankey';
import {useQuery} from '@apollo/react-hooks';

export const CLASSNAME = 'analytics-sankey';
export const CLASSNAME_BOX = `${CLASSNAME}-parent`;

interface IDirectAccessEdge extends React.HTMLAttributes<HTMLElement> {
	node: SankeyNode;
	rangeSelectors: RangeSelectors;
	router: {
		params: object;
		query: object;
	};
}

const DirectAccessEdge: React.FC<IDirectAccessEdge> = ({
	node,
	rangeSelectors,
	router
}) => {
	const [items, setItems] = useState<Array<AssetNode>>([]);

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

	return (
		<div className={CLASSNAME_BOX}>
			<HTMLBox
				color={SANKEY_COLORS.bgDirectTraffic}
				items={items}
				node={node}
			/>
		</div>
	);
};

export default DirectAccessEdge;
