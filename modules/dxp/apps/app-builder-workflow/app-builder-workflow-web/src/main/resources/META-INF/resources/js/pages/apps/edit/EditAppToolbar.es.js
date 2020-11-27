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

import ClayBadge from '@clayui/badge';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayTooltipProvider} from '@clayui/tooltip';
import UpperToolbar, {
	UpperToolbarInput,
} from 'app-builder-web/js/components/upper-toolbar/UpperToolbar.es';
import useDeployApp from 'app-builder-web/js/hooks/useDeployApp.es';
import EditAppContext, {
	UPDATE_APP,
	UPDATE_NAME,
} from 'app-builder-web/js/pages/apps/edit/EditAppContext.es';
import {errorToast} from 'app-builder-web/js/utils/toast.es';
import {TranslationManager} from 'data-engine-taglib';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import '../../../../css/EditApp.scss';
import {canDeployApp, hasConfigBreakChanges} from './utils.es';

export default function EditAppToolbar({isSaving, onCancel, onSave}) {
	const {
		appId,
		config,
		dispatch,
		setAppChangesModalVisible,
		setDeployModalVisible,
		state: {app},
	} = useContext(EditAppContext);
	const {availableLanguageIds, defaultLanguageId} = config.dataObject;

	const {undeployApp} = useDeployApp();

	const [editingLanguageId, setEditingLanguageId] = useState(
		defaultLanguageId
	);

	const appName = app.name[editingLanguageId];
	const availableLanguages = availableLanguageIds.reduce(
		(languages, languageId) => ({
			...languages,
			[languageId]: languageId,
		}),
		{}
	);

	const onAppNameChange = ({target}) => {
		dispatch({
			name: {
				...app.name,
				[editingLanguageId]: target.value,
			},
			type: UPDATE_NAME,
		});
	};

	const onChangeEditingLanguageId = useCallback(
		(newEditingLanguageId) => {
			setEditingLanguageId(newEditingLanguageId);

			if (!app.name[newEditingLanguageId]) {
				dispatch({
					name: {
						...app.name,
						[newEditingLanguageId]: app.name[editingLanguageId],
					},
					type: UPDATE_NAME,
				});
			}
		},
		[app.name, dispatch, editingLanguageId]
	);

	const onClickSave = () => {
		if (appId && hasConfigBreakChanges(config)) {
			setAppChangesModalVisible(true);
		}
		else {
			onSave();
		}
	};

	const onClickUndeploy = () => {
		undeployApp({
			...app,
			appName,
		})
			.then(() => {
				dispatch({
					app: {...app, active: false},
					type: UPDATE_APP,
				});
			})
			.catch(({title}) => errorToast(title));
	};

	useEffect(() => {
		if (!availableLanguageIds.includes(editingLanguageId)) {
			setEditingLanguageId(defaultLanguageId);
		}

		dispatch({
			name: availableLanguageIds.reduce(
				(name, languageId) => ({
					...name,
					[languageId]: app.name[languageId] || appName,
				}),
				{}
			),
			type: UPDATE_NAME,
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [availableLanguageIds, defaultLanguageId]);

	const isDisabledSaveButton =
		!canDeployApp({...app, appName}, config) || isSaving;

	return (
		<UpperToolbar className="align-items-center">
			<TranslationManager
				availableLanguageIds={availableLanguages}
				className="mr-1"
				defaultLanguageId={defaultLanguageId}
				editingLanguageId={editingLanguageId}
				onEditingLanguageIdChange={onChangeEditingLanguageId}
				translatedLanguageIds={app.name}
			/>

			<UpperToolbarInput
				maxLength={30}
				onChange={onAppNameChange}
				placeholder={Liferay.Language.get('untitled-app')}
				value={appName}
			/>

			<UpperToolbar.Group>
				{appId && (
					<ClayBadge
						className="text-secondary version-badge"
						displayType="secondary"
						label={
							<div>
								{`${Liferay.Language.get('version')}:`}{' '}
								<span className="font-weight-normal text-dark">
									{app.version ?? '1.0'}
								</span>
							</div>
						}
					/>
				)}

				<UpperToolbar.Button displayType="secondary" onClick={onCancel}>
					{Liferay.Language.get('cancel')}
				</UpperToolbar.Button>

				<UpperToolbar.Button
					disabled={isDisabledSaveButton}
					displayType="secondary"
					onClick={onClickSave}
				>
					{Liferay.Language.get('save')}
				</UpperToolbar.Button>

				<ClayButton.Group className="ml-2">
					<ClayButton
						disabled={!app.active && isDisabledSaveButton}
						displayType={app.active ? 'secondary' : 'primary'}
						onClick={
							app.active
								? onClickUndeploy
								: () => setDeployModalVisible(true)
						}
						small
					>
						{app.active
							? Liferay.Language.get('undeploy')
							: Liferay.Language.get('deploy')}
					</ClayButton>

					{app.active && (
						<ClayTooltipProvider>
							<ClayButtonWithIcon
								data-tooltip-align="bottom"
								data-tooltip-delay="0"
								displayType="secondary"
								onClick={() => setDeployModalVisible(true)}
								small
								symbol="cog"
								title={Liferay.Language.get('deploy-settings')}
							/>
						</ClayTooltipProvider>
					)}
				</ClayButton.Group>
			</UpperToolbar.Group>
		</UpperToolbar>
	);
}
