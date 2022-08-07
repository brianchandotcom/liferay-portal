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

import {Form, Formik} from 'formik';
import {useState} from 'react';

import Goals from '../steps/Goals';
import yup from '../steps/Goals/schema/yup';
import {requestStatus} from '../utils/constants/requestStatus';
import {stepType} from '../utils/constants/stepType';
import MDFRequest from '../utils/types/mdfRequest';

const initialFormValues: MDFRequest = {
	activities: [],
	country: '',
	liferayBusinessSalesGoals: [],
	overallCampaign: '',
	r_additionalOption_mdfRequest: '',
	r_company_accountEntryId: '',
	requestStatus: requestStatus.DRAFT,
	targetsAudienceRole: [],
	targetsMarket: [],
};

type StepComponent = {
	[key in stepType]?: JSX.Element;
};

const MdfForm = () => {
	const [step, setStep] = useState<stepType>(stepType.GOALS);

	const onSubmit = (value: MDFRequest) => {
		// eslint-disable-next-line no-console
		console.log(value);
	};

	const StepFormComponent: StepComponent = {
		[stepType.GOALS]: (
			<Goals onContinue={() => setStep(stepType.ACTIVITIES)} />
		),
	};

	return (
		<Formik
			initialValues={initialFormValues}
			onSubmit={onSubmit}
			validationSchema={yup}
		>
			<Form>{StepFormComponent[step]}</Form>
		</Formik>
	);
};

export default MdfForm;
