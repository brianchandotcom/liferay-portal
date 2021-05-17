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
		details: [
			{
				key: 'missing-input-alt-attributes',
				title: 'Missing Input ALT Attributes',
				total: '4',
			},
			{
				key: 'missing-video-caption',
				title: 'Missing Video Caption',
				total: '5',
			},
		],
		key: 'accessibility',
		title: 'Accessibility',
		total: '17',
	},
	{
		details: [],
		key: 'seo',
		title: 'SEO',
		total: '123',
	},
];

const mockLayoutReportsIssuesNoSEO = [
	{
		details: [],
		key: 'accessibility',
		title: 'Accessibility',
		total: '17',
	},
	{
		details: [],
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
						imagesPath: 'imagesPath',
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
						imagesPath: 'imagesPath',
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

	it('renders no issues message (no seo and accessibility issues)', () => {
		const {getAllByText, getByText} = render(
			<StoreContextProvider
				value={{
					data: {
						imagesPath: 'imagesPath',
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
		expect(getByText('your-page-has-no-issues')).toBeInTheDocument();
	});

	it('render list of accesibility issues with correct number of failing elements', () => {
		const {getAllByText, getByText} = render(
			<StoreContextProvider
				value={{
					data: {
						imagesPath: 'imagesPath',
						layoutReportsIssues: mockLayoutReportsIssues,
					},
				}}
			>
				<LayoutReportsIssuesList />
			</StoreContextProvider>
		);

		expect(getByText('Missing Input ALT Attributes')).toBeInTheDocument();
		expect(getByText('Missing Video Caption')).toBeInTheDocument();
		expect(getAllByText('4').length).toBe(1);
		expect(getAllByText('5').length).toBe(1);
	});
});
