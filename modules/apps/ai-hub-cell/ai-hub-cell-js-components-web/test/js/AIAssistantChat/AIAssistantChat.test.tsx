/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, render, screen} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom';

import AIAssistantChat from '../../../src/main/resources/META-INF/resources/js/AIAssistantChat/AIAssistantChat';

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/AIAssistantChat/api',
	() => ({
		createEventSource: jest.fn(() => Promise.resolve(null)),
		postChatByExternalReferenceCodeMessage: jest.fn(() =>
			Promise.resolve()
		),
	})
);

const defaultProps = {
	getContext: () => ({}),
	instructionDefinitionScope: 'test-scope',
};

describe('AIAssistantChat', () => {
	it('shows the chat input immediately on open', async () => {
		await act(async () => {
			render(<AIAssistantChat {...defaultProps} />);
		});

		await act(async () => {
			screen
				.getByRole('button', {name: 'ai-assistant'})
				.dispatchEvent(new MouseEvent('click', {bubbles: true}));
		});

		expect(
			screen.getByPlaceholderText('Ask me anything...')
		).toBeInTheDocument();
	});

	it('shows the footer disclaimer', async () => {
		await act(async () => {
			render(<AIAssistantChat {...defaultProps} />);
		});

		await act(async () => {
			screen
				.getByRole('button', {name: 'ai-assistant'})
				.dispatchEvent(new MouseEvent('click', {bubbles: true}));
		});

		expect(
			screen.getByText(
				'ai-generated-responses-may-be-inaccurate-please-review-carefully'
			)
		).toBeInTheDocument();
	});
});
