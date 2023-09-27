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
import DriverInfoForm from './DriverInfoForm';

const DriverInfo = () => {
	const [state, dispatch] = useContext(NewApplicationAutoContext);

	const {form} = state.steps.driverInfo;

	const handleAddDriverClick = () => {
		const id = Number((Math.random() * 1000000).toFixed(0));

		const isThereId = form.some((currentForm) => currentForm.id === id);

		if (!isThereId) {
			const driverInfoObject = {
				accidentCitation: [],
				ageFirstLicenced: '',
				firstName: '',
				gender: '',
				governmentAffiliation: '',
				hasAccidentOrCitations: '',
				highestEducation: '',
				id,
				lastName: '',
				maritalStatus: '',
				millitaryAffiliation: '',
				occupation: '',
				otherOccupation: '',
				relationToContact: '',
			};

			dispatch({
				payload: driverInfoObject,
				type: ACTIONS.SET_NEW_DRIVER,
			});
		}
	};

	const handleSaveChanges = (currentForm: any) => {
		const hasAllRequiredFieldsToNextStep = currentForm.every(
			(_form: any) => {
				const formValidation =
					_form.firstName !== '' &&
					_form.lastName !== '' &&
					_form.relationToContact !== '' &&
					_form.gender !== '' &&
					_form.maritalStatus !== '' &&
					_form.ageFirstLicenced !== '' &&
					_form.occupation !== '' &&
					_form.highestEducation !== '' &&
					_form.millitaryAffiliation !== '' &&
					_form.hasAccidentOrCitations !== '';

				if (_form.occupation === 'Other') {
					return formValidation && _form.otherOccupation !== '';
				}
				if (_form.occupation === 'Government') {
					return formValidation && _form.governmentAffiliation !== '';
				}

				return formValidation;
			}
		);

		dispatch({
			payload: true,
			type: ACTIONS.SET_IS_ABLE_TO_SAVE,
		});
		dispatch({
			payload: hasAllRequiredFieldsToNextStep,
			type: ACTIONS.SET_IS_ABLE_TO_NEXT,
		});
	};

	useEffect(() => {
		handleSaveChanges(form);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [state.steps.driverInfo]);

	return (
		<div className="bg-neutral-0 mx-1 mx-md-8">
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
					className="text-uppercase"
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
