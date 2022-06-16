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
import {render, screen} from '@testing-library/react';
import React from 'react';

import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {VIEWPORT_SIZES} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import {StoreAPIContextProvider} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import CSSFieldSet from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/browser/components/page-structure/components/item-configuration-panels/CSSFieldSet';

const ITEM = {
	config: {
		cssClasses: ['otherClass1', 'otherClass2'],
	},
	itemId: 'itemId',
	type: LAYOUT_DATA_ITEM_TYPES.container,
};

const renderCSSFieldSet = ({permissions}) => {
	return render(
		<StoreAPIContextProvider
			dispatch={() => {}}
			getState={() => ({
				languageId: 'en_US',
				layoutData: {
					items: {
						itemId: {
							...ITEM,
						},
					},
				},
				permissions,
				selectedViewportSize: VIEWPORT_SIZES.desktop,
			})}
		>
			<CSSFieldSet item={ITEM} />
		</StoreAPIContextProvider>
	);
};

describe('CSSFieldSet', () => {
	beforeAll(() => {
		Liferay.FeatureFlags['LPS-147511'] = true;
	});

	afterAll(() => {
		Liferay.FeatureFlags['LPS-147511'] = false;
	});

	it('shows css collapse with UPDATE permission', () => {
		renderCSSFieldSet({permissions: {UPDATE: true}});

		expect(screen.getByText('css')).toBeInTheDocument();
	});

	it('does not show css collapse with UPDATE permission', () => {
		renderCSSFieldSet({permissions: {UPDATE_LAYOUT_BASIC: true}});

		expect(screen.queryByText('css')).not.toBeInTheDocument();
	});

	it('does not show css collapse with UPDATE permission', () => {
		renderCSSFieldSet({permissions: {UPDATE_LAYOUT_LIMITED: true}});

		expect(screen.queryByText('css')).not.toBeInTheDocument();
	});
});
