/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/modal';
import React, {useMemo} from 'react';

import AddAccountsModal from './AddAccountsModal';
import AddOrganizationsModal from './AddOrganizationsModal';
import InviteUsersModal from './InviteUsersModal';

const modals = {
	account: AddAccountsModal,
	organization: AddOrganizationsModal,
	user: InviteUsersModal,
};

export default function ModalProvider({active, closeModal, parentData, type}) {
	const {observer, onClose} = useModal({
		onClose: closeModal,
	});

	const Modal = useMemo(() => parentData && modals[type], [parentData, type]);

	return (
		active && (
			<Modal
				closeModal={onClose}
				observer={observer}
				parentData={parentData}
			/>
		)
	);
}
