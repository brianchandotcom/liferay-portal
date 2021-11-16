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
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import React, {useState} from 'react';

import {saveTemplateAPI} from './BatchPlannerExport';

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

	const _handleSubmit = async (event) => {
		event.preventDefault();

		try {
			const updateData = {[inputNameId]: inputValue};
			const saveTemplateResponse = saveTemplateAPI(
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
					closeModal();
				}
			}
		}
		catch (error) {
			setErrorMessage(Liferay.Language.get('unexpected-error'));
		}
		finally {
			setLoadingResponse(true);
		}
	};

	return (
		<ClayModal observer={observer} size="md">
			<ClayModal.Header>
				{Liferay.Language.get('save-as-template')}
			</ClayModal.Header>

			<form id={`${namespace}form`} onSubmit={_handleSubmit}>
				<ClayModal.Body>
					<div
						className={`form-group ${
							errorMessage ? 'has-error' : ''
						}`}
					>
						<label className="control-label" htmlFor={inputNameId}>
							{Liferay.Language.get('name')}

							<span className="reference-mark">
								<ClayIcon symbol="asterisk" />
							</span>
						</label>

						<input
							autoFocus
							className="form-control"
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
							<div className="form-feedback-item">
								<ClayIcon
									className="inline-item inline-item-before"
									symbol="exclamation-full"
								/>

								{errorMessage}
							</div>
						)}
					</div>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								disabled={loadingResponse}
								displayType="secondary"
								onClick={closeModal}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								disabled={
									loadingResponse || inputValue.length == 0
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
			</form>
		</ClayModal>
	);
};

export default SaveTemplateModal;
