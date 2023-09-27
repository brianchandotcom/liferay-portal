/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDatePicker from '@clayui/date-picker';
import ClayForm, {
	ClayInput,
	ClayRadio,
	ClayRadioGroup,
	ClaySelect,
} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useContext, useEffect, useState} from 'react';

import {useLocation} from '../../../../../common/hooks/useLocation';
import {
	CONSTANTS,
	ConstantListType,
} from '../../../../../common/utils/constants';
import {
	ACTIONS,
	NewApplicationAutoContext,
} from '../../../context/NewApplicationAutoContextProvider';
import {HasValidationsTypes, hasRequiredErrorTypes} from '../../Types';
import {
	isValidEmail,
	isValidPhone,
	isValidUSZip,
} from '../../utils/formValidations';

const ContactInfo = () => {
	const [state, dispatch] = useContext(NewApplicationAutoContext);

	const {setAutoComplete} = useLocation();
	const [ownership, setOwnership] = useState('');

	const addressElement = document.querySelector(
		'#streetAddress'
	) as HTMLInputElement;

	const {form} = state?.steps?.contactInfo;

	const updateFormWithGoogleAddress = (address: any) => {
		const payload = {
			...form,
			city: address.city,
			state: address.state,
			streetAddress: address.street,
			zipCode: address.zip,
		};

		dispatch({payload, type: ACTIONS.SET_CONTACT_INFO_FORM});
	};

	const handleChangeField = (fieldName: string, fieldValue: string) => {
		const payload = {
			...form,
			[fieldName]: fieldValue,
		};

		if (addressElement) {
			setAutoComplete(addressElement, updateFormWithGoogleAddress);
		}

		dispatch({payload, type: ACTIONS.SET_CONTACT_INFO_FORM});
		dispatch({payload: true, type: ACTIONS.SET_HAS_FORM_CHANGE});
	};

	useEffect(() => {
		if (addressElement) {
			setAutoComplete(addressElement, updateFormWithGoogleAddress);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [addressElement]);

	const hasRequiredError: hasRequiredErrorTypes = {
		city: false,
		dateOfBirth: false,
		email: false,
		firstName: false,
		lastName: false,
		phone: false,
		state: false,
		streetAddress: false,
		zipCode: false,
	};

	const hasValidations: HasValidationsTypes = {
		city: false,
		dateOfBirth: false,
		email: false,
		firstName: false,
		lastName: false,
		phone: false,
		state: false,
		streetAddress: false,
		zipCode: false,
	};

	const [hasError, setHasError] = useState(hasRequiredError);

	const [hasValidation, setHasValidation] = useState(hasValidations);

	const handleSaveChanges = (currentForm: any) => {
		const isAbleToBeSave =
			!hasValidation.firstName &&
			!hasValidation.lastName &&
			!hasValidation.email &&
			!hasValidation.phone;

		const hasAllRequiredFieldsToSave =
			currentForm.firstName !== '' &&
			currentForm.lastName !== '' &&
			currentForm.email !== '' &&
			currentForm.phone !== '';

		const isAbleToNextStep =
			isAbleToBeSave &&
			!hasValidation.city &&
			!hasValidation.dateOfBirth &&
			!hasValidation.state &&
			!hasValidation.streetAddress &&
			!hasValidation.zipCode;

		const hasAllRequiredFieldsToNextStep =
			hasAllRequiredFieldsToSave &&
			currentForm.city &&
			currentForm.dateOfBirth &&
			currentForm.state &&
			currentForm.streetAddress &&
			currentForm.zipCode &&
			currentForm.ownership;

		dispatch({
			payload: isAbleToBeSave && hasAllRequiredFieldsToSave,
			type: ACTIONS.SET_IS_ABLE_TO_SAVE,
		});
		dispatch({
			payload: isAbleToNextStep && hasAllRequiredFieldsToNextStep,
			type: ACTIONS.SET_IS_ABLE_TO_NEXT,
		});
	};

	useEffect(() => {
		handleSaveChanges(form);
		setOwnership(form?.ownership);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [form]);

	const ErrorMessage = ({text}: any) => (
		<div className="form-feedback-group">
			<div className="form-feedback-item">
				<span className="form-feedback-indicator">
					<ClayIcon symbol="info-circle"></ClayIcon>
				</span>

				{text ? text : 'This field is required'}
			</div>
		</div>
	);

	const RadioGroup = () => {
		return (
			<ClayRadioGroup className="ml-3" defaultValue={ownership} inline>
				<ClayRadio
					checked={true}
					label="Rent"
					onClick={() => handleChangeField('ownership', 'rent')}
					value="rent"
				/>

				<ClayRadio
					checked={ownership === 'own'}
					label="Own"
					onClick={() => handleChangeField('ownership', 'own')}
					value="own"
				/>
			</ClayRadioGroup>
		);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	};

	return (
		<div className="mx-1 mx-md-8">
			<div className="font-weight-bolder text-paragraph-sm text-uppercase">
				Contact Information
			</div>

			<hr></hr>

			<ClayForm>
				<div className="d-flex flex-column flex-md-row">
					<div
						className={classNames(
							'col filled form-condensed form-group',
							{
								'has-error': hasError.firstName,
							}
						)}
					>
						<ClayInput
							className="bg-neutral-0"
							id="firstName"
							name="firstName"
							onBlur={(event) => {
								const firstNameValue = event.target.value;
								setHasError({
									...hasError,
									firstName:
										firstNameValue === '' ? true : false,
								});
							}}
							onChange={(event) => {
								const firstNameValue = event.target.value;
								handleChangeField('firstName', firstNameValue);
								setHasError({
									...hasError,
									firstName:
										firstNameValue === '' ? true : false,
								});
							}}
							required
							type="text"
							value={form.firstName}
						/>

						<label htmlFor="firstName">
							First Name&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>

						{hasError.firstName && <ErrorMessage />}
					</div>

					<div
						className={classNames(
							'col filled form-condensed form-group',
							{
								'has-error': hasError.lastName,
							}
						)}
					>
						<ClayInput
							className="bg-neutral-0"
							id="lastName"
							name="lastName"
							onBlur={(event) => {
								const lastNameValue = event.target.value;
								setHasError({
									...hasError,
									lastName:
										lastNameValue === '' ? true : false,
								});
							}}
							onChange={(event) => {
								const lastNameValue = event.target.value;
								handleChangeField('lastName', lastNameValue);
								setHasError({
									...hasError,
									lastName:
										lastNameValue === '' ? true : false,
								});
							}}
							required
							type="text"
							value={form.lastName}
						/>

						<label htmlFor="lastName">
							Last Name&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>

						{hasError.lastName && <ErrorMessage />}
					</div>
				</div>

				<div className="d-flex flex-column flex-md-row">
					<div
						className={classNames(
							'col filled form-condensed form-group position-relative',
							{
								'has-error': hasError.dateOfBirth,
							}
						)}
					>
						<ClayDatePicker
							dateFormat="MM/dd/yyyy"
							onBlur={(event) => {
								const dateValue = event;

								handleChangeField(
									'dateOfBirth',
									dateValue.target.value
								);

								setHasError({
									...hasError,
									dateOfBirth:
										dateValue.toString() === ''
											? true
											: false,
								});
							}}
							onChange={(event: any) => {
								const dateValue = event;

								handleChangeField('dateOfBirth', dateValue);

								setHasError({
									...hasError,
									dateOfBirth:
										dateValue === '' ? true : false,
								});
							}}
							placeholder="__/__/____"
							value={form.dateOfBirth}
							years={{
								end: 2026,
								start: 1990,
							}}
						/>

						<ClayIcon
							className="calendar-icon mr-3"
							symbol="calendar"
						/>

						<label htmlFor="dateOfBirth">
							Date of Birth&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>

						{hasError.dateOfBirth && <ErrorMessage />}
					</div>

					<div
						className={classNames(
							'col filled form-condensed form-group',
							{
								'has-error':
									hasError.phone || hasValidation.phone,
							}
						)}
					>
						<ClayInput
							className="bg-neutral-0"
							id="phone"
							name="phone"
							onBlur={(event) => {
								const phoneValue = event.target.value;
								setHasError({
									...hasError,
									phone: phoneValue === '' ? true : false,
								});
							}}
							onChange={(event) => {
								const phoneValue = event.target.value;
								handleChangeField('phone', phoneValue);
								setHasError({
									...hasError,
									phone: phoneValue === '' ? true : false,
								});
								setHasValidation({
									...hasValidation,
									phone:
										isValidPhone(phoneValue) ||
										phoneValue === ''
											? false
											: true,
								});
							}}
							placeholder="xxx-xxx-xxxx"
							required
							type="text"
							value={form.phone}
						/>

						<label htmlFor="phone">
							Phone&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>

						{hasError.phone && <ErrorMessage />}

						{hasValidation.phone && (
							<ErrorMessage text="Phone number is invalid" />
						)}
					</div>

					<div
						className={classNames(
							'col filled form-condensed form-group',
							{
								'has-error':
									hasError.email || hasValidation.email,
							}
						)}
					>
						<ClayInput
							className="bg-neutral-0"
							id="email"
							name="email"
							onBlur={(event) => {
								const emailValue = event.target.value;
								setHasError({
									...hasError,
									email: emailValue === '' ? true : false,
								});
							}}
							onChange={(event) => {
								const emailValue = event.target.value;
								handleChangeField('email', emailValue);
								setHasError({
									...hasError,
									email: emailValue === '' ? true : false,
								});
								setHasValidation({
									...hasValidation,
									email:
										isValidEmail(emailValue) ||
										emailValue === ''
											? false
											: true,
								});
							}}
							required
							type="text"
							value={form.email}
						/>

						<label htmlFor="email">
							Email&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>

						{hasError.email && <ErrorMessage />}

						{!!hasValidation.email && (
							<ErrorMessage text="Email is invalid" />
						)}
					</div>
				</div>
			</ClayForm>

			<div className="font-weight-bolder text-paragraph-sm text-uppercase">
				Address Information
			</div>

			<hr></hr>

			<ClayForm>
				<div className="d-flex flex-column flex-md-row">
					<div
						className={classNames(
							'col filled form-condensed form-group',
							{
								'has-error': hasError.streetAddress,
							}
						)}
					>
						<ClayInput
							className="bg-neutral-0"
							id="streetAddress"
							name="streetAddress"
							onBlur={(event) => {
								const streetAddressValue = event.target.value;
								setHasError({
									...hasError,
									streetAddress:
										streetAddressValue === ''
											? true
											: false,
								});
							}}
							onChange={(event) => {
								const streetAddressValue = event.target.value;
								handleChangeField(
									'streetAddress',
									streetAddressValue
								);
								setHasError({
									...hasError,
									streetAddress:
										streetAddressValue === ''
											? true
											: false,
								});
							}}
							placeholder="Enter a location"
							required
							type="text"
							value={form.streetAddress}
						/>

						<label htmlFor="streetAddress">
							Street Address&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>

						{hasError.streetAddress && <ErrorMessage />}
					</div>

					<div className="col filled form-condensed form-group">
						<ClayInput
							className="bg-neutral-0"
							id="apt"
							name="apt"
							onChange={(event) => {
								handleChangeField('apt', event.target.value);
							}}
							required
							type="text"
							value={form.apt}
						/>

						<label htmlFor="apt">Apt</label>
					</div>
				</div>

				<div className="d-flex flex-column flex-md-row">
					<div
						className={classNames(
							'col filled form-condensed form-group',
							{
								'has-error': hasError.city,
							}
						)}
					>
						<ClayInput
							className="bg-neutral-0"
							id="city"
							name="city"
							onBlur={(event) => {
								const cityValue = event.target.value;
								setHasError({
									...hasError,
									city: cityValue === '' ? true : false,
								});
							}}
							onChange={(event) => {
								const cityValue = event.target.value;
								handleChangeField('city', cityValue);
								setHasError({
									...hasError,
									city: cityValue === '' ? true : false,
								});
							}}
							required
							type="text"
							value={form.city}
						/>

						<label htmlFor="city">
							City&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>

						{hasError.city && <ErrorMessage />}
					</div>

					<div
						className={classNames(
							'col filled form-condensed form-group',
							{
								'has-error': hasError.state,
							}
						)}
					>
						<ClaySelect
							aria-label="Select Label"
							id="mySelectId"
							onBlur={(event) => {
								let stateValue = event.target.value;
								stateValue =
									stateValue === 'CHOOSE AN OPTION'
										? ''
										: stateValue;
								setHasError({
									...hasError,
									state:
										stateValue === '' ||
										stateValue === 'CHOOSE AN OPTION'
											? true
											: false,
								});
							}}
							onChange={(event) => {
								let stateValue = event.target.value;
								stateValue =
									stateValue === 'CHOOSE AN OPTION'
										? ''
										: stateValue;
								handleChangeField('state', stateValue);

								setHasError({
									...hasError,
									state:
										stateValue === '' ||
										stateValue === 'CHOOSE AN OPTION'
											? true
											: false,
								});
							}}
							value={form.state}
						>
							{CONSTANTS.US_STATES.map(
								(us_state: ConstantListType) => (
									<ClaySelect.Option
										key={us_state.value}
										label={us_state.label}
										value={us_state.label}
									/>
								)
							)}
						</ClaySelect>

						<label htmlFor="state">
							State&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>

						{hasError.state && <ErrorMessage />}
					</div>

					<div
						className={classNames(
							'col filled form-condensed form-group',
							{
								'has-error':
									hasError.zipCode || hasValidation.zipCode,
							}
						)}
					>
						<ClayInput
							className="bg-neutral-0"
							id="zipCode"
							name="zipCode"
							onBlur={(event) => {
								const zipCodeValue = event.target.value;
								setHasError({
									...hasError,
									zipCode: zipCodeValue === '' ? true : false,
								});
							}}
							onChange={(event) => {
								const zipCodeValue = event.target.value;
								handleChangeField('zipCode', zipCodeValue);
								setHasError({
									...hasError,
									zipCode: zipCodeValue === '' ? true : false,
								});
								setHasValidation({
									...hasValidation,
									zipCode:
										isValidUSZip(zipCodeValue) ||
										zipCodeValue === ''
											? false
											: true,
								});
							}}
							required
							type="text"
							value={form.zipCode}
						/>

						<label htmlFor="zipCode">
							Zip Code&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>

						{hasError.zipCode && <ErrorMessage />}

						{!!hasValidation.zipCode && (
							<ErrorMessage text="Zip code is invalid" />
						)}
					</div>
				</div>
			</ClayForm>

			<div>
				<div className="mb-1 ml-3 text-neutral-9 text-paragraph-sm">
					Ownership&nbsp;
					<span className="text-danger-darken-1">*</span>
				</div>

				<RadioGroup />
			</div>
		</div>
	);
};

export default ContactInfo;
