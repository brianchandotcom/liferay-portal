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

import Rest from './Rest';
import {TestrayFactor} from './types';

class TestrayFactorRest extends Rest<any, TestrayFactor> {
	constructor() {
		super({
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
