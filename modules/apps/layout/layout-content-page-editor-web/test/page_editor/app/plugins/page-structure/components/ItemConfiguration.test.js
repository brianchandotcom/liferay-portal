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
import {cleanup, render} from '@testing-library/react';
import React from 'react';

import {StoreAPIContextProvider} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import ItemConfiguration from '../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/browser/components/page-structure/components/ItemConfiguration';

const renderComponent = () =>
	render(
		<StoreAPIContextProvider>
			<ItemConfiguration />
		</StoreAPIContextProvider>
	);

describe('ItemConfiguration', () => {
	afterEach(cleanup);

	it('renders a warning message if no item is selected', () => {
		const {getByText} = renderComponent();

		expect(
			getByText('select-an-element-of-the-page-to-show-this-panel')
		).toBeInTheDocument();
	});
});
