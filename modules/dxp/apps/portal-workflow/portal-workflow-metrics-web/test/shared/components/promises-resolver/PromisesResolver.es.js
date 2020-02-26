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

import {cleanup, render, waitForElement} from '@testing-library/react';
import React from 'react';

import PromisesResolver, {
	PromisesResolverContext,
} from '../../../../src/main/resources/META-INF/resources/js/shared/components/promises-resolver/PromisesResolver.es';

describe('The pending view should', () => {
	afterEach(cleanup);

	test('Not render children when pending is false', () => {
		const {getByTestId} = render(
			<PromisesResolverContext.Provider
				value={{error: null, loading: false}}
			>
				<PromisesResolver.Pending>
					<span data-testid="loadingSpan">Loading</span>
				</PromisesResolver.Pending>
			</PromisesResolverContext.Provider>
		);

		expect(() => getByTestId('loadingSpan')).toThrow();
	});

	test('Render children when loading is true', () => {
		const {getByTestId} = render(
			<PromisesResolverContext.Provider
				value={{error: null, loading: true}}
			>
				<PromisesResolver.Pending>
					<span data-testid="loadingSpan">Loading</span>
				</PromisesResolver.Pending>
			</PromisesResolverContext.Provider>
		);

		const loadingSpan = getByTestId('loadingSpan');

		expect(loadingSpan.innerHTML).toEqual('Loading');
	});
});

describe('The PromisesResolver should', () => {
	afterEach(cleanup);

	test('Render its children when the request succeeds', async () => {
		const firstFunction = () => Promise.resolve();
		const secondFunction = () => Promise.resolve();

		const {findByTestId} = render(
			<PromisesResolver promises={[firstFunction(), secondFunction()]}>
				<span data-testid="promisesResolverSpan">
					PromisesResolver rendered children
				</span>
			</PromisesResolver>
		);

		const promisesResolverSpan = await waitForElement(() =>
			findByTestId('promisesResolverSpan')
		);

		expect(promisesResolverSpan.innerHTML).toEqual(
			'PromisesResolver rendered children'
		);
	});
});

describe('The PromisesResolver should not', () => {
	afterEach(cleanup);

	test('Render its children when request fails', async () => {
		const firstFunction = () => Promise.reject();
		const secondFunction = () => Promise.reject();

		const {findByTestId, rerender} = render(
			<PromisesResolver promises={[firstFunction(), secondFunction()]}>
				<span data-testid="promisesResolverSpan">
					PromisesResolver rendered children
				</span>
			</PromisesResolver>
		);

		const promisesResolverSpan = await waitForElement(() =>
			findByTestId('promisesResolverSpan')
		);

		expect(promisesResolverSpan.innerHTML).toEqual(
			'PromisesResolver rendered children'
		);

		rerender(
			<PromisesResolver promises={[...[firstFunction()]]}>
				<span data-testid="promisesResolverSpan">
					PromisesResolver rendered children
				</span>
			</PromisesResolver>
		);

		expect(promisesResolverSpan.innerHTML).toEqual(
			'PromisesResolver rendered children'
		);
	});
});

describe('The rejected view should', () => {
	afterEach(cleanup);

	test('Not render children when error is null', () => {
		const {getByTestId} = render(
			<PromisesResolverContext.Provider
				value={{error: null, loading: true}}
			>
				<PromisesResolver.Rejected>
					<span data-testid="rejectedSpan">Rejected</span>
				</PromisesResolver.Rejected>
			</PromisesResolverContext.Provider>
		);

		expect(() => getByTestId('rejectedSpan')).toThrow();
	});

	test('Render children when has been rejected', () => {
		const {getByTestId} = render(
			<PromisesResolverContext.Provider
				value={{
					error: new Error('Rejected instance mock'),
					loading: false,
				}}
			>
				<PromisesResolver.Rejected>
					<span data-testid="rejectedSpan">Rejected</span>
				</PromisesResolver.Rejected>
			</PromisesResolverContext.Provider>
		);

		const rejectedSpan = getByTestId('rejectedSpan');

		expect(rejectedSpan.innerHTML).toEqual('Rejected');
	});
});

describe('The resolved view should', () => {
	afterEach(cleanup);

	test('Not render children when has been rejected', () => {
		const {getByTestId} = render(
			<PromisesResolverContext.Provider
				value={{
					error: new Error('Rejected instance mock'),
					loading: false,
				}}
			>
				<PromisesResolver.Resolved>
					<span data-testid="resolvedSpan">Resolved</span>
				</PromisesResolver.Resolved>
			</PromisesResolverContext.Provider>
		);

		expect(() => getByTestId('resolvedSpan')).toThrow();
	});

	test('Not render children when loading is true', () => {
		const {getByTestId} = render(
			<PromisesResolverContext.Provider
				value={{error: null, loading: true}}
			>
				<PromisesResolver.Resolved>
					<span data-testid="resolvedSpan">Resolved</span>
				</PromisesResolver.Resolved>
			</PromisesResolverContext.Provider>
		);

		expect(() => getByTestId('resolvedSpan')).toThrow();
	});

	test('Render children when loading is false and has no error', () => {
		const {getByTestId} = render(
			<PromisesResolverContext.Provider
				value={{error: null, loading: false}}
			>
				<PromisesResolver.Resolved>
					<span data-testid="resolvedSpan">Resolved</span>
				</PromisesResolver.Resolved>
			</PromisesResolverContext.Provider>
		);

		const loadingSpan = getByTestId('resolvedSpan');

		expect(loadingSpan.innerHTML).toEqual('Resolved');
	});
});
