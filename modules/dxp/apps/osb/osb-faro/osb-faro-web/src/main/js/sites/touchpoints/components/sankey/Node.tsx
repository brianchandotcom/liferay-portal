import React from 'react';
import {
	EMPTY_NODE_COLOR,
	getFill,
	MAIN_NODE_HEIGHT,
	MAIN_NODE_WIDTH,
	SANKEY_HEIGHT,
	URL_COLOR
} from './utils';
import {Layer, Rectangle} from 'recharts';
import {TitleKey, Type} from './types';
import {toThousands} from 'shared/util/numbers';

function truncateText(text: string, limit: number) {
	if (text.length > limit) {
		return `${text.substring(0, limit)}...`;
	}

	return text;
}

function getRadius(payload: {main: boolean; type: Type}) {
	if (payload.main) {
		return 0;
	}

	if (payload.type === Type.Previous) {
		return [5, 0, 0, 5];
	}

	return [0, 5, 5, 0];
}

function showURL(url?: TitleKey) {
	if (
		url &&
		url !== TitleKey.Direct &&
		url !== TitleKey.DropOffs &&
		url !== TitleKey.Others
	) {
		return true;
	}

	return false;
}

function normalizeNumber(number: number) {
	return isNaN(number) ? 0 : number;
}

export const Node = ({
	emptyState,
	height: initialHeight,
	hovered,
	index,
	onNodeChange = () => {},
	payload,
	selectedNode,
	width: initialWidth,
	x: initialX,
	y: initialY
}: any) => {
	const height = normalizeNumber(initialHeight);
	const width = normalizeNumber(initialWidth);
	const x = normalizeNumber(initialX);
	const y = normalizeNumber(initialY);

	return (
		<Layer
			crossOrigin={undefined}
			fr={undefined}
			key={`CustomNode${index}`}
			onMouseEnter={() => onNodeChange(payload.id)}
			onMouseLeave={() => onNodeChange(null)}
			path={undefined}
		>
			{emptyState ? (
				<>
					<rect
						fill={EMPTY_NODE_COLOR}
						height={MAIN_NODE_HEIGHT}
						width={MAIN_NODE_WIDTH}
						y={y}
					/>

					<text
						x={MAIN_NODE_WIDTH / 2 - 2}
						y={MAIN_NODE_HEIGHT / 2 + 60}
					>
						{toThousands(payload.value)}
					</text>
				</>
			) : (
				<>
					<Rectangle
						fill={getFill({
							hovered,
							index: index - 1,
							payload,
							selectedNode
						})}
						fillOpacity='1'
						height={height}
						radius={getRadius(payload) as number}
						width={width}
						x={x}
						y={y}
					/>

					<text
						textAnchor='middle'
						x={x + width / 2}
						y={y + height / 2 + 5}
					>
						{toThousands(payload.value)}
					</text>
				</>
			)}

			<text
				fontSize='16'
				fontWeight={SANKEY_HEIGHT}
				textAnchor='start'
				x={x}
				y={showURL(payload.url) ? y - 28 : y - 16}
			>
				{truncateText(payload.name, 15)}
			</text>

			{showURL(payload.url) && (
				<text
					fill={URL_COLOR}
					fontSize='12'
					fontWeight={400}
					textAnchor='start'
					x={x}
					y={y - 10}
				>
					{truncateText(payload.url, 25)}
				</text>
			)}
		</Layer>
	);
};
