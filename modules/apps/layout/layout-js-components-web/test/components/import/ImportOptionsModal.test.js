/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {act, fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import {ImportOptionsModal} from '../../../src/main/resources/META-INF/resources/js';

const renderComponent = async ({
	onCloseModal = () => null,
	onImport = () => null,
} = {}) => {
	await act(async () => {
		render(
			<ImportOptionsModal
				onCloseModal={onCloseModal}
				onImport={onImport}
			/>
		);

		jest.advanceTimersByTime(1000);
	});
};

describe('ImportOptionsModal', () => {
	afterAll(() => {
		jest.useRealTimers();
	});

	beforeAll(() => {
		jest.useFakeTimers();
	});

	it('renders text informing the user that some items already exist', () => {
		renderComponent();

		expect(
			screen.getByText(
				'one-or-more-items-from-the-zip-already-exist-in-this-location'
			)
		).toBeInTheDocument();
	});

	it('renders a radio button with 3 options', () => {
		renderComponent();

		expect(screen.getAllByRole('radio').length).toBe(3);
		expect(
			screen.getByRole('radio', {name: /do-not-import-existing-items/i})
		).toBeInTheDocument();
		expect(
			screen.getByRole('radio', {name: /overwrite-existing-items/i})
		).toBeInTheDocument();
		expect(
			screen.getByRole('radio', {name: /keep-both/i})
		).toBeInTheDocument();
	});

	it('renders cancel and import buttons', () => {
		const onImport = jest.fn();
		const onCloseModal = jest.fn();

		renderComponent({onCloseModal, onImport});

		const cancelButton = screen.getByRole('button', {name: /cancel/i});
		const importButton = screen.getByRole('button', {name: /import/i});

		expect(cancelButton).toBeInTheDocument();
		expect(importButton).toBeInTheDocument();

		fireEvent.click(cancelButton);
		fireEvent.click(importButton);

		jest.advanceTimersByTime(1000);

		expect(onCloseModal).toHaveBeenCalled();
		expect(onImport).toHaveBeenCalled();
	});
});
