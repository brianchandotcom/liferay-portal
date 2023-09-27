/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import TestrayError from '../../TestrayError';
import Rest from '../../core/Rest';
import SearchBuilder from '../../core/SearchBuilder';
import i18n from '../../i18n';
import yupSchema from '../../schema/yup';
import {APIResponse, TestrayCaseType} from './types';

type CaseType = typeof yupSchema.caseType.__outputType;

class TestrayCaseTypeImpl extends Rest<CaseType, TestrayCaseType> {
	constructor() {
		super({
			adapter: ({name}) => ({
				name,
			}),
			uri: 'casetypes',
		});
	}

	protected async validate(caseType: CaseType, id?: number) {
		const searchBuilder = new SearchBuilder();

		if (id) {
			searchBuilder.ne('id', id).and();
		}

		const filter = searchBuilder.eq('name', caseType.name).build();

		const response = await this.fetcher<APIResponse<TestrayCaseType>>(
			`/casetypes?filter=${filter}`
		);

		if (response?.totalCount) {
			throw new TestrayError(
				i18n.sub('the-x-name-already-exists', 'case-type')
			);
		}
	}

	protected async beforeCreate(caseType: CaseType): Promise<void> {
		await this.validate(caseType);
	}

	protected async beforeUpdate(
		id: number,
		caseType: CaseType
	): Promise<void> {
		await this.validate(caseType, id);
	}
}

export const testrayCaseTypeImpl = new TestrayCaseTypeImpl();
