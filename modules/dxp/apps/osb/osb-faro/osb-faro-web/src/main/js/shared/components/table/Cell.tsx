import React from 'react';

interface ICellProps {
	children: React.ReactNode;
	className?: string;
	title?: boolean;
}

const Cell: React.FC<ICellProps> = ({children, className, title}) => (
	<td className={className}>
		{title ? (
			<h4 className='table-title text-truncate'>{children}</h4>
		) : (
			children
		)}
	</td>
);

export default Cell;
