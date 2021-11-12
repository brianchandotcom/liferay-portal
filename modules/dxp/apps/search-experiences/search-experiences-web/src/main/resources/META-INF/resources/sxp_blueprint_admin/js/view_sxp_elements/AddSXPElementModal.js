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
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import getCN from 'classnames';
import {fetch, navigate} from 'frontend-js-web';
import React, {useState} from 'react';

import {DEFAULT_ERROR} from '../utils/constants';
import {DEFAULT_EDIT_SXP_ELEMENT} from '../utils/data';
import {isDefined} from '../utils/utils';

/**
 * A slightly modified version of frontend-js-web module's SimpleInputModal
 * React component to include a description field.
 */
const AddSXPElementModal = ({
	closeModal,
	defaultLocale,
	dialogTitle,
	editSXPElementURL,
	initialVisible,
	portletNamespace,
	submitButtonLabel = Liferay.Language.get('create'),
	redirectURL = '',
}) => {
	const isMounted = useIsMounted();
	const [errorMessage, setErrorMessage] = useState();
	const [loadingResponse, setLoadingResponse] = useState(false);
	const [visible, setVisible] = useState(initialVisible);
	const [titleInputValue, setTitleInputValue] = useState('');
	const [descriptionInputValue, setDescriptionInputValue] = useState('');

	const {observer, onClose} = useModal({
		onClose: () => {
			setVisible(false);

			closeModal();
		},
	});

	const _handleFormError = (responseContent) => {
		setErrorMessage(responseContent.error || DEFAULT_ERROR);

		setLoadingResponse(false);
	};

	const _handleSubmit = (event) => {
		event.preventDefault();

		fetch('/o/search-experiences-rest/v1.0/sxp-elements', {
			body: JSON.stringify({
				description_i18n: {[defaultLocale]: descriptionInputValue},
				elementDefinition: DEFAULT_EDIT_SXP_ELEMENT.elementDefinition,
				title_i18n: {[defaultLocale]: titleInputValue},
			}),
			headers: new Headers({
				'Content-Type': 'application/json',
			}),
			method: 'POST',
		})
			.then((response) => {
				if (!response.ok) {
					_handleFormError();
				}

				return response.json();
			})
			.then((responseContent) => {
				if (isMounted()) {
					if (responseContent.error) {
						_handleFormError(responseContent);
					}
					else {
						setVisible(false);

						closeModal();

						if (isDefined(responseContent.id)) {
							const url = new URL(editSXPElementURL);

							url.searchParams.set(
								`${portletNamespace}sxpElementId`,
								responseContent.id
							);

							navigate(url);
						}
						else {
							navigate(redirectURL);
						}
					}
				}
			})
			.catch((response) => {
				_handleFormError(response);
			});

		setLoadingResponse(true);
	};

	return (
		visible && (
			<ClayModal observer={observer} size="md">
				<ClayModal.Header>{dialogTitle}</ClayModal.Header>

				<form id={`${portletNamespace}form`} onSubmit={_handleSubmit}>
					<ClayModal.Body>
						<div
							className={getCN('form-group', {
								'has-error': errorMessage,
							})}
						>
							<label
								className="control-label"
								htmlFor={`${portletNamespace}title`}
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
								id={`${portletNamespace}title`}
								name={`${portletNamespace}title`}
								onChange={(event) =>
									setTitleInputValue(event.target.value)
								}
								required
								type="text"
								value={titleInputValue}
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
								htmlFor={`${portletNamespace}description`}
							>
								{Liferay.Language.get('description')}
							</label>

							<textarea
								className="form-control"
								disabled={loadingResponse}
								id={`${portletNamespace}description`}
								name={`${portletNamespace}description`}
								onChange={(event) =>
									setDescriptionInputValue(event.target.value)
								}
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

export default AddSXPElementModal;
