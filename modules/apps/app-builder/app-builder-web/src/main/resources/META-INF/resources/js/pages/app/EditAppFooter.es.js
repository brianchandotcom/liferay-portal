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

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {withRouter} from 'react-router-dom';
import React, {useContext, useState} from 'react';

import {AppContext} from '../../AppContext.es';
import Button from '../../components/button/Button.es';
import {ToastContext} from '../../components/toast/ToastContext.es';
import {updateItem, addItem} from '../../utils/client.es';
import EditAppContext from './EditAppContext.es';

export default withRouter(
	({
		currentStep,
		history,
		match: {
			params: {dataDefinitionId}
		},
		onCurrentStepChange
	}) => {
		const {
			state: {app}
		} = useContext(EditAppContext);

		const {getStandaloneURL} = useContext(AppContext);
		const {addToast} = useContext(ToastContext);

		const [isDeploying, setDeploying] = useState(false);

		const {
			appDeployments,
			dataLayoutId,
			dataListViewId,
			id: appId,
			name: {en_US: appName}
		} = app;

		const getStandaloneLink = appId => {
			const isStandalone = appDeployments.some(
				deployment => deployment.type === 'standalone'
			);

			if (!isStandalone) {
				return <></>;
			}

			const url = getStandaloneURL(appId);

			return (
				<ClayLink href={url} target="_blank">
					{url} <ClayIcon symbol="shortcut" />
				</ClayLink>
			);
		};

		const addSuccessToast = appId => {
			addToast({
				displayType: 'success',
				message: (
					<>
						{Liferay.Language.get(
							'the-app-was-deployed-successfully'
						)}{' '}
						{getStandaloneLink(appId)}
					</>
				),
				title: `${Liferay.Language.get('success')}:`
			});
		};

		const onCancel = () => {
			history.push(`/custom-object/${dataDefinitionId}/apps`);
		};

		const onDeploy = () => {
			setDeploying(true);

			if (appId) {
				updateItem(`/o/app-builder/v1.0/apps/${appId}`, app)
					.then(() => addSuccessToast(appId))
					.then(onCancel)
					.catch(() => setDeploying(false));
			} else {
				addItem(
					`/o/app-builder/v1.0/data-definitions/${dataDefinitionId}/apps`,
					app
				)
					.then(app => addSuccessToast(app.id))
					.then(onCancel)
					.catch(() => setDeploying(false));
			}
		};

		return (
			<div className="card-footer bg-transparent">
				<div className="autofit-row">
					<div className="col-md-4">
						<Button displayType="secondary" onClick={onCancel}>
							{Liferay.Language.get('cancel')}
						</Button>
					</div>
					<div className="col-md-4 offset-md-4 text-right">
						{currentStep > 0 && (
							<Button
								className="mr-3"
								displayType="secondary"
								onClick={() =>
									onCurrentStepChange(currentStep - 1)
								}
							>
								{Liferay.Language.get('previous')}
							</Button>
						)}
						{currentStep < 2 && (
							<Button
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
							</Button>
						)}
						{currentStep === 2 && (
							<Button
								disabled={
									appDeployments.length === 0 ||
									!appName ||
									isDeploying
								}
								displayType="primary"
								onClick={onDeploy}
							>
								{app.id
									? Liferay.Language.get('save')
									: Liferay.Language.get('deploy')}
							</Button>
						)}
					</div>
				</div>
			</div>
		);
	}
);
