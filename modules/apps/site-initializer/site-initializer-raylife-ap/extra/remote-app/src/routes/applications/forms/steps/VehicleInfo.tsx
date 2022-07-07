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

import ClayForm, {ClayInput} from '@clayui/form';
import React, {useContext} from 'react';

import {
	ACTIONS,
	NewApplicationAutoContext,
} from '../../context/NewApplicationAutoContextProvider';

const VehicleInfo = () => {
	const [state, dispatch] = useContext(NewApplicationAutoContext);

	const {form} = state?.steps?.vehicleInfo;

	const handleChangeField = (fieldName: string, value: string) => {
		const payload = {
			...form,
			[fieldName]: value,
		};

		dispatch({payload, type: ACTIONS.SET_VEHICLE_INFO_FORM});
		dispatch({payload: true, type: ACTIONS.SET_HAS_FORM_CHANGE});
	};

	return (
		<div className="bg-neutral-0 mx-8">
			<ClayForm>
				<div className="row">
					<div className="col filled form-condensed form-group">
						<ClayInput
							autoComplete="off"
							className="bg-neutral-0 w-100 year"
							id="year"
							name="year"
							onChange={(event) => {
								handleChangeField('year', event.target.value);
							}}
							required
							type="text"
							value={form.year}
						/>

						<label htmlFor="year">
							Year&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>
					</div>

					<div className="col filled form-condensed form-group">
						<ClayInput
							autoComplete="off"
							className="bg-neutral-0 make w-100"
							id="make"
							name="make"
							onChange={(event) => {
								handleChangeField('make', event.target.value);
							}}
							required
							type="text"
							value={form.make}
						/>

						<label htmlFor="make">
							Make&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>
					</div>

					<div className="col filled form-condensed form-group">
						<ClayInput
							autoComplete="off"
							className="bg-neutral-0 model w-100"
							id="model"
							name="model"
							onChange={(event) => {
								handleChangeField('model', event.target.value);
							}}
							type="text"
							value={form.model}
						/>

						<label htmlFor="model">Model</label>
					</div>
				</div>

				<div className="row">
					<div className="col filled form-condensed form-group">
						<ClayInput
							autoComplete="off"
							className="bg-neutral-0 primary-usage w-100"
							id="primary-usage"
							name="primary-usage"
							onChange={(event) => {
								handleChangeField(
									'primaryUsage',
									event.target.value
								);
							}}
							required
							type="text"
							value={form.primaryUsage}
						/>

						<label htmlFor="primary-usage">
							Vehicle Primary Usage&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>
					</div>

					<div className="col filled form-condensed form-group">
						<ClayInput
							autoComplete="off"
							className="annual-mileage bg-neutral-0 w-100"
							id="annual-mileage"
							name="annual-mileage"
							onChange={(event) => {
								handleChangeField(
									'annualMileage',
									event.target.value
								);
							}}
							required
							type="text"
							value={form.annualMileage}
						/>

						<label htmlFor="annual-mileage">
							Annual Mileage&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>
					</div>

					<div className="col filled form-condensed form-group">
						<ClayInput
							autoComplete="off"
							className="bg-neutral-0 ownership w-100"
							id="ownership"
							name="ownership"
							onChange={(event) => {
								handleChangeField(
									'ownership',
									event.target.value
								);
							}}
							required
							type="text"
							value={form.ownership}
						/>

						<label htmlFor="email">
							Vehicle Ownership&nbsp;
							<span className="text-danger-darken-1">*</span>
						</label>
					</div>
				</div>
			</ClayForm>
		</div>
	);
};

export default VehicleInfo;
