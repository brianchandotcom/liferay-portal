/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {openConfirmModal} from '@liferay/layout-js-components-web';
import {openToast} from 'frontend-js-components-web';
import {addParams, navigate} from 'frontend-js-web';
import React, {Dispatch} from 'react';

import StructureService from '../../common/services/StructureService';
import {config} from '../config';
import {CacheKey} from '../contexts/CacheContext';
import {Action, State} from '../contexts/StateContext';
import selectHistory from '../selectors/selectHistory';
import selectStructureChildren from '../selectors/selectStructureChildren';
import selectStructureERC from '../selectors/selectStructureERC';
import selectStructureId from '../selectors/selectStructureId';
import selectStructureLabel from '../selectors/selectStructureLabel';
import selectStructureLocalizedLabel from '../selectors/selectStructureLocalizedLabel';
import selectStructureName from '../selectors/selectStructureName';
import selectStructureSpaces from '../selectors/selectStructureSpaces';
import selectStructureStatus from '../selectors/selectStructureStatus';
import selectStructureUuid from '../selectors/selectStructureUuid';
import selectStructureWorkflows from '../selectors/selectStructureWorkflows';
import DisplayPageService from '../services/DisplayPageService';

type Props = {
	dispatch: Dispatch<Action>;
	showExperienceLink: boolean;
	showWarnings?: boolean;
	staleCache: (key: CacheKey) => void;
	state: State;
	validate: () => boolean;
};

export default async function handlePublishStructure({
	dispatch,
	showExperienceLink,
	showWarnings = true,
	staleCache,
	state,
	validate,
}: Props) {
	const valid = validate();

	if (!valid) {
		return;
	}

	const history = selectHistory(state);

	if (showWarnings) {
		if (
			config.isReferenced &&
			!history.deletedChildren.length &&
			!(await openConfirmModal({
				buttonLabel: Liferay.Language.get('publish-and-propagate'),
				center: true,
				status: 'warning',
				text: Liferay.Language.get(
					'this-content-structure-is-being-used-in-other-existing-content-structures'
				),
				title: Liferay.Language.get(
					'publish-content-structure-changes'
				),
			}))
		) {
			return;
		}

		if (
			!config.isReferenced &&
			history.deletedChildren.length &&
			!(await openConfirmModal({
				buttonLabel: Liferay.Language.get('publish'),
				center: true,
				status: 'danger',
				text: Liferay.Language.get(
					'you-have-made-changes-to-the-content-structure-that-may-impact-existing-stored-data-once-published'
				),
				title: Liferay.Language.get(
					'publish-content-structure-changes'
				),
			}))
		) {
			return;
		}

		if (
			config.isReferenced &&
			history.deletedChildren.length &&
			!(await openConfirmModal({
				buttonLabel: Liferay.Language.get('publish-and-propagate'),
				center: true,
				status: 'danger',
				text: Liferay.Language.get(
					'you-have-made-changes-to-the-content-structure-that-may-impact-existing-stored-data-once-published'
				),
				title: Liferay.Language.get(
					'publish-content-structure-changes'
				),
			}))
		) {
			return;
		}
	}

	const children = selectStructureChildren(state);
	const erc = selectStructureERC(state);
	const id = selectStructureId(state);
	const label = selectStructureLabel(state);
	const localizedLabel = selectStructureLocalizedLabel(state);
	const name = selectStructureName(state);
	const spaces = selectStructureSpaces(state);
	const status = selectStructureStatus(state);
	const structureId = selectStructureId(state);
	const workflows = selectStructureWorkflows(state);
	const uuid = selectStructureUuid(state);

	const onSuccess = async () => {
		staleCache('object-definitions');

		if (!showExperienceLink) {
			openToast({
				message: Liferay.Util.sub(
					Liferay.Language.get('x-was-published-successfully'),
					localizedLabel
				),
				type: 'success',
			});

			return;
		}

		openToast({
			message: Liferay.Util.sub(
				Liferay.Language.get(
					'x-was-published-successfully.-remember-to-review-the-customized-editor-if-needed'
				),
				localizedLabel
			),
			toastProps: {
				actions: (
					<ClayButton
						displayType="success"
						onClick={() => {
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
						}}
						size="sm"
					>
						{Liferay.Language.get('customize-editor')}

						<ClayIcon className="ml-2" symbol="shortcut" />
					</ClayButton>
				),
			},
		});
	};

	const previousStatus = state.structure.status;

	const onError = () =>
		dispatch({
			error: 'unexpected',
			property: 'global',
			status: previousStatus,
			type: 'add-error',
			uuid,
		});

	dispatch({status: 'publishing', type: 'set-structure-status'});

	if (status === 'new') {
		const {data, error} = await StructureService.createStructure({
			children,
			erc,
			label,
			name,
			spaces,
			status: 'published',
			workflows,
		});

		if (error) {
			onError();

			return;
		}
		else if (data) {
			dispatch({id: data.id, type: 'publish-structure'});
		}
	}
	else if (status === 'draft') {
		const {error} = await StructureService.updateStructure({
			children,
			erc,
			history,
			id,
			label,
			name,
			spaces,
			status: 'published',
			workflows,
		});

		if (error) {
			onError();

			return;
		}
		else {
			dispatch({type: 'publish-structure'});
		}
	}
	else if (status === 'published') {
		const {error} = await StructureService.updateStructure({
			children,
			erc,
			history,
			id,
			label,
			name,
			spaces,
			status: 'published',
			workflows,
		});

		if (error) {
			onError();

			return;
		}
		else {
			dispatch({type: 'publish-structure'});
		}
	}

	if (status === 'published') {
		if (config.autogeneratedDisplayPage) {
			await DisplayPageService.resetDisplayPage({id: structureId});
		}

		await DisplayPageService.resetTranslationDisplayPage({id: structureId});
	}

	onSuccess();
}
