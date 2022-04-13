/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {yupResolver} from '@hookform/resolvers/yup';
import * as yup from 'yup';

const yupSchema = {
	case: yup.object({
		componentId: yup.string().required(),
		description: yup.string(),
		descriptionType: yup.string(),
		estimatedDuration: yup.number(),
		name: yup.string().required(),
		priority: yup.number(),
		steps: yup.string(),
		stepsType: yup.string().required(),
	}),
	caseType: yup.object({
		name: yup.string().required(),
	}),
	factorCategory: yup.object({
		name: yup.string().required(),
	}),
	factorOption: yup.object({
		factorCategoryId: yup.string().required(),
		name: yup.string().required(),
	}),
	project: yup.object({
		description: yup.string().required(),
		name: yup.string().required(),
	}),
	routine: yup.object({
		autoanalyze: yup.boolean(),
		name: yup.string().required(),
	}),
	suite: yup.object({
		autoanalyze: yup.boolean(),
		description: yup.string(),
		name: yup.string().required(),
		smartSuite: yup.string(),
	}),
};

export {yupResolver};

export default yupSchema;
