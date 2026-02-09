/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import ClayModal from '@clayui/modal';
import {openModal} from 'frontend-js-components-web';
import React, {useState} from 'react';

export type DeletionActionType = 'delete-children';

type Data = {
	body: string;
	buttonLabel: string;
	sessionKey: string;
	title: string;
};

export default async function confirmDeletionAction(type: DeletionActionType) {
	const data = getData(type);

	if (!data) {
		return false;
	}

	if (await isModalDisabled(data.sessionKey)) {
		return true;
	}

	return openDeletionModal(data);
}

function getData(type: DeletionActionType): Data | undefined {
	if (type === 'delete-children') {
		return {
			body: Liferay.Language.get(
				'deleting-fields-may-impact-existing-stored-data-after-publishing-the-structure'
			),
			buttonLabel: Liferay.Language.get('delete'),
			sessionKey: 'disableChildrenDeletionModal',
			title: Liferay.Language.get('delete-fields'),
		};
	}
}

async function isModalDisabled(key: string) {
	const value = await Liferay.Util.Session.get(key);

	return value === 'true';
}

function disableModal(key: string) {
	Liferay.Util.Session.set(key, 'true');
}

function openDeletionModal(data: Data) {
	return new Promise((resolve) => {
		openModal({
			center: true,
			contentComponent: ({closeModal}: {closeModal: () => void}) =>
				ModalContent({
					...data,
					onCancel: () => {
						closeModal();

						resolve(false);
					},
					onConfirm: () => {
						closeModal();

						resolve(true);
					},
				}),
			status: 'warning',
		});
	});
}

function ModalContent({
	body,
	buttonLabel,
	onCancel,
	onConfirm,
	sessionKey,
	title,
}: {
	body: string;
	buttonLabel: string;
	onCancel: () => void;
	onConfirm: () => void;
	sessionKey: string;
	title: string;
}) {
	const [disable, setDisable] = useState(false);

	return (
		<>
			<ClayModal.Header>{title}</ClayModal.Header>

			<ClayModal.Body>
				<p className="mb-0">{body}</p>
			</ClayModal.Body>

			<ClayModal.Footer
				first={
					<ClayCheckbox
						checked={disable}
						label={Liferay.Language.get(
							'do-not-show-me-this-again'
						)}
						onChange={({target: {checked}}) => setDisable(checked)}
					/>
				}
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => {
								onCancel();

								if (disable) {
									disableModal(sessionKey);
								}
							}}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="warning"
							onClick={() => {
								onConfirm();

								if (disable) {
									disableModal(sessionKey);
								}
							}}
						>
							{buttonLabel}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}
