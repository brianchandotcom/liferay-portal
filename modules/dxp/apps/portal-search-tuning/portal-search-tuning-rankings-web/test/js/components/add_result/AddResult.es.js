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

import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import AddResult from '../../../../src/main/resources/META-INF/resources/js/components/add_result/AddResult.es';
import {
	FETCH_SEARCH_DOCUMENTS_URL,
	getMockResultsData,
} from '../../mocks/data.es';

import '@testing-library/jest-dom/extend-expect';

const MODAL_ID = 'add-result-modal';

describe('AddResult', () => {
	beforeEach(() => {
		fetch.mockResponse(JSON.stringify(getMockResultsData()));
	});

	it('shows an add result button', async () => {
		const {getByText} = render(
			<AddResult
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
			/>
		);

		expect(getByText('add-result')).toBeInTheDocument();
	});

	it('shows a modal when the add a result button gets clicked', async () => {
		const {findByTestId, getByText} = render(
			<AddResult
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
			/>
		);

		fireEvent.click(getByText('add-result'));

		const modal = await findByTestId(MODAL_ID);

		expect(modal).toBeInTheDocument();
	});
});
