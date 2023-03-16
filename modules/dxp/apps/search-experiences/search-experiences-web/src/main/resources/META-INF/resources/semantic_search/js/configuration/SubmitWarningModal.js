/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import React from 'react';

export default function SubmitWarningModal({
	message,
	onClose = () => {},
	onSubmit = () => {},
	visible,
}) {
	const {observer, onClose: handleClose} = useModal({
		onClose,
	});

	if (!visible) {
		return null;
	}

	return (
		<ClayModal observer={observer} status="warning">
			<ClayModal.Header>
				{Liferay.Language.get('warning')}
			</ClayModal.Header>

			<ClayModal.Body>{message}</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={handleClose}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton onClick={onSubmit}>
							{Liferay.Language.get('continue-to-save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}
