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

import {default as ClayButton} from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import PropTypes from 'prop-types';
import React from 'react';

export function StyleErrorsModal({onCloseModal, onSubmit}) {
	const {observer, onClose} = useModal({
		onClose: onCloseModal,
	});

	return (
		<ClayModal
			aria-label={Liferay.Language.get('style-errors-detected')}
			observer={observer}
			status="warning"
		>
			<ClayModal.Header>
				{Liferay.Language.get('style-errors-detected')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p>
					{Liferay.Language.get(
						'some-of-the-fields-have-invalid-values-if-you-continue-publishing-the-latest-valid-values-will-display'
					)}
				</p>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="warning"
							onClick={onSubmit}
							type="submit"
						>
							{Liferay.Language.get('continue')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}

StyleErrorsModal.propTypes = {
	onCloseModal: PropTypes.func.isRequired,
	onSubmit: PropTypes.func.isRequired,
};
