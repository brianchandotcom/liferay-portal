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

import ClayIcon from '@clayui/icon';
import {useEffect, useState} from 'react';

import Header from '../../../common/components/header';
import Table from '../../../common/components/table';
import {
	deleteApplicationByExternalReferenceCode,
	getApplications,
} from '../../../common/services';
import formatDate from '../../../common/utils/dateFormater';
import {redirectTo} from '../../../common/utils/liferay';

const HEADERS = [
	{
		clickable: true,
		greyColor: true,
		key: 'dateCreated',
		type: 'link',
		value: 'Date',
	},
	{
		key: 'product',
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
		greyColor: true,
		key: 'name',
		type: 'status',
		value: 'Status',
	},
];

const STATUS_DISABLED = ['Bound', 'Quoted'];

const PARAMETERS = {
	sort: 'dateCreated:desc',
};

type RecentApplication = {
	applicationStatus: {name: string};
	dateCreated: Date;
	externalReferenceCode: string;
	product: string;
};

type TableContent = {[keys: string]: string};

const RecentApplications = () => {
	const [applications, setApplications] = useState<TableContent[]>([]);

	const handleAddApplication = () => {
		alert('Add Application Action');
	};

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
					applicationStatus: {name},
					dateCreated,
					externalReferenceCode,
					product,
				}: RecentApplication) =>
					applicationsList.push({
						dateCreated: formatDate(new Date(dateCreated)),
						externalReferenceCode,
						key: externalReferenceCode,
						name,
						product,
					})
			);

			setApplications(applicationsList);
		});
	}, []);

	return (
		<div className="px-3 ray-dashboard-recent-applications">
			<Header className="mb-5 pt-3" title="Recent Applications">
				<button
					className="btn btn-outline-primary text-paragraph text-uppercase"
					onClick={handleAddApplication}
				>
					<ClayIcon className="mr-2" symbol="plus" />
					Application
				</button>
			</Header>

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
				data={applications.slice(0, 6)}
				headers={HEADERS}
			/>

			<div className="align-items-center bottom-container d-flex justify-content-end mt-4 pb-3 px-3">
				<button
					className="btn btn-inverted btn-solid btn-style-primary text-paragraph text-uppercase"
					onClick={() => redirectTo('Applications')}
				>
					All Applications
					<ClayIcon className="ml-2" symbol="order-arrow-right" />
				</button>
			</div>
		</div>
	);
};

export default RecentApplications;
