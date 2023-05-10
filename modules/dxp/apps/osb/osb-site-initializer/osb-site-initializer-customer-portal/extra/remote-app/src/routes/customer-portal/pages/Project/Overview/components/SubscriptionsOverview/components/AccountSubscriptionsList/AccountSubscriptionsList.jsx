/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
			<div className="d-flex flex-wrap">
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
		<div className="d-flex flex-wrap">
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
					isProvisioned={
						selectedAccountSubscriptionGroup?.isProvisioned
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
						LOGO_PATH_TYPES[selectedAccountSubscriptionGroup?.name]
					}
					onClick={() =>
						setCurrentAccountSubscription({...accountSubscription})
					}
				/>
			))}
		</div>
	);
};

export default AccountSubscriptionsList;
