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
import DealRegistration from '../../common/interfaces/dealRegistration';
import {Liferay} from '../../common/services/liferay';
import {StepType} from './enums/stepType';
import General from './steps/General';
import generalSchema from './steps/General/schema/yup';
import Review from './steps/Review';
import isObjectEmpty from './utils/isObjectEmpty';
import submitForm from './utils/submitForm';

const initialFormValues: DealRegistration = {
	additionalContact: {emailAddress: '', firstName: '', lastName: ''},
	additionalInformationAboutTheOpportunity: '',
	mdfActivityAssociated: {},
	partnerAccount: {},
	primaryProspect: {
		businessUnit: '',
		department: {},
		emailAddress: '',
		firstName: '',
		jobRole: {},
		lastName: '',
		phone: '',
	},
	projectCategories: [],
	projectNeed: [],
	projectTimeline: '',
	prospect: {
		accountName: '',
		address: '',
		city: '',
		country: {},
		industry: {},
		postalCode: '',
		state: {},
	},
	registrationStatus: RequestStatus.PENDING,
};

const initialFormValues: DealRegistration = {
	additionalContact: {emailAddress: '', firstName: '', lastName: ''},
	additionalInformationAboutTheOpportunity: '',
	categories: [],
	mdfActivityAssociated: {},
	partnerAccount: {},
	primaryProspect: {
		businessUnit: '',
		department: {},
		emailAddress: '',
		firstName: '',
		jobRole: {},
		lastName: '',
		phone: '',
	},
	projectNeed: [],
	projectTimeline: '',
	prospect: {
		accountName: '',
		address: '',
		city: '',
		country: {},
		industry: {},
		postalCode: '',
		state: {},
	},
	requestStatus: RequestStatus.PENDING,
};

type StepComponent = {
	[key in StepType]?: JSX.Element;
};

const DealRegistrationForm = () => {
	const [step, setStep] = useState<StepType>(StepType.GENERAL);
	const siteURL = useLiferayNavigate();

	const onCancel = () => {
		Liferay.Util.navigate(
			`${siteURL}/${PRMPageRoute.DEAL_REGISTRATION_LISTING}`
		);
	};
	const onSaveAsDraft = () => {
		(
			values: DealRegistration,
			formikHelpers: Omit<
				FormikHelpers<DealRegistration>,
				'setFieldValue'
			>
		) => submitForm(values, formikHelpers, siteURL, RequestStatus.DRAFT);
	};

	const onContinue = async (
		formikHelpers: Omit<FormikHelpers<DealRegistration>, 'setFieldValue'>,
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
		[StepType.GENERAL]: (
			<General
				onCancel={onCancel}
				onContinue={onContinue}
				onSaveAsDraft={onSaveAsDraft}
				validationSchema={generalSchema}
			/>
		),
		[StepType.REVIEW]: (
			<Review
				onCancel={onCancel}
				onPrevious={onPrevious}
				onSaveAsDraft={onSaveAsDraft}
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

export default DealRegistrationForm;
