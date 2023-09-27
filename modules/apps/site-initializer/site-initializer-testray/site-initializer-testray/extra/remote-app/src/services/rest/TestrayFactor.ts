/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Rest from '../../core/Rest';
import {CategoryOptions} from '../../pages/Project/Routines/Builds/BuildForm/Stack/StackList';
import yupSchema from '../../schema/yup';
import {TestrayFactor, TestrayFactorOption} from './types';

type FactorEnviroment = typeof yupSchema.enviroment.__outputType;
type TestrayFactorForm = Omit<typeof yupSchema.factor.__outputType, 'id'>;

class TestrayFactorRest extends Rest<TestrayFactorForm, TestrayFactor> {
	constructor() {
		super({
			adapter: ({
				factorCategoryId: r_factorCategoryToFactors_c_factorCategoryId,
				factorOptionId: r_factorOptionToFactors_c_factorOptionId,
				name,
				routineId: r_routineToFactors_c_routineId,
				runId: r_runToFactors_c_runId,
			}) => ({
				name,
				r_factorCategoryToFactors_c_factorCategoryId,
				r_factorOptionToFactors_c_factorOptionId,
				r_routineToFactors_c_routineId,
				r_runToFactors_c_runId,
			}),
			nestedFields: 'factorOption,factorCategory',
			transformData: ({
				r_factorCategoryToFactors_c_factorCategory: factorCategory,
				r_factorOptionToFactors_c_factorOption: factorOption,
				...testrayFactor
			}) => ({
				...testrayFactor,
				factorCategory,
				factorOption,
			}),
			uri: 'factors',
		});
	}

	private formatCombinations(
		factorWithOptionsList: TestrayFactor[],
		combinations: TestrayFactor[][]
	): CategoryOptions[][] {
		return combinations.map((combination) =>
			combination
				.map((factor) => {
					const testrayFactor = factorWithOptionsList.find(
						({factorCategory}) =>
							factorCategory?.id === factor.factorCategory?.id
					) as TestrayFactor & {
						options: TestrayFactorOption[];
					};

					const factorOption = testrayFactor?.options?.find(
						({id}) => id === factor?.factorOption?.id
					);

					return {
						factorCategory: testrayFactor?.factorCategory?.name,
						factorCategoryId: factor?.factorCategory?.id,
						factorOption: factorOption?.name,
						factorOptionId: factor?.factorOption?.id,
					} as CategoryOptions;
				})
				.sort((a, b) =>
					a?.factorCategory.localeCompare(b?.factorCategory)
				)
		);
	}

	public getDefaultTestrayFactorOptionsMap(
		testrayFactors: TestrayFactor[]
	): Map<number, number> {
		const defaultTestrayFactorOptionsMap = new Map<number, number>();

		for (const defaultFactor of testrayFactors) {
			defaultTestrayFactorOptionsMap.set(
				defaultFactor?.factorOption?.id as number,
				defaultFactor?.factorCategory?.id as number
			);
		}

		return defaultTestrayFactorOptionsMap;
	}

	public getTestrayFactorCombinations(
		testrayFactors: TestrayFactor[],
		testrayFactorOptionIds: number[][]
	): CategoryOptions[][] {
		const defaultTestrayFactorOptionsMap = this.getDefaultTestrayFactorOptionsMap(
			testrayFactors
		);

		const selectedTestrayFactorOptionsMap = new Map<number, number>();

		const testrayFactorCategoryIds: number[] = testrayFactors.map(
			({factorCategory}) => factorCategory?.id as number
		);

		for (let i = 0; i < testrayFactorCategoryIds.length; i++) {
			const testrayFactorCategoryId = testrayFactorCategoryIds[i];
			const testrayFactorOptionIdsIndexed = testrayFactorOptionIds[i];

			for (const testrayFactorOptionId of testrayFactorOptionIdsIndexed) {
				if (testrayFactorOptionId === 0) {
					continue;
				}

				selectedTestrayFactorOptionsMap.set(
					testrayFactorOptionId,
					testrayFactorCategoryId
				);
			}
		}

		const testrayFactorCombinations: TestrayFactor[][] = [];

		for (const [
			factorOptionId,
			factorCategoryId,
		] of selectedTestrayFactorOptionsMap) {
			if (defaultTestrayFactorOptionsMap.has(factorOptionId)) {
				continue;
			}

			const testrayFactors: TestrayFactor[] = this.getDefaultTestrayFactors(
				defaultTestrayFactorOptionsMap,
				factorCategoryId
			);

			testrayFactors.push({
				factorCategory: {
					id: factorCategoryId,
				},
				factorOption: {
					id: factorOptionId,
				},
			} as TestrayFactor);

			testrayFactorCombinations.push(testrayFactors);
		}

		const defaultTestrayFactors: TestrayFactor[] = this.getDefaultTestrayFactors(
			defaultTestrayFactorOptionsMap,
			0
		);

		testrayFactorCombinations.push(defaultTestrayFactors);

		return this.formatCombinations(
			testrayFactors,
			testrayFactorCombinations
		);
	}

	public getDefaultTestrayFactors(
		defaultTestrayFactorOptionsMap: Map<number, number>,
		selectedTestrayFactorCategoryId: number
	): TestrayFactor[] {
		const testrayFactors: TestrayFactor[] = [];

		for (const [
			testrayFactorOptionId,
			testrayFactorCategoryId,
		] of defaultTestrayFactorOptionsMap) {
			if (testrayFactorCategoryId === selectedTestrayFactorCategoryId) {
				continue;
			}

			testrayFactors.push({
				factorCategory: {
					id: testrayFactorCategoryId,
				},
				factorOption: {
					id: testrayFactorOptionId,
				},
			} as TestrayFactor);
		}

		return testrayFactors;
	}

	public async selectEnvironmentFactor(
		factors: TestrayFactor[],
		factorOptionIds: number[],
		runId: number,
		newRun: boolean
	) {
		let index = 0;

		for (const factor of factors as TestrayFactor[]) {
			const factorOptionId = factorOptionIds[index];
			const factorCategoryId = factor?.factorCategory?.id;

			if (factorCategoryId && factorOptionId) {
				const data: TestrayFactorForm = {
					factorCategoryId: factorCategoryId.toString(),
					factorOptionId: factorOptionId.toString(),
					name: '',
					routineId: undefined,
					runId,
				};

				if (newRun) {
					await this.create(data);
				}
				else {
					await this.update(factor.id, data);
				}
			}

			index++;
		}
	}

	public async selectDefaultEnvironmentFactor(
		factorEnvironment: FactorEnviroment,
		factors: TestrayFactor[]
	): Promise<TestrayFactor[]> {
		let _factors: TestrayFactor[] = [];
		let index = 0;

		const {
			factorCategoryIds = [],
			factorOptionIds = [],
			routineId,
		} = factorEnvironment;

		const unselectedFactorsIds = factors
			.filter((factor) => factor.factorCategory)
			.filter(
				(factor) =>
					!(factorCategoryIds as number[]).includes(
						factor?.factorCategory?.id as number
					)
			)
			.map(({id}) => id);

		for (const unselectedFactorsId of unselectedFactorsIds) {
			await super.remove(unselectedFactorsId);
		}

		_factors = factors.filter(({id}) => !unselectedFactorsIds.includes(id));

		for (const factorCategoryId of factorCategoryIds) {
			const factor = factors.find(
				(factor) => factor?.factorCategory?.id === factorCategoryId
			);

			const factorOptionId = factorOptionIds[index];

			const form = {
				factorCategoryId,
				factorOptionId,
				routineId,
			};

			if (factor) {
				const isTheSameFactorValues =
					factor.factorCategory?.id === factorCategoryId &&
					factor.factorOption?.id === factorOptionId;

				if (!isTheSameFactorValues) {
					const updatedFactor = await super.update(factor.id, form);

					_factors = factors.map((_factor) => {
						if (_factor.id === factor.id) {
							return updatedFactor;
						}

						return _factor;
					});
				}
			}
			else {
				const newFactor = await super.create({
					...form,
					name: '',
					runId: 0,
				});

				_factors = [..._factors, newFactor];
			}

			index++;
		}

		return _factors;
	}
}

export const testrayFactorRest = new TestrayFactorRest();
