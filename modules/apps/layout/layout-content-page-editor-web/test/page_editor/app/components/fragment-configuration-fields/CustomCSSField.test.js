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
import {act, fireEvent, render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import CustomCSSField from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/fragment-configuration-fields/CustomCSSField';
import {FRAGMENT_CLASS_PLACEHOLDER} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/fragmentClassPlaceholder';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {StoreAPIContextProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';

const renderCustomCSSField = ({
	field = {label: 'Custom CSS', name: 'customCSS'},
	onValueSelect = () => {},
	value = null,
} = {}) => {
	return render(
		<StoreAPIContextProvider
			dispatch={() => {}}
			getState={() => ({
				layoutData: {
					items: {
						itemId: {
							itemId: 'itemId',
							type: LAYOUT_DATA_ITEM_TYPES.container,
						},
					},
				},
			})}
		>
			<CustomCSSField
				field={field}
				onValueSelect={onValueSelect}
				value={value}
			/>
		</StoreAPIContextProvider>,
		{
			baseElement: document.body,
		}
	);
};

describe('CSSClassSelectorField', () => {
	it('renders', () => {
		renderCustomCSSField();

		expect(
			screen.getByLabelText('custom-css', {
				selector: 'textarea',
			})
		).toBeInTheDocument();
	});

	it('allow editing the custom css in the modal after clicking on expand button', () => {
		const onValueSelect = jest.fn();

		renderCustomCSSField({onValueSelect});

		// CodeMirror relies on this function to be present in the body

		document.body.createTextRange = () => {
			const textRange = {
				getBoundingClientRect: () => 1,
				getClientRects: () => 1,
			};

			return textRange;
		};

		// Clay modal have an animation when are opened
		// This will make sure that the body is visible before asserting

		jest.useFakeTimers();

		act(() => {
			userEvent.click(screen.getByTitle('expand'));
		});

		act(() => {
			jest.runAllTimers();
		});

		jest.useRealTimers();

		const addButton = screen.getByText('add');

		expect(addButton).toBeInTheDocument();

		act(() => {
			document
				.querySelector('.CodeMirror')
				.CodeMirror.setValue(
					`.${FRAGMENT_CLASS_PLACEHOLDER} { color: blue }`
				);
		});

		userEvent.click(addButton);

		expect(onValueSelect).toBeCalledWith(
			'customCSS',
			`.${FRAGMENT_CLASS_PLACEHOLDER} { color: blue }`
		);
	});

	it('shows default placeholder by default', () => {
		renderCustomCSSField();

		expect(
			screen.getByDisplayValue(`${FRAGMENT_CLASS_PLACEHOLDER}`, {
				exact: false,
			})
		).toBeInTheDocument();
	});

	it('calls onValueSelect when typing something in the textarea', () => {
		const onValueSelect = jest.fn();

		renderCustomCSSField({onValueSelect});

		const textarea = screen.getByLabelText('custom-css', {
			selector: 'textarea',
		});

		const css = `
			.${FRAGMENT_CLASS_PLACEHOLDER} { color: blue; }
			.${FRAGMENT_CLASS_PLACEHOLDER}:hover { color: red; }
		`;

		userEvent.type(textarea, css);

		fireEvent.blur(textarea);

		expect(onValueSelect).toBeCalledWith('customCSS', css);
	});

	it('does not save onValueSelect when typing the same as the default value', () => {
		const onValueSelect = jest.fn();

		renderCustomCSSField({onValueSelect});

		const textarea = screen.getByLabelText('custom-css', {
			selector: 'textarea',
		});

		userEvent.type(textarea, `.${FRAGMENT_CLASS_PLACEHOLDER} {\n\n}`);

		fireEvent.blur(textarea);

		expect(onValueSelect).not.toBeCalled();
	});
});
