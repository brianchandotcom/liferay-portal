/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import ClayForm, {ClayInput, ClayToggle} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayPanel from '@clayui/panel';
import {Provider} from '@clayui/provider';
import {openToast} from '@liferay/object-js-components-web';
import {InputLocalized} from 'frontend-js-components-web';
import React, {useEffect, useRef, useState} from 'react';

import './ChatbotForm.scss';
import {getAgentDefinitions} from '../agent_definition_form/services/AgentDefinitionService';
import Toolbar from '../components/ToolBar';
import {
	deleteChatbotAgentDefinitionRelationship,
	getChatbot,
	postChatbot,
	putChatbot,
	putChatbotAgentDefinitionRelationship,
} from './services/ChatbotService';
import {Chatbot} from './types/Chatbot';

type AgentDefinitionOption = {
	externalReferenceCode: string;
	title: string;
};

function generateEmbedCode(externalReferenceCode: string) {
	return `<script id="${externalReferenceCode}_pid">
  (function() {
    var scriptElement1 = document.createElement("script");

    scriptElement1.async = true;
    scriptElement1.charset = "UTF-8";
    scriptElement1.setAttribute("crossorigin", "*");
    scriptElement1.src = "/o/ai-hub/chatbots/${externalReferenceCode}";

    var scriptElement2 = document.getElementsByTagName("script")[0];

    scriptElement2.parentNode.insertBefore(scriptElement1, scriptElement2);
  })();
</script>`;
}

export default function ChatbotForm({
	accountEntryExternalReferenceCode,
	backURL,
	externalReferenceCode,
}: {
	accountEntryExternalReferenceCode: string;
	backURL: string;
	externalReferenceCode: string;
}) {
	const [formData, setFormData] = useState<Chatbot>({} as Chatbot);
	const [
		existingChatbotExternalReferenceCode,
		setExistingChatbotExternalReferenceCode,
	] = useState(externalReferenceCode);
	const [availableAgentDefinitions, setAvailableAgentDefinitions] = useState<
		AgentDefinitionOption[]
	>([]);
	const [selectedAgentDefinitions, setSelectedAgentDefinitions] = useState<
		AgentDefinitionOption[]
	>([]);
	const [showAgentDefinitionPicker, setShowAgentDefinitionPicker] =
		useState(false);
	const [agentDefinitionSearchQuery, setAgentDefinitionSearchQuery] =
		useState('');

	const agentDefinitionPickerRef = useRef<HTMLDivElement>(null);
	const removedAgentDefinitionERCsRef = useRef<Set<string>>(new Set());

	const handleInputChange = (
		event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
	) => {
		const {name, value} = event.target;

		setFormData((prev) => ({
			...prev,
			[name]: value,
		}));
	};

	const handleRemoveAgentDefinition = (erc: string) => {
		removedAgentDefinitionERCsRef.current.add(erc);
		setSelectedAgentDefinitions((prev) =>
			prev.filter((agent) => agent.externalReferenceCode !== erc)
		);
	};

	const handleAddAgentDefinition = (agent: AgentDefinitionOption) => {
		if (
			!selectedAgentDefinitions.some(
				(a) => a.externalReferenceCode === agent.externalReferenceCode
			)
		) {
			removedAgentDefinitionERCsRef.current.delete(
				agent.externalReferenceCode
			);
			setSelectedAgentDefinitions((prev) => [...prev, agent]);
		}

		setAgentDefinitionSearchQuery('');
		setShowAgentDefinitionPicker(false);
	};

	const handleCopyEmbedCode = () => {
		const code = generateEmbedCode(formData.externalReferenceCode);

		navigator.clipboard.writeText(code).then(() => {
			openToast({
				message: Liferay.Language.get('copied-to-clipboard'),
				type: 'success',
			});
		});
	};

	const handleSubmit = async () => {
		try {
			const payload = {
				...formData,
				r_accountToAIHubChatbots_accountEntryERC:
					accountEntryExternalReferenceCode,
				title:
					formData.title_i18n?.['en_US'] ||
					Object.values(formData.title_i18n ?? {})[0] ||
					'',
			};

			let chatbotExternalReferenceCode =
				existingChatbotExternalReferenceCode;

			if (existingChatbotExternalReferenceCode) {
				await putChatbot(existingChatbotExternalReferenceCode, payload);
			}
			else {
				const created = await postChatbot(payload);

				chatbotExternalReferenceCode = created.externalReferenceCode;

				setExistingChatbotExternalReferenceCode(
					chatbotExternalReferenceCode
				);

				setFormData((prev) => ({
					...prev,
					externalReferenceCode: chatbotExternalReferenceCode,
				}));
			}

			await Promise.all(
				selectedAgentDefinitions.map((agent) =>
					putChatbotAgentDefinitionRelationship(
						chatbotExternalReferenceCode,
						agent.externalReferenceCode
					)
				)
			);

			await Promise.all(
				Array.from(removedAgentDefinitionERCsRef.current).map(
					(agentERC) =>
						deleteChatbotAgentDefinitionRelationship(
							chatbotExternalReferenceCode,
							agentERC
						)
				)
			);

			removedAgentDefinitionERCsRef.current.clear();

			openToast({
				message: Liferay.Language.get('chatbot-saved-successfully'),
				type: 'success',
			});
		}
		catch (error) {
			console.error(error);

			openToast({
				message: Liferay.Language.get('an-unexpected-error-occurred'),
				type: 'danger',
			});
		}
	};

	useEffect(() => {
		async function fetchFormData() {
			if (!externalReferenceCode) {
				setFormData({
					active: false,
					description: '',
					externalReferenceCode: '',
					introMessage_i18n: {},
					notificationMessage_i18n: {},
					placeholderMessage_i18n: {},
					r_accountToAIHubChatbots_accountEntryERC:
						accountEntryExternalReferenceCode,
					showCompanyLogo: true,
					title_i18n: {},
				});

				return;
			}

			try {
				const chatbot = await getChatbot(externalReferenceCode);

				setFormData({
					active: chatbot.active ?? false,
					description: chatbot.description,
					externalReferenceCode: chatbot.externalReferenceCode,
					introMessage_i18n: chatbot.introMessage_i18n,
					notificationMessage_i18n: chatbot.notificationMessage_i18n,
					placeholderMessage_i18n: chatbot.placeholderMessage_i18n,
					r_accountToAIHubChatbots_accountEntryERC:
						chatbot.r_accountToAIHubChatbots_accountEntryERC,
					showCompanyLogo: chatbot.showCompanyLogo,
					title_i18n: chatbot.title_i18n,
				});

				setSelectedAgentDefinitions(
					(chatbot.agentDefinitionsToChatbots ?? []).map(
						(a: {
							externalReferenceCode: string;
							title: string;
						}) => ({
							externalReferenceCode: a.externalReferenceCode,
							title: a.title,
						})
					)
				);
			}
			catch (error) {
				openToast({
					message: Liferay.Language.get(
						'failed-to-load-chatbot-data'
					),
					type: 'danger',
				});
			}
		}

		fetchFormData();
	}, [accountEntryExternalReferenceCode, externalReferenceCode]);

	useEffect(() => {
		async function fetchAgentDefinitions() {
			try {
				const response = await getAgentDefinitions();

				setAvailableAgentDefinitions(
					(response.items || []).map(
						(item: {
							externalReferenceCode: string;
							title: string;
						}) => ({
							externalReferenceCode: item.externalReferenceCode,
							title: item.title,
						})
					)
				);
			}
			catch (error) {
				console.error(error);
			}
		}

		fetchAgentDefinitions();
	}, []);

	useEffect(() => {
		function handleClickOutside(event: MouseEvent) {
			if (
				agentDefinitionPickerRef.current &&
				!agentDefinitionPickerRef.current.contains(event.target as Node)
			) {
				setShowAgentDefinitionPicker(false);
			}
		}

		document.addEventListener('mousedown', handleClickOutside);

		return () => {
			document.removeEventListener('mousedown', handleClickOutside);
		};
	}, []);

	const unselectedAgentDefinitions = availableAgentDefinitions.filter(
		(agent) =>
			!selectedAgentDefinitions.some(
				(agentDefinition) =>
					agentDefinition.externalReferenceCode ===
					agent.externalReferenceCode
			) &&
			agent.title
				.toLowerCase()
				.includes(agentDefinitionSearchQuery.toLowerCase())
	);

	return (
		<>
			<Toolbar
				backURL={backURL}
				title={
					externalReferenceCode
						? Liferay.Language.get('edit-chatbot')
						: Liferay.Language.get('create-chatbot')
				}
			>
				<Toolbar.Item>
					<ClayLink
						aria-label={Liferay.Language.get('cancel')}
						borderless
						button
						displayType="secondary"
						href={backURL}
						small
					>
						{Liferay.Language.get('cancel')}
					</ClayLink>
				</Toolbar.Item>

				<Toolbar.Item>
					<Button
						aria-label={Liferay.Language.get('save')}
						data-title="Save Button"
						data-title-set-as-html
						onClick={handleSubmit}
						size="sm"
					>
						{Liferay.Language.get('save')}
					</Button>
				</Toolbar.Item>
			</Toolbar>

			<ClayLayout.ContainerFluid className="chatbot-form">
				<ClayForm>
					<ClayLayout.Row>
						<ClayLayout.Col md={12}>
							<ClayPanel
								className="chatbot-details"
								collapsable={false}
								title={Liferay.Language.get('details')}
							>
								<ClayPanel.Body>
									<div className="chatbot-details-header">
										<h2>
											{Liferay.Language.get('details')}
										</h2>

										<ClayToggle
											label={Liferay.Language.get(
												'enable-chatbot'
											)}
											name="enabled-toggle"
											onBlur={(
												event: React.FocusEvent<HTMLInputElement>
											) => {
												event.stopPropagation();
											}}
											onToggle={() =>
												setFormData((prev) => ({
													...prev,
													active: !prev.active,
												}))
											}
											toggled={formData.active}
										/>
									</div>

									<ClayForm.Group>
										<InputLocalized
											id="title"
											label={Liferay.Language.get(
												'title'
											)}
											name="title_i18n"
											onChange={(value) =>
												setFormData((prev) => ({
													...prev,
													title_i18n: value,
												}))
											}
											onSelectedLocaleChange={() => {}}
											placeholder={Liferay.Language.get(
												'title'
											)}
											required={true}
											translations={
												(formData.title_i18n as LocalizedValue<string>) ||
												{}
											}
										/>
									</ClayForm.Group>

									<ClayForm.Group>
										<label htmlFor="externalReferenceCode">
											{Liferay.Language.get(
												'external-reference-code'
											)}

											<span className="ml-1 reference-mark text-warning">
												<ClayIcon symbol="asterisk" />
											</span>
										</label>

										<ClayInput
											id="externalReferenceCode"
											name="externalReferenceCode"
											onChange={handleInputChange}
											placeholder={Liferay.Language.get(
												'external-reference-code'
											)}
											required={true}
											type="text"
											value={
												formData.externalReferenceCode ??
												''
											}
										/>
									</ClayForm.Group>

									<ClayForm.Group>
										<label htmlFor="description">
											{Liferay.Language.get(
												'description'
											)}
										</label>

										<textarea
											className="form-control"
											id="description"
											name="description"
											onChange={handleInputChange}
											placeholder={Liferay.Language.get(
												'description'
											)}
											rows={3}
											value={formData.description ?? ''}
										/>
									</ClayForm.Group>

									<ClayForm.Group>
										<label>
											{Liferay.Language.get(
												'assigned-agents'
											)}
										</label>

										<div className="chatbot-assigned-agents">
											{selectedAgentDefinitions.map(
												(agent) => (
													<span
														className="chatbot-agent-tag"
														key={
															agent.externalReferenceCode
														}
													>
														{agent.title}

														<button
															className="chatbot-agent-tag-remove"
															onClick={() =>
																handleRemoveAgentDefinition(
																	agent.externalReferenceCode
																)
															}
															type="button"
														>
															<Provider
																spritemap={
																	Liferay
																		.Icons
																		.spritemap
																}
															>
																<ClayIcon symbol="times" />
															</Provider>
														</button>
													</span>
												)
											)}

											<div
												className="chatbot-agent-picker-wrapper"
												ref={agentDefinitionPickerRef}
											>
												<input
													className="chatbot-agent-search"
													onChange={(event) => {
														setAgentDefinitionSearchQuery(
															event.target.value
														);
														setShowAgentDefinitionPicker(
															true
														);
													}}
													onFocus={() =>
														setShowAgentDefinitionPicker(
															true
														)
													}
													placeholder={
														!selectedAgentDefinitions.length
															? Liferay.Language.get(
																	'search-agents'
																)
															: ''
													}
													type="text"
													value={
														agentDefinitionSearchQuery
													}
												/>

												{showAgentDefinitionPicker &&
													!!unselectedAgentDefinitions.length && (
														<div className="chatbot-agent-picker">
															{unselectedAgentDefinitions.map(
																(agent) => (
																	<button
																		className="chatbot-agent-picker-item"
																		key={
																			agent.externalReferenceCode
																		}
																		onClick={() =>
																			handleAddAgentDefinition(
																				agent
																			)
																		}
																		type="button"
																	>
																		{
																			agent.title
																		}
																	</button>
																)
															)}
														</div>
													)}
											</div>
										</div>
									</ClayForm.Group>

									<ClayForm.Group>
										<InputLocalized
											id="notificationMessage"
											label={Liferay.Language.get(
												'notification-message'
											)}
											name="notificationMessage_i18n"
											onChange={(value) =>
												setFormData((prev) => ({
													...prev,
													notificationMessage_i18n:
														value,
												}))
											}
											onSelectedLocaleChange={() => {}}
											placeholder={Liferay.Language.get(
												'notification-message'
											)}
											translations={
												(formData.notificationMessage_i18n as LocalizedValue<string>) ||
												{}
											}
										/>
									</ClayForm.Group>

									<ClayForm.Group>
										<InputLocalized
											id="placeholderMessage"
											label={Liferay.Language.get(
												'placeholder-message'
											)}
											name="placeholderMessage_i18n"
											onChange={(value) =>
												setFormData((prev) => ({
													...prev,
													placeholderMessage_i18n:
														value,
												}))
											}
											onSelectedLocaleChange={() => {}}
											placeholder={Liferay.Language.get(
												'placeholder-message'
											)}
											translations={
												(formData.placeholderMessage_i18n as LocalizedValue<string>) ||
												{}
											}
										/>
									</ClayForm.Group>

									<ClayForm.Group>
										<InputLocalized
											id="introMessage"
											label={Liferay.Language.get(
												'intro-message'
											)}
											name="introMessage_i18n"
											onChange={(value) =>
												setFormData((prev) => ({
													...prev,
													introMessage_i18n: value,
												}))
											}
											onSelectedLocaleChange={() => {}}
											placeholder={Liferay.Language.get(
												'intro-message'
											)}
											translations={
												(formData.introMessage_i18n as LocalizedValue<string>) ||
												{}
											}
										/>
									</ClayForm.Group>

									<ClayToggle
										label={Liferay.Language.get(
											'show-company-logo'
										)}
										onBlur={(
											event: React.FocusEvent<HTMLInputElement>
										) => {
											event.stopPropagation();
										}}
										onToggle={() =>
											setFormData((prev) => ({
												...prev,
												showCompanyLogo:
													!prev.showCompanyLogo,
											}))
										}
										toggled={formData.showCompanyLogo}
									/>
								</ClayPanel.Body>
							</ClayPanel>

							<div className="chatbot-code-card">
								<div className="chatbot-code-header">
									<strong>
										{Liferay.Language.get('chatbot-code')}
									</strong>

									<Provider
										spritemap={Liferay.Icons.spritemap}
									>
										<ClayIcon
											className="chatbot-help-icon"
											symbol="question-circle"
										/>
									</Provider>

									<button
										className="chatbot-code-copy"
										onClick={handleCopyEmbedCode}
										type="button"
									>
										<Provider
											spritemap={Liferay.Icons.spritemap}
										>
											<ClayIcon symbol="copy" />
										</Provider>
									</button>
								</div>

								<ClayForm.Group>
									<label>
										{Liferay.Language.get('description')}
									</label>

									<textarea
										className="chatbot-code-textarea form-control"
										readOnly
										value={
											formData.externalReferenceCode
												? generateEmbedCode(
														formData.externalReferenceCode
													)
												: ''
										}
									/>
								</ClayForm.Group>
							</div>
						</ClayLayout.Col>
					</ClayLayout.Row>
				</ClayForm>
			</ClayLayout.ContainerFluid>
		</>
	);
}
