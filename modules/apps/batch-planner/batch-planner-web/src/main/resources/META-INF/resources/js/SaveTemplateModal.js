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

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {fetch, openToast} from 'frontend-js-web';
import React, {useState} from 'react';

import {HEADERS, TEMPLATE_CREATED} from './constants';

async function saveTemplate(formDataQuerySelector, updateData, url) {
	const mainFormData = document.querySelector(formDataQuerySelector);
	const formData = new FormData(mainFormData);

	for (const [key, value] of Object.entries(updateData)) {
		formData.append(key, value);
	}

	const response = await fetch(url, {
		body: formData,
		headers: HEADERS,
		method: 'POST',
	});

	return await response.json();
}

const SaveTemplateModal = ({
	closeModal,
	formDataQuerySelector,
	formSubmitURL,
	namespace,
	observer,
}) => {
	const inputNameId = namespace + 'name';
	const isMounted = useIsMounted();
	const [errorMessage, setErrorMessage] = useState();
	const [loadingResponse, setLoadingResponse] = useState(false);
	const [inputValue, setInputValue] = useState('');

	const handleSubmit = async (event) => {
		event.preventDefault();

		try {
			const updateData = {[inputNameId]: inputValue};
			const saveTemplateResponse = await saveTemplate(
				formDataQuerySelector,
				updateData,
				formSubmitURL
			);

			if (isMounted()) {
				if (saveTemplateResponse.error) {
					setLoadingResponse(false);
					setErrorMessage(saveTemplateResponse.error);
				}
				else {
					Liferay.fire(TEMPLATE_CREATED, saveTemplateResponse);
					openToast({
						message: Liferay.Language.get('template-was-created'),
						type: 'success',
					});

					closeModal();
				}
			}
		}
		catch (error) {
			setErrorMessage(Liferay.Language.get('unexpected-error'));
		}
		finally {
			setLoadingResponse(false);
		}
	};

	return (
		<ClayModal observer={observer} size="md">
			<ClayModal.Header>
				{Liferay.Language.get('save-as-template')}
			</ClayModal.Header>

			<ClayForm id={`${namespace}form`} onSubmit={handleSubmit}>
				<ClayModal.Body>
					<ClayForm.Group className={errorMessage ? 'has-error' : ''}>
						<label htmlFor={inputNameId}>
							{Liferay.Language.get('name')}

							<span className="reference-mark">
								<ClayIcon symbol="asterisk" />
							</span>
						</label>

						<ClayInput
							autoFocus
							disabled={loadingResponse}
							id={inputNameId}
							name={inputNameId}
							onChange={(event) =>
								setInputValue(event.target.value)
							}
							placeholder={Liferay.Language.get('template-name')}
							required
							type="text"
							value={inputValue}
						/>

						{errorMessage && (
							<ClayForm.FeedbackGroup>
								<ClayForm.FeedbackItem>
									<ClayForm.FeedbackIndicator symbol="exclamation-full" />

									{errorMessage}
								</ClayForm.FeedbackItem>
							</ClayForm.FeedbackGroup>
						)}
					</ClayForm.Group>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={closeModal}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								disabled={
									loadingResponse || inputValue.length === 0
								}
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
			</ClayForm>
		</ClayModal>
	);
};

export default SaveTemplateModal;
