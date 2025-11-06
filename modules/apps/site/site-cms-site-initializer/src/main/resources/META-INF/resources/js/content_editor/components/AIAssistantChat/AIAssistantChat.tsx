/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {EventSource} from 'eventsource';
import React, {useEffect, useRef, useState} from 'react';

import {createEventSource, postChatByExternalReferenceCodeMessage} from './api';
import AIAssistantMessageBalloon from './components/AIAssistantMessageBalloon';
import UserMessageBalloon from './components/UserMessageBalloon';

import './chat.scss';

interface message {
	sender: string;
	text: string;
}

const AIAssistantChat: React.FC = () => {
	const [active, setActive] = useState<boolean>(false);
	const [isGenerating, setIsGenerating] = useState<boolean>(false);
	const [messages, setMessages] = useState<message[]>([]);
	const [message, setMessage] = useState<string>('');
	const [quickActions, setQuickActions] = useState<string[]>([
		'Generate Content',
		'Generate Title',
		'Translate',
	]);
	const eventSourceRef = useRef<EventSource | null>(null);
	const eventSourceReference = useRef<string | null>(null);
	const messagesEndRef = useRef<HTMLDivElement | null>(null);
	const triggerRef = useRef<HTMLButtonElement | null>(null);
	const textareaRef = useRef<HTMLTextAreaElement | null>(null);

	function onSubmit(event: React.FormEvent<HTMLFormElement>) {
		event.preventDefault();
		if (!message.trim()) {
			return;
		}
		setMessages((previousMessages) => {
			setTimeout(() => {
				messagesEndRef.current?.scrollIntoView({behavior: 'smooth'});
			}, 0);

			return [...previousMessages, { sender: 'user', text: message }];
		});

		setMessage('');

		setIsGenerating(true);

		const contextElements = getContextElements();

		if (eventSourceReference.current) {
			const content = contextElements['ObjectField_content_en_US'];
			const title = contextElements['ObjectField_title'];

			postChatByExternalReferenceCodeMessage(
				content?.value,
				eventSourceReference.current,
				message,
				title.value
			);
		}
	}

	function adjustTextareaHeight(element: HTMLTextAreaElement) {
		const textarea = element ?? textareaRef.current;

		if (!textarea) {
			return;
		}
		
		const style = window.getComputedStyle(textarea);
		const lineHeight = parseFloat(style.lineHeight) || parseFloat(style.fontSize) * 1.2;
		const maxHeight = lineHeight * 4;

		textarea.style.height = 'auto';
		const newHeight = Math.min(textarea.scrollHeight, maxHeight);
		textarea.style.height = `${newHeight}px`;
		textarea.style.overflowY = textarea.scrollHeight > maxHeight ? 'auto' : 'hidden';
	}

	function getContextElements() {
		let form = document.querySelector('.lfr-main-form-container');

		if (!form) {
			form = document.querySelector('.lfr-layout-structure-item-form');
		}

		if (!form) {
			return [];
		}

		return Array.from(
			form.querySelectorAll('[name^="ObjectField_"]')
		).reduce((inputs: any, element: any) => {
			if (element.name) {
				inputs[element.name] = element;
			}

			return inputs;
		}, {});
	}

	function openAIAssistantChatConnection() {
		eventSourceRef.current = createEventSource();

		eventSourceRef.current.addEventListener(
			'Chat Message Sent',
			(event) => {
				setMessages((previousMessages) => {
					setTimeout(() => {
						messagesEndRef.current?.scrollIntoView({
							behavior: 'smooth',
						});
					}, 0);

					const dataJSON = JSON.parse(event.data);

					return [
						...previousMessages,
						{
							sender: 'assistant',
							text: dataJSON['data'],
						},
					];
				});

				setMessage('');

				setIsGenerating(false);
			}
		);

		eventSourceRef.current.addEventListener('Subscribe', (event) => {
			eventSourceReference.current = event.data;
		});
	}

	function closeAIAssistantChatConnection() {
		eventSourceRef.current?.close();

		eventSourceRef.current = null;
	}

	useEffect(() => {
		openAIAssistantChatConnection();

		return () => {
			closeAIAssistantChatConnection();
		};
	}, []);

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={4}
			className="d-flex p-0"
			hasRightSymbols={false}
			menuElementAttrs={{
				style: {
					height: 552,
					maxHeight: 'none',
					maxWidth: 'none',
					overflow: 'hidden',
					width: 448,
				},
			}}
			onActiveChange={setActive}
			trigger={
				<ClayButton
					aria-label={Liferay.Language.get('ai-assistant')}
					borderless
					className="text-primary"
					displayType="secondary"
					ref={triggerRef}
				>
					<ClayIcon
						className="mr-2"
						height={16}
						spritemap={Liferay.Icons.spritemap}
						symbol="stars"
						width={16}
					/>

					{Liferay.Language.get('ai-assistant')}
				</ClayButton>
			}
		>
			<div className="ai-assistant-chat__dropdown-container d-flex flex-column">
				<div className="flex-shrink-0 p-3">
					<ClayLayout.ContentRow className="align-items-center border-bottom justify-content-between mb-3 pb-2">
						<ClayLayout.ContentCol className="ai-assistant-chat__dropdown-title font-weight-semi-bold">
							{Liferay.Language.get('ai-assistant')}
						</ClayLayout.ContentCol>

						<ClayLayout.ContentCol>
							<ClayButton
								aria-label={Liferay.Language.get('close')}
								borderless
								displayType="unstyled"
								onClick={() => setActive(false)}
							>
								<ClayIcon
									className="ai-assistant-chat__dropdown-close-button"
									spritemap={Liferay.Icons.spritemap}
									symbol="times"
								/>
							</ClayButton>
						</ClayLayout.ContentCol>
					</ClayLayout.ContentRow>
				</div>

				<div className="ai-assistant-chat__messages-container flex-grow-1 overflow-auto px-3">
					<AIAssistantMessageBalloon message="Hi! I can help you generate content, titles, tags, or translate your work. What would you like to do?" />

					{messages.map((item, index) =>
						item.sender === 'user' ? (
							<UserMessageBalloon
								key={index}
								message={item.text}
							/>
						) : (
							<AIAssistantMessageBalloon
								key={index}
								message={item.text}
							/>
						)
					)}

					{isGenerating && (
						<div className="ai-assistant-chat-balloon d-flex flex-row mb-2 rounded">
							<div className="align-items-center d-flex ml-2">
								<ClayLoadingIndicator />
							</div>

							<span className="ai-assistant-chat__generating-loading-text font-weight-semi-bold m-2 tex">
								Generating...
							</span>
						</div>
					)}

					<div ref={messagesEndRef} />
				</div>

				<ClayForm
					className="flex-shrink-0 p-3"
					onSubmit={(event) => onSubmit(event)}
				>
					{!messages.length && (
						<>
							{Liferay.Language.get('quick-actions')}
							<ClayLayout.ContentRow className="align-items-center mb-3 mt-2">	
								{quickActions.map((action, index) => (
									<QuickAction action={action} key={index} />
								))}
							</ClayLayout.ContentRow>
						</>
					)}

					<div className="align-items-end border-top d-flex flex-row mb-4 pt-4">
						<textarea
							className="ai-assistant-chat__input form-control mr-2"
							id='assistant-user-input'
							onChange={(event) => {
								setMessage(event.target.value);
								adjustTextareaHeight(event.target);
							}}
							onKeyDown={(event: React.KeyboardEvent<HTMLTextAreaElement>) => {
								event.stopPropagation();
							}}
							placeholder="Ask me anything..."
							rows={1}
							value={message}
						/>

						<ClayButton displayType="primary" type="submit">
							<ClayIcon
								height={12}
								spritemap={Liferay.Icons.spritemap}
								symbol={isGenerating ? "square" : "order-arrow-up"}
								width={12}
							/>
						</ClayButton>
					</div>
				</ClayForm>
			</div>
		</ClayDropDown>
	);
};

export default AIAssistantChat;
