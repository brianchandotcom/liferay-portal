import {getParentNode, SANKEY_NODE_WIDTH} from './sankey';
import {
	getPercentage,
	getRangeSelectorsFromQuery,
	truncateText
} from 'shared/util/util';
import {getUrl} from 'shared/util/urls';
import {pickBy} from 'lodash';
import {Routes, toAssetOverviewRoute} from 'shared/util/router';
import {SankeyNode} from './types';
import {textWrap} from 'd3plus-text';
import {toThousands, undoThousands} from 'shared/util/numbers';

export const assetTypeLabels = {
	blog: Liferay.Language.get('blogs'),
	custom: Liferay.Language.get('custom'),
	document: Liferay.Language.get('documents-and-media'),
	form: Liferay.Language.get('forms'),
	journal: Liferay.Language.get('web-content')
};

export function getAssetUrl(
	{id: assetId, title, type},
	touchpoint,
	{params, query}
) {
	const rangeSelectors = getRangeSelectorsFromQuery(query);

	return toAssetOverviewRoute(
		type,
		{
			...params,
			assetId,
			title,
			touchpoint,
			...(type === 'custom' ? {id: assetId} : false)
		},
		pickBy({...query, rangeKey: rangeSelectors.rangeKey})
	);
}

export function getTouchpointUrl(title, touchpoint, {params, query}) {
	const rangeSelectors = getRangeSelectorsFromQuery(query);

	const router = {
		params: {
			...params,
			title,
			touchpoint: encodeURIComponent(touchpoint)
		},
		query: {
			...query,
			rangeKey: rangeSelectors.rangeKey
		}
	};

	return getUrl(Routes.SITES_TOUCHPOINTS_OVERVIEW, router);
}

export function getSize(value) {
	if (isNaN(value) || Math.sign(value) === -1) {
		return 0;
	}

	return value;
}

export function getTitleY({directAccessMetric}: SankeyNode, items) {
	let titleY = 0;

	if (items.length) {
		titleY = directAccessMetric === 0 ? 220 : 183;
	} else {
		titleY = directAccessMetric === 0 ? 250 : 210;
	}

	return titleY;
}

export function getTotalViews({
	directAccessMetric,
	indirectAccessMetric
}: SankeyNode) {
	return (
		undoThousands(toThousands(directAccessMetric)) +
		undoThousands(toThousands(indirectAccessMetric))
	);
}

export function getMarginY(touchpointList) {
	let marginY = 93;

	if (touchpointList.length <= 2) {
		marginY = 223;
	} else if (touchpointList.length == 3) {
		marginY = 183;
	}

	return marginY;
}

export function getNodeHeight({value}: SankeyNode, nodes: Array<SankeyNode>) {
	const parentNode = getParentNode(nodes);

	return (
		((parentNode.y1 - parentNode.y0) *
			getPercentage(value, parentNode.value)) /
		100
	);
}

export function getWrappedText(name, fontSize = 16) {
	const textWrapper = textWrap()
		.fontSize(fontSize)
		.height(55)
		.overflow(true)
		.width(SANKEY_NODE_WIDTH);

	const defaultCharacterLimit = 20;

	let wrappedText;

	try {
		wrappedText = textWrapper(name);
	} catch (e) {
		wrappedText = {
			lines: [truncateText(name, defaultCharacterLimit, '')],
			truncated: name.length > defaultCharacterLimit
		};
	}

	const constrainLastLineLength = lines => {
		const lastLine = lines[lines.length - 1];

		const linesWithoutLastLine = lines.slice(0, lines.length - 1);

		if (lastLine.length > defaultCharacterLimit && !/[\s]/.test(lastLine)) {
			return linesWithoutLastLine.concat(
				lastLine.substr(0, defaultCharacterLimit)
			);
		}

		return lines;
	};

	return {
		...wrappedText,
		lines: constrainLastLineLength(wrappedText.lines)
	};
}
