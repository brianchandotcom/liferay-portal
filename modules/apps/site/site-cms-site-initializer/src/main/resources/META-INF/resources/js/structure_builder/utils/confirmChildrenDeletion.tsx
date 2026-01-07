/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import ClayModal from '@clayui/modal';
import {openModal} from 'frontend-js-components-web';
import React, {useState} from 'react';

export default async function confirmChildrenDeletion() {
	if (await isModalDisabled()) {
		return true;
	}

	return openDeletionModal();
}

async function isModalDisabled() {
	const value = await Liferay.Util.Session.get(`disableDeletionModal`);

	return value === 'true';
}

function disableModal() {
	Liferay.Util.Session.set(`disableDeletionModal`, 'true');
}

function openDeletionModal() {
	return new Promise((resolve) => {
		openModal({
			center: true,
			contentComponent: ({closeModal}: {closeModal: () => void}) =>
				ModalContent({
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
	onCancel,
	onConfirm,
}: {
	onCancel: () => void;
	onConfirm: () => void;
}) {
	const [disable, setDisable] = useState(false);

	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('delete-fields')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p className="mb-0">
					{Liferay.Language.get(
						'deleting-fields-may-impact-existing-stored-data-after-publishing-the-structure'
					)}
				</p>
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
									disableModal();
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
									disableModal();
								}
							}}
						>
							{Liferay.Language.get('delete')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}
