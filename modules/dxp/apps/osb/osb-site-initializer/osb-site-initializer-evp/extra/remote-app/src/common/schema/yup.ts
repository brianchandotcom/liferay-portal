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

import {yupResolver} from '@hookform/resolvers/yup';
import * as yup from 'yup';

const yupSchema = {
	payment: yup.object({
		finalPaymentDate: yup.string(),
		initialPaymentDate: yup.string(),
	}),
	report: yup.object({
		finalCompanyId: yup.string(),
		finalRequestDate: yup.string(),
		fullName: yup.string(),
		initialCompanyId: yup.string(),
		initialRequestDate: yup.string(),
		liferayBranch: yup.array(yup.string()),
		organizationName: yup.string(),
		requestStatus: yup.array(yup.string()),
	}),
};

export {yupResolver};

export default yupSchema;
