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
import {
	APIResponse,
	TestrayFactor,
	TestrayFactorCategory,
	TestrayFactorOption,
} from './types';

type FactorCategory = typeof yupSchema.factorCategory.__outputType;
class TestrayFactorCategoryRest extends Rest<
	FactorCategory,
	TestrayFactorCategory
> {
	constructor() {
		super({
			adapter: ({id, name}: FactorCategory) => ({
				id,
				name,
			}),
			uri: 'factorcategories',
		});
	}

	public async getFactorCategoryItems(factorItems: TestrayFactor[]) {
		const factorCategoryItems: Array<TestrayFactorOption[]> = [];

		for (const factorItem of factorItems) {
			const response = await this.getFactorCategoryOptions(
				factorItem?.factorCategory?.id as number
			);

			if (response?.items) {
				factorCategoryItems.push(response.items);
			}
		}

		return factorCategoryItems;
	}

	public async getFactorCategoryOptions(
		factorCategoryId: number
	): Promise<APIResponse<TestrayFactorOption> | undefined> {
		return this.fetcher(
			`/${this.uri}/${factorCategoryId}/factorCategoryToOptions?fields=id,name&pageSize=1000`
		);
	}

	protected async validate(factorCategory: FactorCategory, id?: number) {
		const searchBuilder = new SearchBuilder();

		if (id) {
			searchBuilder.ne('id', id).and();
		}

		const filter = searchBuilder.eq('name', factorCategory.name).build();

		const response = await this.fetcher<APIResponse<TestrayFactorCategory>>(
			`/factorcategories?filter=${filter}`
		);

		if (response?.totalCount) {
			throw new Error(i18n.sub('the-x-name-already-exists', 'category'));
		}
	}

	protected async beforeCreate(
		factorCategory: FactorCategory
	): Promise<void> {
		await this.validate(factorCategory);
	}

	protected async beforeUpdate(
		id: number,
		factorCategory: FactorCategory
	): Promise<void> {
		await this.validate(factorCategory, id);
	}
}

export const testrayFactorCategoryRest = new TestrayFactorCategoryRest();
