import getCN from 'classnames';
import React from 'react';

interface IBodyProps extends React.HTMLAttributes<HTMLDivElement> {
	inlineScroller?: boolean;
}

const Body: React.FC<IBodyProps> = ({
	children,
	className,
	inlineScroller = false
}) => (
	<div
		className={getCN('modal-body', className, {
			'inline-scroller': inlineScroller
		})}
	>
		{children}
	</div>
);

export default Body;
