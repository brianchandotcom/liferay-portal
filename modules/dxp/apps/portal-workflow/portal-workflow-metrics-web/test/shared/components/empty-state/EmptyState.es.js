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

import '@testing-library/jest-dom/extend-expect';
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import EmptyState from '../../../../src/main/resources/META-INF/resources/js/shared/components/empty-state/EmptyState.es';

describe('The EmptyState component should', () => {
	afterEach(cleanup);

	test('Be render with message only', () => {
		const {getByText} = render(<EmptyState filtered hideAnimation />);

		const message = getByText('no-results-were-found');

		expect(message).toBeTruthy();
	});

	test('Be render with message and animation', () => {
		const {container, getByText} = render(<EmptyState filtered />);

		const animation = container.querySelector(
			'.taglib-empty-search-result-message-header'
		);
		const message = getByText('no-results-were-found');

		expect(animation).toBeTruthy();
		expect(message).toBeTruthy();
	});

	test('Be render with message, title and animation', () => {
		const {container, getByText} = render(<EmptyState title="No data" />);

		const animation = container.querySelector(
			'.taglib-empty-result-message-header'
		);
		const message = getByText('there-is-no-data-at-the-moment');
		const title = getByText('No data');

		expect(animation).toBeTruthy();
		expect(message).toBeTruthy();
		expect(title).toBeTruthy();
	});

	test('Be render with message and action button', () => {
		const mockClick = jest.fn();

		const {getByText} = render(
			<EmptyState
				actionButton={<button onClick={mockClick}>Reload</button>}
				hideAnimation
				message="Failed to retrieve data, click on 'Reload' to retrying."
			/>
		);

		const button = getByText('Reload');
		const message = getByText(
			"Failed to retrieve data, click on 'Reload' to retrying."
		);

		expect(message).toBeTruthy();

		fireEvent.click(button);

		expect(mockClick).toHaveBeenCalled();
	});
});
