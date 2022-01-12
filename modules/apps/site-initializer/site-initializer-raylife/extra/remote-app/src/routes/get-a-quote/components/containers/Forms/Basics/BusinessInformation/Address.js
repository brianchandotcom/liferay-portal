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
			<div className="d-flex justify-content-between">
				<ControlledInput
					control={control}
					inputProps={{
						className: 'flex-grow-1 mr-4 p-0',
						placeholder: 'Street address',
						ref,
					}}
					label="Physical Business Address"
					name={setFormPath('address')}
					rules={{required: 'Business address is required.'}}
				/>

				<Input
					{...register(setFormPath('addressApt'))}
					className="apt p-0"
					label="&nbsp;"
					placeholder="Apt/Suite (optional)"
				/>
			</div>

			<div className="d-flex flex-row justify-content-between my-5">
				<div className="d-flex flex-grow-1">
					<ControlledInput
						control={control}
						inputProps={{
							className: ' flex-grow-1 p-0',
						}}
						label="City"
						name={setFormPath('city')}
						rules={{required: 'City is required.'}}
					/>

					<StatesControlledSelect
						control={control}
						inputProps={{
							className: 'flex-grow-1 p-0 mx-4',
						}}
						label="State"
						name={setFormPath('state')}
						rules={{
							required: 'This field is required.',
						}}
					/>
				</div>

				<div className="d-flex">
					<ZIPControlledInput
						control={control}
						label="ZIP"
						name={setFormPath('zip')}
						rules={{
							required: 'ZIP is required.',
						}}
					/>
				</div>
			</div>
		</div>
	);
}
