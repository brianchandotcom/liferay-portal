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

	const handleChangeField = (fieldName: string, fieldValue: string) => {
		const payload = {
			...form,
			[fieldName]: fieldValue,
		};

		dispatch({payload, type: ACTIONS.SET_COVERAGE_FORM});
		dispatch({payload: true, type: ACTIONS.SET_HAS_FORM_CHANGE});
	};

	const [hasError, setHasError] = useState(hasRequiredError);

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
			currentForm.collision !== '' ||
			currentForm.comprehensive !== '' ||
			currentForm.medical !== '' ||
			currentForm.propertyDamage !== '' ||
			currentForm.uninsuredOrUnderinsuredMBI !== '' ||
			currentForm.uninsuredOrUnderinsuredMPD;

		const isAbleToNextStep =
			isAbleToSave &&
			!hasError.bodilyInjury &&
			!hasError.collision &&
			!hasError.comprehensive &&
			!hasError.medical &&
			!hasError.propertyDamage &&
			!hasError.uninsuredOrUnderinsuredMBI &&
			!hasError.uninsuredOrUnderinsuredMPD;

		const hasAllRequiredFieldsToNextStep =
			hasSomeField &&
			currentForm.bodilyInjury &&
			currentForm.collision &&
			currentForm.comprehensive &&
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
							const bodilyInjuryValue = event.target.value;
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
							const bodilyInjuryValue = event.target.value;
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
						{bodilyInjuryOptions.map(
							(bodilyInjuryOption, index) => (
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
							const propertyDamageValue = event.target.value;
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
							const propertyDamageValue = event.target.value;
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
						{propertyDamageOptions.map(
							(propertyDamageOption, index) => (
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
							const uninsuredOrUnderinsuredMBIOptionValue =
								event.target.value;
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
							const uninsuredOrUnderinsuredMBIOptionValue =
								event.target.value;
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
						{uninsuredOrUnderinsuredMBIOptions.map(
							(uninsuredOrUnderinsuredMBIOption, index) => (
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
							const uninsuredOrUnderinsuredMPDValue =
								event.target.value;
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
							const uninsuredOrUnderinsuredMPDValue =
								event.target.value;
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
						{uninsuredOrUnderinsuredMPDOptions.map(
							(uninsuredOrUnderinsuredMPDOption, index) => (
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
							const medicalValue = event.target.value;
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
							const medicalValue = event.target.value;
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
						{medicalOptions.map((medicalOption, index) => (
							<ClaySelect.Option
								className="font-weight-bold text-center text-primary"
								key={index}
								label={medicalOption.label}
								value={medicalOption.value}
							/>
						))}
					</ClaySelect>

					<label htmlFor="Medical">
						Medical&nbsp;
						<span className="text-danger-darken-1">*</span>
					</label>

					{hasError.medical && <ErrorMessage />}
				</div>

				<div className="col-sm"></div>
			</div>

			<div className="font-weight-bolder mb-4 mt-3 text-paragraph-sm text-uppercase">
				<u>Vehicle 1: 2018 Toyota</u>
			</div>

			<div className="row">
				<div
					className={classNames(
						'col-sm filled form-condensed form-group',

						{
							'has-error': hasError.comprehensive,
						}
					)}
				>
					<ClaySelect
						aria-label="Select Label"
						id="Comprehensive"
						onBlur={(event) => {
							const comprehensiveValue = event.target.value;
							setHasError({
								...hasError,
								comprehensive:
									comprehensiveValue === '' ||
									comprehensiveValue === 'CHOOSE AN OPTION'
										? true
										: false,
							});
						}}
						onChange={(event) => {
							const comprehensiveValue = event.target.value;
							handleChangeField(
								'comprehensive',
								comprehensiveValue
							);

							setHasError({
								...hasError,
								comprehensive:
									comprehensiveValue === '' ||
									comprehensiveValue === 'CHOOSE AN OPTION'
										? true
										: false,
							});
						}}
						value={form.comprehensive}
					>
						{comprehensiveOptions.map(
							(comprehensiveOption, index) => (
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

					{hasError.comprehensive && <ErrorMessage />}
				</div>

				<div
					className={classNames(
						'col-sm filled form-condensed form-group',

						{
							'has-error': hasError.collision,
						}
					)}
				>
					<ClaySelect
						aria-label="Select Label"
						id="Collision"
						onBlur={(event) => {
							const collisionValue = event.target.value;
							setHasError({
								...hasError,
								collision:
									collisionValue === '' ||
									collisionValue === 'CHOOSE AN OPTION'
										? true
										: false,
							});
						}}
						onChange={(event) => {
							const collisionValue = event.target.value;
							handleChangeField('collision', collisionValue);

							setHasError({
								...hasError,
								collision:
									collisionValue === '' ||
									collisionValue === 'CHOOSE AN OPTION'
										? true
										: false,
							});
						}}
						value={form.collision}
					>
						{collisionOptions.map((collisionOption, index) => (
							<ClaySelect.Option
								className="font-weight-bold text-center text-primary"
								key={index}
								label={collisionOption.label}
								value={collisionOption.value}
							/>
						))}
					</ClaySelect>

					<label htmlFor="collision">
						Collision&nbsp;
						<span className="text-danger-darken-1">*</span>
					</label>

					{hasError.collision && <ErrorMessage />}
				</div>
			</div>
		</div>
	);
};

export default CoverageInfoForm;
