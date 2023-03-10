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

import ClayModal from '@clayui/modal';
import {memo} from 'react';

import i18n from '../../../../../../../../../../../common/I18n';
import {
	Button,
	Table,
} from '../../../../../../../../../../../common/components';

import {useGetAccountSubscriptionUsage} from '../../../../../../../../../../../common/services/liferay/graphql/account-subscription-usage';
import UsageChart from './components/UsageChart';
import useOrderItems from './hooks/useOrderItems';
import getColumns from './utils/getColumns';
import getRows from './utils/getRows';

const AccountSubscriptionModal = ({
	accountKey,
	accountSubscriptionGroup,
	accountSubscriptionProductKey,
	externalReferenceCode,
	isProvisioned,
	observer,
	onClose,
	title,
}) => {
	const [
		{activePage, setActivePage},
		itemsPerPage,
		{data, loading},
	] = useOrderItems(externalReferenceCode);

	const {
		data: accountSubscriptionUsageData,
		loading: accountSubscriptionUsageLoading,
	} = useGetAccountSubscriptionUsage(
		accountKey,
		accountSubscriptionProductKey
	);

	const totalCount = data?.orderItems.totalCount;

	const accountSubscriptionGroupNames = ['DXP', 'Portal'];

	return (
		<ClayModal center observer={observer} size="lg">
			<div className="pt-4 px-4">
				<div className="d-flex justify-content-between mb-4">
					<div className="flex-row mb-1">
						<h6 className="text-brand-primary">
							{i18n.translate('subscription-terms').toUpperCase()}
						</h6>

						<h2 className="text-neutral-10">{title}</h2>
					</div>

					<Button
						appendIcon="times"
						className="align-self-start"
						displayType="unstyled"
						onClick={onClose}
					/>
				</div>

				<h5 className="mb-4">
					{i18n.translate('active-subscriptions')}
				</h5>

				{accountSubscriptionGroupNames.includes(
					accountSubscriptionGroup.name
				) && (
					<UsageChart
						data={
							accountSubscriptionUsageData?.getAccountSubscriptionUsage
						}
						loading={accountSubscriptionUsageLoading}
					/>
				)}

				<Table
					columns={getColumns(isProvisioned)}
					hasPagination
					isLoading={loading}
					paginationConfig={{
						activePage,
						itemsPerPage,
						setActivePage,
						totalCount,
					}}
					rows={getRows(data?.orderItems.items)}
					tableVerticalAlignment="middle"
				/>
			</div>
		</ClayModal>
	);
};

export default memo(AccountSubscriptionModal);
