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

import React, {useState, useEffect, useRef} from 'react';
import ClayModal from '@clayui/modal';
import ClayButton from '@clayui/button';
import ClayAlert from '@clayui/alert';
import BusyButton from '../../busyButton/BusyButton.es';
import ValidatedInput from '../../ValidatedInput/ValidatedInput.es';

function VariantModal({
	active = true,
	errorMessage,
	name = '',
	onClose = () => {},
	onSave = () => {},
	title,
	variantId
}) {
	const [busy, setBusy] = useState(false);
	const [error, setError] = useState(false);
	const [inputName, setInputName] = useState(name);
	const [invalidForm, setInvalidForm] = useState(false);
	const mounted = useRef();

	useEffect(() => {
		mounted.current = true;
		return () => {
			mounted.current = false;
		};
	});

	return active ? (
		<ClayModal onClose={_handleClose} size="md">
			{onClose => (
				<>
					{title && <ClayModal.Header>{title}</ClayModal.Header>}
					<ClayModal.Body>
						<form onSubmit={_handleFormSubmit}>
							{error && errorMessage && (
								<ClayAlert
									displayType="danger"
									title={Liferay.Language.get('error')}
								>
									{errorMessage}
								</ClayAlert>
							)}

							<ValidatedInput
								errorMessage={Liferay.Language.get(
									'variant-name-is-required'
								)}
								label={Liferay.Language.get('name')}
								onChange={event =>
									setInputName(event.target.value)
								}
								onValidationChange={setInvalidForm}
								value={inputName}
							/>
						</form>
					</ClayModal.Body>

					<ClayModal.Footer
						last={
							<ClayButton.Group spaced>
								<ClayButton
									disabled={busy}
									displayType="secondary"
									onClick={onClose}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>
								<BusyButton
									busy={busy}
									disabled={busy || invalidForm}
									displayType="primary"
									onClick={_handleSave}
								>
									{Liferay.Language.get('save')}
								</BusyButton>
							</ClayButton.Group>
						}
					/>
				</>
			)}
		</ClayModal>
	) : null;

	function _handleSave() {
		if (!invalidForm) {
			setBusy(true);
			onSave({name: inputName, variantId})
				.then(() => {
					if (mounted.current) {
						setBusy(false);
						onClose();
					}
				})
				.catch(() => {
					if (mounted.current) {
						setBusy(false);
						setError(true);
					}
				});
		}
	}

	function _handleFormSubmit(event) {
		event.preventDefault();

		_handleSave();
	}

	function _handleClose() {
		onClose();
	}
}

export default VariantModal;
