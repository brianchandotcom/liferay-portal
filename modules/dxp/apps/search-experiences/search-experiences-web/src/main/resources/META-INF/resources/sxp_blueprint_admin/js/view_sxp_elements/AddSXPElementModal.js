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
import ClayModal, {ClayModalProvider, useModal} from '@clayui/modal';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {fetch, navigate} from 'frontend-js-web';
import React, {useContext, useState} from 'react';

import {DEFAULT_ERROR} from '../utils/constants';
import {DEFAULT_EDIT_SXP_ELEMENT} from '../utils/data';
import {isDefined} from '../utils/utils';
import PortletContext from './PortletContext';

const AddSXPElementModal = ({children}) => {
	const {defaultLocale, editSXPElementURL, namespace} = useContext(
		PortletContext
	);

	const isMounted = useIsMounted();

	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	const [descriptionInputValue, setDescriptionInputValue] = useState('');
	const [errorMessage, setErrorMessage] = useState();
	const [loadingResponse, setLoadingResponse] = useState(false);
	const [titleInputValue, setTitleInputValue] = useState('');
	const [visibleModal, setVisibleModal] = useState(false);

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
						setVisibleModal(false);

						if (isDefined(responseContent.id)) {
							const url = new URL(editSXPElementURL);

							url.searchParams.set(
								`${namespace}sxpElementId`,
								responseContent.id
							);

							navigate(url);
						}
						else {
							navigate(window.location.href);
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
		<>
			<ClayModalProvider>
				{visibleModal && (
					<ClayModal observer={observer} size="md">
						<ClayModal.Header>
							{Liferay.Language.get('new-search-element')}
						</ClayModal.Header>

						<form id={`${namespace}form`} onSubmit={_handleSubmit}>
							<ClayModal.Body>
								{errorMessage && (
									<ClayAlert
										displayType="danger"
										onClose={() => setErrorMessage('')}
									>
										{errorMessage}
									</ClayAlert>
								)}

								<div className="form-group">
									<label
										className="control-label"
										htmlFor={`${namespace}title`}
									>
										{Liferay.Language.get('title')}

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
											setTitleInputValue(
												event.target.value
											)
										}
										required
										type="text"
										value={titleInputValue}
									/>
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
											setDescriptionInputValue(
												event.target.value
											)
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

											{Liferay.Language.get('create')}
										</ClayButton>
									</ClayButton.Group>
								}
							/>
						</form>
					</ClayModal>
				)}
			</ClayModalProvider>

			<div onClick={() => setVisibleModal(!visibleModal)}>{children}</div>
		</>
	);
};

export default AddSXPElementModal;
