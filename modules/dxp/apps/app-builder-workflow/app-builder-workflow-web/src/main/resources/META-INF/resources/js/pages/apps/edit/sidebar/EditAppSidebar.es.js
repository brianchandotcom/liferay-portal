/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import EditAppContext from 'app-builder-web/js/pages/apps/edit/EditAppContext.es';
import classNames from 'classnames';
import {sub} from 'data-engine-js-components-web/js/utils/lang.es';
import {Sidebar} from 'data-engine-taglib';
import React, {useContext, useEffect, useState} from 'react';

import AutocompleteMultiSelect from '../../../../components/autocomplete/AutocompleteMultiSelect.es';
import ButtonInfo from '../../../../components/button-info/ButtonInfo.es';
import MissingRequiredFieldsPopover from '../MissingRequiredFieldsPopover.es';
import {REMOVE_STEP_EMPTY_FORM_VIEWS, UPDATE_STEP} from '../configReducer.es';
import ActionsTab from './ActionsTab.es';
import DataAndViewsTab from './DataAndViewsTab.es';

export default function EditAppSidebar() {
	const editAppContext = useContext(EditAppContext);

	const {
		appId,
		config: {
			currentStep,
			dataObject,
			formView,
			listItems: {assigneeRoles},
			stepIndex,
			steps,
			tableView,
		},
		dispatchConfig,
		openFormViewModal,
		state: {app},
		updateFormView,
	} = editAppContext;
	const [currentTab, setCurrentTab] = useState();
	const [showPopover, setShowPopover] = useState(false);

	const {
		appWorkflowTransitions: [primaryAction, secondaryAction] = [],
		appWorkflowDataLayoutLinks = [{}],
	} = currentStep;
	const actionsInfo = [];
	const {missingRequiredFields: {customField, nativeField} = {}} = formView;

	if (primaryAction) {
		actionsInfo.push({
			label: Liferay.Language.get('primary-action'),
			name: `${primaryAction.name} → ${primaryAction.transitionTo}`,
		});
	}

	if (secondaryAction) {
		actionsInfo.push({
			label: Liferay.Language.get('secondary-action'),
			name: `${secondaryAction.name} → ${secondaryAction.transitionTo}`,
		});
	}

	const tabs = [
		{
			content: DataAndViewsTab,
			disabled: stepIndex > 0 && !dataObject.id,
			error:
				stepIndex > 0 &&
				stepIndex < steps.length - 1 &&
				steps[stepIndex].errors.formViews.duplicatedFields.length > 0,
			infoItems:
				stepIndex === 0
					? [
							{
								...dataObject,
								label: Liferay.Language.get('data-object'),
							},
							{
								...formView,
								label: Liferay.Language.get('form-view'),
							},
							{
								...tableView,
								label: Liferay.Language.get('table-view'),
							},
					  ]
					: [
							{
								label: Liferay.Language.get('form-view'),
								name:
									appWorkflowDataLayoutLinks.length > 0
										? appWorkflowDataLayoutLinks
												.map(({name}) => name)
												.reduce(
													(acc, cur) =>
														`${acc}, ${cur}`
												)
										: '',
							},
					  ],
			onClickBack: () => {
				if (stepIndex > 0) {
					dispatchConfig({
						stepIndex,
						type: REMOVE_STEP_EMPTY_FORM_VIEWS,
					});
				}
			},
			show: stepIndex !== steps.length - 1,
			showPopoverIcon:
				!app.active &&
				appId &&
				stepIndex == 0 &&
				(customField || nativeField),
			title: Liferay.Language.get('data-and-views'),
		},
		{
			content: ActionsTab,
			infoItems: actionsInfo,
			show: stepIndex !== steps.length - 1,
			title: Liferay.Language.get('actions'),
		},
	];

	const onChangeAssignees = (assignees) => {
		dispatchConfig({
			step: {
				...currentStep,
				appWorkflowRoleAssignments: assignees.map(({id, name}) => ({
					roleId: id,
					roleName: name,
				})),
			},
			stepIndex,
			type: UPDATE_STEP,
		});
	};

	const onChangeStepName = ({target}) => {
		dispatchConfig({
			step: {...currentStep, name: target.value},
			stepIndex,
			type: UPDATE_STEP,
		});
	};

	const onClick = () => {
		setCurrentTab(null);
		currentTab.onClickBack?.();
	};

	useEffect(() => {
		setCurrentTab(null);
	}, [currentStep]);

	return (
		<Sidebar className="app-builder-workflow-app__sidebar">
			<Sidebar.Header>
				{!currentTab ? (
					<h3 className="title">
						{Liferay.Language.get('step-configuration')}
					</h3>
				) : (
					<div className="tab-title">
						<ClayButton
							displayType="secondary"
							onClick={onClick}
							small
						>
							<span className="icon-monospaced">
								<ClayIcon symbol="angle-left" />
							</span>
						</ClayButton>

						<h3>{currentTab.title}</h3>
					</div>
				)}
			</Sidebar.Header>

			<Sidebar.Body>
				{!currentTab ? (
					<>
						<ClayForm.Group className="form-group-outlined">
							<label>
								{Liferay.Language.get('step-name')}

								<span className="reference-mark">
									<ClayIcon symbol="asterisk" />
								</span>
							</label>

							<ClayInput
								onChange={onChangeStepName}
								type="text"
								value={currentStep.name}
							/>

							{currentStep.initial === undefined && (
								<>
									<label className="mt-4">
										{Liferay.Language.get('assignee')}

										<span className="reference-mark">
											<ClayIcon symbol="asterisk" />
										</span>
									</label>

									<ClayIcon
										className="ml-2 text-muted tooltip-icon"
										data-tooltip-align="top"
										data-tooltip-delay="0"
										symbol="question-circle-full"
										title={Liferay.Language.get(
											'assignees-are-the-roles-responsible-to-transition-this-workflow-step'
										)}
									/>

									<AutocompleteMultiSelect
										emptyMessage={Liferay.Language.get(
											'no-roles-were-found'
										)}
										emptyResultMessage={Liferay.Language.get(
											'no-roles-were-found-with-this-name-try-a-different-one'
										)}
										items={assigneeRoles}
										onChange={onChangeAssignees}
										placeholder={Liferay.Language.get(
											'select-assignees'
										)}
										selectedItems={currentStep.appWorkflowRoleAssignments.map(
											({roleId, roleName}) => ({
												id: roleId,
												name: roleName,
											})
										)}
									/>
								</>
							)}
						</ClayForm.Group>

						{tabs.map(
							(
								{
									disabled,
									error,
									infoItems,
									show,
									showPopoverIcon,
									title,
								},
								index
							) =>
								show && (
									<ClayButton
										className={classNames(
											'mb-3 tab-button',
											(error ||
												(showPopoverIcon &&
													nativeField)) &&
												'border-error'
										)}
										disabled={disabled}
										displayType="secondary"
										key={index}
										onClick={() =>
											setCurrentTab(tabs[index])
										}
									>
										<div className="text-dark">
											<span>{title}</span>

											<ButtonInfo items={infoItems} />
										</div>

										<div className="d-flex">
											{error && (
												<ClayIcon
													className="error mr-2 mt-1 tooltip-popover-icon"
													data-tooltip-align="left"
													data-tooltip-delay="0"
													fontSize="26px"
													symbol="exclamation-full"
													title={`${Liferay.Language.get(
														'error'
													)}: ${Liferay.Language.get(
														'there-are-form-views-with-duplicated-fields'
													)}`}
												/>
											)}

											{showPopoverIcon && (
												<MissingRequiredFieldsPopover
													dataObjectName={
														dataObject.name
													}
													message={{
														custom: sub(
															Liferay.Language.get(
																'the-form-view-for-this-app-was-modified-and-does-not-contain-all-required-fields-for-the-x-object'
															),
															[dataObject.name]
														),
														native: sub(
															Liferay.Language.get(
																'the-form-view-for-this-app-was-modified-and-does-not-contain-all-native-required-fields-it-cannot-be-used-to-create-new-records-for-the-x-object'
															),
															[dataObject.name]
														),
													}}
													nativeField={nativeField}
													onClick={() => {
														setShowPopover(false);

														openFormViewModal({
															dataDefinitionId:
																dataObject.id,
															dataLayoutId:
																formView.id,
															defaultLanguageId:
																dataObject.defaultLanguageId,
															selectFormView: updateFormView,
														});
													}}
													setShowPopover={
														setShowPopover
													}
													showPopover={showPopover}
													triggerClassName="help-cursor icon-with-popover"
												/>
											)}

											<ClayIcon
												className="dropdown-button-asset"
												symbol="angle-right"
											/>
										</div>
									</ClayButton>
								)
						)}
					</>
				) : (
					currentTab.content(editAppContext)
				)}
			</Sidebar.Body>
		</Sidebar>
	);
}
