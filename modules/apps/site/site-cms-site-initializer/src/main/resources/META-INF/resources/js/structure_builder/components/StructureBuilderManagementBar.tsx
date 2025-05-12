/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {openConfirmModal} from '@liferay/layout-js-components-web';
import {ManagementToolbar, openToast} from 'frontend-js-components-web';
import {navigate} from 'frontend-js-web';
import React, {Dispatch} from 'react';

import {config} from '../config';
import {
	Action,
	State,
	useSelector,
	useStateDispatch,
} from '../contexts/StateContext';
import selectHistory from '../selectors/selectHistory';
import selectState from '../selectors/selectState';
import selectStructureERC from '../selectors/selectStructureERC';
import selectStructureFields from '../selectors/selectStructureFields';
import selectStructureId from '../selectors/selectStructureId';
import selectStructureLabel from '../selectors/selectStructureLabel';
import selectStructureLocalizedLabel from '../selectors/selectStructureLocalizedLabel';
import selectStructureName from '../selectors/selectStructureName';
import selectStructureSpaces from '../selectors/selectStructureSpaces';
import selectStructureStatus from '../selectors/selectStructureStatus';
import selectUnsavedChanges from '../selectors/selectUnsavedChanges';
import DisplayPageService from '../services/DisplayPageService';
import StructureService from '../services/StructureService';
import {useValidate} from '../utils/validation';
import AsyncButton from './AsyncButton';
import ManagementBar from './ManagementBar';

export default function StructureBuilderManagementBar() {
	const label = useSelector(selectStructureLocalizedLabel);
	const status = useSelector(selectStructureStatus);

	return (
		<ManagementBar
			backURL="structures"
			title={
				status === 'published'
					? label
					: Liferay.Language.get('new-structure')
			}
		>
			<ManagementToolbar.Item>
				<CustomizeExperienceButton />
			</ManagementToolbar.Item>

			<ManagementToolbar.Item>
				<div className="vertical-divider"></div>
			</ManagementToolbar.Item>

			<ManagementToolbar.Item>
				<ClayLink
					className="btn btn-outline-borderless btn-outline-secondary btn-sm"
					href="structures"
				>
					{Liferay.Language.get('cancel')}
				</ClayLink>
			</ManagementToolbar.Item>

			{status !== 'published' ? (
				<ManagementToolbar.Item>
					<SaveButton />
				</ManagementToolbar.Item>
			) : null}

			<ManagementToolbar.Item>
				<PublishButton />
			</ManagementToolbar.Item>
		</ManagementBar>
	);
}

function CustomizeExperienceButton() {
	const dispatch = useStateDispatch();
	const validate = useValidate();

	const history = useSelector(selectHistory);
	const localizedLabel = useSelector(selectStructureLocalizedLabel);
	const state = useSelector(selectState);
	const status = useSelector(selectStructureStatus);
	const structureId = useSelector(selectStructureId);
	const unsavedChanges = useSelector(selectUnsavedChanges);

	const onPublish = async ({id}: {id: State['id']}) => {
		openToast({
			message: Liferay.Util.sub(
				Liferay.Language.get(
					'x-was-published-successfully.-remember-to-review-the-customized-experience-if-needed'
				),
				localizedLabel
			),
			toastProps: {
				actions: (
					<ClayButton
						displayType="success"
						onClick={() => {
							const url = new URL(
								config.editStructureDisplayPageURL
							);

							url.searchParams.set(
								'objectDefinitionId',
								String(id)
							);

							navigate(url.toString());
						}}
						size="sm"
					>
						{Liferay.Language.get('customize-experience')}

						<ClayIcon className="ml-2" symbol="shortcut" />
					</ClayButton>
				),
			},
		});
	};

	return (
		<ClayButton
			className="font-weight-semi-bold"
			displayType="link"
			onClick={() => {
				if (status !== 'published' || unsavedChanges) {
					openConfirmModal({
						buttonLabel: Liferay.Language.get('publish'),
						center: true,
						onConfirm: async () => {
							await publishStructure({
								dispatch,
								onSuccess: onPublish,
								state,
								validate,
							});
						},
						status: 'warning',
						text: Liferay.Language.get(
							'to-customize-the-experience-you-need-to-publish-the-structure-first'
						),
						title: Liferay.Language.get(
							'publish-to-customize-experience'
						),
					});
				}
				else if (history.deletedFields) {
					openConfirmModal({
						buttonLabel: Liferay.Language.get('publish'),
						center: true,
						onConfirm: async () => {
							await publishStructure({
								checkDeletedFields: false,
								dispatch,
								onSuccess: onPublish,
								state,
								validate,
							});
						},
						status: 'danger',
						text: Liferay.Language.get(
							'to-customize-the-experience-you-need-to-publish-the-structure-first.-you-removed-one-or-more-fields-from-the-structure'
						),
						title: Liferay.Language.get(
							'publish-to-customize-experience'
						),
					});
				}
				else {
					const url = new URL(config.editStructureDisplayPageURL);

					url.searchParams.set(
						'objectDefinitionId',
						String(structureId)
					);

					navigate(url.toString());
				}
			}}
			size="sm"
		>
			{Liferay.Language.get('customize-experience')}

			<ClayIcon className="ml-2" symbol="shortcut" />
		</ClayButton>
	);
}

function SaveButton() {
	const dispatch = useStateDispatch();
	const validate = useValidate();

	const erc = useSelector(selectStructureERC);
	const fields = useSelector(selectStructureFields);
	const label = useSelector(selectStructureLabel);
	const localizedLabel = useSelector(selectStructureLocalizedLabel);
	const name = useSelector(selectStructureName);
	const spaces = useSelector(selectStructureSpaces);
	const status = useSelector(selectStructureStatus);
	const structureId = useSelector(selectStructureId);

	const onSave = async () => {
		const valid = validate();

		if (!valid) {
			return;
		}

		try {
			if (status === 'new') {
				const {id} = await StructureService.createStructure({
					erc,
					fields,
					label,
					name,
					spaces,
				});

				dispatch({id, type: 'create-structure'});
			}
			else {
				await StructureService.updateStructure({
					erc,
					fields,
					id: structureId,
					label,
					name,
					spaces,
				});

				dispatch({type: 'clear-error'});
			}

			openToast({
				message: Liferay.Util.sub(
					Liferay.Language.get('x-was-saved-successfully'),
					localizedLabel
				),
				type: 'success',
			});
		}
		catch (error) {
			const {message} = error as Error;

			dispatch({
				error:
					message ||
					Liferay.Language.get(
						'an-unexpected-error-occurred-while-saving-or-publishing-the-structure'
					),
				type: 'set-error',
			});
		}
	};

	return (
		<AsyncButton
			displayType="secondary"
			label={Liferay.Language.get('save')}
			onClick={onSave}
		/>
	);
}

function PublishButton() {
	const dispatch = useStateDispatch();
	const validate = useValidate();
	const state = useSelector(selectState);
	const localizedLabel = useSelector(selectStructureLocalizedLabel);

	const onPublish = async () => {
		await publishStructure({
			dispatch,
			onSuccess: () => {
				openToast({
					message: Liferay.Util.sub(
						Liferay.Language.get('x-was-published-successfully'),
						localizedLabel
					),
					type: 'success',
				});
			},
			state,
			validate,
		});
	};

	return (
		<AsyncButton
			displayType="primary"
			label={Liferay.Language.get('publish')}
			onClick={onPublish}
		/>
	);
}

async function publishStructure({
	checkDeletedFields = true,
	dispatch,
	onSuccess,
	state,
	validate,
}: {
	checkDeletedFields?: boolean;
	dispatch: Dispatch<Action>;
	onSuccess: ({id}: {id: State['id']}) => void;
	state: State;
	validate: () => boolean;
}) {
	const valid = validate();

	if (!valid) {
		return;
	}

	const history = selectHistory(state);

	if (checkDeletedFields && history.deletedFields) {
		if (
			!(await openConfirmModal({
				buttonLabel: Liferay.Language.get('publish'),
				center: true,
				status: 'danger',
				text: Liferay.Language.get(
					'you-removed-one-or-more-fields-from-the-structure'
				),
				title: Liferay.Language.get('publish-structure-changes'),
			}))
		) {
			return;
		}
	}

	const erc = selectStructureERC(state);
	const fields = selectStructureFields(state);
	const label = selectStructureLabel(state);

	const name = selectStructureName(state);
	const spaces = selectStructureSpaces(state);
	const status = selectStructureStatus(state);
	const structureId = selectStructureId(state);

	let id = structureId;

	try {
		if (status === 'new') {
			const {id: newId} = await StructureService.createStructure({
				erc,
				fields,
				label,
				name,
				spaces,
			});

			id = newId;

			await StructureService.publishStructure({id: newId});

			dispatch({id: newId, type: 'publish-structure'});
		}
		else if (status === 'draft') {
			await StructureService.updateStructure({
				erc,
				fields,
				id: structureId,
				label,
				name,
				spaces,
			});

			await StructureService.publishStructure({id: structureId});

			dispatch({type: 'publish-structure'});
		}
		else if (status === 'published') {
			await StructureService.updateStructure({
				erc,
				fields,
				id: structureId,
				label,
				name,
				spaces,
			});

			dispatch({type: 'publish-structure'});
		}

		if (config.autogeneratedDisplayPage) {
			await DisplayPageService.resetDisplayPage({id});
		}

		onSuccess({id});
	}
	catch (error) {
		const {message} = error as Error;

		dispatch({
			error:
				message ||
				Liferay.Language.get(
					'an-unexpected-error-occurred-while-saving-or-publishing-the-structure'
				),
			type: 'set-error',
		});
	}
}
