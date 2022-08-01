/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {useState} from 'react';

import ActivitiesForm from './pages/ActivitiesForm';
import ActivitiesList from './pages/ActivitiesList';
import GoalsPage from './pages/GoalsPage';
import {STEP_TYPES} from './utils/constants/stepType';

const MdfForm = () => {
	const [step, setStep] = useState<any>(STEP_TYPES.goalsPage);
	const [generalObject, setGeneralObject] = useState<any>();
	const StepLayout = {
		[STEP_TYPES.activitiesList]: (
			<ActivitiesList generalObject={generalObject} setStep={setStep} />
		),
		[STEP_TYPES.goalsPage]: (
			<GoalsPage
				generalObject={generalObject}
				setGeneralObject={setGeneralObject}
				setStep={setStep}
			/>
		),
		[STEP_TYPES.activitiesForm]: (
			<ActivitiesForm
				generalObject={generalObject}
				setGeneralObject={setGeneralObject}
				setStep={setStep}
			/>
		),
	};

	return StepLayout[step];
};

export default MdfForm;
