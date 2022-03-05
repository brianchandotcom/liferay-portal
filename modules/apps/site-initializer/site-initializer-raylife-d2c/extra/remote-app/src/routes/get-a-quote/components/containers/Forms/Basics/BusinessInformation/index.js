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

import classNames from 'classnames';
import React, {useEffect} from 'react';
import {useFormContext} from 'react-hook-form';
import {ControlledInput} from '../../../../../../../common/components/connectors/Controlled/Input';
import {EmailControlledInput} from '../../../../../../../common/components/connectors/Controlled/Input/Email';
import {WebsiteControlledInput} from '../../../../../../../common/components/connectors/Controlled/Input/Website';
import {PhoneControlledInput} from '../../../../../../../common/components/connectors/Controlled/Input/WithMask/Phone';
import {useCustomEvent} from '../../../../../../../common/hooks/useCustomEvent';
import {TIP_EVENT} from '../../../../../../../common/utils/events';
import useMobileContainer from '../../../../../hooks/useMobileContainer';
import {SUBSECTION_KEYS} from '../../../../../utils/constants';
import MobileContainer from '../../../../mobile/MobileContainer';

import {BusinessInformationAddress} from './Address';

const setFormPath = (value) => `basics.businessInformation.${value}`;

export function FormBasicBusinessInformation({form, isMobile}) {
	const [dispatchEvent] = useCustomEvent(TIP_EVENT);
	const {getMobileSubSection, mobileContainerProps} = useMobileContainer();

	const {control} = useFormContext();

	const onFirstNameSettled = () => {
		dispatchEvent({
			inputName: setFormPath('firstName'),
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
			templateData: {
				firstName: '! 👋',
			},
			templateName: 'hi-template',
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<div className="p-0">
			<MobileContainer
				{...mobileContainerProps}
				mobileSubSection={getMobileSubSection(
					SUBSECTION_KEYS.YOUR_NAME
				)}
			>
				<div
					className={classNames(
						'd-flex justify-content-between mb-5',
						{
							['flex-wrap']: isMobile,
						}
					)}
				>
					<ControlledInput
						control={control}
						inputProps={{
							autoFocus: true,
							className: classNames('flex-grow-1 p-0 mr-4', {
								'col-12 mb-4': isMobile,
							}),
							maxLength: 256,
							onBlur: onFirstNameSettled,
						}}
						label={SUBSECTION_KEYS.FIRST_NAME}
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
						label={SUBSECTION_KEYS.LAST_NAME}
						name={setFormPath('lastName')}
						rules={{
							required: 'Last name is required.',
						}}
					/>
				</div>
			</MobileContainer>

			<MobileContainer
				{...mobileContainerProps}
				mobileSubSection={getMobileSubSection(
					SUBSECTION_KEYS.BUSINESS_EMAIL
				)}
			>
				<EmailControlledInput
					control={control}
					label={SUBSECTION_KEYS.BUSINESS_EMAIL}
					name={setFormPath('business.email')}
					rules={{
						required: 'Email is required.',
					}}
				/>
			</MobileContainer>

			<MobileContainer
				{...mobileContainerProps}
				mobileSubSection={getMobileSubSection(SUBSECTION_KEYS.PHONE)}
			>
				<PhoneControlledInput
					control={control}
					label={SUBSECTION_KEYS.PHONE}
					name={setFormPath('business.phone')}
					rules={{
						required: 'Phone number is required.',
					}}
				/>
			</MobileContainer>

			<MobileContainer
				{...mobileContainerProps}
				mobileSubSection={getMobileSubSection(
					SUBSECTION_KEYS.BUSINESS_WEBSITE
				)}
			>
				<WebsiteControlledInput
					control={control}
					label={SUBSECTION_KEYS.BUSINESS_WEBSITE}
					name={setFormPath('business.website')}
				/>
			</MobileContainer>

			<MobileContainer
				{...mobileContainerProps}
				mobileSubSection={getMobileSubSection(
					SUBSECTION_KEYS.PHYSICAL_ADDRESS
				)}
			>
				<BusinessInformationAddress />
			</MobileContainer>
		</div>
	);
}
