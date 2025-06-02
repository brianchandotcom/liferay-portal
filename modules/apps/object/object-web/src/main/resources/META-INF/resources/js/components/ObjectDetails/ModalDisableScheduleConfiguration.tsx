/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Text} from '@clayui/core';
import ClayModal, {useModal} from '@clayui/modal';
import React, {useEffect, useState} from 'react';

export default function ModalDisableScheduleConfiguration() {
	const [handleDisable, setHandleDisable] = useState<() => Promise<void>>();
	const [visible, setVisible] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => {
			setVisible(false);
		},
	});

	useEffect(() => {
		const openModal = ({
			handleDisable,
		}: {
			handleDisable: () => Promise<void>;
		}) => {
			setHandleDisable(() => handleDisable);
			setVisible(true);
		};

		Liferay.on('openModalDisableScheduleConfiguration', openModal);

		return () =>
			Liferay.detach(
				'openModalDisableScheduleConfiguration',
				openModal as () => void
			);
	}, []);

	return (
		<>
			{visible && (
				<ClayModal center observer={observer} status="warning">
					<ClayModal.Header>
						{Liferay.Language.get(
							'disable-schedule-for-object-entries'
						)}
					</ClayModal.Header>

					<ClayModal.Body>
						<Text>
							{Liferay.Language.get(
								'this-object-is-already-published-and-might-have-scheduled-entries-by-disabling-this-setting-the-scheduled-actions-will-not-happen'
							)}
						</Text>
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
						}
					></ClayModal.Footer>
				</ClayModal>
			)}
		</>
	);
}
