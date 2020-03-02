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

import Paragraph from '../../../src/main/resources/META-INF/resources/Paragraph/Paragraph.es';
import withContextMock from '../__mocks__/withContextMock.es';

let component;
const spritemap = 'icons.svg';

const defaultParagraphConfig = {
	name: 'textField',
	spritemap,
};

const ParagraphWithContextMock = withContextMock(Paragraph);

describe('Field Paragraph', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('is readOnly', () => {
		component = new ParagraphWithContextMock({
			...defaultParagraphConfig,
			readOnly: true,
		});

		expect(component).toMatchSnapshot();
	});

	it('has an id', () => {
		component = new ParagraphWithContextMock({
			...defaultParagraphConfig,
			id: 'ID',
		});

		expect(component).toMatchSnapshot();
	});

	it('has a label', () => {
		component = new ParagraphWithContextMock({
			...defaultParagraphConfig,
			label: 'label',
		});

		expect(component).toMatchSnapshot();
	});

	it('has a placeholder', () => {
		component = new ParagraphWithContextMock({
			...defaultParagraphConfig,
			placeholder: 'Placeholder',
		});

		expect(component).toMatchSnapshot();
	});

	it('is not required', () => {
		component = new ParagraphWithContextMock({
			...defaultParagraphConfig,
			required: false,
		});

		expect(component).toMatchSnapshot();
	});

	it('renders Label if showLabel is true', () => {
		component = new ParagraphWithContextMock({
			...defaultParagraphConfig,
			label: 'text',
			showLabel: true,
		});

		expect(component).toMatchSnapshot();
	});

	it('has a spritemap', () => {
		component = new ParagraphWithContextMock(defaultParagraphConfig);

		expect(component).toMatchSnapshot();
	});

	it('has a value', () => {
		component = new ParagraphWithContextMock({
			...defaultParagraphConfig,
			value: 'value',
		});

		expect(component).toMatchSnapshot();
	});

	it('has a key', () => {
		component = new ParagraphWithContextMock({
			...defaultParagraphConfig,
			key: 'key',
		});

		expect(component).toMatchSnapshot();
	});
});
