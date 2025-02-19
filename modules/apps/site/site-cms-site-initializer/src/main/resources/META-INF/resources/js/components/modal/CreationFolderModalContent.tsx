/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import Form, {ClayInput} from '@clayui/form';
import ClayModal from '@clayui/modal';
import React from 'react';

export default function CreationFolderModalContent({
	closeModal,
}: {
	closeModal: voidReturn;
}) {
	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('new-folder')}
			</ClayModal.Header>
			<ClayModal.Body>
				<Form.Group>
					<label htmlFor="folferName">
						{Liferay.Language.get('name')}
					</label>

					<ClayInput id="folferName" type="text" />
				</Form.Group>
			</ClayModal.Body>
			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={closeModal}
							type="button"
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton displayType="primary" type="button">
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}
