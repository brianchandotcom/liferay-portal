/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {fetchContactsOrganization} from '../../utils/api';
import Modal, {ICommonModalProps} from './Modal';
import {EPeople} from './People';

const ModalOrganizations: React.FC<ICommonModalProps> = ({
	observer,
	onCloseModal,
	syncAllAccounts,
	syncAllContacts,
	syncedIds,
}) => (
	<Modal
		columns={[
			{
				expanded: false,
				id: 'name',
				label: Liferay.Language.get('organizations'),
			},
		]}
		emptyState={{
			noResultsTitle: Liferay.Language.get('no-organizations-were-found'),
			title: Liferay.Language.get('there-are-no-organizations'),
		}}
		name={EPeople.OrganizationIds}
		observer={observer}
		onCloseModal={onCloseModal}
		requestFn={fetchContactsOrganization}
		syncAllAccounts={syncAllAccounts}
		syncAllContacts={syncAllContacts}
		syncedIds={syncedIds}
		title={Liferay.Language.get('add-organizations')}
	/>
);

export default ModalOrganizations;
