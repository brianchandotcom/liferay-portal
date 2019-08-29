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

import ClayEmptyState from '../../../../src/main/resources/META-INF/resources/js/components/shared/ClayEmptyState.es';
import React from 'react';
import {render} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

describe('ClayEmptyState', () => {
	it('renders', () => {
		const {asFragment} = render(<ClayEmptyState />);

		expect(asFragment()).toMatchSnapshot();
	});

	it('displays a custom title', () => {
		const {getByText} = render(<ClayEmptyState title="Test Title" />);

		expect(getByText('Test Title')).toBeInTheDocument();
	});

	it('displays a custom description', () => {
		const {getByText} = render(
			<ClayEmptyState description="Test Description" />
		);

		expect(getByText('Test Description')).toBeInTheDocument();
	});
});
