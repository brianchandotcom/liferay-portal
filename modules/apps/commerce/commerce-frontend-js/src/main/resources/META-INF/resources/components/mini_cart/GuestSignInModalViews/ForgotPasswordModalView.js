/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import React, {useCallback, useRef, useState} from 'react';

import {
	FORGOT_PASSWORD,
	SIGN_IN,
	getIframeDOMHooks,
	resizeIframeHeight,
} from '../util/guestModal';

function ForgotPasswordModalView({
	setActiveView,
	setAlert,
	setIsLoading,
	setIsVisible,
	viewsMap,
}) {
	const [primaryButtonText, setPrimaryButtonText] = useState(
		Liferay.Language.get('next')
	);

	const iframeFormRef = useRef(null);
	const iframeLoadedTimesRef = useRef(0);
	const iframeRef = useRef(null);
	const iframeSubmitRef = useRef(null);

	const attachIframeFormListener = useCallback(() => {
		const handler = (event) => {
			const hasFormErrors = event.target.querySelector('.has-error');

			if (hasFormErrors) {
				event.preventDefault();

				resizeIframeHeight(iframeRef.current, iframeFormRef.current);

				return false;
			}

			iframeLoadedTimesRef.current += 1;

			setIsLoading(true);

			return event;
		};

		if (iframeSubmitRef.current) {
			iframeSubmitRef.current.addEventListener('click', handler);
		}
	}, [setIsLoading]);

	const onLoad = useCallback(
		(event) => {
			const iframeBody = iframeRef.current?.contentDocument.body;

			if (iframeBody) {
				if (iframeLoadedTimesRef.current === 2) {
					const message = iframeBody.querySelector(
						'.login-container .alert-info'
					)?.innerText;

					if (message) {
						const {form, submitButton} = getIframeDOMHooks(
							iframeRef.current,
							FORGOT_PASSWORD
						);

						iframeFormRef.current = form;
						iframeSubmitRef.current = submitButton;

						setPrimaryButtonText(
							Liferay.Language.get('send-password-reset-link')
						);

						setIsLoading(false);

						return;
					}
				}

				if (iframeLoadedTimesRef.current === 3) {
					const message = iframeBody.querySelector(
						'.login-container .alert-success'
					)?.innerText;

					if (message) {
						setAlert({
							message,
							type: 'success',
						});

						setActiveView(SIGN_IN);
					}

					return;
				}

				iframeLoadedTimesRef.current += 1;

				const {form, submitButton} = getIframeDOMHooks(
					iframeRef.current,
					FORGOT_PASSWORD
				);

				iframeFormRef.current = form;
				iframeSubmitRef.current = submitButton;

				attachIframeFormListener();

				setIsLoading(false);
			}

			return event;
		},
		[attachIframeFormListener, setActiveView, setAlert, setIsLoading]
	);

	return (
		<>
			<ClayModal.Body>
				<iframe
					className="border-0 w-100"
					id="modalIframe"
					onLoad={onLoad}
					ref={(ref) => {
						iframeRef.current = ref;
					}}
					src={viewsMap[FORGOT_PASSWORD].url}
				/>
			</ClayModal.Body>

			<ClayModal.Footer
				first={
					<ClayButton.Group>
						<ClayButton
							displayType="secondary"
							onClick={(event) => {
								event.preventDefault();

								setActiveView(SIGN_IN);
							}}
						>
							{Liferay.Language.get('sign-in')}
						</ClayButton>
					</ClayButton.Group>
				}
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={(event) => {
								event.preventDefault();

								setIsVisible(false);
							}}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							onClick={(event) => {
								event.preventDefault();

								if (iframeSubmitRef.current) {
									iframeSubmitRef.current.click();
								}
							}}
							type="button"
						>
							{primaryButtonText}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}

export default ForgotPasswordModalView;
