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

function getValuesFromArrayOfObjects(arrayOfObjects: any) {
	const valuesArray = arrayOfObjects?.map((values: string) => {
		return Object.values(values)[0];
	});

	return valuesArray;
}

function populateGoals(goalsResult: string[], goalsArray: string[]) {
	goalsResult.forEach((policy: any) => {
		const month = new Date(policy?.finalReferenceDate)
			.toUTCString()
			.split(' ')[2];

		goalsArray?.forEach((goalElement: any) => {
			if (month in goalElement) {
				goalElement[month] += policy?.goalValue;
			}
		});
	});

	return goalsArray;
}

function populateSales(policiesResult: string[], policiesArray: string[]) {
	policiesResult.forEach((policy: any) => {
		const month = new Date(policy?.boundDate).toUTCString().split(' ')[2];

		policiesArray?.forEach((policyElement: any) => {
			if (month in policyElement) {
				policyElement[month] += policy?.termPremium;
			}
		});
	});

	return policiesArray;
}

const getArrayOfSales = (response: any, arrayOfMonthsArray: string[]) => {
	const monthsResult = response?.data?.items;
	const arrayOfMonths = populateSales(monthsResult, arrayOfMonthsArray);

	return getValuesFromArrayOfObjects(arrayOfMonths);
};
const getArrayOfGoals = (response: any, monthsAgoGoalsArray: string[]) => {
	const monthsGoalsResult = response?.data?.items;
	const monthsAgoGoals = populateGoals(
		monthsGoalsResult,
		monthsAgoGoalsArray
	);

	return getValuesFromArrayOfObjects(monthsAgoGoals);
};

export async function annualRule(
	currentDateString: string[],
	january: string,
	yearToDateGoalsArray: string[],
	yearToDateSalesArray: string[]
) {
	const salesGoal = await getSalesGoal(
		currentDateString[0],
		currentDateString[1],
		currentDateString[0],
		january
	);
	const yearToDateGoalsResult = getArrayOfGoals(
		salesGoal,
		yearToDateGoalsArray
	);

	const policiesSalesGoals = await getPoliciesForSalesGoal(
		currentDateString[0],
		currentDateString[1],
		currentDateString[0],
		january
	);
	const yearToDateSalesResult = getArrayOfSales(
		policiesSalesGoals,
		yearToDateSalesArray
	);

	return [yearToDateGoalsResult, yearToDateSalesResult];
}

export async function sixMonthRule(
	currentDateString: string[],
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
	const lastSixMonthsGoalsResult = getArrayOfGoals(
		salesGoal,
		sixMonthsGoalsArray
	);

	const policiesForSalesGoal = await getPoliciesForSalesGoal(
		currentDateString[0],
		currentDateString[1],
		sixMonthsAgoDate[0],
		sixMonthsAgoDate[1]
	);
	const lastSixMonthsSalesResult = getArrayOfSales(
		policiesForSalesGoal,
		sixMonthsSalesArray
	);

	return [lastSixMonthsGoalsResult, lastSixMonthsSalesResult];
}

export async function threeMonthRule(
	currentDateString: string[],
	threeMonthsAgoDate: string[],
	threeMonthsGoalsArray: string[],
	threeMonthsSalesArray: string[]
) {
	const salesGoal = await getSalesGoal(
		currentDateString[0],
		currentDateString[1],
		threeMonthsAgoDate[0],
		threeMonthsAgoDate[1]
	);
	const lastThreeMonthsGoalsResult = getArrayOfGoals(
		salesGoal,
		threeMonthsGoalsArray
	);

	const policiesForSalesGoal = await getPoliciesForSalesGoal(
		currentDateString[0],
		currentDateString[1],
		threeMonthsAgoDate[0],
		threeMonthsAgoDate[1]
	);
	const lastThreeMonthsSalesResult = getArrayOfSales(
		policiesForSalesGoal,
		threeMonthsSalesArray
	);

	return [lastThreeMonthsGoalsResult, lastThreeMonthsSalesResult];
}
