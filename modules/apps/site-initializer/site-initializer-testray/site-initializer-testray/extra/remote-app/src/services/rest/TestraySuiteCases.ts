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

import Rest from '~/core/Rest';
import yupSchema from '~/schema/yup';

import {TestraySuiteCase} from './types';

type SuiteCase = typeof yupSchema.suiteCase.__outputType;

class TestraySuiteImpl extends Rest<SuiteCase, TestraySuiteCase> {
	constructor() {
		super({
			adapter: ({
				caseId: r_caseToSuitesCases_c_caseId,
				name,
				suiteId: r_suiteToSuitesCases_c_suiteId,
			}) => ({
				name,
				r_caseToSuitesCases_c_caseId,
				r_suiteToSuitesCases_c_suiteId,
			}),
			nestedFields: 'case.component,suite',
			uri: 'suitescaseses',
		});
	}

	public async createSuiteCase(casesId: number[], suiteId: number) {
		for (const caseId of casesId) {
			await super.create({
				caseId,
				name: new Date().getTime() + String(caseId),
				suiteId,
			});
		}
	}
}

export const testraySuiteCaseImpl = new TestraySuiteImpl();
