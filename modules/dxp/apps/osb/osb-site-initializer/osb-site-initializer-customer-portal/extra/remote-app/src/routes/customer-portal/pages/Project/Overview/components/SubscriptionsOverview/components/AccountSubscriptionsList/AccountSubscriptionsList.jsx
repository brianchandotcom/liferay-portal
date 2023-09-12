/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/core';
import {useEffect, useState} from 'react';
import i18n from '../../../../../../../../../common/I18n';
import {LOGO_PATH_TYPES} from '../../../../../../../../../common/services/liferay/graphql/account-subscription-groups/utils/constants/logoPathTypes';
import AccountSubscriptionCard from './components/AccountSubscriptionCard/AccountSubscriptionCard';
import AccountSubscriptionModal from './components/AccountSubscriptionModal/AccountSubscriptionModal';

const AccountSubscriptionsList = ({
	accountKey,
	accountSubscriptionGroup,
	accountSubscriptions,
	loading,
	maxCardsLoading = 4,
	selectedAccountSubscriptionGroup,
}) => {
	const [
		currentAccountSubscription,
		setCurrentAccountSubscription,
	] = useState();

	const {observer, onOpenChange, open} = useModal();

	useEffect(() => onOpenChange(!!currentAccountSubscription), [
		currentAccountSubscription,
		onOpenChange,
	]);

	if (loading) {
		return (
			<div className="d-flex flex-column">
				{[...new Array(maxCardsLoading)].map((_, index) => (
					<AccountSubscriptionCard key={index} loading />
				))}
			</div>
		);
	}

	if (!accountSubscriptions?.length) {
		return (
			<p className="mt-3 mx-auto pt-1 text-center">
				{i18n.translate('no-subscriptions-match-these-criteria')}
			</p>
		);
	}

	return (
		<div className="d-flex flex-column">
			{open && (
				<AccountSubscriptionModal
					accountKey={accountKey}
					accountSubscriptionGroup={accountSubscriptionGroup}
					accountSubscriptionProductKey={
						currentAccountSubscription.productKey
					}
					externalReferenceCode={
						currentAccountSubscription?.externalReferenceCode
					}
					observer={observer}
					onClose={() => onOpenChange(false)}
					title={
						selectedAccountSubscriptionGroup?.name === 'Other'
							? `${currentAccountSubscription?.name}`
							: `${selectedAccountSubscriptionGroup?.name} ${currentAccountSubscription?.name}`
					}
				/>
			)}

			{accountSubscriptions?.map((accountSubscription, index) => (
				<AccountSubscriptionCard
					{...accountSubscription}
					key={index}
					logoPath={
						LOGO_PATH_TYPES[
							selectedAccountSubscriptionGroup?.name?.trim()
						]
					}
					onClick={() =>
						setCurrentAccountSubscription({...accountSubscription})
					}
					selectedAccountSubscriptionGroup={
						selectedAccountSubscriptionGroup
					}
				/>
			))}
		</div>
	);
};

export default AccountSubscriptionsList;
