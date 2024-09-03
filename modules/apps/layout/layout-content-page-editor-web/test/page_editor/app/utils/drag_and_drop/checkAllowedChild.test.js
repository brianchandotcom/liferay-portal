/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FRAGMENT_ENTRY_TYPES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/fragmentEntryTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import checkAllowedChild from '../../../../../src/main/resources/META-INF/resources/page_editor/app/utils/drag_and_drop/checkAllowedChild';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			formTypes: [
				{
					isRestricted: false,
					label: 'Form Type',
					value: '11111',
				},
			],
		},
	})
);

const EMPTY_REF = {current: {}};

const IDS = {
	container: 'container',
	form: 'form-id',
	formStep: 'form-step-id',
	formStepContainer: 'form-step-container-id',
	fragment: 'fragment-id',
	grid: 'grid-id',
};

function getContainer() {
	return {
		children: [],
		config: {},
		itemId: IDS.container,
		type: LAYOUT_DATA_ITEM_TYPES.container,
	};
}

function getFragment(
	{
		fieldTypes = [],
		fragmentEntryLinkId,
		fragmentEntryType = 'component',
		isWidget = false,
		itemId,
		parentId,
	} = {
		fieldTypes: [],
		fragmentEntryType: 'component',
		isWidget: false,
	}
) {
	return {
		children: [],
		config: {
			fragmentEntryLinkId,
		},
		fieldTypes,
		fragmentEntryType,
		isWidget,
		itemId: itemId || IDS.fragment,
		parentId,
		type: LAYOUT_DATA_ITEM_TYPES.fragment,
	};
}

function getForm(
	{children = [], formType = 'simple'} = {children: [], formType: 'simple'}
) {
	const formChildren = formType === 'simple' ? [] : [IDS.formStepContainer];

	return {
		children: [...formChildren, ...children],
		config: {
			classNameId: '11111',
			classTypeId: '0',
			formType,
		},
		itemId: IDS.form,
		type: LAYOUT_DATA_ITEM_TYPES.form,
	};
}

function getFormStepContainer() {
	return {
		children: [IDS.formStep],
		config: {},
		itemId: IDS.formStepContainer,
		parentId: IDS.form,
		type: LAYOUT_DATA_ITEM_TYPES.formStepContainer,
	};
}

function getFormStep() {
	return {
		children: [],
		config: {},
		itemId: IDS.formStep,
		parentId: IDS.formStepContainer,
		type: LAYOUT_DATA_ITEM_TYPES.formStep,
	};
}

function getGrid() {
	return {
		children: [],
		config: {},
		itemId: IDS.grid,
		type: LAYOUT_DATA_ITEM_TYPES.row,
	};
}

describe('checkAllowedChild', () => {
	describe('Form Container', () => {
		it('it is not possible to add containers and grids to a form if it is multistep', () => {
			const container = getContainer();
			const grid = getGrid();
			const form = getForm({formType: 'multistep'});

			expect(
				checkAllowedChild(container, form, EMPTY_REF, EMPTY_REF)
			).toBe(false);

			expect(checkAllowedChild(grid, form, EMPTY_REF, EMPTY_REF)).toBe(
				false
			);
		});

		it('it is not possible to add standard fragments and inputs to a form if it is multistep', () => {
			const fragment = getFragment();
			const input = getFragment({fragmentEntryType: 'input'});
			const form = getForm({formType: 'multistep'});

			expect(
				checkAllowedChild(fragment, form, EMPTY_REF, EMPTY_REF)
			).toBe(false);

			expect(checkAllowedChild(input, form, EMPTY_REF, EMPTY_REF)).toBe(
				false
			);
		});

		it('it is possible to add standard fragments and inputs to a form step', () => {
			const fragment = getFragment();
			const input = getFragment({fragmentEntryType: 'input'});
			const formStep = getFormStep();

			const layoutDataRef = {
				current: {
					items: {
						[IDS.form]: getForm({formType: 'multistep'}),
						[IDS.formStepContainer]: getFormStepContainer(),
						[IDS.formStep]: formStep,
					},
				},
			};

			expect(
				checkAllowedChild(fragment, formStep, layoutDataRef, EMPTY_REF)
			).toBe(true);

			expect(
				checkAllowedChild(input, formStep, layoutDataRef, EMPTY_REF)
			).toBe(true);
		});

		it('it is possible to add standard fragments to a form if it is simple', () => {
			const fragment = getFragment();
			const form = getForm();

			expect(
				checkAllowedChild(fragment, form, EMPTY_REF, EMPTY_REF)
			).toBe(true);
		});

		it('it is not possible to add widgets to a form', () => {
			const widget = getFragment({isWidget: true});
			const form = getForm();

			expect(checkAllowedChild(widget, form, EMPTY_REF, EMPTY_REF)).toBe(
				false
			);
		});
	});

	describe('Input fragments', () => {
		it('it is not possible to add inputs outside a form', () => {
			const input = getFragment({
				fragmentEntryType: FRAGMENT_ENTRY_TYPES.input,
			});

			const container = getContainer();

			expect(
				checkAllowedChild(input, container, EMPTY_REF, EMPTY_REF)
			).toBe(false);
		});

		it('it is possible to add inputs inside a form', () => {
			const input = getFragment({
				fragmentEntryType: FRAGMENT_ENTRY_TYPES.input,
			});

			const form = getForm();

			expect(checkAllowedChild(input, form, EMPTY_REF, EMPTY_REF)).toBe(
				true
			);
		});
	});

	describe('Stepper fragment', () => {
		it('it is possible to add a stepper inside a form', () => {
			const stepper = getFragment({
				fieldTypes: ['stepper'],
				fragmentEntryType: FRAGMENT_ENTRY_TYPES.input,
			});

			const form = getForm();
			const multistepForm = getForm({formType: 'multistep'});

			const layoutDataRef = {
				current: {
					items: {
						[IDS.form]: form,
						[IDS.formStepContainer]: getFormStepContainer(),
						[IDS.formStep]: getFormStep(),
					},
				},
			};

			const layoutDataRefWithMultistep = {
				current: {
					items: {
						[IDS.form]: multistepForm,
						[IDS.formStepContainer]: getFormStepContainer(),
						[IDS.formStep]: getFormStep(),
					},
				},
			};

			expect(
				checkAllowedChild(stepper, form, layoutDataRef, EMPTY_REF)
			).toBe(true);

			expect(
				checkAllowedChild(
					stepper,
					multistepForm,
					layoutDataRefWithMultistep,
					EMPTY_REF
				)
			).toBe(true);
		});

		it('it is not possible to add a stepper inside a form step', () => {
			const stepper = getFragment({
				fieldTypes: ['stepper'],
				fragmentEntryType: FRAGMENT_ENTRY_TYPES.input,
			});

			const formStep = getFormStep();

			const layoutDataRef = {
				current: {
					items: {
						[IDS.form]: getForm({formType: 'multistep'}),
						[IDS.formStepContainer]: getFormStepContainer(),
						[IDS.formStep]: formStep,
					},
				},
			};

			expect(
				checkAllowedChild(stepper, formStep, layoutDataRef, EMPTY_REF)
			).toBe(false);
		});

		it('it is not possible to add a stepper outside a form', () => {
			const stepper = getFragment({
				fieldTypes: ['stepper'],
				fragmentEntryType: FRAGMENT_ENTRY_TYPES.input,
			});

			const container = getContainer();

			expect(
				checkAllowedChild(stepper, container, EMPTY_REF, EMPTY_REF)
			).toBe(false);
		});

		it('it is not possible to add a stepper inside a form that already has a stepper', () => {
			const stepper = getFragment({
				fieldTypes: ['stepper'],
				fragmentEntryType: FRAGMENT_ENTRY_TYPES.input,
			});

			const existingStepper = getFragment({
				fragmentEntryLinkId: 'stepper-fragment-id',
				itemId: 'existing-stepper-id',
				parentId: IDS.form,
			});

			const form = getForm({
				children: [existingStepper.itemId],
				formType: 'multistep',
			});

			const layoutDataRef = {
				current: {
					items: {
						[IDS.form]: form,
						[IDS.formStepContainer]: getFormStepContainer(),
						[IDS.formStep]: getFormStep(),
						[existingStepper.itemId]: existingStepper,
					},
				},
			};

			const fragmentEntryLinksRef = {
				current: {
					[existingStepper.config.fragmentEntryLinkId]: {
						fieldTypes: ['stepper'],
						fragmentEntryLinkId:
							existingStepper.config.fragmentEntryLinkId,
					},
				},
			};

			expect(
				checkAllowedChild(
					stepper,
					form,
					layoutDataRef,
					fragmentEntryLinksRef
				)
			).toBe(false);
		});
	});
});
