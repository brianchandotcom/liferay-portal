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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {AppContext} from 'app-builder-web/js/AppContext.es';
import {ControlMenuBase} from 'app-builder-web/js/components/control-menu/ControlMenu.es';
import {Loading} from 'app-builder-web/js/components/loading/Loading.es';
import useDataDefinition from 'app-builder-web/js/hooks/useDataDefinition.es';
import {getItem} from 'app-builder-web/js/utils/client.es';
import {getLocalizedUserPreferenceValue} from 'app-builder-web/js/utils/lang.es';
import {errorToast, successToast} from 'app-builder-web/js/utils/toast.es';
import {createResourceURL, fetch} from 'frontend-js-web';
import React, {
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useState,
} from 'react';
import {createPortal} from 'react-dom';

import WorkflowInfoBar from '../../components/workflow-info-bar/WorkflowInfoBar.es';
import useAppWorkflow from '../../hooks/useAppWorkflow.es';
import useDDMForms, {
	useDDMFormsSubmit,
	useDDMFormsValidation,
} from '../../hooks/useDDMForms.es';
import useDataLayouts from '../../hooks/useDataLayouts.es';
import useDataRecordApps from '../../hooks/useDataRecordApps.es';
import ReassignEntryModal from './ReassignEntryModal.es';
import {METRIC_INDEXES_KEY, refreshIndex} from './actions.es';

const WorkflowInfoPortal = ({children}) => {
	const portalElementId = 'workflowInfoBar';
	const targetElement = document.getElementById('edit-app-content');

	let portalElement = document.getElementById(portalElementId);
	let portalContainer = portalElement?.parentNode;

	if (portalContainer) {
		portalContainer.removeChild(portalElement);
	}
	else {
		portalContainer = targetElement?.parentNode;
	}

	portalElement = document.createElement('div');
	portalElement.id = portalElementId;

	portalContainer.insertBefore(portalElement, targetElement);

	return createPortal(children, portalElement);
};

export default function EditEntry({
	dataDefinitionId,
	dataRecordId,
	redirect,
	userLanguageId,
}) {
	const {
		appId,
		basePortletURL,
		baseResourceURL,
		dataLayoutIds,
		namespace,
	} = useContext(AppContext);

	const {availableLanguageIds, defaultLanguageId} = useDataDefinition(
		dataDefinitionId
	);

	const dataLayouts = useDataLayouts(dataLayoutIds);
	const dataRecordApps = useDataRecordApps(
		appId,
		useMemo(() => [dataRecordId], [dataRecordId])
	);

	const appWorkflow = useAppWorkflow(appId);

	const {
		appVersion,
		appWorkflowDefinitionId,
		appWorkflowStates: [initialState = {}] = [],
		appWorkflowTasks,
	} = dataRecordApps[dataRecordId] ?? appWorkflow;

	const {appWorkflowTransitions: [transition = {}] = []} = initialState;

	const [isLoading, setLoading] = useState(true);
	const [isModalVisible, setModalVisible] = useState(false);
	const [transitioning, setTransitioning] = useState(false);
	const [workflowInfo, setWorkflowInfo] = useState();

	const actionButtons = [];
	const isEdit = dataRecordId !== '0';
	let pageTitle =
		dataRecordId !== '0'
			? Liferay.Language.get('edit-entry')
			: Liferay.Language.get('add-entry');

	const ddmForms = useDDMForms(
		dataLayoutIds.map(
			(dataLayoutId) => `${namespace}container${dataLayoutId}`
		)
	);

	const doFetch = useCallback(() => {
		setLoading(true);

		if (appWorkflowDefinitionId) {
			if (isEdit) {
				getItem(
					`/o/portal-workflow-metrics/v1.0/processes/${appWorkflowDefinitionId}/instances`,
					{classPKs: [dataRecordId]}
				).then(({items}) => {
					setLoading(false);

					if (items.length) {
						const {id, ...instance} = items.pop();

						const [assignee] = instance.assignees || [];

						const assignedToUser =
							Number(themeDisplay.getUserId()) === assignee?.id;

						setWorkflowInfo({
							...instance,
							appVersion,
							canReassign: assignedToUser || assignee?.reviewer,
							instanceId: id,
							tasks: appWorkflowTasks,
						});
					}
				});
			}
			else {
				setLoading(false);
			}
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [appWorkflowDefinitionId, dataRecordId, isEdit]);

	const onCancel = useCallback(() => {
		if (redirect) {
			Liferay.Util.navigate(redirect);
		}
		else {
			Liferay.Util.navigate(basePortletURL);
		}
	}, [basePortletURL, redirect]);

	const onCloseModal = (isRefetch) => {
		setModalVisible(false);

		if (isRefetch) {
			doFetch();
		}
	};

	const saveDataRecord = useCallback(
		({transitionName}, dataRecord) => {
			const params = {
				appBuilderAppId: appId,
				dataRecord: JSON.stringify(dataRecord),
				dataRecordId,
			};

			const resource = `${
				isEdit ? 'app_builder_workflow/update' : 'app_builder/add'
			}_data_record`;

			if (workflowInfo) {
				const {
					instanceId,
					tasks = [],
					taskNames: [taskName],
				} = workflowInfo;

				//avoids enter submission putting primary action

				const {appWorkflowTransitions: transitions = []} =
					tasks.find(({name}) => name === taskName) || {};

				const action = transitions.find(({primary}) => primary);

				params.taskName = taskName;
				params.transitionName = transitionName ?? action.name;
				params.workflowInstanceId = instanceId;
			}

			fetch(
				createResourceURL(baseResourceURL, {
					p_p_resource_id: `/${resource}`,
				}),
				{
					body: new URLSearchParams(
						Liferay.Util.ns(namespace, params)
					),
					method: 'POST',
				}
			)
				.then(() => {
					successToast(
						isEdit
							? Liferay.Language.get('an-entry-was-updated')
							: Liferay.Language.get('an-entry-was-added')
					);

					refreshIndex(METRIC_INDEXES_KEY)
						.then(onCancel)
						.catch(onCancel);
				})
				.catch(() => {
					errorToast();
					setTransitioning(false);
				});
		},
		[
			appId,
			baseResourceURL,
			dataRecordId,
			isEdit,
			namespace,
			onCancel,
			workflowInfo,
		]
	);

	const validateForms = useDDMFormsValidation(
		ddmForms,
		defaultLanguageId,
		availableLanguageIds
	);

	const onSubmit = useCallback(
		(event) => {
			setTransitioning(true);

			validateForms(event)
				.then((dataRecord) => saveDataRecord(event, dataRecord))
				.catch(() => setTransitioning(false));
		},
		[saveDataRecord, validateForms]
	);

	useDDMFormsSubmit(ddmForms, onSubmit);

	useEffect(() => {
		if (dataLayoutIds.length === ddmForms.length) {
			ddmForms.forEach((ddmForm) => {
				const ddmReactForm = ddmForm.reactComponentRef.current;

				ddmReactForm.updateEditingLanguageId({
					editingLanguageId: userLanguageId,
					preserveValue: true,
				});
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [ddmForms.length, userLanguageId]);

	useEffect(() => {
		if (dataLayoutIds.length === dataLayouts.length) {
			dataLayouts.forEach(({id, name}) => {
				const formName = document.getElementById(`${id}_name`);

				if (formName) {
					formName.innerText = getLocalizedUserPreferenceValue(
						name,
						userLanguageId,
						defaultLanguageId
					);
				}
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [dataLayouts.length, userLanguageId]);

	useEffect(() => {
		doFetch();
	}, [doFetch]);

	if (workflowInfo) {
		const {
			assignees = [],
			completed,
			tasks = [],
			taskNames = [],
		} = workflowInfo;

		if (!completed) {
			const stepName = taskNames[0];
			pageTitle = `${stepName}`;

			const {appWorkflowTransitions = []} =
				tasks.find(({name}) => name === stepName) || {};

			const userId = Number(themeDisplay.getUserId());
			const assigned = assignees.findIndex(({id}) => id === userId) > -1;

			if (assigned) {
				appWorkflowTransitions.sort(
					(actionA, actionB) => actionB.primary - actionA.primary
				);

				appWorkflowTransitions.forEach(({name, primary}, index) => {
					actionButtons[index] = (
						<ClayButton
							disabled={transitioning}
							displayType={primary ? 'primary' : 'secondary'}
							key={index}
							onClick={() => onSubmit({transitionName: name})}
						>
							{name}
						</ClayButton>
					);
				});
			}
		}
	}
	else {
		actionButtons.push(
			<ClayButton
				disabled={transitioning}
				displayType="primary"
				key={0}
				onClick={() => onSubmit({transitionName: name})}
			>
				{transition.name || Liferay.Language.get('submit')}
			</ClayButton>
		);
	}

	return (
		<>
			<ControlMenuBase
				backURL={redirect ? redirect : `${basePortletURL}/#/`}
				title={pageTitle}
				url={location.href}
			/>

			<Loading isLoading={isLoading}>
				{workflowInfo && (
					<WorkflowInfoPortal>
						<div className="d-flex justify-content-center mt-4">
							<WorkflowInfoBar
								{...workflowInfo}
								hideColumns={['step']}
							/>

							{workflowInfo?.canReassign && (
								<ClayTooltipProvider>
									<ClayButtonWithIcon
										className="ml-2"
										data-tooltip-align="bottom"
										data-tooltip-delay="200"
										displayType="secondary"
										onClick={() => setModalVisible(true)}
										small
										symbol="change"
										title={Liferay.Language.get(
											'assign-to'
										)}
									/>
								</ClayTooltipProvider>
							)}
						</div>
					</WorkflowInfoPortal>
				)}

				<ClayButton.Group className="app-builder-form-buttons" spaced>
					{actionButtons}

					<ClayButton
						disabled={transitioning}
						displayType="secondary"
						onClick={onCancel}
					>
						{Liferay.Language.get('cancel')}
					</ClayButton>
				</ClayButton.Group>
			</Loading>

			{isModalVisible && (
				<ReassignEntryModal
					entry={workflowInfo}
					onCloseModal={onCloseModal}
					refetch={doFetch}
				/>
			)}
		</>
	);
}
