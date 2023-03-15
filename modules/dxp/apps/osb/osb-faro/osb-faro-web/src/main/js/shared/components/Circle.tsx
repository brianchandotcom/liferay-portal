import getCN from 'classnames';
import React from 'react';

interface ICircleProps extends React.HTMLAttributes<HTMLElement> {
	color?: string;
	size?: number;
}

const Circle: React.FC<ICircleProps> = ({
	children,
	className,
	color,
	size = 8
}) => (
	<span
		className={getCN('circle', className)}
		style={{
			backgroundColor: color,
			height: `${size}px`,
			width: `${size}px`
		}}
	>
		{children}
	</span>
);

export default Circle;
