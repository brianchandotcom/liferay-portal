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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayForm, {
	ClayInput,
	ClayRadio,
	ClayRadioGroup,
	ClaySelect,
} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useContext, useState} from 'react';

import {
	ACTIONS,
	NewApplicationAutoContext,
} from '../../../context/NewApplicationAutoContextProvider';

type FormDriverInfoTypes = {
	accidentCitationForm: any[];
	form: any[];
	formIndex: number;
	formNumber: number;
	id: number;
};

const DriverInfoForm = ({
	accidentCitationForm,
	form,
	formIndex,
	formNumber,
	id,
}: FormDriverInfoTypes) => {
	const [_state, dispatch] = useContext(NewApplicationAutoContext);

	const [occupationValue, setOccupationValue] = useState<string>();

	const [accidentCitationValue, setAccidentCitationValue] = useState<boolean>(
		false
	);

	const accidentCitations = form.map((form) => {
		return form.accidentCitation.filter(
			(accidentCitation: {id: number}) => accidentCitation.id !== id
		);
	});

	const handleAddCitationClick = () => {
		const driverInfoObject = {
			accidentCitation: [
				{id: Number((Math.random() * 1000000).toFixed(0)), value: ''},
			],
			ageFirstLicenced: '',
			firstName: '',
			gender: '',
			governmentAffiliation: '',
			hasAccidentOrCitations: false,
			highestEducation: '',
			id,
			lastName: '',
			maritalStatus: '',
			militaryAffiliation: '',
			ocupation: '',
			otherOcupation: '',
			relationToContact: '',
		};

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
		accidentCitationId: number,
		formId: number
	) => {
		const payload = {accidentCitationId, formId};
		dispatch({payload, type: ACTIONS.SET_REMOVE_ACCIDENT_CITATION});
	};

	const relationToContactOptions = [
		{
			label: '',
			value: '',
		},
		{
			label: 'Choose an option',
			value: 'chooseAnOption',
		},
		{
			label: 'Self',
			value: 'self',
		},
		{
			label: 'Spouse',
			value: 'spouse',
		},
		{
			label: 'Dependent',
			value: 'dependent',
		},
		{
			label: 'Relative',
			value: 'relative',
		},
	];

	const genderOptions = [
		{
			label: '',
			value: '',
		},
		{
			label: 'Choose an option',
			value: 'chooseAnOption',
		},
		{
			label: 'Male',
			value: 'male',
		},
		{
			label: 'Female',
			value: 'female',
		},
	];

	const maritalStatusOptions = [
		{
			label: '',
			value: '',
		},
		{
			label: 'Choose an option',
			value: 'chooseAnOption',
		},
		{
			label: 'Married',
			value: 'married',
		},
		{
			label: 'Separated',
			value: 'separated',
		},
		{
			label: 'Single',
			value: 'single',
		},
		{
			label: 'Widowed',
			value: 'widowed',
		},
	];

	const occupationOptions = [
		{
			label: '',
			value: '',
		},
		{
			label: 'Choose an option',
			value: 'chooseAnOption',
		},
		{
			label: 'Accountant',
			value: 'accountant',
		},
		{
			label: 'Dentist',
			value: 'dentist',
		},
		{
			label: 'Engineer',
			value: 'engineer',
		},
		{
			label: 'Government',
			value: 'government',
		},
		{
			label: 'Other',
			value: 'other',
		},
	];

	const millitaryAffiliationOptions = [
		{
			label: '',
			value: '',
		},
		{
			label: 'Choose an option',
			value: 'chooseAnOption',
		},
		{
			label: 'None',
			value: 'none',
		},
		{
			label: 'US Air Force',
			value: 'usAirForce',
		},
		{
			label: 'US Coast Guard',
			value: 'usCoastGuard',
		},
		{
			label: 'US Marine Corps',
			value: 'usMarineCorps',
		},
		{
			label: 'US Military',
			value: 'usMilitary',
		},
		{
			label: 'US Navy',
			value: 'usNavy',
		},
	];

	const highestEducationOptions = [
		{
			label: '',
			value: '',
		},
		{
			label: 'Choose an option',
			value: 'chooseAnOption',
		},
		{
			label: 'High School',
			value: 'highSchool',
		},
		{
			label: 'College',
			value: 'college',
		},
		{
			label: 'Masters',
			value: 'masters',
		},
		{
			label: 'PHD',
			value: 'phd',
		},
	];

	const governmentAffiliationOptions = [
		{
			label: '',
			value: '',
		},
		{
			label: 'Choose an option',
			value: 'chooseAnOption',
		},
		{
			label: 'ABC Sheriff Association',
			value: 'abcSheriffAssociation',
		},
		{
			label: 'FBI Academy Associates',
			value: 'fbiAcademyAssociates',
		},
		{
			label: 'State Firefighters Association',
			value: 'stateFirefightersAssociation',
		},
	];

	const accidentCitation = [
		{
			label: '',
			value: '',
		},
		{
			label: 'Choose an option',
			value: 'chooseAnOption',
		},
		{
			label: 'Accident - Not at fault',
			value: 'accidentNotAtFault',
		},
		{
			label: 'Accident - At fault',
			value: 'accidentAtFault',
		},
		{
			label: 'Accident - Car rolled over',
			value: 'accidentCarRolledOver',
		},
		{
			label: 'Citation - Driving under the influence',
			value: 'citationDrivingUnderTheInfluence',
		},
		{
			label: 'Citation - Failing to Yield',
			value: 'citationFailingToYield',
		},
		{
			label: 'US Citation - Speeding',
			value: 'citationSpeeding',
		},
	];

	return (
		<div className="bg-neutral-0 mx-8">
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
					<div className="col filled form-condensed form-group">
						<ClayInput
							autoComplete="off"
							className="bg-neutral-0 firstName w-100"
							id="firstName"
							name="firstName"
							onChange={(event) => {
								handleChangeField(
									'firstName',
									event.target.value,
									id
								);
							}}
							required
							type="text"
						/>

						<label htmlFor="firstName">
							First Name&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>
					</div>

					<div className="col filled form-condensed form-group">
						<ClayInput
							autoComplete="off"
							className="bg-neutral-0 lastName w-100"
							id="lastName"
							name="lastName"
							onChange={(event) => {
								handleChangeField(
									'lastName',
									event.target.value,
									id
								);
							}}
							required
							type="text"
						/>

						<label htmlFor="lastName">
							Last Name&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>
					</div>

					<div className="col filled form-condensed form-group">
						<ClaySelect
							aria-label="Select Label"
							id="relationToContact"
							onChange={(event) => {
								handleChangeField(
									'relationToContact',
									event.target.value,
									id
								);
							}}
						>
							{relationToContactOptions.map(
								(relationToContactOption) => (
									<ClaySelect.Option
										className="text-uppercase"
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
					</div>
				</div>

				<div className="row">
					<div className="col filled form-condensed form-group">
						<ClaySelect
							aria-label="Select Label"
							id="gender"
							onChange={(event) => {
								handleChangeField(
									'gender',
									event.target.value,
									id
								);
							}}
						>
							{genderOptions.map((genderOption) => (
								<ClaySelect.Option
									className="text-uppercase"
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
					</div>

					<div className="col filled form-condensed form-group">
						<ClaySelect
							aria-label="Select Label"
							id="maritalStatusOptions"
							onChange={(event) => {
								handleChangeField(
									'maritalStatusOptions',
									event.target.value,
									id
								);
							}}
						>
							{maritalStatusOptions.map((maritalStatusOption) => (
								<ClaySelect.Option
									className="text-uppercase"
									key={maritalStatusOption.value}
									label={maritalStatusOption.label}
									value={maritalStatusOption.value}
								/>
							))}
						</ClaySelect>

						<label htmlFor="maritalStatusOptions">
							Marital Status&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>
					</div>

					<div className="col filled form-condensed form-group">
						<ClayInput
							autoComplete="off"
							className="ageFirstLicenced bg-neutral-0 w-100"
							id="ageFirstLicenced"
							name="ageFirstLicenced"
							onChange={(event) => {
								handleChangeField(
									'ageFirstLicenced',
									event.target.value,
									id
								);
							}}
							required
							type="text"
						/>

						<label htmlFor="ageFirstLicenced">
							Age First Licenced&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>
					</div>
				</div>

				<div className="row">
					<div className="col filled form-condensed form-group">
						<ClaySelect
							aria-label="Select Label"
							id="occupation"
							onChange={(occupation) => {
								setOccupationValue(occupation.target.value);

								handleChangeField(
									'occupation',
									occupation.target.value,
									id
								);
							}}
						>
							{occupationOptions.map((occupationOption) => (
								<ClaySelect.Option
									className="text-uppercase"
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
					</div>

					<div className="col filled form-condensed form-group">
						<ClayInput
							autoComplete="off"
							className="bg-neutral-0 otherOcupation w-100"
							disabled={occupationValue !== 'other'}
							id="otherOcupation"
							name="otherOcupation"
							onChange={(event) => {
								handleChangeField(
									'otherOcupation',
									event.target.value,
									id
								);
							}}
							required
							type="text"
						/>

						{occupationValue === 'other' && (
							<label htmlFor="otherOccupation">
								Other Occupation
								<span className="text-danger-darken-1">*</span>
							</label>
						)}

						{occupationValue !== 'other' && (
							<label htmlFor="otherOccupation">
								Other Occupation
							</label>
						)}
					</div>

					<div className="col filled form-condensed form-group">
						<ClaySelect
							aria-label="Select Label"
							id="highestEducation"
							onChange={(event) => {
								handleChangeField(
									'highestEducation',
									event.target.value,
									id
								);
							}}
						>
							{highestEducationOptions.map(
								(highestEducationOption) => (
									<ClaySelect.Option
										className="text-uppercase"
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
					</div>
				</div>

				<div className="mb-3 row">
					<div className="col filled form-condensed form-group">
						<ClaySelect
							aria-label="Select Label"
							disabled={occupationValue !== 'government'}
							id="governmentAffiliation"
							onChange={(event) => {
								handleChangeField(
									'governmentAffiliation',
									event.target.value,
									id
								);
							}}
						>
							{governmentAffiliationOptions.map(
								(governmentAffiliationOption) => (
									<ClaySelect.Option
										className="text-uppercase"
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

						{occupationValue === 'government' && (
							<label htmlFor="otherOcupation">
								Government Affiliation&nbsp;
								<span className="text-danger-darken-1">*</span>
							</label>
						)}

						{occupationValue !== 'government' && (
							<label htmlFor="otherOcupation">
								Government Affiliation&nbsp;
							</label>
						)}
					</div>

					<div className="col filled form-condensed form-group millitaryAffiliation">
						<ClayDropDownWithItems
							items={millitaryAffiliationOptions}
							searchable
							trigger={
								<ClayButton className="after bg-neutral-0 border-neutral-5 button-select d-flex icon justify-content-end rounded text-neutral-10">
									<span>
										<ClayIcon
											className="button-select-icon"
											symbol="caret-double-l"
										/>
									</span>
								</ClayButton>
							}
						/>

						<label htmlFor="millitaryAffiliation">
							Millitary Affiliation&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>
					</div>
				</div>

				<div>
					<p className="text-paragraph-sm text-uppercase">
						<u>Accidents &amp; Violations</u>
					</p>

					<div className="mb-1 mt-2 text-neutral-9 text-paragraph-sm">
						Accidents or Citations in the last 10 years?*
					</div>

					<ClayRadioGroup inline>
						<ClayRadio
							label="Yes"
							onClick={() => setAccidentCitationValue(true)}
							value="yes"
						/>

						<ClayRadio
							label="No"
							onClick={() => setAccidentCitationValue(false)}
							value="no"
						/>
					</ClayRadioGroup>
				</div>

				{accidentCitationValue && (
					<div className="row">
						{accidentCitations[formIndex].map(
							(
								currentAccidentCitations: number,
								index: number
							) => (
								<>
									<div className="accidentCitation col-10 filled form-condensed form-group">
										<ClayDropDownWithItems
											items={accidentCitation}
											key={index}
											searchable
											trigger={
												<ClayButton className="after bg-neutral-0 border-neutral-5 button-select d-flex icon justify-content-end mb-3 rounded text-neutral-10">
													<span>
														<ClayIcon
															className="button-select-icon"
															symbol="caret-double-l"
														/>
													</span>
												</ClayButton>
											}
										/>

										<label htmlFor="accidentCitation">
											Accident Citation
											<span className="text-danger-darken-1">
												*
											</span>
										</label>
									</div>
									{index === 0 ? (
										<div className="col-1 form-group p-0">
											<ClayButtonWithIcon
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
												className="bg-neutral-0 border-neutral-0 text-neutral-9"
												onClick={() => {
													handleRemoveAccidentCitationClick(
														accidentCitationForm[0]
															.id,
														form[formIndex].id
													);
												}}
												symbol="times-circle"
											/>
										</div>
									)}
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
