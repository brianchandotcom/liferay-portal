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

import {FormikHelpers, setNestedObjectValues} from 'formik';
import {useState} from 'react';

import PRMFormik from '../../common/components/PRMFormik';
import {RequestStatus} from '../../common/enums/requestStatus';
import MDFRequest from '../../common/interfaces/mdfRequest';
import {StepType} from './enums/stepType';
import Goals from './steps/Goals';
import goalsSchema from './steps/Goals/schema/yup';
import isObjectEmpty from './utils/isObjectEmpty';

const initialFormValues: MDFRequest = {
	activities: [],
	country: {},
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

	const onSubmit = (
		value: MDFRequest,
		formikHelpers: FormikHelpers<MDFRequest>
	) => {
		// eslint-disable-next-line no-console
		console.log(value);
		formikHelpers.setSubmitting(false);
	};

	const onSaveAsDraft = (
		value: MDFRequest,
		formikHelpers: FormikHelpers<MDFRequest>
	) => {
		// eslint-disable-next-line no-console
		console.log(value);
		formikHelpers.setSubmitting(true);
	};

	const onContinue = async (formikHelpers: FormikHelpers<MDFRequest>) => {
		const validationErrors = await formikHelpers.validateForm();

		if (isObjectEmpty(validationErrors)) {
			setStep(StepType.ACTIVITIES);

			return;
		}

		formikHelpers.setTouched(setNestedObjectValues(validationErrors, true));
	};

	const onCancel = () => {
		// eslint-disable-next-line no-console
		console.log('Cancel!');
	};

	const StepFormComponent: StepComponent = {
		[StepType.GOALS]: (
			<Goals
				onCancel={onCancel}
				onContinue={onContinue}
				onSaveAsDraft={onSaveAsDraft}
				validationSchema={goalsSchema}
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
