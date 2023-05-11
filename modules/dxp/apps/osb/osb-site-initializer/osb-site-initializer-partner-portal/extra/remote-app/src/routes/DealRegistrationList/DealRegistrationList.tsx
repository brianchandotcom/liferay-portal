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
import {useModal} from '@clayui/modal';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useState} from 'react';
import {CSVLink} from 'react-csv';

import Modal from '../../common/components/Modal';
import Table from '../../common/components/Table';
import TableHeader from '../../common/components/TableHeader';
import Search from '../../common/components/TableHeader/Search';
import {DealRegistrationColumnKey} from '../../common/enums/dealRegistrationColumnKey';
import {ObjectActionName} from '../../common/enums/objectActionName';
import {PermissionActionType} from '../../common/enums/permissionActionType';
import {PRMPageRoute} from '../../common/enums/prmPageRoute';
import useLiferayNavigate from '../../common/hooks/useLiferayNavigate';
import usePagination from '../../common/hooks/usePagination';
import usePermissionActions from '../../common/hooks/usePermissionActions';
import {DealRegistrationListItem} from '../../common/interfaces/dealRegistrationListItem';
import {Liferay} from '../../common/services/liferay';
import getDoubleParagraph from '../../common/utils/getDoubleParagraph';
import ModalContent from './components/ModalContent';
import useFilters from './hooks/useFilters';
import useGetListItemsFromDealRegistration from './hooks/useGetListItemsFromDealRegistration';
export type DealRegistrationItem = {
	[key in DealRegistrationColumnKey]?: any;
};
interface IProps {
	getFilteredItems: (items: DealRegistrationItem[]) => DealRegistrationItem[];
	sort: string;
}
const DealRegistrationList = ({getFilteredItems, sort}: IProps) => {
	const {filters, filtersTerm, onFilter} = useFilters();

	const [isVisibleModal, setIsVisibleModal] = useState(false);
	const [modalContent, setModalContent] = useState<DealRegistrationItem>({});

	const {observer, onClose} = useModal({
		onClose: () => setIsVisibleModal(false),
	});

	const pagination = usePagination();

	const siteURL = useLiferayNavigate();

	const {data, isValidating} = useGetListItemsFromDealRegistration(
		pagination.activePage,
		pagination.activeDelta,
		filtersTerm,
		sort
	);

	const actions = usePermissionActions(ObjectActionName.DEAL_REGISTRATION);

	const filteredData = data.items && getFilteredItems(data.items);

	const columns = [
		{
			columnKey: DealRegistrationColumnKey.PARTNER_ACCOUNT_NAME,
			label: 'Partner Account Name',
		},
		{
			columnKey: DealRegistrationColumnKey.PARTNER_NAME,
			label: 'Partner Name',
		},
		{
			columnKey: DealRegistrationColumnKey.ACCOUNT_NAME,
			label: 'Account Name',
		},
		{
			columnKey: DealRegistrationColumnKey.DATE_SUBMITTED,
			label: 'Date Submitted',
		},
		{
			columnKey: DealRegistrationColumnKey.PRIMARY_PROSPECT_NAME,
			label: getDoubleParagraph('Primary Prospect', 'Name'),
		},
		{
			columnKey: DealRegistrationColumnKey.PRIMARY_PROSPECT_EMAIL,
			label: getDoubleParagraph('Primary Prospect', 'Email'),
		},
		{
			columnKey: DealRegistrationColumnKey.PRIMARY_PROSPECT_PHONE,
			label: getDoubleParagraph('Primary Prospect', 'Phone'),
		},
		{
			columnKey: DealRegistrationColumnKey.STATUS,
			label: 'Status',
		},
	];

	const handleCustomClickOnRow = (item: DealRegistrationItem) => {
		setIsVisibleModal(true);
		setModalContent(item);
	};

	const getModal = () => {
		return (
			<Modal observer={observer} size="lg">
				<ModalContent content={modalContent} onClose={onClose} />
			</Modal>
		);
	};

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
			const {totalCount: totalPagination} = data;

			return (
				<div className="mt-3">
					<Table<DealRegistrationListItem>
						columns={columns}
						customClickOnRow={handleCustomClickOnRow}
						rows={items}
					/>

					<ClayPaginationBarWithBasicItems
						{...pagination}
						totalItems={totalPagination as number}
					/>
				</div>
			);
		}
	};

	return (
		<div className="border-0 my-4">
			<h1>Partner Deal Registration</h1>

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
								!!filteredData?.length &&
								!isValidating && (
									<div>
										<p className="font-weight-semi-bold m-0 ml-1 mt-3 text-paragraph-sm">
											{filteredData?.length > 1
												? `${filteredData?.length} results for ${filters.searchTerm}`
												: `${filteredData?.length} result for ${filters.searchTerm}`}
										</p>
									</div>
								)}
						</div>
					</div>
				</div>

				<div>
					{!!filteredData?.length &&
						actions?.includes(PermissionActionType.EXPORT) && (
							<CSVLink
								className="btn btn-secondary mb-2 mb-lg-0 mr-2"
								data={filteredData}
								filename="Partner Deal Registration.csv"
							>
								Export Deal Registrations
							</CSVLink>
						)}

					{actions?.includes(PermissionActionType.CREATE) && (
						<ClayButton
							className="mb-2 mb-lg-0 mr-2"
							onClick={() =>
								Liferay.Util.navigate(
									`${siteURL}/${PRMPageRoute.CREATE_DEAL_REGISTRATION}`
								)
							}
						>
							Register New Deal
						</ClayButton>
					)}
				</div>
			</TableHeader>

			{isVisibleModal && getModal()}

			{isValidating && <ClayLoadingIndicator />}

			{!isValidating && getTable(filteredData?.length || 0, filteredData)}
		</div>
	);
};
export default DealRegistrationList;
