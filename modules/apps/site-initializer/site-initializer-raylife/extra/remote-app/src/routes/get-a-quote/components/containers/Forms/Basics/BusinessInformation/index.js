/* eslint-disable react-hooks/exhaustive-deps */
import React, {useEffect} from 'react';
import {useFormContext} from 'react-hook-form';
import {ControlledInput} from '~/common/components/connectors/Controlled/Input';
import {EmailControlledInput} from '~/common/components/connectors/Controlled/Input/Email';
import {WebsiteControlledInput} from '~/common/components/connectors/Controlled/Input/Website';
import {PhoneControlledInput} from '~/common/components/connectors/Controlled/Input/WithMask/Phone';
import {CardFormActionsWithSave} from '~/common/components/fragments/Card/FormActionsWithSave';
import {useCustomEvent} from '~/common/hooks/useCustomEvent';
import {STORAGE_KEYS, Storage} from '~/common/services/liferay/storage';
import {TIP_EVENT} from '~/common/utils/events';
import useFormActions from '~/routes/get-a-quote/hooks/useFormActions';
import {useProductQuotes} from '~/routes/get-a-quote/hooks/useProductQuotes';
import {useStepWizard} from '~/routes/get-a-quote/hooks/useStepWizard';
import {AVAILABLE_STEPS} from '~/routes/get-a-quote/utils/constants';

import {BusinessInformationAddress} from './Address';

const setFormPath = (value) => `basics.businessInformation.${value}`;

const getSelectedProductName = () => {
	try {
		return JSON.parse(Storage.getItem(STORAGE_KEYS.PRODUCT))?.productName;
	}
	catch (error) {
		return '';
	}
};

export function FormBasicBusinessInformation({form}) {
	const {selectedStep} = useStepWizard();
	const {productQuotes} = useProductQuotes();
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
		setValue,
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

	const defaultProductId = productQuotes.find(
		({title}) => title === getSelectedProductName()
	)?.id;

	useEffect(() => {
		if (defaultProductId && !form.basics.productQuote) {
			setValue('basics.productQuote', defaultProductId);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [defaultProductId, form.basics.productQuote]);

	return (
		<div className="card">
			<div className="card-content">
				<div className="content-row">
					<ControlledInput
						control={control}
						inputProps={{
							autoFocus: true,
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
}
