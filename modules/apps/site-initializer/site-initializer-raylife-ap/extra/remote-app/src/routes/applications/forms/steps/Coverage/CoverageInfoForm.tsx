/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useContext, useEffect, useState} from 'react';

import {
	ACTIONS,
	NewApplicationAutoContext,
} from '../../../context/NewApplicationAutoContextProvider';
import {
	bodilyInjuryOptions,
	collisionOptions,
	comprehensiveOptions,
	medicalOptions,
	propertyDamageOptions,
	uninsuredOrUnderinsuredMBIOptions,
	uninsuredOrUnderinsuredMPDOptions,
} from './SelectOptions';

type hasRequiredErrorTypes = {
	bodilyInjury: boolean;
	collision: boolean;
	comprehensive: boolean;
	medical: boolean;
	propertyDamage: boolean;
	uninsuredOrUnderinsuredMBI: boolean;
	uninsuredOrUnderinsuredMPD: boolean;
};

type StateInfoTypes = 'CA' | 'NY' | 'NV';

const hasRequiredError: hasRequiredErrorTypes = {
	bodilyInjury: false,
	collision: false,
	comprehensive: false,
	medical: false,
	propertyDamage: false,
	uninsuredOrUnderinsuredMBI: false,
	uninsuredOrUnderinsuredMPD: false,
};

const CoverageInfoForm = () => {
	const [state, dispatch] = useContext(NewApplicationAutoContext);

	const {form} = state?.steps?.coverage;

	const vehicleInfoForm = state?.steps?.vehicleInfo?.form;

	const stateInfoForm = state?.steps?.contactInfo?.form
		?.state as StateInfoTypes;

	const handleChangeField = (fieldName: string, fieldValue: string) => {
		const payload = {
			...form,
			[fieldName]: fieldValue,
		};

		dispatch({payload, type: ACTIONS.SET_COVERAGE_FORM});
		dispatch({payload: true, type: ACTIONS.SET_HAS_FORM_CHANGE});
	};

	const handleChangeVehicleInfo = (
		index: number,
		fieldName: string,
		fieldValue: string
	) => {
		const vehicles = form.vehicles.map((vehicle, vehicleIndex) => {
			if (vehicleIndex === index) {
				return {
					...vehicle,
					[fieldName]: fieldValue,
				};
			}

			return vehicle;
		});

		dispatch({
			payload: {
				...form,
				vehicles,
			},
			type: ACTIONS.SET_COVERAGE_FORM,
		});
		dispatch({payload: true, type: ACTIONS.SET_HAS_FORM_CHANGE});
	};

	const [hasError, setHasError] = useState(hasRequiredError);

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

	const VehicleForm = (
		vehicleInfoForm: any,
		formIndex: number
	): JSX.Element => {
		const handleSaveChanges = (currentForm: any) => {
			const isAbleToSave =
				!hasError.bodilyInjury ||
				!hasError.collision ||
				!hasError.comprehensive ||
				!hasError.medical ||
				!hasError.propertyDamage ||
				!hasError.uninsuredOrUnderinsuredMBI ||
				!hasError.uninsuredOrUnderinsuredMPD;

			const hasSomeField =
				currentForm.bodilyInjury !== '' ||
				currentForm.vehicles[formIndex].collision !== '' ||
				currentForm.vehicles[formIndex].comprehensive !== '' ||
				currentForm.medical !== '' ||
				currentForm.propertyDamage !== '' ||
				currentForm.uninsuredOrUnderinsuredMBI !== '' ||
				currentForm.uninsuredOrUnderinsuredMPD;

			const isAbleToNextStep =
				isAbleToSave &&
				Object.values(hasError).every((value) => value === false);

			const hasAllRequiredFieldsToNextStep =
				hasSomeField &&
				currentForm.bodilyInjury &&
				currentForm.vehicles[formIndex].collision &&
				currentForm.vehicles[formIndex].comprehensive &&
				currentForm.medical &&
				currentForm.propertyDamage &&
				currentForm.uninsuredOrUnderinsuredMBI &&
				currentForm.uninsuredOrUnderinsuredMPD;

			dispatch({
				payload: isAbleToSave && hasSomeField,
				type: ACTIONS.SET_IS_ABLE_TO_SAVE,
			});
			dispatch({
				payload: isAbleToNextStep && hasAllRequiredFieldsToNextStep,
				type: ACTIONS.SET_IS_ABLE_TO_NEXT,
			});
		};

		useEffect(() => {
			handleSaveChanges(form);

			// eslint-disable-next-line react-hooks/exhaustive-deps
		}, [form]);

		return (
			<>
				<div className="font-weight-bolder mb-4 mt-3 text-paragraph-sm text-uppercase">
					<u>
						{`Vehicle ${formIndex + 1}: ${vehicleInfoForm.year} ${
							vehicleInfoForm.make
						}`}
					</u>
				</div>

				<div className="row" key={formIndex}>
					<div
						className={classNames(
							'col-sm filled form-condensed form-group',

							{
								'has-error': (hasError as any)[
									`comprehensive.${formIndex}`
								],
							}
						)}
					>
						<ClaySelect
							aria-label="Select Label"
							id="comprehensive"
							name="comprehensive"
							onBlur={(event) => {
								let comprehensiveValue = event.target.value;
								comprehensiveValue =
									comprehensiveValue === 'CHOOSE AN OPTION'
										? ''
										: comprehensiveValue;
								setHasError({
									...hasError,
									comprehensive:
										comprehensiveValue === ''
											? true
											: false,
								});
							}}
							onChange={(event) => {
								let comprehensiveValue = event.target.value;
								comprehensiveValue =
									comprehensiveValue === 'CHOOSE AN OPTION'
										? ''
										: comprehensiveValue;
								handleChangeVehicleInfo(
									formIndex,
									'comprehensive',
									comprehensiveValue
								);

								setHasError({
									...hasError,
									[`comprehensive.${formIndex}`]:
										comprehensiveValue === ''
											? true
											: false,
								});
							}}
							value={form.vehicles[formIndex].comprehensive}
						>
							{comprehensiveOptions[stateInfoForm].map(
								(comprehensiveOption: any, index: number) => (
									<ClaySelect.Option
										className="font-weight-bold text-center text-primary"
										key={index}
										label={comprehensiveOption.label}
										value={comprehensiveOption.value}
									/>
								)
							)}
						</ClaySelect>

						<label htmlFor="Comprehensive">
							Comprehensive&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>

						{(hasError as any)[`comprehensive.${formIndex}`] && (
							<ErrorMessage />
						)}
					</div>

					<div
						className={classNames(
							'col-sm filled form-condensed form-group',

							{
								'has-error': (hasError as any)[
									`collision.${formIndex}`
								],
							}
						)}
					>
						<ClaySelect
							aria-label="Select Label"
							id="collision"
							onBlur={(event) => {
								let collisionValue = event.target.value;
								collisionValue =
									collisionValue === 'CHOOSE AN OPTION'
										? ''
										: collisionValue;

								setHasError({
									...hasError,
									collision:
										collisionValue === '' ? true : false,
								});
							}}
							onChange={(event) => {
								let collisionValue = event.target.value;
								collisionValue =
									collisionValue === 'CHOOSE AN OPTION'
										? ''
										: collisionValue;
								handleChangeVehicleInfo(
									formIndex,
									'collision',
									collisionValue
								);

								setHasError({
									...hasError,
									[`collision.${formIndex}`]:
										collisionValue === '' ? true : false,
								});
							}}
							value={form.vehicles[formIndex].collision}
						>
							{collisionOptions[stateInfoForm].map(
								(collisionOption: any, index: number) => (
									<ClaySelect.Option
										className="font-weight-bold text-center text-primary"
										key={index}
										label={collisionOption.label}
										value={collisionOption.value}
									/>
								)
							)}
						</ClaySelect>

						<label htmlFor="Collision">
							Collision&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>

						{(hasError as any)[`collision.${formIndex}`] && (
							<ErrorMessage />
						)}
					</div>
				</div>
			</>
		);
	};

	return (
		<div className="container m-0 p-4">
			<p className="font-weight-bolder mb-4 mt-0 text-paragraph-sm text-uppercase">
				Choose your coverages
			</p>

			<hr />

			<div className="font-weight-bolder mb-4 mt-0 text-paragraph-sm text-uppercase">
				<u>Policy Coverages</u>
			</div>

			<div className="row">
				<div
					className={classNames(
						'col-sm filled form-condensed form-group',

						{
							'has-error': hasError.bodilyInjury,
						}
					)}
				>
					<ClaySelect
						aria-label="Select Label"
						className="text-left"
						id="mySelectId"
						onBlur={(event) => {
							let bodilyInjuryValue = event.target.value;
							bodilyInjuryValue =
								bodilyInjuryValue === 'CHOOSE AN OPTION'
									? ''
									: bodilyInjuryValue;
							setHasError({
								...hasError,
								bodilyInjury:
									bodilyInjuryValue === '' ||
									bodilyInjuryValue === 'CHOOSE AN OPTION'
										? true
										: false,
							});
						}}
						onChange={(event) => {
							let bodilyInjuryValue = event.target.value;
							bodilyInjuryValue =
								bodilyInjuryValue === 'CHOOSE AN OPTION'
									? ''
									: bodilyInjuryValue;
							handleChangeField(
								'bodilyInjury',
								bodilyInjuryValue
							);

							setHasError({
								...hasError,
								bodilyInjury:
									bodilyInjuryValue === '' ||
									bodilyInjuryValue === 'CHOOSE AN OPTION'
										? true
										: false,
							});
						}}
						value={form.bodilyInjury}
					>
						{bodilyInjuryOptions[stateInfoForm].map(
							(bodilyInjuryOption: any, index: number) => (
								<ClaySelect.Option
									className="font-weight-bold text-center text-primary"
									key={index}
									label={bodilyInjuryOption.label}
									value={bodilyInjuryOption.value}
								/>
							)
						)}
					</ClaySelect>

					<label htmlFor="mySelectId">
						Bodily Injury&nbsp;
						<span className="text-danger-darken-1">*</span>
					</label>

					{hasError.bodilyInjury && <ErrorMessage />}
				</div>

				<div
					className={classNames(
						'col-sm filled form-condensed form-group',

						{
							'has-error': hasError.propertyDamage,
						}
					)}
				>
					<ClaySelect
						aria-label="Select Label"
						id="propertyDamage"
						onBlur={(event) => {
							let propertyDamageValue = event.target.value;
							propertyDamageValue =
								propertyDamageValue === 'CHOOSE AN OPTION'
									? ''
									: propertyDamageValue;
							setHasError({
								...hasError,
								propertyDamage:
									propertyDamageValue === '' ||
									propertyDamageValue === 'CHOOSE AN OPTION'
										? true
										: false,
							});
						}}
						onChange={(event) => {
							let propertyDamageValue = event.target.value;
							propertyDamageValue =
								propertyDamageValue === 'CHOOSE AN OPTION'
									? ''
									: propertyDamageValue;
							handleChangeField(
								'propertyDamage',
								propertyDamageValue
							);

							setHasError({
								...hasError,
								propertyDamage:
									propertyDamageValue === '' ||
									propertyDamageValue === 'CHOOSE AN OPTION'
										? true
										: false,
							});
						}}
						value={form.propertyDamage}
					>
						{propertyDamageOptions[stateInfoForm].map(
							(propertyDamageOption: any, index: number) => (
								<ClaySelect.Option
									className="font-weight-bold text-center text-primary"
									key={index}
									label={propertyDamageOption.label}
									value={propertyDamageOption.value}
								/>
							)
						)}
					</ClaySelect>

					<label htmlFor="propertyDamage">
						Property Damage&nbsp;
						<span className="text-danger-darken-1">*</span>
					</label>

					{hasError.propertyDamage && <ErrorMessage />}
				</div>
			</div>

			<div className="row">
				<div
					className={classNames(
						'col-sm filled form-condensed form-group',

						{
							'has-error': hasError.uninsuredOrUnderinsuredMBI,
						}
					)}
				>
					<ClaySelect
						aria-label="Select Label"
						id="uninsuredOrUnderinsuredMBI"
						onBlur={(event) => {
							let uninsuredOrUnderinsuredMBIOptionValue =
								event.target.value;
							uninsuredOrUnderinsuredMBIOptionValue =
								uninsuredOrUnderinsuredMBIOptionValue ===
								'CHOOSE AN OPTION'
									? ''
									: uninsuredOrUnderinsuredMBIOptionValue;
							setHasError({
								...hasError,
								uninsuredOrUnderinsuredMBI:
									uninsuredOrUnderinsuredMBIOptionValue ===
										'' ||
									uninsuredOrUnderinsuredMBIOptionValue ===
										'CHOOSE AN OPTION'
										? true
										: false,
							});
						}}
						onChange={(event) => {
							let uninsuredOrUnderinsuredMBIOptionValue =
								event.target.value;
							uninsuredOrUnderinsuredMBIOptionValue =
								uninsuredOrUnderinsuredMBIOptionValue ===
								'CHOOSE AN OPTION'
									? ''
									: uninsuredOrUnderinsuredMBIOptionValue;
							handleChangeField(
								'uninsuredOrUnderinsuredMBI',
								uninsuredOrUnderinsuredMBIOptionValue
							);

							setHasError({
								...hasError,
								uninsuredOrUnderinsuredMBI:
									uninsuredOrUnderinsuredMBIOptionValue ===
										'' ||
									uninsuredOrUnderinsuredMBIOptionValue ===
										'CHOOSE AN OPTION'
										? true
										: false,
							});
						}}
						value={form.uninsuredOrUnderinsuredMBI}
					>
						{uninsuredOrUnderinsuredMBIOptions[stateInfoForm].map(
							(
								uninsuredOrUnderinsuredMBIOption: any,
								index: number
							) => (
								<ClaySelect.Option
									className="font-weight-bold text-center text-primary"
									key={index}
									label={
										uninsuredOrUnderinsuredMBIOption.label
									}
									value={
										uninsuredOrUnderinsuredMBIOption.value
									}
								/>
							)
						)}
					</ClaySelect>

					<label htmlFor="uninsuredOrUnderinsuredMBI">
						Uninsured / Underinsured Motorist Bodily Injured&nbsp;
						<span className="text-danger-darken-1">*</span>
					</label>

					{hasError.uninsuredOrUnderinsuredMBI && <ErrorMessage />}
				</div>

				<div
					className={classNames(
						'col-sm filled form-condensed form-group',

						{
							'has-error': hasError.uninsuredOrUnderinsuredMPD,
						}
					)}
				>
					<ClaySelect
						aria-label="Select Label"
						id="uninsuredOrUnderinsuredMPD"
						onBlur={(event) => {
							let uninsuredOrUnderinsuredMPDValue =
								event.target.value;
							uninsuredOrUnderinsuredMPDValue =
								uninsuredOrUnderinsuredMPDValue ===
								'CHOOSE AN OPTION'
									? ''
									: uninsuredOrUnderinsuredMPDValue;
							setHasError({
								...hasError,
								uninsuredOrUnderinsuredMPD:
									uninsuredOrUnderinsuredMPDValue === '' ||
									uninsuredOrUnderinsuredMPDValue ===
										'CHOOSE AN OPTION'
										? true
										: false,
							});
						}}
						onChange={(event) => {
							let uninsuredOrUnderinsuredMPDValue =
								event.target.value;
							uninsuredOrUnderinsuredMPDValue =
								uninsuredOrUnderinsuredMPDValue ===
								'CHOOSE AN OPTION'
									? ''
									: uninsuredOrUnderinsuredMPDValue;
							handleChangeField(
								'uninsuredOrUnderinsuredMPD',
								uninsuredOrUnderinsuredMPDValue
							);

							setHasError({
								...hasError,
								uninsuredOrUnderinsuredMPD:
									uninsuredOrUnderinsuredMPDValue === '' ||
									uninsuredOrUnderinsuredMPDValue ===
										'CHOOSE AN OPTION'
										? true
										: false,
							});
						}}
						value={form.uninsuredOrUnderinsuredMPD}
					>
						{uninsuredOrUnderinsuredMPDOptions[stateInfoForm].map(
							(
								uninsuredOrUnderinsuredMPDOption: any,
								index: number
							) => (
								<ClaySelect.Option
									className="font-weight-bold text-center text-primary"
									key={index}
									label={
										uninsuredOrUnderinsuredMPDOption.label
									}
									value={
										uninsuredOrUnderinsuredMPDOption.value
									}
								/>
							)
						)}
					</ClaySelect>

					<label htmlFor="uninsuredOrUnderinsuredMPD">
						Uninsured / Underinsured Motorist Property Damage&nbsp;
						<span className="text-danger-darken-1">*</span>
					</label>

					{hasError.uninsuredOrUnderinsuredMPD && <ErrorMessage />}
				</div>
			</div>

			<div className="row">
				<div
					className={classNames(
						'col-sm filled form-condensed form-group',

						{
							'has-error': hasError.medical,
						}
					)}
				>
					<ClaySelect
						aria-label="Select Label"
						id="Medical"
						onBlur={(event) => {
							let medicalValue = event.target.value;
							medicalValue =
								medicalValue === 'CHOOSE AN OPTION'
									? ''
									: medicalValue;
							setHasError({
								...hasError,
								medical:
									medicalValue === '' ||
									medicalValue === 'CHOOSE AN OPTION'
										? true
										: false,
							});
						}}
						onChange={(event) => {
							let medicalValue = event.target.value;
							medicalValue =
								medicalValue === 'CHOOSE AN OPTION'
									? ''
									: medicalValue;
							handleChangeField('medical', medicalValue);

							setHasError({
								...hasError,
								medical:
									medicalValue === '' ||
									medicalValue === 'CHOOSE AN OPTION'
										? true
										: false,
							});
						}}
						value={form.medical}
					>
						{medicalOptions[stateInfoForm].map(
							(medicalOption: any, index: number) => (
								<ClaySelect.Option
									className="font-weight-bold text-center text-primary"
									key={index}
									label={medicalOption.label}
									value={medicalOption.value}
								/>
							)
						)}
					</ClaySelect>

					<label htmlFor="Medical">
						Medical&nbsp;
						<span className="text-danger-darken-1">*</span>
					</label>

					{hasError.medical && <ErrorMessage />}
				</div>

				<div className="col-sm"></div>
			</div>

			{vehicleInfoForm.map((vehicleInfo: any, index: number) =>
				VehicleForm(vehicleInfo, index)
			)}
		</div>
	);
};

export default CoverageInfoForm;
