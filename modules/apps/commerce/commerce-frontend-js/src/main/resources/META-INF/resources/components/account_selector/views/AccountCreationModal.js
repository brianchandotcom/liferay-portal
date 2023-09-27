/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayModal from '@clayui/modal';
import {fetch} from 'frontend-js-web';
import React, {useState} from 'react';

import AccountCreationModalBody from './AccountCreationModalBody';

const ACCOUNTS_ROOT_ENDPOINT = '/o/headless-admin-user/v1.0/accounts';

export default function AccountCreationModal({
	accountTypes,
	closeModal,
	handleAccountChange,
	observer,
}) {
	const [accountData, setAccountData] = useState({
		description: '',
		externalReferenceCode: '',
		name: '',
		organizations: [],
		taxId: '',
		type: accountTypes[0],
	});

	const createAccount = (event) => {
		event.preventDefault();

		const organizationIds = accountData.organizations.map(
			({value}) => value
		);

		fetch(ACCOUNTS_ROOT_ENDPOINT, {
			body: JSON.stringify({
				description: accountData.description,
				externalReferenceCode: accountData.externalReferenceCode,
				id: accountData.taxId,
				name: accountData.name,
				organizationIds,
				type: accountData.type,
			}),
			headers: {
				'Content-Type': 'application/json',
			},
			method: 'POST',
		})
			.then((response) => response.json())
			.then((response) => {
				handleAccountChange(response);

				closeModal();
			})
			.catch((error) => console.error(error));
	};

	return (
		<ClayModal center className="commerce-modal" observer={observer}>
			<ClayModal.Header>
				{Liferay.Language.get('create-new-account')}
			</ClayModal.Header>

			<ClayForm onSubmit={createAccount}>
				<AccountCreationModalBody
					accountData={accountData}
					accountTypes={accountTypes}
					setAccountData={setAccountData}
				/>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={closeModal}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton type="submit">
								{Liferay.Language.get('create')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayForm>
		</ClayModal>
	);
}
