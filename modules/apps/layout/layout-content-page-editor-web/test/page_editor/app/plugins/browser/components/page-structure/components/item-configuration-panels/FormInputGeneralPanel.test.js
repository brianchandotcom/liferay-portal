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

import React from 'react';

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';

import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/freemarkerFragmentEntryProcessor';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {VIEWPORT_SIZES} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import {StoreAPIContextProvider} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import {FormInputGeneralPanel} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/browser/components/page-structure/components/item-configuration-panels/FormInputGeneralPanel';

const FRAGMENT_ENTRY_LINK_ID = '1';

const FORM_ITEM = {
	children: [],
	config: {
		classNameId: 'classNameId',
		classTypeId: 'classTypeId',
	},
	itemId: 'form-item',
	parentId: '',
	type: LAYOUT_DATA_ITEM_TYPES.form,
};

const INPUT_ITEM = {
	children: [],
	config: {
		fragmentEntryLinkId: FRAGMENT_ENTRY_LINK_ID,
	},
	itemId: 'input-item',
	parentId: 'form-item',
	type: LAYOUT_DATA_ITEM_TYPES.fragment,
};

const MOCK_CACHE = {
	'allowedInputTypes-dateFragment': ['date'],
	'allowedInputTypes-textFragment': ['text'],
	'formFields-classNameId-classTypeId': [
		{
			fields: [
				{
					key: 'requiredField',
					label: 'Required Field',
					name: 'requiredField',
					required: true,
					type: 'text',
				},
				{
					key: 'notRequiredField',
					label: 'Not Required Field',
					name: 'notRequiredField',
					required: false,
					type: 'text',
				},
			],
			label: 'Fieldset',
		},
	],
};

jest.mock(
	'../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/utils/useCache',
	() => jest.fn(({key}) => MOCK_CACHE[key.join('-')])
);

const renderComponent = ({
	fragmentEntryKey = 'textFragment',
	mappedFieldId,
} = {}) => {
	const mockDispatch = jest.fn((a) => {
		if (typeof a === 'function') {
			return a(mockDispatch, () => state);
		}
	});

	const state = {
		fragmentEntryLinks: {
			[FRAGMENT_ENTRY_LINK_ID]: {
				comments: [],
				configuration: {},
				editableValues: {
					[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]: {
						inputFieldId: mappedFieldId,
					},
				},
				fragmentEntryKey,
				fragmentEntryLinkId: FRAGMENT_ENTRY_LINK_ID,
				name: 'Fragment',
			},
		},
		languageId: 'en_US',
		layoutData: {
			items: {
				[FORM_ITEM.itemId]: FORM_ITEM,
				[INPUT_ITEM.itemId]: INPUT_ITEM,
			},
		},
		permissions: {UPDATE: true},
		selectedViewportSize: VIEWPORT_SIZES.desktop,
	};

	return render(
		<StoreAPIContextProvider dispatch={mockDispatch} getState={() => state}>
			<FormInputGeneralPanel item={INPUT_ITEM} />
		</StoreAPIContextProvider>
	);
};

describe('FormInputGeneralPanel', () => {
	it('shows an alert instead of field selector when there are no suitable fields for fragment allowed types', () => {
		renderComponent({fragmentEntryKey: 'dateFragment'});

		expect(
			screen.getByText(
				'there-are-no-suitable-fields-in-the-item-to-be-mapped-to-the-fragment'
			)
		).toBeInTheDocument();

		expect(screen.queryByLabelText('field')).not.toBeInTheDocument();
	});

	it('shows field selector when there are available fields', () => {
		renderComponent();

		expect(screen.getByLabelText('field')).toBeInTheDocument();
	});

	it('does not show configuration fieldset when fragment is not mapped', () => {
		renderComponent();

		expect(
			screen.queryByText('input-fragment-configuration')
		).not.toBeInTheDocument();
	});

	it('shows configuration fieldset when fragment is mapped', () => {
		renderComponent({mappedFieldId: 'requiredField'});

		expect(
			screen.getByText('input-fragment-configuration')
		).toBeInTheDocument();
	});

	it('shows checkbox to set field as required disabled when fragment is mapped to a required field', () => {
		renderComponent({mappedFieldId: 'requiredField'});

		expect(screen.getByLabelText('mark-as-required')).toBeDisabled();
	});

	it('shows checkbox to set field as required enabled when fragment is mapped to a not required field', () => {
		renderComponent({mappedFieldId: 'notRequiredField'});

		expect(screen.getByLabelText('mark-as-required')).not.toBeDisabled();
	});
});
