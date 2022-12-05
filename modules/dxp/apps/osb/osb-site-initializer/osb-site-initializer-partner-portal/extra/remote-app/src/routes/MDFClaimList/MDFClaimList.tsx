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
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {CSVLink} from 'react-csv';

import Dropdown from '../../common/components/Dropdown';
import StatusBadge from '../../common/components/StatusBadge';
import Table from '../../common/components/Table';
import TableHeader from '../../common/components/TableHeader';
import DropDownWithDrillDown from '../../common/components/TableHeader/Filter/components/DropDownWithDrillDown';
import DateFilter from '../../common/components/TableHeader/Filter/components/filters/DateFilter';
import Search from '../../common/components/TableHeader/Search';
import {MDFClaimColumnKey} from '../../common/enums/mdfClaimColumnKey';
import {Status} from '../../common/enums/status';
import useLiferayNavigate from '../../common/hooks/useLiferayNavigate';
import usePagination from '../../common/hooks/usePagination';
import {MDFClaimListItem} from '../../common/interfaces/mdfClaimListItem';
import TableColumn from '../../common/interfaces/tableColumn';
import {Liferay} from '../../common/services/liferay';
import getDropDownFilterMenus from '../../common/utils/getDropDownFilterMenus';
import useFilters from './hooks/useFilters';
import useGetListItemsFromMDFClaims from './hooks/useGetListItemsFromMDFClaims';
import {INITIAL_FILTER} from './utils/constants/initialFilter';

type MDFClaimItem = {
	[key in MDFClaimColumnKey]?: any;
};

const MDFClaimList = () => {
	const {filters, filtersTerm, onFilter} = useFilters();

	const pagination = usePagination();
	const {data, isValidating} = useGetListItemsFromMDFClaims(
		pagination.activePage,
		pagination.activeDelta,
		filtersTerm
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

			<TableHeader>
				<div className="d-flex">
					<div>
						<Search
							onSearchSubmit={(searchTerm: string) =>
								onFilter({
									searchTerm,
								})
							}
						/>

						<div className="bd-highlight flex-shrink-2 mt-1">
							{!!filters.searchTerm &&
								!!data.items?.length &&
								!isValidating && (
									<div>
										<p className="font-weight-semi-bold m-0 ml-1 mt-3 text-paragraph-sm">
											{data.items?.length > 1
												? `${data.items?.length} results for ${filters.searchTerm}`
												: `${data.items?.length} result for ${filters.searchTerm}`}
										</p>
									</div>
								)}

							{filters.hasValue && (
								<ClayButton
									borderless
									className="link"
									onClick={() => {
										onFilter({
											...INITIAL_FILTER,
											searchTerm: filters.searchTerm,
										});
									}}
									small
								>
									<ClayIcon
										className="ml-n2 mr-1"
										symbol="times-circle"
									/>
									Clear All Filters
								</ClayButton>
							)}
						</div>
					</div>

					<DropDownWithDrillDown
						className=""
						initialActiveMenu="x0a0"
						menus={getDropDownFilterMenus([
							{
								component: (
									<DateFilter
										dateFilters={(dates: {
											endDate: string;
											startDate: string;
										}) => {
											onFilter({
												dateCreated: {
													dates,
												},
											});
										}}
										filterDescription="Claim Submitted "
									/>
								),
								name: 'Date Submitted',
							},
						])}
						trigger={
							<ClayButton borderless className="btn-secondary">
								<span className="inline-item inline-item-before">
									<ClayIcon symbol="filter" />
								</span>
								Filter
							</ClayButton>
						}
					/>
				</div>

				<div className="mb-2 mb-lg-0">
					{!!data.items?.length && (
						<CSVLink
							className="btn btn-secondary mr-2"
							data={data.items}
							filename="mdf-claim.csv"
						>
							Export Claim Report
						</CSVLink>
					)}
				</div>
			</TableHeader>

			{!isValidating &&
				getTable(data.totalCount || 0, data.items, columns)}

			{isValidating && <ClayLoadingIndicator />}
		</div>
	);
};
export default MDFClaimList;
