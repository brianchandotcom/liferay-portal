/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput, ClayToggle} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayMultiSelect from '@clayui/multi-select';
import ClayPanel from '@clayui/panel';
import React, {useEffect, useRef, useState} from 'react';

import './AgentDefinitionForm.scss';

import Button from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import Icon from '@clayui/icon';
import Link from '@clayui/link';
import {Provider} from '@clayui/provider';
import {openToast} from '@liferay/object-js-components-web';
import {InputLocalized} from 'frontend-js-components-web';

import Toolbar from '../components/ToolBar';
import {
	deleteAgentDefinitionToContentRetrievers,
	getAgentDefinition,
	putAgentDefinition,
	putAgentDefinitionToContentRetrievers,
} from './services/AgentDefinitionService';
import {getContentRetrievers} from './services/ContentRetrieverService';
import {
	getWorkflowDefinition,
	getWorkflowDefinitions,
} from './services/WorkflowDefinitionService';
import {AgentDefinition} from './types/AgentDefinition';
import {ContentRetriever} from './types/ContentRetriever';
import {WorkflowDefinition} from './types/WorkflowDefinition';

const contentRetrieversList = await (async () => {
	const response = await getContentRetrievers();

	return response.items || [];
})();

export default function AgentDefinitionForm({
	accountEntryExternalReferenceCode,
	backURL,
	externalReferenceCode,
	readonly,
	workflowDefinitionURL,
}: {
	accountEntryExternalReferenceCode: string;
	backURL: string;
	externalReferenceCode: string;
	readonly: boolean;
	workflowDefinitionURL: string;
}) {
	const [formData, setFormData] = useState<AgentDefinition>(
		{} as AgentDefinition
	);
	const [workflowDefinitions, setWorkflowDefinitions] = useState<
		WorkflowDefinition[]
	>([]);

	const [contentRetrievers, setContentRetrievers] = useState<
		ContentRetriever[]
	>([]);

	const initialContentRetrieversRef = useRef<ContentRetriever[]>([]);

	const [dataSourceValue, setDataSourceValue] = useState('');

	const syncRelationships = async (agentDefinitionERC: string) => {
		const initialERCs = new Set(
			initialContentRetrieversRef.current.map(
				(contentRetriever) => contentRetriever.externalReferenceCode
			)
		);
		const currentERCs = new Set(
			contentRetrievers.map(
				(contentRetriever) => contentRetriever.externalReferenceCode
			)
		);

		const toAdd = contentRetrievers.filter(
			(contentRetriever) =>
				!initialERCs.has(contentRetriever.externalReferenceCode)
		);
		const toRemove = initialContentRetrieversRef.current.filter(
			(contentRetriever) =>
				!currentERCs.has(contentRetriever.externalReferenceCode)
		);

		const requests = [
			...toAdd.map((contentRetriever) =>
				putAgentDefinitionToContentRetrievers(
					agentDefinitionERC,
					contentRetriever.externalReferenceCode
				)
			),
			...toRemove.map((contentRetriever) =>
				deleteAgentDefinitionToContentRetrievers(
					agentDefinitionERC,
					contentRetriever.externalReferenceCode
				)
			),
		];

		if (requests.length) {
			await Promise.all(requests);
			initialContentRetrieversRef.current = [...contentRetrievers];
		}
	};

	const handleActive = () => {
		setFormData((prev) => ({
			...prev,
			active: !prev.active,
		}));
	};

	const handleInputChange = (
		event: React.ChangeEvent<
			HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement
		>
	) => {
		const {name, value} = event.target;
		setFormData((prev) => ({
			...prev,
			[name]: value,
		}));
	};

	const handleViewWorkflow = () => {
		window.location.href = workflowDefinitionURL;
	};

	const handleSubmit = async () => {
		try {
			const response = await putAgentDefinition(formData);

			if (formData.externalReferenceCode) {
				await syncRelationships(formData.externalReferenceCode);
			}

			if (response?.status?.label === 'approved') {
				openToast({
					message: Liferay.Language.get('agent-saved-successfully'),
					type: 'success',
				});
			}
			else {
				openToast({
					message: Liferay.Language.get('failed-to-save-agent'),
					type: 'danger',
				});
			}
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
					inputVariables: '',
					outputVariable: '',
					r_accountToAIHubAgentDefinitions_accountEntryERC:
						accountEntryExternalReferenceCode,
					title_i18n: {},
					workflowDefinitionName: '',
				});

				return;
			}

			try {
				const agentDefinition = await getAgentDefinition(
					externalReferenceCode
				);

				setFormData({
					active: agentDefinition.active,
					description: agentDefinition.description,
					externalReferenceCode:
						agentDefinition.externalReferenceCode,
					inputVariables: agentDefinition.inputVariables,
					outputVariable: agentDefinition.outputVariable,
					r_accountToAIHubAgentDefinitions_accountEntryERC:
						agentDefinition.r_accountToAIHubAgentDefinitions_accountEntryERC,
					title_i18n: agentDefinition.title_i18n,
					workflowDefinitionName:
						agentDefinition.workflowDefinitionName,
				});

				setContentRetrievers(
					agentDefinition.agentDefinitionsToContentRetrievers
				);

				initialContentRetrieversRef.current =
					agentDefinition.agentDefinitionsToContentRetrievers;
			}
			catch (error) {
				openToast({
					message: Liferay.Language.get('failed-to-load-agent-data'),
					type: 'danger',
				});
			}
		}

		fetchFormData();
	}, [accountEntryExternalReferenceCode, externalReferenceCode, readonly]);

	useEffect(() => {
		async function fetchWorkflowDefinitions() {
			try {
				if (readonly) {
					const response = await getWorkflowDefinition(
						formData.workflowDefinitionName
					);

					setWorkflowDefinitions([response]);

					return;
				}
				const response = await getWorkflowDefinitions();

				setWorkflowDefinitions(response.items || []);
			}
			catch (error) {
				console.error(error);

				openToast({
					message: Liferay.Language.get(
						'failed-to-load-workflow-definitions'
					),
					type: 'danger',
				});
			}
		}

		fetchWorkflowDefinitions();
	}, [formData.workflowDefinitionName, readonly]);

	return (
		<>
			<Toolbar
				backURL={backURL}
				title={Liferay.Language.get('create-agent')}
			>
				<Toolbar.Item>
					<Link
						aria-label={Liferay.Language.get('cancel')}
						borderless
						button
						displayType="secondary"
						href={backURL}
						small
					>
						{Liferay.Language.get('cancel')}
					</Link>
				</Toolbar.Item>

				<Toolbar.Item>
					<Button
						aria-labelledby="saveButton"
						data-title="Save Button"
						data-title-set-as-html
						disabled={readonly}
						onClick={handleSubmit}
						size="sm"
					>
						{Liferay.Language.get('save')}
					</Button>
				</Toolbar.Item>
			</Toolbar>

			<ClayLayout.ContainerFluid className="agent-definition-form">
				<ClayForm>
					<div className="agent-definition-header">
						<ClayToggle
							disabled={readonly}
							label={Liferay.Language.get('enable-agent')}
							name="active-toggle"
							onBlur={(
								event: React.FocusEvent<HTMLInputElement>
							) => {
								event.stopPropagation();
							}}
							onToggle={handleActive}
							toggled={formData.active}
						/>

						<Provider spritemap={Liferay.Icons.spritemap}>
							<Button
								displayType="secondary"
								onClick={handleViewWorkflow}
							>
								<span className="inline-item inline-item-before">
									<Icon symbol="icon-rule-builder" />
								</span>

								{Liferay.Language.get('view-workflow')}
							</Button>
						</Provider>
					</div>

					<ClayLayout.Row>
						<ClayLayout.Col md={12}>
							<ClayPanel
								className="agent-definition-details"
								collapsable={false}
								title={Liferay.Language.get('details')}
							>
								<ClayPanel.Body>
									<h2>{Liferay.Language.get('details')}</h2>

									<ClayForm.Group>
										<InputLocalized
											disabled={readonly}
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
												(formData.title_i18n as Record<
													string,
													string
												>) || {}
											}
										/>
									</ClayForm.Group>

									<ClayForm.Group>
										<label htmlFor="externalReferenceCode">
											{Liferay.Language.get(
												'external-reference-code'
											)}

											<span className="ml-1 reference-mark text-warning">
												<Icon symbol="asterisk" />
											</span>
										</label>

										<ClayInput
											disabled={readonly}
											id="externalReferenceCode"
											name="externalReferenceCode"
											onChange={handleInputChange}
											placeholder={Liferay.Language.get(
												'external-reference-code'
											)}
											required={true}
											type="text"
											value={
												formData.externalReferenceCode
											}
										/>
									</ClayForm.Group>

									<ClayForm.Group>
										<label htmlFor="description">
											{Liferay.Language.get(
												'description'
											)}

											<span className="ml-1 reference-mark text-warning">
												<Icon symbol="asterisk" />
											</span>
										</label>

										<textarea
											className="form-control"
											disabled={readonly}
											id="description"
											name="description"
											onChange={handleInputChange}
											placeholder={Liferay.Language.get(
												'add-a-description'
											)}
											rows={4}
											value={formData.description}
										/>
									</ClayForm.Group>

									<ClayForm.Group>
										<label htmlFor="input-variables">
											{Liferay.Language.get(
												'input-variables'
											)}

											<span className="ml-1 reference-mark text-warning">
												<Icon symbol="asterisk" />
											</span>
										</label>

										<ClayInput
											disabled={readonly}
											id="input-variables"
											name="inputVariables"
											onChange={handleInputChange}
											placeholder="inputVariable1,inputVariable2"
											required={true}
											type="text"
											value={formData.inputVariables}
										/>
									</ClayForm.Group>

									<ClayForm.Group>
										<label htmlFor="output-variable">
											{Liferay.Language.get(
												'output-variable'
											)}

											<span className="ml-1 reference-mark text-warning">
												<Icon symbol="asterisk" />
											</span>
										</label>

										<ClayInput
											disabled={readonly}
											id="output-variable"
											name="outputVariable"
											onChange={handleInputChange}
											placeholder="outputVariable"
											required={true}
											type="text"
											value={formData.outputVariable}
										/>
									</ClayForm.Group>

									<ClayForm.Group>
										<label htmlFor="workflowDefinitionName">
											{Liferay.Language.get(
												'workflow-definition'
											)}

											<span className="ml-1 reference-mark text-warning">
												<Icon symbol="asterisk" />
											</span>
										</label>

										<Picker
											className="agent-workflow-definition"
											disabled={readonly}
											items={workflowDefinitions.map(
												(workflowDefinition) => ({
													label: workflowDefinition.title,
													value: workflowDefinition.name,
												})
											)}
											onSelectionChange={(value) => {
												setFormData((prev) => ({
													...prev,
													workflowDefinitionName:
														value as string,
												}));
											}}
											placeholder={Liferay.Language.get(
												'workflow-definition'
											)}
											required={true}
											selectedKey={
												formData.workflowDefinitionName
											}
										>
											{({label, value}) => (
												<Option key={value}>
													{label}
												</Option>
											)}
										</Picker>
									</ClayForm.Group>
								</ClayPanel.Body>
							</ClayPanel>
						</ClayLayout.Col>
					</ClayLayout.Row>

					<ClayLayout.Row>
						<ClayLayout.Col md={12}>
							<ClayPanel
								className="agent-definition-details"
								collapsable={false}
								title={Liferay.Language.get('data-sources')}
							>
								<ClayPanel.Body>
									<h2>
										{Liferay.Language.get('data-sources')}
									</h2>

									<label htmlFor="assignedSources">
										{Liferay.Language.get(
											'assigned-sources'
										)}
									</label>

									<ClayMultiSelect
										allowDuplicateValues={false}
										allowsCustomLabel={false}
										disabled={readonly}
										inputName="assignedSources"
										items={contentRetrievers}
										locator={{
											label: 'title',
											value: 'externalReferenceCode',
										}}
										onChange={setDataSourceValue}
										onItemsChange={(items) =>
											setContentRetrievers(items)
										}
										sourceItems={contentRetrieversList}
										value={dataSourceValue}
									/>
								</ClayPanel.Body>
							</ClayPanel>
						</ClayLayout.Col>
					</ClayLayout.Row>
				</ClayForm>
			</ClayLayout.ContainerFluid>
		</>
	);
}
