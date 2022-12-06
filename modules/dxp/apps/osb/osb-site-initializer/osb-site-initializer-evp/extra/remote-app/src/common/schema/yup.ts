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
	report: yup.object({
		branchRequest: yup.array(yup.number()),
		finalCompanyId: yup.string(),
		finalCompanyName: yup.string(),
		finalRequestDate: yup.string(),
		finalUserName: yup.string(),
		initialCompanyId: yup.string(),
		initialCompanyName: yup.string(),
		initialRequestDate: yup.string(),
		initialUserName: yup.string(),
		statusRequest: yup.array(yup.number()),
	}),
};

export {yupResolver};

export default yupSchema;
