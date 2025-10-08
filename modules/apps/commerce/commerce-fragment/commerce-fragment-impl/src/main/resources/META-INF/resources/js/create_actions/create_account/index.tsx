/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useModal} from '@clayui/modal';

// @ts-ignore

import {AccountUtils, CommerceNotificationUtils, commerceEvents} from 'commerce-frontend-js';
import React from 'react';

import AccountCreationModal from './modal/AccountCreationModal';

interface CreateAccountProps {
	accountEntryAllowedTypes: string[];
	commerceChannelId: number;
	setCurrentAccountURL: string;
	hasAddAccountsPermission: boolean;
	label: string;
}

export interface OnAccountChangeParams {
	account: any;
	doCheckout: boolean;
}

const CreateAccountAction = ({
	accountEntryAllowedTypes,
	commerceChannelId,
	setCurrentAccountURL,
	hasAddAccountsPermission,
	label,
}: CreateAccountProps) => {
	const {observer, onOpenChange, open} = useModal();

	const onAccountChange = ({account}: OnAccountChangeParams) => {
		AccountUtils.selectAccount(account.id, setCurrentAccountURL)
			.then(() => {
				Liferay.fire(commerceEvents.CURRENT_ACCOUNT_UPDATED, {
					id: account.id,
				});
			})
			.catch(CommerceNotificationUtils.showErrorNotification);
	};

	return (
		<>
			<ClayButton
				className="btn-create-account"
				disabled={!hasAddAccountsPermission}
				onClick={() => onOpenChange(true)}
			>
				{label}
			</ClayButton>

			{open && (
				<AccountCreationModal
					accountTypes={accountEntryAllowedTypes}
					closeModal={() => onOpenChange(false)}
					commerceChannelId={commerceChannelId}
					observer={observer}
					onAccountChange={onAccountChange}
				/>
			)}
		</>
	);
};

export default CreateAccountAction;
