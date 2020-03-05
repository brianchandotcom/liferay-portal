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

import {cleanup, fireEvent, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';
import {DndProvider} from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';

import {StoreAPIContextProvider} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/store/index';
import FragmentsSidebar from '../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/fragments/components/FragmentsSidebar';

import '@testing-library/jest-dom/extend-expect';

const renderFragmentsSidebar = () =>
	render(
		<DndProvider backend={HTML5Backend}>
			<StoreAPIContextProvider
				getState={() => ({
					fragments: [
						{
							fragmentCollectionId: 'basicComponents',
							fragmentEntries: [
								{
									fragmentEntryKey: 'basicComponentButton',
									imagePreviewURL: 'buttonImageURL',
									name: 'Button',
								},
								{
									fragmentEntryKey: 'basicComponentCard',
									imagePreviewURL: 'cardImageURL',
									name: 'Card',
								},
								{
									fragmentEntryKey: 'basicComponentHeading',
									imagePreviewURL: 'headingImageURL',
									name: 'Heading',
								},
							],
							name: 'Basic Components',
						},
					],
				})}
			>
				<FragmentsSidebar />
			</StoreAPIContextProvider>
		</DndProvider>
	);

describe('FragmentsSidebar', () => {
	afterEach(cleanup);

	it('renders the categories collapsed', () => {
		const {
			getByLabelText,
			getByText,
			queryByText,
		} = renderFragmentsSidebar();

		expect(getByText('Basic Components')).toBeInTheDocument();
		expect(getByText('layout-elements')).toBeInTheDocument();
		expect(getByLabelText('search-form')).toBeInTheDocument();

		expect(queryByText('Button')).not.toBeInTheDocument();
	});

	it('expands a category on click and closes it when clicking it again', () => {
		const {getByText, queryByText} = renderFragmentsSidebar();
		const basicComponentsCollapse = getByText('Basic Components');

		fireEvent.click(basicComponentsCollapse);

		expect(basicComponentsCollapse.getAttribute('aria-expanded')).toBe(
			'true'
		);

		expect(getByText('Button'));

		fireEvent.click(basicComponentsCollapse);

		expect(basicComponentsCollapse.getAttribute('aria-expanded')).toBe(
			'false'
		);

		expect(queryByText('Button')).not.toBeInTheDocument();
	});

	it('finds an element when you search it', () => {
		const {
			getByLabelText,
			getByText,
			queryByText,
		} = renderFragmentsSidebar();
		const searchFormInput = getByLabelText('search-form');

		userEvent.type(searchFormInput, 'button');

		expect(getByText('Button')).toBeInTheDocument();

		expect(queryByText('Heading')).not.toBeInTheDocument();
	});

	it('expands all categories when you type something in search form and hides layout elements', () => {
		const {
			getByLabelText,
			getByText,
			queryByText,
		} = renderFragmentsSidebar();

		userEvent.type(getByLabelText('search-form'), 'a');

		expect(
			getByText('Basic Components').getAttribute('aria-expanded')
		).toBe('true');

		expect(queryByText('layout-elements')).not.toBeInTheDocument();
	});
});
