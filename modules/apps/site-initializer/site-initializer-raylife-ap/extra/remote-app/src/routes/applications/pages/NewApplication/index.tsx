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

import {useContext} from 'react';

import {NewApplicationAutoContext} from '../../context/NewApplicationAutoContextProvider';
import ContactInfo from '../../forms/steps/ContactInfo/ContactInfoForm';
import Coverage from '../../forms/steps/Coverage';
import DriverInfo from '../../forms/steps/DriverInfo';
import Review from '../../forms/steps/Review';
import VehicleInfo from '../../forms/steps/VehicleInfo';
import NewApplicationAuto from './NewApplicationAuto';

const NewApplication = () => {
	const [state] = useContext(NewApplicationAutoContext);

	return (
		<NewApplicationAuto>
			{state.currentStep === 0 && <ContactInfo />}

			{state.currentStep === 1 && <VehicleInfo />}

			{state.currentStep === 2 && <DriverInfo />}

			{state.currentStep === 3 && <Coverage />}

			{state.currentStep === 4 && <Review />}
		</NewApplicationAuto>
	);
};

export default NewApplication;
