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

import yupSchema from '../../schema/yup';
import Rest from './Rest';
import {TestrayFactor} from './types';

type TestrayFactorType = Omit<typeof yupSchema.factor.__outputType, 'id'>;

class TestrayFactorRest extends Rest<TestrayFactorType, TestrayFactor> {
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
}

export const testrayFactorRest = new TestrayFactorRest();
