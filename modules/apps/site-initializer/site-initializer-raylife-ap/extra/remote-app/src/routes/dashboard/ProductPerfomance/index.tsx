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
import ClayChart from '@clayui/charts';

import 'clay-charts-react/lib/css/main.css';
import {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useEffect, useRef, useState} from 'react';

import Header from '../../../common/components/header';
import ProductList, {
	ProductCell,
} from '../../../common/components/product-list';
import {getPoliciesForSalesGoal, getSalesGoal} from '../../../common/services';
import {
	currentDateString,
	december,
	january,
	sixMonthsAgoDate,
	threeMonthsAgoDate,
} from '../../../common/utils/dateFormatter';
import {
	BarChartPerformanceTypes,
	Policy,
	ProductListType,
	SalesGoal,
} from './ProductPerfomanceTypes';
import {annualRule, sixMonthRule, threeMonthRule} from './businessRules';
import {populateDataByPeriod} from './filterPeriodRules';

const BarChartPerformancee: BarChartPerformanceTypes = {
	colors: [],
	dataColumns: [],
	groups: [''],
	height: 400,
	labelColumns: [],
	showLegend: false,
	showTooltip: true,
	titleTotal: true,
	totalSum: 0,
	width: 600,
};

const TIME_PERIODS = [
	{
		label: '3 MO',
		value: '2',
	},
	{
		label: '6 MO',
		value: '1',
	},
	{
		label: 'YTD',
		value: '0',
	},
];

const colors: {[keys: string]: {}} = {
	achieved: '#55C2FF',
	exceeded: '#FFD76E',
	goals: '#DCF1FD',
};

let paddingValue = 100;
let widthValue = 20;

const ProductPerformance = () => {
	const [products, setProducts] = useState<ProductCell[]>([]);
	const [timePeriod, setTimePeriod] = useState('0');
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
	let categoryLabelTooltip = '';

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
			period: 2,
			periodDate: 'Period',
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
			period: 1,
			periodDate: 'Period',
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
			period: 0,
			periodDate: 'Period',
			tooltip: yearToDateGoals,
		},
	];

	const getData = () => {
		return loadData?.filter((data) => data.period === Number(timePeriod));
	};

	const lengthExceededColumn = getData()[0]?.exceeded.length - 1;

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

	const tooltip = {
		format: {
			name(categoryLabel: string) {
				categoryLabelTooltip = categoryLabel;

				return categoryLabel;
			},

			value(value: number, _id: number, _index: number, x: number) {
				return categoryLabelTooltip === 'goals'
					? currentTooltip[x]
					: value;
			},
		},
		grouped: false,
		linked: false,
		show: true,
	};

	const productsBaseSetup = async () => {
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

		yearlyPolicies?.data?.items?.forEach(
			({
				productExternalReferenceCode,
				productName,
				termPremium,
			}: Policy) => {
				if (!yearlyProductsTotal[productExternalReferenceCode]) {
					let productNameAbbrevation = productName;

					if (productName.length > 8) {
						productNameAbbrevation = productName
							.split(' ')
							.map((product) => product.charAt(0))
							.join('');
					}

					yearlyProductsTotal[productExternalReferenceCode] = {
						goalValue: 0,
						productName: productNameAbbrevation,
						totalSales: termPremium,
					};

					return;
				}

				yearlyProductsTotal[productExternalReferenceCode][
					'totalSales'
				] += termPremium;
			}
		);

		yearlySalesGoal?.data?.items?.forEach(
			({goalValue, productExternalReferenceCode}: SalesGoal) => {
				yearlyProductsTotal[productExternalReferenceCode][
					'goalValue'
				] += goalValue;
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
			labelRef.current.categories(getData()[0]?.label);
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
		setYearToDateGoals(annualRuleValues[0]);
		setYearToDateSales(annualRuleValues[1]);
		setCurrentTooltip(annualRuleValues[0]);

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
		setSixMonthsGoalsData(sixMonthRuleValues[0]);
		setSixMonthsSalesData(sixMonthRuleValues[1]);

		if (lengthExceededColumn === 6) {
			setCurrentTooltip(sixMonthRuleValues[0]);
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
	}, []);

	useEffect(() => {
		if (timePeriod === '0') {
			settingAnnualRules();
			paddingValue = 100;
			widthValue = 15;
		}

		if (timePeriod === '1') {
			settingSixMonthRule();
			paddingValue = 100;
			widthValue = 20;
		}

		if (timePeriod === '2') {
			settingThreeMonthRule();
			paddingValue = 100;
			widthValue = 30;
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

	const findActiveProduct = products.find((product) => product.active)
		?.productName;
	const isFilterAllActive = (product: ProductCell) => !product.active;

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

				<div className="p-md-5 px-2 py-3">
					{isLoading && (
						<ClayChart
							axis={{
								x: {
									height: 65,
									label: {
										position: 'outer-center',
										text: 'Period (Month)',
									},
									position: {x: 30},
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
										stepSize: 10000,
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
								show: true,
							}}
							padding={{
								right: paddingValue,
							}}
							ref={labelRef}
							size={{
								height: BarChartPerformancee.height,
								width: BarChartPerformancee.width,
							}}
							tooltip={tooltip}
						/>
					)}
				</div>
			</div>
		</div>
	);
};

export default ProductPerformance;
