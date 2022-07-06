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

import ClayDatePicker from '@clayui/date-picker';
import ClayForm, {
	ClayInput,
	ClayRadio,
	ClayRadioGroup,
	ClaySelect,
} from '@clayui/form';
import {useState} from 'react';

const DriverInfo = () => {
	const [date, setDate] = useState<string>('');

	const options = [
		{
			label: '',
			value: '1',
		},
		{
			label: 'CHOOSE AN OPTION',
			value: '2',
		},
		{
			label: 'CA',
			value: '3',
		},
		{
			label: 'NV',
			value: '4',
		},
		{
			label: 'NY',
			value: '5',
		},
	];

	return (
		<div className="mx-8">
			<div className="font-weight-bolder text-paragraph-sm">
				CONTACT INFORMATION
			</div>

			<hr></hr>

			<ClayForm>
				<div className="d-flex flex-row">
					<div className="col filled form-condensed form-group">
						<ClayInput
							className="bg-neutral-0"
							id="firstName"
							name="firstName"
							required
							type="text"
						/>

						<label htmlFor="firstName">First Name*</label>
					</div>

					<div className="col filled form-condensed form-group">
						<ClayInput
							className="bg-neutral-0"
							id="lastName"
							name="lastName"
							required
							type="text"
						/>

						<label htmlFor="lastName">Last Name*</label>
					</div>
				</div>

				<div className="d-flex flex-row">
					<div className="col filled form-condensed form-group">
						<ClayDatePicker
							dateFormat="MM-dd-yyyy"
							onChange={setDate}
							value={date}
							years={{
								end: 2026,
								start: 2017,
							}}
						/>

						<label htmlFor="dateOfBirth">Date of Birth*</label>
					</div>

					<div className="col filled form-condensed form-group">
						<ClayInput
							className="bg-neutral-0"
							id="phone"
							name="phone"
							required
							type="text"
						/>

						<label htmlFor="phone">Phone*</label>
					</div>

					<div className="col filled form-condensed form-group">
						<ClayInput
							className="bg-neutral-0"
							id="email"
							name="email"
							required
							type="text"
						/>

						<label htmlFor="email">Email*</label>
					</div>
				</div>
			</ClayForm>

			<div className="font-weight-bolder text-paragraph-sm">
				ADDRESS INFORMATION
			</div>

			<hr></hr>

			<ClayForm>
				<div className="d-flex flex-row">
					<div className="col filled form-condensed form-group">
						<ClayInput
							className="bg-neutral-0"
							id="streetAddress"
							name="streetAddress"
							required
							type="text"
						/>

						<label htmlFor="streetAddress">Street Address*</label>
					</div>

					<div className="col filled form-condensed form-group">
						<ClayInput
							className="bg-neutral-0"
							id="apt"
							name="apt"
							required
							type="text"
						/>

						<label htmlFor="apt">Apt</label>
					</div>
				</div>

				<div className="d-flex flex-row">
					<div className="col filled form-condensed form-group">
						<ClayInput
							className="bg-neutral-0"
							id="city"
							name="city"
							required
							type="text"
						/>

						<label htmlFor="city">City*</label>
					</div>

					<div className="col filled form-condensed form-group">
						<ClaySelect aria-label="Select Label" id="mySelectId">
							{options.map((item) => (
								<ClaySelect.Option
									key={item.value}
									label={item.label}
									value={item.value}
								/>
							))}
						</ClaySelect>

						<label htmlFor="state">State*</label>
					</div>

					<div className="col filled form-condensed form-group">
						<ClayInput
							className="bg-neutral-0"
							id="zipCode"
							name="zipCode"
							required
							type="text"
						/>

						<label htmlFor="zipCode">Zip Code*</label>
					</div>
				</div>
			</ClayForm>

			<div>
				<div className="mb-1 ml-3 mt-2 text-neutral-9 text-paragraph-sm">
					Ownership*
				</div>

				<ClayRadioGroup className="ml-3" inline>
					<ClayRadio label="Rent" value="one" />

					<ClayRadio label="Own" value="two" />
				</ClayRadioGroup>
			</div>
		</div>
	);
};

export default DriverInfo;
