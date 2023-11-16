export const MAIN_NODE_COLOR = '#50D2A0';

export const MAIN_NODE_HEIGHT = 80;

export const MAIN_NODE_WIDTH = 120;

export const SANKEY_HEIGHT = 600;

export const SANKEY_WIDTH = 1200;

export const EMPTY_NODE_COLOR = '#E7E7ED';

export const SECONDARY_NODE_COLOR = '#A7A9BC';

export const CHART_COLORS = [
	'#4B9BFF',
	'#FFB46E',
	'#FF5F5F',
	'#B077FF',
	'#FF73C3',
	'#FFD76E',
	'#9CE269',
	'#5FC8FF'
];

export const URL_COLOR = '#6B6C7E';

export function getFill({hovered, index, payload, selectedNode}) {
	if (payload.main) {
		return MAIN_NODE_COLOR;
	}

	if (hovered && payload.id !== selectedNode) {
		return SECONDARY_NODE_COLOR;
	}

	return payload.color || CHART_COLORS[index];
}
