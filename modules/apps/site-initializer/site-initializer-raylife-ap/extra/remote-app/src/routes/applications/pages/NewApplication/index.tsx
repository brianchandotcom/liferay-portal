/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext} from 'react';

import {NewApplicationAutoContext} from '../../context/NewApplicationAutoContextProvider';
import DrivingUnderTheInfluence from '../../forms/steps/Citation';
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

			{state.currentStep === 5 && <DrivingUnderTheInfluence />}
		</NewApplicationAuto>
	);
};

export default NewApplication;
