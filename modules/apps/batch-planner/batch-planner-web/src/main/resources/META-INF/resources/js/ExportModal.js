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
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {exportAPI} from './api';

const ExportModal = ({
	closeModal,
	formDataQuerySelector,
	formSubmitURL,
	namespace,
	observer,
}) => {
	const isMounted = useIsMounted();
	const [errorMessage, setErrorMessage] = useState();
	const [loadingResponse, setLoadingResponse] = useState(false);

	const _handleSubmit = async (event) => {
		event.preventDefault();

		try {
			const responseJson = exportAPI(
				formDataQuerySelector,
				formSubmitURL
			);

			if (isMounted()) {
				if (responseJson.error) {
					setLoadingResponse(false);
					setErrorMessage(responseJson?.error);
				} else {
					closeModal();
				}
			}
		} catch (error) {
			setErrorMessage(Liferay.Language.get('unexpected-error'));
		} finally {
			setLoadingResponse(true);
		}
	};

	return (
		<ClayModal className="modal-info" observer={observer} size="md">
			<ClayModal.Header>
				{Liferay.Language.get('export')}
			</ClayModal.Header>

			<form id={`${namespace}form`} onSubmit={_handleSubmit}>
				<ClayModal.Body>
					<div
						className={`form-group ${
							errorMessage ? 'has-error' : ''
						}`}
					>
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

								{Liferay.Language.get('download')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</form>
		</ClayModal>
	);
};

ExportModal.propTypes = {
	closeModal: PropTypes.func.isRequired,
	formDataQuerySelector: PropTypes.string.isRequired,
	formSubmitURL: PropTypes.string.isRequired,
	namespace: PropTypes.string.isRequired,
	observer: PropTypes.object.isRequired,
};

export default ExportModal;
