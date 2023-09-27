/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {fetchContactsUsersGroup} from '../../utils/api';
import Modal, {ICommonModalProps} from './Modal';
import {EPeople} from './People';

const ModalUserGroups: React.FC<ICommonModalProps> = ({
	observer,
	onCloseModal,
	syncAllAccounts,
	syncAllContacts,
	syncedIds,
}) => (
	<Modal
		columns={[
			{
				expanded: true,
				id: 'name',
				label: Liferay.Language.get('user-groups'),
			},
		]}
		emptyState={{
			noResultsTitle: Liferay.Language.get('no-user-groups-were-found'),
			title: Liferay.Language.get('there-are-no-user-groups'),
		}}
		name={EPeople.UserGroupIds}
		observer={observer}
		onCloseModal={onCloseModal}
		requestFn={fetchContactsUsersGroup}
		syncAllAccounts={syncAllAccounts}
		syncAllContacts={syncAllContacts}
		syncedIds={syncedIds}
		title={Liferay.Language.get('add-user-groups')}
	/>
);

export default ModalUserGroups;
