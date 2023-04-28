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

import {BoxItem} from '../../../components/Form/DualListBox';
import SearchBuilder from '../../../core/SearchBuilder';
import {TestraySuite} from '../../../services/rest';
import {
	State as CaseParameter,
	initialState as CaseParameterInitialState,
} from './SuiteCasesModal';

const getCaseParameters = (testraySuite: TestraySuite): CaseParameter => {
	try {
		return JSON.parse(testraySuite.caseParameters);
	}
	catch (error) {
		return CaseParameterInitialState;
	}
};

const getCaseValues = (caseParameter: BoxItem[]) =>
	caseParameter?.map(({value}) => value);

const useSuiteCaseFilter = (testraySuite: TestraySuite) => {
	if (!testraySuite?.caseParameters) {
		return SearchBuilder.eq('suiteId', testraySuite?.id);
	}

	const caseParameters = getCaseParameters(testraySuite);

	const searchBuilder = new SearchBuilder();

	if (caseParameters?.testrayCaseTypes) {
		searchBuilder
			.in('caseTypeId', getCaseValues(caseParameters.testrayCaseTypes))
			.or();
	}

	if (caseParameters?.testrayComponents) {
		searchBuilder
			.in('componentId', getCaseValues(caseParameters.testrayComponents))
			.or();
	}

	if (caseParameters?.testrayRequirements) {
		searchBuilder.in(
			'requerimentsId',
			getCaseValues(caseParameters.testrayRequirements)
		);
	}

	return searchBuilder.build();
};

export {getCaseParameters, getCaseValues};

export default useSuiteCaseFilter;
