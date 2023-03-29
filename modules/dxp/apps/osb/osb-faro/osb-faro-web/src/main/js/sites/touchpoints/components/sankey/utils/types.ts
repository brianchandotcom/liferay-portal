export type SankeyNode = Node & {
	index: number;
	sourceLinks: Array<Node>;
	targetLinks: Array<Node>;
	x0: number;
	x1: number;
	y0: number;
	y1: number;
	value: number;
};

export type Node = {
	color?: string;
	directAccessMetric?: number;
	external: boolean;
	indirectAccessMetric?: number;
	name: string;
	total?: number;
	url: string;
	wrappedText: {
		lines: Array<string>;
		sentence: string;
		truncated: boolean;
	};
};

export type SankeyLink = Link & {
	index: number;
	source: SankeyNode;
	target: SankeyNode;
	width: number;
	y0: number;
	y1: number;
};

export type Link = {
	source: number;
	target: number;
	value: number;
};

export type AssetNode = {
	assetId?: string;
	assetType?: string;
	id: string;
	interactions: number;
	title: string;
	type: string;
	wrappedText: {
		lines: Array<string>;
		sentence: string;
		truncated: boolean;
	};
};
