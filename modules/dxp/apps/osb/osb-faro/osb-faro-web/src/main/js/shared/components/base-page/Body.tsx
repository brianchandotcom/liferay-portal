import getCN from 'classnames';
import React from 'react';

interface IBodyProps extends React.HTMLAttributes<HTMLElement> {
	disabled?: boolean;
	pageContainer?: boolean;
}

const Body: React.FC<IBodyProps> = ({
	children,
	className,
	disabled,
	pageContainer = true
}) => (
	<div
		className={getCN(
			'body-root',
			{
				disabled,
				'page-container': pageContainer
			},
			className
		)}
	>
		<span className='children-wrapper'>{children}</span>
	</div>
);

export default Body;
