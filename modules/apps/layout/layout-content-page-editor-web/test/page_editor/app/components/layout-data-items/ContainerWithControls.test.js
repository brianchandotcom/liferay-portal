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
import {cleanup, queryByText, render} from '@testing-library/react';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {ContainerWithControls} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/layout-data-items';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {VIEWPORT_SIZES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import {
	ControlsProvider,
	useSelectItem,
} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext';
import {StoreAPIContextProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			frontendTokens: {},
		},
	})
);

const renderContainer = ({
	activeItemId = 'container',
	hasUpdatePermissions = true,
	lockedExperience = false,
} = {}) => {
	const container = {
		children: [],
		config: {styles: {}},
		itemId: 'container',
		parentId: null,
		type: LAYOUT_DATA_ITEM_TYPES.container,
	};

	const layoutData = {
		items: {container},
	};

	const AutoSelect = () => {
		useSelectItem()(activeItemId);

		return null;
	};

	return render(
		<DndProvider backend={HTML5Backend}>
			<ControlsProvider>
				<StoreAPIContextProvider
					getState={() => ({
						permissions: {
							LOCKED_SEGMENTS_EXPERIMENT: lockedExperience,
							UPDATE: hasUpdatePermissions,
						},
						selectedViewportSize: VIEWPORT_SIZES.desktop,
					})}
				>
					<AutoSelect />
					<ContainerWithControls
						item={container}
						layoutData={layoutData}
					/>
				</StoreAPIContextProvider>
			</ControlsProvider>
		</DndProvider>
	);
};

describe('ContainerWithControls', () => {
	afterEach(cleanup);

	it('does not add container class if user has no permissions', () => {
		const {baseElement} = renderContainer({hasUpdatePermissions: false});

		expect(baseElement.querySelector('.page-editor__container')).toBe(null);
	});

	it('does not allow deleting or duplicating the container if user has no permissions', () => {
		const {baseElement} = renderContainer({hasUpdatePermissions: false});

		expect(queryByText(baseElement, 'delete')).not.toBeInTheDocument();
		expect(queryByText(baseElement, 'duplicate')).not.toBeInTheDocument();
	});
});
