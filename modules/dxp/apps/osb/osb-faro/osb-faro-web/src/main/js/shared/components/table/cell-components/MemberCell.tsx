import getCN from 'classnames';
import React from 'react';
import {isBlank} from 'shared/util/util';
import {Text} from '@clayui/core';

interface IMemberCellProps {
	className?: string;
	data: {
		memberName: string;
		email: string;
		accountName?: string;
		firstSeenDate: string;
		lastActive: string;
		profileType: string;
		membershipChange: {
			modifiedDate: string;
			type: string;
		};
	};
}

const MemberCell: React.FC<IMemberCellProps> = ({className, data}) => {
	const {email, memberName = '-'} = data;

	const anonymous = isBlank(email);

	return (
		<td className={getCN('name-cell-root', className)}>
			<div className='text-dark'>
				<Text size={3} weight='semi-bold'>
					{memberName}
				</Text>
			</div>
			{!anonymous && (
				<Text color='secondary' size={3}>
					{email}
				</Text>
			)}
		</td>
	);
};

export default MemberCell;
