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

import HeaderBackButton from '../../../../src/main/resources/META-INF/resources/js/shared/components/header/HeaderBackButton.es';
import {MockRouter} from '../../../mock/MockRouter.es';

import '@testing-library/jest-dom/extend-expect';

describe('The HeaderBackButton component should', () => {
	let containerWrapper;

	afterEach(cleanup);

	beforeEach(() => {
		const body = document.createElement('div');

		document.body.innerHTML = '';

		body.innerHTML =
			'<div id="workflow" data-testid="workflow"><button>Close Sidebar</button></div>';

		document.body.appendChild(body);

		containerWrapper = document.getElementById('workflow');
	});

	test('Not render when pathname is equal to basePath', () => {
		const {getByTestId} = render(
			<MockRouter initialPath="/">
				<HeaderBackButton basePath="/" container={containerWrapper} />
			</MockRouter>
		);

		const container = getByTestId('workflow');

		expect(container.children.length).toEqual(1);
		expect(container.children[0]).toHaveTextContent('Close Sidebar');
	});

	test('Render when pathname is different to basePath', () => {
		const {getByTestId} = render(
			<MockRouter>
				<HeaderBackButton basePath="/" container={containerWrapper} />
			</MockRouter>
		);

		const {children} = getByTestId('workflow');

		const link = children[1].children[0].children[0];
		const {classList} = link.children[0].children[0];
		const href = link.getAttribute('href');

		expect(children.length).toEqual(2);
		expect(children[0]).toHaveTextContent('Close Sidebar');
		expect(classList.contains('lexicon-icon-angle-left')).toBe(true);
		expect(href).toBe('/');
	});
});
