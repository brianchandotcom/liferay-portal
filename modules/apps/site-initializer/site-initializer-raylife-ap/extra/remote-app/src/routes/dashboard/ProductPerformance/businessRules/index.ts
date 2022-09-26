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

import {
	getPoliciesForSalesGoal,
	getSalesGoal,
} from '../../../../common/services';
import {SalesGoalTypes, SalesPolicesTypes} from '../ProductPerformanceTypes';

function getValuesFromArrayOfObjects(arrayOfObjects: any) {
	const valuesArray = arrayOfObjects?.map((values: any) => {
		return Object.values(values)[0];
	});

	return valuesArray;
}

function populateGoalsProducts(
	goalsResult: SalesGoalTypes[],
	goalsArray: string[],
	productExternalReferenceCode: string
) {
	goalsResult.forEach((policy: SalesGoalTypes) => {
		const month = new Date(policy.finalReferenceDate)
			.toUTCString()
			.split(' ')[2];
		goalsArray?.forEach((goalElement: any) => {
			if (month in goalElement) {
				if (
					policy?.productExternalReferenceCode ===
					productExternalReferenceCode
				) {
					goalElement[month] = policy?.goalValue;
				}
				else {
					goalElement[month] += policy?.goalValue;
				}
			}
		});
	});

	return goalsArray;
}

function populateSalesProducts(
	policiesResult: SalesPolicesTypes[],
	policiesArray: string[],
	productExternalReferenceCode: string
) {
	policiesResult.forEach((policy: SalesPolicesTypes) => {
		const month = new Date(policy?.boundDate).toUTCString().split(' ')[2];
		policiesArray?.forEach((policyElement: any) => {
			if (month in policyElement) {
				if (
					policy?.productExternalReferenceCode ===
					productExternalReferenceCode
				) {
					policyElement[month] += policy?.termPremium;
				}
				else {
					policyElement[month] += policy?.termPremium;
				}
			}
		});
	});

	return policiesArray;
}

const getArrayOfSalesProducts = (
	response: SalesPolicesTypes[],
	arrayOfMonthsArray: string[],
	productExternalReferenceCode: string
) => {
	const monthsResult = response;
	const arrayOfMonths = populateSalesProducts(
		monthsResult,
		arrayOfMonthsArray,
		productExternalReferenceCode
	);

	return getValuesFromArrayOfObjects(arrayOfMonths);
};

const getArrayOfGoalsProducts = (
	response: SalesGoalTypes[],
	monthsAgoGoalsArray: string[],
	productExternalReferenceCode: string
) => {
	const monthsGoalsResult = response;
	const monthsAgoGoals = populateGoalsProducts(
		monthsGoalsResult,
		monthsAgoGoalsArray,
		productExternalReferenceCode
	);

	return getValuesFromArrayOfObjects(monthsAgoGoals);
};

const compareProducts = (data: any, productExternalReferenceCode: string) => {
	return data.filter((item: SalesGoalTypes | SalesPolicesTypes) => {
		if (
			item.productExternalReferenceCode === productExternalReferenceCode
		) {
			return item;
		}
		else if (productExternalReferenceCode === 'All') {
			return item;
		}
	});
};

export async function annualRule(
	currentDateString: string[],
	january: string,
	yearToDateGoalsArray: string[],
	yearToDateSalesArray: string[],
	productExternalReferenceCode: string
) {
	const salesGoal = await getSalesGoal(
		currentDateString[0],
		currentDateString[1],
		currentDateString[0],
		january
	);

	const policiesForSalesGoal = await getPoliciesForSalesGoal(
		currentDateString[0],
		currentDateString[1],
		currentDateString[0],
		january
	);

	const getSalesGoalData = salesGoal?.data?.items;

	const getPoliciesSalesGoals = policiesForSalesGoal?.data?.items;

	const goalsProduct = await compareProducts(
		getSalesGoalData,
		productExternalReferenceCode
	);

	const salesProduct = await compareProducts(
		getPoliciesSalesGoals,
		productExternalReferenceCode
	);

	const yearToDateGoalsResultProducts = getArrayOfGoalsProducts(
		goalsProduct,
		yearToDateGoalsArray,
		productExternalReferenceCode
	);

	const yearToDateSalesResultProducts = getArrayOfSalesProducts(
		salesProduct,
		yearToDateSalesArray,
		productExternalReferenceCode
	);

	return [yearToDateGoalsResultProducts, yearToDateSalesResultProducts];
}

export async function sixMonthRule(
	currentDateString: string[],
	productExternalReferenceCode: string,
	sixMonthsAgoDate: string[],
	sixMonthsGoalsArray: string[],
	sixMonthsSalesArray: string[]
) {
	const salesGoal = await getSalesGoal(
		currentDateString[0],
		currentDateString[1],
		sixMonthsAgoDate[0],
		sixMonthsAgoDate[1]
	);

	const policiesForSalesGoal = await getPoliciesForSalesGoal(
		currentDateString[0],
		currentDateString[1],
		sixMonthsAgoDate[0],
		sixMonthsAgoDate[1]
	);

	const getSalesGoalData = salesGoal?.data?.items;

	const getPoliciesSalesGoals = policiesForSalesGoal?.data?.items;

	const goalsProduct = await compareProducts(
		getSalesGoalData,
		productExternalReferenceCode
	);

	const salesProduct = await compareProducts(
		getPoliciesSalesGoals,
		productExternalReferenceCode
	);

	const sixMonthsGoalsResultProducts = getArrayOfGoalsProducts(
		goalsProduct,
		sixMonthsGoalsArray,
		productExternalReferenceCode
	);

	const sixMonthsSalesResultProducts = getArrayOfSalesProducts(
		salesProduct,
		sixMonthsSalesArray,
		productExternalReferenceCode
	);

	return [sixMonthsGoalsResultProducts, sixMonthsSalesResultProducts];
}

export async function threeMonthRule(
	currentDateString: string[],
	threeMonthsAgoDate: string[],
	threeMonthsGoalsArray: string[],
	threeMonthsSalesArray: string[],
	productExternalReferenceCode: string
) {
	const salesGoal = await getSalesGoal(
		currentDateString[0],
		currentDateString[1],
		threeMonthsAgoDate[0],
		threeMonthsAgoDate[1]
	);

	const policiesForSalesGoal = await getPoliciesForSalesGoal(
		currentDateString[0],
		currentDateString[1],
		threeMonthsAgoDate[0],
		threeMonthsAgoDate[1]
	);

	const getSalesGoalData = salesGoal?.data?.items;

	const getPoliciesSalesGoals = policiesForSalesGoal?.data?.items;

	const goalsProduct = await compareProducts(
		getSalesGoalData,
		productExternalReferenceCode
	);

	const salesProduct = await compareProducts(
		getPoliciesSalesGoals,
		productExternalReferenceCode
	);

	const threeMonthsGoalsResultProducts = getArrayOfGoalsProducts(
		goalsProduct,
		threeMonthsGoalsArray,
		productExternalReferenceCode
	);

	const threeMonthsSalesResultProducts = getArrayOfSalesProducts(
		salesProduct,
		threeMonthsSalesArray,
		productExternalReferenceCode
	);

	return [threeMonthsGoalsResultProducts, threeMonthsSalesResultProducts];
}
