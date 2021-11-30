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

	useEffect(() => {
		setAlert(NATURAL_VALUE);
	}, [confirmPassword, email, password]);

	const onCreateAccount = () => {
		if (isMatchingAllRules) {
			SendAccountRequest(email, password).then((response) => {
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

				dispatch({payload: response.id, type: ACTIONS.SET_ACCOUNT_ID});
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
		<div className="create-account">
			<div className="create-account__subtitle">
				Create a Raylife account to continue. This will be used to login
				to your dashboard.
			</div>

			<div className="create-account__form">
				<div className="create-account__form__content-input">
					<input
						className="email"
						onChange={(event) => {
							setEmail(event.target.value);
						}}
						placeholder="sam.jones@gmail.com"
						required
						type="text"
					/>

					<label>Email</label>
				</div>

				<div>
					{email && !isEmailValid && (
						<WarningBadge>
							Please enter a valid email address.
						</WarningBadge>
					)}
				</div>

				<div className="create-account__form__content-input">
					<input
						onChange={(event) => {
							setPassword(event.target.value);
							setObjValidate(
								validadePassword(
									confirmPassword,
									event.target.value
								)
							);
						}}
						placeholder="Create a Password"
						required
						type="password"
					/>

					<label>Password</label>
				</div>

				<div className="create-account__form__content-input">
					<input
						onChange={(event) => {
							setConfirmPassword(event.target.value);
							setObjValidate(
								validadePassword(event.target.value, password)
							);
						}}
						placeholder="Re-enter Password"
						required
						type="password"
					/>

					<label>Re-enter Password</label>
				</div>

				<ListRules objValidate={objValidate} />
			</div>

			<div className="create-account__align-right">
				<button
					className="btn"
					disabled={!matchAllRules}
					onClick={onCreateAccount}
				>
					CREATE ACCOUNT
				</button>
			</div>

			{email && alert === UNCHECKED_VALUE && (
				<div className="create-account__alert-create">
					<WarningBadge>
						Unable to create your account. Please try again
					</WarningBadge>
				</div>
			)}
		</div>
	);
}
