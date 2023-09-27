/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect} from 'react';
import {useFormContext} from 'react-hook-form';
import {NumberControlledInput} from '../../../../../common/components/connectors/Controlled/Input/Number';
import {CurrencyControlledInput} from '../../../../../common/components/connectors/Controlled/Input/WithMask/Currency';
import {FEINControlledInput} from '../../../../../common/components/connectors/Controlled/Input/WithMask/FEIN';
import {YearControlledInput} from '../../../../../common/components/connectors/Controlled/Input/WithMask/Year';
import {ControlledSwitch} from '../../../../../common/components/connectors/Controlled/Switch';
import {TIP_EVENT} from '../../../../../common/utils/events';
import useMobileContainer from '../../../hooks/useMobileContainer';
import {useTriggerContext} from '../../../hooks/useTriggerContext';
import {AVAILABLE_STEPS, SUBSECTION_KEYS} from '../../../utils/constants';
import MobileContainer from '../../mobile/MobileContainer';

const setFormPath = (value) => `employees.${value}`;

const hasFein = (value) => value === 'true';

export function FormEmployees({form}) {
	const {control, getValues, setValue} = useFormContext();

	const {
		getMobileSubSection,
		mobileContainerProps,
		nextStep,
	} = useMobileContainer();

	const forceValidation = () => {
		setValue(
			setFormPath('businessOperatesYearRound'),
			getValues(setFormPath('businessOperatesYearRound')),
			{shouldValidate: true}
		);
	};

	useEffect(() => {
		forceValidation();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const {isSelected, updateState} = useTriggerContext();

	return (
		<div className="card-content">
			<MobileContainer
				{...mobileContainerProps}
				mobileSubSection={getMobileSubSection(
					SUBSECTION_KEYS.BUSINESS_FEDERAL_EMPLOYER_IDENTIFICATION_NUMBER
				)}
			>
				<div className="mb-4">
					<ControlledSwitch
						control={control}
						inputProps={{
							onChange: (value) => {
								AVAILABLE_STEPS.EMPLOYEES.mobileSubSections.map(
									(mobileSubSection) => {
										if (
											mobileSubSection.title ===
											SUBSECTION_KEYS.BUSINESS_FEDERAL_EMPLOYER_IDENTIFICATION_NUMBER
										) {
											mobileSubSection.hideContinueButton = false;
										}
									}
								);

								setValue(setFormPath('hasFein'), value, {
									shouldValidate: true,
								});

								if (value === 'false') {
									setValue(setFormPath('fein'), '');
								}
							},
						}}
						label={
							mobileContainerProps.isMobile
								? ''
								: SUBSECTION_KEYS.BUSINESS_FEDERAL_EMPLOYER_IDENTIFICATION_NUMBER
						}
						name={setFormPath('hasFein')}
						rules={{required: true}}
					/>
				</div>

				<FEINControlledInput
					control={control}
					label={
						SUBSECTION_KEYS.FEDERAL_EMPLOYER_IDENTIFICATION_NUMBER
					}
					moreInfoProps={{
						callback: () => updateState(setFormPath('fein')),
						event: TIP_EVENT,
						selected: isSelected(setFormPath('fein')),
						value: {
							inputName: setFormPath('fein'),
							templateName:
								'federal-employer-identification-number',
							value: form?.employees?.fein,
						},
					}}
					name={setFormPath('fein')}
					renderInput={hasFein(form?.employees?.hasFein)}
					rules={{
						required: 'FEIN is required.',
					}}
				/>
			</MobileContainer>

			<MobileContainer
				{...mobileContainerProps}
				mobileSubSection={getMobileSubSection(
					SUBSECTION_KEYS.YEAR_BUSINESS_STARTED
				)}
			>
				<YearControlledInput
					control={control}
					label={SUBSECTION_KEYS.YEAR_BUSINESS_STARTED}
					name={setFormPath('startBusinessAtYear')}
					rules={{required: 'This field is required'}}
				/>
			</MobileContainer>

			<MobileContainer
				{...mobileContainerProps}
				mobileSubSection={getMobileSubSection(
					SUBSECTION_KEYS.BUSINESS_YEAR_OPERATION
				)}
			>
				<ControlledSwitch
					control={control}
					label={SUBSECTION_KEYS.BUSINESS_YEAR_OPERATION}
					name={setFormPath('businessOperatesYearRound')}
					onSelect={nextStep}
					rules={{required: true}}
				/>
			</MobileContainer>

			<MobileContainer
				{...mobileContainerProps}
				mobileSubSection={getMobileSubSection(
					SUBSECTION_KEYS.EMPLOYEES_AMOUNT
				)}
			>
				<NumberControlledInput
					control={control}
					label={SUBSECTION_KEYS.EMPLOYEES_AMOUNT}
					moreInfoProps={{
						callback: () =>
							updateState(setFormPath('partTimeEmployees')),
						event: TIP_EVENT,
						selected: isSelected(setFormPath('partTimeEmployees')),
						value: {
							inputName: setFormPath('partTimeEmployees'),
							templateName: 'full-or-part-time-employees',
							value: form?.employees?.partTimeEmployees,
						},
					}}
					name={setFormPath('partTimeEmployees')}
					rules={{
						min: {
							message: 'You must have at least one employee.',
							value: 1,
						},
						required: 'This field is required',
					}}
				/>
			</MobileContainer>

			<MobileContainer
				{...mobileContainerProps}
				mobileSubSection={getMobileSubSection(
					SUBSECTION_KEYS.BUSINESS_ANUAL_GROSS_REVENUE
				)}
			>
				<CurrencyControlledInput
					control={control}
					label={SUBSECTION_KEYS.BUSINESS_ANUAL_GROSS_REVENUE}
					name={setFormPath('estimatedAnnualGrossRevenue')}
					rules={{required: 'This field is required'}}
				/>
			</MobileContainer>

			<MobileContainer
				{...mobileContainerProps}
				mobileSubSection={getMobileSubSection(
					SUBSECTION_KEYS.OWNERS_ANNUAL_PAYROLL
				)}
			>
				<CurrencyControlledInput
					control={control}
					label={SUBSECTION_KEYS.OWNERS_ANNUAL_PAYROLL}
					name={setFormPath('annualPayrollForOwner')}
					rules={{required: 'This field is required'}}
				/>
			</MobileContainer>

			<MobileContainer
				{...mobileContainerProps}
				mobileSubSection={getMobileSubSection(
					SUBSECTION_KEYS.EMPLOYEES_ANNUAL_PAYROLL
				)}
			>
				<CurrencyControlledInput
					control={control}
					label={SUBSECTION_KEYS.EMPLOYEES_ANNUAL_PAYROLL}
					name={setFormPath('annualPayrollForEmployees')}
					rules={{required: 'This field is required'}}
				/>
			</MobileContainer>
		</div>
	);
}
