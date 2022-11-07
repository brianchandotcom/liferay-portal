/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import React from 'react';

import {fetchContactsUsersGroup} from '../../utils/api';
import Modal, {ICommonModalProps} from './Modal';
import {useAddItems} from './utils';

const ModalUserGroups: React.FC<ICommonModalProps> = ({
	observer,
	onCloseModal,
	syncAllAccounts,
	syncAllContacts,
}) => {
	const addItems = useAddItems({
		name: 'syncedUserGroupIds',
		onCloseModal,
		syncAllAccounts,
		syncAllContacts,
	});

	return (
		<Modal
			columns={[
				{
					expanded: true,
					label: Liferay.Language.get('user-groups'),
					value: 'name',
				},
			]}
			emptyStateTitle={Liferay.Language.get('there-are-no-user-groups')}
			fetchFn={fetchContactsUsersGroup}
			noResultsTitle={Liferay.Language.get('no-user-groups-were-found')}
			observer={observer}
			onAddItems={addItems}
			onCloseModal={onCloseModal}
			title={Liferay.Language.get('add-user-groups')}
		/>
	);
};

export default ModalUserGroups;
