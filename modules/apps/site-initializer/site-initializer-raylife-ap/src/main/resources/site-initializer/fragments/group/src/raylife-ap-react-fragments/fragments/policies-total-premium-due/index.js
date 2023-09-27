/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelect} from '@clayui/form';
import React, {useEffect, useRef, useState} from 'react';

import BarChart from '../../../common/components/bar-chart';
import ClayIconProvider from '../../../common/context/ClayIconProvider';
import {getPoliciesChartExpiringPolicies} from '../../../common/services/Policy';
import {getProducts} from '../../../common/services/Products';
import {
	currentDateString,
	lastDateCurrentMonth,
	lastDateNextThreeMonth,
} from '../../../common/utils/dateFormatter';
import useWindowDimensions from '../../../hooks/useWindowDimensions';

const PERIOD = {
	THIS_MONTH: '1',
	THREE_MONTH: '2',
};

export default function () {
	const [expirationPoliciesList, setExpirationPoliciesList] = useState({});
	const [selectedFilterDate, setSelectedFilterDate] = useState('1');
	const [dataChart, setDataChart] = useState();
	const [labelChart, setLabelChart] = useState();
	const [isLoading, setIsLoading] = useState(false);
	const [sumPolicies, setSumPolicies] = useState();

	const labelRef = useRef();
	const {width} = useWindowDimensions();

	const maxProductNameLenght = 10;
	const minProductNameLenght = 7;

	const chartContainer = document.getElementById('total-premium-due-bar');

	let chartWidth = 0;
	if (chartContainer) {
		const styles = getComputedStyle(chartContainer);
		chartWidth = Number(styles.width.replace('px', ''));
	}

	const filterOptions = [
		{
			label: 'This Month',
			value: PERIOD.THIS_MONTH,
		},
		{
			label: '3 MO',
			value: PERIOD.THREE_MONTH,
		},
	];

	const createPolicyObj = (policiesList, policiesObject) => {
		policiesList?.forEach((policy) => {
			policiesObject[policy?.productName] += policy?.termPremium;
			if (!policiesObject[policy?.productName]) {
				policiesObject[policy?.productName] = policy?.termPremium;
			}
		});

		return policiesObject;
	};
	const createDataPolicyToBarChartFormat = (policiesObject) => {
		const dataColumnsFormatted = [...Object.values(policiesObject)];
		dataColumnsFormatted.unshift('data');

		return dataColumnsFormatted;
	};
	const createLabelPolicyToBarChartFormat = (policiesObject) => {
		const labelColumnsFormatted = [...Object.keys(policiesObject)];
		labelColumnsFormatted.unshift('x');

		return labelColumnsFormatted;
	};
	const createSumPolicies = (policiesData) => {
		const policiesValuesList = [...policiesData];
		policiesValuesList.shift();

		let sumOfPolicies = 0;
		for (let i = 0; i < policiesValuesList.length; i++) {
			sumOfPolicies += policiesValuesList[i];
		}

		setSumPolicies(sumOfPolicies);
	};

	const formatLabel = (labelColumnsFormatted, maxProductNameLenght) => {
		const productNameAbbrevation = labelColumnsFormatted?.map(
			(productName) => {
				if (productName?.length > maxProductNameLenght) {
					return productName
						?.split(' ')
						.map((product) => product.charAt(0))
						.join('');
				}

				if (
					minProductNameLenght < productName.length &&
					productName.length < maxProductNameLenght
				) {
					return productName.substring(0, 4);
				}

				return productName;
			}
		);

		return productNameAbbrevation;
	};

	const handleValueFormatter = (value) => {
		const formattedValue = new Intl.NumberFormat('en-us', {
			currency: 'USD',
			style: 'currency',
		}).format(value);

		return formattedValue;
	};

	const handleExpirationPolicies = () => {
		Promise.allSettled([
			getProducts(),
			getPoliciesChartExpiringPolicies(
				currentDateString[0],
				currentDateString[1],
				currentDateString[2],
				lastDateCurrentMonth[0],
				lastDateCurrentMonth[1],
				lastDateCurrentMonth[2]
			),
		]).then((results) => {
			const [
				productPoliciesResult,
				expiringPoliciesResultThisMonth,
			] = results;
			let policiesObjectList = {};
			const productList = productPoliciesResult?.value?.data?.items;

			productList?.map((product) => {
				product.productValue = 0;
			});

			const productListFormatted = {};
			productList?.forEach((product) => {
				if (!productListFormatted[product?.name]) {
					productListFormatted[product?.name] = 0;
				}

				setExpirationPoliciesList({...productListFormatted});
				policiesObjectList = {...productListFormatted};
			});

			if (selectedFilterDate === PERIOD.THIS_MONTH) {
				const policiesList = [];

				const totalFilteredPolicies =
					expiringPoliciesResultThisMonth?.value?.data;

				totalFilteredPolicies?.items?.map((policy) => {
					policiesList.push(policy);
				});

				const policiesObject = createPolicyObj(
					policiesList,
					policiesObjectList
				);

				const dataColumnsFormatted = createDataPolicyToBarChartFormat(
					policiesObject
				);
				const labelColumnsFormatted = createLabelPolicyToBarChartFormat(
					policiesObject
				);

				setDataChart(dataColumnsFormatted);

				const formattedLabel = formatLabel(
					labelColumnsFormatted,
					maxProductNameLenght
				);

				setLabelChart(formattedLabel);

				createSumPolicies(dataColumnsFormatted);

				setIsLoading(true);

				return;
			}

			if (selectedFilterDate === PERIOD.THREE_MONTH) {
				getPoliciesChartExpiringPolicies(
					currentDateString[0],
					currentDateString[1],
					currentDateString[2],
					lastDateNextThreeMonth[0],
					lastDateNextThreeMonth[1],
					lastDateNextThreeMonth[2]
				).then((results) => {
					const policiesList = [];

					const totalFilteredPolicies = results?.data;

					totalFilteredPolicies?.items?.map((policy) => {
						policiesList.push(policy);
					});

					const policiesObject = createPolicyObj(
						policiesList,
						expirationPoliciesList
					);

					const dataColumnsFormatted = createDataPolicyToBarChartFormat(
						policiesObject
					);
					const labelColumnsFormatted = createLabelPolicyToBarChartFormat(
						policiesObject
					);

					setDataChart(dataColumnsFormatted);

					const formattedLabel = formatLabel(
						labelColumnsFormatted,
						maxProductNameLenght
					);

					setLabelChart(formattedLabel);

					createSumPolicies(dataColumnsFormatted);

					setIsLoading(true);

					return;
				});
			}
		});
	};

	useEffect(() => {
		handleExpirationPolicies();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedFilterDate, sumPolicies]);

	useEffect(() => {
		if (labelRef.current) {
			const chartContainer = document.getElementById(
				'total-premium-due-bar'
			);

			if (chartContainer) {
				const styles = getComputedStyle(chartContainer);
				const chartContainerWidth = Number(
					styles.width.replace('px', '')
				);
				const calculatedWidth = chartContainerWidth - 10;
				labelRef.current.resize({
					height: 275,
					width: calculatedWidth,
				});
			}
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [width]);

	const colors = [
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

	return (
		<ClayIconProvider>
			<div className="d-flex flex-column px-5 total-premium-due-container">
				<div className="align-items-center d-flex font-weight-bold h4 justify-content-between mt-3 total-premium-due-header">
					<div className="total-premium-due-title">
						Total Premium Due
					</div>

					<ClaySelect
						className="border-0 sm total-premium-due-select"
						onChange={({target}) => {
							setSelectedFilterDate(target.value);
						}}
						sizing="sm"
						value={selectedFilterDate}
					>
						{filterOptions.map((option) => (
							<ClaySelect.Option
								key={option.value}
								label={option.label}
								value={option.value}
							/>
						))}
					</ClaySelect>
				</div>

				<div
					className="align-items-center d-flex flex-grow-1 justify-content-center total-premium-due-bar"
					id="total-premium-due-bar"
				>
					{isLoading && !!sumPolicies ? (
						<BarChart
							barRatio={0}
							barWidth={10}
							colors={colors}
							dataColumns={dataChart}
							format
							height={200}
							labelColumns={labelChart}
							labelRef={labelRef}
							titleTotal={false}
							width={chartWidth}
						/>
					) : (
						<div className="align-items-center d-flex flex-grow-1 justify-content-center">
							<span className="h6">No Data</span>
						</div>
					)}
				</div>

				<hr className="mx-3 my-1" />

				{isLoading && (
					<div className="d-flex h6 justify-content-center py-2">
						Total:
						{sumPolicies ? (
							<span className="h6 px-1">
								{handleValueFormatter(sumPolicies)}
							</span>
						) : (
							<i>&nbsp;No data 2</i>
						)}
					</div>
				)}
			</div>
		</ClayIconProvider>
	);
}
