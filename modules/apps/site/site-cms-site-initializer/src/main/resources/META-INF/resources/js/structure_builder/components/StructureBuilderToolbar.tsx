/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {openConfirmModal} from '@liferay/layout-js-components-web';
import {addParams, navigate} from 'frontend-js-web';
import React, {useEffect} from 'react';

import Toolbar from '../../common/components/Toolbar';
import {config} from '../config';
import {useCache, useStaleCache} from '../contexts/CacheContext';
import {useSelector, useStateDispatch} from '../contexts/StateContext';
import selectHistory from '../selectors/selectHistory';
import selectState from '../selectors/selectState';
import selectStructureId from '../selectors/selectStructureId';
import selectStructureLocalizedLabel from '../selectors/selectStructureLocalizedLabel';
import selectStructureStatus from '../selectors/selectStructureStatus';
import selectUnsavedChanges from '../selectors/selectUnsavedChanges';
import {publishStructure} from '../utils/publishStructure';
import {saveStructure} from '../utils/saveStructure';
import {useValidate} from '../utils/validation';
import AsyncButton from './AsyncButton';

export default function StructureBuilderToolbar() {
	const label = useSelector(selectStructureLocalizedLabel);
	const status = useSelector(selectStructureStatus);

	const dispatch = useStateDispatch();

	const {load, status: objectDefinitionStatus} =
		useCache('object-definitions');

	useEffect(() => {
		if (objectDefinitionStatus === 'stale') {
			load().then((objectDefinitions) =>
				dispatch({
					objectDefinitions,
					type: 'refresh-referenced-structures',
				})
			);
		}
	}, [dispatch, load, objectDefinitionStatus]);

	return (
		<Toolbar
			backURL="structures"
			title={
				status === 'published'
					? label
					: Liferay.Language.get('new-content-structure')
			}
		>
			<Toolbar.Item className="nav-divider-end">
				<CustomizeEditorButton />
			</Toolbar.Item>

			<Toolbar.Item>
				<ClayLink
					className="btn btn-outline-borderless btn-outline-secondary btn-sm"
					href="structures"
				>
					{Liferay.Language.get('cancel')}
				</ClayLink>
			</Toolbar.Item>

			{status !== 'published' ? (
				<Toolbar.Item>
					<SaveButton />
				</Toolbar.Item>
			) : null}

			<Toolbar.Item>
				<PublishButton />
			</Toolbar.Item>
		</Toolbar>
	);
}

function CustomizeEditorButton() {
	const dispatch = useStateDispatch();
	const validate = useValidate();

	const history = useSelector(selectHistory);
	const state = useSelector(selectState);
	const status = useSelector(selectStructureStatus);
	const structureId = useSelector(selectStructureId);
	const unsavedChanges = useSelector(selectUnsavedChanges);

	const staleCache = useStaleCache();

	return (
		<ClayButton
			borderless
			className="font-weight-semi-bold mr-2"
			displayType="primary"
			onClick={() => {
				if (status === 'published' && history.deletedChildren) {
					openConfirmModal({
						buttonLabel: Liferay.Language.get('publish'),
						center: true,
						onConfirm: async () => {
							await publishStructure({
								dispatch,
								showExperienceLink: true,
								showWarnings: false,
								staleCache,
								state,
								validate,
							});
						},
						status: 'danger',
						text: Liferay.Language.get(
							'to-customize-the-editor-you-need-to-publish-the-content-structure-first.-you-removed-one-or-more-fields-from-the-content-structure'
						),
						title: Liferay.Language.get(
							'publish-to-customize-editor'
						),
					});
				}
				else if (status !== 'published' || unsavedChanges) {
					openConfirmModal({
						buttonLabel: Liferay.Language.get('publish'),
						center: true,
						onConfirm: async () => {
							await publishStructure({
								dispatch,
								showExperienceLink: true,
								staleCache,
								state,
								validate,
							});
						},
						status: 'warning',
						text: Liferay.Language.get(
							'to-customize-the-editor-you-need-to-publish-the-content-structure-first'
						),
						title: Liferay.Language.get(
							'publish-to-customize-editor'
						),
					});
				}
				else {
					const editStructureDisplayPageURL = addParams(
						{
							backURL: addParams(
								{
									objectDefinitionId: structureId,
								},
								config.structureBuilderURL
							),
							objectDefinitionId: structureId,
						},
						config.editStructureDisplayPageURL
					);

					navigate(editStructureDisplayPageURL);
				}
			}}
			size="sm"
		>
			{Liferay.Language.get('customize-editor')}

			<ClayIcon className="ml-2" symbol="shortcut" />
		</ClayButton>
	);
}

function SaveButton() {
	const dispatch = useStateDispatch();
	const validate = useValidate();

	const state = useSelector(selectState);

	const onSave = async () => {
		await saveStructure({
			dispatch,
			state,
			validate,
		});
	};

	const {status} = state.structure;

	return (
		<AsyncButton
			displayType="secondary"
			label={Liferay.Language.get('save')}
			onClick={onSave}
			status={status === 'saving' ? 'loading' : 'idle'}
		/>
	);
}

function PublishButton() {
	const dispatch = useStateDispatch();
	const validate = useValidate();
	const state = useSelector(selectState);

	const staleCache = useStaleCache();

	const onPublish = async () => {
		await publishStructure({
			dispatch,
			showExperienceLink: !config.autogeneratedDisplayPage,
			staleCache,
			state,
			validate,
		});
	};

	const {status} = state.structure;

	return (
		<AsyncButton
			displayType="primary"
			label={Liferay.Language.get('publish')}
			onClick={onPublish}
			status={status === 'publishing' ? 'loading' : 'idle'}
		/>
	);
}
