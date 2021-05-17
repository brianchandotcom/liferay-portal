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

import {act, cleanup, fireEvent, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import {StoreContextProvider} from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import PageContents from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/browser/components/contents/components/PageContents';

const contents = {
	Collection: [
		{
			classPK: '39694',
			subtype: 'Web Content Article - Basic Web Content',
			title: 'Collection1',
			type: 'Collection',
		},
	],
	Document: [
		{
			actions: {},
			classPK: '39615',
			subtype: 'Basic Document',
			title: 'mountain.png',
			type: 'Document',
		},
	],
	['Inline text']: [
		{
			config: {},
			defaultValue: 'Heading Example',
			editableId: '39835-element-text',
			en_US: 'This is a inline text',
			title: 'This is a inline text',
		},
	],
	['Web Content Article']: [
		{
			actions: {},
			classPK: '39807',
			subtype: 'Basic Web Content',
			title: 'WC1',
			type: 'Web Content Article',
		},
		{
			actions: {},
			classPK: '39808',
			subtype: 'Basic Web Content',
			title: 'WC2',
			type: 'Web Content Article',
		},
	],
};

const renderPageContents = ({pageContents = contents}) =>
	render(
		<StoreContextProvider initialState={[{}, {}]}>
			<PageContents pageContents={pageContents} />
		</StoreContextProvider>
	);

describe('PageContent', () => {
	afterEach(() => {
		cleanup();
		jest.useFakeTimers();
	});

	it('shows properly the list of page contents', () => {
		const {getAllByText, getByText} = renderPageContents({});

		Object.entries(contents).forEach(([key, values]) => {
			values.forEach((value) => {
				expect(getByText(value.title)).toBeInTheDocument();
			});
			expect(getAllByText(key)[0]).toBeInTheDocument();
		});
	});

	it('filters content according to a input value', () => {
		const {getByLabelText, queryByText} = renderPageContents({});
		const input = getByLabelText('search-form');

		act(() => {
			fireEvent.change(input, {
				target: {value: 'WC'},
			});

			jest.runAllTimers();
		});

		expect(queryByText('WC1')).toBeInTheDocument();
		expect(queryByText('WC2')).toBeInTheDocument();
		expect(queryByText('This is a inline text')).not.toBeInTheDocument();
		expect(queryByText('mountain.png')).not.toBeInTheDocument();
	});

	it('filters content according to a type value', async () => {
		const {getByRole, queryByText} = renderPageContents({});
		const dropdown = getByRole('listbox');

		userEvent.click(dropdown);

		const dropdownItems = document.querySelectorAll('.dropdown-item');

		userEvent.click(dropdownItems[2]);

		expect(queryByText('mountain.png')).toBeInTheDocument();
		expect(queryByText('Collection1')).not.toBeInTheDocument();
		expect(queryByText('This is a inline text')).not.toBeInTheDocument();
		expect(queryByText('WC1')).not.toBeInTheDocument();
	});
});
