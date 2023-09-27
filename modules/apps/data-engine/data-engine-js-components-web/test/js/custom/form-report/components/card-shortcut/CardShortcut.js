/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import CardShortcut from '../../../../../../src/main/resources/META-INF/resources/js/custom/form-report/components/card-shortcut/CardShortcut';
import SidebarContextProviderWrapper from '../../__utils__/SidebarContextProviderWrapper';

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
