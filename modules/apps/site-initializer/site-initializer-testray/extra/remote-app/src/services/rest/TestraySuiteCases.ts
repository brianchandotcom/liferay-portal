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

import fetcher from '../fetcher';

const nestedFieldsParam =
	'nestedFields=case.component,suite&nestedFieldsDepth=2';

const suitesCasesResource = `/suitescaseses?${nestedFieldsParam}`;

const createSuiteCaseBatch = (suites: {caseId: number; suiteId: number}[]) => {
	if (suites.length <= 20) {
		return Promise.allSettled(
			suites.map(
				({
					caseId: r_caseToSuitesCases_c_caseId,
					suiteId: r_suiteToSuitesCases_c_suiteId,
				}) =>
					fetcher.post('/suitescaseses', {
						name:
							new Date().getTime() + r_caseToSuitesCases_c_caseId,
						r_caseToSuitesCases_c_caseId,
						r_suiteToSuitesCases_c_suiteId,
					})
			)
		);
	}

	fetcher.post(
		'/suitescaseses/batch',
		suites.map(
			({
				caseId: r_caseToSuitesCases_c_caseId,
				suiteId: r_suiteToSuitesCases_c_suiteId,
			}: any) => ({
				name: new Date().getTime() + r_caseToSuitesCases_c_caseId,
				r_caseToSuitesCases_c_caseId,
				r_suiteToSuitesCases_c_suiteId,
			})
		)
	);
};

export {createSuiteCaseBatch, suitesCasesResource};
