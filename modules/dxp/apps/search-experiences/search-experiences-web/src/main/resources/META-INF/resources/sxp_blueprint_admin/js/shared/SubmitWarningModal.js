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

import ErrorListItem from './ErrorListItem';

const SubmitWarningModal = ({
	errors,
	isSubmitting,
	message,
	onClose = () => {},
	onSubmit = () => {},
	visible,
}) => {
	const {observer, onClose: handleClose} = useModal({
		onClose,
	});

	if (!visible) {
		return null;
	}

	return (
		<ClayModal observer={observer} size="md" status="warning">
			<ClayModal.Header>
				{Liferay.Language.get('warning')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p>{message}</p>

				{errors.map((error, index) => (
					<ErrorListItem error={error} key={index} />
				))}
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

						<ClayButton disabled={isSubmitting} onClick={onSubmit}>
							{Liferay.Language.get('continue-to-save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};

export default SubmitWarningModal;
