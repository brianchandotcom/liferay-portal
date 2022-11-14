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

import ClayAlert from '@clayui/alert';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';

import Dropdown from '../../common/components/Dropdown';
import StatusBadge from '../../common/components/StatusBadge';
import Table from '../../common/components/Table';
import {MDFClaimColumnKey} from '../../common/enums/mdfClaimColumnKey';
import {Status} from '../../common/enums/status';
import useLiferayNavigate from '../../common/hooks/useLiferayNavigate';
import usePagination from '../../common/hooks/usePagination';
import {MDFClaimListItem} from '../../common/interfaces/mdfClaimListItem';
import TableColumn from '../../common/interfaces/tableColumn';
import {Liferay} from '../../common/services/liferay';
import useGetListItemsFromMDFClaims from './hooks/useGetListItemsFromMDFClaims';

type MDFClaimItem = {
	[key in MDFClaimColumnKey]?: any;
};

const MDFClaimList = () => {
	const pagination = usePagination();
	const {data, isValidating} = useGetListItemsFromMDFClaims(
		pagination.activePage,
		pagination.activeDelta
	);

	const siteURL = useLiferayNavigate();

	const columns = [
		{
			columnKey: MDFClaimColumnKey.REQUEST_ID,
			label: 'Request ID',
			render: (data?: string) => <>{`Request-${data}`}</>,
		},
		{
			columnKey: MDFClaimColumnKey.PARTNER,
			label: 'Partner',
		},
		{
			columnKey: MDFClaimColumnKey.STATUS,
			label: 'Status',
			render: (data?: string) => <StatusBadge status={data as Status} />,
		},
		{
			columnKey: MDFClaimColumnKey.TYPE,
			label: 'Type',
		},
		{
			columnKey: MDFClaimColumnKey.AMOUNT_CLAIMED,
			label: 'Amount Claimed',
		},
		{
			columnKey: MDFClaimColumnKey.PAID,
			label: 'Paid',
		},
		{
			columnKey: MDFClaimColumnKey.DATE_SUBMITTED,
			label: 'Date Submitted',
		},
		{
			columnKey: MDFClaimColumnKey.ACTION,
			label: '',
			render: (_: string | undefined, row: MDFClaimListItem) => (
				<Dropdown
					onClick={() =>
						Liferay.Util.navigate(
							`${siteURL}/l/${row[MDFClaimColumnKey.REQUEST_ID]}`
						)
					}
					options={[
						{
							icon: 'view',
							key: 'approve',
							label: ' View',
						},
					]}
				></Dropdown>
			),
		},
	];

	const getTable = (
		totalCount: number,
		items?: MDFClaimItem[],
		columns?: TableColumn<MDFClaimListItem>[]
	) => {
		if (items && columns) {
			if (!totalCount) {
				return (
					<div className="d-flex justify-content-center mt-4">
						<ClayAlert
							className="m-0 w-50"
							displayType="info"
							title="Info:"
						>
							No entries were found
						</ClayAlert>
					</div>
				);
			}

			return (
				<div className="mt-3">
					<Table<MDFClaimListItem>
						borderless
						columns={columns}
						responsive
						rows={items}
					/>

					<ClayPaginationBarWithBasicItems
						{...pagination}
						totalItems={totalCount}
					/>
				</div>
			);
		}
	};

	return (
		<div className="border-0 my-4">
			<h1>MDF Claim</h1>

			{isValidating && <ClayLoadingIndicator />}

			{!isValidating &&
				getTable(data.totalCount || 0, data.items, columns)}
		</div>
	);
};
export default MDFClaimList;
