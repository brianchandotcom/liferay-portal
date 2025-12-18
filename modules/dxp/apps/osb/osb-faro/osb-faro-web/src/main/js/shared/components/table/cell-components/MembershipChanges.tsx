import getCN from 'classnames';
import Label from '@clayui/label';
import React from 'react';
import {formatUTCDate} from 'shared/util/date';

enum MembershipChange {
	ADDED = 'ADDED',
	REMOVED = 'REMOVED'
}

interface IMembershipChanges {
	className?: string;
	data: {
		createDateTime: string;
		type?: MembershipChange;
	};
}

const MEMBERSHIP_CHANGE_MAP: Record<
	MembershipChange,
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
	data: {createDateTime, type}
}) => (
	<td className={getCN('name-cell-root', className)}>
		<div>{formatUTCDate(createDateTime)}</div>
		{type && (
			<Label displayType={MEMBERSHIP_CHANGE_MAP[type].displayType}>
				{MEMBERSHIP_CHANGE_MAP[type].label}
			</Label>
		)}
	</td>
);

export default MembershipChanges;
