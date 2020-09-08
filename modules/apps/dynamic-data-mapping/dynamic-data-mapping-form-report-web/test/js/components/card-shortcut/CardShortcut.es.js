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

import {act, cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import CardShortcut from '../../../../src/main/resources/META-INF/resources/js/components/card-shortcut/CardShortcut.es';
import SidebarContextProviderWrapper from '../../SidebarContextProviderWrapper.es';

const fields = [
	{
		label: 'Field 1',
		name: 'field1',
		type: 'text',
	},
	{
		label: 'Field 2',
		name: 'field2',
		type: 'radio',
	},
];

const createCardShortcut = () => (
	<SidebarContextProviderWrapper>
		<CardShortcut fields={fields} />
	</SidebarContextProviderWrapper>
);

describe('CardShortcut', () => {
	afterEach(cleanup);

	const {location} = window;

	beforeEach(() => {
		jest.useFakeTimers();
	});

	afterAll(() => {
		window.location = location;
	});

	it('a label is created for each field', () => {
		const {getByText} = render(createCardShortcut());

		fields.forEach((field) => {
			expect(getByText(field.label).className).toBe('field-label');
		});
	});

	it('it highlights the item selected', () => {
		const {getByText} = render(createCardShortcut());

		const item = getByText('Field 2');

		expect(item.closest('.selected')).toBe(null);

		fireEvent.click(item);

		act(() => {
			jest.runAllTimers();
		});

		expect(item.closest('.selected')).not.toBe(null);
	});
});
