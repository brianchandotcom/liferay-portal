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

import {AppContext} from 'app-builder-web/js/AppContext.es';
import ControlMenu from 'app-builder-web/js/components/control-menu/ControlMenu.es';
import {Loading} from 'app-builder-web/js/components/loading/Loading.es';
import {getDataObjects} from 'app-builder-web/js/pages/apps/SelectObjectsDropDown.es';
import EditAppContext, {
	UPDATE_APP,
	reducer,
} from 'app-builder-web/js/pages/apps/edit/EditAppContext.es';
import {getItem, parseResponse} from 'app-builder-web/js/utils/client.es';
import {errorToast, successToast} from 'app-builder-web/js/utils/toast.es';
import {createResourceURL, fetch} from 'frontend-js-web';
import React, {useContext, useEffect, useReducer, useState} from 'react';

import '../../../../css/EditApp.scss';
import ApplyAppChangesModal from './ApplyAppChangesModal.es';
import DeployAppModal from './DeployAppModal.es';
import EditAppToolbar from './EditAppToolbar.es';
import {
	getAssigneeRoles,
	getDataDefinition,
	getFormViews,
	getTableViews,
	populateConfigData,
} from './actions.es';
import configReducer, {
	UPDATE_CONFIG,
	UPDATE_LIST_ITEMS,
	getInitialConfig,
} from './configReducer.es';
import EditAppSidebar from './sidebar/EditAppSidebar.es';
import WorkflowBuilder from './workflow-builder/WorkflowBuilder.es';

export default ({
	history,
	match: {
		params: {appId},
	},
	scope,
}) => {
	const {baseResourceURL, getStandaloneURL, namespace} = useContext(
		AppContext
	);

	const [config, dispatchConfig] = useReducer(
		configReducer,
		getInitialConfig()
	);

	const {defaultLanguageId} = config.dataObject;

	const [{app}, dispatch] = useReducer(reducer, {
		app: {
			active: false,
			appDeployments: [],
			dataLayoutId: null,
			dataListViewId: null,
			name: {
				[defaultLanguageId]: '',
			},
			scope,
		},
	});

	const [isAppChangesModalVisible, setAppChangesModalVisible] = useState(
		false
	);
	const [isDeployModalVisible, setDeployModalVisible] = useState(false);
	const [isLoading, setLoading] = useState(false);
	const [isSaving, setSaving] = useState(false);

	const editState = {
		appId,
		config,
		dispatch,
		dispatchConfig,
		isAppChangesModalVisible,
		isDeployModalVisible,
		setAppChangesModalVisible,
		setDeployModalVisible,
		state: {app},
	};

	useEffect(() => {
		if (app.dataDefinitionId && defaultLanguageId) {
			dispatchConfig({
				listItems: {fetching: true},
				type: UPDATE_LIST_ITEMS,
			});

			Promise.all([
				getFormViews(app.dataDefinitionId, defaultLanguageId),
				getTableViews(app.dataDefinitionId, defaultLanguageId),
			])
				.then(([formViews, tableViews]) => {
					dispatchConfig({
						listItems: {
							fetching: false,
							formViews,
							tableViews,
						},
						type: UPDATE_LIST_ITEMS,
					});
				})
				.catch(() => {
					dispatchConfig({
						listItems: {fetching: false},
						type: UPDATE_LIST_ITEMS,
					});
				});
		}
	}, [app.dataDefinitionId, defaultLanguageId]);

	useEffect(() => {
		const promises = [getAssigneeRoles(), getDataObjects()];

		dispatchConfig({
			listItems: {fetching: true},
			type: UPDATE_LIST_ITEMS,
		});

		if (appId) {
			setLoading(true);

			Promise.all([
				getItem(`/o/app-builder/v1.0/apps/${appId}`),
				getItem(
					`/o/app-builder-workflow/v1.0/apps/${appId}/app-workflows`
				),
				...promises,
			])
				.then(([app, ...previousResults]) => {
					return getDataDefinition(
						app.dataDefinitionId
					).then((dataDefinition) => [
						app,
						dataDefinition,
						...previousResults,
					]);
				})
				.then(([app, dataDefinition, ...previousResults]) => {
					return Promise.all([
						getFormViews(
							app.dataDefinitionId,
							dataDefinition.defaultLanguageId
						),
						getTableViews(
							app.dataDefinitionId,
							dataDefinition.defaultLanguageId
						),
					]).then((results) => [app, ...previousResults, ...results]);
				})
				.then(populateConfigData)
				.then(([app, config]) => {
					dispatch({
						app,
						type: UPDATE_APP,
					});

					dispatchConfig({
						config,
						type: UPDATE_CONFIG,
					});

					setLoading(false);
				})
				.catch(() => {
					errorToast();
					setLoading(false);

					dispatchConfig({
						listItems: {fetching: false},
						type: UPDATE_LIST_ITEMS,
					});
				});
		}
		else {
			Promise.all(promises)
				.then(([assigneeRoles, dataObjects]) => {
					dispatchConfig({
						listItems: {
							assigneeRoles,
							dataObjects,
							fetching: false,
						},
						type: UPDATE_LIST_ITEMS,
					});
				})
				.catch(() => {
					dispatchConfig({
						listItems: {fetching: false},
						type: UPDATE_LIST_ITEMS,
					});
				});
		}
	}, [appId]);

	let title = Liferay.Language.get('new-workflow-powered-app');

	if (appId) {
		title = Liferay.Language.get('edit-workflow-powered-app');
	}

	const getStandaloneLink = ({appDeployments, id}) => {
		const isStandalone = appDeployments.some(
			({type}) => type === 'standalone'
		);

		return isStandalone
			? `<a href="${getStandaloneURL(
					id
			  )}" target="_blank">${Liferay.Language.get(
					'open-standalone-app'
			  )}. ${Liferay.Util.getLexiconIconTpl('shortcut')}</a>`
			: '';
	};

	const onCancel = () => {
		history.push(`/${scope}`);
	};

	const onSave = (callback = () => {}, deployed) => {
		const workflowAppSteps = [...config.steps];

		const workflowApp = {
			appWorkflowStates: [
				workflowAppSteps.shift(),
				workflowAppSteps.pop(),
			],
			appWorkflowTasks: workflowAppSteps.map(
				({appWorkflowDataLayoutLinks, ...restProps}) => ({
					...restProps,
					appWorkflowDataLayoutLinks: appWorkflowDataLayoutLinks.map(
						({dataLayoutId, readOnly}) => ({dataLayoutId, readOnly})
					),
					errors: undefined,
				})
			),
		};

		const resource = appId ? 'update' : 'add';

		const params = {
			app: JSON.stringify({...app, active: deployed ?? app.active}),
			appWorkflow: JSON.stringify(workflowApp),
		};

		if (appId) {
			params.appBuilderAppId = appId;
		}
		else {
			params.dataDefinitionId = app.dataDefinitionId;
		}

		fetch(
			createResourceURL(baseResourceURL, {
				p_p_resource_id: `/app_builder/${resource}_workflow_app`,
			}),
			{
				body: new URLSearchParams(Liferay.Util.ns(namespace, params)),
				method: 'POST',
			}
		)
			.then(parseResponse)
			.then((app) => {
				const message = deployed
					? `${Liferay.Language.get(
							'the-app-was-deployed-successfully'
					  )} ${getStandaloneLink(app)}`
					: Liferay.Language.get('the-app-was-saved-successfully');

				callback();
				successToast(message);
				setSaving(false);

				onCancel();
			})
			.catch(({errorMessage}) => {
				callback();
				errorToast(`${errorMessage}`);
				setSaving(false);
			});
	};

	return (
		<div className="app-builder-workflow-app">
			<ControlMenu backURL={`../../${scope}`} title={title} />

			<Loading isLoading={isLoading}>
				<EditAppContext.Provider value={editState}>
					<EditAppToolbar
						isSaving={isSaving}
						onCancel={onCancel}
						onSave={onSave}
					/>

					<WorkflowBuilder />

					<EditAppSidebar />

					<ApplyAppChangesModal onSave={onSave} />

					<DeployAppModal onSave={onSave} />
				</EditAppContext.Provider>
			</Loading>
		</div>
	);
};
