/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useState} from 'react';

import DonutChart from '../../../common/components/donut-chart';
import {getClaims, getPolicies, getProducts} from '../../../common/services';

export default function () {
	const [chartTitle, setChartTitle] = useState('');
	const [loadData, setLoadData] = useState(false);

	const [columns, setColumns] = useState([]);
	const [colors, setColors] = useState({});

	const colorsArray = [
		'#7154E1',
		'#55C2FF',
		'#4BC286',
		'#FF9A24',
		'#EC676A',
		'#D9E4FE',
		'#1F77B4',
		'#D1D1D9',
		'#B5CDFE',
	];

	const MAX_NAME_LENGHT = 15;

	useEffect(() => {
		Promise.allSettled([getProducts(), getClaims(), getPolicies()]).then(
			(results) => {
				const [
					productQuotesResult,
					claimsResult,
					policiesResult,
				] = results;

				const columnsArr = [];
				const colorsObj = {};

				const totalClaims = claimsResult?.value?.data;

				const totalPolicies = policiesResult?.value?.data?.items;

				const productNameOfClaims = totalClaims?.items?.map((claim) => {
					const getPolicyThroughClaim = totalPolicies?.filter(
						(policy) =>
							policy.externalReferenceCode ===
							claim.r_policyToClaims_c_raylifePolicyERC
					);

					return getPolicyThroughClaim[0];
				});

				setChartTitle(totalClaims?.totalCount);

				productQuotesResult?.value?.data?.items?.map(
					(productQuote, index) => {
						const countActiveClaims = productNameOfClaims?.filter(
							(policy) => productQuote.name === policy.productName
						).length;

						const shortDescription = productQuote.shortDescription;
						const fullName = productQuote.name;
						let productName = fullName;

						const productAbbrevation = productName
							.split(' ')
							.map((product) => product.charAt(0))
							.join('');

						if (productName?.length > MAX_NAME_LENGHT) {
							productName =
								shortDescription === ''
									? productAbbrevation
									: shortDescription;
						}

						colorsObj[fullName] = colorsArray[index];

						if (countActiveClaims > 0) {
							columnsArr[index] = [
								fullName,
								countActiveClaims,
								productName,
							];
						}
					}
				);

				setColumns(columnsArr);
				setColors(colorsObj);

				setLoadData(true);
			}
		);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const chartData = {
		colors,
		columns,
		onclick: (event) => {
			const EVENT_OPTION = {
				async: true,
				fireOn: true,
			};

			const eventPublish = Liferay.publish(
				'openSettingsFilterClaimsEvent',
				EVENT_OPTION
			);

			eventPublish.fire({
				eventName: event.name,
			});
		},
		type: 'donut',
	};

	return (
		<div className="claims-total-container d-flex flex-column flex-shrink-0 pb-4 pt-3 px-3">
			<div className="claims-total-title font-weight-bolder h4">
				Total Claims
			</div>

			{!!chartData.columns.length && (
				<DonutChart chartData={chartData} title={chartTitle} />
			)}

			{!chartData.columns.length && loadData && (
				<div className="align-items-center d-flex flex-grow-1 justify-content-center">
					<span>No Data</span>
				</div>
			)}
		</div>
	);
}
