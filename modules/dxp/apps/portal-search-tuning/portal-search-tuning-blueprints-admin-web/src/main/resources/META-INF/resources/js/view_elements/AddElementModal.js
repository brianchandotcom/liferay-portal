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
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import getCN from 'classnames';
import {fetch, navigate} from 'frontend-js-web';
import React, {useState} from 'react';

import {DEFAULT_EDIT_ELEMENT} from '../utils/data';

/**
 * A slightly modified version of frontend-js-web module's SimpleInputModal
 * React component to include a description field.
 */
const AddElementModal = ({
	alert,
	closeModal,
	defaultLocale,
	dialogTitle,
	formSubmitURL,
	initialVisible,
	namespace,
	onFormSuccess,
	submitButtonLabel = Liferay.Language.get('create'),
	type,
}) => {
	const isMounted = useIsMounted();
	const [errorMessage, setErrorMessage] = useState();
	const [loadingResponse, setLoadingResponse] = useState(false);
	const [visible, setVisible] = useState(initialVisible);
	const [inputValue, setInputValue] = useState('');
	const [descriptionInputValue, setDescriptionInputValue] = useState('');

	const handleFormError = (responseContent) => {
		setErrorMessage(responseContent.error || '');
	};

	const _handleSubmit = (event) => {
		event.preventDefault();

		const formData = new FormData(
			document.querySelector(`#${namespace}form`)
		);

		formData.append(
			`${namespace}configuration`,
			JSON.stringify(DEFAULT_EDIT_ELEMENT)
		);

		fetch(formSubmitURL, {
			body: formData,
			method: 'POST',
		})
			.then((response) => response.json())
			.then((responseContent) => {
				if (isMounted()) {
					if (responseContent.error) {
						setLoadingResponse(false);

						handleFormError(responseContent);
					}
					else {
						setVisible(false);

						closeModal();

						if (responseContent.redirectURL) {
							navigate(responseContent.redirectURL);
						}
						else {
							if (onFormSuccess) {
								onFormSuccess({
									...responseContent,
									redirectURL:
										responseContent.redirectURL || '',
								});
							}
						}
					}
				}
			})
			.catch((response) => {
				handleFormError(response);
			});

		setLoadingResponse(true);
	};

	const {observer, onClose} = useModal({
		onClose: () => {
			setVisible(false);

			closeModal();
		},
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
							name={`${namespace}elementType`}
							type="hidden"
							value={type}
						/>

						<div
							className={getCN('form-group', {
								'has-error': errorMessage,
							})}
						>
							<label
								className="control-label"
								htmlFor={`${namespace}title`}
							>
								{Liferay.Language.get('name')}

								<span className="reference-mark">
									<ClayIcon symbol="asterisk" />
								</span>
							</label>

							<input
								autoFocus
								className="form-control"
								disabled={loadingResponse}
								id={`${namespace}title`}
								name={`${namespace}title`}
								onChange={(event) =>
									setInputValue(event.target.value)
								}
								required
								type="text"
								value={inputValue}
							/>

							<input
								id={`${namespace}title_${defaultLocale}`}
								name={`${namespace}title_${defaultLocale}`}
								type="hidden"
								value={inputValue}
							/>

							{errorMessage && (
								<div className="form-feedback-item">
									<ClayIcon
										className="inline-item inline-item-before"
										symbol="exclamation-full"
									/>

									{errorMessage}
								</div>
							)}
						</div>

						<div className="form-group">
							<label
								className="control-label"
								htmlFor={`${namespace}description`}
							>
								{Liferay.Language.get('description')}
							</label>

							<textarea
								className="form-control"
								disabled={loadingResponse}
								id={`${namespace}description`}
								name={`${namespace}description`}
								onChange={(event) =>
									setDescriptionInputValue(event.target.value)
								}
								value={descriptionInputValue}
							/>

							<input
								id={`${namespace}description_${defaultLocale}`}
								name={`${namespace}description_${defaultLocale}`}
								type="hidden"
								value={descriptionInputValue}
							/>
						</div>
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

									{submitButtonLabel}
								</ClayButton>
							</ClayButton.Group>
						}
					/>
				</form>
			</ClayModal>
		)
	);
};

export default AddElementModal;
