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

import './index.scss';

import {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';
const HEADERS = [
	{
		key: 'claim',
		value: 'Claim Number',
	},
	{
		key: 'property',
		value: 'Property / Vehicle',
	},
	{
		key: 'date',
		value: 'Date Filed',
	},
	{
		key: 'fullName',
		value: 'Name',
	},
	{
		key: 'status',
		value: 'Status',
	},
];
const activeClaimsData: {[keys: string]: string}[] = [
	{
		claim: '816644',
		date: '11/20/2022',
		fullName: 'Maria Santos',
		property: '2019 Ford Edge Se',
		status: 'Repair',
	},
	{
		claim: '322751',
		date: '04/30/2022',
		fullName: 'Maria Santos',
		property: '2019 Ford Edge Se',
		status: 'Pending Settlement',
	},
	{
		claim: '322751',
		date: '04/30/2022',
		fullName: 'Maria Santos',
		property: '2019 Ford Edge Se',
		status: 'Pending Settlement',
	},
];
const PolicyActiveClaims = () => {
	return (
		<div className="bg-neutral policy-active-claims-container rounded">
			<div className="bg-neutral-0 policy-active-claims-title pt-3 px-5 rounded-top">
				<h5 className="m-0">Active Claims</h5>
			</div>

			<hr className="my-0" />

			<ClayTable
				borderedColumns={false}
				borderless
				className="tableeee w-100"
				hover={false}
			>
				<ClayTable.Head>
					<ClayTable.Row>
						{HEADERS.map((header, index) => (
							<ClayTable.Cell
								className="border-bottom py-0 text-paragraph-sm"
								headingCell
								key={index}
							>
								{header.value}
							</ClayTable.Cell>
						))}
					</ClayTable.Row>
				</ClayTable.Head>

				<ClayTable.Body>
					{activeClaimsData.map((rowContent, rowIndex) => (
						<>
							<ClayTable.Row key={rowIndex}>
								{HEADERS.map((item, index) => (
									<ClayTable.Cell
										className="border-0"
										key={index}
									>
										<span>{rowContent[item.key]}</span>
									</ClayTable.Cell>
								))}
							</ClayTable.Row>
							<ClayTable.Row className="info-row">
								<ClayTable.Cell
									className="border-0"
									colSpan={5}
								>
									<div
										className="bg-success-lighten-2 label-borderless-success rounded-xs w-100"
										role="alert"
									>
										<div className="d-flex justify-content-between p-1">
											<div className="align-items-center borderless col-5 d-flex pr-0">
												<span className="alert-indicator"></span>

												<strong className="m-0 p-1">
													<ClayIcon
														className="clay-icon-next p-0"
														symbol="info-circle"
													/>

													<span className="font-weight-semi-bold p-1">
														Next Step:
													</span>
												</strong>

												<span className="m-0 p-1">
													Review estimation for 816644
												</span>
											</div>

											<div className="align-items-center border-0 col-2 d-flex justify-content-end px-0">
												<a
													className="m-0 p-1 view-detail-link"
													href="#"
												>
													View Detail
												</a>

												<ClayButtonWithIcon
													aria-label="Delete"
													displayType={null}
													symbol="times"
												></ClayButtonWithIcon>
											</div>
										</div>
									</div>
								</ClayTable.Cell>
							</ClayTable.Row>
						</>
					))}
				</ClayTable.Body>
			</ClayTable>
		</div>
	);
};
export default PolicyActiveClaims;
