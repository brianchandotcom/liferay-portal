import NameCell from './Name';
import React from 'react';
import {get} from 'lodash';
import {isBlank} from 'shared/util/util';
import {Routes, toRoute} from 'shared/util/router';

interface IIndividualLinksProps {
	channelId?: string;
	className?: string;
	data: {
		emailAddress?: string;
		id: string;
		individualDeleted: boolean;
		individualEmail: string;
		individualId: string;
		individualName: string;
		name: string;
		ownerId?: string;
	};
	disabled?: boolean;
	groupId: string;
}

const IndividualLinkCell: React.FC<IIndividualLinksProps> = ({
	channelId,
	className,
	data,
	disabled,
	groupId
}) => {
	const id = data.individualId || data.ownerId || data.id;

	const name = data.name || data.individualName || '-';

	const email =
		get(data, ['properties', 'email']) ||
		data.individualEmail ||
		data.emailAddress;

	const anonymous = isBlank(email);

	return (
		<NameCell
			className={className}
			data={{...data, id, name}}
			disabled={data.individualDeleted || anonymous || disabled}
			routeFn={({data: {id}}) =>
				toRoute(Routes.CONTACTS_INDIVIDUAL, {
					channelId,
					groupId,
					id
				})
			}
		/>
	);
};

export default IndividualLinkCell;
