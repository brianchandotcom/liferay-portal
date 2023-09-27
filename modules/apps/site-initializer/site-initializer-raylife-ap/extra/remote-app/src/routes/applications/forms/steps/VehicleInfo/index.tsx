/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

			const coverageVehicleObject = {
				collision: '',
				comprehensive: '',
			};

			dispatch({
				payload: vehicleInfoObject,
				type: ACTIONS.SET_NEW_VEHICLE,
			});

			dispatch({
				payload: {
					...state.steps.coverage.form,
					vehicles: [
						...state.steps.coverage.form.vehicles,
						coverageVehicleObject,
					],
				},
				type: ACTIONS.SET_COVERAGE_FORM,
			});
		}
	};

	const handleSaveChanges = (currentForm: any) => {
		const hasAllRequiredFieldsToNextStep = currentForm?.every(
			(_form: any) =>
				_form.annualMileage !== '' &&
				_form.make !== '' &&
				_form.ownership !== '' &&
				_form.primaryUsage !== '' &&
				_form.year !== ''
		);

		const minAnnualMileage = 50;

		const annualGreaterThanFifty = currentForm?.every(
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
		<div className="bg-neutral-0 mx-1 mx-md-8">
			<ClayForm>
				{form?.map((currentForm, index) => (
					<VehicleInfoForm
						form={form}
						formIndex={index}
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
