import getCN from 'classnames';
import React from 'react';
import {CLASSNAME} from '../Sankey';
import {Link} from 'react-router-dom';

const ICON_RADIUS_TOUCHPOINT_SIZE = 12;
const TITLE_FONT_SIZE = 16;

export interface ITitleProps extends IIconProps {
	hasOnClick?: boolean;
	textClass: string;
	url?: string;
	title: {
		lines: Array<string>;
		sentence: string;
		truncated: boolean;
	};
}

interface IIconProps extends React.SVGAttributes<SVGElement> {
	color: string;
	heightOffset?: number;
	iconLetter: string;
	isCloseButton?: boolean;
	parentLines?: number;
	radius?: number;
	y: number;
}

const Title: React.FC<ITitleProps> = ({
	hasOnClick = false,
	textClass,
	title: {lines, sentence, truncated},
	url,
	y,
	...iconProps
}) => {
	const classes = getCN(textClass, {'text-truncated': lines.length > 1});

	const offsetY = (lines.length - 1) * TITLE_FONT_SIZE * -1 - 10;

	const Wrapper = ({children}) => {
		if (url) {
			return (
				<Link className={`${CLASSNAME}-text-button`} to={url}>
					{children}
				</Link>
			);
		} else if (hasOnClick) {
			return (
				<tspan className={`${CLASSNAME}-text-button`}>{children}</tspan>
			);
		}

		return children;
	};

	return (
		<>
			<Icon {...iconProps} y={y} />

			<text className={classes} y={y}>
				<Wrapper>
					<title>
						{sentence || Liferay.Language.get('untitled')}
					</title>

					{lines.map((line, index) => (
						<tspan
							dx={25}
							dy={offsetY + index * 14}
							key={`${index}line`}
							x={0}
							y={y}
						>
							{line}
							{truncated && index === lines.length - 1 && (
								// eslint-disable-next-line
								<tspan>&#8230;</tspan>
							)}
						</tspan>
					))}
				</Wrapper>
			</text>
		</>
	);
};

export default Title;

const Icon: React.FC<IIconProps> = ({
	color,
	heightOffset = 0,
	iconLetter,
	isCloseButton = false,
	parentLines = 1,
	radius = ICON_RADIUS_TOUCHPOINT_SIZE,
	y
}) => {
	const offsetY = (parentLines - 1) * 20 * -1 - 6;
	const rectHeight = parentLines * 11 + heightOffset;
	const margin =
		radius === ICON_RADIUS_TOUCHPOINT_SIZE ? 0 : (parentLines - 1) * 8;

	const textClasses = getCN(`${CLASSNAME}-icon-reference`, {
		'analytics-sankey-close-list': isCloseButton,
		'analytics-sankey-icon-normal': radius === ICON_RADIUS_TOUCHPOINT_SIZE,
		'analytics-sankey-icon-small': radius !== ICON_RADIUS_TOUCHPOINT_SIZE,
		'text-truncated': parentLines > 1
	});

	return (
		<>
			<circle
				cx={7}
				cy={y - 10 + margin + offsetY / 2}
				fill={color}
				r={radius}
			/>

			{/* rect between circles */}
			{radius < ICON_RADIUS_TOUCHPOINT_SIZE && (
				<rect
					fill={color}
					height={rectHeight}
					width='2'
					x={6}
					y={
						y -
						16 -
						12 * parentLines +
						margin -
						heightOffset +
						offsetY / 2
					}
				/>
			)}

			<text
				className={textClasses}
				x={8.5 - radius / 2}
				y={y - 11 + margin + offsetY / 2 + radius / 2}
			>
				{iconLetter}
			</text>
		</>
	);
};
