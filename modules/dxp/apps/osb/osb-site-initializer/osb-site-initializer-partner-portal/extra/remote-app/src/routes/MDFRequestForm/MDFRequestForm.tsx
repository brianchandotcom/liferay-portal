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
import {PRMPageRoute} from '../../common/enums/prmPageRoute';
import {RequestStatus} from '../../common/enums/requestStatus';
import useLiferayNavigate from '../../common/hooks/useLiferayNavigate';
import MDFRequest from '../../common/interfaces/mdfRequest';
import {Liferay} from '../../common/services/liferay';
import {StepType} from './enums/stepType';
import Activities from './steps/Activities';
import activitiesSchema from './steps/Activities/schema/yup';
import Goals from './steps/Goals';
import goalsSchema from './steps/Goals/schema/yup';
import Review from './steps/Review/Review';
import isObjectEmpty from './utils/isObjectEmpty';
import submitForm from './utils/submitForm';

const initialFormValues: MDFRequest = {
	activities: [],
	additionalOption: {},
	company: {},
	country: {},
	liferayBusinessSalesGoals: [],
	overallCampaignDescription: '',
	overallCampaignName: '',
	requestStatus: RequestStatus.PENDING,
	targetAudienceRoles: [],
	targetMarkets: [],
};

type StepComponent = {
	[key in StepType]?: JSX.Element;
};

const MDFRequestForm = () => {
	const [step, setStep] = useState<StepType>(StepType.GOALS);
	const siteURL = useLiferayNavigate();

	const onCancel = () =>
		Liferay.Util.navigate(
			`${siteURL}/${PRMPageRoute.MDF_REQUESTS_LISTING}`
		);

	const onContinue = async (
		formikHelpers: Omit<FormikHelpers<MDFRequest>, 'setFieldValue'>,
		nextStep: StepType
	) => {
		const validationErrors = await formikHelpers.validateForm();

		if (isObjectEmpty(validationErrors)) {
			setStep(nextStep);

			return;
		}

		formikHelpers.setTouched(setNestedObjectValues(validationErrors, true));
	};

	const onPrevious = (previousStep: StepType) => setStep(previousStep);

	const StepFormComponent: StepComponent = {
		[StepType.GOALS]: (
			<Goals
				onCancel={onCancel}
				onContinue={onContinue}
				onSaveAsDraft={(
					values: MDFRequest,
					formikHelpers: Omit<
						FormikHelpers<MDFRequest>,
						'setFieldValue'
					>
				) =>
					submitForm(
						values,
						formikHelpers,
						siteURL,
						RequestStatus.DRAFT
					)
				}
				validationSchema={goalsSchema}
			/>
		),
		[StepType.ACTIVITIES]: (
			<PRMFormik.Array
				component={Activities}
				name="activities"
				onCancel={onCancel}
				onContinue={onContinue}
				onPrevious={onPrevious}
				onSaveAsDraft={(
					values: MDFRequest,
					formikHelpers: Omit<
						FormikHelpers<MDFRequest>,
						'setFieldValue'
					>
				) =>
					submitForm(
						values,
						formikHelpers,
						siteURL,
						RequestStatus.DRAFT
					)
				}
				validationSchema={activitiesSchema}
			/>
		),
		[StepType.REVIEW]: (
			<Review
				onCancel={onCancel}
				onPrevious={onPrevious}
				onSaveAsDraft={(
					values: MDFRequest,
					formikHelpers: Omit<
						FormikHelpers<MDFRequest>,
						'setFieldValue'
					>
				) =>
					submitForm(
						values,
						formikHelpers,
						siteURL,
						RequestStatus.DRAFT
					)
				}
			/>
		),
	};

	return (
		<PRMFormik
			initialValues={initialFormValues}
			onSubmit={(values, formikHelpers) =>
				submitForm(values, formikHelpers, siteURL)
			}
		>
			{StepFormComponent[step]}
		</PRMFormik>
	);
};

export default MDFRequestForm;
