/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	if (caseParameters?.testrayCaseTypes?.length) {
		searchBuilder
			.in('caseTypeId', getCaseValues(caseParameters.testrayCaseTypes))
			.or();
	}

	if (caseParameters?.testrayComponents?.length) {
		searchBuilder
			.in('componentId', getCaseValues(caseParameters.testrayComponents))
			.or();
	}

	if (caseParameters?.testrayRequirements?.length) {
		searchBuilder.in(
			'requerimentsId',
			getCaseValues(caseParameters.testrayRequirements)
		);
	}

	return searchBuilder.build();
};

export {getCaseParameters, getCaseValues};

export default useSuiteCaseFilter;
