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

import DocumentLibrary from '../../../src/main/resources/META-INF/resources/DocumentLibrary/DocumentLibrary.es';
import withContextMock from '../__mocks__/withContextMock.es';

let component;
const spritemap = 'icons.svg';

const defaultDocumentLibraryConfig = {
	name: 'textField',
	spritemap,
};

const DocumentLibraryWithContextMock = withContextMock(DocumentLibrary);

describe('Field DocumentLibrary', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('is not readOnly', () => {
		component = new DocumentLibraryWithContextMock({
			...defaultDocumentLibraryConfig,
			readOnly: false,
		});

		expect(component).toMatchSnapshot();
	});

	it('has a helptext', () => {
		component = new DocumentLibraryWithContextMock({
			...defaultDocumentLibraryConfig,
			tip: 'Type something',
		});

		expect(component).toMatchSnapshot();
	});

	it('has an id', () => {
		component = new DocumentLibraryWithContextMock({
			...defaultDocumentLibraryConfig,
			id: 'ID',
		});

		expect(component).toMatchSnapshot();
	});

	it('has a label', () => {
		component = new DocumentLibraryWithContextMock({
			...defaultDocumentLibraryConfig,
			label: 'label',
		});

		expect(component).toMatchSnapshot();
	});

	it('has a placeholder', () => {
		component = new DocumentLibraryWithContextMock({
			...defaultDocumentLibraryConfig,
			placeholder: 'Placeholder',
		});

		expect(component).toMatchSnapshot();
	});

	it('is not required', () => {
		component = new DocumentLibraryWithContextMock({
			...defaultDocumentLibraryConfig,
			required: false,
		});

		expect(component).toMatchSnapshot();
	});

	it('renders Label if showLabel is true', () => {
		component = new DocumentLibraryWithContextMock({
			...defaultDocumentLibraryConfig,
			label: 'text',
			showLabel: true,
		});

		expect(component).toMatchSnapshot();
	});

	it('has a spritemap', () => {
		component = new DocumentLibraryWithContextMock(
			defaultDocumentLibraryConfig
		);

		expect(component).toMatchSnapshot();
	});

	it('has a value', () => {
		component = new DocumentLibraryWithContextMock({
			...defaultDocumentLibraryConfig,
			value: '{"id":"123"}',
		});

		expect(component).toMatchSnapshot();
	});
});
