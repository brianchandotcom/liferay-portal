/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Text} from '@clayui/core';
import ClayModal, {ClayModalProvider, useModal} from '@clayui/modal';
import React, {useEffect, useState} from 'react';

interface OpenModalPayload {
	handleDisable?: () => Promise<void>;
	isBlocked?: boolean;
}

function ModalDisableInheritance() {
	const [handleDisable, setHandleDisable] = useState<() => Promise<void>>();
	const [isBlocked, setIsBlocked] = useState(false);
	const [visible, setVisible] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => {
			setVisible(false);
		},
	});

	useEffect(() => {
		const openModal = ({handleDisable, isBlocked}: OpenModalPayload) => {
			setHandleDisable(() => handleDisable);
			setIsBlocked(!!isBlocked);
			setVisible(true);
		};

		Liferay.on('openModalDisableInheritance', openModal);

		return () =>
			Liferay.detach(
				'openModalDisableInheritance',
				openModal as () => void
			);
	}, []);

	if (!visible) {
		return null;
	}

	return (
		<ClayModalProvider>
			<ClayModal
				center
				observer={observer}
				status={isBlocked ? 'danger' : 'warning'}
			>
				<ClayModal.Header
					closeButtonAriaLabel={Liferay.Language.get('close')}
				>
					{isBlocked
						? Liferay.Language.get(
								'disabling-inheritance-not-allowed'
							)
						: Liferay.Language.get(
								'disable-inheritance-confirmation'
							)}
				</ClayModal.Header>

				<ClayModal.Body className="c-gap-4 d-flex flex-column">
					<Text>
						{isBlocked
							? Liferay.Language.get(
									'this-object-requires-all-entries-to-have-a-parent'
								)
							: Liferay.Language.get(
									'when-you-disable-inheritance-the-regular-relationship-is-restored'
								)}
					</Text>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						isBlocked ? (
							<ClayButton displayType="primary" onClick={onClose}>
								{Liferay.Language.get('done')}
							</ClayButton>
						) : (
							<ClayButton.Group spaced>
								<ClayButton
									displayType="secondary"
									onClick={onClose}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>

								<ClayButton
									displayType="warning"
									onClick={async () => {
										if (handleDisable) {
											await handleDisable();
										}

										onClose();
									}}
								>
									{Liferay.Language.get('disable')}
								</ClayButton>
							</ClayButton.Group>
						)
					}
				></ClayModal.Footer>
			</ClayModal>
		</ClayModalProvider>
	);
}

export default ModalDisableInheritance;
