/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {sub} from 'frontend-js-web';
import React from 'react';

export default function MultipleFilesUploadModalContent({
	closeModal,
}: {
	closeModal: () => void;
}) {
	return (
		<form>
			<ClayModal.Header>
				{sub(
					Liferay.Language.get('upload-x'),
					Liferay.Language.get('multiple-files')
				)}
			</ClayModal.Header>

			<ClayModal.Body></ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={closeModal}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton>
							{Liferay.Language.get('upload')}
						</ClayButton>
					</ClayButton.Group>
				}
			></ClayModal.Footer>
		</form>
	);
}
