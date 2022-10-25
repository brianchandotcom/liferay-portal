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
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import ClayPaginationBar from '@clayui/pagination-bar';
import React, {useEffect, useState} from 'react';

import Header from '../../../../common/components/header';
import Table from '../../../../common/components/table';
import {
	deleteApplicationByExternalReferenceCode,
	getApplications,
} from '../../../../common/services';
import {Parameters} from '../../../../common/services/index';
import formatDate from '../../../../common/utils/dateFormatter';
import {redirectTo} from '../../../../common/utils/liferay';

const HEADERS = [
	{
		clickable: true,
		greyColor: true,
		key: 'applicationCreateDate',
		type: 'link',
		value: 'Date Filed',
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
		value: 'Application Number',
	},
	{
		key: 'fullName',
		value: 'Name',
	},
	{
		clickable: true,
		greyColor: true,
		key: 'email',
		type: 'link',
		value: 'Email Address',
	},
	{
		greyColor: true,
		key: 'name',
		type: 'status',
		value: 'Status',
	},
];

const STATUS_DISABLED = ['Bound', 'Quoted'];

type Application = {
	applicationCreateDate: Date;
	applicationNumber: number;
	applicationStatus: {name: string};
	email: string;
	externalReferenceCode: string;
	firstName: string;
	lastName: string;
	productName: string;
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

const ApplicationsTable = () => {
	const [applications, setApplications] = useState<TableContent[]>([]);
	const [totalCount, setTotalCount] = useState<number>(0);
	const [pageSize, setPageSize] = useState<number>(5);
	const [totalPages, setTotalPages] = useState<number>(0);
	const [page, setPage] = useState<number>(1);
	const [firstPaginationLabel, setFirstPaginationLabel] = useState<number>(1);
	const [secondPaginationLabel, setSecondPaginationLabel] = useState<number>(
		1
	);

	const [searchInput, setSearchInput] = useState('');
	const firstNameSearched = searchInput.split(' ')[0];
	const lastNameSearched = searchInput.split(' ')[1];

	const PARAMETERS_DEFAULT = {
		page: '0',
		pageSize: '0',
		sort: 'applicationCreateDate:desc',
	};

	const PARAMETERS_SEARCH = {
		filter: !lastNameSearched
			? `contains(firstName,'${firstNameSearched}') or contains(lastName,'${firstNameSearched}') or contains(email, '${firstNameSearched}') or contains(externalReferenceCode, '${firstNameSearched}')`
			: `contains(firstName,'${firstNameSearched}') and contains(lastName,'${lastNameSearched}')`,
		page: '0',
		pageSize: '0',
		sort: 'applicationCreateDate:desc',
	};

	const [parameters, setParameters] = useState<Parameters>(
		PARAMETERS_DEFAULT
	);

	parameters.pageSize = pageSize.toString();
	parameters.page = page.toString();

	const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		setSearchInput(event.target.value);
	};

	const handleClick = () => {
		if (!searchInput) {
			setParameters(PARAMETERS_DEFAULT);
		}

		return setParameters(PARAMETERS_SEARCH);
	};

	const handleDeleteApplication = (externalReferenceCode: string) => {
		deleteApplicationByExternalReferenceCode(externalReferenceCode);

		const filteredApplications = applications.filter(
			(application) => !(application.key === externalReferenceCode)
		);

		setApplications(filteredApplications);
		setTotalCount(totalCount - 1);
	};

	const handleEditApplication = (externalReferenceCode: string) => {
		alert(`Edit ${externalReferenceCode} Action`);
	};

	const setDisabledAction = (identifier: string) => {
		const application = applications.find(
			(application) => application.key === identifier
		) as TableContent;

		return STATUS_DISABLED.includes(application.name);
	};

	useEffect(() => {
		getApplications(parameters).then((results) => {
			const applicationsList: TableContent[] = [];

			results?.data?.items.forEach(
				({
					applicationCreateDate,
					applicationStatus: {name},
					email,
					externalReferenceCode,
					firstName,
					lastName,
					productName,
				}: Application) => {
					const fullName = firstName + ' ' + lastName;

					applicationsList.push({
						applicationCreateDate: formatDate(
							new Date(applicationCreateDate),
							true
						),
						email,
						externalReferenceCode,
						fullName,
						key: externalReferenceCode,
						name,
						productName,
					});
				}
			);
			setApplications(applicationsList);

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
	}, [pageSize, page, parameters, searchInput]);

	const title = `Applications (${totalCount})`;

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
			((item.clickable && rowContent['name'] === 'Incomplete') ||
				rowContent['name'] === 'Bound') &&
			(item.key === 'externalReferenceCode' ||
				item.key === 'applicationCreateDate')
		) {
			handleRedirectToDetailsPages(
				rowContent['externalReferenceCode'],
				'app-details'
			);
		}
	};

	return (
		<div className="px-3 ray-dashboard-recent-applications">
			<div className="d-flex justify-content-between">
				<Header className="mb-5 pt-3" title={title} />

				<ClayForm.Group className="mt-3 w-25">
					<ClayInput.Group>
						<ClayInput.GroupItem prepend>
							<ClayInput
								onChange={handleChange}
								placeholder="Search for..."
								type="text"
							/>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem append shrink>
							<ClayButton
								displayType="secondary"
								onClick={handleClick}
								type="submit"
							>
								<ClayIcon symbol="search" />
							</ClayButton>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>
			</div>

			<Table
				actions={[
					{
						action: handleEditApplication,
						disabled: setDisabledAction,
						value: 'Edit',
					},
					{
						action: handleDeleteApplication,
						value: 'Delete',
					},
				]}
				data={applications}
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

export default ApplicationsTable;
