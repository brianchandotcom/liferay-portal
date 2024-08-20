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

import {FormStep} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/layout_data_items/FormStep';
import {FormStepContainer} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/layout_data_items/FormStepContainer';
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

jest.mock('frontend-js-web', () => ({
	...jest.requireActual('frontend-js-web'),
	sub: jest.fn((langKey, arg) => langKey.replace('x', arg)),
}));

const LAYOUT_DATA = {
	items: {
		itemId: {
			children: [],
			config: {styles: {}},
			itemId: 'itemId',
			parentId: null,
			type: LAYOUT_DATA_ITEM_TYPES.row,
		},
	},
};

const renderTopper = ({
	Component = Row,
	activeItemIds = [],
	hasUpdatePermissions = true,
	isActive = true,
	itemId = 'itemId',
	layoutData = LAYOUT_DATA,
	lockedExperience = false,
} = {}) => {
	const item = layoutData.items[itemId];

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
						item={item}
						layoutData={layoutData}
					>
						<Component item={item} layoutData={layoutData} />
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
		const layoutData = {
			items: {
				itemId: {
					children: [],
					config: {name: 'customName'},
					itemId: 'itemId',
					parentId: null,
					type: LAYOUT_DATA_ITEM_TYPES.row,
				},
			},
		};

		const {baseElement} = renderTopper({layoutData});

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
		const layoutData = {
			items: {
				fragment: {
					children: [],
					config: {name: 'customName'},
					itemId: 'fragment',
					parentId: null,
					type: LAYOUT_DATA_ITEM_TYPES.fragment,
				},
			},
		};

		const params = {
			activeItemIds: ['item-1'],
			isActive: true,
			itemId: 'fragment',
			layoutData,
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

	describe('Form Step components', () => {
		it('renders step name correctly', () => {
			const layoutData = {
				items: {
					formStep1: {
						children: [],
						itemId: 'formStep1',
						parentId: 'formStepContainer',
						type: LAYOUT_DATA_ITEM_TYPES.formStep,
					},

					formStep2: {
						children: [],
						itemId: 'formStep2',
						parentId: 'formStepContainer',
						type: LAYOUT_DATA_ITEM_TYPES.formStep,
					},

					formStepContainer: {
						children: ['formStep1', 'formStep2'],
						itemId: 'formStepContainer',
						parentId: null,
						type: LAYOUT_DATA_ITEM_TYPES.formStepContainer,
					},
				},
			};

			renderTopper({
				Component: FormStep,
				itemId: 'formStep2',
				layoutData,
			});

			expect(screen.getByText('step-2')).toBeInTheDocument();
		});

		it('does not render actions in the form step container', () => {
			const layoutData = {
				items: {
					formStepContainer: {
						children: [],
						config: {},
						itemId: 'formStepContainer',
						parentId: null,
						type: LAYOUT_DATA_ITEM_TYPES.formStepContainer,
					},
				},
			};

			renderTopper({
				Component: FormStepContainer,
				itemId: 'formStepContainer',
				layoutData,
			});

			expect(screen.queryByText('options')).not.toBeInTheDocument();
		});
	});
});
