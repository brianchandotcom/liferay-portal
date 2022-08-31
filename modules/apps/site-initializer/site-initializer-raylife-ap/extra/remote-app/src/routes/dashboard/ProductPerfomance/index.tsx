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
} from '../../../common/utils/dateFormatter';
import {dataColumn} from './DataProductPerfomance';
import {
	BarChartPerformanceTypes,
	DataChart,
	MonthProperties,
	Policy,
	ProductListType,
	SalesGoal,
} from './ProductPerfomanceTypes';

const PERIOD = {
	SIX_MONTH: '1',
	THREE_MONTH: '2',
	YTD: '0',
};

const BarChartPerformancee: BarChartPerformanceTypes = {
	colors: [],
	dataColumns: [],
	groups: [''],
	height: 338,
	labelColumns: [],
	showLegend: false,
	showTooltip: true,
	titleTotal: true,
	totalSum: 0,
	width: 700,
};

const TIME_PERIODS = [
	{
		label: '3 MO',
		padding: 30,
		value: '2',
		width: 20,
	},
	{
		label: '6 MO',
		padding: 100,
		value: '1',
		width: 50,
	},
	{
		label: 'YTD',
		padding: 130,
		value: '0',
		width: 120,
	},
];

const colors: {[keys: string]: {}} = {
	achieved: '#55C2FF',
	exceeded: '#FFD76E',
	goals: '#DCF1FD',
};

const date = new Date();
const currentMonth = date.getMonth();
const threeMonthsAgo = currentMonth - 3;
const sixMonthsAgo = currentMonth - 6;

const paddingValue = 100;

const ProductPerformance = () => {
	const [products, setProducts] = useState<ProductCell[]>([]);
	const [timePeriod, setTimePeriod] = useState(PERIOD.THREE_MONTH);
	const [filterChart, setFilterChart] = useState<MonthProperties[]>(
		filterByPeriod(threeMonthsAgo)
	);
	const [labelAxisX] = useState<[]>();
	const ref = useRef<any>();

	function filterByPeriod(period: number) {
		const months: MonthProperties[] = Object.values(dataColumn);
		const periodFiltered = months.filter((month) =>
			timePeriod === PERIOD.YTD
				? month.index <= currentMonth
				: month.index < currentMonth + 1 && month.index > period
		);

		return periodFiltered;
	}

	function setLabelByPeriod(filteredPeriod: MonthProperties[]) {
		const definedLabel = filteredPeriod.map((month) => month.label);

		return definedLabel;
	}

	const setLabelYearly = setLabelByPeriod(filterByPeriod(currentMonth));
	const setLabelSix = setLabelByPeriod(filterByPeriod(sixMonthsAgo));
	const setLabelThree = setLabelByPeriod(filterByPeriod(threeMonthsAgo));

	const achieved = filterChart.map((month: MonthProperties) =>
		month.achieved > month.goals ? month.goals : month.achieved
	);
	const exceeded = filterChart.map((month: MonthProperties) =>
		month.achieved > month.goals ? month.achieved - month.goals : NaN
	);
	const goals = filterChart.map((month: MonthProperties) =>
		month.goals < 0 || month.goals < month.achieved ? NaN : month.goals
	);

	const dataChart: DataChart = {
		data: {
			columns: [
				['achieved', ...achieved],
				['exceeded', ...exceeded],
				['goals', ...goals],
			],
			groups: [
				['achieved', 'exceeded'],
				['achieved', 'goals'],
			],
		},
	};

	const changeFilter = (timePeriod: string) => {
		if (timePeriod === PERIOD.SIX_MONTH) {
			setFilterChart(filterByPeriod(sixMonthsAgo));

			return ref.current.categories(setLabelSix);
		}

		if (timePeriod === PERIOD.THREE_MONTH) {
			setFilterChart(filterByPeriod(threeMonthsAgo));

			return ref.current.categories(setLabelThree);
		}

		if (timePeriod === PERIOD.YTD) {
			setFilterChart(filterByPeriod(currentMonth));

			return ref.current.categories(setLabelYearly);
		}

		return timePeriod === PERIOD.YTD;
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
					yearlyProductsTotal[productExternalReferenceCode] = {
						goalValue: 0,
						productName,
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

	useEffect(() => {
		productsBaseSetup();

		changeFilter(timePeriod);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [timePeriod]);

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

		setProducts(newProducts);
	};

	const isFilterAllActive = (product: ProductCell) => !product.active;

	const findActiveProduct = products.find((product) => product.active)
		?.productName;

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
									handleProductFilterToggle('All');
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

				<div className="p-5">
					<ClayChart
						axis={{
							x: {
								categories: labelAxisX,
								height: 85,
								label: {
									position: 'outer-center',
									text: 'Period (Month)',
								},
								position: {x: 30},
								show: true,
								type: 'category',
								width: 100,
							},
							y: {
								height: 80,
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
									format(value: string) {
										return '$' + value;
									},
									stepSize: 50,
								},
								width: 100,
							},
						}}
						bar={{
							width: 20,
						}}
						data={{
							colors,
							columns: dataChart.data.columns,
							groups: dataChart.data.groups,
							order: {
								function() {
									Object.values(
										dataColumn
									).map((month: MonthProperties) =>
										month.achieved > month.goals
											? 'asc'
											: 'desc '
									);
								},
							},
							type: 'bar',
						}}
						grid={{
							x: {
								show: true,
							},
							y: {
								show: true,
							},
						}}
						legend={{
							show: false,
						}}
						padding={{
							right: paddingValue,
						}}
						ref={ref}
						size={{
							height: BarChartPerformancee.height,
							width: BarChartPerformancee.width,
						}}
						tooltip={{
							show: true,
						}}
					/>
				</div>
			</div>
		</div>
	);
};

export default ProductPerformance;
