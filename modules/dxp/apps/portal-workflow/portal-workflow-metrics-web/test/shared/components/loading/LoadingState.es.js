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
		const {getByTestId} = render(<LoadingState />);

		const loading = getByTestId('loadingState');

		expect(loading.children.length).toEqual(1);
		expect(loading.children[0].className).toBe('loading-animation');
	});

	test('Be render with loading message', () => {
		const {getByTestId} = render(
			<LoadingState message="fetching data..." />
		);

		const loading = getByTestId('loadingState');
		const loadingMsg = getByTestId('loadingStateMsg');

		expect(loading.children.length).toEqual(2);
		expect(loadingMsg).toHaveTextContent('fetching data...');
	});
});
