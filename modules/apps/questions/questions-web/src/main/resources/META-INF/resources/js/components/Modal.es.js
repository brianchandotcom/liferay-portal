/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import React from 'react';

export default ({
	body,
	callback,
	onClose,
	status = 'info',
	textPrimaryButton = 'Save',
	textSecondaryButton = 'Cancel',
	title,
	visible,
}) => {
	const {observer, onClose: close} = useModal({
		onClose,
	});

	return (
		<>
			{visible && (
				<ClayModal
					className="d-flex justify-content-center"
					observer={observer}
					status={status}
				>
					<ClayModal.Header>{title}</ClayModal.Header>
					<ClayModal.Body>{body}</ClayModal.Body>
					<ClayModal.Footer
						first={
							<ClayButton displayType="secondary" onClick={close}>
								{textSecondaryButton}
							</ClayButton>
						}
						last={
							<ClayButton
								displayType="primary"
								onClick={() => {
									callback();
									close();
								}}
							>
								{textPrimaryButton}
							</ClayButton>
						}
					/>
				</ClayModal>
			)}
		</>
	);
};
