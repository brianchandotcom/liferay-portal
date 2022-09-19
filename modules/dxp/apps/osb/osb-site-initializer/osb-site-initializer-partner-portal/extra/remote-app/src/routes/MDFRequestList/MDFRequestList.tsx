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
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';

import {MDFColumnKey} from '../../common/enums/mdfColumnKey';
import {PRMPageRoute} from '../../common/enums/prmPageRoute';
import useLiferayNavigate from '../../common/hooks/useLiferayNavigate';
import {MDFRequestListItem} from '../../common/interfaces/mdfRequestListItem';
import TableColumn from '../../common/interfaces/tableColumn';
import {Liferay} from '../../common/services/liferay';
import Table from './components/Table';
import useGetMDFRequestListData from './hooks/useGetMDFRequestListData';
import usePagination from './hooks/usePagination';
import getMDFListColumns from './utils/getMDFListColumns';

type MDFRequestItem = {
	[key in MDFColumnKey]?: any;
};

const MDFRequestList = () => {
	const pagination = usePagination();
	const {data, isValidating} = useGetMDFRequestListData(
		pagination.activePage,
		pagination.activeDelta
	);

	const siteURL = useLiferayNavigate();
	const columns = getMDFListColumns(data.listColumns, siteURL);

	const getTable = (
		totalCount: number,
		items?: MDFRequestItem[],
		columns?: TableColumn<MDFRequestListItem>[]
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
					<Table<MDFRequestListItem>
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
		<div className="border-0 pb-3 pt-5 px-6 sheet">
			<h1>MDF Requests</h1>

			<div className="bg-neutral-1 d-flex justify-content-end p-3 rounded">
				<ClayButton
					onClick={() =>
						Liferay.Util.navigate(
							`${siteURL}/${PRMPageRoute.CREATE_MDF_REQUEST}`
						)
					}
				>
					New Request
				</ClayButton>
			</div>

			{isValidating && <ClayLoadingIndicator />}

			{!isValidating &&
				getTable(
					data.listItems.totalCount || 0,
					data.listItems.items,
					columns
				)}
		</div>
	);
};
export default MDFRequestList;
