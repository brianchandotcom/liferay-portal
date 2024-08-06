/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import Row from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/layout_data_items/Row';
import Topper from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/topper/Topper';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {VIEWPORT_SIZES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import {
	ControlsProvider,
	useSelectItem,
} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext';
import {StoreAPIContextProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext',
	() => {
		const selectItem = jest.fn();

		return {
			...jest.requireActual(
				'../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext'
			),
			useSelectItem: () => selectItem,
		};
	}
);

const renderTopper = ({
	hasUpdatePermissions = true,
	lockedExperience = false,
	rowConfig = {styles: {}},
	activeItemIds = [],
	isActive = true,
	type = LAYOUT_DATA_ITEM_TYPES.row,
} = {}) => {
	const row = {
		children: [],
		config: rowConfig,
		itemId: 'row',
		parentId: null,
		type,
	};

	const layoutData = {
		items: {row},
	};

	return render(
		<DndProvider backend={HTML5Backend}>
			<ControlsProvider activeInitialState={{activeItemIds}}>
				<StoreAPIContextProvider
					getState={() => ({
						fragmentEntryLinks: {},
						layoutData,
						permissions: {
							LOCKED_SEGMENTS_EXPERIMENT: lockedExperience,
							UPDATE: hasUpdatePermissions,
						},
						selectedViewportSize: VIEWPORT_SIZES.desktop,
					})}
				>
					<Topper
						isActive={isActive}
						item={row}
						layoutData={layoutData}
					>
						<Row item={row} layoutData={layoutData}></Row>
					</Topper>
				</StoreAPIContextProvider>
			</ControlsProvider>
		</DndProvider>
	);
};

describe('Topper', () => {
	it('does not render Topper if user has no permissions', () => {
		const {baseElement} = renderTopper({hasUpdatePermissions: false});

		expect(baseElement.querySelector('.page-editor__topper')).toBe(null);
	});

	it('renders Topper if user has permissions', () => {
		const {baseElement} = renderTopper();

		expect(
			baseElement.querySelector('.page-editor__topper')
		).toBeInTheDocument();
	});

	it('renders name of the fragment', () => {
		const {baseElement} = renderTopper();

		expect(
			baseElement.querySelector('[data-name="grid"]')
		).toBeInTheDocument();
	});

	it('renders custom name of the fragment', () => {
		const {baseElement} = renderTopper({rowConfig: {name: 'customName'}});

		expect(
			baseElement.querySelector('[data-name="customName"]')
		).toBeInTheDocument();
	});

	it('disables options when multiple items are selected', () => {
		Liferay.FeatureFlags['LPD-18221'] = true;

		renderTopper({activeItemIds: ['item-1', 'item-2'], isActive: true});

		expect(screen.getByLabelText('options')).toBeDisabled();

		Liferay.FeatureFlags['LPD-18221'] = false;
	});

	describe('Ensures that selectItem() is not called when the topper buttons are clicked', () => {
		const params = {
			activeItemIds: ['item-1'],
			isActive: true,
			type: 'fragment',
		};

		it('clicks on options dropdown', () => {
			renderTopper(params);

			const selectItem = useSelectItem();

			userEvent.click(screen.getByLabelText('options'));

			expect(selectItem).not.toBeCalled();
		});

		it('clicks in an options action', () => {
			renderTopper(params);

			const selectItem = useSelectItem();

			userEvent.click(screen.getByText('duplicate'));

			expect(selectItem).not.toBeCalled();
		});

		it('clicks on comments button', () => {
			renderTopper(params);

			const selectItem = useSelectItem();

			userEvent.click(screen.getByLabelText('comments'));

			expect(selectItem).not.toBeCalled();
		});
	});
});
