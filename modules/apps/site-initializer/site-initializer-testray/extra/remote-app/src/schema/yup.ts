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
		id: yup.string(),
		name: yup.string().required(),
		productVersionId: yup.string().required(),
		promoted: yup.boolean(),
		routineId: yup.string().required(),
		template: yup.boolean(),
	}),
	case: yup.object({
		addAnother: yup.boolean(),
		caseTypeId: yup.string().required(),
		componentId: yup.string().required(),
		description: yup.string(),
		descriptionType: yup.string(),
		estimatedDuration: yup.number().min(0),
		name: yup.string().required(),
		priority: yup.string(),
		steps: yup.string(),
		stepsType: yup.string(),
	}),
	caseResult: yup.object({
		commentMBMessage: yup.string(),
		dueStatus: yup.string().required(),
		issues: yup.string(),
	}),
	caseType: yup.object({
		name: yup.string().required(),
	}),
	factorCategory: yup.object({
		id: yup.string(),
		name: yup.string().required(),
	}),
	factorOption: yup.object({
		factorCategoryId: yup.string(),
		name: yup.string().required(),
	}),
	option: yup.object({
		name: yup.string(),
	}),
	password: yup.object({
		confirmpassword: yup
			.string()
			.required()
			.oneOf(
				[yup.ref('password'), null],
				i18n.translate('passwords-must-match')
			),
		password: yup
			.string()
			.required()
			.matches(
				/[a-zA-Z]/,
				i18n.translate('password-can-only-contain-latin-letters')
			),
	}),
	project: yup.object({
		description: yup.string().notRequired(),
		id: yup.string().notRequired(),
		name: yup.string().required(),
	}),
	requirement: yup.object({
		componentId: yup.string().required(),
		description: yup.string().required(),
		descriptionType: yup.string().required(),
		id: yup.string(),
		key: yup.string(),
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
		caseParameters: yup.string(),
		description: yup.string(),
		id: yup.string(),
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
