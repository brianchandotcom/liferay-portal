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
		emptyStateTitle={Liferay.Language.get('there-are-no-organizations')}
		name={EPeople.OrganizationIds}
		noResultsTitle={Liferay.Language.get('no-organizations-were-found')}
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
