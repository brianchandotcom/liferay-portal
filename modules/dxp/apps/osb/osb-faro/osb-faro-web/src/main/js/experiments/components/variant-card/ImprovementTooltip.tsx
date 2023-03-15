import React from 'react';
import Trend from 'shared/components/Trend';
import {Colors} from 'shared/util/charts';

const GREEN_COLOR = Colors.positive;
const RED_COLOR = Colors.negative;

interface ImprovementTooltipIProps extends React.HTMLAttributes<HTMLElement> {
	improvement: number;
}

const ImprovementCell: React.FC<ImprovementTooltipIProps> = ({
	improvement,
	...otherProps
}) => {
	const icon = improvement > 0 ? 'caret-top' : 'caret-bottom';
	const color =
		improvement === 0 ? '' : improvement > 0 ? GREEN_COLOR : RED_COLOR;

	return (
		<span {...otherProps}>
			{improvement !== 0 ? (
				<Trend color={color} icon={icon} label={`${improvement}%`} />
			) : (
				'-'
			)}
		</span>
	);
};

export default ImprovementCell;
