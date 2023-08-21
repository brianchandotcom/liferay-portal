/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import LayoutReports from '../../../../src/main/resources/META-INF/resources/js/components/layout_reports/LayoutReports';
import {StoreContextProvider} from '../../../../src/main/resources/META-INF/resources/js/context/StoreContext';
import {layoutReportsIssues, pageURLs, selectedItem} from '../../mocks';

jest.mock('frontend-js-web', () => ({
	...jest.requireActual('frontend-js-web'),
	sub: jest.fn((langKey, arg) => langKey.replace('x', arg)),
}));

const getLayoutReportsComponent = ({
	error = null,
	layoutReportsIssues = null,
	loading = false,
	privateLayout = false,
	selectedItem = null,
	validConnection = true,
} = {}) => {
	return (
		<StoreContextProvider
			value={{
				data: {
					defaultLanguageId: 'en-US',
					layoutReportsIssues,
					pageURLs,
					privateLayout,
					validConnection,
				},
				error,
				languageId: 'en-US',
				loading,
				selectedItem,
			}}
		>
			<LayoutReports eventTriggered={true} />
		</StoreContextProvider>
	);
};

const languageSelectorIsInTheDocument = ({fn, useNot = false}) => {
	['Home', 'en-US', 'http://localhost:8080'].forEach((str) => {
		// eslint-disable-next-line @liferay/expect-assert
		const expected = useNot ? expect(fn(str)).not : expect(fn(str));

		expected.toBeInTheDocument();
	});
};

describe('LayoutReports renders proper component', () => {
	afterEach(cleanup);

	it('Renders error component if and error code 500 is received', () => {
		const {getByText} = render(
			getLayoutReportsComponent({
				error: {
					code: 500,
				},
			})
		);

		expect(getByText('this-page-cannot-be-audited')).toBeInTheDocument();
		expect(getByText('show-details')).toBeInTheDocument();

		languageSelectorIsInTheDocument({fn: getByText});
	});

	it('Renders error component without the extended information if a private page is received', () => {
		const {getByText, queryByText} = render(
			getLayoutReportsComponent({
				privateLayout: true,
			})
		);

		expect(getByText('this-page-cannot-be-audited')).toBeInTheDocument();
		expect(queryByText('show-details')).not.toBeInTheDocument();

		languageSelectorIsInTheDocument({fn: getByText});
	});

	it('Renders not-configured component when a valid connection is not provided, without the page lang selector', () => {
		const {getByText, queryByText} = render(
			getLayoutReportsComponent({
				validConnection: false,
			})
		);

		expect(
			getByText(
				"check-issues-that-impact-on-your-page's-accessibility-and-seo"
			)
		).toBeInTheDocument();

		languageSelectorIsInTheDocument({fn: queryByText, useNot: true});
	});

	it('Renders issue detail if available', () => {
		const {getByText} = render(
			getLayoutReportsComponent({
				selectedItem,
			})
		);

		expect(getByText(selectedItem.tips)).toBeInTheDocument();

		languageSelectorIsInTheDocument({fn: getByText});
	});

	it('Renders issues list if available', () => {
		const {getByRole, getByText} = render(
			getLayoutReportsComponent({layoutReportsIssues})
		);

		const alert = getByRole('alert');

		expect(alert).toBeInTheDocument();
		expect(
			getByText('July 5, 2021 12:09 PM', {exact: false})
		).toBeInTheDocument();

		languageSelectorIsInTheDocument({fn: getByText});
	});

	it('renders language selector button disabled when issues are loading', () => {
		const {getByText} = render(getLayoutReportsComponent({loading: true}));

		const button = getByText('en-US').parentElement;

		expect(button.disabled).toBe(true);
	});
});
