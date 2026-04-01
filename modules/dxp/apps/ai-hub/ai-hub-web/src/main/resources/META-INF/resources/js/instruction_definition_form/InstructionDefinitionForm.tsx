/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput, ClayToggle} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import React, {useEffect, useState} from 'react';

import './InstructionDefinitionForm.scss';

import Button from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import Icon from '@clayui/icon';
import Link from '@clayui/link';
import {openToast} from '@liferay/object-js-components-web';
import {InputLocalized} from 'frontend-js-components-web';

import Toolbar from '../components/ToolBar';
import {
	getInstructionDefinition,
	putInstructionDefinition,
} from './services/InstructionDefinitionService';
import {
	ListTypeEntry,
	getListTypeEntries,
} from './services/ListTypeDefinitionService';
import {InstructionDefinition} from './types/InstructionDefinition';

export default function InstructionDefinitionForm({
	accountEntryExternalReferenceCode,
	backURL,
	externalReferenceCode,
}: {
	accountEntryExternalReferenceCode: string;
	backURL: string;
	externalReferenceCode: string;
}) {
	const [formData, setFormData] = useState<InstructionDefinition>(
		{} as InstructionDefinition
	);
	const [scopeOptions, setScopeOptions] = useState<ListTypeEntry[]>([]);

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

	const handleSubmit = async () => {
		try {
			const response = await putInstructionDefinition(formData);

			if (response?.externalReferenceCode) {
				openToast({
					message: Liferay.Language.get(
						'instruction-saved-successfully'
					),
					type: 'success',
				});
			}
			else {
				openToast({
					message: Liferay.Language.get('failed-to-save-instruction'),
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
					instruction: '',
					occasion: '',
					r_accountToAIHubInstructionDefinitions_accountEntryERC:
						accountEntryExternalReferenceCode,
					scope: '',
					title_i18n: {},
				});

				return;
			}

			try {
				const instructionDefinition = await getInstructionDefinition(
					externalReferenceCode
				);

				setFormData({
					active: instructionDefinition.active,
					description: instructionDefinition.description,
					externalReferenceCode:
						instructionDefinition.externalReferenceCode,
					instruction: instructionDefinition.instruction,
					occasion: instructionDefinition.occasion || '',
					r_accountToAIHubInstructionDefinitions_accountEntryERC:
						instructionDefinition.r_accountToAIHubInstructionDefinitions_accountEntryERC,
					scope: instructionDefinition.scope?.key || '',
					title_i18n: instructionDefinition.title_i18n,
				});
			}
			catch (error) {
				openToast({
					message: Liferay.Language.get(
						'failed-to-load-instruction-data'
					),
					type: 'danger',
				});
			}
		}

		fetchFormData();
	}, [accountEntryExternalReferenceCode, externalReferenceCode]);

	useEffect(() => {
		async function fetchScopeOptions() {
			try {
				const response = await getListTypeEntries(
					'L_AI_HUB_INSTRUCTION_DEFINITION_SCOPES'
				);

				setScopeOptions(response.items || []);
			}
			catch (error) {
				console.error(error);

				openToast({
					message: Liferay.Language.get(
						'failed-to-load-scope-options'
					),
					type: 'danger',
				});
			}
		}

		fetchScopeOptions();
	}, []);

	return (
		<>
			<Toolbar
				backURL={backURL}
				title={
					externalReferenceCode
						? Liferay.Language.get('edit-instruction')
						: Liferay.Language.get('create-instruction')
				}
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

			<ClayLayout.ContainerFluid className="instruction-definition-form">
				<ClayForm>
					<ClayLayout.Row>
						<ClayLayout.Col md={12}>
							<ClayPanel
								className="instruction-definition-form-details"
								collapsable={false}
								title={Liferay.Language.get('details')}
							>
								<ClayPanel.Body>
									<div className="instruction-definition-form-header">
										<h2>
											{Liferay.Language.get('details')}
										</h2>

										<ClayToggle
											label={Liferay.Language.get(
												'enable-instruction'
											)}
											name="enabled-toggle"
											onBlur={(
												event: React.FocusEvent<HTMLInputElement>
											) => {
												event.stopPropagation();
											}}
											onToggle={handleActive}
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
										</label>

										<textarea
											className="form-control"
											id="description"
											name="description"
											onChange={handleInputChange}
											placeholder={Liferay.Language.get(
												'add-a-description'
											)}
											rows={3}
											value={formData.description}
										/>
									</ClayForm.Group>

									<ClayForm.Group>
										<label htmlFor="instruction">
											{Liferay.Language.get(
												'instruction'
											)}

											<span className="ml-1 reference-mark text-warning">
												<Icon symbol="asterisk" />
											</span>
										</label>

										<textarea
											className="form-control"
											id="instruction"
											name="instruction"
											onChange={handleInputChange}
											placeholder={Liferay.Language.get(
												'add-an-instruction'
											)}
											rows={6}
											value={formData.instruction}
										/>
									</ClayForm.Group>

									<ClayForm.Group>
										<label htmlFor="scope">
											{Liferay.Language.get(
												'where-to-use'
											)}

											<span className="ml-1 reference-mark text-warning">
												<Icon symbol="asterisk" />
											</span>
										</label>

										<Picker
											className="instruction-definition-form-scope-field"
											items={scopeOptions.map(
												(option) => ({
													label:
														option.name_i18n?.[
															Liferay.ThemeDisplay.getDefaultLanguageId()
														] || option.name,
													value: option.key,
												})
											)}
											onSelectionChange={(value) => {
												setFormData((prev) => ({
													...prev,
													scope: value as string,
												}));
											}}
											placeholder={Liferay.Language.get(
												'select-an-option'
											)}
											required={true}
											selectedKey={formData.scope}
										>
											{({label, value}) => (
												<Option key={value}>
													{label}
												</Option>
											)}
										</Picker>
									</ClayForm.Group>

									<ClayForm.Group>
										<label htmlFor="occasion">
											{Liferay.Language.get(
												'when-to-use'
											)}

											<span
												className="ml-1"
												data-tooltip-align="bottom"
												title={Liferay.Language.get(
													'specify-when-to-apply-this-instruction'
												)}
											>
												<Icon symbol="question-circle-full" />
											</span>
										</label>

										<textarea
											className="form-control"
											id="occasion"
											name="occasion"
											onChange={handleInputChange}
											rows={2}
											value={formData.occasion}
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
