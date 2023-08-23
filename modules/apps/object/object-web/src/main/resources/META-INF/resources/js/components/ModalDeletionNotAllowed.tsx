/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import {sub} from 'frontend-js-web';
import React from 'react';

interface ModalDeletionNotAllowedProps {
	onVisibilityChange: () => void;
	selectedItemLabel: string;
}

export function ModalDeletionNotAllowed({
	onVisibilityChange,
	selectedItemLabel,
}: ModalDeletionNotAllowedProps) {
	const {observer, onClose} = useModal({
		onClose: () => onVisibilityChange(),
	});

	return (
		<ClayModal center observer={observer} status="warning">
			<ClayModal.Header>
				{Liferay.Language.get('deletion-not-allowed')}
			</ClayModal.Header>

			<ClayModal.Body>
				<span
					dangerouslySetInnerHTML={{
						__html: sub(
							Liferay.Language.get(
								'x-is-being-used-by-a-root-object-and-cannot-be-deleted'
							),
							`<strong>"${selectedItemLabel}"</strong>`
						),
					}}
				/>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group key={1} spaced>
						<ClayButton
							displayType="warning"
							onClick={() => onClose()}
						>
							{Liferay.Language.get('done')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}
