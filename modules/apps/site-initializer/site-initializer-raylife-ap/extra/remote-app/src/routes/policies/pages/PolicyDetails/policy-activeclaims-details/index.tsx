/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

export type RowContentType = {
	[key: string]: string | number;
};

export type RowType = {
	content: RowContentType;
	index: number;
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
	}, [policyId]);

	const ComponentRow = ({content, index}: RowType) => {
		return (
			<>
				<ClayTable.Row>
					{HEADERS.map((item, index) => (
						<ClayTable.Cell className="border-0 px-5" key={index}>
							<span>{content[item.key]}</span>
						</ClayTable.Cell>
					))}
				</ClayTable.Row>

				<Alert claimNumber={content.claim as number} index={index} />
			</>
		);
	};

	return (
		<div>
			{isLoading && (
				<div className="bg-neutral policy-active-claims-container rounded w-100">
					<div className="bg-neutral-0 p-4 policy-active-claims-title pt-3 px-5 rounded-top w-100">
						<h5 className="m-0">Active Claims</h5>
					</div>

					<hr className="my-0" />

					<ClayTable
						borderedColumns={false}
						borderless
						className="rounded table w-100"
						hover={false}
					>
						<ClayTable.Head>
							<ClayTable.Row>
								{HEADERS.map((header, index) => (
									<ClayTable.Cell
										className="border-bottom px-5 py-0 text-paragraph-sm"
										headingCell
										key={index}
									>
										{header.value}
									</ClayTable.Cell>
								))}
							</ClayTable.Row>
						</ClayTable.Head>

						<ClayTable.Body>
							{claimsTable.map((rowContent, rowIndex) => (
								<ComponentRow
									content={rowContent}
									index={rowIndex}
									key={rowIndex}
								/>
							))}
						</ClayTable.Body>
					</ClayTable>
				</div>
			)}
		</div>
	);
};
export default PolicyActiveClaims;
