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

import userEvent from '@testing-library/user-event';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';
import {act, fireEvent, render, screen} from '@testing-library/react';

import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {VIEWPORT_SIZES} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import {config} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/index';
import {StoreAPIContextProvider} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import updateFormItemConfig from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateFormItemConfig';
import {FormGeneralPanel} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/browser/components/page-structure/components/item-configuration-panels/FormGeneralPanel';

jest.mock(
	'../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateFormItemConfig',
	() => jest.fn()
);

jest.mock(
	'../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			availableLanguages: {
				en_US: {
					default: false,
					displayName: 'English (United States)',
					languageIcon: 'en-us',
					languageId: 'en_US',
					w3cLanguageId: 'en-US',
				},
			},
			commonStyles: [],
			formTypes: [
				{
					label: 'None',
					subtypes: [],
					value: '0',
				},
				{
					label: 'Type',
					subtypes: [],
					value: 'classNameId',
				},
			],
		},
	})
);

const MAPPED_FORM_ITEM = {
	children: [],
	config: {
		classNameId: 'classNameId',
		classTypeId: '0',
	},
	itemId: 'form-item',
	parentId: '',
	type: LAYOUT_DATA_ITEM_TYPES.form,
};

const UNMAPPED_FORM_ITEM = {
	children: [],
	config: {classNameId: '0'},
	itemId: 'form-item',
	parentId: '',
	type: LAYOUT_DATA_ITEM_TYPES.form,
};

const renderComponent = ({item = MAPPED_FORM_ITEM} = {}) => {
	const mockDispatch = jest.fn((a) => {
		if (typeof a === 'function') {
			return a(mockDispatch, () => state);
		}
	});

	const state = {
		languageId: 'en_US',
		layoutData: {
			items: {
				[item.itemId]: item,
			},
		},
		permissions: {UPDATE: true},
		selectedViewportSize: VIEWPORT_SIZES.desktop,
	};

	return render(
		<StoreAPIContextProvider dispatch={mockDispatch} getState={() => state}>
			<FormGeneralPanel item={item} />
		</StoreAPIContextProvider>
	);
};

describe('FormGeneralPanel', () => {
	beforeEach(() => {
		updateFormItemConfig.mockClear();
	});

	it('renders success message options if the form is mapped', async () => {
		await act(async () => {
			renderComponent();
		});

		expect(screen.getByLabelText('success-message')).toBeInTheDocument();
	});

	it('does not renders success message options if the form is not mapped', async () => {
		await act(async () => {
			renderComponent({item: UNMAPPED_FORM_ITEM});
		});

		expect(
			screen.queryByLabelText('success-message')
		).not.toBeInTheDocument();
	});

	it('success text is the default option selected', async () => {
		await act(async () => {
			renderComponent();
		});

		expect(screen.getByLabelText('success-text')).toBeInTheDocument();

		expect(
			screen.getByDisplayValue(
				'thank-you.-your-information-was-successfully-received'
			)
		).toBeInTheDocument();
	});

	it('save text as success text when user type it', async () => {
		await act(async () => {
			renderComponent();
		});

		const input = screen.queryByLabelText('success-text');

		userEvent.type(input, 'New message', {
			initialSelectionEnd: 100,
			initialSelectionStart: 0,
		});

		fireEvent.blur(input);

		expect(updateFormItemConfig).toBeCalledWith(
			expect.objectContaining({
				itemConfig: {
					successMessage: {message: {en_US: 'New message'}},
				},
			})
		);
	});

	it('save url when user type it', async () => {
		await act(async () => {
			renderComponent();
		});

		const selector = screen.queryByLabelText('success-message');

		fireEvent.change(selector, {
			target: {value: 'url'},
		});

		const input = screen.queryByLabelText('external-url');

		userEvent.type(input, 'https://liferay.com', {
			initialSelectionEnd: 100,
			initialSelectionStart: 0,
		});

		fireEvent.blur(input);

		expect(updateFormItemConfig).toBeCalledWith(
			expect.objectContaining({
				itemConfig: {
					successMessage: {url: {en_US: 'https://liferay.com'}},
				},
			})
		);
	});

	it('loads the correct fields when the item is already configured with text', async () => {
		const item = {
			...MAPPED_FORM_ITEM,

			config: {
				...MAPPED_FORM_ITEM.config,
				successMessage: {message: {en_US: 'Message'}},
			},
		};

		await act(async () => {
			renderComponent({item});
		});

		const input = screen.getByLabelText('success-text');

		expect(input).toBeInTheDocument();
		expect(input.value).toBe('Message');
	});

	it('loads the correct fields when the item is already configured with url', async () => {
		const item = {
			...MAPPED_FORM_ITEM,

			config: {
				...MAPPED_FORM_ITEM.config,
				successMessage: {url: {en_US: 'https://liferay.com'}},
			},
		};

		await act(async () => {
			renderComponent({item});
		});

		const input = screen.getByLabelText('external-url');

		expect(input).toBeInTheDocument();
		expect(input.value).toBe('https://liferay.com');
	});

	it('loads the correct fields when the item is already configured with page', async () => {
		config.layoutItemSelectorURL = 'http://example.com';

		global.Liferay = {
			...global.Liferay,
			PortletKeys: {
				ITEM_SELECTOR: '',
			},
			Util: {
				...global.Liferay.Util,
				getPortletNamespace: () => '',
			},
		};

		const item = {
			...MAPPED_FORM_ITEM,

			config: {
				...MAPPED_FORM_ITEM.config,
				successMessage: {
					layout: {
						groupId: '1',
						layoutId: '1',
						layoutUuid: 'uuid',
						privateLayout: false,
						title: 'My Page',
					},
				},
			},
		};

		await act(async () => {
			renderComponent({item});
		});

		expect(screen.getByLabelText('page')).toBeInTheDocument();
		expect(screen.getByDisplayValue('My Page')).toBeInTheDocument();
	});
});
