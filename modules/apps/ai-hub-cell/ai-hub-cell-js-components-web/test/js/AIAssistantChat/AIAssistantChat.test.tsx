/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom';

import AIAssistantChat from '../../../src/main/resources/META-INF/resources/js/AIAssistantChat/AIAssistantChat';
import {
	createEventSource,
	postChatByExternalReferenceCodeMessage,
} from '../../../src/main/resources/META-INF/resources/js/AIAssistantChat/api';
import {classifyCategorizationIntent} from '../../../src/main/resources/META-INF/resources/js/Categorization/services/classifyCategorizationIntent';
import {postAIIssueReport} from '../../../src/main/resources/META-INF/resources/js/ReportFeedback/api';

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/AIAssistantChat/api',
	() => ({
		createEventSource: jest.fn(() => Promise.resolve(null)),
		postChatByExternalReferenceCodeMessage: jest.fn(() =>
			Promise.resolve()
		),
	})
);

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/AIAssistantChat/components/CategorizationMessageBalloon',
	() => ({
		__esModule: true,
		default: () => 'categorization-balloon',
	})
);

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/Categorization/services/classifyCategorizationIntent'
);

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/ReportFeedback/api'
);

const mockClassify = classifyCategorizationIntent as jest.MockedFunction<
	typeof classifyCategorizationIntent
>;
const mockCreateEventSource = createEventSource as jest.MockedFunction<
	typeof createEventSource
>;
const mockPostChat =
	postChatByExternalReferenceCodeMessage as jest.MockedFunction<
		typeof postChatByExternalReferenceCodeMessage
	>;
const mockPostAIIssueReport = postAIIssueReport as jest.MockedFunction<
	typeof postAIIssueReport
>;

const defaultProps = {
	getContext: () => ({}),
	instructionDefinitionScope: 'test-scope',
};

function createFakeEventSource() {
	const listeners: Record<string, (event: {data: string}) => void> = {};

	return {
		addEventListener: jest.fn(
			(type: string, handler: (event: {data: string}) => void) => {
				listeners[type] = handler;
			}
		),
		close: jest.fn(),
		emit(type: string, data: string) {
			listeners[type]?.({data});
		},
	};
}

async function renderAndOpen() {
	await act(async () => {
		render(<AIAssistantChat {...defaultProps} />);
	});

	await act(async () => {
		screen
			.getByRole('button', {name: 'ai-assistant'})
			.dispatchEvent(new MouseEvent('click', {bubbles: true}));
	});
}

describe('AIAssistantChat', () => {
	beforeEach(() => {
		window.HTMLElement.prototype.scrollTo = jest.fn();

		mockCreateEventSource.mockReset();
		mockCreateEventSource.mockResolvedValue(null);
		mockPostChat.mockReset();
		mockPostChat.mockResolvedValue(undefined);
		mockPostAIIssueReport.mockReset();
		mockPostAIIssueReport.mockResolvedValue({id: 'report-1'});

		global.Liferay = {
			...global.Liferay,
			Util: {
				...global.Liferay?.Util,
				openToast: jest.fn(),
			},
		};
	});

	it('accumulates several image events into a single balloon', async () => {
		const fakeEventSource = createFakeEventSource();

		mockCreateEventSource.mockResolvedValue(fakeEventSource as never);

		await renderAndOpen();

		await act(async () => {
			fakeEventSource.emit(
				'Chat Message Sent',
				JSON.stringify({
					data: 'AAA',
					mimeType: 'image/png',
					type: 'image',
				})
			);
		});

		await act(async () => {
			fakeEventSource.emit(
				'Chat Message Sent',
				JSON.stringify({
					data: 'BBB',
					mimeType: 'image/png',
					type: 'image',
				})
			);
		});

		const images = screen.getAllByAltText('generated-image');

		expect(images).toHaveLength(2);
		expect(images[0]).toHaveAttribute('src', 'data:image/png;base64,AAA');
		expect(images[1]).toHaveAttribute('src', 'data:image/png;base64,BBB');

		expect(
			screen.getAllByRole('checkbox', {name: 'generated-image'})
		).toHaveLength(2);
	});

	it('defaults the mime type to image/png when the image event omits it', async () => {
		const fakeEventSource = createFakeEventSource();

		mockCreateEventSource.mockResolvedValue(fakeEventSource as never);

		await renderAndOpen();

		await act(async () => {
			fakeEventSource.emit(
				'Chat Message Sent',
				JSON.stringify({data: 'CCC', type: 'image'})
			);
		});

		expect(screen.getByAltText('generated-image')).toHaveAttribute(
			'src',
			'data:image/png;base64,CCC'
		);
	});

	it('exposes the feedback row on a successful message and wires the codes', async () => {
		const fakeEventSource = createFakeEventSource();

		mockCreateEventSource.mockResolvedValue(fakeEventSource as never);

		await renderAndOpen();

		await act(async () => {
			fakeEventSource.emit(
				'Chat Message Sent',
				JSON.stringify({
					agentDefinitionExternalReferenceCodes: ['agent-x'],
					data: 'Here is your answer',
				})
			);
		});

		expect(
			screen.getByRole('button', {
				name: 'send-negative-feedback-or-report-legal-concern',
			})
		).toBeInTheDocument();

		await act(async () => {
			fireEvent.click(
				screen.getByRole('button', {name: 'give-positive-feedback'})
			);
		});

		expect(mockPostAIIssueReport).toHaveBeenCalledWith({
			agentDefinitionExternalReferenceCodes: ['agent-x'],
			feedback: 'positive',
			surface: 'aiAssistant',
		});
	});

	it('hides the feedback row on an error message', async () => {
		const fakeEventSource = createFakeEventSource();

		mockCreateEventSource.mockResolvedValue(fakeEventSource as never);

		await renderAndOpen();

		await act(async () => {
			fakeEventSource.emit(
				'Agent Invocation Failed',
				JSON.stringify({data: 'Something went wrong'})
			);
		});

		expect(screen.getByText('Something went wrong')).toBeInTheDocument();
		expect(
			screen.queryByRole('button', {name: 'give-positive-feedback'})
		).not.toBeInTheDocument();
		expect(
			screen.queryByRole('button', {
				name: 'send-negative-feedback-or-report-legal-concern',
			})
		).not.toBeInTheDocument();
	});

	it('merges the static context and the getContext snapshot when sending', async () => {
		const fakeEventSource = createFakeEventSource();

		mockCreateEventSource.mockResolvedValue(fakeEventSource as never);

		await act(async () => {
			render(
				<AIAssistantChat
					context={{scope: 'static'}}
					getContext={() => ({live: 'value'})}
					instructionDefinitionScope="test-scope"
				/>
			);
		});

		await act(async () => {
			screen
				.getByRole('button', {name: 'ai-assistant'})
				.dispatchEvent(new MouseEvent('click', {bubbles: true}));
		});

		await act(async () => {
			fakeEventSource.emit('Subscribe', 'ref-code');
		});

		const textArea = screen.getByPlaceholderText('Ask me anything...');

		await act(async () => {
			fireEvent.change(textArea, {target: {value: 'Hello'}});
		});

		await act(async () => {
			fireEvent.submit(textArea.closest('form') as HTMLFormElement);
		});

		expect(mockPostChat).toHaveBeenCalledWith(
			expect.objectContaining({
				chatContext: {live: 'value', scope: 'static'},
			})
		);
	});

	it('renders a generated image from an image event', async () => {
		const fakeEventSource = createFakeEventSource();

		mockCreateEventSource.mockResolvedValue(fakeEventSource as never);

		await renderAndOpen();

		await act(async () => {
			fakeEventSource.emit(
				'Chat Message Sent',
				JSON.stringify({
					agentDefinitionExternalReferenceCodes: ['agent-x'],
					data: 'BASE64',
					mimeType: 'image/png',
					type: 'image',
				})
			);
		});

		expect(screen.getByAltText('generated-image')).toHaveAttribute(
			'src',
			'data:image/png;base64,BASE64'
		);
	});

	it('sends the open event message to the chat only once', async () => {
		const fakeEventSource = createFakeEventSource();

		mockCreateEventSource.mockResolvedValue(fakeEventSource as never);

		const openHandlers: ((payload: unknown) => void)[] = [];

		(Liferay.on as jest.Mock).mockImplementation(
			(name: string, callback: (payload: unknown) => void) => {
				if (name === 'openAIAssistantChat') {
					openHandlers.push(callback);
				}
			}
		);

		await act(async () => {
			render(<AIAssistantChat {...defaultProps} />);
		});

		await act(async () => {
			fakeEventSource.emit('Subscribe', 'ref-1');
		});

		await act(async () => {
			openHandlers.forEach((handler) =>
				handler({message: 'generate an image of a cat'})
			);
		});

		expect(mockPostChat).toHaveBeenCalledTimes(1);

		(Liferay.on as jest.Mock).mockReset();
	});

	it('shows the chat input immediately on open', async () => {
		await renderAndOpen();

		expect(
			screen.getByPlaceholderText('Ask me anything...')
		).toBeInTheDocument();
	});

	it('shows the footer disclaimer', async () => {
		await renderAndOpen();

		expect(
			screen.getByText('ai-generated-responses-may-be-inaccurate')
		).toBeInTheDocument();
	});

	describe('free-form categorization', () => {
		beforeEach(() => {
			mockClassify.mockReset();
			mockPostChat.mockClear();
			(Liferay.fire as jest.Mock).mockClear();
		});

		it('does not classify when the feature is disabled', async () => {
			const fakeEventSource = createFakeEventSource();

			mockCreateEventSource.mockResolvedValue(fakeEventSource as never);

			await act(async () => {
				render(
					<AIAssistantChat
						{...defaultProps}
						initialMessage="tag this article"
					/>
				);
			});

			await act(async () => {
				fakeEventSource.emit('Subscribe', 'ref-1');
			});

			expect(mockClassify).not.toHaveBeenCalled();
			expect(mockPostChat).toHaveBeenCalled();
		});

		it('fires a single request event for a categorization message', async () => {
			const fakeEventSource = createFakeEventSource();

			mockCreateEventSource.mockResolvedValue(fakeEventSource as never);
			mockClassify.mockResolvedValue({
				actions: [{agent: 'tag', count: 3, targets: []}],
				passthrough: false,
			});

			await act(async () => {
				render(
					<AIAssistantChat
						{...defaultProps}
						enableFreeFormCategorization
						initialMessage="tag this article"
					/>
				);
			});

			await act(async () => {
				fakeEventSource.emit('Subscribe', 'ref-1');
			});

			expect(mockClassify).toHaveBeenCalledWith('tag this article');
			expect(Liferay.fire).toHaveBeenCalledWith(
				'cms:aiAssistant:requestCategorize',
				{actions: [{agent: 'tag', count: 3, targets: []}]}
			);
			expect(mockPostChat).not.toHaveBeenCalled();
		});

		it('posts a passthrough message to the chat', async () => {
			const fakeEventSource = createFakeEventSource();

			mockCreateEventSource.mockResolvedValue(fakeEventSource as never);
			mockClassify.mockResolvedValue({actions: [], passthrough: true});

			await act(async () => {
				render(
					<AIAssistantChat
						{...defaultProps}
						enableFreeFormCategorization
						initialMessage="what can you do?"
					/>
				);
			});

			await act(async () => {
				fakeEventSource.emit('Subscribe', 'ref-1');
			});

			expect(mockClassify).toHaveBeenCalledWith('what can you do?');
			expect(mockPostChat).toHaveBeenCalled();
			expect(Liferay.fire).not.toHaveBeenCalledWith(
				'cms:aiAssistant:requestCategorize',
				expect.anything()
			);
		});

		it('renders only the balloon when the categorization event suppresses the user message', async () => {
			const handlers: Record<string, (payload: unknown) => void> = {};

			(Liferay.on as jest.Mock).mockImplementation(
				(name: string, callback: (payload: unknown) => void) => {
					handlers[name] = callback;
				}
			);

			await act(async () => {
				render(<AIAssistantChat {...defaultProps} />);
			});

			await act(async () => {
				handlers['cms:aiAssistant:categorize']({
					agent: 'L_GENERATE_TAGS',
					cmsGroupId: 1,
					content: 'x',
					scopeId: 1,
					suppressUserMessage: true,
					targets: ['kayaking'],
				});
			});

			expect(
				screen.getByText('categorization-balloon')
			).toBeInTheDocument();
			expect(screen.queryByText('generate-tags')).not.toBeInTheDocument();

			(Liferay.on as jest.Mock).mockReset();
		});
	});
});
