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

import HeaderTitle from '../../../../src/main/resources/META-INF/resources/js/shared/components/header/HeaderTitle.es';

import '@testing-library/jest-dom/extend-expect';

describe('The HeaderTitle component should', () => {
	test('Render with title "Metrics" and rerender with "Single Approver"', () => {
		const body = document.createElement('div');

		body.innerHTML = '<div id="workflow" data-testid="workflow"></div>';

		document.body.appendChild(body);

		document.title = 'Metrics';

		const containerWrapper = document.getElementById('workflow');

		const {getByTestId, rerender} = render(
			<HeaderTitle container={containerWrapper} title="Metrics" />
		);

		const container = getByTestId('workflow');

		expect(container.children[0]).toHaveTextContent('Metrics');
		expect(document.title).toBe('Metrics');

		rerender(
			<HeaderTitle container={containerWrapper} title="Single Approver" />
		);

		expect(container.children[0]).toHaveTextContent('Single Approver');
		expect(document.title).toBe('Single Approver');
	});
});
