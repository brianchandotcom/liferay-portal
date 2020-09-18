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
import {cleanup, render} from '@testing-library/react';
import React from 'react';

import ContentView from '../../../../src/main/resources/META-INF/resources/js/shared/components/content-view/ContentView.es';
import PromisesResolver from '../../../../src/main/resources/META-INF/resources/js/shared/components/promises-resolver/PromisesResolver.es';

describe('The ContentView component should', () => {
	afterEach(cleanup);

	test('Be rendered with children', () => {
		const {getByText} = render(
			<ContentView>
				<div>Lorem Ipsum</div>
			</ContentView>
		);

		expect(getByText('Lorem Ipsum')).toBeTruthy();
	});

	test('Be rendered with empty state and the expected message', () => {
		const {getByText} = render(
			<ContentView emptyProps={{message: 'No results were found.'}} />
		);

		expect(getByText('No results were found.')).toBeTruthy();
	});

	test('Be rendered with loading state', async () => {
		const {container} = render(
			<PromisesResolver promises={[new Promise(() => {})]}>
				<ContentView />
			</PromisesResolver>
		);

		expect(
			container.querySelector('span.loading-animation')
		).not.toBeNull();
	});

	describe('Be rendered with error state', () => {
		let getByText;

		beforeAll(() => {
			const renderResult = render(
				<PromisesResolver
					promises={[
						new Promise((_, reject) => {
							reject(new Error('error'));
						}),
					]}
				>
					<ContentView
						errorProps={{message: 'Unable to retrieve data'}}
					/>
				</PromisesResolver>
			);

			getByText = renderResult.getByText;
		});

		test('Be rendered with expected message', () => {
			expect(getByText('Unable to retrieve data')).toBeTruthy();
		});
	});
});
