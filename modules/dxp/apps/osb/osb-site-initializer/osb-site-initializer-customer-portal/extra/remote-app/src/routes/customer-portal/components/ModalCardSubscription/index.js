import {useQuery} from '@apollo/client';
import ClayModal from '@clayui/modal';
import React, {useState} from 'react';
import BaseButton from '../../../../common/components/BaseButton';
import Table from '../../../../common/components/Table';
import {getAccountSubscriptionsTerms} from '../../../../common/services/liferay/graphql/queries';
import {status} from '../../utils/constants';
import getDateCustomFormat from '../../utils/dateCustomFormat';
import StatusTag from '../StatusTag';

const dateFormat = {
	day: '2-digit',
	month: '2-digit',
	year: 'numeric',
};

const columns = [
	{
		accessor: 'start-end-date',
		align: 'center',
		expanded: true,
		header: {
			name: 'Start-End Date',
		},
	},
	{
		accessor: 'provisioned',
		align: 'center',
		header: {
			name: 'Provisioned',
		},
	},
	{
		accessor: 'quantity',
		align: 'center',
		header: {
			name: 'Purchased',
		},
	},
	{
		accessor: 'instance-size',
		align: 'center',
		header: {
			name: 'Instance Size',
		},
	},
	{
		accessor: 'subscription-term-status',
		align: 'center',
		header: {
			name: 'Status',
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

	return (
		<ClayModal center observer={observer} size="lg">
			<div className="pt-4 px-4">
				<div className="d-flex justify-content-between mb-4">
					<div className="flex-row mb-1">
						<h6 className="text-brand-primary">
							SUBSCRIPTION TERMS
						</h6>

						<h2 className="text-neutral-10">{`${subscriptionGroup} ${subscriptionName}`}</h2>
					</div>

					<BaseButton
						appendIcon="times"
						aria-label="close"
						className="align-self-start"
						displayType="unstyled"
						onClick={onClose}
					/>
				</div>

				<div>
					<Table
						activePage={activePage}
						columns={columns}
						hasPagination={totalCount >= 5}
						itemsPerPage={5}
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
											currentStatus={
												status[
													`${subscriptionTermStatus.toLowerCase()}`
												]
											}
										/>
									)) ||
									'-',
							})
						)}
						setActivePage={setActivePage}
						tableVerticalAlignment="middle"
						totalCount={totalCount}
					/>
				</div>
			</div>
		</ClayModal>
	);
};

export default ModalCardSubscription;
