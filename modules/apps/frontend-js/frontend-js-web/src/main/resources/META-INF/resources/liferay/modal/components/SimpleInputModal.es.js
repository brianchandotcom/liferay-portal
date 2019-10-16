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

import {fetch, navigate} from 'frontend-js-web';
import React, {useState} from 'react';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';

/**
 * Manipulates small amounts of data with a form shown inside a modal.
 */
const SimpleInputModal = ({
	cleanUp,
	dialogTitle,
	formSubmitURL,
	idFieldName,
	idFieldValue,
	initialVisible,
	mainFieldName,
	mainFieldLabel,
	namespace,
	placeholder
}) => {
	const [loadingResponse, setLoadingResponse] = useState(false);
	const [visible, setVisible] = useState(initialVisible);
	const [value, setValue] = useState('');

	const _handleSubmit = event => {
		event.preventDefault();

		const formData = new FormData(
			document.querySelector(`#${namespace}form`)
		);

		fetch(formSubmitURL, {
			body: formData,
			method: 'POST'
		})
			.then(response => response.json())
			.then(responseContent => {
				if (responseContent.error) {
					setLoadingResponse(false);
				} else {
					navigate(responseContent.redirectURL);
				}
			});

		setLoadingResponse(true);
	};

	const {observer, onClose} = useModal({
		onClose: () => {
			setVisible(false);

			Liferay.once('destroyPortlet', cleanUp);
		}
	});

	return (
		visible && (
			<ClayModal observer={observer} size="md">
				<ClayModal.Header>{dialogTitle}</ClayModal.Header>

				<form id={`${namespace}form`} onSubmit={_handleSubmit}>
					<ClayModal.Body>
						<input
							name={`${namespace}${idFieldName}`}
							type="hidden"
							value={idFieldValue}
						/>

						<label
							className="control-label"
							htmlFor={`${namespace}${mainFieldName}`}
						>
							{mainFieldLabel}

							<span className="reference-mark">
								<ClayIcon symbol="asterisk" />
							</span>
						</label>

						<input
							autoFocus
							className="form-control"
							disabled={loadingResponse}
							id={`${namespace}${mainFieldName}`}
							name={`${namespace}${mainFieldName}`}
							onChange={event => setValue(event.target.value)}
							placeholder={placeholder}
							required
							type="text"
							value={value}
						/>
					</ClayModal.Body>

					<ClayModal.Footer
						last={
							<ClayButton.Group spaced>
								<ClayButton
									disabled={loadingResponse}
									displayType="secondary"
									onClick={onClose}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>

								<ClayButton
									disabled={loadingResponse}
									displayType="primary"
									type="submit"
								>
									{loadingResponse && (
										<span className="inline-item inline-item-before">
											<span
												aria-hidden="true"
												className="loading-animation"
											></span>
										</span>
									)}

									{Liferay.Language.get('save')}
								</ClayButton>
							</ClayButton.Group>
						}
					/>
				</form>
			</ClayModal>
		)
	);
};

export {SimpleInputModal};
