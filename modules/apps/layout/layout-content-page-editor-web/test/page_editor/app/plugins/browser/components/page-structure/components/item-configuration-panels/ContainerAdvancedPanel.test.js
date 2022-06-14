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
import {fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {StoreAPIContextProvider} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import updateItemConfig from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig';
import ContainerAdvancedPanel from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/browser/components/page-structure/components/item-configuration-panels/ContainerAdvancedPanel';

jest.mock(
	'../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig',
	() => jest.fn()
);

function renderComponent(
	itemConfig = {
		tablet: {styles: {}},
	}
) {
	return render(
		<StoreAPIContextProvider
			dispatch={() => {}}
			getState={() => ({
				languageId: 'en_US',
				layoutData: {
					items: [],
				},
				permissions: {UPDATE: true},
				segmentsExperienceId: '0',
				selectedViewportSize: 'desktop',
			})}
		>
			<ContainerAdvancedPanel
				item={{
					children: [],
					config: itemConfig,
					itemId: 'container-id',
					parentId: 'parent-id',
					type: LAYOUT_DATA_ITEM_TYPES.container,
				}}
			/>
		</StoreAPIContextProvider>,
		{
			baseElement: document.body,
		}
	);
}

describe('ContainerAdvancedPanel', () => {
	it('renders stored html tag', () => {
		renderComponent({htmlTag: 'main'});

		expect(screen.getByLabelText('html-tag')).toHaveValue('main');
	});

	it('calls dispatch method with selected html tag', async () => {
		renderComponent();

		const htmlTagSelect = screen.getByLabelText('html-tag');

		fireEvent.change(htmlTagSelect, {
			target: {value: 'section'},
		});

		expect(updateItemConfig).toBeCalledWith(
			expect.objectContaining({
				itemConfig: {
					htmlTag: 'section',
				},
			})
		);
	});
});
