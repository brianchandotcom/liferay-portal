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

import Header from '../../../common/components/header';
import Table from '../../../common/components/table';
import {
	deleteApplicationByExternalReferenceCode,
	getApplications,
} from '../../../common/services';
import formatDate from '../../../common/utils/dateFormater';

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
		key: 'email',
		value: 'Email Adress',
	},
	{
		greyColor: true,
		key: 'name',
		type: 'status',
		value: 'Status',
	},
];

const STATUS_DISABLED = ['Bound', 'Quoted'];

const PARAMETERS = {
	sort: 'applicationCreateDate:desc',
};

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

const ApplicationsTable = () => {
	const [applications, setApplications] = useState<TableContent[]>([]);
	const [active, setActive] = useState(5);

	const handleDeleteApplication = (externalReferenceCode: string) => {
		deleteApplicationByExternalReferenceCode(externalReferenceCode);

		const filteredApplications = applications.filter(
			(application) => !(application.key === externalReferenceCode)
		);

		setApplications(filteredApplications);
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
		getApplications(PARAMETERS).then((results) => {
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
							new Date(applicationCreateDate)
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
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<div className="px-3 ray-dashboard-recent-applications">
			<Header className="mb-5 pt-3" title="Applications (123)" />

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
			/>

			<div className="d-flex justify-content-between mt-3">
				<ClayPaginationBar>
					<ClayPaginationBar.DropDown
						items={[
							{
								label: '5',
								onClick: () => {},
							},
							{
								label: '10',
								onClick: () => {},
							},
							{
								label: '20',
								onClick: () => {},
							},
							{
								label: '30',
								onClick: () => {},
							},
							{
								label: '50',
								onClick: () => {},
							},
							{
								label: '75',
								onClick: () => {},
							},
						]}
						trigger={
							<ClayButton displayType="unstyled">
								10 Entries
								<ClayIcon symbol="caret-double-l" />
							</ClayButton>
						}
					/>

					<ClayPaginationBar.Results>
						Showing 31 to 60 of 438 entries.
					</ClayPaginationBar.Results>
				</ClayPaginationBar>

				<ClayPaginationWithBasicItems
					active={active}
					ellipsisBuffer={2}
					onActiveChange={setActive}
					totalPages={20}
				/>
			</div>
		</div>
	);
};

export default ApplicationsTable;
