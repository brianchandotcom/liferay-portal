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
import React, {useContext} from 'react';

import {
	ACTIONS,
	NewApplicationAutoContext,
} from '../../../context/NewApplicationAutoContextProvider';
import DriverInfoForm from './DriverInfoForm';

const DriverInfo = () => {
	const [state, dispatch] = useContext(NewApplicationAutoContext);

	const {form} = state.steps.driverInfo;

	const handleAddDriverClick = () => {
		const id = Number((Math.random() * 1000000).toFixed(0));

		const isThereId = form.some((currentForm) => currentForm.id === id);

		if (!isThereId) {
			const driverInfoObject = {
				accidentCitation: [
					{
						id: Number((Math.random() * 1000000).toFixed(0)),
						value: '',
					},
				],
				ageFirstLicenced: '',
				firstName: '',
				gender: '',
				governmentAffiliation: '',
				hasAccidentOrCitations: false,
				highestEducation: '',
				id,
				lastName: '',
				maritalStatus: '',
				militaryAffiliation: '',
				ocupation: '',
				otherOcupation: '',
				relationToContact: '',
			};

			dispatch({
				payload: driverInfoObject,
				type: ACTIONS.SET_NEW_DRIVER,
			});
		}
	};

	return (
		<div className="bg-neutral-0 mx-8">
			<ClayForm>
				{form.map((currentForm, index) => (
					<DriverInfoForm
						accidentCitationForm={currentForm.accidentCitation}
						form={form}
						formIndex={index}
						formNumber={index + 1}
						id={currentForm.id}
						key={index}
					/>
				))}

				<ClayButton
					className="ml-7 text-uppercase"
					displayType="link"
					onClick={() => handleAddDriverClick()}
				>
					<ClayIcon symbol="plus" />
					&nbsp;Add Driver
				</ClayButton>
			</ClayForm>
		</div>
	);
};

export default DriverInfo;
