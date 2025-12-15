import getCN from 'classnames';
import Label from '@clayui/label';
import React from 'react';
import {formatUTCDate} from 'shared/util/date';

interface IMembershipChanges {
	className?: string;
	data: {
		membershipChange: {
			modifiedDate: string;
			type?: 'ADDED' | 'REMOVED';
		};
	};
}

const MEMBERSHIP_CHANGE_MAP: Record<
	'ADDED' | 'REMOVED',
	{label: string; displayType: 'success' | 'danger'}
> = {
	ADDED: {
		displayType: 'success',
		label: Liferay.Language.get('added')
	},
	REMOVED: {
		displayType: 'danger',
		label: Liferay.Language.get('removed')
	}
};

const MembershipChanges: React.FC<IMembershipChanges> = ({
	className,
	data: {membershipChange}
}) => {
	const {modifiedDate, type} = membershipChange;

	return (
		<td className={getCN('name-cell-root', className)}>
			<div>{formatUTCDate(modifiedDate)}</div>
			{type && (
				<Label displayType={MEMBERSHIP_CHANGE_MAP[type].displayType}>
					{MEMBERSHIP_CHANGE_MAP[type].label}
				</Label>
			)}
		</td>
	);
};

export default MembershipChanges;
