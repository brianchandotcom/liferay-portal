/* eslint-disable react-hooks/exhaustive-deps */
import React, {useEffect} from 'react';
import {useFormContext} from 'react-hook-form';

import {TIP_EVENT} from '../../../../../events';
import {useCustomEvent} from '../../../../../hooks/useCustomEvent';
import useFormActions from '../../../../../hooks/useFormActions';
import {useStepWizard} from '../../../../../hooks/useStepWizard';
import {AVAILABLE_STEPS} from '../../../../../utils/constants';
import {ControlledInput} from '../../../../connectors/Controlled/Input';
import {EmailControlledInput} from '../../../../connectors/Controlled/Input/Email';
import {WebsiteControlledInput} from '../../../../connectors/Controlled/Input/Website';
import {PhoneControlledInput} from '../../../../connectors/Controlled/Input/WithMask/Phone';
import {CardFormActionsWithSave} from '../../../../fragments/Card/FormActionsWithSave';
import {BusinessInformationAddress} from './Address';

const setFormPath = (value) => `basics.businessInformation.${value}`;

export const FormBasicBusinessInformation = ({form}) => {
	const {selectedStep} = useStepWizard();
	const [dispatchEvent] = useCustomEvent(TIP_EVENT);
	const {onNext, onPrevious, onSave} = useFormActions(
		form,
		AVAILABLE_STEPS.BASICS_BUSINESS_TYPE,
		AVAILABLE_STEPS.BASICS_PRODUCT_QUOTE,
		'Unable to save your information. Please try again.'
	);

	const {
		control,
		formState: {isValid},
	} = useFormContext();

	const onFirstNameSettled = () => {
		dispatchEvent({
			inputName: setFormPath('firstName'),
			step: selectedStep,
			templateData: {
				firstName: ` ${
					form?.basics?.businessInformation?.firstName?.trim() || ''
				}! 👋`,
			},
			templateName: 'hi-template',
			value: form?.basics?.businessInformation?.firstName,
		});
	};

	useEffect(() => {
		if (form?.basics?.businessInformation?.firstName) {
			return onFirstNameSettled();
		}

		dispatchEvent({
			step: selectedStep,
			templateData: {
				firstName: '! 👋',
			},
			templateName: 'hi-template',
		});
	}, []);

	return (
		<div className="card">
			<div className="card-content">
				<div className="content-row">
					<ControlledInput
						control={control}
						inputProps={{
							maxLength: 256,
							onBlur: onFirstNameSettled,
						}}
						label="First Name"
						name={setFormPath('firstName')}
						rules={{
							required: 'First name is required.',
						}}
					/>
					<ControlledInput
						control={control}
						inputProps={{
							maxLength: 256,
						}}
						label="Last Name"
						name={setFormPath('lastName')}
						rules={{
							required: 'Last name is required.',
						}}
					/>
				</div>
				<EmailControlledInput
					control={control}
					label="Business Email"
					name={setFormPath('business.email')}
					rules={{
						required: 'Email is required.',
					}}
				/>
				<PhoneControlledInput
					control={control}
					label="Phone"
					name={setFormPath('business.phone')}
					rules={{
						required: 'Phone number is required.',
					}}
				/>
				<WebsiteControlledInput
					control={control}
					label="Business Website (optional)"
					name={setFormPath('business.website')}
				/>
				<BusinessInformationAddress />
			</div>
			<CardFormActionsWithSave
				isValid={isValid}
				onNext={onNext}
				onPrevious={onPrevious}
				onSave={onSave}
			/>
		</div>
	);
};
