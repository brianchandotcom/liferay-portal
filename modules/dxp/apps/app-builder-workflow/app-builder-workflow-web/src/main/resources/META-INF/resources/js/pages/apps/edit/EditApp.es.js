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

import ControlMenu from 'app-builder-web/js/components/control-menu/ControlMenu.es';
import {Loading} from 'app-builder-web/js/components/loading/Loading.es';
import UpperToolbar from 'app-builder-web/js/components/upper-toolbar/UpperToolbar.es';
import EditAppContext, {
	UPDATE_APP,
	UPDATE_NAME,
	reducer,
} from 'app-builder-web/js/pages/apps/edit/EditAppContext.es';
import {getItem} from 'app-builder-web/js/utils/client.es';
import {errorToast} from 'app-builder-web/js/utils/toast.es';
import React, {useEffect, useReducer, useState} from 'react';

import '../../../../css/EditApp.scss';
import DeployAppModal from './DeployAppModal.es';
import {
	getAssigneeRoles,
	getDataObjects,
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
import {canDeployApp} from './utils.es';
import WorkflowBuilder from './workflow-builder/WorkflowBuilder.es';

export default ({
	history,
	match: {
		params: {appId},
	},
	scope,
}) => {
	const [{app}, dispatch] = useReducer(reducer, {
		app: {
			active: true,
			appDeployments: [],
			dataLayoutId: null,
			dataListViewId: null,
			name: {
				en_US: '',
			},
			scope,
		},
	});
	const [config, dispatchConfig] = useReducer(
		configReducer,
		getInitialConfig()
	);

	const [isModalVisible, setModalVisible] = useState(false);
	const [isLoading, setLoading] = useState(false);

	const editState = {
		appId,
		config,
		dispatch,
		dispatchConfig,
		isModalVisible,
		setModalVisible,
		state: {app},
	};

	useEffect(() => {
		if (app.dataDefinitionId) {
			dispatchConfig({
				listItems: {fetching: true},
				type: UPDATE_LIST_ITEMS,
			});

			Promise.all([
				getFormViews(app.dataDefinitionId),
				getTableViews(app.dataDefinitionId),
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
	}, [app.dataDefinitionId]);

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
					return Promise.all([
						getFormViews(app.dataDefinitionId),
						getTableViews(app.dataDefinitionId),
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

	const onCancel = () => {
		history.goBack();
	};

	const onAppNameChange = ({target}) => {
		dispatch({appName: target.value, type: UPDATE_NAME});
	};

	const maxLength = 30;

	const isDeployButtonDisabled = !canDeployApp(app, config);

	return (
		<div className="app-builder-workflow-app">
			<ControlMenu backURL={`../../${scope}`} title={title} />

			<Loading isLoading={isLoading}>
				<EditAppContext.Provider value={editState}>
					<UpperToolbar>
						<UpperToolbar.Input
							maxLength={maxLength}
							onChange={onAppNameChange}
							placeholder={Liferay.Language.get('untitled-app')}
							value={app.name.en_US}
						/>
						<UpperToolbar.Group>
							<UpperToolbar.Button
								displayType="secondary"
								onClick={onCancel}
							>
								{Liferay.Language.get('cancel')}
							</UpperToolbar.Button>

							<UpperToolbar.Button
								disabled={isDeployButtonDisabled}
								onClick={() => setModalVisible(true)}
							>
								{Liferay.Language.get('deploy')}
							</UpperToolbar.Button>
						</UpperToolbar.Group>
					</UpperToolbar>

					<WorkflowBuilder />

					<EditAppSidebar />

					<DeployAppModal onCancel={onCancel} />
				</EditAppContext.Provider>
			</Loading>
		</div>
	);
};
