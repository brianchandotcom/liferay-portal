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
			throw new Error(
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
