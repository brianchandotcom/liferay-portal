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
import {TestrayFactorOption} from './types';

type FactorOption = typeof yupSchema.factorOption.__outputType;

class TestrayFactorOptionsImpl extends Rest<FactorOption, TestrayFactorOption> {
	constructor() {
		super({
			adapter: ({
				factorCategoryId: r_factorCategoryToOptions_c_factorCategoryId,
				name,
			}: FactorOption) => ({
				name,
				r_factorCategoryToOptions_c_factorCategoryId,
			}),
			nestedFields: 'factorCategory',
			transformData: (testrayFactorOption) => ({
				...testrayFactorOption,
				factorCategory: testrayFactorOption?.r_factorCategoryToOptions_c_factorCategory
					? {
							...testrayFactorOption.r_factorCategoryToOptions_c_factorCategory,
					  }
					: undefined,
			}),
			uri: 'factoroptions',
		});
	}
}

const testrayFactorOptionsImpl = new TestrayFactorOptionsImpl();

export {testrayFactorOptionsImpl};
