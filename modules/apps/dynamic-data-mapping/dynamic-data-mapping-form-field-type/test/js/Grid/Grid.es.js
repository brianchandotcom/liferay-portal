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

import Grid from '../../../src/main/resources/META-INF/resources/Grid/Grid.es';

let component;
const spritemap = 'icons.svg';

describe('Grid', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('renders columns', () => {
		component = new Grid({
			columns: [
				{
					label: 'col1',
					value: 'fieldId'
				},
				{
					label: 'col2',
					value: 'fieldId'
				}
			],
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('renders no columns when columns comes empty', () => {
		component = new Grid({
			columns: [],
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('is not edidable', () => {
		component = new Grid({
			readOnly: false,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('has a tip', () => {
		component = new Grid({
			spritemap,
			tip: 'Type something'
		});

		expect(component).toMatchSnapshot();
	});

	it('has an id', () => {
		component = new Grid({
			id: 'ID',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('has a label', () => {
		component = new Grid({
			label: 'label',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('is not required', () => {
		component = new Grid({
			required: false,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('renders rows', () => {
		component = new Grid({
			rows: [
				{
					label: 'row1',
					value: 'fieldId'
				},
				{
					label: 'row2',
					value: 'fieldId'
				}
			],
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('renders no rows when row comes empty', () => {
		component = new Grid({
			rows: [],
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('renders Label if showLabel is true', () => {
		component = new Grid({
			label: 'text',
			showLabel: true,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('has a spritemap', () => {
		component = new Grid({
			spritemap
		});

		expect(component).toMatchSnapshot();
	});
});
