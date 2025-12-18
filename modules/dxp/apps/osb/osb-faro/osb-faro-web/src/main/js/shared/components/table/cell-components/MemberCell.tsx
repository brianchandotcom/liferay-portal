import getCN from 'classnames';
import React from 'react';
import {isBlank} from 'shared/util/util';
import {Text} from '@clayui/core';

interface IMemberCellProps {
	className?: string;
	data: {
		name: string;
		emailAddress: string;
	};
}

const MemberCell: React.FC<IMemberCellProps> = ({className, data}) => {
	const {emailAddress, name = '-'} = data;

	const anonymous = isBlank(emailAddress);

	return (
		<td className={getCN('name-cell-root', className)}>
			<div className='text-dark'>
				<Text size={3} weight='semi-bold'>
					{name}
				</Text>
			</div>
			{!anonymous && (
				<Text color='secondary' size={3}>
					{emailAddress}
				</Text>
			)}
		</td>
	);
};

export default MemberCell;
