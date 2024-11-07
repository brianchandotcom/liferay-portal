/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Text} from '@clayui/core';
import ClayModal, {ClayModalProvider, useModal} from '@clayui/modal';
import React from 'react';

interface ModalDisableInheritanceProps {
	setValues: (value: Partial<ObjectRelationship>) => void;
	setVisibleModal: (value: boolean) => void;
	values: Partial<ObjectRelationship>;
}

export function ModalDisableInheritance({
	setValues,
	setVisibleModal,
	values,
}: ModalDisableInheritanceProps) {
	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	const warningMessages = [
		Liferay.Language.get('permissions-are-copied-from-the-old-parent'),
		Liferay.Language.get(
			'rest-endpoints-are-created-for-the-new-parent-with-the-endpoints-of-remaining-children-grouped-under-it'
		),
		Liferay.Language.get(
			'an-object-action-trigger-for-updating-child-objects-is-created'
		),
		Liferay.Language.get(
			'options-for-showing-the-widget-in-the-page-builder-and-for-configuring-the-panel-link-become-available'
		),
	];

	return (
		<ClayModalProvider>
			<ClayModal center observer={observer} status="warning">
				<ClayModal.Header>
					{Liferay.Language.get('disable-inheritance')}
				</ClayModal.Header>

				<ClayModal.Body className="c-gap-4 d-flex flex-column">
					<Text>
						{Liferay.Language.get(
							'when-you-disconnect-a-parent-from-inheritance-the-first-child-object-becomes-the-new-parent'
						)}
					</Text>

					<ol>
						{warningMessages.map((warningMessage) => (
							<li key={warningMessage}>
								<Text>{warningMessage}</Text>
							</li>
						))}
					</ol>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={onClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								displayType="warning"
								onClick={() => {
									setValues({
										...values,
										edge: false,
									});

									onClose();
								}}
							>
								{Liferay.Language.get('disable')}
							</ClayButton>
						</ClayButton.Group>
					}
				></ClayModal.Footer>
			</ClayModal>
		</ClayModalProvider>
	);
}
