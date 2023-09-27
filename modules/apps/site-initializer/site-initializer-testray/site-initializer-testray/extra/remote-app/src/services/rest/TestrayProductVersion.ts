/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import TestrayError from '../../TestrayError';
import Rest from '../../core/Rest';
import SearchBuilder from '../../core/SearchBuilder';
import i18n from '../../i18n';
import yupSchema from '../../schema/yup';
import {APIResponse, TestrayProductVersion} from './types';

type ProductVersion = typeof yupSchema.productVersion.__outputType;

class TestrayProductVersionImpl extends Rest<
	ProductVersion,
	TestrayProductVersion
> {
	constructor() {
		super({
			adapter: ({
				name,
				projectId: r_projectToProductVersions_c_projectId,
			}) => ({
				name,
				r_projectToProductVersions_c_projectId,
			}),
			nestedFields: 'project',
			transformData: (testrayProductVersion) => ({
				...testrayProductVersion,
				project:
					testrayProductVersion?.r_projectToProductVersions_c_project,
			}),
			uri: 'productversions',
		});
	}

	private async validate(productVersion: ProductVersion, id?: number) {
		const searchBuilder = new SearchBuilder();

		if (id) {
			searchBuilder.ne('id', id).and();
		}

		const filter = searchBuilder
			.eq('name', productVersion.name)
			.and()
			.eq('projectId', productVersion.projectId as string)
			.build();

		const response = await this.fetcher<APIResponse<TestrayProductVersion>>(
			`/productversions?filter=${filter}`
		);

		if (response?.totalCount) {
			throw new TestrayError(
				i18n.sub('the-x-name-already-exists', 'product-version')
			);
		}
	}

	protected async beforeCreate(
		productVersion: ProductVersion
	): Promise<void> {
		await this.validate(productVersion);
	}

	protected async beforeUpdate(
		id: number,
		productVersion: ProductVersion
	): Promise<void> {
		await this.validate(productVersion, id);
	}
}

const testrayProductVersionImpl = new TestrayProductVersionImpl();

export {testrayProductVersionImpl};
