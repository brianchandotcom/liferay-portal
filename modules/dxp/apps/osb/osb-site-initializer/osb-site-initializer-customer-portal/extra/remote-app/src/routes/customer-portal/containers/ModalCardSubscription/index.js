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

import {useQuery} from '@apollo/client';
import ClayModal from '@clayui/modal';
import React, {useState} from 'react';
import i18n from '../../../../common/I18n';
import {Button, StatusTag, Table} from '../../../../common/components';
import {getAccountSubscriptionsTerms} from '../../../../common/services/liferay/graphql/queries';
import {STATUS_TAG_TYPES} from '../../utils/constants';
import getDateCustomFormat from '../../utils/getDateCustomFormat';
import getKebabCase from '../../utils/getKebabCase';

const dateFormat = {
	day: '2-digit',
	month: '2-digit',
	year: 'numeric',
};

const provisionedRequiredGroups = [
	'Commerce',
	'DXP',
	'Portal',
	'Social Office',
];

const provisionedIndex = 1;

const columns = [
	{
		accessor: 'start-end-date',
		align: 'center',
		bodyClass: 'border-0',
		expanded: true,
		header: {
			name: i18n.translate('start-end-date'),
			styles:
				'bg-neutral-1 font-weight-bold text-neutral-8 table-cell-expand-smaller py-3',
		},
	},
	{
		accessor: 'provisioned',
		align: 'center',
		bodyClass: 'border-0',
		header: {
			name: i18n.translate('provisioned'),
			styles:
				'bg-neutral-1 font-weight-bold text-neutral-8 table-cell-expand-smaller py-3',
		},
	},
	{
		accessor: 'quantity',
		align: 'center',
		bodyClass: 'border-0',
		header: {
			name: i18n.translate('purchased'),
			styles:
				'bg-neutral-1 font-weight-bold text-neutral-8 table-cell-expand-smaller py-3',
		},
	},
	{
		accessor: 'instance-size',
		align: 'center',
		bodyClass: 'border-0',
		header: {
			name: i18n.translate('instance-size'),
			styles:
				'bg-neutral-1 font-weight-bold text-neutral-8 table-cell-expand-smaller py-3',
		},
	},
	{
		accessor: 'subscription-term-status',
		align: 'center',
		bodyClass: 'border-0',
		header: {
			name: i18n.translate('status'),
			styles:
				'bg-neutral-1 font-weight-bold text-neutral-8 table-cell-expand-smaller py-3',
		},
	},
];

const ModalCardSubscription = ({
	accountSubscriptionERC,
	observer,
	onClose,
	subscriptionGroup,
	subscriptionName,
}) => {
	const [activePage, setActivePage] = useState(1);

	const {data: subscriptionsTerms} = useQuery(getAccountSubscriptionsTerms, {
		variables: {
			filter: `accountSubscriptionERC eq '${accountSubscriptionERC}'`,
			page: activePage,
			pageSize: 5,
		},
	});

	const dataAccountSubscriptionTerms =
		subscriptionsTerms?.c?.accountSubscriptionTerms?.items || [];

	const totalCount =
		subscriptionsTerms?.c?.accountSubscriptionTerms?.totalCount;

	const columnsWithoutProvisioned = () => {
		const customColumns = [...columns];
		customColumns.splice(provisionedIndex, 1);

		return customColumns;
	};

	return (
		<ClayModal center observer={observer} size="lg">
			<div className="pt-4 px-4">
				<div className="d-flex justify-content-between mb-4">
					<div className="flex-row mb-1">
						<h6 className="text-brand-primary">
							{i18n.translate('subscription-terms').toUpperCase()}
						</h6>

						<h2 className="text-neutral-10">{`${i18n.translate(
							getKebabCase(subscriptionGroup)
						)} ${i18n.translate(
							getKebabCase(subscriptionName)
						)}`}</h2>
					</div>

					<Button
						appendIcon="times"
						aria-label="close"
						className="align-self-start"
						displayType="unstyled"
						onClick={onClose}
					/>
				</div>

				<div>
					<Table
						columns={
							provisionedRequiredGroups.includes(
								subscriptionGroup
							)
								? columns
								: columnsWithoutProvisioned(columns)
						}
						hasPagination
						paginationConfig={{
							activePage,
							itemsPerPage: 5,
							setActivePage,
							totalCount,
						}}
						rows={dataAccountSubscriptionTerms.map(
							({
								endDate,
								instanceSize,
								provisioned,
								quantity,
								startDate,
								subscriptionTermStatus,
							}) => ({
								'instance-size': instanceSize || '-',
								'provisioned': provisioned || '-',
								'quantity': quantity || '-',
								'start-end-date': `${getDateCustomFormat(
									startDate,
									dateFormat
								)} - ${getDateCustomFormat(
									endDate,
									dateFormat
								)}`,
								'subscription-term-status':
									(subscriptionTermStatus && (
										<StatusTag
											currentStatus={i18n.translate(
												STATUS_TAG_TYPES[
													`${subscriptionTermStatus.toLowerCase()}`
												]
											)}
										/>
									)) ||
									'-',
							})
						)}
						tableVerticalAlignment="middle"
					/>
				</div>
			</div>
		</ClayModal>
	);
};

export default ModalCardSubscription;
