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

import React, {useEffect, useRef} from 'react';
import {useFormContext} from 'react-hook-form';
import {ControlledInput} from '../../../../../../../common/components/connectors/Controlled/Input';
import {ZIPControlledInput} from '../../../../../../../common/components/connectors/Controlled/Input/WithMask/ZIP';
import {StatesControlledSelect} from '../../../../../../../common/components/connectors/Controlled/Select/States';
import {Input} from '../../../../../../../common/components/fragments/Forms/Input';
import {useLocation} from '../../../../../hooks/useLocation';
import {SUBSECTION_KEYS} from '../../../../../utils/constants';

const setFormPath = (value) =>
	`basics.businessInformation.business.location.${value}`;

export function BusinessInformationAddress() {
	const ref = useRef();
	const {setAutoComplete} = useLocation();
	const {control, register, setValue} = useFormContext();

	useEffect(() => {
		if (ref.current) {
			setAutoComplete(ref.current, updateFormWithGoogleAddress);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [ref]);

	const updateFormWithGoogleAddress = (address) => {

		// We need to put shouldValidate at least in one Field
		// to force validation to others

		setValue(setFormPath('city'), address.city, {shouldValidate: true});
		setValue(setFormPath('state'), address.state);
		setValue(setFormPath('zip'), address.zip);
		setValue(
			setFormPath('address'),
			`${address.streetNumber} ${address.street}`
		);
	};

	return (
		<div className="d-flex flex-column">
			<div className="d-flex flex-wrap justify-content-between">
				<ControlledInput
					control={control}
					inputProps={{
						className:
							'col-sm-12 col-md-8 col-lg-8 pr-lg-4 pr-md-4 p-0',
						placeholder: 'Street address',
						ref,
					}}
					label={SUBSECTION_KEYS.PHYSICAL_BUSINESS_ADDRESS}
					name={setFormPath('address')}
					rules={{required: 'Business address is required.'}}
				/>

				<Input
					{...register(setFormPath('addressApt'))}
					className="col-lg-4 col-md-4 col-sm-12 p-0"
					label="&nbsp;"
					placeholder="Apt/Suite (optional)"
				/>
			</div>

			<div className="d-flex flex-row flex-wrap justify-content-between my-3 my-lg-5 my-md-5 my-sm-3">
				<ControlledInput
					control={control}
					inputProps={{
						className:
							'col-sm-8 col-md-5 col-lg-5 pr-sm-4 pr-xs-0 p-0',
					}}
					label={SUBSECTION_KEYS.CITY}
					name={setFormPath('city')}
					rules={{required: 'City is required.'}}
				/>

				<StatesControlledSelect
					control={control}
					inputProps={{
						className:
							'col-sm-4 col-md-3 col-lg-3 pr-md-4 pr-lg-4 p-0 mt-3 mt-sm-0 mt-md-0 mt-lg-0',
					}}
					label={SUBSECTION_KEYS.STATE}
					name={setFormPath('state')}
					rules={{
						required: 'This field is required.',
					}}
				/>

				<ZIPControlledInput
					control={control}
					inputProps={{
						className:
							'col-sm-12 col-md-4 col-lg-4 mt-md-0 mt-sm-3 pt-xs-3 p-0 mt-3 mt-md-0 mt-lg-0',
					}}
					label={SUBSECTION_KEYS.ZIP}
					name={setFormPath('zip')}
					rules={{
						required: 'ZIP is required.',
					}}
				/>
			</div>
		</div>
	);
}
