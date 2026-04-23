/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {fireEvent, render, screen, waitFor} from '@testing-library/react';
import React from 'react';

import CMSUploadModal from '../../js/Attachment/CMSFilesUploadModal';

jest.mock('@clayui/modal', () => ({
	__esModule: true,
	default: {
		Header: ({children}: {children: React.ReactNode}) => (
			<header data-testid="modal-header">{children}</header>
		),
	},
}));

jest.mock('frontend-js-components-web', () => ({
	FieldBase: ({
		children,
		errorMessage,
		label,
		required,
	}: {
		children: React.ReactNode;
		errorMessage?: string;
		label: string;
		required?: boolean;
	}) => (
		<div>
			<label htmlFor="spaceSelect">
				{label}

				{required && <span>*</span>}
			</label>

			{errorMessage && <span role="alert">{errorMessage}</span>}

			{children}
		</div>
	),
	MultipleFileUploader: ({
		formValidation,
		onModalClose,
		onUploadComplete,
		scopeSelectorElement,
	}: {
		formValidation: () => Promise<boolean>;
		onModalClose: () => void;
		onUploadComplete: (result: {
			failedFiles: string[];
			successFiles: string[];
		}) => void;
		scopeSelectorElement?: React.ReactNode;
	}) => (
		<div data-testid="multi-uploader">
			{scopeSelectorElement}

			<button data-testid="btn-validate" onClick={formValidation}>
				validate
			</button>

			<button
				data-testid="btn-complete-success-single"
				onClick={() =>
					onUploadComplete({
						failedFiles: [],
						successFiles: ['file.txt'],
					})
				}
			>
				complete single
			</button>

			<button
				data-testid="btn-complete-success-multiple"
				onClick={() =>
					onUploadComplete({
						failedFiles: [],
						successFiles: ['file1.txt', 'file2.txt'],
					})
				}
			>
				complete multiple
			</button>

			<button
				data-testid="btn-complete-with-failures"
				onClick={() =>
					onUploadComplete({
						failedFiles: ['file.txt'],
						successFiles: [],
					})
				}
			>
				complete failure
			</button>

			<button data-testid="btn-close" onClick={onModalClose}>
				close
			</button>
		</div>
	),
	openToast: jest.fn(),
}));

jest.mock('frontend-js-web', () => ({
	fetch: jest.fn(),
	getFileAsBase64: jest.fn(),
	sub: jest.fn((key: string) => key),
}));

(globalThis as any).Liferay = {
	Language: {
		get: (key: string) => key,
	},
	ThemeDisplay: {
		getPathContext: () => '',
		getSiteGroupId: () => '12345',
	},
};

const mockOpenToast: jest.Mock = jest.requireMock(
	'frontend-js-components-web'
).openToast;
const mockFetch: jest.Mock = jest.requireMock('frontend-js-web').fetch;
const mockGetFileAsBase64: jest.Mock =
	jest.requireMock('frontend-js-web').getFileAsBase64;
const mockSub: jest.Mock = jest.requireMock('frontend-js-web').sub;

const ONE_SPACE = [
	{externalReferenceCode: 'space-erc-1', id: 1, name: 'Space One'},
];

const MULTIPLE_SPACES = [
	{externalReferenceCode: 'space-erc-1', id: 1, name: 'Space One'},
	{externalReferenceCode: 'space-erc-2', id: 2, name: 'Space Two'},
];

const defaultProps = {
	onModalClose: jest.fn(),
	onUploadSuccess: jest.fn(),
};

beforeEach(() => {
	mockFetch.mockResolvedValue({
		json: jest.fn().mockResolvedValue({id: 1}),
		ok: true,
	});
	mockGetFileAsBase64.mockResolvedValue('base64data==');
});

afterEach(() => {
	jest.clearAllMocks();
});

describe('CMSFilesUploadModal — space selector rendering', () => {
	it('does not render space selector when spaces array is empty', () => {
		render(<CMSUploadModal {...defaultProps} spaces={[]} />);

		expect(screen.queryByRole('combobox')).not.toBeInTheDocument();
	});

	it('renders an enabled selector with empty value when there is only one space', () => {
		render(<CMSUploadModal {...defaultProps} spaces={ONE_SPACE} />);

		const select = screen.getByRole('combobox');

		expect(select).not.toBeDisabled();
		expect(select).toHaveValue('');
	});

	it('shows a placeholder option for single space', () => {
		render(<CMSUploadModal {...defaultProps} spaces={ONE_SPACE} />);

		expect(
			screen.getByRole('option', {name: 'select-a-space'})
		).toBeInTheDocument();
	});

	it('renders enabled selector with placeholder when multiple spaces exist', () => {
		render(<CMSUploadModal {...defaultProps} spaces={MULTIPLE_SPACES} />);

		const select = screen.getByRole('combobox');

		expect(select).not.toBeDisabled();
		expect(select).toHaveValue('');
		expect(
			screen.getByRole('option', {name: 'select-a-space'})
		).toBeInTheDocument();
	});

	it('renders an option for every space', () => {
		render(<CMSUploadModal {...defaultProps} spaces={MULTIPLE_SPACES} />);

		expect(
			screen.getByRole('option', {name: 'Space One'})
		).toBeInTheDocument();
		expect(
			screen.getByRole('option', {name: 'Space Two'})
		).toBeInTheDocument();
	});
});

describe('CMSFilesUploadModal — form validation', () => {
	it('fails validation and shows error even when only one space exists', async () => {
		render(<CMSUploadModal {...defaultProps} spaces={ONE_SPACE} />);

		fireEvent.click(screen.getByTestId('btn-validate'));

		await waitFor(() => {
			expect(screen.getByRole('alert')).toHaveTextContent(
				'this-field-is-required'
			);
		});
	});

	it('fails validation and shows inline error when no space is selected', async () => {
		render(<CMSUploadModal {...defaultProps} spaces={MULTIPLE_SPACES} />);

		fireEvent.click(screen.getByTestId('btn-validate'));

		await waitFor(() => {
			expect(screen.getByRole('alert')).toHaveTextContent(
				'this-field-is-required'
			);
		});
	});

	it('passes validation after a space is selected', async () => {
		render(<CMSUploadModal {...defaultProps} spaces={MULTIPLE_SPACES} />);

		fireEvent.change(screen.getByRole('combobox'), {
			target: {value: 'space-erc-1'},
		});

		fireEvent.click(screen.getByTestId('btn-validate'));

		await waitFor(() => {
			expect(screen.queryByRole('alert')).not.toBeInTheDocument();
		});
	});

	it('clears the inline error immediately when a space is selected', async () => {
		render(<CMSUploadModal {...defaultProps} spaces={MULTIPLE_SPACES} />);

		fireEvent.click(screen.getByTestId('btn-validate'));

		await waitFor(() => {
			expect(screen.getByRole('alert')).toBeInTheDocument();
		});

		fireEvent.change(screen.getByRole('combobox'), {
			target: {value: 'space-erc-1'},
		});

		expect(screen.queryByRole('alert')).not.toBeInTheDocument();
	});
});

describe('CMSFilesUploadModal — upload completion', () => {
	it('calls onUploadSuccess and shows a toast on successful upload', () => {
		render(<CMSUploadModal {...defaultProps} spaces={ONE_SPACE} />);

		fireEvent.change(screen.getByRole('combobox'), {
			target: {value: 'space-erc-1'},
		});

		fireEvent.click(screen.getByTestId('btn-complete-success-single'));

		expect(defaultProps.onUploadSuccess).toHaveBeenCalledTimes(1);
		expect(mockOpenToast).toHaveBeenCalledWith(
			expect.objectContaining({type: 'success'})
		);
	});

	it('uses the singular message key for a single uploaded file', () => {
		render(<CMSUploadModal {...defaultProps} spaces={ONE_SPACE} />);

		fireEvent.change(screen.getByRole('combobox'), {
			target: {value: 'space-erc-1'},
		});

		fireEvent.click(screen.getByTestId('btn-complete-success-single'));

		expect(mockSub).toHaveBeenCalledWith(
			'x-file-was-successfully-uploaded-to-x-space',
			expect.anything()
		);
	});

	it('uses the plural message key for multiple uploaded files', () => {
		render(<CMSUploadModal {...defaultProps} spaces={ONE_SPACE} />);

		fireEvent.change(screen.getByRole('combobox'), {
			target: {value: 'space-erc-1'},
		});

		fireEvent.click(screen.getByTestId('btn-complete-success-multiple'));

		expect(mockSub).toHaveBeenCalledWith(
			'x-files-were-successfully-uploaded-to-x-space',
			expect.anything()
		);
	});

	it('includes the space name in the toast message', () => {
		render(<CMSUploadModal {...defaultProps} spaces={ONE_SPACE} />);

		fireEvent.change(screen.getByRole('combobox'), {
			target: {value: 'space-erc-1'},
		});

		fireEvent.click(screen.getByTestId('btn-complete-success-single'));

		expect(mockSub).toHaveBeenCalledWith(
			expect.any(String),
			expect.arrayContaining(['Space One'])
		);
	});

	it('closes the modal when upload completes with no failures', () => {
		render(<CMSUploadModal {...defaultProps} spaces={ONE_SPACE} />);

		fireEvent.click(screen.getByTestId('btn-complete-success-single'));

		expect(defaultProps.onModalClose).toHaveBeenCalledTimes(1);
	});

	it('does not close the modal when there are failed files', () => {
		render(<CMSUploadModal {...defaultProps} spaces={ONE_SPACE} />);

		fireEvent.click(screen.getByTestId('btn-complete-with-failures'));

		expect(defaultProps.onModalClose).not.toHaveBeenCalled();
	});

	it('does not call onUploadSuccess when there are no successful files', () => {
		render(<CMSUploadModal {...defaultProps} spaces={ONE_SPACE} />);

		fireEvent.click(screen.getByTestId('btn-complete-with-failures'));

		expect(defaultProps.onUploadSuccess).not.toHaveBeenCalled();
	});
});
