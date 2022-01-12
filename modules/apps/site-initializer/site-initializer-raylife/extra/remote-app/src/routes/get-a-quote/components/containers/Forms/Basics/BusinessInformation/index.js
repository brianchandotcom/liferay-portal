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

import React, {useEffect} from 'react';
import {useFormContext} from 'react-hook-form';
import {ControlledInput} from '../../../../../../../common/components/connectors/Controlled/Input';
import {EmailControlledInput} from '../../../../../../../common/components/connectors/Controlled/Input/Email';
import {WebsiteControlledInput} from '../../../../../../../common/components/connectors/Controlled/Input/Website';
import {PhoneControlledInput} from '../../../../../../../common/components/connectors/Controlled/Input/WithMask/Phone';
import {useCustomEvent} from '../../../../../../../common/hooks/useCustomEvent';
import {TIP_EVENT} from '../../../../../../../common/utils/events';
import useFormActions from '../../../../../hooks/useFormActions';
import {useStepWizard} from '../../../../../hooks/useStepWizard';
import {AVAILABLE_STEPS} from '../../../../../utils/constants';
import FormCard from '../../../../card/FormCard';
import {CardFormActions} from '../../../../form-actions/FormActions';
import {CardFormActionsMobile} from '../../../../form-actions/FormActionsMobile';

import {BusinessInformationAddress} from './Address';

const setFormPath = (value) => `basics.businessInformation.${value}`;

export function FormBasicBusinessInformation({form}) {
	const {selectedStep} = useStepWizard();
	const [dispatchEvent] = useCustomEvent(TIP_EVENT);
	const {onNext, onPrevious, onSave} = useFormActions(
		form,
		AVAILABLE_STEPS.BASICS_BUSINESS_TYPE,
		AVAILABLE_STEPS.BUSINESS,
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
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<FormCard
			footer={
				<CardFormActionsMobile
					onPrevious={onPrevious}
					onSave={onSave}
				/>
			}
		>
			<div className="p-0">
				<div className="d-flex justify-content-between mb-5">
					<ControlledInput
						control={control}
						inputProps={{
							autoFocus: true,
							className: 'flex-grow-1 p-0 mr-4',
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
							className: 'flex-grow-1 p-0 ',
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

			<CardFormActions
				isValid={isValid}
				onNext={onNext}
				onPrevious={onPrevious}
				onSave={onSave}
			/>
		</FormCard>
	);
}
