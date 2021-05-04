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
import {act, cleanup, render} from '@testing-library/react';
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

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/index',
	() => ({
		config: {
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
		},
	})
);

const renderComponent = ({
	hasUpdatePermissions = true,
	lockedExperience = false,
} = {}) => {
	return render(
		<StoreAPIContextProvider
			dispatch={() => Promise.resolve({styleBook: {}})}
			getState={() => ({
				masterLayout: {
					masterLayoutPlid: '0',
				},
				permissions: {
					LOCKED_SEGMENTS_EXPERIMENT: lockedExperience,
					UPDATE: hasUpdatePermissions,
				},
			})}
		>
			<PageDesignOptionsSidebar />
		</StoreAPIContextProvider>
	);
};

describe('PageDesignOptionsSidebar', () => {
	afterEach(cleanup);
	changeMasterLayout.mockClear();

	it('has a sidebar panel title', () => {
		const {getByText} = renderComponent();

		expect(getByText('page-design-options')).toBeInTheDocument();
	});

	it('calls changeMasterLayout when a master layout is selected', async () => {
		const {getByLabelText} = renderComponent();
		const button = getByLabelText('Pablo Master Layout');

		await act(async () => {
			userEvent.click(button);
		});

		expect(changeMasterLayout).toBeCalledWith(
			expect.objectContaining({masterLayoutPlid: '15'})
		);
	});

	it('calls changeStyleBookEntry when a style is selected', () => {
		const {getByLabelText} = renderComponent();
		const button = getByLabelText('Pablo Style');

		userEvent.click(button);

		expect(LayoutService.changeStyleBookEntry).toHaveBeenCalledTimes(1);
		expect(LayoutService.changeStyleBookEntry).toHaveBeenCalledWith(
			expect.objectContaining({
				styleBookEntryId: '3',
			})
		);
	});
});
