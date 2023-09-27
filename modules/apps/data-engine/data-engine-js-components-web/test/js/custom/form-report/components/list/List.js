/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import List from '../../../../../../src/main/resources/META-INF/resources/js/custom/form-report/components/list/List';

const props = {
	data: ['label1', 'label2', 'label3', 'label4', 'label5'],
	field: {name: 'fieldName'},
	onClick: () => {},
	summary: {},
	totalEntries: 5,
};

describe('List', () => {
	it('renders', () => {
		const {asFragment, container} = render(<List {...props} />);

		const li = container.querySelectorAll('li');

		expect(li.length).toBe(5);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders color list', () => {
		const colorProps = {
			...props,
			data: ['7F26FF', '2BA676', 'CBCBCB', 'FF21A0', 'FF0D0D'],
			type: 'color',
		};

		const {container} = render(<List {...colorProps} />);

		const colorTextList = container.querySelectorAll('.color-text');

		expect(colorTextList.length).toBe(5);

		colorTextList.forEach((colorText, index) =>
			expect(colorText.textContent).toBe(colorProps.data[index])
		);
	});

	it('shows a button to see all entries when there are more than 5 entries', () => {
		const {container} = render(<List {...props} totalEntries={6} />);

		expect(container.querySelector('button').innerHTML).toBe(
			'see-all-entries'
		);
	});

	it('renders dates in the english US format', () => {
		const originalThemeDisplay = themeDisplay;
		themeDisplay = {getLanguageId: () => 'en_US'};

		const {getByText} = render(
			<List {...props} data={['2020-12-20']} type="date" />
		);

		expect(getByText('12/20/2020')).toBeTruthy();

		themeDisplay = originalThemeDisplay;
	});

	it('renders dates in the portuguese BR format', () => {
		const originalThemeDisplay = themeDisplay;
		themeDisplay = {getLanguageId: () => 'pt_BR'};

		const {getByText} = render(
			<List {...props} data={['2020-12-20']} type="date" />
		);

		expect(getByText('20/12/2020')).toBeTruthy();

		themeDisplay = originalThemeDisplay;
	});

	it('renders dates and time in the english US format', () => {
		const originalThemeDisplay = themeDisplay;
		themeDisplay = {getLanguageId: () => 'en_US'};

		const {getByText} = render(
			<List {...props} data={['2020-12-20 14:48']} type="date_time" />
		);

		expect(getByText('12/20/2020 2:48 PM')).toBeTruthy();

		themeDisplay = originalThemeDisplay;
	});

	it('renders dates and time in the japanese format', () => {
		const originalThemeDisplay = themeDisplay;
		themeDisplay = {getLanguageId: () => 'ja_JP'};

		const {getByText} = render(
			<List {...props} data={['2020-12-20 14:48']} type="date_time" />
		);

		expect(getByText('2020/12/20 14:48')).toBeTruthy();

		themeDisplay = originalThemeDisplay;
	});
});
