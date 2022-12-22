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

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {FormikHelpers, setNestedObjectValues} from 'formik';
import {useState} from 'react';

import PRMFormik from '../../common/components/PRMFormik';
import {PRMPageRoute} from '../../common/enums/prmPageRoute';
import {Status} from '../../common/enums/status';
import useLiferayNavigate from '../../common/hooks/useLiferayNavigate';
import MDFRequestDTO from '../../common/interfaces/dto/mdfRequestDTO';
import MDFRequest from '../../common/interfaces/mdfRequest';
import {Liferay} from '../../common/services/liferay';
import useGetMDFRequestById from '../../common/services/liferay/object/mdf-requests/useGetMDFRequestById';
import {getMDFRequestFromDTO} from '../../common/utils/dto/mdf-request/getMDFRequestFromDTO';
import isObjectEmpty from '../../common/utils/isObjectEmpty';
import useGetMDFRequestIdByHash from '../MDFClaimForm/hooks/useGetMDFRequestIdByHash';
import {StepType} from './enums/stepType';
import Activities from './steps/Activities';
import activitiesSchema from './steps/Activities/schema/yup';
import Goals from './steps/Goals';
import goalsSchema from './steps/Goals/schema/yup';
import Review from './steps/Review/Review';
import submitForm from './utils/submitForm';

const initialFormValues: MDFRequest = {
	activities: [],
	additionalOption: {},
	company: {},
	country: {},
	liferayBusinessSalesGoals: [],
	overallCampaignDescription: '',
	overallCampaignName: '',
	requestStatus: Status.PENDING,
	targetAudienceRoles: [],
	targetMarkets: [],
};

type StepComponent = {
	[key in StepType]?: JSX.Element;
};

const FIRST_POSITION_AFTER_HASH = 0;

const MDFRequestForm = () => {
	const [step, setStep] = useState<StepType>(StepType.GOALS);
	const siteURL = useLiferayNavigate();

	const mdfRequestId = Number(
		useGetMDFRequestIdByHash(FIRST_POSITION_AFTER_HASH)
	);

	const {data, isValidating} = useGetMDFRequestById(mdfRequestId);

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
						mdfRequestId,
						siteURL,
						Status.DRAFT
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
						mdfRequestId,
						siteURL,
						Status.DRAFT
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
						mdfRequestId,
						siteURL,
						Status.DRAFT
					)
				}
			/>
		),
	};

	if ((isValidating || !data) && mdfRequestId) {
		return <ClayLoadingIndicator />;
	}

	return (
		<PRMFormik
			initialValues={
				mdfRequestId
					? getMDFRequestFromDTO(
							data as MDFRequestDTO,
							Status.PENDING
					  )
					: initialFormValues
			}
			onSubmit={(values, formikHelpers) =>
				submitForm(values, formikHelpers, mdfRequestId, siteURL)
			}
		>
			{StepFormComponent[step]}
		</PRMFormik>
	);
};

export default MDFRequestForm;
