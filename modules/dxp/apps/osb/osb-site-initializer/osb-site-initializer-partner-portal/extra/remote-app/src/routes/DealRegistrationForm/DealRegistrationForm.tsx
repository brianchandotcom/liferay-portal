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
import DealRegistration from '../../common/interfaces/dealRegistration';
import {StepType} from './enums/stepType';
import General from './steps/General';
import generalSchema from './steps/General/schema/yup';
import Review from './steps/Review';

const initialFormValues: DealRegistration = {
	additionalContact: {emailAddress: '', firstName: '', lastName: ''},
	additionalInformationAboutTheOpportunity: '',
	categories: [],
	generalMdfActivityAssociated: {},
	partnerAccountName: {},
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

	const onSubmit = () => {};

	const onCancel = () => {
		setStep(StepType.GENERAL);
	};
	const onSaveAsDraft = () => {};
	const onContinue = () => {
		setStep(StepType.REVIEW);
	};

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
			<Review onCancel={onCancel} onSaveAsDraft={onSaveAsDraft} />
		),
	};

	return (
		<PRMFormik initialValues={initialFormValues} onSubmit={onSubmit}>
			{StepFormComponent[step]}
		</PRMFormik>
	);
};

export default DealRegistrationForm;
