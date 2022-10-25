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
import {
	deletePolicyByExternalReferenceCode,
	getNotExpiredPolicies,
} from '../../../../common/services';
import formatDate from '../../../../common/utils/dateFormatter';
import {redirectTo} from '../../../../common/utils/liferay';

const daysToExpirePolicyAlert = 15;

const PoliciesTable = () => {
	const [policies, setPolicies] = useState<TableContent[]>([]);
	const [totalCount, setTotalCount] = useState<number>(0);
	const [pageSize, setPageSize] = useState<number>(5);
	const [totalPages, setTotalPages] = useState<number>(0);
	const [page, setPage] = useState<number>(1);
	const [firstPaginationLabel, setFirstPaginationLabel] = useState<number>(1);
	const [secondPaginationLabel, setSecondPaginationLabel] = useState<number>(
		1
	);
	const [hasRedLine, setHasRedLine] = useState<boolean>(false);

	const HEADERS = [
		{
			bold: false,
			key: 'renewalDue',
			redColor: hasRedLine,
			type: 'status',
			value: 'Renewal Due',
		},
		{
			key: 'productName',
			value: 'Product',
		},
		{
			bold: true,
			clickable: true,
			key: 'externalReferenceCode',
			type: 'link',
			value: 'Policy Number',
		},
		{
			greyColor: true,
			key: 'policyOwnerName',
			value: 'Name',
		},
		{
			greyColor: true,
			key: 'monthlyPremium',
			type: 'status',
			value: 'Monthly Premium',
		},
		{
			greyColor: true,
			key: 'policyPeriod',
			value: 'Policy Period',
		},
		{
			greyColor: true,
			key: 'commission',
			type: 'status',
			value: 'Commission',
		},
	];

	const PARAMETERS = {
		page: '0',
		pageSize: '0',
		sort: 'endDate:asc',
	};

	type Policy = {
		commission: number;
		endDate: string;
		externalReferenceCode: string;
		policyOwnerName: string;
		productName: string;
		startDate: string;
		termPremium: number;
	};

	type TableContent = {[keys: string]: string};

	type TableItemType = {
		bold: boolean;
		clickable: boolean;
		key: string;
		type: string;
		value: string;
	};

	type TableRowContentType = {[keys: string]: string};

	PARAMETERS.pageSize = pageSize.toString();
	PARAMETERS.page = page.toString();

	const handleDeletePolicy = (externalReferenceCode: string) => {
		deletePolicyByExternalReferenceCode(externalReferenceCode);

		const filteredPolicies = policies.filter(
			(application) => !(application.key === externalReferenceCode)
		);

		setPolicies(filteredPolicies);
		setTotalCount(totalCount - 1);
	};

	const handleEditPolicy = (externalReferenceCode: string) => {
		alert(`Edit ${externalReferenceCode} Action`);
	};

	useEffect(() => {
		getNotExpiredPolicies(PARAMETERS).then((results) => {
			const policiesList: TableContent[] = [];
			results?.data?.items?.forEach(
				({
					commission,
					endDate,
					externalReferenceCode,
					policyOwnerName,
					productName,
					startDate,
					termPremium,
				}: Policy) => {
					const policyEndDate = Date.parse(endDate);

					const currentDate: any = new Date();

					const differenceOfDays = policyEndDate - currentDate;

					const renewalDue =
						Math.floor(differenceOfDays / (1000 * 60 * 60 * 24)) +
						1;

					setHasRedLine(
						renewalDue >= 0 && renewalDue < daysToExpirePolicyAlert
					);

					const renewalDueDisplayRules = () => {
						if (renewalDue < 0) {
							return 'Expired';
						}
						if (renewalDue === 0) {
							return 'Due Today';
						}
						else {
							return renewalDue;
						}
					};

					const policyPeriod = `${formatDate(
						new Date(startDate),
						true
					)} - ${formatDate(new Date(endDate), true)}`;

					const monthlyPremium = termPremium / 12;

					policiesList.push({
						commission: `$${commission.toFixed(2)}`,
						externalReferenceCode,
						isExpiring: (renewalDue < 0).toString(),
						isRedLine: (
							renewalDue >= 0 &&
							renewalDue < daysToExpirePolicyAlert
						).toString(),
						key: externalReferenceCode,
						monthlyPremium: `$${monthlyPremium.toFixed(2)}`,
						policyOwnerName,
						policyPeriod: `${policyPeriod}`,
						productName,
						renewalDue: `${renewalDueDisplayRules()}`,
						renewalDueCalculation: `${
							renewalDue >= 0 ? renewalDue : null
						}`,
					});

					policiesList.sort((firstPolicy, secondPolicy) =>
						Number(firstPolicy.renewalDueCalculation) >
						Number(secondPolicy.renewalDueCalculation)
							? 1
							: Number(secondPolicy.renewalDueCalculation) >
							  Number(firstPolicy.renewalDueCalculation)
							? -1
							: 0
					);
				}
			);
			setPolicies(policiesList);

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
	}, [pageSize, page]);

	const title = `Policies (${totalCount})`;

	const handleRedirectToGmail = (email: string) => {
		window.location.href = `mailto:${email}`;
	};

	const handleRedirectToDetailsPages = (
		externalReferenceCode: string,
		entity: string
	) => {
		redirectTo(`${entity}?externalReferenceCode=${externalReferenceCode}`);
	};

	const onClickRules = (
		item: TableItemType,
		rowContent: TableRowContentType
	) => {
		if (item.clickable && item.key === 'email') {
			handleRedirectToGmail(rowContent[item.key]);
		}

		if (
			rowContent['productName'] === 'Auto' &&
			item.key === 'externalReferenceCode'
		) {
			handleRedirectToDetailsPages(
				rowContent['externalReferenceCode'],
				'policy-details'
			);
		}
	};

	return (
		<div className="ray-dashboard-recent-policies">
			<Header className="mb-5 pt-3 px-4" title={title} />

			<Table
				actions={[
					{
						action: handleEditPolicy,
						value: 'Edit',
					},
					{
						action: handleDeletePolicy,
						value: 'Delete',
					},
				]}
				data={policies}
				headers={HEADERS}
				onClickRules={onClickRules}
			/>

			<div className="d-flex justify-content-between mt-3">
				<ClayPaginationBar>
					<ClayPaginationBar.DropDown
						items={[
							{
								label: '5',
								onClick: () => {
									setPageSize(5);
									setPage(1);
								},
							},
							{
								label: '10',
								onClick: () => {
									setPageSize(10);
									setPage(1);
								},
							},
							{
								label: '20',
								onClick: () => {
									setPageSize(20);
									setPage(1);
								},
							},
							{
								label: '30',
								onClick: () => {
									setPageSize(30);
									setPage(1);
								},
							},
							{
								href: '#3',
								label: '50',
								onClick: () => {
									setPageSize(50);
									setPage(1);
								},
							},
							{
								label: '75',
								onClick: () => {
									setPageSize(75);
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

export default PoliciesTable;
