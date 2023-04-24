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

import ClayAlert from '@clayui/alert';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {FormikHelpers, setNestedObjectValues} from 'formik';
import {useMemo, useState} from 'react';

import PRMFormik from '../../common/components/PRMFormik';
import {ObjectActionName} from '../../common/enums/objectActionName';
import {PermissionActionType} from '../../common/enums/permissionActionType';
import {PRMPageRoute} from '../../common/enums/prmPageRoute';
import useLiferayNavigate from '../../common/hooks/useLiferayNavigate';
import usePermissionActions from '../../common/hooks/usePermissionActions';
import MDFRequestDTO from '../../common/interfaces/dto/mdfRequestDTO';
import MDFRequest from '../../common/interfaces/mdfRequest';
import {Liferay} from '../../common/services/liferay';
import useGetMDFRequestById from '../../common/services/liferay/object/mdf-requests/useGetMDFRequestById';
import useGetMyUserAccount from '../../common/services/liferay/user-account/useGetMyUserAccount';
import {Status} from '../../common/utils/constants/status';
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
	currency: {},
	liferayBusinessSalesGoals: [],
	maxDateActivity: '',
	mdfRequestStatus: Status.DRAFT,
	minDateActivity: '',
	overallCampaignDescription: '',
	overallCampaignName: '',
	targetAudienceRoles: [],
	targetMarkets: [],
	totalCostOfExpense: 0,
	totalMDFRequestAmount: 0,
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
	const {data: myUserAccountData} = useGetMyUserAccount();
	const actions = usePermissionActions(ObjectActionName.MDF_REQUEST);

	const hasPermissionToAccess = useMemo(
		() =>
			actions?.some(
				(action) =>
					action === PermissionActionType.CREATE ||
					action === PermissionActionType.UPDATE ||
					action === PermissionActionType.UPDATE_WO_CHANGE_STATUS
			),
		[actions]
	);

	const currentMDFRequestHasValidStatus =
		data?.mdfRequestStatus.key === Status.DRAFT.key ||
		data?.mdfRequestStatus.key === Status.REQUEST_MORE_INFO.key;

	const hasPermissionShowForm = mdfRequestId
		? hasPermissionToAccess && currentMDFRequestHasValidStatus
		: hasPermissionToAccess;

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
				) => submitForm(values, formikHelpers, siteURL, Status.DRAFT)}
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
				) => submitForm(values, formikHelpers, siteURL, Status.DRAFT)}
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
				) => submitForm(values, formikHelpers, siteURL, Status.DRAFT)}
			/>
		),
	};

	if (
		((isValidating || !data) && mdfRequestId) ||
		!myUserAccountData ||
		!actions
	) {
		return <ClayLoadingIndicator />;
	}

	if (!hasPermissionShowForm) {
		return (
			<ClayAlert className="m-0 w-100" displayType="info" title="Info:">
				You don&apos;t have permission
			</ClayAlert>
		);
	}

	return (
		<PRMFormik
			initialValues={
				mdfRequestId
					? getMDFRequestFromDTO(data as MDFRequestDTO)
					: initialFormValues
			}
			onSubmit={(values, formikHelpers) =>
				submitForm(
					values,
					formikHelpers,
					siteURL,
					Status.PENDING,
					actions.every(
						(action) =>
							action !==
							PermissionActionType.UPDATE_WO_CHANGE_STATUS
					)
				)
			}
		>
			{StepFormComponent[step]}
		</PRMFormik>
	);
};

export default MDFRequestForm;
