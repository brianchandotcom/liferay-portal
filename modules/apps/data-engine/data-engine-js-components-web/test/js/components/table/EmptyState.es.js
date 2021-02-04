/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import EmptyState, {
	withEmpty,
} from '../../../../src/main/resources/META-INF/resources/js/components/table/EmptyState.es';

describe('EmptyState', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {queryByText} = render(
			<EmptyState description="description" title="title" />
		);

		expect(queryByText('title')).toBeTruthy();
		expect(queryByText('description')).toBeTruthy();
	});

	describe('withEmpty', () => {
		afterEach(cleanup);

		const Component = () => <div>component</div>;

		it('renders component', () => {
			const {queryByText} = render(withEmpty(Component)({}));

			expect(queryByText('component')).toBeTruthy();
		});

		it('renders default empty', () => {
			const {queryByText} = render(
				withEmpty(Component)({
					isEmpty: true,
					keywords: '',
				})
			);

			expect(queryByText('there-are-no-entries')).toBeTruthy();
		});

		it('renders empty search', () => {
			const {queryByText} = render(
				withEmpty(Component)({
					emptyState: {},
					isEmpty: true,
					keywords: 'text',
				})
			);

			expect(queryByText('no-results-were-found')).toBeTruthy();
			expect(queryByText('there-are-no-results-for-x')).toBeTruthy();
		});

		it('renders empty filtered', () => {
			const {queryByText} = render(
				withEmpty(Component)({
					emptyState: {},
					isEmpty: true,
					isFiltered: true,
					keywords: '',
				})
			);

			expect(queryByText('no-results-were-found')).toBeTruthy();
			expect(
				queryByText('there-are-no-results-with-these-attributes')
			).toBeTruthy();
		});

		it('renders empty search and filtered', () => {
			const {queryByText} = render(
				withEmpty(Component)({
					emptyState: {},
					isEmpty: true,
					isFiltered: true,
					keywords: 'text',
				})
			);

			expect(queryByText('no-results-were-found')).toBeTruthy();
			expect(
				queryByText('there-are-no-results-for-x-with-these-attributes')
			).toBeTruthy();
		});
	});
});
