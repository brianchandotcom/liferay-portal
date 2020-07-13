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
import {AppContext} from 'app-builder-web/js/AppContext.es';
import {ControlMenuBase} from 'app-builder-web/js/components/control-menu/ControlMenu.es';
import {getItem} from 'app-builder-web/js/utils/client.es';
import {successToast} from 'app-builder-web/js/utils/toast.es';
import {createResourceURL, fetch} from 'frontend-js-web';
import React, {useCallback, useContext, useEffect, useState} from 'react';
import {createPortal} from 'react-dom';

import WorkflowInfoBar from '../../components/workflow-info-bar/WorkflowInfoBar.es';
import useAppWorkflow from '../../hooks/useAppWorkflow.es';
import useDDMForms from '../../hooks/useDDMForms.es';

const createWorkflowInfoPortal = (props) => {
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
	portalElement.className = 'border-bottom py-4';
	portalElement.id = portalElementId;

	portalContainer.insertBefore(portalElement, targetElement);

	return createPortal(
		<WorkflowInfoBar {...props} hideColumns={['step']} />,
		portalElement
	);
};

export default function EditEntry({dataRecordId, redirect}) {
	const {
		appId,
		basePortletURL,
		baseResourceURL,
		dataLayoutIds,
		namespace,
	} = useContext(AppContext);

	const {
		appWorkflowDefinitionId,
		appWorkflowStates: [initialState = {}] = [],
		appWorkflowTasks,
	} = useAppWorkflow(appId);

	const {appWorkflowTransitions: [transition = {}] = []} = initialState;

	const [transitioning, setTransitioning] = useState(false);
	const [workflowInfo, setWorkflowInfo] = useState();

	const actionButtons = [];
	const isEdit = dataRecordId !== '0';
	let pageTitle =
		dataRecordId !== '0'
			? Liferay.Language.get('edit-entry')
			: Liferay.Language.get('add-entry');

	const onCancel = useCallback(() => {
		setTransitioning(false);

		if (redirect) {
			Liferay.Util.navigate(redirect);
		}
		else {
			Liferay.Util.navigate(basePortletURL);
		}
	}, [basePortletURL, redirect]);

	const onSubmit = useDDMForms(
		dataLayoutIds.map(
			(dataLayoutId) => `${namespace}container${dataLayoutId}`
		),
		useCallback(
			({transitionName}, dataRecord) => {
				setTransitioning(true);

				const params = {
					appBuilderAppId: appId,
					dataRecord: JSON.stringify(dataRecord),
					dataRecordId,
				};

				const resource = `${isEdit ? 'update' : 'add'}_data_record`;

				if (workflowInfo) {
					const {
						id,
						tasks = [],
						taskNames: [taskName],
					} = workflowInfo;

					//avoids enter submission putting primary action

					const {appWorkflowTransitions: transitions = []} =
						tasks.find(({name}) => name === taskName) || {};

					const action = transitions.find(({primary}) => primary);

					params.taskName = taskName;
					params.transitionName = transitionName ?? action.name;
					params.workflowInstanceId = id;
				}

				fetch(
					createResourceURL(baseResourceURL, {
						p_p_resource_id: `/app_builder/${resource}`,
					}),
					{
						body: new URLSearchParams(
							Liferay.Util.ns(namespace, params)
						),
						method: 'POST',
					}
				).then(() => {
					successToast(
						isEdit
							? Liferay.Language.get('an-entry-was-updated')
							: Liferay.Language.get('an-entry-was-added')
					);
					onCancel();
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
		)
	);

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

	useEffect(() => {
		if (appWorkflowDefinitionId && isEdit) {
			getItem(
				`/o/portal-workflow-metrics/v1.0/processes/${appWorkflowDefinitionId}/instances`,
				{classPKs: [dataRecordId]}
			).then(({items, totalCount}) => {
				if (totalCount > 0) {
					setWorkflowInfo({
						...items.pop(),
						tasks: appWorkflowTasks,
					});
				}
			});
		}
	}, [appWorkflowDefinitionId, appWorkflowTasks, dataRecordId, isEdit]);

	return (
		<>
			<ControlMenuBase
				backURL={redirect ? redirect : `${basePortletURL}/#/`}
				title={pageTitle}
				url={location.href}
			/>

			{workflowInfo && createWorkflowInfoPortal(workflowInfo)}

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
		</>
	);
}
