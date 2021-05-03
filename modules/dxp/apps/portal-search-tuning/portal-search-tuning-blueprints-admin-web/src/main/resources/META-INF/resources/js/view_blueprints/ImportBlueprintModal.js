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
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

const VALID_EXTENSIONS = '.json';

const ImportBlueprintModal = ({formSubmitURL}) => {
	const isMounted = useIsMounted();
	const [errorMessage, setErrorMessage] = useState();
	const [loadingResponse, setLoadingResponse] = useState(false);

	const formRef = useRef();

	const _handleClose = (data) => {
		Liferay.Util.getOpener().Liferay.fire('closeModal', data);
	};

	const _handleFormError = (responseContent) => {
		setErrorMessage(responseContent.error || '');

		setLoadingResponse(false);
	};

	const _handleSubmit = (event) => {
		event.preventDefault();

		setLoadingResponse(true);

		const formData = new FormData(formRef.current);

		fetch(formSubmitURL, {
			body: formData,
			method: 'POST',
		})
			.then((response) => response.json())
			.then((responseContent) => {
				var redirectURL = new URL(
					responseContent.redirectURL,
					window.location.origin
				);

				redirectURL.searchParams.set('p_p_state', 'normal');

				if (isMounted()) {
					if (responseContent.error) {
						_handleFormError(responseContent);
					}
					else {
						_handleClose({redirect: redirectURL});
					}
				}
			})
			.catch((response) => {
				_handleFormError(response);
			});
	};

	return (
		<form
			className="import-blueprint-form"
			onSubmit={_handleSubmit}
			ref={formRef}
		>
			<ClayModal.Body>
				{errorMessage && (
					<ClayAlert
						displayType="danger"
						onClose={() => setErrorMessage('')}
						title={Liferay.Language.get('error')}
					>
						{errorMessage}
					</ClayAlert>
				)}

				<p className="text-secondary">
					{Liferay.Language.get(
						'select-a-blueprint-or-element-json-file-to-import'
					)}
				</p>

				<div className="form-group">
					<label className="control-label" htmlFor="file">
						{Liferay.Language.get('select-file')}

						<span className="reference-mark">
							<ClayIcon symbol="asterisk" />
						</span>
					</label>

					<ClayInput
						accept={VALID_EXTENSIONS}
						name="file"
						required
						type="file"
					/>
				</div>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							disabled={loadingResponse}
							displayType="secondary"
							onClick={_handleClose}
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
									/>
								</span>
							)}

							{Liferay.Language.get('import')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</form>
	);
};

export default ImportBlueprintModal;
