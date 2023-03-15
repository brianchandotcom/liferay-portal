import React from 'react';
import Title from './Title';
import {ASSET_HEIGHT, getNodeColor, SANKEY_COLORS} from '../utils/sankey';
import {AssetNode, SankeyNode} from '../utils/types';
import {CLASSNAME} from '../Sankey';
import {getAssetUrl, getTouchpointUrl, getWrappedText} from '../utils/edges';
import {sub} from 'shared/util/lang';
import {toThousands} from 'shared/util/numbers';

interface IHeaderProps extends React.SVGAttributes<SVGElement> {
	activeIndex: number;
	expandedTouchpoint: SankeyNode;
	items: Array<AssetNode>;
	isParentNode?: boolean;
	loading?: boolean;
	node: SankeyNode & {
		color?: string;
		views?: number;
		x: number;
		y: number;
	};
	router: {
		params: object;
		query: object;
	};
	setExpandedTouchpoint: (object) => void;
}

const Header: React.FC<IHeaderProps> = ({
	activeIndex,
	expandedTouchpoint,
	isParentNode = false,
	items,
	loading = false,
	node,
	router,
	setExpandedTouchpoint
}) => {
	const {external, index, name, url, views, wrappedText, y} = node;

	const {lines} = wrappedText;

	const handleShowMoreAssets = e => {
		const {nodeIndex} = e.currentTarget.dataset;
		const index = parseInt(nodeIndex);

		if (expandedTouchpoint && expandedTouchpoint.index === index) {
			return false;
		}

		setExpandedTouchpoint &&
			setExpandedTouchpoint({
				index,
				items
			});

		return false;
	};

	const handleCloseAssets = () => {
		setExpandedTouchpoint && setExpandedTouchpoint(null);
	};

	const handleTouchpointUrl = () => {
		if (
			!external &&
			url !== Liferay.Language.get('others').toLowerCase() &&
			!isParentNode
		) {
			return getTouchpointUrl(name, url, router);
		}
	};

	const renderGroupedInformation = () => (
		<>
			{/* Assets */}
			{items.map(
				(
					{assetId, assetType, wrappedText, ...otherAssetProps},
					index
				) => (
					<Title
						color={getNodeColor(node, activeIndex)}
						heightOffset={lines.length > 1 ? -1 : 7}
						iconLetter='A'
						key={index}
						parentLines={lines.length}
						radius={9}
						textClass={`${CLASSNAME}-subtitle`}
						title={wrappedText}
						url={getAssetUrl(
							{
								...otherAssetProps,
								id: assetId,
								type: assetType
							},
							url,
							router
						)}
						y={y - 1 + ASSET_HEIGHT * (index + 1)}
					/>
				)
			)}

			{/* Close Button*/}
			<g data-node-index={index} onClick={handleCloseAssets}>
				<Title
					color={getNodeColor(node, activeIndex)}
					hasOnClick
					heightOffset={lines.length > 1 ? -1 : 7}
					iconLetter='-'
					isCloseButton
					parentLines={lines.length}
					radius={9}
					textClass={`${CLASSNAME}-subtitle-show-link`}
					title={getWrappedText(Liferay.Language.get('close-list'))}
					y={y - 1 + ASSET_HEIGHT * (items.length + 1)}
				/>
			</g>
		</>
	);

	const renderSingleInformation = () => {
		const {index, name, url, y} = node;

		return (
			<>
				{items.length > 1 && name !== Liferay.Language.get('others') && (
					<g data-node-index={index} onClick={handleShowMoreAssets}>
						<Title
							color={getNodeColor(node, activeIndex)}
							hasOnClick
							iconLetter='+'
							parentLines={lines.length}
							radius={9}
							textClass={`${CLASSNAME}-subtitle-show-link`}
							title={getWrappedText(
								sub(Liferay.Language.get('show-top-x-assets'), [
									items.length
								])
							)}
							y={y + 28}
						/>
					</g>
				)}

				{items.length === 1 && name !== Liferay.Language.get('others') && (
					<g>
						<Title
							color={getNodeColor(node, activeIndex)}
							iconLetter='A'
							parentLines={lines.length}
							radius={9}
							textClass={`${CLASSNAME}-subtitle`}
							title={items[0].wrappedText}
							url={getAssetUrl(
								{
									...items[0],
									id: items[0].assetId,
									type: items[0].assetType
								},
								url,
								router
							)}
							y={y + 28}
						/>
					</g>
				)}
			</>
		);
	};

	return (
		<>
			{/* total views */}
			<text
				className={`${CLASSNAME}-views`}
				fill={SANKEY_COLORS.views}
				x={25}
				y={y - (lines.length > 1 ? lines.length * 22 : 30)}
			>
				{`${toThousands(views)} ${Liferay.Language.get('views')}`}
			</text>

			<Title
				color={getNodeColor(node, activeIndex)}
				iconLetter='P'
				parentLines={lines.length}
				textClass={`${CLASSNAME}-title`}
				title={wrappedText}
				url={handleTouchpointUrl()}
				y={y}
			/>

			{loading && (
				<g>
					<Title
						color={getNodeColor(node, activeIndex)}
						iconLetter='+'
						parentLines={lines.length}
						radius={9}
						textClass={`${CLASSNAME}-subtitle-show-link`}
						title={getWrappedText(
							Liferay.Language.get('loading-assets')
						)}
						y={y + 28}
					/>
				</g>
			)}

			{expandedTouchpoint && expandedTouchpoint.index === index
				? renderGroupedInformation()
				: renderSingleInformation()}
		</>
	);
};

export default Header;
