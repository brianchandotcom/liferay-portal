/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

export function InstallFragmentModalBody() {
	return (
		<>
			<ClayLoadingIndicator
				className="mb-4 mt-2"
				displayType="primary"
				shape="squares"
				size="md"
			/>

			<div className="ml-4 mr-4 text-center text-secondary">
				{Liferay.Language.get(
					'the-installation-process-is-ongoing-and-may-take-some-time'
				)}

				{Liferay.Language.get(
					'closing-the-window-will-not-cancel-the-process'
				)}
			</div>
		</>
	);
}

export default function InstallFragmentModal({
	name,
	onCloseModal = () => {},
}: {
	name: string;
	onCloseModal?: () => void;
}) {
	const [visible, setVisible] = useState(true);

	const {observer} = useModal({
		onClose: () => {
			setVisible(false);
			onCloseModal();
		},
	});

	return (
		visible && (
			<ClayModal className="modal-dialog-centered" observer={observer}>
				<ClayModal.Header>
					{sub(Liferay.Language.get('installing-x'), name)}
				</ClayModal.Header>

				<ClayModal.Body>
					<InstallFragmentModalBody />
				</ClayModal.Body>
			</ClayModal>
		)
	);
}
