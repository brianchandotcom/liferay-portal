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

const ApplyBaselineModal = ({
	onSubmit = () => {},
	onClose = () => {},
	visible,
}) => {
	const {observer, onClose: handleClose} = useModal({
		onClose,
	});

	const _handleSubmit = () => {
		onSubmit();

		handleClose();
	};

	if (!visible) {
		return null;
	}

	return (
		<ClayModal className="preview-modal" observer={observer} size="md">
			<ClayModal.Header>
				{Liferay.Language.get('apply-baseline-setting')}
			</ClayModal.Header>

			<ClayModal.Body>
				{Liferay.Language.get(
					'this-will-reset-clause-contributors-to-the-baseline-setting'
				)}
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={handleClose}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							onClick={_handleSubmit}
						>
							{Liferay.Language.get('apply')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};

export default React.memo(ApplyBaselineModal);
