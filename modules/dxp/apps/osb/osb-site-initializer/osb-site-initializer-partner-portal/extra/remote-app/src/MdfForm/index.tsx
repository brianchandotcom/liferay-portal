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

import {stepType} from '../utils/constants/stepType';
import ActivitiesForm from './pages/ActivitiesForm';
import ActivitiesList from './pages/ActivitiesList';
import GoalsPage from './pages/GoalsPage';
import ReviewForm from './pages/ReviewForm';

const MdfForm = () => {
	const [step, setStep] = useState<stepType>(stepType.GOALS);
	const [generalObject, setGeneralObject] = useState<any>();
	const StepLayout = {
		[stepType.ACTIVITIES]: (
			<ActivitiesList generalObject={generalObject} setStep={setStep} />
		),
		[stepType.GOALS]: (
			<GoalsPage
				generalObject={generalObject}
				setGeneralObject={setGeneralObject}
				setStep={setStep}
			/>
		),
		[stepType.ACTIVITY]: (
			<ActivitiesForm
				generalObject={generalObject}
				setGeneralObject={setGeneralObject}
				setStep={setStep}
			/>
		),
		[stepType.REVIEW]: (
			<ReviewForm generalObject={generalObject} setStep={setStep} />
		),
	};

	return StepLayout[step];
};

export default MdfForm;
