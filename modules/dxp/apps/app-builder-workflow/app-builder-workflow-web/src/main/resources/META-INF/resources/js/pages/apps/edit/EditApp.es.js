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
import {getDataObjects} from 'app-builder-web/js/components/select-objects/SelectObjects.es';
import EditAppContext, {
	UPDATE_APP,
	UPDATE_DATA_LAYOUT_ID,
	reducer,
} from 'app-builder-web/js/pages/apps/edit/EditAppContext.es';
import {getLocalizedValue} from 'app-builder-web/js/utils/lang.es';
import Loading from 'data-engine-js-components-web/js/components/loading/Loading.es';
import {
	getItem,
	parseResponse,
} from 'data-engine-js-components-web/js/utils/client.es';
import {
	errorToast,
	successToast,
} from 'data-engine-js-components-web/js/utils/toast.es';
import {createResourceURL, fetch, openModal} from 'frontend-js-web';
import React, {useContext, useEffect, useReducer, useState} from 'react';

import '../../../../css/EditApp.scss';
import ApplyAppChangesModal from './ApplyAppChangesModal.es';
import DeployAppModal from './DeployAppModal.es';
import EditAppToolbar from './EditAppToolbar.es';
import MissingRequiredFieldsModal from './MissingRequiredFieldsModal.es';
import {
	getAssigneeRoles,
	getDataDefinition,
	getFormViews,
	getTableViews,
	populateConfigData,
} from './actions.es';
import configReducer, {
	UPDATE_CONFIG,
	UPDATE_DATA_DEFINITION_FIELDS,
	UPDATE_FORM_VIEW,
	UPDATE_LIST_ITEMS,
	getInitialConfig,
} from './configReducer.es';
import EditAppSidebar from './sidebar/EditAppSidebar.es';
import {checkRequiredFields} from './utils.es';
import WorkflowBuilder from './workflow-builder/WorkflowBuilder.es';

export default ({
	history,
	match: {
		params: {appId},
	},
	scope,
}) => {
	const {
		baseResourceURL,
		getStandaloneURL,
		namespace,
		objectsPortletURL,
	} = useContext(AppContext);

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
	const [
		missingRequiredFieldsVisible,
		setMissingRequiredFieldsVisible,
	] = useState(false);
	const [isLoading, setLoading] = useState(false);
	const [isSaving, setSaving] = useState(false);

	const openFormViewModal = ({
		dataDefinitionId,
		dataLayoutId,
		defaultLanguageId,
		firstFormView,
		selectFormView,
	}) => {
		const event = window.top?.Liferay.once(
			'newFormViewCreated',
			({dataDefinition, newFormView}) => {
				successToast(
					Liferay.Language.get('the-form-view-was-saved-successfully')
				);
				getFormViews(dataDefinitionId, defaultLanguageId).then(
					(formViews) => {
						const checkedFormViews = checkRequiredFields(
							formViews,
							dataDefinition
						);

						dispatchConfig({
							listItems: {
								fetching: false,
								formViews: checkedFormViews,
							},
							type: UPDATE_LIST_ITEMS,
						});

						if (firstFormView) {
							dispatchConfig({
								dataDefinitionFields:
									dataDefinition.dataDefinitionFields,
								type: UPDATE_DATA_DEFINITION_FIELDS,
							});
						}

						const currentFormView = checkedFormViews.find(
							({id}) => id === newFormView.id
						);

						if (
							!currentFormView.missingRequiredFields?.nativeField
						) {
							selectFormView({
								...currentFormView,
								name: getLocalizedValue(
									defaultLanguageId,
									newFormView.name
								),
							});
						}
						else if (newFormView.id === app.dataLayoutId) {
							selectFormView({});
						}
					}
				);
			}
		);
		openModal({
			onClose: () => event?.detach(),
			title: dataLayoutId
				? Liferay.Language.get('edit-form-view')
				: Liferay.Language.get('new-form-view'),
			url: `${Liferay.Util.PortletURL.createRenderURL(objectsPortletURL, {
				dataDefinitionId,
				dataLayoutId,
				mvcRenderCommandName: '/app_builder/edit_form_view',
				newCustomObject: true,
				p_p_state: 'pop_up',
			})}`,
		});
	};

	const updateFormView = (formView) => {
		dispatchConfig({
			formView,
			type: UPDATE_FORM_VIEW,
		});

		dispatch({
			...formView,
			type: UPDATE_DATA_LAYOUT_ID,
		});
	};

	const editState = {
		appId,
		config,
		dispatch,
		dispatchConfig,
		isAppChangesModalVisible,
		isDeployModalVisible,
		openFormViewModal,
		setAppChangesModalVisible,
		setDeployModalVisible,
		setMissingRequiredFieldsVisible,
		state: {app},
		updateFormView,
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
							formViews: checkRequiredFields(
								formViews,
								config.dataObject
							),
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
		// eslint-disable-next-line react-hooks/exhaustive-deps
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

	useEffect(() => {
		if (!app.active && config.dataObject.id) {
			dispatchConfig({
				config: {
					formView: checkRequiredFields(
						[config.formView],
						config.dataObject
					)[0],
				},
				type: UPDATE_CONFIG,
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [app.active]);

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

	const onCloseMissingRequiredFieldsModal = () =>
		setMissingRequiredFieldsVisible(false);

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
				p_p_resource_id: `/app_builder_workflow/${resource}_app_builder_app`,
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

					<MissingRequiredFieldsModal
						customActionOnClick={() => {
							setDeployModalVisible(true);

							onCloseMissingRequiredFieldsModal();
						}}
						dataObjectName={config.dataObject.name}
						onCloseModal={onCloseMissingRequiredFieldsModal}
						visible={missingRequiredFieldsVisible}
					/>
				</EditAppContext.Provider>
			</Loading>
		</div>
	);
};
