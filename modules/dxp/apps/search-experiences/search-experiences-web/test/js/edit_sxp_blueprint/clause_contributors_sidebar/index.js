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

import ClauseContributorsSidebar from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/edit_sxp_blueprint/clause_contributors_sidebar';
const Toasts = require('../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/toasts');
import {mockClassNames} from '../../mocks/data';

import '@testing-library/jest-dom/extend-expect';

// Prevents "TypeError: Liferay.component is not a function" error on openToast

Toasts.openSuccessToast = jest.fn();

// Suppress act warning until @testing-library/react is updated past 9
// to use screen. See https://javascript.plainenglish.io/
// you-probably-dont-need-act-in-your-react-tests-2a0bcd2ad65c

const originalError = console.error;

beforeAll(() => {
	console.error = (...args) => {
		if (/Warning.*not wrapped in act/.test(args[0])) {
			return;
		}
		originalError.call(console, ...args);
	};
});

afterAll(() => {
	console.error = originalError;
});

function renderClause(props) {
	return render(
		<ClauseContributorsSidebar
			frameworkConfig={{
				clauseContributorsExcludes: [],
				clauseContributorsIncludes: [],
			}}
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

	it('renders the list of contributors', async () => {
		const {findAllByText, getByText} = renderClause();

		await findAllByText('KeywordQueryContributor', {
			exact: false,
		});

		mockClassNames('KeywordQueryContributor').forEach(({className}) =>
			getByText(className)
		);

		mockClassNames('ModelPrefilterContributor').forEach(({className}) =>
			getByText(className)
		);

		mockClassNames('QueryPrefilterContributor').forEach(({className}) =>
			getByText(className)
		);
	});

	it('enables the correct number of active contributors', async () => {
		const activeKeywordContributors = mockClassNames(
			'KeywordQueryContributor',
			false,
			5
		);

		const {findAllByText, getAllByLabelText} = renderClause({
			frameworkConfig: {
				clauseContributorsExcludes: [],
				clauseContributorsIncludes: activeKeywordContributors,
			},
		});

		await findAllByText('KeywordQueryContributor', {
			exact: false,
		});

		expect(getAllByLabelText('on').length).toEqual(
			activeKeywordContributors.length
		);
	});
});
