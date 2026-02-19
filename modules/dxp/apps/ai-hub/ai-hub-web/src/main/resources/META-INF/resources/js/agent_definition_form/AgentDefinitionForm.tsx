/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput, ClayToggle} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import React, {useEffect, useState} from 'react';

import './AgentDefinitionForm.scss';

import Button from '@clayui/button';
import Icon from '@clayui/icon';
import Link from '@clayui/link';
import {Provider} from '@clayui/provider';
import {openToast} from '@liferay/object-js-components-web';
import {InputLocalized} from 'frontend-js-components-web';

import Toolbar from './components/ToolBar';
import {
	getAgentDefinition,
	putAgentDefinition,
} from './services/AgentDefinitionService';
import {AgentDefinition} from './types/AgentDefinition';

export default function AgentDefinitionForm({
	backURL,
	externalReferenceCode,
	workflowDefinitionURL,
}: {
	backURL: string;
	externalReferenceCode: string;
	workflowDefinitionURL: string;
}) {
	const [formData, setFormData] = useState<AgentDefinition>(
		{} as AgentDefinition
	);

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
		async function fetchAgentDefinition() {
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
					title_i18n: agentDefinition.title_i18n,
					workflowDefinitionName:
						agentDefinition.workflowDefinitionName,
				});
			}
			catch (error) {
				openToast({
					message: Liferay.Language.get('failed-to-load-agent-data'),
					type: 'danger',
				});
			}
		}

		if (externalReferenceCode) {
			fetchAgentDefinition();
		}
	}, [externalReferenceCode]);

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
						href="/web/ai-hub/agents"
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
												<Icon symbol="asterisk" />
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

										<ClayInput
											id="workflowDefinitionName"
											name="workflowDefinitionName"
											onChange={handleInputChange}
											placeholder={Liferay.Language.get(
												'workflow-definition'
											)}
											required={true}
											type="text"
											value={
												formData.workflowDefinitionName
											}
										/>
									</ClayForm.Group>
								</ClayPanel.Body>
							</ClayPanel>
						</ClayLayout.Col>
					</ClayLayout.Row>
				</ClayForm>
			</ClayLayout.ContainerFluid>
		</>
	);
}
