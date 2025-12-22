/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';

// eslint-disable-next-line
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {act, fireEvent, render, waitFor} from '@testing-library/react';
import {fetch} from 'frontend-js-web';
import React from 'react';

import ExportTranslationModalContent from '../../../../src/main/resources/META-INF/resources/js/main_view/modal/ExportTranslationModalContent';

const mockCloseModal = jest.fn();

jest.mock('frontend-js-components-web', () => ({
	openToast: jest.fn(),
}));

jest.mock('frontend-js-web', () => ({
	...(jest.requireActual('frontend-js-web') as any),
	fetch: jest.fn(() => {
		return Promise.resolve({
			blob: () =>
				Promise.resolve(
					new Blob(['mock data'], {type: 'application/octet-stream'})
				),
			headers: {
				get: () => 'attachment; filename="mock-file.zip"',
			},
			json: () => Promise.resolve({items: []}),
			ok: true,
		});
	}),
}));

const mockedFetch = fetch as jest.Mock<unknown>;

const DEFAULT_PROPS = {
	availableExportFileFormats: [
		{
			displayName: 'XLIFF 2.0',
			mimeType: 'application/xliff+xml',
		},
		{
			displayName: 'XLIFF 1.2',
			mimeType: 'application/x-xliff+xml',
		},
	],
	availableSourceLocales: [
		{
			displayName: 'English (United States)',
			languageId: 'en_US',
		},
		{
			displayName: 'Spanish (Spain)',
			languageId: 'es_ES',
		},
	],
	availableTargetLocales: [
		{
			displayName: 'English (United States)',
			languageId: 'en_US',
		},
		{
			displayName: 'Spanish (Spain)',
			languageId: 'es_ES',
		},
		{
			displayName: 'French (France)',
			languageId: 'fr_FR',
		},
		{
			displayName: 'German (Germany)',
			languageId: 'de_DE',
		},
	],
	closeModal: mockCloseModal,
	defaultSourceLanguageId: 'en_US',
	itemId: 123,
};

const renderComponent = (props = DEFAULT_PROPS) => {
	return render(<ExportTranslationModalContent {...props} />);
};

(global as any).URL = {
	createObjectURL: jest.fn(() => 'blob:mock/url-string'),
	revokeObjectURL: jest.fn(),
};

describe('ExportTranslationModalContent', () => {
	afterEach(() => {
		jest.clearAllMocks();
	});

	afterAll(() => {
		jest.restoreAllMocks();
		mockedFetch.mockReset();
	});

	it('checks the accessibility of the export translation component', async () => {
		const {container} = renderComponent();

		await act(async () => {
			checkAccessibility({bestPractices: true, context: container});
		});
	});

	it('renders the modal header and selection labels', () => {
		const {getByText} = renderComponent();

		expect(getByText('export-for-translation')).toBeInTheDocument();

		expect(getByText('export-file-format')).toBeInTheDocument();
		expect(getByText('source-language')).toBeInTheDocument();
		expect(getByText('translation-languages')).toBeInTheDocument();
	});

	it('shows the source language as readonly when singular', () => {
		const {getByLabelText} = renderComponent({
			...DEFAULT_PROPS,
			availableSourceLocales: [DEFAULT_PROPS.availableSourceLocales[0]],
		});

		expect(getByLabelText('source-language')).toHaveAttribute('readonly');
	});

	it('shows the source language to be a select when multiple', () => {
		const {getByRole} = renderComponent();

		DEFAULT_PROPS.availableSourceLocales.forEach((locale) => {
			expect(
				getByRole('option', {name: locale.displayName})
			).toBeInTheDocument();
		});
	});

	it('disables the target language if chosen as a source language', async () => {
		const {getByLabelText} = renderComponent();

		expect(getByLabelText('English (United States)')).toBeDisabled();

		fireEvent.change(getByLabelText('source-language'), {
			target: {value: 'es_ES'},
		});

		await waitFor(() => {
			expect(
				getByLabelText('English (United States)')
			).not.toBeDisabled();
			expect(getByLabelText('Spanish (Spain)')).toBeDisabled();
		});
	});

	it('renders all the target languages with checkboxes', () => {
		const {getByLabelText} = renderComponent();

		DEFAULT_PROPS.availableTargetLocales.forEach((locale) => {
			expect(getByLabelText(locale.displayName)).toBeInTheDocument();
		});
	});

	it('calls export API when the export button is clicked', async () => {
		const {getByLabelText, getByText} = renderComponent();

		fireEvent.click(getByLabelText('Spanish (Spain)'));
		fireEvent.click(getByLabelText('French (France)'));

		fireEvent.click(getByText('export'));

		await waitFor(() => {
			expect(mockedFetch).toHaveBeenCalledWith(
				'/o/cms/basic-web-contents/123/translations?sourceLanguageId=en_US&targetLanguageIds=es_ES%2Cfr_FR&version=2.0'
			);
		});
	});
});
