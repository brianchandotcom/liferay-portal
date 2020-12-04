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

import ClayLayout from '@clayui/layout';
import React, {useEffect, useReducer, useState} from 'react';

import ControlMenu from '../../../components/control-menu/ControlMenu.es';
import {Loading} from '../../../components/loading/Loading.es';
import useDataDefinition from '../../../hooks/useDataDefinition.es';
import {toQuery} from '../../../hooks/useQuery.es';
import {getItem} from '../../../utils/client.es';
import EditAppBody from './EditAppBody.es';
import EditAppContext, {UPDATE_APP, reducer} from './EditAppContext.es';
import EditAppFooter from './EditAppFooter.es';
import EditAppHeader from './EditAppHeader.es';

export default ({
	location: {search},
	match: {
		params: {appId, dataDefinitionId},
	},
	scope,
}) => {
	const {availableLanguageIds = [], defaultLanguageId} = useDataDefinition(
		dataDefinitionId
	);

	const [currentStep, setCurrentStep] = useState(0);
	const [isLoading, setLoading] = useState(false);
	const [editingLanguageId, setEditingLanguageId] = useState('');
	const [state, dispatch] = useReducer(reducer, {
		app: {
			active: true,
			appDeployments: [],
			dataLayoutId: null,
			dataListViewId: null,
			name: {},
			scope,
		},
	});

	const {backUrl} = toQuery(search, {backUrl: '../'});

	useEffect(() => {
		if (!editingLanguageId) {
			setEditingLanguageId(defaultLanguageId);
		}
	}, [defaultLanguageId, editingLanguageId]);

	useEffect(() => {
		if (appId) {
			setLoading(true);

			getItem(`/o/app-builder/v1.0/apps/${appId}`)
				.then((app) => {
					dispatch({
						app,
						type: UPDATE_APP,
					});
					setLoading(false);
				})
				.catch((_) => setLoading(false));
		}
	}, [appId]);

	let title = Liferay.Language.get('new-app');

	if (appId) {
		title = Liferay.Language.get('edit-app');
	}

	return (
		<>
			<ControlMenu backURL={backUrl} title={title} />

			<Loading isLoading={isLoading}>
				<EditAppContext.Provider value={{dispatch, state}}>
					<ClayLayout.ContainerFluid className="mt-4" size="lg">
						<div className="card card-root mb-0 shadowless-card">
							<EditAppHeader
								availableLanguageIds={availableLanguageIds}
								defaultLanguageId={defaultLanguageId}
								editingLanguageId={editingLanguageId}
								onEditingLanguageIdChange={(
									editingLanguageId
								) => {
									setEditingLanguageId(editingLanguageId);
								}}
							/>

							<EditAppBody
								currentStep={currentStep}
								dataDefinitionId={dataDefinitionId}
								defaultLanguageId={defaultLanguageId}
							/>

							<h4 className="card-divider"></h4>

							<EditAppFooter
								currentStep={currentStep}
								defaultLanguageId={defaultLanguageId}
								editingLanguageId={editingLanguageId}
								onCurrentStepChange={setCurrentStep}
							/>
						</div>
					</ClayLayout.ContainerFluid>
				</EditAppContext.Provider>
			</Loading>
		</>
	);
};
