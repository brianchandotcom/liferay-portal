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

import ClayTable from '@clayui/table';
import React, {useEffect, useState} from 'react';

import Alert from '../../../../../common/components/alert';
import {getClaimsByPolicyId} from '../../../../../common/services';
import formatDate from '../../../../../common/utils/dateFormatter';

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

export type Parameters = {
	[key: string]: string | string[];
};

type ClaimTableElements = {
	claim: number;
	date: string;
	fullName: string;
	key: number;
	property: string;
	status: string;
};

type ClaimDetailItems = {
	creator: {familyName: string; givenName: string};
	dateCreated: string;
	id: number;
	label: string;
	status: {label: string};
};

type PolicyItems = {
	dataJSON: string;
	id: number;
};

const PolicyActiveClaims = ({dataJSON, id}: PolicyItems) => {
	const [claimsTable, setClaimsTable] = useState<ClaimTableElements[]>([]);
	const [isLoading, setIsLoading] = useState<Boolean>(false);

	const policyDataJSON = dataJSON && JSON.parse(dataJSON);

	const policyId = id;

	const policyFormData = policyDataJSON?.vehicleInfo?.form;

	const policyObjectData = {
		policyMake: policyFormData?.[0]?.make,
		policyModel: policyFormData?.[0]?.model,
		policyYear: policyFormData?.[0]?.year,
	};

	useEffect(() => {
		getClaimsByPolicyId(policyId).then((result) => {
			const claimsList: ClaimTableElements[] = [];
			if (result?.data?.items.length) {
				setIsLoading(true);

				result?.data?.items.forEach(
					({
						creator: {familyName, givenName},
						dateCreated,
						id,
						status: {label},
					}: ClaimDetailItems) => {
						const fullName = givenName + ' ' + familyName;
						claimsList.push({
							claim: id,
							date: formatDate(new Date(dateCreated), true),
							fullName,
							key: id,
							property: `${policyObjectData.policyYear} ${policyObjectData.policyMake} ${policyObjectData.policyModel}`,
							status: label,
						});
					}
				);
				setClaimsTable(claimsList);
			}
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [policyId, policyDataJSON]);

	return (
		<div>
			{isLoading && (
				<div className="bg-neutral policy-active-claims-container rounded">
					<div className="bg-neutral-0 policy-active-claims-title pt-3 px-5 rounded-top">
						<h5 className="m-0">Active Claims</h5>
					</div>

					<hr className="my-0" />

					<ClayTable
						borderedColumns={false}
						borderless
						className="table w-100"
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
							{claimsTable.map(
								(rowContent: any, rowIndex: number) => (
									<>
										<ClayTable.Row key={rowIndex}>
											{HEADERS.map((item, index) => (
												<ClayTable.Cell
													className="border-0"
													key={index}
												>
													<span>
														{rowContent[item.key]}
													</span>
												</ClayTable.Cell>
											))}
										</ClayTable.Row>

										<Alert
											claimNumber={rowContent.claim}
											index={rowIndex}
										/>
									</>
								)
							)}
						</ClayTable.Body>
					</ClayTable>
				</div>
			)}
		</div>
	);
};
export default PolicyActiveClaims;
