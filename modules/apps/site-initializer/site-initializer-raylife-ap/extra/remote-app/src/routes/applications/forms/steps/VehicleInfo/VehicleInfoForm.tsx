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

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useContext} from 'react';

import {
	ACTIONS,
	NewApplicationAutoContext,
} from '../../../context/NewApplicationAutoContextProvider';

type FormVehicleInfoTypes = {
	form: any[];
	formNumber: number;
	id: number;
};

const FormVehicleInfo = ({form, formNumber, id}: FormVehicleInfoTypes) => {
	const [_state, dispatch] = useContext(NewApplicationAutoContext);

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

		dispatch({payload, type: ACTIONS.SET_VEHICLE_INFO_FORM});

		dispatch({payload: true, type: ACTIONS.SET_HAS_FORM_CHANGE});
	};

	const handleRemoveFormClick = (id: number) => {
		const payload = {id};
		dispatch({payload, type: ACTIONS.SET_REMOVE_VEHICLE});
	};

	return (
		<>
			<div className="align-items-center d-flex justify-content-between ml-5 mr-3 row">
				<div className="border-bottom flex-grow-1">
					<span className="font-weight-bolder text-paragraph-sm">
						VEHICLE {formNumber}
					</span>
				</div>

				{form.length > 1 && (
					<div className="">
						<ClayButton
							className="border-white font-weight-bolder text-paragraph-sm"
							displayType="secondary"
							onClick={() => handleRemoveFormClick(id)}
						>
							<ClayIcon symbol="times-circle" />
							&nbsp;REMOVE VEHICLE
						</ClayButton>
					</div>
				)}
			</div>

			<div className="mx-3">
				<hr />
			</div>

			<div className="row">
				<div className="col filled form-condensed form-group">
					<ClayInput
						autoComplete="off"
						className="bg-neutral-0 w-100 year"
						id="year"
						name="year"
						onChange={(event) => {
							handleChangeField('year', event.target.value, id);
						}}
						required
						type="text"
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
							handleChangeField('make', event.target.value, id);
						}}
						required
						type="text"
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
							handleChangeField('model', event.target.value, id);
						}}
						type="text"
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
								event.target.value,
								id
							);
						}}
						required
						type="text"
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
								event.target.value,
								id
							);
						}}
						required
						type="text"
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
								event.target.value,
								id
							);
						}}
						required
						type="text"
					/>

					<label htmlFor="email">
						Vehicle Ownership&nbsp;
						<span className="text-danger-darken-1">*</span>
					</label>
				</div>
			</div>
		</>
	);
};

export default FormVehicleInfo;
