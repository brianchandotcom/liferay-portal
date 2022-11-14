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

import Table from '../../common/components/Table';
import {DealRegistrationColumnKey} from '../../common/enums/dealRegistrationColumnKey';
import {PRMPageRoute} from '../../common/enums/prmPageRoute';
import useLiferayNavigate from '../../common/hooks/useLiferayNavigate';
import {DealRegistrationListItem} from '../../common/interfaces/dealRegistrationListItem';
import {Liferay} from '../../common/services/liferay';
import usePagination from '../MDFRequestList/hooks/usePagination';
import useGetListItemsFromDealRegistration from './hooks/useGetListItemsFromDealRegistration';
type DealRegistrationItem = {
	[key in DealRegistrationColumnKey]?: any;
};

const DealRegistrationList = () => {
	const pagination = usePagination();
	const {data, isValidating} = useGetListItemsFromDealRegistration(
		pagination.activePage,
		pagination.activeDelta
	);

	const siteURL = useLiferayNavigate();
	const columns = [
		{
			columnKey: DealRegistrationColumnKey.ACCOUNT_NAME,
			label: 'Account Name',
		},
		{
			columnKey: DealRegistrationColumnKey.START_DATE,
			label: 'Start Date',
		},
		{
			columnKey: DealRegistrationColumnKey.END_DATE,
			label: 'End Date',
		},
	];

	const getTable = (totalCount: number, items?: DealRegistrationItem[]) => {
		if (items) {
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
					<Table<DealRegistrationListItem>
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
			<h1>Partner Deal Registration</h1>

			<div className="bg-neutral-1 d-flex justify-content-end p-3 rounded">
				<ClayButton
					onClick={() =>
						Liferay.Util.navigate(
							`${siteURL}/${PRMPageRoute.CREATE_DEAL_REGISTRATION}`
						)
					}
				>
					Register New Deal
				</ClayButton>
			</div>

			{isValidating && <ClayLoadingIndicator />}

			{!isValidating && getTable(data?.totalCount || 0, data?.items)}
		</div>
	);
};
export default DealRegistrationList;
