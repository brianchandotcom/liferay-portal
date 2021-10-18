import React, {useEffect, useState} from 'react';
import {WarningBadge} from '~/common/components/fragments/Badges/Warning';
import {EMAIL_REGEX} from '~/common/utils/patterns';
import {
	SendAccountRequest,
	validadePassword,
} from '~/routes/selected-quote/utils/CreateAccount';
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

export const CreateAnAccount = ({setExpanded, setStepChecked}) => {
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
			const response = SendAccountRequest(email, password);

			if (response === CHECK_VALUE) {
				setExpanded('uploadDocuments');
				setStepChecked('createAnAccount', true);
			}

			setAlert(response);
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
		<div id="ca">
			<div className="ca-sub-title">
				Create a Raylife account to continue. This will be used to login
				to your dashboard.
			</div>

			<div className="ca-width-div">
				<div id="ca-content-input">
					<input
						className="ca-input"
						id="ca-input-email"
						onChange={(event) => {
							setEmail(event.target.value);
						}}
						placeholder="sam.jones@gmail.com"
						required
						type="text"
					/>

					<label className="ca-label">Email</label>
				</div>

				<div>
					{email && !isEmailValid && (
						<WarningBadge>
							Must be a valid email address.
						</WarningBadge>
					)}
				</div>

				<div id="ca-content-input">
					<input
						className="ca-input"
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

					<label className="ca-label">Password</label>
				</div>

				<div id="ca-content-input">
					<input
						className="ca-input"
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

					<label className="ca-label">Re-enter Password</label>
				</div>

				<ListRules objValidate={objValidate} />
			</div>

			<div className="ca-align-right">
				<button
					className="btn ca-btn"
					disabled={!matchAllRules}
					onClick={onCreateAccount}
				>
					CREATE ACCOUNT
				</button>
			</div>

			{email && alert === UNCHECKED_VALUE && (
				<div className="ca-alert-create">
					<WarningBadge>
						Unable to create your account. Please try again
					</WarningBadge>
				</div>
			)}
		</div>
	);
};
