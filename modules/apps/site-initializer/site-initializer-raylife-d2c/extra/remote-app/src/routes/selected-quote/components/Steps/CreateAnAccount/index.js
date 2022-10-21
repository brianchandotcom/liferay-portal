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
import classNames from 'classnames';
import React, {useContext, useEffect, useState} from 'react';
import {WarningBadge} from '../../../../../common/components/fragments/Badges/Warning';
import {EMAIL_REGEX} from '../../../../../common/utils/patterns';
import {
	ACTIONS,
	SelectedQuoteContext,
} from '../../../context/SelectedQuoteContextProvider';
import {
	SendAccountRequest,
	validadePassword,
} from '../../../utils/CreateAccount';
import Captcha from '../../Captcha';
import {ListRules} from '../CreateAnAccount/ListRules';
import {
	CHECK_VALUE,
	INITIAL_VALIDATION,
	NATURAL_VALUE,
	UNCHECKED_VALUE,
} from './constants';

const _isEmailValid = (email) => {
	const regex = new RegExp(EMAIL_REGEX);

	return regex.test(email);
};

export function CreateAnAccount() {
	const [, dispatch] = useContext(SelectedQuoteContext);
	const [alert, setAlert] = useState(NATURAL_VALUE);
	const [confirmPassword, setConfirmPassword] = useState('');
	const [email, setEmail] = useState('');
	const [password, setPassword] = useState('');
	const [objValidate, setObjValidate] = useState(INITIAL_VALIDATION);
	const [passwordLabel, setPasswordLabel] = useState('Password');
	const [captcha, setCaptcha] = useState('');
	const [hasError, setHasError] = useState(false);
	const [loading, setLoading] = useState(false);
	const [createAnAccount, setCreateAnAccount] = useState(false);

	useEffect(() => {
		setAlert(NATURAL_VALUE);
	}, [confirmPassword, email, password]);

	const onCreateAccount = () => {
		if (isMatchingAllRules()) {
			setLoading(true);

			SendAccountRequest(email, password, captcha)
				.then(() => {
					dispatch({
						payload: {
							panelKey: 'uploadDocuments',
							value: true,
						},
						type: ACTIONS.SET_EXPANDED,
					});

					dispatch({
						payload: {
							panelKey: 'createAnAccount',
							value: false,
						},
						type: ACTIONS.SET_EXPANDED,
					});

					dispatch({
						payload: {
							panelKey: 'createAnAccount',
							value: true,
						},
						type: ACTIONS.SET_STEP_CHECKED,
					});
				})
				.catch(() => {
					setHasError(true);

					setLoading(false);
				});
		}
	};

	const isEmailValid = _isEmailValid(email);

	const isMatchingAllRules = () => {
		for (const validation of Object.values(objValidate)) {
			if (validation !== CHECK_VALUE) {
				return false;
			}
		}

		return isEmailValid;
	};

	const matchAllRules = isMatchingAllRules();

	return (
		<div className="create-account mb-md-4 ml-lg-5 ml-md-4 mt-0 mt-md-4">
			{createAnAccount && (
				<h5 className="font-weight-bolder mb-5 mx-0">
					Create a Raylife account to continue. This will be used to
					login to your dashboard.
				</h5>
			)}

			<ClayForm autoComplete="off" className="create-account__form">
				<div className="create-account__form__content-input filled form-condensed form-group mb-1 mt-4">
					<ClayInput
						autoComplete="off"
						className="bg-neutral-0 email w-100"
						id="email"
						name="email"
						onChange={(event) => {
							setEmail(event.target.value);
						}}
						placeholder="sam.jones@gmail.com"
						required
						type="text"
						value={email}
					/>

					<label htmlFor="email">Email</label>
				</div>

				<div>
					{email && !isEmailValid && (
						<WarningBadge>
							Please enter a valid email address.
						</WarningBadge>
					)}
				</div>

				<div
					className={classNames(
						'create-account__form__content-input form-condensed form-group mt-4',
						{
							filled: password,
						}
					)}
				>
					<ClayInput
						autoComplete="off"
						className="w-100"
						id="password"
						onBlur={() => {
							if (createAnAccount && !password) {
								setPasswordLabel('Create a Password');
							}
						}}
						onChange={(event) => {
							setPassword(event.target.value);
							setObjValidate(
								validadePassword(
									confirmPassword,
									event.target.value
								)
							);
						}}
						onFocus={() => setPasswordLabel('Password')}
						required
						type="password"
						value={password}
					/>

					<label htmlFor="password">{passwordLabel}</label>

					{!createAnAccount && (
						<ClayButton displayType="link text-paragraph-sm text-neutral-7">
							Forget password?
						</ClayButton>
					)}
				</div>

				{createAnAccount && (
					<div
						className={classNames(
							'create-account__form__content-input form-condensed form-group mt-4',
							{
								filled: confirmPassword,
							}
						)}
					>
						<ClayInput
							autoComplete="off"
							className="w-100"
							id="rePassword"
							onChange={(event) => {
								setConfirmPassword(event.target.value);
								setObjValidate(
									validadePassword(
										event.target.value,
										password
									)
								);
							}}
							required
							type="password"
							value={confirmPassword}
						/>

						<label htmlFor="rePassword">Re-enter Password</label>
					</div>
				)}

				{createAnAccount && <ListRules objValidate={objValidate} />}

				{createAnAccount && (
					<Captcha
						captchaValue={captcha}
						setCaptchaValue={setCaptcha}
					/>
				)}
			</ClayForm>

			{createAnAccount ? (
				<div className="align-items-center d-flex justify-content-center justify-content-md-end">
					<ClayButton
						className="align-items-center d-flex"
						disabled={!matchAllRules || loading || !captcha}
						displayType="primary"
						onClick={onCreateAccount}
					>
						CREATE ACCOUNT
					</ClayButton>
				</div>
			) : (
				<div className="align-items-center d-flex justify-content-center justify-content-md-end">
					<ClayButton
						className="align-items-center d-flex mr-3 mt-6"
						displayType="secondary"
						onClick={() => {
							setCreateAnAccount(true);
							setPasswordLabel('Create a Password');
						}}
					>
						CREATE NEW ACCOUNT
					</ClayButton>

					<ClayButton
						className="align-items-center d-flex mt-6"
						disabled={!isEmailValid || password === ''}
						displayType="primary"
					>
						LOGIN
					</ClayButton>
				</div>
			)}

			{(email && alert === UNCHECKED_VALUE) ||
				(hasError && (
					<div className="create-account__alert-create mt-4 rounded">
						<WarningBadge>
							Unable to create your account. Please try again
						</WarningBadge>
					</div>
				))}
		</div>
	);
}
