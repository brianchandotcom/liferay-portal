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
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import {StoreContextProvider} from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import PageContent from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/browser/components/contents/components/PageContent';

const renderPageContent = (props) =>
	render(
		<StoreContextProvider initialState={[{}, {}]}>
			<PageContent
				actions={{
					editURL: 'editURL',
					permissionsURL: 'permissionsURL',
					viewUsagesURL: 'viewUsagesURL',
				}}
				subtype="Web Content Article"
				title="Test Web Content"
				{...props}
			></PageContent>
		</StoreContextProvider>
	);

describe('PageContent', () => {
	afterEach(cleanup);

	it('shows properly the title of the content', () => {
		const {getByText} = renderPageContent();

		expect(getByText('Test Web Content')).toBeInTheDocument();
	});

	it('shows properly the content subtype', () => {
		const {getByText} = renderPageContent();

		expect(getByText('Web Content Article')).toBeInTheDocument();
	});

	it('shows Edit action in dropdown menu if receives an Edit URL', () => {
		const {getByText} = renderPageContent();

		fireEvent.click(getByText('open-actions-menu'));

		expect(getByText('edit')).toBeInTheDocument();
	});

	it('shows Permissions action in dropdown menu if receives a Permissions URL', () => {
		const {getByText} = renderPageContent();

		fireEvent.click(getByText('open-actions-menu'));

		expect(getByText('permissions')).toBeInTheDocument();
	});

	it('shows View Usages action in dropdown menu if receives a View Usages URL', () => {
		const {getByText} = renderPageContent();

		fireEvent.click(getByText('open-actions-menu'));

		expect(getByText('view-usages')).toBeInTheDocument();
	});

	it('shows Edit action if the content is inline text', () => {
		const {getByText} = renderPageContent({actions: {}});

		expect(getByText('edit-inline-text')).toBeInTheDocument();
	});
});
