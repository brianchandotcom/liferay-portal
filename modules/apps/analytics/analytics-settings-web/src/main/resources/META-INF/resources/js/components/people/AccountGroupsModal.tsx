/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {fetchAccountGroups} from '../../utils/api';
import Modal, {ICommonModalProps} from './Modal';
import {EPeople} from './People';

const ModalAccountGroups: React.FC<ICommonModalProps> = ({
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
				label: Liferay.Language.get('account-group'),
			},
		]}
		emptyState={{
			noResultsTitle: Liferay.Language.get(
				'no-account-groups-were-found'
			),
			title: Liferay.Language.get('there-are-no-account-groups'),
		}}
		name={EPeople.AccountGroupIds}
		observer={observer}
		onCloseModal={onCloseModal}
		requestFn={fetchAccountGroups}
		syncAllAccounts={syncAllAccounts}
		syncAllContacts={syncAllContacts}
		syncedIds={syncedIds}
		title={Liferay.Language.get('add-account-groups')}
	/>
);

export default ModalAccountGroups;
