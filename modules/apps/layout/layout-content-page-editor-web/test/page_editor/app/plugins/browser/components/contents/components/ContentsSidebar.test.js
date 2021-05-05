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

import {StoreContextProvider} from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import ContentsSidebar from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/browser/components/contents/components/ContentsSidebar';

const pageContents = [
	{
		subtype: 'Basic Web Content',
		title: 'WC1',
		type: 'Web Content Article',
	},
	{
		subtype: 'Basic Web Content',
		title: 'WC2',
		type: 'Web Content Article',
	},
];

const renderPageContent = ({pageContents}) =>
	render(
		<StoreContextProvider
			initialState={{fragmentEntryLinks: {}, pageContents}}
		>
			<ContentsSidebar></ContentsSidebar>
		</StoreContextProvider>
	);

describe('ContentsSidebar', () => {
	afterEach(cleanup);

	it('shows the content list', () => {
		const {getByText} = renderPageContent({pageContents});

		expect(getByText('WC1')).toBeInTheDocument();
		expect(getByText('WC2')).toBeInTheDocument();
	});

	it('shows an alert when there is no content', () => {
		const {getByText} = renderPageContent({pageContents: []});

		expect(
			getByText('there-is-no-content-on-this-page')
		).toBeInTheDocument();
	});
});
