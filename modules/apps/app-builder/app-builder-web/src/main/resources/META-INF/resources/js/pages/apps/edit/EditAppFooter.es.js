/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import {
	addItem,
	updateItem,
} from 'data-engine-js-components-web/js/utils/client.es';
import {
	errorToast,
	successToast,
} from 'data-engine-js-components-web/js/utils/toast.es';
import React, {useContext, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../../AppContext.es';
import {normalizeNames} from '../../../utils/normalizers.es';
import {isProductMenuValid} from '../utils.es';
import EditAppContext from './EditAppContext.es';

export default withRouter(
	({
		currentStep,
		defaultLanguageId,
		editingLanguageId,
		history,
		match: {
			params: {dataDefinitionId},
		},
		onCurrentStepChange,
	}) => {
		const {
			state: {app},
		} = useContext(EditAppContext);

		const {getStandaloneURL} = useContext(AppContext);

		const [isDeploying, setDeploying] = useState(false);

		const {
			appDeployments,
			dataLayoutId,
			dataListViewId,
			id: appId,
			name,
		} = app;

		const getStandaloneLink = (appId) => {
			const isStandalone = appDeployments.some(
				(deployment) => deployment.type === 'standalone'
			);

			return isStandalone
				? `<a href="${getStandaloneURL(
						appId
				  )}" target="_blank">${Liferay.Language.get(
						'open-standalone-app'
				  )}. ${Liferay.Util.getLexiconIconTpl('shortcut')}</a>`
				: '';
		};

		const onSuccess = (appId) => {
			successToast(
				`${Liferay.Language.get(
					'the-app-was-deployed-successfully'
				)} ${getStandaloneLink(appId)}`
			);

			setDeploying(false);
		};

		const onError = (error) => {
			const {title = ''} = error;
			errorToast(`${title}.`);
			setDeploying(false);
		};

		const onCancel = () => {
			history.goBack();
		};

		const onDeploy = () => {
			setDeploying(true);

			if (!name[defaultLanguageId]) {
				name[defaultLanguageId] = name[editingLanguageId];
			}

			const data = {
				...app,
				name: normalizeNames({
					allowEmptyKeys: false,
					defaultName: Liferay.Language.get('untitled-app'),
					localizableValue: name,
				}),
			};

			if (appId) {
				updateItem({
					endpoint: `/o/app-builder/v1.0/apps/${appId}`,
					item: data,
				})
					.then(() => onSuccess(appId))
					.then(onCancel)
					.catch(onError);
			}
			else {
				addItem(
					`/o/app-builder/v1.0/data-definitions/${dataDefinitionId}/apps`,
					data
				)
					.then((app) => onSuccess(app.id))
					.then(onCancel)
					.catch(onError);
			}
		};

		return (
			<div className="bg-transparent card-footer">
				<ClayLayout.ContentRow>
					<ClayLayout.Col md="4">
						<ClayButton displayType="secondary" onClick={onCancel}>
							{Liferay.Language.get('cancel')}
						</ClayButton>
					</ClayLayout.Col>
					<ClayLayout.Col className="offset-md-4 text-right" md="4">
						{currentStep > 0 && (
							<ClayButton
								className="mr-3"
								displayType="secondary"
								onClick={() =>
									onCurrentStepChange(currentStep - 1)
								}
							>
								{Liferay.Language.get('previous')}
							</ClayButton>
						)}
						{currentStep < 3 && (
							<ClayButton
								disabled={
									(currentStep === 0 && !dataLayoutId) ||
									(currentStep === 1 && !dataListViewId)
								}
								displayType="primary"
								onClick={() =>
									onCurrentStepChange(currentStep + 1)
								}
							>
								{Liferay.Language.get('next')}
							</ClayButton>
						)}
						{currentStep === 3 && (
							<ClayButton
								disabled={
									appDeployments.length === 0 ||
									!name[editingLanguageId]?.trim() ||
									isDeploying ||
									!isProductMenuValid(app)
								}
								displayType="primary"
								onClick={onDeploy}
							>
								{app.id
									? Liferay.Language.get('save')
									: Liferay.Language.get('deploy')}
							</ClayButton>
						)}
					</ClayLayout.Col>
				</ClayLayout.ContentRow>
			</div>
		);
	}
);
