import Circle from 'shared/components/Circle';
import React from 'react';

const CLASSNAME_LEGEND = 'chart-legend';
const CLASSNAME_LEGEND_ITEM = 'chart-legend-item';

export type LegendData = {
	color: string;
	name: string;
};

interface LegendIProps extends React.HTMLAttributes<HTMLElement> {
	data: Array<LegendData>;
}

const Legend: React.FC<LegendIProps> = ({data, ...otherProps}) => (
	<ul className={CLASSNAME_LEGEND} {...otherProps}>
		{data.map(({color, name}, index) => (
			<li className={CLASSNAME_LEGEND_ITEM} key={index}>
				<Circle color={color} /> {` ${name}`}
			</li>
		))}
	</ul>
);

export default Legend;
