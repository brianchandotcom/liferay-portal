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

import '@testing-library/jest-dom/extend-expect';
import {act, render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {StoreAPIContextProvider} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import LayoutService from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/LayoutService';
import changeMasterLayout from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/changeMasterLayout';
import PageDesignOptionsSidebar from '../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/page-design-options/components/PageDesignOptionsSidebar';

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/LayoutService',
	() => ({
		changeStyleBookEntry: jest.fn(() => Promise.resolve({tokenValues: {}})),
	})
);

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/changeMasterLayout',
	() => jest.fn()
);

const DEFAULT_CONFIG = {
	layoutType: '0',
	masterLayouts: [
		{masterLayoutPlid: '0', name: 'Blank'},
		{
			masterLayoutPlid: '15',
			name: 'Pablo Master Layout',
		},
	],
	portletNamespace: 'ContentPageEditorPortlet',
	styleBookEnabled: true,
	styleBooks: [
		{
			name: 'Pablo Style',
			styleBookEntryId: '3',
		},
	],
};

const mockConfigGetter = jest.fn(() => DEFAULT_CONFIG);

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/index',
	() => ({
		get config() {
			return mockConfigGetter();
		},
	})
);

const renderComponent = ({masterLayoutPlid = '0'} = {}) => {
	return render(
		<StoreAPIContextProvider
			dispatch={() => Promise.resolve({styleBook: {}})}
			getState={() => ({
				masterLayout: {
					masterLayoutPlid,
				},
				permissions: {
					LOCKED_SEGMENTS_EXPERIMENT: true,
					UPDATE: false,
				},
			})}
		>
			<PageDesignOptionsSidebar />
		</StoreAPIContextProvider>
	);
};

describe('PageDesignOptionsSidebar', () => {
	it('has a sidebar panel title', () => {
		renderComponent();

		expect(screen.getByText('page-design-options')).toBeInTheDocument();
	});

	it('calls changeMasterLayout when a master layout is selected', async () => {
		renderComponent();
		const button = screen.getByLabelText('Pablo Master Layout');

		await act(async () => {
			userEvent.click(button);
		});

		expect(changeMasterLayout).toBeCalledWith(
			expect.objectContaining({masterLayoutPlid: '15'})
		);
	});

	it('calls changeStyleBookEntry when a style is selected', () => {
		renderComponent();
		const button = screen.getByLabelText('Pablo Style');

		userEvent.click(button);

		expect(LayoutService.changeStyleBookEntry).toHaveBeenCalledTimes(1);
		expect(LayoutService.changeStyleBookEntry).toHaveBeenCalledWith(
			expect.objectContaining({
				styleBookEntryId: '3',
			})
		);
	});

	it('renders Styles from Theme card when page does not have a master layout and there is not a default style book', () => {
		mockConfigGetter.mockReturnValue({
			...DEFAULT_CONFIG,
			defaultStyleBookEntryName: null,
		});

		renderComponent();

		expect(screen.getByLabelText('styles-from-theme')).toBeInTheDocument();
	});

	it('renders Styles from Master card when page have a master layout with a stylebook associated', () => {
		mockConfigGetter.mockReturnValue({
			...DEFAULT_CONFIG,
			defaultStyleBookEntryName: 'Master Page Style Book',
		});

		renderComponent({masterLayoutPlid: '15'});

		expect(screen.getByLabelText('styles-from-master')).toBeInTheDocument();
		expect(screen.getByText('Master Page Style Book')).toBeInTheDocument();
	});

	it('renders Styles by Default card when there is a default style book', () => {
		mockConfigGetter.mockReturnValue({
			...DEFAULT_CONFIG,
			defaultStyleBookEntryName: 'Master Page Style Book',
		});

		renderComponent();

		expect(screen.getByLabelText('styles-by-default')).toBeInTheDocument();
		expect(screen.getByText('Master Page Style Book')).toBeInTheDocument();
	});
});
