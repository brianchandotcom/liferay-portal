/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	cleanup,
	fireEvent,
	render,
	screen,
	waitFor,
} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom';

import ChatbotForm from '../../../src/main/resources/META-INF/resources/js/chatbot_form/ChatbotForm';

const mockGetChatbot = jest.fn();
const mockPostChatbot = jest.fn();
const mockPutChatbot = jest.fn();
const mockOpenToast = jest.fn();

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/agent_definition_form/services/AgentDefinitionService',
	() => ({
		getAgentDefinitions: jest.fn().mockResolvedValue({items: []}),
	})
);

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/chatbot_form/services/ChatbotService',
	() => ({
		deleteChatbotAgentDefinitionRelationship: jest
			.fn()
			.mockResolvedValue({}),
		getChatbot: (...args: any[]) => mockGetChatbot(...args),
		postChatbot: (...args: any[]) => mockPostChatbot(...args),
		putChatbot: (...args: any[]) => mockPutChatbot(...args),
		putChatbotAgentDefinitionRelationship: jest.fn().mockResolvedValue({}),
	})
);

jest.mock('@liferay/object-js-components-web', () => ({
	openToast: (...args: any[]) => mockOpenToast(...args),
}));

jest.mock('frontend-js-components-web', () => ({
	InputLocalized: ({label}: {label: string}) => <label>{label}</label>,
}));

jest.mock('frontend-js-web', () => ({
	sub: (template: string, ...args: string[]) =>
		template.replace(/\{\d+\}/g, () => args.shift() ?? ''),
}));

(global as any).Liferay = {
	Icons: {spritemap: 'icons.svg'},
	Language: {
		get: (key: string) => key,
	},
};

const defaultProps = {
	accountEntryExternalReferenceCode: 'ACCOUNT',
	backURL: '/back',
	companyLogoAcceptedFileExtensions: 'jpeg, jpg, png',
	companyLogoMaximumFileSize: 1024,
	companyLogoMaximumFileSizeLabel: '1 KB',
	companyLogoUploadTip: 'Upload a jpeg, jpg, png no larger than 1 KB',
	externalReferenceCode: '',
	portalURL: 'http://localhost:8080',
	readOnly: false,
};

function getHiddenFileInput(): HTMLInputElement {
	return document.getElementById('companyLogo') as HTMLInputElement;
}

function makeFile(name: string, sizeInBytes: number, type = 'image/png'): File {
	const blob = new Blob([new Uint8Array(sizeInBytes)], {type});

	return new File([blob], name, {type});
}

describe('ChatbotForm company logo upload', () => {
	beforeEach(() => {
		mockOpenToast.mockClear();
		mockGetChatbot.mockReset();
		mockPostChatbot.mockReset();
		mockPutChatbot.mockReset();
	});

	afterEach(() => {
		cleanup();
	});

	it('rejects files larger than companyLogoMaximumFileSize when limit is greater than zero', async () => {
		render(<ChatbotForm {...defaultProps} />);

		const file = makeFile('big-logo.png', 4096);

		fireEvent.change(getHiddenFileInput(), {target: {files: [file]}});

		await waitFor(() => {
			expect(mockOpenToast).toHaveBeenCalledWith(
				expect.objectContaining({type: 'danger'})
			);
		});

		expect(screen.queryByText('big-logo.png')).not.toBeInTheDocument();
	});

	it('accepts files of any size when companyLogoMaximumFileSize is zero (unlimited)', async () => {
		render(
			<ChatbotForm {...defaultProps} companyLogoMaximumFileSize={0} />
		);

		const file = makeFile('huge-logo.png', 5_000_000);

		fireEvent.change(getHiddenFileInput(), {target: {files: [file]}});

		await screen.findByText('huge-logo.png');

		expect(mockOpenToast).not.toHaveBeenCalledWith(
			expect.objectContaining({type: 'danger'})
		);
	});

	it('hides the Clear button when no company logo is set', () => {
		render(<ChatbotForm {...defaultProps} />);

		expect(
			screen.queryByRole('button', {name: 'clear'})
		).not.toBeInTheDocument();
	});

	it('renders the Clear button after a valid file is selected', async () => {
		render(<ChatbotForm {...defaultProps} />);

		const file = makeFile('logo.png', 512);

		fireEvent.change(getHiddenFileInput(), {target: {files: [file]}});

		expect(
			await screen.findByRole('button', {name: 'clear'})
		).toBeInTheDocument();
	});

	it('disables the select button while reading the file', async () => {
		render(<ChatbotForm {...defaultProps} />);

		const file = makeFile('logo.png', 512);

		fireEvent.change(getHiddenFileInput(), {target: {files: [file]}});

		const selectButton = screen.getByLabelText('select-x');

		expect(selectButton).toBeDisabled();

		await waitFor(() => expect(selectButton).not.toBeDisabled());
	});
});
