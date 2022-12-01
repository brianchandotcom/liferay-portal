/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import ClayPaginationBar from '@clayui/pagination-bar';
import {useEffect, useState} from 'react';

import Header from '../../../../common/components/header';
import Table from '../../../../common/components/table';
import {getPolicyByExternalReferenceCode} from '../../../../common/services';
import {
	deleteClaimByExternalReferenceCode,
	getClaims,
} from '../../../../common/services/Claim';
import formatDate from '../../../../common/utils/dateFormatter';

const ClaimsTable = () => {
	const [dataClaims, setDataClaims] = useState<TableContentType[]>([]);
	const [totalPages, setTotalPages] = useState<number>(0);
	const [pageSize, setPageSize] = useState<number>(20);
	const [totalCount, setTotalCount] = useState<number>(0);
	const [page, setPage] = useState<number>(1);
	const [firstPaginationLabel, setFirstPaginationLabel] = useState<number>(1);
	const [secondPaginationLabel, setSecondPaginationLabel] = useState<number>(
		1
	);

	const HEADERS = [
		{
			greyColor: true,
			key: 'claimCreateDate',
			value: 'Date Field',
		},
		{
			greyColor: true,
			key: 'productName',
			value: 'Product',
		},
		{
			bold: true,
			key: 'externalReferenceCode',
			type: 'link',
			value: 'Claim Number',
		},
		{
			greyColor: true,
			key: 'policyNumber',
			value: 'Policy Number',
		},
		{
			greyColor: true,
			key: 'claimName',
			value: 'Name',
		},
		{
			greyColor: true,
			key: 'claimStatus',
			type: 'hasBubble',
			value: 'Status',
		},
	];

	const PARAMETERS = {
		page: '0',
		pageSize: '0',
	};

	PARAMETERS.pageSize = pageSize.toString();
	PARAMETERS.page = page.toString();

	type TableContentType = {
		[key: string]: string;
	};

	type ClaimTableType = {
		claimCreateDate: string;
		claimStatus: {name: string};
		externalReferenceCode: string;
		r_policyToClaims_c_raylifePolicyERC: string;
	};

	type PolicyDataType = {
		policyOwnerName: string;
		productName: string;
	};

	const handleDeleteClaim = (externalReferenceCode: string) => {
		deleteClaimByExternalReferenceCode(externalReferenceCode);

		const filteredClaims = dataClaims.filter(
			(claim) => !(claim?.key === externalReferenceCode)
		);

		setDataClaims(filteredClaims);
		setTotalCount(totalCount - 1);
	};

	const handleEditClaim = (externalReferenceCode: string) => {
		alert(`Edit ${externalReferenceCode} Action`);
	};

	useEffect(() => {
		getClaims(PARAMETERS).then((results) => {
			const claimList: TableContentType[] = [];
			results?.data?.items?.forEach(
				async ({
					claimCreateDate,
					claimStatus,
					externalReferenceCode,
					r_policyToClaims_c_raylifePolicyERC,
				}: ClaimTableType) => {
					const policyElement = await getPolicyByExternalReferenceCode<
						PolicyDataType
					>(r_policyToClaims_c_raylifePolicyERC);

					const policyOwnerName =
						policyElement?.data?.policyOwnerName;

					const productName = policyElement?.data?.productName;

					claimList.push({
						claimCreateDate: formatDate(
							new Date(claimCreateDate),
							true
						),
						claimName: policyOwnerName,
						claimStatus: claimStatus?.name,
						key: externalReferenceCode,
						policyNumber: r_policyToClaims_c_raylifePolicyERC,
						productName,
					});
				}
			);

			setDataClaims(claimList);

			const totalCount = results?.data?.totalCount;
			setTotalCount(totalCount);

			const totalPages = Math.ceil(totalCount / pageSize);
			setTotalPages(totalPages);

			const firstPaginationLabel = (page - 1) * pageSize + 1;
			setFirstPaginationLabel(firstPaginationLabel);

			const secondPaginationLabel =
				totalCount > page * pageSize ? page * pageSize : totalCount;
			setSecondPaginationLabel(secondPaginationLabel);
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [page, setPage]);

	const title = `Claims (${totalCount})`;

	return (
		<div className="px-3">
			<Header className="mb-5 pt-3" title={title} />

			<Table
				actions={[
					{action: handleEditClaim, value: 'Edit'},
					{
						action: handleDeleteClaim,
						value: 'Delete',
					},
				]}
				data={dataClaims}
				headers={HEADERS}
			/>

			<div className="d-flex justify-content-between mt-3 px-3">
				<ClayPaginationBar>
					<ClayPaginationBar.DropDown
						items={[
							{
								label: '20',
								onClick: () => {
									setPageSize(20);
									setPage(1);
								},
							},
							{
								label: '40',
								onClick: () => {
									setPageSize(40);
									setPage(1);
								},
							},
							{
								label: '60',
								onClick: () => {
									setPageSize(60);
									setPage(1);
								},
							},
							{
								label: '80',
								onClick: () => {
									setPageSize(80);
									setPage(1);
								},
							},
							{
								href: '#3',
								label: '100',
								onClick: () => {
									setPageSize(100);
									setPage(1);
								},
							},
							{
								label: '120',
								onClick: () => {
									setPageSize(120);
									setPage(1);
								},
							},
						]}
						trigger={
							<ClayButton displayType="unstyled">
								{pageSize}
								&nbsp;Entries
								<ClayIcon symbol="caret-double-l" />
							</ClayButton>
						}
					/>

					<ClayPaginationBar.Results>
						Showing {firstPaginationLabel}
						&nbsp;to&nbsp;
						{secondPaginationLabel} of {totalCount} entries.
					</ClayPaginationBar.Results>
				</ClayPaginationBar>

				<ClayPaginationWithBasicItems
					activePage={page}
					ellipsisBuffer={2}
					onPageChange={(page: number) => setPage(page)}
					totalPages={totalPages}
				/>
			</div>
		</div>
	);
};

export default ClaimsTable;
