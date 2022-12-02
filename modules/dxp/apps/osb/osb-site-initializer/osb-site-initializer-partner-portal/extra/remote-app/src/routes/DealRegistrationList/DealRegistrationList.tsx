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

import Modal from '../../common/components/Modal';
import Table from '../../common/components/Table';
import TableHeader from '../../common/components/TableHeader';
import Search from '../../common/components/TableHeader/Search';
import {DealRegistrationColumnKey} from '../../common/enums/dealRegistrationColumnKey';
import {PRMPageRoute} from '../../common/enums/prmPageRoute';
import useLiferayNavigate from '../../common/hooks/useLiferayNavigate';
import usePagination from '../../common/hooks/usePagination';
import {DealRegistrationListItem} from '../../common/interfaces/dealRegistrationListItem';
import {Liferay} from '../../common/services/liferay';
import ModalContent from './components/ModalContent';
import useFilters from './hooks/useFilters';
import useGetListItemsFromDealRegistration from './hooks/useGetListItemsFromDealRegistration';
import getDoubleParagraph from './utils/getDoubleParagraph';

export type DealRegistrationItem = {
	[key in DealRegistrationColumnKey]?: any;
};

const DealRegistrationList = () => {
	const {filters, filtersTerm, onFilter} = useFilters();
	const [isVisibleModal, setIsVisibleModal] = useState(false);
	const [modalContent, setModalContent] = useState<DealRegistrationItem>({});

	const {observer, onClose} = useModal({
		onClose: () => setIsVisibleModal(false),
	});

	const pagination = usePagination();
	const {data, isValidating} = useGetListItemsFromDealRegistration(
		pagination.activePage,
		pagination.activeDelta,
		filtersTerm
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
		{
			columnKey: DealRegistrationColumnKey.DEAL_AMOUNT,
			label: 'Deal Amount',
		},
		{
			columnKey: DealRegistrationColumnKey.PARTNER_REP,
			label: getDoubleParagraph('Partner Rep', 'Partner Email'),
		},
		{
			columnKey: DealRegistrationColumnKey.LIFERAY_REP,
			label: 'Liferay Rep',
		},
		{
			columnKey: DealRegistrationColumnKey.STAGE,
			label: 'Stage',
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

			return (
				<div className="mt-3">
					<Table<DealRegistrationListItem>
						borderless
						columns={columns}
						customClickOnRow={handleCustomClickOnRow}
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
			<h1>Partner Opportunity Registration</h1>

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
						</div>
					</div>
				</div>

				<div className="mb-2 mb-lg-0">
					<ClayButton
						onClick={() =>
							Liferay.Util.navigate(
								`${siteURL}/${PRMPageRoute.CREATE_DEAL_REGISTRATION}`
							)
						}
					>
						Register New Opportunity
					</ClayButton>
				</div>
			</TableHeader>

			{isVisibleModal && getModal()}

			{isValidating && <ClayLoadingIndicator />}

			{!isValidating && getTable(data?.totalCount || 0, data?.items)}
		</div>
	);
};
export default DealRegistrationList;
