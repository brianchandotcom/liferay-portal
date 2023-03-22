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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import React, {useEffect, useRef, useState} from 'react';

import BusyButton from '../../BusyButton/BusyButton.es';
import ValidatedInput from '../../ValidatedInput/ValidatedInput.es';

export default function VariantForm({
	errorMessage,
	name = '',
	onClose,
	onSave,
	title,
	variantId,
}) {
	const [inputName, setInputName] = useState(name);
	const [error, setError] = useState(false);
	const [invalidForm, setInvalidForm] = useState(false);
	const [busy, setBusy] = useState(false);
	const mountedRef = useRef();

	const onSubmit = (event) => {
		event.preventDefault();

		if (!invalidForm) {
			setBusy(true);

			onSave({name: inputName, variantId})
				.then(() => {
					if (mountedRef.current) {
						setBusy(false);
						onClose();
					}
				})
				.catch(() => {
					if (mountedRef.current) {
						setBusy(false);
						setError(true);
					}
				});
		}
	};

	useEffect(() => {
		mountedRef.current = true;

		return () => {
			mountedRef.current = false;
		};
	});

	return (
		<form onSubmit={onSubmit}>
			<ClayModal.Header>{title}</ClayModal.Header>

			<ClayModal.Body>
				{error && errorMessage && (
					<ClayAlert
						displayType="danger"
						title={Liferay.Language.get('error')}
					>
						{errorMessage}
					</ClayAlert>
				)}

				<ValidatedInput
					autofocus
					errorMessage={Liferay.Language.get(
						'variant-name-is-required'
					)}
					label={Liferay.Language.get('name')}
					onChange={(event) => setInputName(event.target.value)}
					onValidationChange={setInvalidForm}
					required
					value={inputName}
				/>
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
							onClick={onSubmit}
							type="submit"
						>
							{Liferay.Language.get('save')}
						</BusyButton>
					</ClayButton.Group>
				}
			/>
		</form>
	);
}
