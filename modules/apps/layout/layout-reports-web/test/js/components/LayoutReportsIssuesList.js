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

import '@testing-library/jest-dom/extend-expect';

import LayoutReportsIssuesList from '../../../src/main/resources/META-INF/resources/js/components/LayoutReportsIssuesList';
import {StoreContextProvider} from '../../../src/main/resources/META-INF/resources/js/context/StoreContext';

const mockLayoutReportsIssues = [
	{
		key: 'accessibility',
		title: 'Accessibility',
		total: '17',
	},
	{
		key: 'seo',
		title: 'SEO',
		total: '123',
	},
];

const mockLayoutReportsIssuesNoSEO = [
	{
		key: 'accessibility',
		title: 'Accessibility',
		total: '17',
	},
	{
		key: 'seo',
		title: 'SEO',
		total: '0',
	},
];

const mockLayoutReportsIssuesNoAccessibilityNoSEO = [
	{
		key: 'accessibility',
		title: 'Accessibility',
		total: '0',
	},
	{
		key: 'seo',
		title: 'SEO',
		total: '0',
	},
];

describe('LayoutReportsIssuesList', () => {
	afterEach(cleanup);

	it('renders accessibility and seo sections with issues count', () => {
		const {getByText} = render(
			<StoreContextProvider
				value={{
					data: {
						assetsPath: 'assetsPath',
						layoutReportsIssues: mockLayoutReportsIssues,
					},
				}}
			>
				<LayoutReportsIssuesList />
			</StoreContextProvider>
		);

		expect(getByText('Accessibility')).toBeInTheDocument();
		expect(getByText('17')).toBeInTheDocument();
		expect(getByText('SEO')).toBeInTheDocument();
		expect(getByText('+100')).toBeInTheDocument();
	});

	it('renders no seo or accesibility issues message', () => {
		const {getByText} = render(
			<StoreContextProvider
				value={{
					data: {
						assetsPath: 'assetsPath',
						layoutReportsIssues: mockLayoutReportsIssuesNoSEO,
					},
				}}
			>
				<LayoutReportsIssuesList />
			</StoreContextProvider>
		);

		expect(getByText('SEO')).toBeInTheDocument();
		expect(getByText('0')).toBeInTheDocument();
		expect(
			getByText('there-are-no-SEO-related-issues')
		).toBeInTheDocument();
	});

	it('renders congratulations messages (no seo and accessibility issues)', () => {
		const {getAllByText, getByText} = render(
			<StoreContextProvider
				value={{
					data: {
						assetsPath: 'assetsPath',
						layoutReportsIssues: mockLayoutReportsIssuesNoAccessibilityNoSEO,
					},
				}}
			>
				<LayoutReportsIssuesList />
			</StoreContextProvider>
		);

		expect(getByText('Accessibility')).toBeInTheDocument();
		expect(getByText('SEO')).toBeInTheDocument();
		expect(getAllByText('0').length).toBe(2);
		expect(getByText('congratulations')).toBeInTheDocument();
		expect(
			getByText('your-page-does-not-have-any-issues-to-fix')
		).toBeInTheDocument();
	});
});
