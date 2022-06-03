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

import i18n from '../i18n';

const yupSchema = {
	build: yup.object({
		description: yup.string(),
		gitHash: yup.string(),
		name: yup.string().required(),
		productVersionId: yup.string(),
		routineId: yup.string(),
		template: yup.string(),
	}),
	case: yup.object({
		addAnother: yup.boolean(),
		caseTypeId: yup.string().required(),
		componentId: yup.string().required(),
		description: yup.string(),
		descriptionType: yup.string(),
		estimatedDuration: yup.number(),
		name: yup.string().required(),
		priority: yup.number(),
		steps: yup.string(),
		stepsType: yup.string(),
	}),
	caseType: yup.object({
		name: yup.string().required(),
	}),
	factorCategory: yup.object({
		name: yup.string().required(),
	}),
	factorOption: yup.object({
		factorCategoryId: yup.string(),
		name: yup.string().required(),
	}),
	project: yup.object({
		description: yup.string(),
		name: yup.string().required(),
	}),
	requirement: yup.object({
		componentId: yup.string().required(),
		description: yup.string().required(),
		descriptionType: yup.string().required(),
		linkTitle: yup.string().required(),
		linkURL: yup.string().required(),
		summary: yup.string().required(),
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
	user: yup.object({
		alternateName: yup.string().required(),
		emailAddress: yup.string().email().required(),
		familyName: yup.string().required(),
		givenName: yup.string().required(),
		password: yup
			.string()
			.required(i18n.translate('no-password-provided'))
			.min(
				8,
				i18n.translate(
					'password-is-too-short-should-be-8-chars-minimum'
				)
			)
			.matches(
				/[a-zA-Z]/,
				i18n.translate('password-can-only-contain-latin-letters')
			),
		repassword: yup
			.string()
			.oneOf(
				[yup.ref('password'), null],
				i18n.translate('passwords-must-match')
			),
	}),
};

export {yupResolver};

export default yupSchema;
