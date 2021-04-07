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

import {act, cleanup, render} from '@testing-library/react';
import {PageProvider} from 'data-engine-js-components-web';
import React from 'react';

import Paragraph from '../../../src/main/resources/META-INF/resources/Paragraph/Paragraph.es';

const spritemap = 'icons.svg';

const defaultParagraphConfig = {
	name: 'textField',
	spritemap,
};

const ParagraphWithProvider = (props) => (
	<PageProvider value={{editingLanguageId: 'en_US'}}>
		<Paragraph {...props} />
	</PageProvider>
);

describe('Field Paragraph', () => {
	// eslint-disable-next-line no-console
	const originalWarn = console.warn;

	beforeAll(() => {
		// eslint-disable-next-line no-console
		console.warn = (...args) => {
			if (/DataProvider: Trying/.test(args[0])) {
				return;
			}
			originalWarn.call(console, ...args);
		};
	});

	afterAll(() => {
		// eslint-disable-next-line no-console
		console.warn = originalWarn;
	});

	afterEach(cleanup);

	beforeEach(() => {
		jest.useFakeTimers();
		fetch.mockResponseOnce(JSON.stringify({}));
	});

	it('is readOnly', () => {
		const {container} = render(
			<ParagraphWithProvider {...defaultParagraphConfig} readOnly />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has an id', () => {
		const {container} = render(
			<ParagraphWithProvider {...defaultParagraphConfig} id="Id" />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a label', () => {
		const {container} = render(
			<ParagraphWithProvider {...defaultParagraphConfig} label="label" />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a placeholder', () => {
		const {container} = render(
			<ParagraphWithProvider
				{...defaultParagraphConfig}
				placeholder="Placeholder"
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('is not required', () => {
		const {container} = render(
			<ParagraphWithProvider
				{...defaultParagraphConfig}
				required={false}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('renders Label if showLabel is true', () => {
		const {container} = render(
			<ParagraphWithProvider
				{...defaultParagraphConfig}
				label="text"
				showLabel
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a value', () => {
		const {container} = render(
			<ParagraphWithProvider {...defaultParagraphConfig} value="value" />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a key', () => {
		const {container} = render(
			<ParagraphWithProvider {...defaultParagraphConfig} key="key" />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});
});
