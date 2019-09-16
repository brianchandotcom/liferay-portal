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

import React, {useState} from 'react';
import DeployApp from './DeployApp.es';
import EditAppBody from './EditAppBody.es';
import EditAppFooter from './EditAppFooter.es';
import MultiStepNav from './MultiStepNav.es';
import ControlMenu from '../../components/control-menu/ControlMenu.es';
import {UpperToolbarInput} from '../../components/upper-toolbar/UpperToolbar.es';
import {addItem, updateItem} from '../../utils/client.es';

export default ({
	history,
	match: {
		params: {dataDefinitionId, appId}
	}
}) => {
	const [app, setApp] = useState({
		dataLayoutId: null,
		dataListViewId: null,
		name: {
			en_US: ''
		}
	});

	const [currentStep, setCurrentStep] = useState(0);

	let title = Liferay.Language.get('new-app');

	if (appId) {
		title = Liferay.Language.get('edit-app');
	}

	const getEmptyState = (description, title) => {
		return {
			description,
			title
		};
	};

	const onAppNameChange = event => {
		const name = event.target.value;

		setApp(prevApp => ({
			...prevApp,
			name: {
				en_US: name
			}
		}));
	};

	const onCancel = () => {
		history.push(`/custom-object/${dataDefinitionId}/apps`);
	};

	const onDeploy = () => {
		if (app.name.en_US === '') {
			return;
		}

		if (appId) {
			updateItem(`/o/app-builder/v1.0/apps/${appId}`, app).then(onCancel);
		} else {
			addItem(
				`/o/app-builder/v1.0/data-definitions/${dataDefinitionId}/apps`,
				app
			).then(onCancel);
		}
	};

	const onDataLayoutIdChange = dataLayoutId => {
		setApp(prevApp => ({
			...prevApp,
			dataLayoutId
		}));
	};

	const onDataListViewIdChange = dataListViewId => {
		setApp(prevApp => ({
			...prevApp,
			dataListViewId
		}));
	};

	const {
		dataLayoutId,
		dataListViewId,
		name: {en_US: appName}
	} = app;

	return (
		<>
			<ControlMenu backURL="../" title={title} />

			<div className="container-fluid container-fluid-max-lg mt-4">
				<div className="card card-root shadowless-card mb-0">
					<div className="card-header align-items-center d-flex justify-content-between bg-transparent">
						<UpperToolbarInput
							onInput={onAppNameChange}
							placeholder={Liferay.Language.get('untitled-app')}
							value={appName}
						/>
					</div>

					<h4 className="card-divider mb-4"></h4>

					<div className="card-body shadowless-card-body p-0">
						<div className="autofit-row">
							<div className="col-md-12">
								<MultiStepNav currentStep={currentStep} />
							</div>
						</div>

						{currentStep == 0 && (
							<EditAppBody
								emptyState={getEmptyState(
									Liferay.Language.get(
										'create-one-or-more-forms-to-display-the-data-held-in-your-data-object'
									),
									Liferay.Language.get(
										'there-are-no-form-views-yet'
									)
								)}
								endpoint={`/o/data-engine/v1.0/data-definitions/${dataDefinitionId}/data-layouts`}
								itemId={dataLayoutId}
								onItemIdChange={onDataLayoutIdChange}
								title={Liferay.Language.get(
									'select-a-form-view'
								)}
							/>
						)}

						{currentStep == 1 && (
							<EditAppBody
								emptyState={getEmptyState(
									Liferay.Language.get(
										'create-one-or-more-tables-to-display-the-data-held-in-your-data-object'
									),
									Liferay.Language.get(
										'there-are-no-table-views-yet'
									)
								)}
								endpoint={`/o/data-engine/v1.0/data-definitions/${dataDefinitionId}/data-list-views`}
								itemId={dataListViewId}
								onItemIdChange={onDataListViewIdChange}
								title={Liferay.Language.get(
									'select-a-table-view'
								)}
							/>
						)}

						{currentStep == 2 && (
							<DeployApp/>
						)}
					</div>

					<h4 className="card-divider"></h4>

					<EditAppFooter
						currentStep={currentStep}
						onCancel={onCancel}
						onDeploy={onDeploy}
						onStepChange={step => setCurrentStep(step)}
					/>
				</div>
			</div>
		</>
	);
};
