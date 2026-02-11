/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayModal from '@clayui/modal';
import {openModal} from 'frontend-js-components-web';
import React, {useRef} from 'react';

import {IItemsActions} from '../types';

const WorkflowTransitionModalComponent = ({
	closeModal,
	namespace,
	onTransitionSave,
	title,
}: {
	closeModal: Function;
	namespace: string;
	onTransitionSave: ({comment}: {comment: string}) => void;
	title: string;
}) => {
	const commentInputRef =
		useRef() as React.MutableRefObject<HTMLInputElement>;

	const fieldId = `${namespace}_transitionCommentInput`;

	return (
		<>
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				{title}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm.Group>
					<label htmlFor={fieldId}>
						{Liferay.Language.get('comment')}
					</label>

					<ClayInput
						autoFocus={true}
						id={fieldId}
						placeholder={Liferay.Language.get('leave-a-comment')}
						ref={commentInputRef}
						type="text"
					/>
				</ClayForm.Group>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							onClick={() => {
								closeModal();
								onTransitionSave({
									comment: commentInputRef.current.value,
								});
							}}
						>
							{Liferay.Language.get('save')}
						</ClayButton>

						<ClayButton
							displayType="secondary"
							onClick={() => closeModal()}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
};

export function openWorkflowTransitionModal({
	action,
	executeAsyncItemAction,
	itemId,
}: {
	action: IItemsActions;
	executeAsyncItemAction: Function;
	itemId: string | number;
}): void {
	const {data, id} = action;

	const title = data?.title ?? Liferay.Language.get('transition');

	const requestBody = data?.requestBody ?? '{}';

	openModal({
		contentComponent: ({closeModal}: {closeModal: Function}) => (
			<WorkflowTransitionModalComponent
				closeModal={closeModal}
				namespace={`${id}_${itemId}`}
				onTransitionSave={({comment}: {comment: string}) => {
					executeAsyncItemAction({
						method: action.method,
						requestBody: JSON.stringify({
							...JSON.parse(requestBody),
							comment,
						}),
						url: action.href,
					});
				}}
				title={title}
			/>
		),
	});
}
