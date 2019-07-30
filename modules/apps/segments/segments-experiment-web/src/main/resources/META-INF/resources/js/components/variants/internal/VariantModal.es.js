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

import React, {useState} from 'react';
import ClayModal from '@clayui/modal';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayAlert from '@clayui/alert';
import BusyButton from '../../busyButton/BusyButton.es';

function VariantModal({
	active = true,
	name = '',
	onClose = () => {},
	onSave = () => {}
}) {
	const [inputName, setInputName] = useState(name);
	const [busy, setBusy] = useState(false);
	const [error, setError] = useState(false);

	return active ? (
		<ClayModal onClose={_handleClose} size="md">
			{onClose => (
				<>
					<ClayModal.Header>
						{Liferay.Language.get('create-new-variant')}
					</ClayModal.Header>
					<ClayModal.Body>
						{error && (
							<ClayAlert
								displayType="danger"
								title={Liferay.Language.get('error')}
							>
								{error}
							</ClayAlert>
						)}
						<label className="form-group d-block">
							<span className="d-inline-block mb-2">
								{Liferay.Language.get('name')}
								<ClayIcon
									className="reference-mark text-warning ml-1"
									symbol="asterisk"
								/>
							</span>
							<input
								className="form-control"
								onChange={event =>
									setInputName(event.target.value)
								}
								ref={input => input && input.focus()}
								type="text"
								value={inputName}
							/>
						</label>
					</ClayModal.Body>
					<ClayModal.Footer
						first={
							<ClayButton.Group spaced>
								<BusyButton
									busy={busy}
									disabled={busy}
									displayType="primary"
									onClick={_handleSave}
								>
									{Liferay.Language.get('save')}
								</BusyButton>
								<ClayButton
									disabled={busy}
									displayType="secondary"
									onClick={onClose}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>
							</ClayButton.Group>
						}
					/>
				</>
			)}
		</ClayModal>
	) : null;

	function _handleSave() {
		setBusy(true);
		onSave(inputName)
			.then(() => {
				setBusy(false);
				onClose();
			})
			.catch(() => {
				setBusy(false);
				setError(Liferay.Language.get('create-variant-error-message'));
			});
	}

	function _handleClose() {
		onClose();
	}
}

export default VariantModal;
