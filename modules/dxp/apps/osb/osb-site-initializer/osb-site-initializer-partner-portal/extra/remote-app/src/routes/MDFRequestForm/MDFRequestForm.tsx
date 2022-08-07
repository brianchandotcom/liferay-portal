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

import PRMFormik from '../../common/components/PRMFormik';
import {RequestStatus} from '../../common/enums/requestStatus';
import MDFRequest from '../../common/interfaces/mdfRequest';
import {StepType} from './enums/stepType';
import Goals from './steps/Goals';
import yup from './steps/Goals/schema/yup';

const initialFormValues: MDFRequest = {
	activities: [],
	country: '',
	liferayBusinessSalesGoals: [],
	overallCampaign: '',
	r_additionalOption_mdfRequest: '',
	r_company_accountEntryId: '',
	requestStatus: RequestStatus.DRAFT,
	targetsAudienceRole: [],
	targetsMarket: [],
};

type StepComponent = {
	[key in StepType]?: JSX.Element;
};

const MDFRequestForm = () => {
	const [step, setStep] = useState<StepType>(StepType.GOALS);

	const onSubmit = (value: MDFRequest) => {
		// eslint-disable-next-line no-console
		console.log(value);
	};

	const StepFormComponent: StepComponent = {
		[StepType.GOALS]: (
			<Goals
				onContinue={() => setStep(StepType.ACTIVITIES)}
				validationSchema={yup}
			/>
		),
	};

	return (
		<PRMFormik initialValues={initialFormValues} onSubmit={onSubmit}>
			{StepFormComponent[step]}
		</PRMFormik>
	);
};

export default MDFRequestForm;
