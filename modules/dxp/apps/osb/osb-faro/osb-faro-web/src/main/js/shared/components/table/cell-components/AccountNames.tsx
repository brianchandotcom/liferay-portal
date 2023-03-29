import getCN from 'classnames';
import React from 'react';

interface IAccountNamesProps {
	className?: string;
	data: {
		accountNames: string[];
	};
}

const AccountNames: React.FC<IAccountNamesProps> = ({
	className,
	data: {accountNames}
}) => (
	<td className={getCN('name-cell-root', className)}>
		<div className='text-truncate'>
			{accountNames && accountNames.length
				? accountNames.join(', ')
				: '-'}
		</div>
	</td>
);

export default AccountNames;
