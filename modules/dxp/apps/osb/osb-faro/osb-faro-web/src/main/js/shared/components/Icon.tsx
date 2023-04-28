import getCN from 'classnames';
import getSVG from '../util/svg';
import React from 'react';

export type Size = 'sm' | 'md' | 'lg' | 'xl' | 'xxl' | 'xxxl';
interface IIconProps extends React.SVGProps<SVGSVGElement> {
	monospaced?: boolean;
	size?: Size;
	symbol: string;
}

const Icon: React.FC<IIconProps> = ({
	className,
	color,
	monospaced,
	size,
	symbol,
	...otherProps
}) => {
	const classes = getCN(
		'icon-root',
		'lexicon-icon',
		`lexicon-icon-${symbol}`,
		className,
		{
			[`${color}-color`]: color,
			['icon-monospaced']: monospaced,
			[`icon-size-${size}`]: size
		}
	);

	const svg = getSVG(symbol);

	return (
		<svg {...otherProps} className={classes} viewBox={svg.viewBox}>
			<use xlinkHref={`/o/osb-faro-web/dist/sprite.svg#${svg.id}`} />
		</svg>
	);
};

export default Icon;
