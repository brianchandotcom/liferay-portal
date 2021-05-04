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
import {cleanup, render, waitForElement} from '@testing-library/react';
import React from 'react';

import Container from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/layout-data-items/Container';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {VIEWPORT_SIZES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import {StoreAPIContextProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			frontendTokens: {},
		},
	})
);

const renderContainer = (config) => {
	return render(
		<StoreAPIContextProvider
			getState={() => ({
				languageId: 'en',
				selectedViewportSize: VIEWPORT_SIZES.desktop,
			})}
		>
			<Container
				data={{}}
				item={{
					children: [],
					config: {...config, styles: {}},
					itemId: 'containerId',
					type: LAYOUT_DATA_ITEM_TYPES.container,
				}}
				withinTopper={false}
			>
				Container
			</Container>
		</StoreAPIContextProvider>
	);
};

describe('Container', () => {
	afterEach(cleanup);

	it('wraps the container inside a link if configuration is specified', async () => {
		const {getByRole} = renderContainer({
			link: {
				href: 'https://sandro.vero.victor.com',
				target: '_blank',
			},
		});

		const link = await waitForElement(() => getByRole('link'));

		expect(link.href).toBe('https://sandro.vero.victor.com/');
		expect(link.target).toBe('_blank');
		expect(link.textContent).toBe('Container');
	});
});
