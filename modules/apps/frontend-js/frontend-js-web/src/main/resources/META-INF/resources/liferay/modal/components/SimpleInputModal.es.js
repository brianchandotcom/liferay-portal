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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import {useIsMounted} from 'frontend-js-react-web';
import {fetch, navigate} from 'frontend-js-web';
import React, {useState} from 'react';

/**
 * Manipulates small amounts of data with a form shown inside a modal.
 */
const SimpleInputModal = ({
	alert,
	checkboxFieldLabel,
	checkboxFieldName,
	checkboxFieldValue,
	closeModal,
	dialogTitle,
	formSubmitURL,
	idFieldName,
	idFieldValue,
	initialVisible,
	mainFieldLabel,
	mainFieldName,
	namespace,
	placeholder
}) => {
	const isMounted = useIsMounted();
	const [errorMessage, setErrorMessage] = useState();
	const [loadingResponse, setLoadingResponse] = useState(false);
	const [visible, setVisible] = useState(initialVisible);
	const [inputValue, setInputValue] = useState('');
	const [isChecked, setChecked] = useState(checkboxFieldValue);

	const handleFormError = responseContent => {
		setErrorMessage(responseContent.error || '');
	};

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
				if (isMounted()) {
					if (responseContent.error) {
						setLoadingResponse(false);

						handleFormError(responseContent);
					} else {
						setVisible(false);

						closeModal();

						navigate(responseContent.redirectURL);
					}
				}
			})
			.catch(response => {
				handleFormError(response);
			});

		setLoadingResponse(true);
	};

	const {observer, onClose} = useModal({
		onClose: () => {
			setVisible(false);

			closeModal();
		}
	});

	return (
		visible && (
			<ClayModal observer={observer} size="md">
				<ClayModal.Header>{dialogTitle}</ClayModal.Header>

				<form id={`${namespace}form`} onSubmit={_handleSubmit}>
					<ClayModal.Body>
						{alert && alert.message && alert.title && (
							<ClayAlert
								displayType={alert.style}
								title={alert.title}
							>
								{alert.message}
							</ClayAlert>
						)}

						<input
							name={`${namespace}${idFieldName}`}
							type="hidden"
							value={idFieldValue}
						/>

						<div
							className={`form-group ${
								errorMessage ? 'has-error' : ''
							}`}
						>
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
								onChange={event =>
									setInputValue(event.target.value)
								}
								placeholder={placeholder}
								required
								type="text"
								value={inputValue}
							/>

							{errorMessage && (
								<div className="form-feedback-item">
									<ClayIcon symbol="exclamation-full" />

									{errorMessage}
								</div>
							)}
						</div>

						{checkboxFieldName && checkboxFieldLabel && (
							<div className="form-check">
								<ClayCheckbox
									checked={isChecked}
									disabled={loadingResponse}
									label={checkboxFieldLabel}
									name={`${namespace}${checkboxFieldName}`}
									onChange={() =>
										setChecked(isChecked => !isChecked)
									}
								/>
							</div>
						)}
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

export default SimpleInputModal;
