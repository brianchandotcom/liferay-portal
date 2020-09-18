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

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import LoadingState from '../../../../src/main/resources/META-INF/resources/js/shared/components/loading/LoadingState.es';

import '@testing-library/jest-dom/extend-expect';

describe('The LoadingState component should', () => {
	afterEach(cleanup);

	test('Be render with default props', () => {
		const {container} = render(<LoadingState />);

		const loading = container.querySelector('span.loading-animation');

		expect(loading).toBeTruthy();
	});

	test('Be render with loading message', () => {
		const {getByText} = render(<LoadingState message="fetching data..." />);

		const loadingMessage = getByText('fetching data...');

		expect(loadingMessage).toBeTruthy();
	});
});
