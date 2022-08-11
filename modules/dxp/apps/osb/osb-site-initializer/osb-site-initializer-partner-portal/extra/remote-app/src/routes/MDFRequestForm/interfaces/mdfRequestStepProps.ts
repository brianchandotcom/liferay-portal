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

import {FormikHelpers} from 'formik';

import {StepType} from '../enums/stepType';

export default interface MDFRequestStepProps<T> {
	onCancel: () => void;
	onContinue?: (
		formikHelpers: Omit<FormikHelpers<T>, 'setFieldValue'>,
		nextStep: StepType
	) => void;
	onPrevious?: (previousStep: StepType) => void;
	onSaveAsDraft?: (
		values: T,
		formikHelpers: Omit<FormikHelpers<T>, 'setFieldValue'>
	) => void;
	onSubmit?: (
		values: T,
		formikHelpers: Omit<FormikHelpers<T>, 'setFieldValue'>
	) => void;
}
