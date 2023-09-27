/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
