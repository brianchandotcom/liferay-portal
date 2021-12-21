/* eslint-disable no-unused-vars */
/* eslint-disable no-import-assign */
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

import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';
import {act} from 'react-dom/test-utils';

import FileUrlCopyButton from '../../../src/main/resources/META-INF/resources/js/components/FileUrlCopyButton';

const demoFileUrl = 'http://localhost:8080/documents/my-demo-url.txt';

describe('FileUrlCopyButton', () => {
	beforeEach(() => {
		jest.useFakeTimers();

		global.navigator.writeText = jest.fn();
		Liferay.component = jest.fn();
	});

	afterEach(() => {
		jest.runOnlyPendingTimers();
		jest.useRealTimers();

		jest.restoreAllMocks();
	});

	it('matches the snapshot', () => {
		const {asFragment} = render(<FileUrlCopyButton url={demoFileUrl} />);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders an input and a button with the proper value and the initial UI', () => {
		const {getByDisplayValue, getByRole} = render(
			<FileUrlCopyButton url={demoFileUrl} />
		);

		expect(getByDisplayValue(demoFileUrl)).toBeInTheDocument();

		const button = getByRole('button');
		expect(button).toBeInTheDocument();

		const icon = button.getElementsByTagName('svg')[0];
		expect(icon.classList).toContain('lexicon-icon-copy');
	});

	it('renders the proper icon after clicking the button', async () => {
		const {getByRole} = render(<FileUrlCopyButton url={demoFileUrl} />);
		const button = getByRole('button');

		fireEvent(
			button,
			new MouseEvent('click', {
				bubbles: true,
				cancelable: true,
			})
		);

		let icon = button.getElementsByTagName('svg')[0];
		expect(icon.classList).toContain('lexicon-icon-check');

		act(() => {
			jest.runAllTimers();
		});

		icon = button.getElementsByTagName('svg')[0];
		expect(icon.classList).toContain('lexicon-icon-copy');

		expect(Liferay.component).toHaveBeenCalledTimes(1);
	});
});
