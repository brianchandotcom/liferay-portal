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
import i18n from '../../../../../../../../../../../common/I18n';
import {
	Button,
	StatusTag,
	Table,
} from '../../../../../../../../../../../common/components';
import {getCommerceOrderItems} from '../../../../../../../../../../../common/services/liferay/graphql/queries';
import {SLA_STATUS_TYPES} from '../../../../../../../../../../../common/utils/constants';
import getDateCustomFormat from '../../../../../../../../../../../common/utils/getDateCustomFormat';
import getKebabCase from '../../../../../../../../../../../common/utils/getKebabCase';

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

const ModalCardSubscriptions = ({
	accountSubscriptionERC,
	observer,
	onClose,
	subscriptionGroup,
	subscriptionName,
}) => {
	const [activePage, setActivePage] = useState(1);

	const {data} = useQuery(getCommerceOrderItems, {
		variables: {
			filter: `customFields/accountSubscriptionERC eq '${accountSubscriptionERC}'`,
			page: activePage,
			pageSize: 5,
		},
	});

	const dataOrderItems = data?.orderItems?.items || [];

	const totalCount = data?.orderItems?.totalCount;

	const columnsWithoutProvisioned = () => {
		const customColumns = [...columns];
		customColumns.splice(provisionedIndex, 1);

		return customColumns;
	};

	const getRowByColumns = () => {
		return dataOrderItems.map(({customFields, options, quantity}) => {
			const optionsParsed = JSON.parse(options);
			const fields = customFields.reduce(
				(fieldsAccumulator, currentField) => ({
					...fieldsAccumulator,
					[currentField.name]: currentField.customValue.data,
				}),
				{}
			);

			return {
				'instance-size': optionsParsed.instanceSize || '-',
				'provisioned': fields.provisionedCount || '-',
				'quantity': quantity || '-',
				'start-end-date': `${getDateCustomFormat(
					optionsParsed.startDate,
					dateFormat
				)} - ${getDateCustomFormat(optionsParsed.endDate, dateFormat)}`,
				'subscription-term-status':
					(fields.status && (
						<StatusTag
							currentStatus={i18n.translate(
								SLA_STATUS_TYPES[
									`${fields.status.toLowerCase()}`
								]
							)}
						/>
					)) ||
					'-',
			};
		});
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
						rows={getRowByColumns()}
						tableVerticalAlignment="middle"
					/>
				</div>
			</div>
		</ClayModal>
	);
};

export default ModalCardSubscriptions;
