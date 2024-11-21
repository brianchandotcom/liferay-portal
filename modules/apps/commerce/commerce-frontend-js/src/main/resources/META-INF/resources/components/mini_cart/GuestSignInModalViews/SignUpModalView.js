/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {Col, Row} from '@clayui/layout';
import ClayModal from '@clayui/modal';
import classnames from 'classnames';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import {
	SIGN_IN,
	SIGN_UP,
	getAccountTypes,
	getIframeDOMHooks,
	resizeIframeHeight,
	storeAccountInformation,
} from '../util/guestModal';

function SignUpModalView({
	setActiveView,
	setAlert,
	setIsLoading,
	setIsVisible,
	viewsMap,
}) {
	const [accountName, setAccountName] = useState('');
	const [accountType, setAccountType] = useState('');
	const [availableAccountTypes] = useState(getAccountTypes());
	const [errors, setErrors] = useState({
		accountName: false,
		accountType: false,
	});
	const [userEmail, setUserEmail] = useState('');

	const iframeFormRef = useRef(null);
	const iframeLoadedOnceRef = useRef(false);
	const iframeRef = useRef(null);
	const iframeSubmitRef = useRef(null);

	const attachIframeFormListener = useCallback(() => {
		const handler = (event) => {
			const hasFormErrors =
				iframeFormRef.current?.querySelector('.has-error');

			if (errors.accountName || errors.accountType || hasFormErrors) {
				event.preventDefault();

				resizeIframeHeight(iframeRef.current, iframeFormRef.current);

				return false;
			}

			const formEmailAddress = iframeFormRef.current.querySelector(
				'[name$="emailAddress"]'
			)?.value;

			setUserEmail(formEmailAddress);

			setIsLoading(true);

			return event;
		};

		if (iframeFormRef.current) {
			iframeFormRef.current.addEventListener('submit', handler);
		}
	}, [errors, setIsLoading, setUserEmail]);

	const onLoad = useCallback(
		(event) => {
			if (iframeRef.current && iframeLoadedOnceRef.current) {
				const successMessage =
					iframeRef.current.contentDocument.body.querySelector(
						'.login-container .alert-success'
					)?.innerText;

				if (accountName && accountType && userEmail && successMessage) {
					storeAccountInformation({
						accountName,
						accountType,
						userEmail,
					});

					setAlert({
						message: successMessage,
						type: 'success',
					});

					setActiveView(SIGN_IN);

					return;
				}
				else {
					iframeLoadedOnceRef.current = false;

					iframeRef.current.contentWindow.location.reload();
				}
			}

			iframeLoadedOnceRef.current = true;

			const {form, submitButton} = getIframeDOMHooks(
				iframeRef.current,
				SIGN_UP
			);

			iframeFormRef.current = form;
			iframeSubmitRef.current = submitButton;

			attachIframeFormListener();

			setIsLoading(false);

			return event;
		},
		[
			accountName,
			accountType,
			attachIframeFormListener,
			setActiveView,
			setAlert,
			setIsLoading,
			userEmail,
		]
	);

	const doSubmit = useCallback(
		(event) => {
			event.preventDefault();

			setErrors({
				accountName: !accountName,
				accountType: !accountType,
			});
		},
		[accountName, accountType, setErrors]
	);

	useEffect(() => {
		if (iframeSubmitRef.current) {
			iframeSubmitRef.current.click();
		}
	}, [errors]);

	useEffect(() => {
		if (availableAccountTypes.length === 1) {
			setAccountType(availableAccountTypes[0].value);
		}
	}, [availableAccountTypes, setAccountType]);

	return (
		<>
			<ClayModal.Body className="sign-up-modal-view">
				<iframe
					className="border-0 w-100"
					id="modalIframe"
					onLoad={onLoad}
					ref={(ref) => {
						iframeRef.current = ref;
					}}
					src={viewsMap[SIGN_UP].url}
				/>

				<ClayForm.Group className="mb-3 px-1">
					<h3 className="mb-3 sheet-subtitle">
						{Liferay.Language.get('account-detail')}
					</h3>

					<Row>
						<Col>
							<ClayForm.Group
								className={classnames('mb-3', {
									'has-error': errors.accountName,
								})}
							>
								<label htmlFor="accountName">
									{Liferay.Language.get('account-name')}

									<span className="ml-1 reference-mark">
										<ClayIcon symbol="asterisk" />

										<span className="hide-accessible sr-only">
											{Liferay.Language.get('required')}
										</span>
									</span>
								</label>

								<ClayInput
									maxLength={100}
									name="accountName"
									onChange={(event) =>
										setAccountName(event.target.value)
									}
									required
									type="text"
									value={accountName}
								/>

								{errors.accountName && (
									<ClayForm.FeedbackGroup>
										<ClayForm.FeedbackItem>
											<ClayForm.FeedbackIndicator symbol="info-circle" />

											{Liferay.Language.get(
												'this-field-is-required'
											)}
										</ClayForm.FeedbackItem>
									</ClayForm.FeedbackGroup>
								)}
							</ClayForm.Group>

							<ClayForm.Group
								className={classnames('mb-3', {
									'has-error': errors.accountType,
									'hide': availableAccountTypes.length === 1,
								})}
							>
								<label htmlFor="available-accounts-list">
									{Liferay.Language.get('account-type')}

									<span className="ml-1 reference-mark">
										<ClayIcon symbol="asterisk" />

										<span className="hide-accessible sr-only">
											{Liferay.Language.get('required')}
										</span>
									</span>
								</label>

								<ClaySelect
									aria-label={Liferay.Language.get(
										'account-type'
									)}
									disabled={
										availableAccountTypes.length === 1
									}
									id="available-account-types"
									name="available-account-types"
									onChange={(event) => {
										setAccountType(event.target.value);
									}}
								>
									{availableAccountTypes.map(
										({label, value}, index) => (
											<ClaySelect.Option
												key={`${value}_${index}`}
												label={label}
												value={value}
											/>
										)
									)}
								</ClaySelect>

								{errors.accountType && (
									<ClayForm.FeedbackGroup>
										<ClayForm.FeedbackItem>
											<ClayForm.FeedbackIndicator symbol="info-circle" />

											{Liferay.Language.get(
												'this-field-is-required'
											)}
										</ClayForm.FeedbackItem>
									</ClayForm.FeedbackGroup>
								)}
							</ClayForm.Group>
						</Col>
					</Row>
				</ClayForm.Group>
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
							onClick={doSubmit}
							type="button"
						>
							{Liferay.Language.get('done')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}

export default SignUpModalView;
