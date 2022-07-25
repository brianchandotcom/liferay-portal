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
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useContext, useEffect} from 'react';

import {
	ACTIONS,
	NewApplicationAutoContext,
} from '../../../context/NewApplicationAutoContextProvider';
import VehicleInfoForm from './VehicleInfoForm';

const VehicleInfo = () => {
	const [state, dispatch] = useContext(NewApplicationAutoContext);

	const {form} = state.steps.vehicleInfo;

	const handleAddVehicleClick = () => {
		const id = Number((Math.random() * 1000000).toFixed(0));

		const isThereId = form.some((currentForm) => currentForm.id === id);

		if (!isThereId) {
			const vehicleInfoObject = {
				annualMileage: '',
				id,
				make: '',
				model: '',
				ownership: '',
				primaryUsage: '',
				year: '',
			};

			dispatch({
				payload: vehicleInfoObject,
				type: ACTIONS.SET_NEW_VEHICLE,
			});
		}
	};

	const handleSaveChanges = (currentForm: any) => {
		const hasAllRequiredFieldsToNextStep = currentForm.every(
			(_form: any) =>
				_form.annualMileage !== '' &&
				_form.make !== '' &&
				_form.ownership !== '' &&
				_form.primaryUsage !== '' &&
				_form.year !== ''
		);

		const minAnnualMileage = 50;

		const annualGreaterThanFifty = currentForm.every(
			(_form: any) => _form.annualMileage >= minAnnualMileage
		);

		dispatch({
			payload: true,
			type: ACTIONS.SET_IS_ABLE_TO_SAVE,
		});
		dispatch({
			payload: hasAllRequiredFieldsToNextStep && annualGreaterThanFifty,
			type: ACTIONS.SET_IS_ABLE_TO_NEXT,
		});
	};

	useEffect(() => {
		handleSaveChanges(form);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [state.steps.vehicleInfo]);

	return (
		<div className="bg-neutral-0 mx-8">
			<ClayForm>
				{form.map((currentForm, index) => (
					<VehicleInfoForm
						form={form}
						formNumber={index + 1}
						id={currentForm.id}
						key={index}
					/>
				))}

				<ClayButton
					className="font-weight-bold pl-0 text-brand-primary text-paragraph-sm"
					displayType="link"
					onClick={() => handleAddVehicleClick()}
				>
					<ClayIcon symbol="plus" />
					&nbsp;ADD VEHICLE
				</ClayButton>
			</ClayForm>
		</div>
	);
};

export default VehicleInfo;
