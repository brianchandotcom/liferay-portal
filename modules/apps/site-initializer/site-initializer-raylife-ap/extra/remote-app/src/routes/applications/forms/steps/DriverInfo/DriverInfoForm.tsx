/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayForm, {
	ClayInput,
	ClayRadio,
	ClayRadioGroup,
	ClaySelect,
} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useContext, useEffect, useState} from 'react';

import {
	ACTIONS,
	NewApplicationAutoContext,
} from '../../../context/NewApplicationAutoContextProvider';
import {
	accidentCitationOptions,
	genderOptions,
	governmentAffiliationOptions,
	highestEducationOptions,
	maritalStatusOptions,
	millitaryAffiliationOptions,
	occupationOptions,
	relationToContactOptions,
} from './SelectOptions';

type FormDriverInfoTypes = {
	accidentCitationForm: any[];
	form: any[];
	formIndex: number;
	formNumber: number;
	id: number;
};

const DriverInfoForm = ({
	form,
	formIndex,
	formNumber,
	id,
}: FormDriverInfoTypes) => {
	const [_state, dispatch] = useContext(NewApplicationAutoContext);

	const [occupationValue, setOccupationValue] = useState<string>();

	const [newAccidentCitations, setNewAccidentCitations] = useState<any[]>([]);

	const [
		_millitaryAffiliationOptions,
		setMillitaryAffiliationOptions,
	] = useState<any[]>([]);

	const [millitaryAffiliation, setMillitaryAffiliation] = useState<any>('');

	enum dropdownAlign {
		topCenter = 0,
		topRight = 1,
		middleRight = 2,
		bottomRight = 3,
		bottomCenter = 4,
		bottomLeft = 5,
		middleLeft = 6,
		topLeft = 7,
	}

	type hasRequiredErrorTypes = {
		accidentCitation: boolean;
		ageFirstLicenced: boolean;
		firstName: boolean;
		gender: boolean;
		governmentAffiliation: boolean;
		highestEducation: boolean;
		lastName: boolean;
		maritalStatus: boolean;
		millitaryAffiliation: boolean;
		occupation: boolean;
		otherOccupation: boolean;
		relationToContact: boolean;
	};

	const hasRequiredError: hasRequiredErrorTypes = {
		accidentCitation: false,
		ageFirstLicenced: false,
		firstName: false,
		gender: false,
		governmentAffiliation: false,
		highestEducation: false,
		lastName: false,
		maritalStatus: false,
		millitaryAffiliation: false,
		occupation: false,
		otherOccupation: false,
		relationToContact: false,
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

	const accidentCitations = form.map((form) => {
		const accidentCitationList = form.accidentCitation || [];

		return accidentCitationList.filter(
			(accidentCitation: {id: number}) =>
				accidentCitation.id !== form.accidentCitation.id
		);
	});

	const handleUpdateField = (
		formId: number,
		currentId: number,
		index: number,
		value: string
	) => {
		const payload = {
			formId,
			id: currentId,
			index,
			value,
		};

		dispatch({payload, type: ACTIONS.UPDATE_DRIVER_INFO_FORM});
	};

	const currentId = Number((Math.random() * 1000000).toFixed(0));

	const handleAccidentCitation = () => {
		const newAccidentCitationOptions = accidentCitationOptions.map(
			(accidentCitationOption) => {
				return {
					...accidentCitationOption,
					onClick: (event: any) => {
						handleUpdateField(
							form[formIndex].id,
							currentId,
							formIndex,
							event.target.textContent
						);
					},
				};
			}
		);

		setNewAccidentCitations([
			...newAccidentCitations,
			newAccidentCitationOptions,
		]);
	};

	const handleAddCitationClick = () => {
		const driverInfoObject = {
			accidentCitation: [{id: currentId, value: ''}],
			ageFirstLicenced: '',
			firstName: '',
			gender: '',
			governmentAffiliation: '',
			hasAccidentOrCitations: '',
			highestEducation: '',
			id,
			lastName: '',
			maritalStatus: '',
			millitaryAffiliation: '',
			occupation: '',
			otherOccupation: '',
			relationToContact: '',
		};

		handleAccidentCitation();

		dispatch({
			payload: driverInfoObject,
			type: ACTIONS.SET_NEW_ACCIDENT_CITATION,
		});
	};

	const handleChangeField = (
		fieldName: string,
		value: string,
		id: number
	) => {
		const payload = {
			fieldName,
			id,
			value,
		};

		dispatch({payload, type: ACTIONS.SET_DRIVER_INFO_FORM});

		dispatch({payload: true, type: ACTIONS.SET_HAS_FORM_CHANGE});
	};

	const handleRemoveFormClick = (id: number) => {
		const payload = {id};

		dispatch({payload, type: ACTIONS.SET_REMOVE_DRIVER});
	};

	const handleRemoveAccidentCitationClick = (
		accidentCitationIndex: number,
		formIndex: number
	) => {
		const payload = {
			accidentCitationIndex,
			formIndex,
		};
		dispatch({payload, type: ACTIONS.SET_REMOVE_ACCIDENT_CITATION});
	};

	const [
		millitaryAffiliationSearch,
		setMillitaryAffiliationSearch,
	] = useState<any>('');

	const millitaryAffiliationOptionsAddClick = () => {
		return millitaryAffiliationOptions.map((millitaryAffiliationOption) => {
			return {
				...millitaryAffiliationOption,
				id,
				onClick: (event: any) => {
					let value = event.target.textContent || '';

					value = value === 'Choose an option' ? '' : value;
					setMillitaryAffiliation(value);
					handleChangeField('millitaryAffiliation', value, id);
					setHasError({
						...hasError,
						millitaryAffiliation: value === '',
					});
				},
			};
		});
	};

	useEffect(() => {
		setMillitaryAffiliationOptions(millitaryAffiliationOptionsAddClick());
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		setMillitaryAffiliationSearch('');
		setMillitaryAffiliationOptions(millitaryAffiliationOptionsAddClick());
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [millitaryAffiliation]);

	return (
		<div className="bg-neutral-0">
			<div className="align-items-center d-flex justify-content-between ml-5 mr-3 row">
				<div className="border-bottom flex-grow-1">
					<span className="font-weight-bolder text-paragraph-sm text-uppercase">
						Driver {formNumber}
					</span>
				</div>

				{form.length > 1 && (
					<div className="">
						<ClayButton
							className="border-white font-weight-bolder text-paragraph-sm text-uppercase"
							displayType="secondary"
							onClick={() => handleRemoveFormClick(id)}
						>
							<ClayIcon symbol="times-circle" />
							&nbsp;Remove Driver
						</ClayButton>
					</div>
				)}
			</div>

			<div className="mx-3">
				<hr />

				<div className="font-weight-bolder mb-4 mt-3 text-paragraph-sm text-uppercase">
					<u>Driver Information</u>
				</div>
			</div>

			<ClayForm>
				<div className="row">
					<div
						className={classNames(
							'col-md col-12 filled form-condensed form-group',
							{
								'has-error': hasError.firstName,
							}
						)}
					>
						<ClayInput
							autoComplete="off"
							id="firstName"
							name="firstName"
							onBlur={(event) => {
								setHasError({
									...hasError,
									firstName:
										event.target.value === ''
											? true
											: false,
								});
							}}
							onChange={(event) => {
								handleChangeField(
									'firstName',
									event.target.value,
									id
								);
								setHasError({
									...hasError,
									firstName:
										event.target.value === ''
											? true
											: false,
								});
							}}
							required
							type="text"
							value={form[formNumber - 1].firstName}
						/>

						<label htmlFor="firstName">
							First Name&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>

						{hasError.firstName && <ErrorMessage />}
					</div>

					<div
						className={classNames(
							'col-md col-12 filled form-condensed form-group',
							{
								'has-error': hasError.lastName,
							}
						)}
					>
						<ClayInput
							autoComplete="off"
							id="lastName"
							name="lastName"
							onBlur={(event) => {
								setHasError({
									...hasError,
									lastName:
										event.target.value === ''
											? true
											: false,
								});
							}}
							onChange={(event) => {
								handleChangeField(
									'lastName',
									event.target.value,
									id
								);
								setHasError({
									...hasError,
									lastName:
										event.target.value === ''
											? true
											: false,
								});
							}}
							required
							type="text"
							value={form[formNumber - 1].lastName}
						/>

						<label htmlFor="lastName">
							Last Name&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>

						{hasError.lastName && <ErrorMessage />}
					</div>

					<div
						className={classNames(
							'col-md col-12 filled form-condensed form-group',
							{
								'has-error': hasError.relationToContact,
							}
						)}
					>
						<ClaySelect
							aria-label="Select Label"
							id="relationToContact"
							onChange={(event) => {
								let value = event.target.value;

								value =
									value === 'Choose an option' ? '' : value;
								handleChangeField(
									'relationToContact',
									value,
									id
								);
								setHasError({
									...hasError,
									relationToContact: value === '',
								});
							}}
							value={form[formNumber - 1].relationToContact}
						>
							{relationToContactOptions.map(
								(relationToContactOption) => (
									<ClaySelect.Option
										className="font-weight-bold text-brand-primary text-center text-paragraph-sm text-uppercase"
										key={relationToContactOption.value}
										label={relationToContactOption.label}
										value={relationToContactOption.value}
									/>
								)
							)}
						</ClaySelect>

						<label htmlFor="relationToContact">
							Relation To Contact&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>

						{hasError.relationToContact && <ErrorMessage />}
					</div>
				</div>

				<div className="row">
					<div
						className={classNames(
							'col-md col-12 filled form-condensed form-group',
							{
								'has-error': hasError.gender,
							}
						)}
					>
						<ClaySelect
							aria-label="Select Label"
							id="gender"
							onChange={(event) => {
								let value = event.target.value;
								value =
									value === 'Choose an option' ? '' : value;
								handleChangeField('gender', value, id);
								setHasError({
									...hasError,
									gender: value === '' ? true : false,
								});
							}}
							value={form[formNumber - 1].gender}
						>
							{genderOptions.map((genderOption) => (
								<ClaySelect.Option
									className="font-weight-bold text-brand-primary text-center text-paragraph-sm text-uppercase"
									key={genderOption.value}
									label={genderOption.label}
									value={genderOption.value}
								/>
							))}
						</ClaySelect>

						<label htmlFor="gender">
							Gender&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>

						{hasError.gender && <ErrorMessage />}
					</div>

					<div
						className={classNames(
							'col-md col-12 filled form-condensed form-group',
							{
								'has-error': hasError.maritalStatus,
							}
						)}
					>
						<ClaySelect
							aria-label="Select Label"
							id="maritalStatus"
							onChange={(event) => {
								let value = event.target.value;
								value =
									value === 'Choose an option' ? '' : value;
								handleChangeField('maritalStatus', value, id);
								setHasError({
									...hasError,
									maritalStatus: value === '' ? true : false,
								});
							}}
							value={form[formNumber - 1].maritalStatus}
						>
							{maritalStatusOptions.map((maritalStatusOption) => (
								<ClaySelect.Option
									className="font-weight-bold text-brand-primary text-center text-paragraph-sm text-uppercase"
									key={maritalStatusOption.value}
									label={maritalStatusOption.label}
									value={maritalStatusOption.value}
								/>
							))}
						</ClaySelect>

						<label htmlFor="maritalStatus">
							Marital Status&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>

						{hasError.maritalStatus && <ErrorMessage />}
					</div>

					<div
						className={classNames(
							'col-md col-12 filled form-condensed form-group',
							{
								'has-error': hasError.ageFirstLicenced,
							}
						)}
					>
						<ClayInput
							autoComplete="off"
							className="ageFirstLicenced bg-neutral-0 w-100"
							id="ageFirstLicenced"
							name="ageFirstLicenced"
							onChange={(event) => {
								let value = event.target.value;
								value =
									value === 'Choose an option' ? '' : value;
								handleChangeField(
									'ageFirstLicenced',
									value,
									id
								);
								setHasError({
									...hasError,
									ageFirstLicenced:
										value === '' ? true : false,
								});
							}}
							required
							type="number"
							value={form[formNumber - 1].ageFirstLicenced}
						/>

						<label htmlFor="ageFirstLicenced">
							Age First Licenced&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>

						{hasError.ageFirstLicenced && <ErrorMessage />}
					</div>
				</div>

				<div className="row">
					<div
						className={classNames(
							'col-md col-12 filled form-condensed form-group',
							{
								'has-error': hasError.occupation,
							}
						)}
					>
						<ClaySelect
							aria-label="Select Label"
							id="occupation"
							onChange={(event) => {
								let value = event.target.value;

								setOccupationValue(value);

								value =
									value === 'Choose an option' ? '' : value;

								handleChangeField('occupation', value, id);

								setHasError({
									...hasError,
									occupation: value === '' ? true : false,
								});
							}}
							value={form[formNumber - 1].occupation}
						>
							{occupationOptions.map((occupationOption) => (
								<ClaySelect.Option
									className="font-weight-bold text-brand-primary text-center text-paragraph-sm text-uppercase"
									key={occupationOption.value}
									label={occupationOption.label}
									value={occupationOption.value}
								/>
							))}
						</ClaySelect>

						<label htmlFor="occupation">
							Occupation&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>

						{hasError.occupation && <ErrorMessage />}
					</div>

					<div
						className={classNames(
							'col-md col-12 filled form-condensed form-group',
							{
								'has-error': hasError.otherOccupation,
							}
						)}
					>
						<ClayInput
							autoComplete="off"
							className="bg-neutral-0 otherOccupation w-100"
							disabled={occupationValue !== 'Other'}
							id="otherOccupation"
							name="otherOccupation"
							onBlur={(event) => {
								setHasError({
									...hasError,
									otherOccupation:
										event.target.value === ''
											? true
											: false,
								});
							}}
							onChange={(event) => {
								let value = event.target.value;
								value =
									value === 'Choose an option' ? '' : value;
								handleChangeField('otherOccupation', value, id);
								setHasError({
									...hasError,
									otherOccupation:
										value === '' &&
										occupationValue === 'other'
											? true
											: false,
								});
							}}
							required
							type="text"
							value={form[formNumber - 1].otherOccupation}
						/>

						{occupationValue === 'Other' && (
							<label htmlFor="otherOccupation">
								Other Occupation
								<span className="text-danger-darken-1">*</span>
							</label>
						)}

						{occupationValue !== 'Other' && (
							<label htmlFor="otherOccupation">
								Other Occupation
							</label>
						)}

						{hasError.otherOccupation && <ErrorMessage />}
					</div>

					<div
						className={classNames(
							'col-md col-12 filled form-condensed form-group',
							{
								'has-error': hasError.highestEducation,
							}
						)}
					>
						<ClaySelect
							aria-label="Select Label"
							id="highestEducation"
							onChange={(event) => {
								let value = event.target.value;
								value =
									value === 'Choose an option' ? '' : value;
								handleChangeField(
									'highestEducation',
									value,
									id
								);
								setHasError({
									...hasError,
									highestEducation:
										value === '' ? true : false,
								});
							}}
							value={form[formNumber - 1].highestEducation}
						>
							{highestEducationOptions.map(
								(highestEducationOption) => (
									<ClaySelect.Option
										className="font-weight-bold text-brand-primary text-center text-paragraph-sm text-uppercase"
										key={highestEducationOption.value}
										label={highestEducationOption.label}
										value={highestEducationOption.value}
									/>
								)
							)}
						</ClaySelect>

						<label htmlFor="highestEducation">
							Highest Education&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>

						{hasError.highestEducation && <ErrorMessage />}
					</div>
				</div>

				<div className="row">
					<div
						className={classNames(
							'col-md col-12 filled form-condensed form-group',
							{
								'has-error': hasError.governmentAffiliation,
							}
						)}
					>
						<ClaySelect
							aria-label="Select Label"
							disabled={occupationValue !== 'Government'}
							id="governmentAffiliation"
							onChange={(event) => {
								let value = event.target.value;
								value =
									value === 'Choose an option' ? '' : value;
								handleChangeField(
									'governmentAffiliation',
									value,
									id
								);
								setHasError({
									...hasError,
									governmentAffiliation:
										value === '' ? true : false,
								});
							}}
							value={form[formNumber - 1].governmentAffiliation}
						>
							{governmentAffiliationOptions.map(
								(governmentAffiliationOption) => (
									<ClaySelect.Option
										className="font-weight-bold text-brand-primary text-center text-paragraph-sm text-uppercase"
										key={governmentAffiliationOption.value}
										label={
											governmentAffiliationOption.label
										}
										value={
											governmentAffiliationOption.value
										}
									/>
								)
							)}
						</ClaySelect>

						{occupationValue === 'Government' && (
							<label htmlFor="otherOccupation">
								Government Affiliation&nbsp;
								<span className="text-danger-darken-1">*</span>
							</label>
						)}

						{occupationValue !== 'Government' && (
							<label htmlFor="otherOccupation">
								Government Affiliation&nbsp;
							</label>
						)}

						{hasError.governmentAffiliation && <ErrorMessage />}
					</div>

					<div
						className={classNames(
							'col-md col-12 filled form-condensed form-group',
							{
								'has-error': hasError.millitaryAffiliation,
							}
						)}
					>
						<ClayDropDownWithItems
							alignmentByViewport={true}
							alignmentPosition={dropdownAlign.bottomCenter}
							items={_millitaryAffiliationOptions}
							onSearchValueChange={(value: string) => {
								const filterOptions = millitaryAffiliationOptionsAddClick().filter(
									(millitaryAffiliationOption) => {
										if (
											millitaryAffiliationOption.label
												.toLowerCase()
												.includes(value)
										) {
											return millitaryAffiliationOption;
										}
									}
								);

								setMillitaryAffiliationOptions(filterOptions);
								setMillitaryAffiliationSearch(value);
							}}
							searchValue={millitaryAffiliationSearch}
							searchable={true}
							trigger={
								<div className="d-flex select-millitary-affiliation text-neutral-10">
									<ClaySelect
										className="bg-neutral-0 border-neutral-5 cursor-pointer select-style text-paragraph"
										disabled
										required
										value={
											form[formNumber - 1]
												.millitaryAffiliation
										}
									>
										<ClaySelect.Option
											className="cursor-pointer font-weight-bold py-4 text-brand-primary text-center text-paragraph-sm text-uppercase"
											label={
												form[formNumber - 1]
													.millitaryAffiliation
											}
											value={
												form[formNumber - 1]
													.millitaryAffiliation
											}
										/>
									</ClaySelect>
								</div>
							}
						/>

						<label htmlFor="year">
							Millitary Affiliation&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>

						{hasError.millitaryAffiliation && <ErrorMessage />}
					</div>
				</div>

				<div>
					<p className="text-paragraph-sm text-uppercase">
						<u>Accidents &amp; Violations</u>
					</p>

					<div className="mb-1 mt-2 text-neutral-9 text-paragraph-sm">
						Accidents or Citations in the last 10 years?*
					</div>

					<ClayRadioGroup
						defaultValue={form[formIndex].hasAccidentOrCitations}
						inline
					>
						<ClayRadio
							label="Yes"
							onClick={() => {
								handleChangeField(
									'hasAccidentOrCitations',
									'yes',
									id
								);

								if (!form[formIndex].accidentCitation.length) {
									handleAddCitationClick();
								}
							}}
							value="yes"
						/>

						<ClayRadio
							label="No"
							onClick={() => {
								handleChangeField(
									'hasAccidentOrCitations',
									'no',
									id
								);
							}}
							value="no"
						/>
					</ClayRadioGroup>
				</div>

				{form[formIndex]?.hasAccidentOrCitations === 'yes' && (
					<div className="row">
						{accidentCitations[formIndex]?.map(
							(
								currentAccidentCitations: number,
								index: number
							) => (
								<>
									<div
										className="accidentCitation col-10"
										key={index}
									>
										<div
											className={classNames(
												'filled form-condensed form-group',
												{
													'has-error':
														hasError.accidentCitation,
												}
											)}
										>
											<ClayDropDownWithItems
												alignmentByViewport={true}
												alignmentPosition={
													dropdownAlign.bottomCenter
												}
												items={
													newAccidentCitations[
														index
													] ||
													handleAccidentCitation()
												}
												searchable={true}
												trigger={
													<div className="d-flex select-accident-citation text-neutral-10">
														<ClaySelect
															className="bg-neutral-0 border-neutral-5 cursor-pointer select-style text-paragraph"
															disabled
															key={index}
															required
														>
															<ClaySelect.Option
																className="cursor-pointer py-4 text-brand-primary text-center text-paragraph"
																label={
																	form[
																		formIndex
																	]
																		.accidentCitation[
																		index
																	].value
																}
																selected
															></ClaySelect.Option>
														</ClaySelect>
													</div>
												}
											/>

											<label htmlFor="accidentCitation">
												Accident Citation
												<span className="text-danger-darken-1">
													*
												</span>
											</label>

											{hasError.accidentCitation && (
												<ErrorMessage />
											)}
										</div>
									</div>
									<div>
										{index === 0 ? (
											<div className="col-1 form-group p-0">
												<ClayButtonWithIcon
													aria-label="Add"
													className="outline-primary"
													onClick={() =>
														handleAddCitationClick()
													}
													outline
													symbol="plus"
												/>
											</div>
										) : (
											<div className="col-1 form-group p-0">
												<ClayButtonWithIcon
													aria-label="Delete"
													className="bg-neutral-0 border-neutral-0 text-neutral-9"
													onClick={() => {
														handleRemoveAccidentCitationClick(
															index,
															formIndex
														);
													}}
													symbol="times-circle"
												/>
											</div>
										)}
									</div>
								</>
							)
						)}
					</div>
				)}
			</ClayForm>
		</div>
	);
};

export default DriverInfoForm;
