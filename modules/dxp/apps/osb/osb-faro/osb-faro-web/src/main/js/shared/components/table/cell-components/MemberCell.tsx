import getCN from 'classnames';
import Link from '@clayui/link';
import React from 'react';
import {isBlank} from 'shared/util/util';
import {Text} from '@clayui/core';

interface IMemberCellProps {
	className?: string;
	data: {
		name: string;
		id: string;
		properties: {
			email?: string;
			emailAddress?: string;
		};
	};
	routeFn?: Function;
}

const MemberCell: React.FC<IMemberCellProps> = ({className, data, routeFn}) => {
	const {
		name,
		properties: {email, emailAddress}
	} = data;

	const resolvedEmail = email || emailAddress;

	const anonymous = isBlank(resolvedEmail);

	return (
		<td className={getCN('name-cell-root', className)}>
			<div className='text-dark'>
				<Text size={3} weight='semi-bold'>
					{routeFn ? (
						<Link className='text-dark' href={routeFn({data})}>
							{name}
						</Link>
					) : (
						name
					)}
				</Text>
			</div>
			{!anonymous && (
				<Text color='secondary' size={3}>
					{resolvedEmail}
				</Text>
			)}
		</td>
	);
};

export default MemberCell;
