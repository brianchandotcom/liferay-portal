import {Colors, nextColor} from 'shared/util/charts';
import {getWrappedText} from './edges';
import {Link, Node} from './types';
import {removeProtocol} from 'shared/util/util';
import {sankey} from 'd3-sankey';

export const SANKEY_NODE_WIDTH = 200;
export const SANKEY_NODE_PADDING = 100;
export const SANKEY_PARENT_NODE_HEIGHT = 220;
export const SANKEY_OFFSET = 71;
export const SANKEY_DEFAULT_HEIGHT = 720;
export const SANKEY_STARTER_HEIGHT = 410;
export const ASSET_HEIGHT = 32;
export const EXPANDED_NODE_OFFSET = 40;

export function isParentNode(node) {
	return Object.prototype.hasOwnProperty.call(node, 'directAccessMetric');
}

export function getParentNode(nodes) {
	return nodes.find(isParentNode);
}

export enum SANKEY_COLORS {
	bgDirectTraffic = '#50D2A0',
	bgGray = '#D7D7DA',
	bgInactive = '#DCDDE1',
	bgPage = '#F1F2F5',
	bgShapeMain = '#FFFFFF',
	directTraffic = '#000000',
	link = '#B5B5B5',
	title = '#000000',
	views = '#6C6C76'
}

export function getNodeColor(node, activeIndex) {
	const {color, index} = node;

	if (activeIndex > -1 && activeIndex !== index) {
		return SANKEY_COLORS.bgGray;
	}

	if (color) {
		return color;
	}

	if (isParentNode(node)) {
		return SANKEY_COLORS.bgDirectTraffic;
	}

	return nextColor(index);
}

export function getCenterY(nodes) {
	const minAndMax = nodes.reduce((result, {y0, y1}) => {
		result[0] = y0 < result[0] || result[0] === undefined ? y0 : result[0];
		result[1] = y1 > result[1] || result[1] === undefined ? y1 : result[1];
		return result;
	}, []);

	return (minAndMax[0] + minAndMax[1]) / 2;
}

export function getDeltaY(parentNode, centerY) {
	const currentCenter = parentNode.y0 + SANKEY_PARENT_NODE_HEIGHT / 2;

	return Math.abs(centerY - currentCenter);
}

export function getInternalData(sankeyData, width, height) {
	const xMargin = 100;
	const yMargin = 60;

	const makeSankey = sankey(sankeyData)
		.iterations(0)
		.nodeWidth(SANKEY_NODE_WIDTH)
		.nodePadding(SANKEY_NODE_PADDING)
		.extent([
			[xMargin, yMargin],
			[width - xMargin, height - yMargin]
		]);

	return makeSankey(sankeyData);
}

export function getSankeyHeight(nodes) {
	const height = SANKEY_STARTER_HEIGHT;

	if (height < SANKEY_DEFAULT_HEIGHT && nodes.length && nodes.length > 2) {
		return SANKEY_DEFAULT_HEIGHT;
	}

	return height;
}

export function getAssetsHeight(items) {
	return items.length * ASSET_HEIGHT;
}

export function calcSankeyNodePosition(expandedTouchpoint, node, y0) {
	const margin = 13;

	const assetsHeight =
		expandedTouchpoint && node.index > expandedTouchpoint.index
			? getAssetsHeight(expandedTouchpoint.items) + EXPANDED_NODE_OFFSET
			: 25;

	return y0 + assetsHeight + margin;
}

export function calcExpandedTouchpointBoxPosition(expandedTouchpoint, {index}) {
	return expandedTouchpoint && index === expandedTouchpoint.index
		? getAssetsHeight(expandedTouchpoint.items) + EXPANDED_NODE_OFFSET
		: 30;
}

export const getBounds = svgRef => {
	if (svgRef.current) {
		const bounds = svgRef.current.getBoundingClientRect();

		return bounds;
	}

	return {};
};

export const getSankeyData = (
	{page},
	router
): {
	links: Array<Link>;
	nodes: Array<Node>;
} => {
	const {
		directAccessMetric,
		indirectAccessMetric,
		pageReferrerMetrics,
		viewsMetric
	} = page;

	const {title, touchpoint} = router.params;

	const referrers = pageReferrerMetrics.filter(
		({accessMetric: {value}}) => value > 0
	);

	let nodes = referrers.map(
		({assetTitle, external, referrer}): Node => {
			const url = referrer || '';
			const name =
				url === 'others'
					? Liferay.Language.get('others')
					: assetTitle || removeProtocol(url);

			const node: Node = {
				external,
				name,
				url,
				wrappedText: getWrappedText(name)
			};

			if (url === 'others') {
				node.color = Colors.gray;
			}

			return node;
		}
	);

	nodes = [
		...nodes,
		{
			directAccessMetric: directAccessMetric.value,
			indirectAccessMetric: indirectAccessMetric.value,
			name: decodeURIComponent(title),
			total: viewsMetric.value,
			url: touchpoint,
			wrappedText: getWrappedText(decodeURIComponent(title))
		}
	];

	const links = referrers.map(
		({accessMetric}, index): Link => ({
			source: index,
			target: nodes.length - 1,
			value: accessMetric.value
		})
	);

	return {
		links,
		nodes
	};
};
