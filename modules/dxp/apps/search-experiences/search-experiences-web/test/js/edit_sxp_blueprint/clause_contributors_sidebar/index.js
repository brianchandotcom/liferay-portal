/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {render} from '@testing-library/react';
import React from 'react';

import ClauseContributorsSidebar from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/edit_sxp_blueprint/clause_contributors_sidebar/index';
const Toasts = require('../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/toasts');
import {filterAndSortClassNames} from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/utils';
import {mockClassNames} from '../../mocks/data';

import '@testing-library/jest-dom/extend-expect';

// Prevents "TypeError: Liferay.component is not a function" error on openToast

Toasts.openSuccessToast = jest.fn();

const keywordQueryContributors = filterAndSortClassNames(
	mockClassNames('KeywordQueryContributor')
);
const modelPrefilterContributors = filterAndSortClassNames(
	mockClassNames('ModelPrefilterContributor')
);
const queryPrefilterContributors = filterAndSortClassNames(
	mockClassNames('QueryPrefilterContributor')
);

function renderClause(props) {
	return render(
		<ClauseContributorsSidebar
			frameworkConfig={{
				clauseContributorsExcludes: [],
				clauseContributorsIncludes: [],
			}}
			initialClauseContributorsList={[
				{
					label: 'KeywordQueryContributor',
					value: keywordQueryContributors,
				},
				{
					label: 'ModelPrefilterContributor',
					value: modelPrefilterContributors,
				},
				{
					label: 'QueryPrefilterContributor',
					value: queryPrefilterContributors,
				},
			]}
			onFrameworkConfigChange={jest.fn()}
			visible={false}
			{...props}
		/>
	);
}

describe('QueryBuilder', () => {
	it('renders the clause contributors sidebar', () => {
		const {container} = renderClause();

		expect(container).not.toBeNull();
	});

	it('renders the list of contributors', () => {
		const {getByText} = renderClause();

		keywordQueryContributors.forEach((className) => getByText(className));

		modelPrefilterContributors.forEach((className) => getByText(className));

		queryPrefilterContributors.forEach((className) => getByText(className));
	});

	it('enables the correct number of active contributors', () => {
		const activeKeywordContributors = mockClassNames(
			'KeywordQueryContributor',
			false,
			5
		);

		const {getAllByLabelText} = renderClause({
			frameworkConfig: {
				clauseContributorsExcludes: [],
				clauseContributorsIncludes: activeKeywordContributors,
			},
		});

		expect(getAllByLabelText('on').length).toEqual(
			activeKeywordContributors.length
		);
	});
});
