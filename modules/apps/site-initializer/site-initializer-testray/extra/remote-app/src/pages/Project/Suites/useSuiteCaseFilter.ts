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
import {TestraySuite} from '../../../graphql/queries';
import {SearchBuilder, searchUtil} from '../../../util/search';
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
	if (!testraySuite.caseParameters) {
		return searchUtil.eq('suiteId', testraySuite.id);
	}

	const caseParameters = getCaseParameters(testraySuite);

	const searchBuilder = new SearchBuilder()
		.in('caseTypeId', getCaseValues(caseParameters.testrayCaseTypes))
		.or()
		.in('componentId', getCaseValues(caseParameters.testrayComponents))
		.or()
		.in('componentId', getCaseValues(caseParameters.testrayRequirements))
		.build();

	return searchBuilder;
};

export {getCaseParameters};

export default useSuiteCaseFilter;
