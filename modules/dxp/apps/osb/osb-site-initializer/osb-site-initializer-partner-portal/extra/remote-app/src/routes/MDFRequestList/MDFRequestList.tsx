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

import ClayButton from '@clayui/button';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useMemo, useState} from 'react';

import {MDFColumnKey} from '../../common/enums/mdfColumnKey';
import {PRMPageRoute} from '../../common/enums/prmPageRoute';
import useGetMDFListingColumns from '../../common/services/liferay/object/mdf-listing/useGetMDFListingColumns';
import useGetMDFRequests from '../../common/services/liferay/object/mdf-requests/useGetMDFRequests';
import liferayNavigate from '../../common/utils/liferayNavigate';
import Table from './components/Table';
import getMDFActivityPeriod from './utils/getMDFActivityPeriod';
import getMDFBudgetInfos from './utils/getMDFBudgetInfos';
import getMDFDates from './utils/getMDFDates';

type MDFRequestListItem = {
	[key in MDFColumnKey]?: string;
};

const MDFRequestList = () => {
	const [pageSize, setPageSize] = useState<number>(5);
	const [page, setPage] = useState<number>(1);

	const {data} = useGetMDFRequests({page, pageSize});

	const listItems = useMemo(
		() =>
			data?.items.map((item) => {
				return {
					[MDFColumnKey.ID]: `Request-${item.id}`,
					...getMDFActivityPeriod(
						item.minDateActivity,
						item.maxDateActivity
					),
					[MDFColumnKey.PARTNER]:
						item.r_accountToMDFRequests_accountEntry?.name,
					...getMDFDates(item.dateCreated, item.dateModified),
					...getMDFBudgetInfos(
						item.totalCostOfExpense,
						item.totalMDFRequestAmount
					),
				};
			}),
		[data?.items]
	);

	const {data: listColumns} = useGetMDFListingColumns();

	return (
		<div className="border-0 pb-3 pt-5 px-6 sheet">
			<h1>MDF Requests</h1>

			<div className="bg-neutral-1 d-flex justify-content-end p-3 rounded">
				<ClayButton
					onClick={() => {
						liferayNavigate(PRMPageRoute.CREATE_MDF_REQUEST);
					}}
				>
					New Request
				</ClayButton>
			</div>

			{data && listItems && listColumns && (
				<div className="mt-3">
					<Table<MDFRequestListItem>
						borderless
						columns={listColumns}
						responsive
						rows={listItems}
					/>

					<ClayPaginationBarWithBasicItems
						activeDelta={pageSize}
						activePage={page}
						onDeltaChange={setPageSize}
						onPageChange={setPage}
						totalItems={data.totalCount}
					/>
				</div>
			)}
		</div>
	);
};
export default MDFRequestList;
