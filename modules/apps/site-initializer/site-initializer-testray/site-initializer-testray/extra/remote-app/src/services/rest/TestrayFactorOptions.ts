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

import i18n from '../../i18n';
import yupSchema from '../../schema/yup';
import {SearchBuilder} from '../../util/search';
import Rest from './Rest';
import {APIResponse, TestrayFactorOption} from './types';

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

	protected async validate(factorOption: FactorOption, id?: number) {
		const searchBuilder = new SearchBuilder();

		if (id) {
			searchBuilder.ne('id', id).and();
		}

		const filter = searchBuilder.eq('name', factorOption.name).build();

		const response = await this.fetcher<APIResponse<TestrayFactorOption>>(
			`/factoroptions?filter=${filter}`
		);

		if (response?.totalCount) {
			throw new Error(i18n.sub('the-x-name-already-exists', 'option'));
		}
	}

	protected async beforeCreate(factorOption: FactorOption): Promise<void> {
		await this.validate(factorOption);
	}

	protected async beforeUpdate(
		id: number,
		factorOption: FactorOption
	): Promise<void> {
		await this.validate(factorOption, id);
	}
}

const testrayFactorOptionsImpl = new TestrayFactorOptionsImpl();

export {testrayFactorOptionsImpl};
