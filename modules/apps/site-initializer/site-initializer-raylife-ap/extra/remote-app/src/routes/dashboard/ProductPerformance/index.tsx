/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayChart from '@clayui/charts';

import '@clayui/charts/lib/css/main.css';
import {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useEffect, useRef, useState} from 'react';

import Header from '../../../common/components/header';
import ProductList, {
	ProductCell,
} from '../../../common/components/product-list';
import {getPoliciesForSalesGoal, getSalesGoal} from '../../../common/services';
import {getProducts} from '../../../common/services/Products';
import {
	currentDateString,
	december,
	january,
	sixMonthsAgoDate,
	threeMonthsAgoDate,
} from '../../../common/utils/dateFormatter';
import useWindowDimensions from '../../../hooks/useWindowDimensions';
import {
	PolicyTypes,
	ProductListType,
	ProductPropertiesTypes,
	SalesGoalTypes,
} from './ProductPerformanceTypes';
import {annualRule, sixMonthRule, threeMonthRule} from './businessRules';
import {populateDataByPeriod} from './filterPeriodRules';

enum PERIODS {
	YEAR = '0',
	THREE_MONTH = '2',
	SIX_MONTH = '1',
}

const TIME_PERIODS = [
	{
		label: '3 MO',
		value: PERIODS.THREE_MONTH,
	},
	{
		label: '6 MO',
		value: PERIODS.SIX_MONTH,
	},
	{
		label: 'YTD',
		value: PERIODS.YEAR,
	},
];

const colors: {[keys: string]: {}} = {
	achieved: '#55C2FF',
	exceeded: '#FFD76E',
	goals: '#DCF1FD',
};

let widthValue = 15;

const ProductPerformance = () => {
	const [products, setProducts] = useState<ProductCell[]>([]);
	const [timePeriod, setTimePeriod] = useState<string>(PERIODS.YEAR);
	const labelRef = useRef<any>();
	const [isLoading, setIsLoading] = useState(false);
	const [threeMonthsSalesData, setThreeMonthsSalesData] = useState<number[]>(
		[]
	);
	const [threeMonthsGoalsData, setThreeMonthsGoalsData] = useState<number[]>(
		[]
	);
	const [sixMonthsSalesData, setSixMonthsSalesData] = useState<number[]>([]);
	const [sixMonthsGoalsData, setSixMonthsGoalsData] = useState<number[]>([]);
	const [yearToDateSales, setYearToDateSales] = useState<number[]>([]);
	const [yearToDateGoals, setYearToDateGoals] = useState<number[]>([]);
	const [currentTooltip, setCurrentTooltip] = useState<number[]>(
		yearToDateGoals
	);
	const inicialProductValue = 'All';
	const [productValues, setProductsValues] = useState(inicialProductValue);

	const {width} = useWindowDimensions();

	const chartContainer = document.getElementById(
		'dashboard-product-performance-chart-container'
	);

	let chartWidth = 0;
	if (chartContainer) {
		const styles = getComputedStyle(chartContainer);
		chartWidth = Number(styles.width.replace('px', ''));
	}

	useEffect(() => {
		if (labelRef.current) {
			const chartContainer = document.getElementById(
				'dashboard-product-performance-chart-container'
			);

			if (chartContainer) {
				const styles = getComputedStyle(chartContainer);
				const chartContainerWidth = Number(
					styles.width.replace('px', '')
				);
				const calculatedWidth = chartContainerWidth - 10;
				labelRef.current.resize({
					height: 440,
					width: calculatedWidth,
				});
			}
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [width]);

	const tooltip = {
		format: {
			name(categoryLabel: string) {
				return categoryLabel;
			},
			value(value: number, _id: number, _index: string, x: number) {
				if (value !== 0) {
					if (_index === 'goals') {
						const tooltipGoals = (value = currentTooltip[x]);

						return tooltipGoals;
					}
					else {
						return value;
					}
				}
			},
		},
		grouped: false,
		show: true,
	};

	function getExceededValues(goalValue: number[], salesValue: number[]) {
		const exceededValue = goalValue?.map((goal: number, index: number) => {
			if (goal - salesValue[index] <= 0) {
				return (goal - salesValue[index]) * -1;
			}
			else {
				return 0;
			}
		});

		return exceededValue;
	}

	function getGoalsValues(goalValue: number[], salesValue: number[]) {
		const goalsValues = goalValue?.map((goal: number, index: number) => {
			if (goal - salesValue[index] >= 0) {
				return goal - salesValue[index];
			}
			else {
				return 0;
			}
		});

		return goalsValues;
	}

	function getAchievedValues(goalValue: number[], salesValue: number[]) {
		const achievedValues = goalValue?.map((goal: number, index: number) => {
			if (goal - salesValue[index] <= 0) {
				return goal;
			}
			else {
				return salesValue[index];
			}
		});

		return achievedValues;
	}
	const threeMonthsSalesArray: string[] = [];
	const threeMonthsGoalsArray: string[] = [];
	const sixMonthsSalesArray: string[] = [];
	const sixMonthsGoalsArray: string[] = [];
	const yearToDateSalesArray: string[] = [];
	const yearToDateGoalsArray: string[] = [];
	const threeMonthsLabel: string[] = [];
	const sixMonthsLabel: string[] = [];
	const yearToDateLabel: string[] = [];
	const threeMonthsDatePeriod = 2;
	const sixMonthsDatePeriod = 5;
	const indexOfCurrentMonth = new Date().getMonth();

	populateDataByPeriod(
		threeMonthsDatePeriod,
		threeMonthsLabel,
		threeMonthsSalesArray,
		threeMonthsGoalsArray
	);

	populateDataByPeriod(
		sixMonthsDatePeriod,
		sixMonthsLabel,
		sixMonthsSalesArray,
		sixMonthsGoalsArray
	);

	populateDataByPeriod(
		indexOfCurrentMonth,
		yearToDateLabel,
		yearToDateSalesArray,
		yearToDateGoalsArray
	);

	const loadData = [
		{
			achieved: [
				'achieved',
				...getAchievedValues(
					threeMonthsGoalsData,
					threeMonthsSalesData
				),
			],
			dataGroups: ['achieved', 'exceeded', 'goals'],
			exceeded: [
				'exceeded',
				...getExceededValues(
					threeMonthsGoalsData,
					threeMonthsSalesData
				),
			],
			goals: [
				'goals',
				...getGoalsValues(threeMonthsGoalsData, threeMonthsSalesData),
			],
			label: threeMonthsLabel,
			period: Number(PERIODS.THREE_MONTH),
		},
		{
			achieved: [
				'achieved',
				...getAchievedValues(sixMonthsGoalsData, sixMonthsSalesData),
			],
			dataGroups: ['achieved', 'exceeded', 'goals'],
			exceeded: [
				'exceeded',
				...getExceededValues(sixMonthsGoalsData, sixMonthsSalesData),
			],
			goals: [
				'goals',
				...getGoalsValues(sixMonthsGoalsData, sixMonthsSalesData),
			],
			label: sixMonthsLabel,
			period: Number(PERIODS.SIX_MONTH),
		},
		{
			achieved: [
				'achieved',
				...getAchievedValues(yearToDateGoals, yearToDateSales),
			],
			dataGroups: ['achieved', 'exceeded', 'goals'],
			exceeded: [
				'exceeded',
				...getExceededValues(yearToDateGoals, yearToDateSales),
			],
			goals: [
				'goals',
				...getGoalsValues(yearToDateGoals, yearToDateSales),
			],
			label: yearToDateLabel,
			period: Number(PERIODS.YEAR),
		},
	];

	const getData = () => {
		return loadData?.filter((data) => data.period === Number(timePeriod));
	};

	const lengthExceededColumn = getData()[0]?.exceeded.length - 1;

	const chartSelectedData = {
		achievedData: Object.values(getData()[0]?.achieved),
		exceededData: Object.values(getData()[0]?.exceeded),
		goalsData: Object.values(getData()[0]?.goals),
	};

	const handleVerifyDataOfPolicies = (
		arrayOfPolicyValues: (string | number)[]
	) => {
		const sumOfMonthsValues = arrayOfPolicyValues?.reduce(
			(acumulador: number | string, valorAtual: number | string) =>
				Number(acumulador) + Number(valorAtual),
			0
		);

		return sumOfMonthsValues;
	};

	chartSelectedData.achievedData[0] = 0;

	chartSelectedData.exceededData[0] = 0;

	chartSelectedData.goalsData[0] = 0;

	const hasNoData =
		handleVerifyDataOfPolicies(chartSelectedData.achievedData) === 0 &&
		handleVerifyDataOfPolicies(chartSelectedData.goalsData) === 0 &&
		handleVerifyDataOfPolicies(chartSelectedData.exceededData) === 0;

	const dataChart = {
		colors,
		columns: [
			getData()[0]?.achieved,
			getData()[0]?.goals,
			getData()[0]?.exceeded,
		],
		groups: [['achieved', 'exceeded', 'goals']],
		order: {
			function() {
				loadData?.map((month: any) =>
					month.achieved > month.goals ? 'asc' : 'desc '
				);
			},
		},
		type: 'bar',
	};

	const productsBaseSetup = async () => {
		const productsData = await getProducts();

		const productERCList = productsData.data.items;

		const yearlyPolicies = await getPoliciesForSalesGoal(
			currentDateString[0],
			currentDateString[1],
			currentDateString[0],
			january
		);

		const yearlySalesGoal = await getSalesGoal(
			currentDateString[0],
			december,
			currentDateString[0],
			january
		);

		const newProductList: ProductCell[] = [];
		const yearlyProductsTotal: ProductListType = {};

		productERCList?.forEach(
			({externalReferenceCode, name}: ProductPropertiesTypes) => {
				let productNameAbbrevation = name;

				if (name.length > 8) {
					productNameAbbrevation = name
						.split(' ')
						.map((product: string) => product.charAt(0))
						.join('');
				}

				yearlyProductsTotal[externalReferenceCode] = {
					goalValue: 0,
					productExternalReferenceCode: externalReferenceCode,
					productName: productNameAbbrevation,
					totalSales: 0,
				};
			}
		);

		yearlyPolicies?.data?.items?.forEach(
			({
				productExternalReferenceCode,

				termPremium,
			}: PolicyTypes) => {
				yearlyProductsTotal[productExternalReferenceCode][
					'totalSales'
				] += termPremium;
			}
		);

		yearlySalesGoal?.data?.items?.forEach(
			({goalValue, productExternalReferenceCode}: SalesGoalTypes) => {
				if (yearlyProductsTotal[productExternalReferenceCode]) {
					yearlyProductsTotal[productExternalReferenceCode][
						'goalValue'
					] += goalValue;
				}
			}
		);

		Object.keys(yearlyProductsTotal).forEach(
			(productExternalReferenceCode: string) => {
				newProductList.push({
					active: false,
					goalValue:
						yearlyProductsTotal[productExternalReferenceCode][
							'goalValue'
						],
					productExternalReferenceCode,
					productName:
						yearlyProductsTotal[productExternalReferenceCode][
							'productName'
						],
					totalSales:
						yearlyProductsTotal[productExternalReferenceCode][
							'totalSales'
						],
				});
			}
		);
		setProducts(newProductList);
	};

	const settingLabelsPeriod = () => {
		if (isLoading === true) {
			labelRef?.current?.categories(getData()[0]?.label);
		}
	};
	const settingAnnualRules = async () => {
		const annualRuleValues = await annualRule(
			currentDateString,
			january,
			yearToDateGoalsArray,
			yearToDateSalesArray,
			productValues
		);
		setCurrentTooltip(annualRuleValues[0]);
		setYearToDateGoals(annualRuleValues[0]);
		setYearToDateSales(annualRuleValues[1]);

		if (lengthExceededColumn === indexOfCurrentMonth + 1) {
			setIsLoading(true);
			settingLabelsPeriod();
		}
	};

	const settingSixMonthRule = async () => {
		const sixMonthRuleValues = await sixMonthRule(
			currentDateString,
			productValues,
			sixMonthsAgoDate,
			sixMonthsGoalsArray,
			sixMonthsSalesArray
		);

		setCurrentTooltip(sixMonthRuleValues[0]);
		setSixMonthsGoalsData(sixMonthRuleValues[0]);
		setSixMonthsSalesData(sixMonthRuleValues[1]);

		if (lengthExceededColumn === 6) {
			setIsLoading(true);
			settingLabelsPeriod();
		}
	};

	const settingThreeMonthRule = async () => {
		const threeMonthRuleValues = await threeMonthRule(
			currentDateString,
			threeMonthsAgoDate,
			threeMonthsGoalsArray,
			threeMonthsSalesArray,
			productValues
		);

		setCurrentTooltip(threeMonthRuleValues[0]);
		setThreeMonthsGoalsData(threeMonthRuleValues[0]);
		setThreeMonthsSalesData(threeMonthRuleValues[1]);

		if (lengthExceededColumn === 3) {
			setIsLoading(true);
			settingLabelsPeriod();
		}
	};
	useEffect(() => {
		productsBaseSetup();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		if (timePeriod === PERIODS.YEAR) {
			settingAnnualRules();
			widthValue = 15;
		}

		if (timePeriod === PERIODS.SIX_MONTH) {
			settingSixMonthRule();
			widthValue = 24;
		}

		if (timePeriod === PERIODS.THREE_MONTH) {
			settingThreeMonthRule();
			widthValue = 36;
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [lengthExceededColumn, isLoading, timePeriod, productValues]);

	const handleProductFilterToggle = (
		productExternalReferenceCode: string
	) => {
		const newProducts = products.map((product) => {
			product.productExternalReferenceCode ===
			productExternalReferenceCode
				? (product.active = true)
				: (product.active = false);

			return product;
		});
		setProductsValues(productExternalReferenceCode);
		setProducts(newProducts);
		setIsLoading(false);
	};

	const currencyConversion = (value: number) =>
		value.toLocaleString('en-US', {
			currency: 'USD',
			style: 'currency',
		});

	const findActiveProduct = products.find((product) => product.active)
		?.productName;

	const isFilterAllActive = (product: ProductCell) => !product.active;

	const findActiveProducttotalSales = products.find(
		(product) => product.active
	)?.totalSales;
	const findActiveProductgoalValue = products.find(
		(product) => product.active
	)?.goalValue;

	return (
		<div className="d-flex flex-wrap ray-dashboard-product-performance">
			<div className="col-md-5 left-container px-0">
				<Header
					className="header-row px-4 py-3"
					title="Product Performance"
				/>

				<ProductList
					onSelect={handleProductFilterToggle}
					productList={products}
				/>
			</div>

			<div className="col-md-7 px-0 right-container">
				<div className="align-items-center d-flex header-row justify-content-between px-4 py-3">
					<p className="m-0 text-paragraph">
						<ClayButton
							className={classNames('general-filter mr-1', {
								'disabled font-weight-bolder': products.every(
									isFilterAllActive
								),
							})}
							displayType="unstyled"
							onClick={() => {
								if (!products.every(isFilterAllActive)) {
									setProductsValues(inicialProductValue);
									handleProductFilterToggle(
										inicialProductValue
									);
								}
							}}
						>
							All
						</ClayButton>

						{!products.every(isFilterAllActive) && (
							<>
								<ClayIcon
									className="mr-1"
									symbol="angle-right-small"
								/>
								<span className="font-weight-bolder">{`${findActiveProduct}`}</span>
							</>
						)}
					</p>

					<ClaySelect
						className="product-performance-select"
						onChange={({target}) => {
							setTimePeriod(target.value);
							setIsLoading(false);
						}}
						sizing="sm"
						value={timePeriod}
					>
						{TIME_PERIODS.map((timePeriod, index) => (
							<ClaySelect.Option
								key={index}
								label={timePeriod.label}
								value={timePeriod.value}
							/>
						))}
					</ClaySelect>
				</div>

				{!products.every(isFilterAllActive) && (
					<div className="dashboard-value-product-performance my-5">
						<div className="justify-content-between product-performance-value text-center">
							{currencyConversion(
								Number(findActiveProducttotalSales)
							)}
						</div>

						<div className="justify-content-between product-performance-goals text-center">
							Yearly Goal:
							{currencyConversion(
								Number(findActiveProductgoalValue)
							)}
						</div>
					</div>
				)}

				<div
					className="p-md-5 px-2 py-3"
					id="dashboard-product-performance-chart-container"
				>
					{!hasNoData ? (
						isLoading && (
							<ClayChart
								axis={{
									x: {
										height: 75,
										label: {
											position: 'outer-center',
											text: 'Period (Month)',
										},
										show: true,
										type: 'category',
									},
									y: {
										label: {
											position: 'outer-middle',
											text: 'Dollar ($)',
										},
										padding: {
											left: 200,
											right: 200,
										},
										show: true,
										tick: {
											format(x: string) {
												return '$' + x;
											},
										},
									},
								}}
								bar={{
									width: widthValue,
								}}
								data={dataChart}
								grid={{
									x: {
										show: false,
									},
									y: {
										show: true,
									},
								}}
								legend={{
									item: {
										onover: () => {
											return false;
										},
									},
									padding: 5,
									show: true,
								}}
								padding={{
									bottom: 20,
									right: 42.5,
								}}
								ref={labelRef}
								size={{
									height: 480,
									width: chartWidth,
								}}
								tooltip={tooltip}
							/>
						)
					) : (
						<div className="align-items-center d-flex flex-column justify-content-center mt-10 py-8">
							<span>No Data</span>
						</div>
					)}
				</div>
			</div>
		</div>
	);
};

export default ProductPerformance;
