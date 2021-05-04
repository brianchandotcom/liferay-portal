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

import {Column} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/layout-data-items';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {StoreAPIContextProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';

const renderColumn = ({
	hasUpdatePermissions = true,
	lockedExperience = false,
} = {}) => {
	const column = {
		children: [],
		config: {},
		itemId: 'column',
		parentId: null,
		type: LAYOUT_DATA_ITEM_TYPES.column,
	};

	return render(
		<StoreAPIContextProvider
			getState={() => ({
				permissions: {
					LOCKED_SEGMENTS_EXPERIMENT: lockedExperience,
					UPDATE: hasUpdatePermissions,
				},
			})}
		>
			<Column item={column} />
		</StoreAPIContextProvider>
	);
};

describe('Column', () => {
	afterEach(cleanup);

	it('removes column borders if current experience is locked', () => {
		const {container} = renderColumn({lockedExperience: true});

		expect(container.querySelector('.page-editor__col__border')).toBe(null);
	});

	it('removes column borders if user has no permissions', () => {
		const {container} = renderColumn({hasUpdatePermissions: false});

		expect(container.querySelector('.page-editor__col__border')).toBe(null);
	});
});
