import getCN from 'classnames';
import getSVG from '../util/svg';
import React from 'react';

export type Size = 'sm' | 'md' | 'lg' | 'xl' | 'xxl' | 'xxxl';

export enum Colors {
	Main = 'main',
	MainLighten4 = 'mainLighten4',
	MainLighten8 = 'mainLighten8',
	MainLighten28 = 'mainLighten28',
	MainLighten52 = 'mainLighten52',
	MainLighten65 = 'mainLighten65',
	MainLighten74 = 'mainLighten74',
	Secondary = 'secondary',
	SecondaryHover = 'secondaryHover',
	Primary = 'primary',
	PrimaryDarken5 = 'primaryDarken5',
	PrimaryDarken10 = 'primaryDarken10',
	PrimaryLighten23 = 'primaryLighten23',
	PrimaryLighten33 = 'primaryLighten33',
	PrimaryLighten45 = 'primaryLighten45',
	White = 'white',
	Black = 'black',
	LightBackground = 'lightBackground',
	Error = 'error',
	ErrorLighten28 = 'errorLighten28',
	ErrorLighten50 = 'errorLighten50',
	Success = 'success',
	SuccessLighten25 = 'successLighten25',
	SuccessLighten63 = 'successLighten63',
	Warning = 'warning',
	WarningLighten25 = 'warningLighten25',
	WarningLighten60 = 'warningLighten60',
	Info = 'info',
	InfoLighten28 = 'infoLighten28',
	InfoLighten53 = 'infoLighten53'
}

interface IIconProps extends React.SVGProps<SVGSVGElement> {
	color?: Colors;
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
