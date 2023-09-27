/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import TestrayError from '../../TestrayError';
import Rest from '../../core/Rest';
import SearchBuilder from '../../core/SearchBuilder';
import i18n from '../../i18n';
import yupSchema from '../../schema/yup';
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
				factorCategory:
					testrayFactorOption?.r_factorCategoryToOptions_c_factorCategory,
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
			throw new TestrayError(
				i18n.sub('the-x-name-already-exists', 'option')
			);
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
