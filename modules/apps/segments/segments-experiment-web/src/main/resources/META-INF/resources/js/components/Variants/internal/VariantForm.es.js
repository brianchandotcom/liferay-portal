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

import React, {useState, useRef, useEffect} from 'react';
import ClayAlert from '@clayui/alert';
import ValidatedInput from '../../ValidatedInput/ValidatedInput.es';
import ClayModal from '@clayui/modal';
import ClayButton from '@clayui/button';
import BusyButton from '../../BusyButton/BusyButton.es';

export default function VariantForm({
	onSave,
	onClose,
	name = '',
	errorMessage,
	title,
	variantId
}) {
	const [inputName, setInputName] = useState(name);
	const [error, setError] = useState(false);
	const [invalidForm, setInvalidForm] = useState(false);
	const [busy, setBusy] = useState(false);
	const mounted = useRef();

	useEffect(() => {
		mounted.current = true;
		return () => {
			mounted.current = false;
		};
	});

	return (
		<>
			<ClayModal.Header>{title}</ClayModal.Header>
			<ClayModal.Body>
				<form onSubmit={_handleSave}>
					{error && errorMessage && (
						<ClayAlert
							displayType="danger"
							title={Liferay.Language.get('error')}
						>
							{errorMessage}
						</ClayAlert>
					)}

					<ValidatedInput
						errorMessage={Liferay.Language.get('required')}
						label={Liferay.Language.get('name')}
						onChange={event => setInputName(event.target.value)}
						onValidationChange={setInvalidForm}
						value={inputName}
					/>
				</form>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
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
	);

	function _handleSave(event) {
		event.preventDefault();

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
}
