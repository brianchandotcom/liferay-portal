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

import Results from '../../../src/main/resources/META-INF/resources/js/components/Results';
import {RESOURCE} from '../mocks/data';

import '@testing-library/jest-dom/extend-expect';

function renderResults(props) {
	return render(
		<Results
			activePage={1}
			items={RESOURCE.items}
			onPageChange={jest.fn()}
			query="test"
			totalHits={2000}
			totalPages={20}
			{...props}
		/>
	);
}

describe('BlueprintsSearch', () => {
	it('displays titles of results', () => {
		const {getByText} = renderResults();

		RESOURCE.items.map((item) => getByText(item.title));
	});

	it('displays descriptions of results', () => {
		const {getByText} = renderResults();

		RESOURCE.items.map((item) => getByText(item.description));
	});

	it('displays dates of results', () => {
		const {getByText} = renderResults();

		RESOURCE.items.map((item) => getByText(`on ${item.date}`));
	});
});
