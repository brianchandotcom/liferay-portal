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

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';
import {act} from 'react-dom/test-utils';

import DownloadSpreadsheetButton from '../../../src/main/resources/META-INF/resources/js/components/DownloadSpreadsheetButton/DownloadSpreadsheetButton';
import * as utils from '../../../src/main/resources/META-INF/resources/js/utils/downloadSpreadsheetUtils';

const getComponent = (fileURL = 'demo-file-url') => {
	return <DownloadSpreadsheetButton {...{fileURL, total: 12}} />;
};

describe('DownloadSpreadsheetButton', () => {
	beforeEach(() => {
		jest.useFakeTimers();

		global.Liferay.on = jest.fn().mockReturnValue({
			detach: jest.fn(),
		});

		global.fetch = jest.fn().mockReturnValue({
			blob: jest.fn(),
		});

		global.URL = {
			createObjectURL: jest.fn(),
			revokeObjectURL: jest.fn(),
		};
	});

	afterEach(() => {
		jest.runOnlyPendingTimers();
		jest.useRealTimers();

		jest.restoreAllMocks();
		cleanup();
	});

	it('renders a DownloadSpreadsheetButton component with the proper initial UI state', () => {
		const {container, getByText, getByTitle} = render(getComponent());

		expect(getByText('export-xls')).toBeInTheDocument();

		const {className: btnClassName} = getByTitle(
			'download-your-data-as-an-xls-file'
		);

		expect(
			getByTitle('download-your-data-as-an-xls-file')
		).toBeInTheDocument();
		expect(btnClassName).toContain(
			'btn-outline-borderless btn-outline-secondary'
		);

		expect(
			container.getElementsByClassName('lexicon-icon-download').length
		).toBe(1);
	});

	it('...with the proper loading UI state', async () => {
		const {container, findByText, getByText} = render(getComponent());
		const exportButton = getByText('export-xls');

		expect(getByText('export-xls')).toBeInTheDocument();

		fireEvent(
			exportButton,
			new MouseEvent('click', {
				bubbles: true,
				cancelable: true,
			})
		);

		await findByText('generating-xls');
		expect(getByText('generating-xls')).toBeInTheDocument();
		expect(
			container.getElementsByClassName('loading-animation').length
		).toBe(1);

		act(() => {
			jest.runAllTimers();
		});

		expect(getByText('export-xls')).toBeInTheDocument();
	});

	it('...with the proper restored UI state after cancel', async () => {
		const {container, findByText, getByText, getByTitle} = render(
			getComponent()
		);
		const exportButton = getByText('export-xls');

		fireEvent(
			exportButton,
			new MouseEvent('click', {
				bubbles: true,
				cancelable: true,
			})
		);

		const cancelButton = getByTitle('cancel-export');
		expect(cancelButton).toBeInTheDocument();
		expect(
			container.getElementsByClassName('lexicon-icon-times-circle').length
		).toBe(1);

		await findByText('generating-xls');
		expect(getByText('generating-xls')).toBeInTheDocument();

		fireEvent(
			cancelButton,
			new MouseEvent('click', {
				bubbles: true,
				cancelable: true,
			})
		);

		act(() => {
			jest.runAllTimers();
		});

		await findByText('export-xls');
		expect(getByText('export-xls')).toBeInTheDocument();

		expect(
			container.getElementsByClassName('loading-animation').length
		).toBe(0);
	});

	it('...that calls the proper functions on events', async () => {
		/* eslint-disable no-import-assign */
		const fileURL = 'demo-file-url';
		const {findByText, getByText} = render(getComponent(fileURL));
		const exportButton = getByText('export-xls');

		utils.downloadFileFromBlob = jest.fn();

		const mockedFetch = jest.fn();
		utils.fetchFile = jest.fn(mockedFetch);

		const blob = new Blob();

		mockedFetch.mockReturnValue(blob);

		const controller = new AbortController();

		fireEvent(
			exportButton,
			new MouseEvent('click', {
				bubbles: true,
				cancelable: true,
			})
		);

		await findByText('generating-xls');
		expect(getByText('generating-xls')).toBeInTheDocument();

		act(() => {
			jest.runAllTimers();
		});

		await expect(mockedFetch).toHaveBeenCalledWith({
			controller,
			url: fileURL,
		});
		expect(utils.downloadFileFromBlob).toHaveBeenCalledWith(blob);
	});
});
