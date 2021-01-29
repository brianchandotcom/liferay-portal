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

import ClayPopover from '@clayui/popover';
import {AppContext} from 'app-builder-web/js/AppContext.es';
import Button from 'app-builder-web/js/components/button/Button.es';
import useDeployApp from 'app-builder-web/js/hooks/useDeployApp.es';
import ListApps, {Actions} from 'app-builder-web/js/pages/apps/ListApps.es';
import {COLUMNS, FILTERS} from 'app-builder-web/js/pages/apps/constants.es';
import {parseResponse} from 'app-builder-web/js/utils/client.es';
import {errorToast, successToast} from 'app-builder-web/js/utils/toast.es';
import {createResourceURL, fetch} from 'frontend-js-web';
import {compile} from 'path-to-regexp';
import React, {useContext, useState} from 'react';

import MissingFieldsModal from './edit/MissingFieldsModal.es';
import {getDataDefinition, getFormViews} from './edit/actions.es';
import {checkRequiredFields} from './edit/utils.es';

export default ({scope, ...props}) => {
	const {userId} = useContext(AppContext);
	const {deployApp} = useDeployApp();
	const [currentApp, setCurrentApp] = useState({dataDefinitionName: ''});
	const [showTooltip, setShowTooltip] = useState(false);
	const [deployCallback, setDeployCallback] = useState({});
	const [missingFieldsModalVisible, setMissingFieldsModalVisible] = useState(
		false
	);
	const {baseResourceURL, namespace} = useContext(AppContext);

	const [firstColumn, ...otherColumns] = COLUMNS;
	const lastColumn = otherColumns.pop();

	const columns = [
		firstColumn,
		{
			key: 'dataDefinitionName',
			value: Liferay.Language.get('object'),
		},
		...otherColumns,
		{
			key: 'version',
			value: Liferay.Language.get('version'),
		},
		lastColumn,
	];

	const newAppLink = compile(props.editPath[0])();

	const emptyState = {
		button: () => (
			<Button displayType="secondary" href={newAppLink}>
				{Liferay.Language.get('create-new-app')}
			</Button>
		),
		description: Liferay.Language.get(
			'integrate-the-data-collection-and-management-of-an-object-with-a-step-driven-workflow-process'
		),
		title: Liferay.Language.get('there-are-no-apps-yet'),
	};

	const filters = [
		...FILTERS,
		{
			items: [
				{
					label: Liferay.Language.get('me'),
					value: userId,
				},
			],
			key: 'userIds',
			multiple: true,
			name: 'author',
		},
	];

	const confirmDelete = ({id}) => {
		return new Promise((resolve) => {
			const confirmed = confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			);

			if (confirmed) {
				fetch(
					createResourceURL(baseResourceURL, {
						p_p_resource_id:
							'/app_builder_workflow/delete_app_builder_app',
					}),
					{
						body: new URLSearchParams(
							Liferay.Util.ns(namespace, {
								appBuilderAppId: id,
							})
						),
						method: 'POST',
					}
				)
					.then(parseResponse)
					.then(() => {
						successToast(
							Liferay.Language.get(
								'the-item-was-deleted-successfully'
							)
						);

						resolve(true);
					})
					.catch(({errorMessage}) => {
						errorToast(errorMessage);

						resolve(false);
					});
			}
			else {
				resolve(false);
			}
		});
	};

	function validateFormViewMissingRequiredFields(app) {
		const isFormViewMissingRequiredFields = (app) => {
			return getDataDefinition(app.dataDefinitionId).then(
				(dataDefinition) => {
					setCurrentApp(app);

					return getFormViews(
						dataDefinition.id,
						dataDefinition.defaultLanguageId
					).then((formViews) => {
						formViews = checkRequiredFields(
							formViews,
							dataDefinition
						);

						return formViews.find(({id}) => id === app.dataLayoutId)
							.missingRequiredFields?.customField;
					});
				}
			);
		};

		return isFormViewMissingRequiredFields(app).then((missing) => {
			if (missing) {
				return new Promise((resolve, reject) => {
					setDeployCallback({reject, resolve});
					setMissingFieldsModalVisible(true);
				});
			}
		});
	}

	const actions = [...Actions(validateFormViewMissingRequiredFields)];

	actions.splice(-1, 1, {
		action: confirmDelete,
		name: Liferay.Language.get('delete'),
		show: ({active}) => !active,
	});

	return (
		<>
			<ListApps
				listViewProps={{
					actions,
					addButton: () => (
						<ClayPopover
							alignPosition="bottom-right"
							header={Liferay.Language.get(
								'workflow-powered-app'
							)}
							show={showTooltip}
							trigger={
								<Button
									className="nav-btn nav-btn-monospaced"
									href={newAppLink}
									onMouseOut={() => setShowTooltip(false)}
									onMouseOver={() => setShowTooltip(true)}
									symbol="plus"
								/>
							}
						>
							{Liferay.Language.get(
								'create-an-app-that-integrates-a-step-driven-workflow-process'
							)}
						</ClayPopover>
					),
					columns,
					emptyState,
					endpoint: `/o/app-builder/v1.0/apps?scope=${scope}`,
					filters,
				}}
				{...props}
			/>

			<MissingFieldsModal
				dataObjectName={currentApp.dataDefinitionName}
				missingFieldsModalVisible={missingFieldsModalVisible}
				onDeploy={() =>
					deployApp(currentApp)
						.then((result) => {
							setMissingFieldsModalVisible(false);
							deployCallback.resolve(result);
						})
						.catch(deployCallback.reject)
				}
				setMissingFieldsModalVisible={setMissingFieldsModalVisible}
			/>
		</>
	);
};
